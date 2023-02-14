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

package com.linecorp.link.developers.client.api.retrofit

import com.linecorp.link.developers.client.api.ApiKeySecret
import com.linecorp.link.developers.client.request.UserBaseCoinTransferRequest
import java.time.Clock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiClientBaseCoinTest {
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "https://test-api.blockchain.line.me/"
    private val apiKeySecret =
        ApiKeySecret(System.getenv("API_KEY"), System.getenv("API_SECRET"))

    @BeforeEach
    fun setUp() {
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @Test
    fun test_get_base_coin_balance() {

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )
        val genericResponse = runBlocking {
            apiClient.baseCoinBalanceOfUser("U9fc03e78e1ae958b1bd3633cfb48acb9")
        }

        assertNotNull((genericResponse))
        assertEquals(1000, genericResponse.statusCode)
        assertEquals("Success", genericResponse.statusMessage)
        assertNotNull(genericResponse.responseData)
        assertEquals("TC", genericResponse.responseData?.symbol)
    }

    @Test
    fun test_issue_base_coin_transfer() {

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl,
            false,
            apiKeySecret
        )
        val genericResponse = runBlocking {
            apiClient.issueSessionTokenForBaseCoinTransfer(
                userId = "U9fc03e78e1ae958b1bd3633cfb48acb9",
                requestType = "URI",
                request = UserBaseCoinTransferRequest(
                    toAddress = null,
                    toUserId = "U9fc03e78e1ae958b1bd3633cfb48acb9",
                    amount = "1",
                    landingUri = null
                )
            )
        }

        assertNotNull((genericResponse))
        // fail because receiver is same and sender has no balance
        assertEquals(4000, genericResponse.statusCode)
        assertEquals("Bad request", genericResponse.statusMessage)
    }
}
