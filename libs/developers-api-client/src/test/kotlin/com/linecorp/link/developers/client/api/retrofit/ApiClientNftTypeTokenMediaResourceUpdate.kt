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
import com.linecorp.link.developers.client.api.TestSocketUtils
import com.linecorp.link.developers.client.request.TokenType
import com.linecorp.link.developers.client.request.UpdateNonFungibleTypeTokenResourceRequest
import com.linecorp.link.developers.client.response.UpdateTokenMediaRefreshResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiClientNftTypeTokenMediaResourceUpdate {
    private val port = TestSocketUtils.findAvailableTcpPort()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "http://localhost:$port"
    private val apiKeySecret = ApiKeySecret(key = "test", secret = "test")

    @BeforeAll
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port)
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @Suppress("MaxLineLength", "LongMethod")
    @Test
    fun `testUpdateNonFungibleTypeItemTokensMediaResource()`() {
        val mockResponse = MockResponse()
            .setResponseCode(202)
            .setHeader("Content-Type", "application/json")
            .setBody(
                """
                {
                    "responseTime": 1585467713049,
                    "statusCode": 1002,
                    "statusMessage": "Accepted",
                    "responseData": {
                        "requestId": "test1234"
                    }
                } 
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )

        val response = runBlocking {
            apiClient.updateNonFungibleItemTokenTypesMediaResource(
                contractId = "test1234",
                request = UpdateNonFungibleTypeTokenResourceRequest(
                    updateList = listOf(
                        TokenType(
                            tokenType = "10000001"
                        )
                    )
                )
            )
        }

        assertTrue(response.responseData is UpdateTokenMediaRefreshResponse)
        assertNotNull(response.responseData!!.requestId)
    }

    @Suppress("MaxLineLength", "LongMethod")
    @Test
    fun `testUpdateNonFungibleTypeItemTokensThumbnails()`() {
        val mockResponse = MockResponse()
            .setResponseCode(202)
            .setHeader("Content-Type", "application/json")
            .setBody(
                """
                {
                    "responseTime": 1585467713049,
                    "statusCode": 1002,
                    "statusMessage": "Accepted",
                    "responseData": {
                        "requestId": "test1234"
                    }
                } 
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )

        val response = runBlocking {
            apiClient.updateNonFungibleItemTokenTypesThumbnail(
                contractId = "test1234",
                request = UpdateNonFungibleTypeTokenResourceRequest(
                    updateList = listOf(
                        TokenType(
                            tokenType = "10000001"
                        )
                    )
                )
            )
        }

        assertTrue(response.responseData is UpdateTokenMediaRefreshResponse)
        assertNotNull(response.responseData!!.requestId)
    }
}
