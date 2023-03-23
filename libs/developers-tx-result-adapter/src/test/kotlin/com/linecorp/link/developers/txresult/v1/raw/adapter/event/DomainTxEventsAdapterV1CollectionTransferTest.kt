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

package com.linecorp.link.developers.txresult.v1.raw.adapter.event

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftHolderChanged
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTransferred
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DomainTxEventsAdapterV1CollectionTransferTest {

    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_transfer_from_ft tx-result, then extract 'EventCollectionFtTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_from_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtTransferred>().isNotEmpty())
        val eventCollectionFtTransferred = actual.first() as EventCollectionFtTransferred
        assertEquals("bf365bab", eventCollectionFtTransferred.contractId)
        assertEquals("00000001", eventCollectionFtTransferred.tokenType)
        assertEquals("50", eventCollectionFtTransferred.amount)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionFtTransferred.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionFtTransferred.toAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionFtTransferred.proxyAddress)
        assertEquals(0, eventCollectionFtTransferred.msgIndex)
        assertEquals("EventCollectionFtTransferred", eventCollectionFtTransferred.eventName)
    }

    @Test
    fun `given collection_transfer_nft tx-result, then extract 'EventCollectionNftTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTransferred>().isNotEmpty())
        val eventCollectionNftTransferred = actual.first() as EventCollectionNftTransferred
        assertEquals("d036ce60", eventCollectionNftTransferred.contractId)
        assertEquals(listOf("1000000100000001"), eventCollectionNftTransferred.tokenIds)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionNftTransferred.fromAddress)
        assertEquals("link1rrjua8zktmqnr6hlsqz7qyx5gxm5z96yt8f5ae", eventCollectionNftTransferred.toAddress)
        assertEquals("", eventCollectionNftTransferred.proxyAddress)
        assertEquals(0, eventCollectionNftTransferred.msgIndex)
        assertEquals("EventCollectionNftTransferred", eventCollectionNftTransferred.eventName)

        val eventCollectionNftHolderChanged = actual.filterIsInstance<EventCollectionNftHolderChanged>().first()
        assertNotNull(eventCollectionNftHolderChanged)
        assertEquals("d036ce60", eventCollectionNftHolderChanged.contractId)
        assertEquals(listOf("1000000100000001"), eventCollectionNftHolderChanged.tokenIds)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionNftHolderChanged.fromAddress)
        assertEquals("link1rrjua8zktmqnr6hlsqz7qyx5gxm5z96yt8f5ae", eventCollectionNftHolderChanged.toAddress)
        assertEquals(0, eventCollectionNftHolderChanged.msgIndex)
        assertEquals("EventCollectionNftHolderChanged", eventCollectionNftHolderChanged.eventName)
    }

    @Test
    fun `given collection_transfer_from_nft tx-result, then extract 'EventCollectionNftTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_from_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTransferred>().isNotEmpty())
        val eventCollectionNftTransferred = actual.first() as EventCollectionNftTransferred
        assertEquals("bf365bab", eventCollectionNftTransferred.contractId)
        assertEquals(listOf("100000010000000e", "100000010000000f"), eventCollectionNftTransferred.tokenIds)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionNftTransferred.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionNftTransferred.toAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionNftTransferred.proxyAddress)
        assertEquals(0, eventCollectionNftTransferred.msgIndex)
        assertEquals("EventCollectionNftTransferred", eventCollectionNftTransferred.eventName)

        val eventCollectionNftHolderChanged = actual.filterIsInstance<EventCollectionNftHolderChanged>().first()
        assertNotNull(eventCollectionNftHolderChanged)
        assertEquals("bf365bab", eventCollectionNftHolderChanged.contractId)
        assertEquals(listOf("100000010000000e", "100000010000000f"), eventCollectionNftHolderChanged.tokenIds)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionNftHolderChanged.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionNftHolderChanged.toAddress)
        assertEquals(0, eventCollectionNftHolderChanged.msgIndex)
        assertEquals("EventCollectionNftHolderChanged", eventCollectionNftHolderChanged.eventName)
    }

    @Test
    fun `given collection_transfer_batch_nft tx-result, then extract 'EventCollectionNftTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText(
                "raw-transaction/collection_transfer_batch_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTransferred>().isNotEmpty())
        val eventCollectionNftTransferred = actual.first() as EventCollectionNftTransferred
        assertEquals("803820e6", eventCollectionNftTransferred.contractId)
        assertEquals(listOf("1000000100000004", "1000000100000006"), eventCollectionNftTransferred.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventCollectionNftTransferred.fromAddress)
        assertEquals("tlink1nq492tmyhcdz5dp52r7hht6f3w9f3m5wwxwyxv", eventCollectionNftTransferred.toAddress)
        assertEquals("", eventCollectionNftTransferred.proxyAddress)
        assertEquals(0, eventCollectionNftTransferred.msgIndex)
        assertEquals("EventCollectionNftTransferred", eventCollectionNftTransferred.eventName)

        val eventCollectionNftHolderChanged = actual.filterIsInstance<EventCollectionNftHolderChanged>().first()
        assertNotNull(eventCollectionNftHolderChanged)
        assertEquals("803820e6", eventCollectionNftHolderChanged.contractId)
        assertEquals(listOf("1000000100000004", "1000000100000006"), eventCollectionNftHolderChanged.tokenIds)
        assertEquals(
            "tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t",
            eventCollectionNftHolderChanged.fromAddress
        )
        assertEquals("tlink1nq492tmyhcdz5dp52r7hht6f3w9f3m5wwxwyxv", eventCollectionNftHolderChanged.toAddress)
        assertEquals(0, eventCollectionNftHolderChanged.msgIndex)
        assertEquals("EventCollectionNftHolderChanged", eventCollectionNftHolderChanged.eventName)
    }
}
