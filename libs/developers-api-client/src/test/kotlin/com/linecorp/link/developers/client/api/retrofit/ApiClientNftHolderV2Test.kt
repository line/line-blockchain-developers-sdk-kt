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
import com.linecorp.link.developers.client.request.OrderBy
import com.linecorp.link.developers.client.response.NonFungibleTokenTypeHolderList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiClientNftHolderV2Test {
    private val port = TestSocketUtils.findAvailableTcpPort()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofitApiClientFactory: RetrofitApiClientFactory

    private val baseUrl = "http://localhost:$port"
    private val apiKeySecret = ApiKeySecret(key = "test", secret = "test")

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port)
        retrofitApiClientFactory = RetrofitApiClientFactory()
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_v2_nft_holder_query() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody("""
            {
              "responseTime": 1683526595974,
              "statusCode": 1000,
              "statusMessage": "Success",
              "responseData": {
                    "list": [
                      {
                        "walletAddress": "tlink1z2y3xfvxq3xqrechtey05jea9krmkc3wd6dvx5",
                        "userId": null,
                        "numberOfIndex": "1022"
                      },
                      {
                        "walletAddress": "tlink1tqhku70z8sla9py8zf76zn9pcnrcfppv2jzgwk",
                        "userId": null,
                        "numberOfIndex": "743"
                        },client/response/responses.kt
                      {
                        "walletAddress": "tlink1gmw0agxluucsg2ay87ruz5sl3g9e7dtclyuh7l",
                        "userId": null,
                        "numberOfIndex": "3"
                      },
                      {
                        "walletAddress": "tlink16800lv0kjyv8pfnpe5jqmh7eshnsmdjvud5teu",
                        "userId": null,
                        "numberOfIndex": "239"
                      },
                      {
                        "walletAddress": "tlink139972p0zjqt9q54sttv6q40hyqk9qvar8pffjj",
                        "userId": null,
                        "numberOfIndex": "18"
                      }
                    ],
                    "prePageToken": "",
                    "nextPageToken": "eJxtjssOgjAQRf9l1iwE1AA7REyICRrtQlekaaeRgAVqIaDh363PuHBWc09mTu4NKOcKL5dQ8qiSWlGmE7OVJTKdVzIVepWXGhUEt88pBKDLXBZ20bie6nh77Tu39YtBMEb9"
                }
              }
            """.trimIndent())
        
        mockWebServer.enqueue(mockResponse)
        
        val apiClient = retrofitApiClientFactory.buildDefaultApiClient(
            baseUrl = baseUrl,
            enableLogging = true,
            apiKeySecret = apiKeySecret
        )

        val response = runBlocking {
            apiClient.nonFungibleTokenTypeHoldersV2(
                contractId = "test1234",
                tokenType = "10000001",
                limit = 10,
                pageToken = null,
                orderBy = OrderBy.ASC
            )
        }
        
        assertTrue(response.responseData is NonFungibleTokenTypeHolderList)
        assertEquals("tlink1z2y3xfvxq3xqrechtey05jea9krmkc3wd6dvx5", response.responseData!!.list.first().walletAddress)
        assertEquals("eJxtjssOgjAQRf9l1iwE1AA7REyICRrtQlekaaeRgAVqIaDh363PuHBWc09mTu4NKOcKL5dQ8qiSWlGmE7OVJTKdVzIVepWXGhUEt88pBKDLXBZ20bie6nh77Tu39YtBMEb9", response.responseData!!.nextPageToken)
    }
}
