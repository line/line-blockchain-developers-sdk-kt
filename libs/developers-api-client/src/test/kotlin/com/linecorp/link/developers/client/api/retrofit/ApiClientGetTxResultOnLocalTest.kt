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
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiClientGetTxResultOnLocalTest {
    private lateinit var mockWebServer: MockWebServer
    private val mockServerPort = 3000
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "http://localhost:$mockServerPort"
    private val apiKeySecret = ApiKeySecret("test", "test")

    @BeforeAll
    fun setUpAll() {
        mockWebServer = MockWebServer()
        mockWebServer.start(mockServerPort)
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @BeforeEach
    fun setUp() {
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @Test
    fun test_get_lbd_service_details() {
        val inputStream = this::class.java.classLoader.getResourceAsStream("txresults/burn-ft-txresult-response.json")
        val body = inputStream.bufferedReader().use { it.readText() }
        mockWebServer.enqueue(okResponse(body))

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )
        val genericResponse = runBlocking {
            apiClient.transactionV2(
                txHash = "BC66DB40C56E0EC9921B70146AA2BA377236DA81826320A9A50EA54A846F16AE"
            )
        }

        assertNotNull((genericResponse))
        assertEquals(1000, genericResponse.statusCode)
        assertEquals("Success", genericResponse.statusMessage)
        assertNotNull(genericResponse.responseData)
        assertTrue(genericResponse.responseData?.events?.any { it.eventName == "EventCollectionFtBurned" } ?: false)
    }

    private fun okResponse(body: String): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(body)

    }
}
