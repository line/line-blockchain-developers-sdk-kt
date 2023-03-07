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
import com.linecorp.link.developers.txresult.v1.raw.model.RawMessageEventKeyType
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
                    setOf(unknownTransactionEvent(eventType.name))
                } else {
                    resolveTransactionEvent(eventType, event, log)
                }
            }.toSet()
        }
    }

    @Suppress("LongMethod", "CyclomaticComplexMethod", "ComplexMethod")
    private fun resolveTransactionEvent(
        eventType: RawMessageEventKeyType,
        event: RawTransactionEvent,
        log: RawTransactionLog,
    ): Set<TransactionEvent> = when (eventType) {
        // account
        RawMessageEventKeyType.AccountMsgCreateAccount -> {
            setOf(txEventConverter.accountCreated(event, log.msgIndex))
        }

        RawMessageEventKeyType.AccountMsgEmpty -> {
            setOf(txEventConverter.emptyMsgCreated(event, log.msgIndex))
        }
        // coin
        RawMessageEventKeyType.CoinMsgSend -> {
            setOf(txEventConverter.coinTransferred(event, log.msgIndex))
        }

        // collection
        RawMessageEventKeyType.CollectionMsgCreate -> {
            val eventGrantPermission = log.findEvent(RawMessageEventKeyType.GrantPermission)
            setOf(txEventConverter.collectionCreated(event, eventGrantPermission, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgIssueFT -> {
            setOf(txEventConverter.collectionFtIssued(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgIssueNFT -> {
            val senderAddress = log.events.first { it.type == "message" }.findAttribute(Sender)
            setOf(txEventConverter.collectionNftIssued(event, senderAddress, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgMintFT -> {
            setOf(txEventConverter.collectionFtMinted(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgMintNFT -> {
            setOf(txEventConverter.collectionNftMinted(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgBurnFT -> {
            setOf(txEventConverter.collectionFtBurned(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgBurnFTFrom -> {
            setOf(txEventConverter.collectionFtBurned(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgBurnNFT -> {
            val eventOperationBurnNft =
                log.findEvent(RawMessageEventKeyType.CollectionOperationBurnNFT)

            setOf(txEventConverter.collectionNftBurned(event, eventOperationBurnNft, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgBurnNFTFrom -> {
            val eventOperationBurnNft =
                log.findEvent(RawMessageEventKeyType.CollectionOperationBurnNFT)

            setOf(txEventConverter.collectionNftBurned(event, eventOperationBurnNft, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgModify -> {
            // TODO figure out better way get sender address
            val senderAddress = log.events.first { it.type == "message" }.findAttribute(Sender)
            setOf(txEventConverter.collectionModified(event, senderAddress, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgTransferFT -> {
            setOf(txEventConverter.collectionFtTransferred(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgTransferFTFrom -> {
            setOf(txEventConverter.collectionFtTransferred(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgTransferNFT -> {

            val eventOperationTransferNFT =
                log.findEvent(RawMessageEventKeyType.CollectionOperationTransferNFT)

            setOf(
                txEventConverter.collectionNftTransferred(event, log.msgIndex),
                txEventConverter.collectionNftHolderChanged(event, eventOperationTransferNFT, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgTransferNFTFrom -> {

            val eventOperationTransferNFT =
                log.findEvent(RawMessageEventKeyType.CollectionOperationTransferNFT)

            setOf(
                txEventConverter.collectionNftTransferred(event, log.msgIndex),
                txEventConverter.collectionNftHolderChanged(event, eventOperationTransferNFT, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgAttach -> {

            val eventOperationRootChanged =
                log.findEvent(RawMessageEventKeyType.CollectionOperationRootChanged)

            setOf(
                txEventConverter.collectionNftAttached(event, log.msgIndex),
                txEventConverter.collectionNftRootChanged(event, eventOperationRootChanged, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgAttachFrom -> {
            val eventOperationRootChanged =
                log.findEvent(RawMessageEventKeyType.CollectionOperationRootChanged)

            setOf(
                txEventConverter.collectionNftAttached(event, log.msgIndex),
                txEventConverter.collectionNftRootChanged(event, eventOperationRootChanged, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgDetach -> {

            val eventOperationRootChanged =
                log.findEvent(RawMessageEventKeyType.CollectionOperationRootChanged)

            setOf(
                txEventConverter.collectionNftDetached(event, log.msgIndex),
                txEventConverter.collectionNftRootChanged(event, eventOperationRootChanged, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgDetachFrom -> {

            val eventOperationRootChanged =
                log.findEvent(RawMessageEventKeyType.CollectionOperationRootChanged)

            setOf(
                txEventConverter.collectionNftDetached(event, log.msgIndex),
                txEventConverter.collectionNftRootChanged(event, eventOperationRootChanged, log.msgIndex)
            )
        }

        RawMessageEventKeyType.CollectionMsgApprove -> {
            setOf(txEventConverter.collectionProxyApproved(event, log.msgIndex))
        }

        RawMessageEventKeyType.CollectionMsgDisapprove -> {
            setOf(txEventConverter.collectionProxyDisapproved(event, log.msgIndex))
        }

        // token
        RawMessageEventKeyType.TokenMsgIssue -> {
            setOf(txEventConverter.tokenIssued(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgMint -> {
            setOf(txEventConverter.tokenMinted(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgBurn -> {
            setOf(txEventConverter.tokenBurned(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgBurnFrom -> {
            setOf(txEventConverter.tokenBurned(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgModify -> {
            val messageEvent = log.events.find { it.type == "message" }
            setOf(txEventConverter.tokenModified(event, messageEvent, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgTransfer -> {
            setOf(txEventConverter.tokenTransferred(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgTransferFrom -> {
            setOf(txEventConverter.tokenTransferred(event, log.msgIndex))
        }

        RawMessageEventKeyType.TokenMsgApprove -> {
            setOf(txEventConverter.tokenProxyApproved(event, log.msgIndex))
        }

//        RawMessageEventKeyType.TokenMsgGrantPermission -> {
//            setOf(txEventConverter.tokenPermissionGranted(event, log.msgIndex))
//        }
//
//        RawMessageEventKeyType.TokenMsgRevokePermission -> {
//            setOf(txEventConverter.tokenPermissionRenounced(event, log.msgIndex))
//        }

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
