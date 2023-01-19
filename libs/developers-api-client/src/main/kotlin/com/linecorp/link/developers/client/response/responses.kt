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

package com.linecorp.link.developers.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.linecorp.link.developers.client.exception.InvalidResponseValueException
import java.io.IOException
import java.math.BigInteger
import java.util.Date

sealed class ApiResponse<out T:Any> {
    /**
     * Success response with body
     * */
    data class Success<T:Any>(val body:T): ApiResponse<T>()
    /**
     * Failure response with body
     * non-2xx response, contains error body
     */
    data class ApiError<T:Any>(val body :T, val code:Int): ApiResponse<T>()
    /**
     * Network Error, such as no internet-connection
     * */
    data class NetworkError(val error:IOException): ApiResponse<Nothing>()
    /**
     * For example, json parsing error
     * */
    data class UnknownError(val error: Throwable?): ApiResponse<Nothing>()
}


data class GenericResponse<T>(
    val responseTime: Long,
    val statusCode: Int,
    val statusMessage: String,
    val responseData: T? = null
) {
    companion object {
        fun unknownResponse(throwable: Throwable): GenericResponse<Unit> {
            val exceptionName = (throwable.cause ?: throwable).javaClass.name
            val errorMessage = throwable.cause?.message ?: throwable.message ?: "Unknown Error"
            val statusMessage = "Exception: $exceptionName, error-message: $errorMessage"

            return GenericResponse(-1, -1, statusMessage)
        }
    }
}

data class ServiceDetail(
    val serviceId: String,
    val name: String,
    val description: String?,
    val category: String
)

class UserRequestStatus(val status: RequestSessionTokenStatus)
enum class RequestSessionTokenStatus {
    Authorized,
    Unauthorized;
}

data class SimpleServiceToken(
    val contractId: String,
    val ownerAddress: String,
    val createdAt: Long,
    val serviceId: String,
    val decimals: Int,
    val name: String,
    val symbol: String,
    val meta: String,
    val imgUri: String,
)

data class ServiceToken(
    val contractId: String,
    val ownerAddress: String,
    val createdAt: Long,
    val serviceId: String,
    val decimals: Int,
    val name: String,
    val symbol: String,
    val meta: String,
    val imgUri: String,
    val totalSupply: String,
    val totalMint: String,
    val totalBurn: String
)

data class ServiceTokenHolder(
    val address: String,
    val userId: String?,
    val amount: String
)

data class TransactionResponse(val txHash: String)

data class TxResultResponse(
    val height: Long,
    @JsonProperty("txhash") val txHash: String,
    val codespace: String?,
    val code: Int,
    val index: Int,
    val data: String?,
    val logs: List<LogResponse>?,
    val info: String?,
    val gasWanted: Long?,
    val gasUsed: Long,
    val tx: TypedValueResponse<StdTxResponse>,
    val timestamp: Date
)

data class LogResponse(
    val msgIndex: Int,
    val log: String,
    val events: List<EventResponse>?
)

data class EventResponse(
    val type: String,
    val attributes: List<KeyValueResponse<String>>
)

data class KeyValueResponse<T>(val key: String, val value: T?)
data class TypedValueResponse<T : Any?>(val type: String, val value: T)

data class StdTxResponse(
    val msg: List<TypedValueResponse<*>>,
    val fee: FeeResponse,
    val memo: String,
    val signatures: List<SignatureResponse>
)

data class FeeResponse(val gas: BigInteger, val amount: List<CoinResponse>)
data class SignatureResponse(
    val pubKey: TypedValueResponse<ByteArray>?,
    val signature: ByteArray
)

data class BaseCoinBalance(
    val symbol: String,
    val decimals: Long,
    val amount: String
)

data class CoinResponse(val denom: String, val amount: BigInteger) {
    override fun toString(): String {
        return amount.toString() + denom
    }
}

data class Memo(val memo: String)

data class WalletResponse(
    val name: String,
    val walletAddress: String,
    val createdAt: Long
)

data class ServiceTokenBalance(
    val contractId: String,
    val name: String,
    val symbol: String,
    val imgUri: String,
    val decimals: Long,
    val amount: String
)

data class FungibleBalance(
    val tokenType: String,
    val name: String,
    val meta: String,
    val amount: String
)

data class NonFungibleBalance(
    val tokenType: String,
    val name: String,
    val meta: String,
    val numberOfIndex: String
)

data class TokenIndex(val tokenIndex: String, val name: String, val meta: String)

data class ItemToken(
    val contractId: String,
    val baseImgUri: String,
    val ownerAddress: String,
    val serviceId: String,
    val createdAt: Long
)

data class FungibleToken(
    val name: String,
    val meta: String,
    val tokenType: String,
    val totalSupply: String,
    val totalMint: String,
    val totalBurn: String,
    val createdAt: Long
)

data class FungibleTokenHolder(
    val userId: String?,
    val walletAddress: String,
    val amount: String
)

data class ItemTokenType(
    val name: String,
    val meta: String,
    val tokenType: String,
    val totalSupply: String,
    val totalMint: String,
    val totalBurn: String,
    val createdAt: Long
)

data class NonFungibleTokenType(
    val name: String,
    val meta: String,
    val tokenType: String,
    val totalSupply: BigInteger,
    val totalMint: BigInteger,
    val totalBurn: BigInteger,
    val createdAt: Long,
    val tokens: List<NonFungibleIndex>
)

data class NonFungibleIndex(
    val tokenIndex: String,
    val name: String,
    val meta: String,
    val createdAt: Long,
    val burnedAt: Long?
)

data class NonFungibleId(
    val tokenId: String,
    val name: String,
    val meta: String,
    val createdAt: Long,
    val burnedAt: Long?
)

data class NonFungibleTokenTypeHolder(
    val userId: String?,
    val walletAddress: String,
    val numberOfIndex: String
)

data class NonFungibleTokenHolder(
    val tokenId: String,
    val userId: String?,
    val walletAddress: String,
    val amount: String
)

data class UserIdAddress(val userId: String, val address: String)
data class RequestSession(val requestSessionToken: String, val redirectUri: String)

@JsonDeserialize(using = RequestSessionStatusDeserializer::class)
enum class RequestSessionStatus {
    AUTHORIZED, UNAUTHORIZED
}

class RequestSessionStatusDeserializer(): StdDeserializer<RequestSessionStatus>(RequestSessionStatus::class.java) {
    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext): RequestSessionStatus {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)

        val status = node["status"].asText()

        return RequestSessionStatus.values().firstOrNull() {
            it.name == status.uppercase()
        } ?: throw InvalidResponseValueException("invalid request-session-status")
    }

    companion object {
        private const val serialVersionUID: Long = 8877453474216287548L
    }
}

data class ProxyStatus(val isApproved: Boolean)
