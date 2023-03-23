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

package com.linecorp.link.developers.txresult.v2

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.link.developers.jackson.TransactionEventDeserializer
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.core.model.TxResult
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetNewTxResultTest {
    private lateinit var mockWebServer: MockWebServer
    private val mockServerPort = 3000

    private val objectMapper = createObjectMapper()
    private val strategies = ExchangeStrategies
        .builder()
        .codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
        }.build()

    private val webClient =
        WebClient.builder().exchangeStrategies(strategies).baseUrl("http://localhost:$mockServerPort").build()

    private fun createObjectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        val simpleModule = SimpleModule().addDeserializer(
            TransactionEvent::class.java,
            TransactionEventDeserializer()
        )
        objectMapper.registerModule(simpleModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }

    @BeforeAll
    fun setUpAll() {
        mockWebServer = MockWebServer()
        mockWebServer.start(mockServerPort)
    }

    @AfterAll
    fun tearDownAll() {
        mockWebServer.shutdown()
    }

    @Test
    fun getNewTxResult() {

        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/burn-ft-txresult.json")
        val body = inputStream.bufferedReader().use { it.readText() }
        mockWebServer.enqueue(okResponse(body))

        val txResult = webClient.get().retrieve().bodyToMono(TxResult::class.java).block()
        assertNotNull(txResult)
        assertTrue(txResult.events.any { it.eventName == "EventCollectionFtBurned" })
    }

    private fun okResponse(body: String): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(body)
    }
}
