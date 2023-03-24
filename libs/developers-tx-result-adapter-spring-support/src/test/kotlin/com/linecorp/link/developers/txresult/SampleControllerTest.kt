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

package com.linecorp.link.developers.txresult

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.link.developers.txresult.core.model.TxResult
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    classes = [TestDummyApp::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
class SampleControllerTest {
    @Autowired
    private lateinit var testWebClient: WebTestClient

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun testApi() {
        testWebClient.get().uri("/test")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
    }

    @Test
    fun sampleTxResultJsonString() {
        testWebClient.get().uri("/sample-tx-result/json-string")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith {
                val txResult = objectMapper.readValue(it.responseBody, TxResult::class.java)
                assertNotNull(txResult)
            }
    }

    @Test
    fun sampleTxResult() {
        testWebClient.get().uri("/sample-tx-result")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith {
                val txResult = objectMapper.readValue(it.responseBody, TxResult::class.java)
                assertNotNull(txResult)
            }
    }
}
