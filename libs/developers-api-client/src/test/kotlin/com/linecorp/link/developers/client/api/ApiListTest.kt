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

package com.linecorp.link.developers.client.api

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

class ApiListTest {
    @Test
    fun listGETApis() {
        val getAPIs = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(GET::class.java)?.let {
                "GET ${method.getAnnotation(GET::class.java).value}"
            }

        }.sorted()

        val expectedGETAPIs = listOfAPIs.filter { it.startsWith("GET") }.sorted()
        assertEquals(expectedGETAPIs, getAPIs)
    }

    @Test
    fun listPOSTApis() {
        // post
        val postAPIs = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(POST::class.java)?.let {
                "POST ${method.getAnnotation(POST::class.java).value}"
            }

        }

        val postAPIs2 = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(HTTP::class.java)?.let {
                if (it.method == "POST") {
                    "POST ${method.getAnnotation(HTTP::class.java).path}"
                } else {
                    null
                }
            }
        }

        val actualAPIs = ( postAPIs +postAPIs2 ).sorted()

        val expectedPOSTAPIs = listOfAPIs.filter { it.startsWith("POST") }.sorted()
        assertEquals(expectedPOSTAPIs, actualAPIs)
    }

    @Test
    fun listPUTApis() {
        // put
        val putAPIs = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(PUT::class.java)?.let {
                "PUT ${method.getAnnotation(PUT::class.java).value}"
            }

        }

        val putAPIs2 = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(HTTP::class.java)?.let {
                if (it.method == "PUT") {
                    "PUT ${method.getAnnotation(HTTP::class.java).path}"
                } else {
                    null
                }
            }
        }

        val actualAPIs = ( putAPIs +putAPIs2 ).sorted()

        val expectedPUTAPIs = listOfAPIs.filter { it.startsWith("PUT") }.sorted()
        assertEquals(expectedPUTAPIs, actualAPIs)
    }

    @Test
    fun listDELETEApis() {
        // delete
        val deleteAPIs = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(DELETE::class.java)?.let {
                "DELETE ${method.getAnnotation(DELETE::class.java).value}"
            }

        }
        val deleteAPIs2 = ApiClient::class.java.declaredMethods.mapNotNull { method ->
            method.getAnnotation(HTTP::class.java)?.let {
                if (it.method == "DELETE") {
                    "DELETE ${method.getAnnotation(HTTP::class.java).path}"
                } else {
                    null
                }
            }
        }

        val actualAPIs = ( deleteAPIs +deleteAPIs2 ).sorted()

        val expectedDELETEAPIs = listOfAPIs.filter { it.startsWith("DELETE") }.sorted()
        assertEquals(expectedDELETEAPIs, actualAPIs)
    }

    companion object {
        val listOfAPIs = listOf(
            "GET /v1/time",
            "GET /v1/services/{serviceId}",
            "GET /v1/service-tokens/by-txHash/{txHash}",
            "GET /v1/service-tokens",
            "GET /v1/service-tokens/{contractId}",
            "GET /v1/service-tokens/{contractId}/holders",
            "PUT /v1/service-tokens/{contractId}",
            "POST /v1/service-tokens/{contractId}/burn-from",
            "POST /v1/service-tokens",
            "POST /v1/service-tokens/{contractId}/mint",
            "GET /v1/item-tokens",
            "GET /v1/item-tokens/{contractId}/fungibles",
            "GET /v1/item-tokens/{contractId}/non-fungibles",
            "GET /v1/item-tokens/{contractId}/fungibles/{tokenType}",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}",
            "GET /v1/item-tokens/{contractId}/fungibles/{tokenType}/holders",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/holders",
            "GET /v1/item-tokens/{contractId}",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/children",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/parent",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/root",
            "GET /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/holder",
            "GET /v1/item-tokens/{contractId}/fungibles/media-resources/{requestId}/status",
            "GET /v1/item-tokens/{contractId}/fungibles/thumbnails/{requestId}/status",
            "GET /v1/item-tokens/{contractId}/non-fungibles/media-resources/{requestId}/status",
            "GET /v1/item-tokens/{contractId}/non-fungibles/thumbnails/{requestId}/status",
            "POST /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/parent",
            "DELETE /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/parent",
            "PUT /v1/item-tokens/{contractId}/fungibles/{tokenType}",
            "PUT /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}",
            "PUT /v1/item-tokens/{contractId}/fungibles/media-resources",
            "PUT /v1/item-tokens/{contractId}/fungibles/thumbnails",
            "PUT /v1/item-tokens/{contractId}/non-fungibles/media-resources",
            "PUT /v1/item-tokens/{contractId}/non-fungibles/thumbnails",
            "PUT /v1/item-tokens/{contractId}/non-fungibles/{tokenType}",
            "POST /v1/item-tokens/{contractId}/fungibles/{tokenType}/burn",
            "POST /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/burn",
            "POST /v1/item-tokens",
            "POST /v1/item-tokens/{contractId}/fungibles",
            "POST /v1/item-tokens/{contractId}/non-fungibles",
            "POST /v1/item-tokens/{contractId}/fungibles/{tokenType}/mint",
            "POST /v1/item-tokens/{contractId}/non-fungibles/{tokenType}/mint",
            "POST /v1/item-tokens/{contractId}/non-fungibles/multi-mint",
            "POST /v1/item-tokens/{contractId}/non-fungibles/multi-recipients/multi-mint",
            "GET /v1/wallets/{walletAddress}/item-tokens/{contractId}/fungibles",
            "GET /v1/wallets/{walletAddress}/item-tokens/{contractId}/non-fungibles",
            "GET /v1/wallets/{walletAddress}/service-tokens",
            "GET /v1/wallets/{walletAddress}/base-coin",
            "GET /v1/wallets/{walletAddress}/item-tokens/{contractId}/fungibles/{tokenType}",
            "GET /v1/wallets/{walletAddress}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}",
            "GET /v1/wallets/{walletAddress}/service-tokens/{contractId}",
            "GET /v1/wallets/{walletAddress}/item-tokens/{contractId}/non-fungibles/{tokenType}",
            "GET /v1/wallets/{walletAddress}",
            "GET /v1/wallets",
            "GET /v2/wallets/{walletAddress}/transactions",
            "GET /v1/wallets/{walletAddress}/transactions",
            "POST /v1/wallets/{walletAddress}/item-tokens/{contractId}/non-fungibles/batch-transfer",
            "POST /v1/wallets/{walletAddress}/item-tokens/{contractId}/fungibles/{tokenType}/transfer",
            "POST /v1/wallets/{walletAddress}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/transfer",
            "POST /v1/wallets/{walletAddress}/service-tokens/{contractId}/transfer",
            "POST /v1/wallets/{walletAddress}/base-coin/transfer",
            "GET /v1/users/{userId}/item-tokens/{contractId}/fungibles",
            "GET /v1/users/{userId}/item-tokens/{contractId}/non-fungibles/with-type",
            "GET /v1/users/{userId}/item-tokens/{contractId}/non-fungibles",
            "GET /v1/users/{userId}/service-tokens",
            "GET /v1/users/{userId}/base-coin",
            "GET /v1/users/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}",
            "GET /v1/users/{userId}/item-tokens/{contractId}/fungibles/{tokenType}",
            "GET /v1/users/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}",
            "GET /v1/users/{userId}/service-tokens/{contractId}",
            "GET /v1/users/{userId}/item-tokens/{contractId}/proxy",
            "GET /v1/users/{userId}/service-tokens/{contractId}/proxy",
            "GET /v1/user-requests/{requestSessionToken}",
            "GET /v1/users/{userId}",
            "GET /v2/users/{userId}/transactions",
            "GET /v1/users/{userId}/transactions",
            "POST /v1/user-requests/{requestSessionToken}/commit",
            "POST /v1/users/{userId}/base-coin/request-transfer",
            "POST /v1/users/{userId}/item-tokens/{contractId}/request-proxy",
            "POST /v1/users/{userId}/service-tokens/{contractId}/request-proxy",
            "POST /v1/users/{userId}/service-tokens/{contractId}/request-transfer",
            "POST /v1/users/{userId}/item-tokens/{contractId}/non-fungibles/batch-transfer",
            "POST /v1/users/{userId}/item-tokens/{contractId}/fungibles/{tokenType}/transfer",
            "POST /v1/users/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/transfer",
            "POST /v1/users/{userId}/service-tokens/{contractId}/transfer",
            "GET /v2/transactions/{txHash}",
            "GET /v1/transactions/{txHash}",
            "GET /v1/memos/{txHash}",
            "POST /v1/memos"
        ).sorted()
    }
}
