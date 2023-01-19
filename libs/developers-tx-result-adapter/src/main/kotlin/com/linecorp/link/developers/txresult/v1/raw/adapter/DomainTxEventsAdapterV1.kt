/*
 * Copyright 2023 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.link.developers.txresult.v1.raw.adapter

import com.linecorp.link.developers.txresult.adapter.TxResultAdapter
import com.linecorp.link.developers.txresult.core.event.UnknownTransactionEvent
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Sender
import com.linecorp.link.developers.txresult.v1.raw.model.RawEventType
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionEvent
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionLog
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult
import com.linecorp.link.developers.txresult.v1.raw.model.convertToEventType
import com.linecorp.link.developers.txresult.v1.raw.model.findAttribute
import com.linecorp.link.developers.txresult.v1.raw.model.findEvent

class DomainTxEventsAdapterV1 : TxResultAdapter<RawTransactionResult, Set<TransactionEvent>> {

    private val txEventConverter: DomainTxEventConverterV1 = DomainTxEventConverterV1()

    override fun adapt(input: RawTransactionResult): Set<TransactionEvent> {

        val logs = input.logs

        return if (input.code != 0 || logs.isNullOrEmpty()) {
            emptySet()
        } else {
            logs.flatMap { log ->
                val msgIndex = log.msgIndex

                val eventType = convertToEventType(input.tx.value.msg[msgIndex].type)
                val event = log.findEvent(eventType!!)

                if (event == null) {
                    setOf(unknownTransactionEvent(eventType?.name))
                } else {
                    resolveTransactionEvent(log, event, eventType)
                }
            }?.toSet()
        }
    }

    private fun resolveTransactionEvent( // TODO Can refactoring with more efficient code?
        log: RawTransactionLog, event: RawTransactionEvent, eventType: RawEventType
    ): Set<TransactionEvent> = when (eventType) {
        // account
        RawEventType.AccountMsgCreateAccount -> {
            setOf(txEventConverter.accountCreated(event))
        }

        RawEventType.AccountMsgEmpty -> {
            setOf(txEventConverter.emptyMsgCreated(event))
        }
        // coin
        RawEventType.CoinMsgSend -> {
            setOf(txEventConverter.coinTransferred(event))
        }
        // token
        RawEventType.TokenMsgIssue -> {
            setOf(txEventConverter.tokenIssued(event))
        }

        RawEventType.TokenMsgMint -> {
            setOf(txEventConverter.tokenMinted(event))
        }

        RawEventType.TokenMsgBurn -> {
            setOf(txEventConverter.tokenBurned(event))
        }

        RawEventType.TokenMsgBurnFrom -> {
            setOf(txEventConverter.tokenBurned(event))
        }

        RawEventType.TokenMsgModify -> {
            setOf(txEventConverter.tokenModified(log, event))
        }

        RawEventType.TokenMsgTransfer -> {
            setOf(txEventConverter.tokenTransferred(event))
        }

        RawEventType.TokenMsgTransferFrom -> {
            setOf(txEventConverter.tokenTransferred(event))
        }

        RawEventType.TokenMsgApprove -> {
            setOf(txEventConverter.tokenProxyApproved(event))
        }

        RawEventType.TokenMsgGrantPermission -> {
            setOf(txEventConverter.tokenPermissionGranted(event))
        }

        RawEventType.TokenMsgRevokePermission -> {
            setOf(txEventConverter.tokenPermissionRenounced(event))
        }

        // collection
        RawEventType.CollectionMsgMintFT -> {
            setOf(txEventConverter.collectionFtMinted(event))
        }

        RawEventType.CollectionMsgCreate -> {
            setOf(txEventConverter.collectionCreated(log, event))
        }

        RawEventType.CollectionMsgIssueFT -> {
            setOf(txEventConverter.collectionFtIssued(event))
        }

        RawEventType.CollectionMsgIssueNFT -> {
            setOf(txEventConverter.collectionNftIssued(event))
        }

        RawEventType.CollectionMsgMintFT -> {
            setOf(txEventConverter.collectionFtMinted(event))
        }

        RawEventType.CollectionMsgMintNFT -> {
            setOf(txEventConverter.collectionNftMinted(event))
        }

        RawEventType.CollectionMsgBurnFT -> {
            setOf(txEventConverter.collectionFtBurned(event))
        }

        RawEventType.CollectionMsgBurnFTFrom -> {
            setOf(txEventConverter.collectionFtBurned(event))
        }

        RawEventType.CollectionMsgBurnNFT -> {
            setOf(txEventConverter.collectionNftBurned(event))
        }

        RawEventType.CollectionMsgBurnNFTFrom -> {
            setOf(txEventConverter.collectionNftBurned(event))
        }

        RawEventType.CollectionMsgModify -> {
            // TODO figure out better way get sender address
            val senderAddress = log.events.first { it.type == "message" }.findAttribute(Sender)
            setOf(txEventConverter.collectionModified(event, senderAddress))
        }

        RawEventType.CollectionMsgTransferFT -> {
            setOf(txEventConverter.collectionFtTransferred(event))
        }

        RawEventType.CollectionMsgTransferFTFrom -> {
            setOf(txEventConverter.collectionFtTransferred(event))
        }

        RawEventType.CollectionMsgTransferNFT -> {
            setOf(
                txEventConverter.collectionNftTransferred(event),
                txEventConverter.collectionNftHolderChanged(event)
            )
        }

        RawEventType.CollectionMsgTransferNFTFrom -> {
            setOf(
                txEventConverter.collectionNftTransferred(event),
                txEventConverter.collectionNftHolderChanged(event)
            )
        }

        RawEventType.CollectionMsgAttach -> {
            setOf(
                txEventConverter.collectionNftAttached(event),
                txEventConverter.collectionNftRootChanged(event)
            )
        }

        RawEventType.CollectionMsgAttachFrom -> {
            setOf(
                txEventConverter.collectionNftAttached(event),
                txEventConverter.collectionNftRootChanged(event)
            )
        }

        RawEventType.CollectionMsgDetach -> {
            setOf(
                txEventConverter.collectionNftDetached(event),
                txEventConverter.collectionNftRootChanged(event)
            )
        }

        RawEventType.CollectionMsgDetachFrom -> {
            setOf(
                txEventConverter.collectionNftDetached(event),
                txEventConverter.collectionNftRootChanged(event)
            )
        }

        RawEventType.CollectionMsgApprove -> {
            setOf(txEventConverter.collectionProxyApproved(event))
        }

        RawEventType.CollectionMsgDisapprove -> {
            setOf(txEventConverter.collectionProxyDisapproved(event))
        }

        else -> {
            setOf(unknownTransactionEvent(eventType.name))
        }
    }

    private fun unknownTransactionEvent(type: String, extraMessage: String? = null) = UnknownTransactionEvent(
        type = type,
        attributes = emptySet(),
        extraMessage = extraMessage
    )
}
