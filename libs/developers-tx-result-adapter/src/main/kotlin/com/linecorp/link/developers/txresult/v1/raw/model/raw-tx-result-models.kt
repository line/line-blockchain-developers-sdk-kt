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
@file:Suppress("TooManyFunctions")
package com.linecorp.link.developers.txresult.v1.raw.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.linecorp.link.developers.chain.account.Address
import com.linecorp.link.developers.chain.account.PubKey
import com.linecorp.link.developers.txresult.core.event.token.TokenAttribute
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Amount
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Approver
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.ContractId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Decimals
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.ExParentTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.From
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Name
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.NewRootTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Owner
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.ParentTokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Proxy
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Recipient
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Sender
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.Symbol
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.To
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenId
import com.linecorp.link.developers.txresult.v1.raw.model.EventAttributeType.TokenType
import org.apache.commons.lang3.StringUtils

data class RawTransactionResult(
    val height: Long,
    val index: Int,
    val code: Int = 0,
    @JsonProperty("codespace")
    @JsonAlias("codeSpace")
    val codespace: String? = "",
    @JsonProperty("txhash")
    @JsonAlias("txHash")
    val txhash: String,
    val timestamp: Long,
    val data: String? = "",
    val info: String? = "",
    @JsonProperty("gasWanted")
    @JsonAlias("gas_wanted")
    val gasWanted: Long,
    @JsonProperty("gasUsed")
    @JsonAlias("gas_used")
    val gasUsed: Long,
    val logs: Set<RawTransactionLog>?,
    val tx: RawTransactionRequest,
) {
    fun getSignerAddresses(hrpPrefix: String): Set<String> {
        return this.tx.value.signatures.map {
            val pubKeyBin = java.util.Base64.getDecoder().decode(it.pubKey.value)
            getAddress(hrpPrefix, PubKey(pubKeyBin))
        }.toSet()
    }

    private fun getAddress(hrpPrefix: String, pubKey: PubKey): String {
        return Address.invoke(pubKey).toBech32(hrpPrefix)
    }
}

data class RawTransactionLog(
    @JsonProperty("msgIndex")
    @JsonAlias("msg_index")
    val msgIndex: Int,
    val log: String = "",
    val events: Set<RawTransactionEvent>,
)


fun RawTransactionLog.findEvent(rawEventType: RawMessageEventKeyType): RawTransactionEvent? {
    return this.events.find { it.type == rawEventType.eventName || rawEventType.candidateEventName.contains(it.type) }
}

data class RawTransactionEvent(
    val type: String,
    val attributes: Set<RawTransactionEventAttribute>,
)

fun RawTransactionEvent?.findAttributeOrNull(attributeType: EventAttributeType): String? {
    return this?.attributes?.singleOrNull { attributeType.values.contains(it.key) }?.value
}

fun RawTransactionEvent?.findAttribute(
    attributeType: EventAttributeType,
    defaultValue: String = StringUtils.EMPTY,
): String {
    return this?.findAttributeNotNull(attributeType, defaultValue) ?: defaultValue
}

fun RawTransactionEvent?.findAttributes(
    attributeType: EventAttributeType,
): Collection<String> {
    return this?.attributes?.filter { attributeType.values.contains(it.key) }?.map {
        it.value
    }?.toList() ?: emptyList()
}

fun RawTransactionEvent?.ownerAddress(): String = this.findAttribute(Owner)

fun RawTransactionEvent?.name(): String {
    return this.findAttribute(Name)
}

fun RawTransactionEvent?.amount(): String {
    return this.findAttribute(Amount)
}

fun RawTransactionEvent?.decimals(): String {
    return this.findAttribute(Decimals)
}

fun RawTransactionEvent?.contractId(): String {
    return this.findAttribute(ContractId)
}

fun RawTransactionEvent?.symbol(): String {
    return this.findAttribute(Symbol)
}

fun RawTransactionEvent?.tokenType(): String {
    return this.findAttribute(TokenType)
}

fun RawTransactionEvent?.tokenId(): String {
    return this.findAttribute(TokenId)
}

fun RawTransactionEvent?.multiTokenIds(): Collection<String> {
    return this.findAttributes(TokenId)
}

