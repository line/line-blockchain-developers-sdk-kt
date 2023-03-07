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

import com.linecorp.link.developers.txresult.core.event.account.EventAccountCreated
import com.linecorp.link.developers.txresult.core.event.account.EventEmptyMsgCreated
import com.linecorp.link.developers.txresult.core.event.bank.EventCoinTransferred
import com.linecorp.link.developers.txresult.core.event.item.CollectionAttribute
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionCreated
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftAttached
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftDetached
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftHolderChanged
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftRootChanged
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTypeModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyApproved
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyDisapproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenBurned
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenMinted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyApproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenTransferred
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.util.ItemTokenTypeUtil
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Amount
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.ContractId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.CreateAccountTarget
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Sender
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenType
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionEvent
import com.linecorp.link.developers.txresult.v1.raw.model.amount
import com.linecorp.link.developers.txresult.v1.raw.model.approverAddress
import com.linecorp.link.developers.txresult.v1.raw.model.attributesExclude
import com.linecorp.link.developers.txresult.v1.raw.model.contractId
import com.linecorp.link.developers.txresult.v1.raw.model.decimals
import com.linecorp.link.developers.txresult.v1.raw.model.exParentTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.findAttribute
import com.linecorp.link.developers.txresult.v1.raw.model.fromAddress
import com.linecorp.link.developers.txresult.v1.raw.model.multiTokenIds
import com.linecorp.link.developers.txresult.v1.raw.model.name
import com.linecorp.link.developers.txresult.v1.raw.model.newRootTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.ownerAddress
import com.linecorp.link.developers.txresult.v1.raw.model.parentTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.parseAmount
import com.linecorp.link.developers.txresult.v1.raw.model.proxyAddress
import com.linecorp.link.developers.txresult.v1.raw.model.recipientAddress
import com.linecorp.link.developers.txresult.v1.raw.model.senderAddress
import com.linecorp.link.developers.txresult.v1.raw.model.symbol
import com.linecorp.link.developers.txresult.v1.raw.model.toAddress
import com.linecorp.link.developers.txresult.v1.raw.model.toTokenAttribute
import com.linecorp.link.developers.txresult.v1.raw.model.tokenId
import com.linecorp.link.developers.txresult.v1.raw.model.tokenType

@Suppress("TooManyFunctions")
class DomainTxEventConverterV1 {

    fun accountCreated(event: RawTransactionEvent, msgIndex: Int): EventAccountCreated {
        val createdAccountAddress = event.findAttribute(CreateAccountTarget)
        return EventAccountCreated(createdAccountAddress, msgIndex)
    }

    fun emptyMsgCreated(event: RawTransactionEvent, msgIndex: Int): EventEmptyMsgCreated {
        val senderAddress = event.findAttribute(Sender)
        return EventEmptyMsgCreated(senderAddress, msgIndex)
    }

    fun coinTransferred(event: RawTransactionEvent, msgIndex: Int): EventCoinTransferred {
        val amountPair = event.findAttribute(Amount).parseAmount()
        return EventCoinTransferred(
            denomination = amountPair.second,
            amount = amountPair.first,
            fromAddress = event.senderAddress(),
            toAddress = event.recipientAddress(),
            msgIndex = msgIndex,
        )
    }

    fun tokenIssued(event: RawTransactionEvent, msgIndex: Int): EventTokenIssued {
        return EventTokenIssued(
            contractId = event.contractId(),
            issuerAddress = event.ownerAddress(),
            name = event.name(),
            symbol = event.symbol(),
            receiverAddress = event.toAddress(),
            amount = event.amount(),
            decimals = event.decimals().toInt(),
            msgIndex = msgIndex,
        )
    }

    fun tokenMinted(event: RawTransactionEvent, msgIndex: Int): EventTokenMinted {
        return EventTokenMinted(
            contractId = event.contractId(),
            minterAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            amount = event.amount(),
            msgIndex = msgIndex,
        )
    }

