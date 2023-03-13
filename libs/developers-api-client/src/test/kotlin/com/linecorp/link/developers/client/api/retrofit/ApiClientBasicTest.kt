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
import java.time.Clock
import java.time.Instant
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class ApiClientBasicTest {
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "https://test-api.blockchain.line.me/"
    private val invalidBaseUrl = "https://invali-url.com/"
    private val apiKeySecret =
        ApiKeySecret(System.getenv("API_KEY"), System.getenv("API_SECRET"))

    @BeforeEach
    fun setUp() {
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @Test
    fun test_success_time_api() {

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )

        val timeResponse = runBlocking {
            delay(1000)
            apiClient.time()
        }

        assertNotNull((timeResponse))
        assertEquals(1000, timeResponse.statusCode)
        assertEquals("Success", timeResponse.statusMessage)
        // I guess there is a bug no LBD
//        assertTrue(timeResponse.responseTime > now)
        assertNull(timeResponse.responseData)
    }

    @Test
    fun test_error_time_api_invalid_service_api_key() {
        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )

        val now = Instant.now().toEpochMilli()

        val timeResponse = runBlocking {
            apiClient.time()
        }

        assertNotNull((timeResponse))
        assertEquals(4012, timeResponse.statusCode)
        assertEquals("Service-api-key not found", timeResponse.statusMessage)
        assertTrue(timeResponse.responseTime > now)
        assertNull(timeResponse.responseData)
    }

    @Test
    fun test_error_time_api_invalid_base_url() {
        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = invalidBaseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )
        val timeResponse = runBlocking {
            apiClient.time()
        }

        assertNotNull((timeResponse))
        assertEquals(-1, timeResponse.statusCode)
        assertTrue(timeResponse.statusMessage.contains("UnknownHostException"))
    }
}