fun RawTransactionEvent?.parentTokenId(): String {
    return this.findAttribute(ParentTokenId)
}

fun RawTransactionEvent?.exParentTokenId(): String {
    return this.findAttribute(ExParentTokenId)
}

fun RawTransactionEvent?.newRootTokenId(): String {
    return this.findAttribute(NewRootTokenId)
}

fun RawTransactionEvent?.senderAddress(): String {
    return this.findAttribute(Sender)
}

fun RawTransactionEvent?.fromAddress(): String {
    return this.findAttribute(From)
}

fun RawTransactionEvent?.recipientAddress(): String = this.findAttribute(Recipient)

fun RawTransactionEvent?.toAddress(): String {
    return this.findAttribute(To)
}

fun RawTransactionEvent?.proxyAddress(): String {
    return this.findAttribute(Proxy)
}

fun RawTransactionEvent?.approverAddress(): String {
    return this.findAttribute(Approver)
}

fun String.parseAmount(): Pair<String, String> {
    val regex = "(\\d+)(\\w+)".toRegex()
    val groupValues = regex.matchEntire(this)?.groupValues ?: listOf("", "")
    return Pair(groupValues[1], groupValues[2])
}

fun RawTransactionEvent.findAttributeNotNull(
    attributeType: EventAttributeType,
    defaultValue: String = StringUtils.EMPTY,
): String {
    return this.attributes.firstOrNull { attributeType.values.contains(it.key) }?.value ?: defaultValue
}

fun RawTransactionEvent?.attributesExclude(vararg eventTypesToExclude: EventAttributeType): Set<RawTransactionEventAttribute> {
    return this?.attributes?.filter { rawTransactionEventAttribute ->
        eventTypesToExclude.none { singleEventTypeToExclude ->
            singleEventTypeToExclude.values.contains(rawTransactionEventAttribute.key)
        }
    }?.toSet() ?: emptySet()
}

fun RawTransactionEventAttribute.toTokenAttribute(): TokenAttribute {
    return TokenAttribute(this.key, this.value)
}

data class RawTransactionEventAttribute(
    val key: String, val value: String,
)

data class RawTransactionRequest(
    val type: String,
    val value: RawTransactionRequestValue,
)

data class RawTransactionRequestValue(
    val msg: List<RawTransactionRequestMessage>,
    val fee: RawTransactionRequestFee,
    val memo: String = "",
    val signatures: Set<RawTxSignature>,
)

data class RawTxSignature(
    @JsonProperty("pubKey")
    @JsonAlias("pub_key")
    val pubKey: RawTransactionRequestPubKey,
    val signature: String,
)

data class RawTransactionRequestPubKey(
    val type: String,
    val value: String,
)

data class RawTransactionRequestMessage(
    @JsonProperty("msgIndex")
    @JsonAlias("msg_index")
    val msgIndex: Int,
    val type: String,
    val value: Any,
)

data class RawTransactionRequestFee(
    val gas: Long = 0,
    val amount: Set<RawTransactionRequestAmount> = emptySet(),
)

data class RawTransactionRequestAmount(
    val denomination: String? = null,
    val amount: String? = null,
)

@Suppress("UNCHECKED_CAST")
data class RawMessageValueMap(
    private val value: Map<String, Any>,
) {
    constructor(message: RawTransactionRequestMessage) : this(message.value as Map<String, Any>)

    fun find(eventAttributeType: EventAttributeType, defaultString: String = StringUtils.EMPTY): String {
        return eventAttributeType.values.mapNotNull { value[it] }.singleOrNull()?.toString()
            ?: defaultString
    }

    fun findList(
        eventAttributeType: EventAttributeType,
        defaultList: List<RawMessageValueMap> = emptyList()
    ): List<RawMessageValueMap> {
        return eventAttributeType.values.mapNotNull { value[it] }.singleOrNull()
            ?.let { it as Collection<Map<String, String>> }
            ?.map { RawMessageValueMap(it) } ?: defaultList
    }
}

fun RawTransactionRequestMessage.toValueMap(): RawMessageValueMap {
    return RawMessageValueMap(this)
}
