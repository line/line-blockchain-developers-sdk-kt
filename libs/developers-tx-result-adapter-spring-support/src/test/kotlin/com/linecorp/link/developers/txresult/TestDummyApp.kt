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
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.core.io.ClassPathResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("EmptyClassBlock")
@SpringBootApplication()
@ConfigurationPropertiesScan(
    "com.linecorp.link.developers.txresult"
)
class TestDummyApp {
}

fun main(args: Array<String>) {
    runApplication<TestDummyApp>(*args)
}


@RestController
class SampleController(
    private val objectMapper: ObjectMapper
) {
    @GetMapping("/test")
    fun test(): String {
        return objectMapper.writeValueAsString(TestData(name = "test"))
    }

    @GetMapping("/sample-tx-result/json-string")
    fun sampleTxResultJsonString(): String {
        val txJsonString = ClassPathResource("./txresults/burn-ft-txresult-response.json").inputStream.bufferedReader()
            .use { it.readText() }
        val txResult = objectMapper.readValue(txJsonString, TxResult::class.java)
        return objectMapper.writeValueAsString(txResult)
    }

    @GetMapping("/sample-tx-result")
    fun sampleTxResult(): TxResult {
        val txJsonString = ClassPathResource("./txresults/burn-ft-txresult-response.json").inputStream.bufferedReader()
            .use { it.readText() }
        return objectMapper.readValue(txJsonString, TxResult::class.java)
    }
}

data class TestData(
    val name: String
)
