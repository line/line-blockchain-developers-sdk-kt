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
import com.linecorp.link.developers.txresult.core.event.bank.EventCoinTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTransferred
import com.linecorp.link.developers.txresult.core.event.token.EventTokenBurned
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenMinted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyApproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenTransferred
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
    fun `events from updating item token collection tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/update-item-token-collection-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionModified })
    }

    @Test
    fun `events from minting item token collection tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/mint-nft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionNftMinted })
        val eventCollectionNftMinted = actual.events.filterIsInstance<EventCollectionNftMinted>().first()
        assertTrue(eventCollectionNftMinted.tokenIds.isNotEmpty())
    }

    @Test
    fun `events from issuing item token ft tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/issue-ft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionFtIssued })
    }

    @Test
    fun `events from minting item token ft tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/mint-ft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionFtMinted })
    }

    @Test
    fun `events from burning item token ft tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/burn-ft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionFtBurned })
    }

    @Test
    fun `events from transferring item token ft tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/transfer-ft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionFtTransferred })
    }

    @Test
    fun `events from updating item token ft tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream("./txresults/update-ft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionFtModified })
    }


    @Test
    fun `events from minting and transferring item token collection tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/mint-transfer-nft-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCollectionNftMinted })
        assertTrue(actual.events.any { it is EventCollectionNftTransferred })
        val eventCollectionNftMinted = actual.events.filterIsInstance<EventCollectionNftMinted>().first()
        assertTrue(eventCollectionNftMinted.tokenIds.isNotEmpty())
        val eventCollectionNftTransferred = actual.events.filterIsInstance<EventCollectionNftTransferred>().first()
        assertTrue(eventCollectionNftTransferred.tokenIds.isNotEmpty())
    }

    @Test
    fun `events from transferring base-coin tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/tranfer-base-coin-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventCoinTransferred })
    }

    @Test
    fun `events from issuing service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/create-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenIssued })
    }

    @Test
    fun `events from updating service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/update-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenModified })
    }

    @Test
    fun `events from burning service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/burn-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenBurned })
    }

    @Test
    fun `events from minting service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/mint-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenMinted })
    }

    @Test
    fun `events from transferFrom service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/transfer-from-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenTransferred })
        val eventCoinTransferred = actual.events.filterIsInstance<EventTokenTransferred>().first()
        assertTrue(eventCoinTransferred.proxyAddress?.isNotBlank() ?: false)
    }

    @Test
    fun `events from approve proxy for service token tx-result`() {
        val typeReference = object : TypeReference<TxResult>() {}
        val inputStream =
            this::class.java.classLoader.getResourceAsStream("./txresults/approve-proxy-service-token-txresult.json")
        val actual = objectMapper.readValue(inputStream, typeReference)
        assertTrue(actual.events.any { it is EventTokenProxyApproved })
        val eventCoinTransferred = actual.events.filterIsInstance<EventTokenProxyApproved>().first()
        assertTrue(eventCoinTransferred.proxyAddress.isNotBlank())
    }

}