    fun tokenBurned(event: RawTransactionEvent, msgIndex: Int): EventTokenBurned {
        return EventTokenBurned(
            contractId = event.contractId(),
            amount = event.amount(),
            fromAddress = event.fromAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun tokenModified(
        eventTokenModified: RawTransactionEvent, emptyMessageEvent: RawTransactionEvent?, msgIndex: Int
    ): EventTokenModified {
        val tokenAttributes = eventTokenModified.attributesExclude(ContractId).map {
            it.toTokenAttribute()
        }.toSet()
        return EventTokenModified(
            contractId = eventTokenModified.contractId(),
            modifierAddress = emptyMessageEvent.senderAddress(),
            tokenAttributes = tokenAttributes,
            msgIndex = msgIndex,
        )
    }

    fun tokenTransferred(event: RawTransactionEvent, msgIndex: Int): EventTokenTransferred {
        return EventTokenTransferred(
            contractId = event.contractId(),
            fromAddress = event.fromAddress(),
            amount = event.amount(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun tokenProxyApproved(event: RawTransactionEvent, msgIndex: Int): EventTokenProxyApproved {
        return EventTokenProxyApproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

//    fun tokenPermissionGranted(event: RawTransactionEvent, msgIndex: Int): EventTokenPermissionGranted {
//        TODO()
//    }
//
//    fun tokenPermissionRenounced(event: RawTransactionEvent, msgIndex: Int): EventTokenPermissionRenounced {
//        TODO()
//    }

    fun collectionCreated(
        eventCollectionCreated: RawTransactionEvent,
        eventGrantPerm: RawTransactionEvent?,
        msgIndex: Int,
    ): EventCollectionCreated {
        return EventCollectionCreated(
            contractId = eventCollectionCreated.contractId(),
            creatorAddress = eventGrantPerm.toAddress(),
            name = eventCollectionCreated.name(),
            msgIndex = msgIndex,
        )
    }

    fun collectionFtBurned(event: RawTransactionEvent, msgIndex: Int): EventCollectionFtBurned {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]

        return EventCollectionFtBurned(
            contractId = event.contractId(),
            tokenType = ItemTokenTypeUtil.tokenType(tokenId),
            tokenId = tokenId,
            fromAddress = event.fromAddress(),
            amount = amount,
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionFtIssued(event: RawTransactionEvent, msgIndex: Int): EventCollectionFtIssued {
        return EventCollectionFtIssued(
            contractId = event.contractId(),
            name = event.name(),
            tokenType = ItemTokenTypeUtil.tokenType(event.tokenId()),
            amount = event.amount(),
            decimals = event.decimals().toInt(),
            issuerAddress = event.ownerAddress(),
            receiverAddress = event.toAddress(),
            msgIndex = msgIndex,
        )
    }

    private fun collectionFtModified(
        event: RawTransactionEvent,
        senderAddress: String,
        msgIndex: Int,
    ): EventCollectionFtModified {

        return EventCollectionFtModified(
            contractId = event.contractId(),
            tokenType = ItemTokenTypeUtil.tokenType(event.tokenId()),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenId).map {
                CollectionAttribute(it.key, it.value)
            }.toSet(),
            msgIndex = msgIndex,
        )
    }

    private fun collectionNftTypeModified(
        event: RawTransactionEvent,
        senderAddress: String,
        msgIndex: Int,
    ): EventCollectionNftTypeModified {

        return EventCollectionNftTypeModified(
            contractId = event.contractId(),
            tokenType = event.tokenType(),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenType).map {
                CollectionAttribute(it.key, it.value)
            }.toSet(),
            msgIndex = msgIndex,
        )
    }

    private fun collectionNftModified(
        event: RawTransactionEvent,
        senderAddress: String,
        msgIndex: Int,
    ): EventCollectionNftModified {

        return EventCollectionNftModified(
            contractId = event.contractId(),
            tokenId = event.tokenId(),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenId).map {
                CollectionAttribute(it.key, it.value)
            }.toSet(),
            msgIndex = msgIndex,
        )
    }

    fun collectionFtTransferred(event: RawTransactionEvent, msgIndex: Int): EventCollectionFtTransferred {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]

        return EventCollectionFtTransferred(
            contractId = event.contractId(),
            amount = amount,
            tokenType = ItemTokenTypeUtil.tokenType(tokenId),
            tokenId = tokenId,
            fromAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionModified(
        event: RawTransactionEvent,
        senderAddress: String,
        msgIndex: Int,
    ): TransactionEvent {
        val tokenId = event.tokenId()
        val tokenType = event.tokenType().let { it.ifBlank { tokenId } }
        val isFungible = tokenType.isNotBlank() && tokenType.startsWith("0")
        val eventType = event.type
        return if (eventType == "modify_collection") {
            EventCollectionModified(
                contractId = event.contractId(),
                modifierAddress = senderAddress,
                tokenAttributes = event.attributesExclude(ContractId).map {
                    CollectionAttribute(it.key, it.value)
                }.toSet(),
                msgIndex = msgIndex,
            )
        } else if (eventType == "modify_token_type") {
            collectionNftTypeModified(event, senderAddress, msgIndex)
        } else if (isFungible) {
            collectionFtModified(event, senderAddress, msgIndex)
        } else {
            collectionNftModified(event, senderAddress, msgIndex)
        }
    }

    fun collectionNftBurned(
        eventBurnNft: RawTransactionEvent,
        eventOperationBurnNft: RawTransactionEvent?,
        msgIndex: Int,
    ): EventCollectionNftBurned {
        return EventCollectionNftBurned(
            contractId = eventBurnNft.contractId(),
            tokenIds = eventOperationBurnNft.multiTokenIds(),
            fromAddress = eventBurnNft.fromAddress(),
            proxyAddress = eventBurnNft.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionNftAttached(
        event: RawTransactionEvent,
        msgIndex: Int
    ): EventCollectionNftAttached {
        return EventCollectionNftAttached(
            contractId = event.contractId(),
            holderAddress = event.fromAddress(),
            childTokenId = event.tokenId(),
            parentTokenId = event.parentTokenId(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionNftDetached(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionNftDetached {
        return EventCollectionNftDetached(
            contractId = event.contractId(),
            holderAddress = event.fromAddress(),
            exChildTokenId = event.tokenId(),
            exParentTokenId = event.exParentTokenId(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionNftHolderChanged(
        eventTransferNFT: RawTransactionEvent,
        eventOperationTransferNft: RawTransactionEvent?,
        msgIndex: Int,
    ): EventCollectionNftHolderChanged {
        return EventCollectionNftHolderChanged(
            contractId = eventTransferNFT.contractId(),
            tokenIds = eventOperationTransferNft.multiTokenIds(),
            fromAddress = eventTransferNFT.fromAddress(),
            toAddress = eventTransferNFT.toAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionNftIssued(
        event: RawTransactionEvent,
        issuerAddress: String,
        msgIndex: Int,
    ): EventCollectionNftIssued {
        return EventCollectionNftIssued(
            contractId = event.contractId(),
            tokenType = event.tokenType(),
            issuerAddress = issuerAddress,
            msgIndex = msgIndex,
        )
    }

    fun collectionNftRootChanged(
        event: RawTransactionEvent,
        eventOperationRootChanged: RawTransactionEvent?,
        msgIndex: Int,
    ): EventCollectionNftRootChanged {
        val oldRootTokenId = event.exParentTokenId()
        val newRootTokenId = event.newRootTokenId()
        return EventCollectionNftRootChanged(
            contractId = event.contractId(),
            tokenIds = eventOperationRootChanged.multiTokenIds(),
            oldRootTokenId = oldRootTokenId,
            newRootTokenId = newRootTokenId,
            msgIndex = msgIndex,
        )
    }

    fun collectionNftTransferred(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionNftTransferred {
        return EventCollectionNftTransferred(
            contractId = event.contractId(),
            tokenIds = event.multiTokenIds(),
            fromAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionProxyApproved(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionProxyApproved {
        return EventCollectionProxyApproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionProxyDisapproved(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionProxyDisapproved {
        return EventCollectionProxyDisapproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionFtMinted(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionFtMinted {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]
        return EventCollectionFtMinted(
            contractId = event.contractId(),
            tokenType = ItemTokenTypeUtil.tokenType(tokenId),
            tokenId = tokenId,
            amount = amount,
            minterAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            msgIndex = msgIndex,
        )
    }

    fun collectionNftMinted(
        event: RawTransactionEvent,
        msgIndex: Int,
    ): EventCollectionNftMinted {
        return EventCollectionNftMinted(
            contractId = event.contractId(),
            tokenIds = event.multiTokenIds(),
            toAddress = event.toAddress(),
            minterAddress = event.fromAddress(),
            msgIndex = msgIndex,
        )
    }
}
