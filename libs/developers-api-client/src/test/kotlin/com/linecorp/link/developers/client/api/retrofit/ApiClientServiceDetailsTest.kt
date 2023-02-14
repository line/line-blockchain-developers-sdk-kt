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

class ApiClientServiceDetailsTest {
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "https://test-api.blockchain.line.me/"
    private val apiKeySecret =
        ApiKeySecret(System.getenv("API_KEY"), System.getenv("API_SECRET"))

    @BeforeEach
    fun setUp() {
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @Test
    fun test_get_lbd_service_details() {

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )
        val genericResponse = runBlocking {
            apiClient.serviceDetail(
                serviceId = "5016b367-eae8-44cb-8052-6672b498d894"
            )
        }

        assertNotNull((genericResponse))
        assertEquals(1000, genericResponse.statusCode)
        assertEquals("Success", genericResponse.statusMessage)
        assertNotNull(genericResponse.responseData)
    }
}
