/*
 *   Copyright 2023 LINE Corporation
 *
 *   LINE Corporation licenses this file to you under the Apache License,
 *   version 2.0 (the "License"); you may not use this file except in compliance
 *   with the License. You may obtain a copy of the License at:
 *
 *     https:www.apache.orglicensesLICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 *
 */

@file:Suppress("DuplicatedCode")

package com.linecorp.link.developers.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.linecorp.link.developers.txresult.core.event.UnknownTransactionEvent
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
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionPermissionGranted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionPermissionRenounced
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyApproved
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyDisapproved
import com.linecorp.link.developers.txresult.core.event.item.ItemTokenPermission
import com.linecorp.link.developers.txresult.core.event.token.EventTokenBurned
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenMinted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenPermissionGranted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenPermissionRenounced
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyApproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyDisapproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenTransferred
import com.linecorp.link.developers.txresult.core.event.token.TokenAttribute
import com.linecorp.link.developers.txresult.core.event.token.TokenPermission
import com.linecorp.link.developers.txresult.core.model.TransactionEvent

class TransactionEventDeserializer : JsonDeserializer<TransactionEvent>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): TransactionEvent {
        val mapTypeReference = object : TypeReference<Map<Any, Any?>>() {}
        val value: Map<Any, Any?> = p.readValueAs(mapTypeReference)
        return when {
            value["eventName"] == "EventCoinTransferred" -> {
                EventCoinTransferred(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    denomination = value["denomination"].toString(),
                    fromAddress = value["fromAddress"].toString(),
                    toAddress = value["toAddress"].toString(),
                    amount = value["amount"].toString(),
                )
            }
            value["eventName"] == "EventAccountCreated" -> {
                EventAccountCreated(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    createdAddress = value["createdAddress"].toString(),
                )
            }
            value["eventName"] == "EventEmptyMsgCreated" -> {
                EventEmptyMsgCreated(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    senderAddress = value["senderAddress"].toString(),
                )
            }
            value["eventName"] == "EventTokenBurned" -> {
                EventTokenBurned(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    fromAddress = value["fromAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                    amount = value["amount"].toString(),
                )
            }
            value["eventName"] == "EventTokenIssued" -> {
                EventTokenIssued(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    issuerAddress = value["issuerAddress"].toString(),
                    receiverAddress = value["receiverAddress"].toString(),
                    name = value["name"].toString(),
                    symbol = value["symbol"].toString(),
                    decimals = value["decimals"].toString().toInt(),
                    amount = value["amount"].toString(),
                )
            }
            value["eventName"] == "EventTokenMinted" -> {
                EventTokenMinted(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    minterAddress = value["minterAddress"].toString(),
                    toAddress = value["toAddress"].toString(),
                    amount = value["amount"].toString(),
                )
            }
            value["eventName"] == "EventTokenTransferred" -> {
                EventTokenTransferred(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    fromAddress = value["minterAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                    toAddress = value["toAddress"].toString(),
                    amount = value["amount"].toString(),
                )
            }
            value["eventName"] == "EventTokenModified" -> {
                EventTokenModified(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    modifierAddress = value["modifierAddress"].toString(),
                    tokenAttributes = deserializeTokenAttributes(value)
                )
            }
            value["eventName"] == "EventTokenPermissionGranted" -> {
                EventTokenPermissionGranted(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    granteeAddress = value["granteeAddress"].toString(),
                    granterAddress = value["granterAddress"].toString(),
                    permission = TokenPermission.valueOf(value["permission"].toString())
                )
            }
            value["eventName"] == "EventTokenPermissionRenounced" -> {
                EventTokenPermissionRenounced(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    granteeAddress = value["granteeAddress"].toString(),
                    permission = TokenPermission.valueOf(value["permission"].toString())
                )
            }
            value["eventName"] == "EventTokenProxyApproved" -> {
                EventTokenProxyApproved(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    approverAddress = value["approverAddress"].toString(),
                    proxyAddress = value["proxyAddress"].toString(),
                )
            }
            value["eventName"] == "EventTokenProxyDisapproved" -> {
                EventTokenProxyDisapproved(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    approverAddress = value["approverAddress"].toString(),
                    proxyAddress = value["proxyAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionCreated" -> {
                EventCollectionCreated(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    name = value["name"].toString(),
                    creatorAddress = value["creatorAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionModified" -> {
                EventCollectionModified(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenAttributes = deserializeCollectionAttributes(value),
                    modifierAddress = value["modifierAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionFtIssued" -> {
                EventCollectionFtIssued(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    name = value["name"].toString(),
                    amount = value["amount"].toString(),
                    decimals = value["decimals"].toString().toInt(),
                    issuerAddress = value["issuerAddress"].toString(),
                    receiverAddress = value["receiverAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionFtMinted" -> {
                EventCollectionFtMinted(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    tokenId = value["tokenId"].toString(),
                    amount = value["amount"].toString(),
                    toAddress = value["toAddress"].toString(),
                    minterAddress = value["minterAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionFtModified" -> {
                EventCollectionFtModified(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    tokenAttributes = deserializeCollectionAttributes(value),
                    modifierAddress = value["modifierAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionFtTransferred" -> {
                EventCollectionFtTransferred(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    tokenId = value["tokenId"].toString(),
                    amount = value["amount"].toString(),
                    toAddress = value["toAddress"].toString(),
                    fromAddress = value["fromAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionFtBurned" -> {
                EventCollectionFtBurned(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    tokenId = value["tokenId"].toString(),
                    amount = value["amount"].toString(),
                    fromAddress = value["fromAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionNftIssued" -> {
                EventCollectionNftIssued(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    issuerAddress = value["issuerAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftMinted" -> {
                @Suppress("UNCHECKED_CAST")
                EventCollectionNftMinted(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenIds = (value["tokenIds"] as List<String>),
                    toAddress = value["toAddress"].toString(),
                    minterAddress = value["minterAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftModified" -> {
                EventCollectionNftModified(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenId = value["tokenId"].toString(),
                    tokenAttributes = deserializeCollectionAttributes(value),
                    modifierAddress = value["modifierAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftTypeModified" -> {
                EventCollectionNftTypeModified(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenType = value["tokenType"].toString(),
                    tokenAttributes = deserializeCollectionAttributes(value),
                    modifierAddress = value["modifierAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftAttached" -> {
                EventCollectionNftAttached(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    childTokenId = value["childTokenId"].toString(),
                    parentTokenId = value["parentTokenId"].toString(),
                    holderAddress = value["holderAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionNftDetached" -> {
                EventCollectionNftDetached(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    exChildTokenId = value["exChildTokenId"].toString(),
                    exParentTokenId = value["exParentTokenId"].toString(),
                    holderAddress = value["holderAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionNftRootChanged" -> {
                @Suppress("UNCHECKED_CAST")
                EventCollectionNftRootChanged(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenIds = (value["tokenIds"] as List<String>),
                    oldRootTokenId = value["oldRootTokenId"].toString(),
                    newRootTokenId = value["newRootTokenId"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftHolderChanged" -> {
                @Suppress("UNCHECKED_CAST")
                EventCollectionNftHolderChanged(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenIds = (value["tokenIds"] as List<String>),
                    fromAddress = value["fromAddress"].toString(),
                    toAddress = value["toAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionNftTransferred" -> {
                @Suppress("UNCHECKED_CAST")
                EventCollectionNftTransferred(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenIds = (value["tokenIds"] as List<String>),
                    fromAddress = value["fromAddress"].toString(),
                    toAddress = value["toAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionNftBurned" -> {
                @Suppress("UNCHECKED_CAST")
                EventCollectionNftBurned(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    tokenIds = (value["tokenIds"] as List<String>),
                    fromAddress = value["fromAddress"].toString(),
                    proxyAddress = value["proxyAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionPermissionGranted" -> {
                EventCollectionPermissionGranted(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    permission =  ItemTokenPermission.valueOf(value["permission"].toString()),
                    granteeAddress = value["granteeAddress"].toString(),
                    granterAddress = value["granterAddress"]?.toString(),
                )
            }
            value["eventName"] == "EventCollectionPermissionRenounced" -> {
                EventCollectionPermissionRenounced(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    permission = ItemTokenPermission.valueOf(value["permission"].toString()),
                    granteeAddress = value["granteeAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionProxyApproved" -> {
                EventCollectionProxyApproved(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    approverAddress = value["approverAddress"].toString(),
                    proxyAddress = value["proxyAddress"].toString(),
                )
            }
            value["eventName"] == "EventCollectionProxyDisapproved" -> {
                EventCollectionProxyDisapproved(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    contractId = value["contractId"].toString(),
                    approverAddress = value["approverAddress"].toString(),
                    proxyAddress = value["proxyAddress"].toString(),
                )
            }
            else -> {
                UnknownTransactionEvent(
                    msgIndex = value["msgIndex"].toString().toInt(),
                    type = value["type"]?.toString() ?: "",
                    attributes = emptyList(),
                    extraMessage = "tx result from response: $value"
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun deserializeTokenAttributes(value: Map<Any, Any?>) =
        (value["tokenAttributes"] as List<Map<String, String>>).map {
            TokenAttribute(
                key = it["key"] ?: "",
                value = it["value"] ?: ""
            )
        }.toSet()

    @Suppress("UNCHECKED_CAST")
    private fun deserializeCollectionAttributes(value: Map<Any, Any?>) =
        (value["tokenAttributes"] as List<Map<String, String>>).map {
            CollectionAttribute(
                key = it["key"] ?: "",
                value = it["value"] ?: ""
            )
        }.toSet()
}
