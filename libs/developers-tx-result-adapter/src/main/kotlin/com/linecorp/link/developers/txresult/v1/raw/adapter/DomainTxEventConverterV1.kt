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
import com.linecorp.link.developers.txresult.core.event.item.ItemTokenEvent
import com.linecorp.link.developers.txresult.core.event.token.EventTokenBurned
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenMinted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenPermissionGranted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenPermissionRenounced
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyApproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenTransferred
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.util.ItemTokenTypeUtil
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Amount
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.ContractId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.CreateAccountTarget
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.From
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Proxy
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Sender
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenType
import com.linecorp.link.developers.txresult.v1.raw.model.RawEventType.AccountMsgEmpty
import com.linecorp.link.developers.txresult.v1.raw.model.RawEventType.GrantPermission
import com.linecorp.link.developers.txresult.v1.raw.model.RawMessageValueMap
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionEvent
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionLog
import com.linecorp.link.developers.txresult.v1.raw.model.amount
import com.linecorp.link.developers.txresult.v1.raw.model.approverAddress
import com.linecorp.link.developers.txresult.v1.raw.model.attributesExclude
import com.linecorp.link.developers.txresult.v1.raw.model.contractId
import com.linecorp.link.developers.txresult.v1.raw.model.decimals
import com.linecorp.link.developers.txresult.v1.raw.model.exParentTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.findAttribute
import com.linecorp.link.developers.txresult.v1.raw.model.findEvent
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

class DomainTxEventConverterV1 {

    fun accountCreated(event: RawTransactionEvent): EventAccountCreated {
        val createdAccountAddress = event.findAttribute(CreateAccountTarget)
        return EventAccountCreated(createdAccountAddress)
    }

    fun emptyMsgCreated(event: RawTransactionEvent): EventEmptyMsgCreated {
        val senderAddress = event.findAttribute(Sender)
        return EventEmptyMsgCreated(senderAddress)
    }

    fun coinTransferred(event: RawTransactionEvent): EventCoinTransferred {
        val amountPair = event.findAttribute(Amount).parseAmount()
        return EventCoinTransferred(
            denomination = amountPair.second,
            amount = amountPair.first,
            fromAddress = event.senderAddress(),
            toAddress = event.recipientAddress()
        )
    }

    fun tokenIssued(event: RawTransactionEvent): EventTokenIssued {
        return EventTokenIssued(
            contractId = event.contractId(),
            issuerAddress = event.ownerAddress(),
            name = event.name(),
            symbol = event.symbol(),
            receiverAddress = event.toAddress(),
            amount = event.amount(),
            decimals = event.decimals().toInt()
        )
    }

    fun tokenMinted(event: RawTransactionEvent): EventTokenMinted {
        return EventTokenMinted(
            contractId = event.contractId(),
            minterAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            amount = event.amount(),
        )
    }

