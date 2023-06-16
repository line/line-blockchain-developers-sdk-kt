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
import com.linecorp.link.developers.client.response.NonFungibleTokenHolder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiClientNftHolderWithMetaTest {
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
    fun `testQueryNftHoldersWithMeta()`() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(
                """
                {
                    "responseTime": 1585467707858,
                    "statusCode": 1000,
                    "statusMessage": "Success",
                    "responseData": {
                        "tokenId": "1000000100000001",
                        "walletAddress": "tlink1nf5uhdmtsshmkqvlmq45kn4q9atnkx4l3u4rww",
                        "userId": null,
                        "amount": "1",
                        "meta": "test meta"
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
            apiClient.nonFungibleTokenHolder(
                contractId = "test1234",
                tokenType = "10000001",
                tokenIndex = "00000001"
            )
        }

        assertTrue(response.responseData is NonFungibleTokenHolder)
        assertEquals("1000000100000001", response.responseData!!.tokenId)
        assertEquals("test meta", response.responseData!!.meta)
    }

    @Test
    fun `given no meta in response, testQueryNftHoldersWithMeta()`() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(
                """
                {
                    "responseTime": 1585467707858,
                    "statusCode": 1000,
                    "statusMessage": "Success",
                    "responseData": {
                        "tokenId": "1000000100000001",
                        "walletAddress": "tlink1nf5uhdmtsshmkqvlmq45kn4q9atnkx4l3u4rww",
                        "userId": null,
                        "amount": "1"
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
            apiClient.nonFungibleTokenHolder(
                contractId = "test1234",
                tokenType = "10000001",
                tokenIndex = "00000001"
            )
        }

        assertTrue(response.responseData is NonFungibleTokenHolder)
        assertEquals("1000000100000001", response.responseData!!.tokenId)
        assertNull(response.responseData!!.meta)
    }
}
