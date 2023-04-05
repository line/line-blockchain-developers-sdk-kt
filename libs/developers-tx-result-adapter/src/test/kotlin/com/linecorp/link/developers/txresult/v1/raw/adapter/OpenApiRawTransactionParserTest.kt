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

package com.linecorp.link.developers.txresult.v1.raw.adapter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.link.developers.GenericResponse
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult
import java.nio.file.Files
import java.util.stream.Collectors
import kotlin.io.path.toPath
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenApiRawTransactionParserTest {
    private lateinit var mockWebServer: MockWebServer
    private val objectMapper = jacksonObjectMapper()

    @BeforeAll
    fun setUpAll() {
        mockWebServer = MockWebServer()
        mockWebServer.start(29999)
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_parse_tx_result_response() {
        val resourceUrl = this::class.java.classLoader.getResource("./http/open-api-sample-response.json")!!
        val path = resourceUrl.toURI().toPath()
        val responseBody = Files.readAllLines(path).stream().collect(Collectors.joining(" "))

        val typeReference = object : TypeReference<GenericResponse<RawTransactionResult>>() {}
        val rawTxResultResponse = objectMapper.readValue(resourceUrl, typeReference)

        mockWebServer.enqueue(okResponse(responseBody))

        val webClient = WebClient.create("http://localhost:29999")
        val txResultResponse = webClient.get()
            .uri("/v1/transactions/{txHash}", "61AB8A054D47CA05E4ABE591B929282CBCD7DACD5A4C8259020C566F0EC186BE")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono<GenericResponse<RawTransactionResult>>().block()

        assertNotNull(txResultResponse)
        assertNotNull(txResultResponse.responseData)
        assertNotNull(txResultResponse.responseData.txhash)
        assertEquals(txResultResponse.responseData.txhash, rawTxResultResponse.responseData?.txhash)
    }

    private fun okResponse(responseBody: String) = MockResponse()
        .setResponseCode(200)
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(responseBody)
}