    fun tokenBurned(event: RawTransactionEvent): EventTokenBurned {
        return EventTokenBurned(
            contractId = event.contractId(),
            amount = event.amount(),
            fromAddress = event.fromAddress(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun tokenModified(log: RawTransactionLog, event: RawTransactionEvent): EventTokenModified {
        val messageTypeEvent = log.findEvent(AccountMsgEmpty)
        val tokenAttributes = event.attributesExclude(ContractId).map {
            it.toTokenAttribute()
        }.toSet()
        return EventTokenModified(
            contractId = event.contractId(),
            modifierAddress = messageTypeEvent.senderAddress(),
            tokenAttributes = tokenAttributes
        )
    }

    fun tokenTransferred(event: RawTransactionEvent): EventTokenTransferred {
        return EventTokenTransferred(
            contractId = event.contractId(),
            fromAddress = event.fromAddress(),
            amount = event.amount(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress()
        )
    }

    fun tokenProxyApproved(event: RawTransactionEvent): EventTokenProxyApproved {
        return EventTokenProxyApproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress()
        )
    }

    fun tokenPermissionGranted(@Suppress("UNUSED_PARAMETER") event: RawTransactionEvent): EventTokenPermissionGranted {
        TODO()
    }

    fun tokenPermissionRenounced(@Suppress("UNUSED_PARAMETER") event: RawTransactionEvent): EventTokenPermissionRenounced {
        TODO()
    }

    fun collectionCreated(
        log: RawTransactionLog,
        event: RawTransactionEvent,
    ): EventCollectionCreated {
        val eventGrantPerm = log.findEvent(GrantPermission)
        return EventCollectionCreated(
            contractId = event.contractId(),
            creatorAddress = eventGrantPerm.toAddress(),
            name = event.name()
        )
    }

    fun collectionFtBurned(event: RawTransactionEvent): EventCollectionFtBurned {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]

        return EventCollectionFtBurned(
            contractId = event.contractId(),
            tokenId = tokenId,
            fromAddress = event.fromAddress(),
            amount = amount,
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionFtIssued(event: RawTransactionEvent): EventCollectionFtIssued {
        return EventCollectionFtIssued(
            contractId = event.contractId(),
            name = event.name(),
            tokenType = event.tokenId(),
            amount = event.amount(),
            decimals = event.decimals().toInt(),
            issuerAddress = event.ownerAddress(),
            receiverAddress = event.toAddress()
        )
    }

    fun collectionFtModified(
        event: RawTransactionEvent,
        senderAddress: String
    ): EventCollectionFtModified {

        return EventCollectionFtModified(
            contractId = event.contractId(),
            tokenType = ItemTokenTypeUtil.tokenType(event.tokenId()),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenId).map {
                CollectionAttribute(it.key, it.value)
            }.toSet()
        )
    }

    fun collectionNftTypeModified(
        event: RawTransactionEvent,
        senderAddress: String,
    ): EventCollectionNftTypeModified {

        return EventCollectionNftTypeModified(
            contractId = event.contractId(),
            tokenType = event.tokenType(),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenType).map {
                CollectionAttribute(it.key, it.value)
            }.toSet()
        )
    }

    fun collectionNftModified(event: RawTransactionEvent, senderAddress: String): EventCollectionNftModified {

        return EventCollectionNftModified(
            contractId = event.contractId(),
            tokenId = event.tokenId(),
            modifierAddress = senderAddress,
            tokenAttributes = event.attributesExclude(ContractId, TokenId).map {
                CollectionAttribute(it.key, it.value)
            }.toSet()
        )
    }

    fun collectionFtTransferred(event: RawTransactionEvent): EventCollectionFtTransferred {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]

        return EventCollectionFtTransferred(
            contractId = event.contractId(),
            amount = amount,
            tokenId = tokenId,
            fromAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress()
        )
    }

    fun collectionModified(event: RawTransactionEvent, senderAddress: String): ItemTokenEvent {
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
                }.toSet()
            )
        } else if (eventType == "modify_token_type") {
            collectionNftTypeModified(event, senderAddress)
        } else if (isFungible) {
            collectionFtModified(event, senderAddress)
        } else {
            collectionNftModified(event, senderAddress)
        }
    }

    fun collectionNftBurned(event: RawTransactionEvent): EventCollectionNftBurned {
        return EventCollectionNftBurned(
            contractId = event.contractId(),
            tokenIds = listOf(event.tokenId()), // TODO get tokenIds from operation_burn_nft
            fromAddress = event.fromAddress(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionNftAttached(event: RawTransactionEvent): EventCollectionNftAttached {
        return EventCollectionNftAttached(
            contractId = event.contractId(),
            holderAddress = event.fromAddress(),
            childTokenId = event.tokenId(),
            parentTokenId = event.parentTokenId(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionNftDetached(event: RawTransactionEvent): EventCollectionNftDetached {
        return EventCollectionNftDetached(
            contractId = event.contractId(),
            holderAddress = event.fromAddress(),
            exChildTokenId = event.tokenId(),
            exParentTokenId = event.exParentTokenId(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionNftHolderChanged(event: RawTransactionEvent): EventCollectionNftHolderChanged {
        return EventCollectionNftHolderChanged(
            contractId = event.contractId(),
            tokenIds = event.multiTokenIds(),
            fromAddress = event.fromAddress(),
            toAddress = event.toAddress()
        )
    }

    fun collectionNftIssued(event: RawTransactionEvent): EventCollectionNftIssued {
        return EventCollectionNftIssued(
            contractId = event.contractId(),
            tokenType = event.tokenType(),
        )
    }

    fun collectionNftRootChanged(event: RawTransactionEvent): EventCollectionNftRootChanged {
        val oldRootTokenId = event.exParentTokenId()
        val newRootTokenId = event.newRootTokenId()
        return EventCollectionNftRootChanged(
            contractId = event.contractId(),
            tokenIds = listOf(event.tokenId()), // TODO tokenIds from operation_root_changed
            oldRootTokenId = oldRootTokenId,
            newRootTokenId = newRootTokenId
        )
    }

    fun collectionNftTransferred(event: RawTransactionEvent): EventCollectionNftTransferred {
        return EventCollectionNftTransferred(
            contractId = event.contractId(),
            tokenIds = event.multiTokenIds(),
            fromAddress = event.fromAddress(),
            toAddress = event.toAddress(),
            proxyAddress = event.proxyAddress()
        )
    }

    fun collectionProxyApproved(event: RawTransactionEvent): EventCollectionProxyApproved {
        return EventCollectionProxyApproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionProxyDisapproved(event: RawTransactionEvent): EventCollectionProxyDisapproved {
        return EventCollectionProxyDisapproved(
            contractId = event.contractId(),
            approverAddress = event.approverAddress(),
            proxyAddress = event.proxyAddress(),
        )
    }

    fun collectionFtMinted(event: RawTransactionEvent): EventCollectionFtMinted {
        val rawAmount = event.amount()
        val amount = rawAmount.split(":")[0]
        val tokenId = rawAmount.split(":")[1]
        return EventCollectionFtMinted(
            contractId = event.contractId(),
            tokenId = tokenId,
            amount = amount,
            minterAddress = event.fromAddress(),
            toAddress = event.toAddress()
        )
    }

    fun collectionNftMinted(event: RawTransactionEvent): EventCollectionNftMinted {
        return EventCollectionNftMinted(
            contractId = event.contractId(),
            tokenIds = event.multiTokenIds(),
            toAddress = event.toAddress(),
            minterAddress = event.fromAddress()
        )
    }

    fun collectionFtBurnedFromMessage(rawMessageValueMap: RawMessageValueMap): TransactionEvent {
        val amountMap = rawMessageValueMap.findList(Amount)

        return EventCollectionFtBurned(
            contractId = rawMessageValueMap.find(ContractId),
            tokenId = amountMap.first().find(TokenId),
            fromAddress = rawMessageValueMap.find(From),
            amount = amountMap.first().find(Amount),
            proxyAddress = rawMessageValueMap.find(Proxy),
        )
    }
}
