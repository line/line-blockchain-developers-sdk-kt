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

package com.linecorp.link.developers.jackson

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.core.model.TxResult
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionEventDeserializerTest {
    private lateinit var underTest: TransactionEventDeserializer

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        underTest = TransactionEventDeserializer()
        objectMapper = jacksonObjectMapper()
        val simpleModule = SimpleModule().addDeserializer(
            TransactionEvent::class.java,
            underTest
        )
        objectMapper.registerModule(simpleModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Test
    fun `events from issuing service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/create-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenIssued })
    }

    @Test
    fun `events from updating service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/update-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenModified })
    }

    @Test
    fun `events from updating item token collection tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/update-item-token-collection-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionModified })
    }

}
