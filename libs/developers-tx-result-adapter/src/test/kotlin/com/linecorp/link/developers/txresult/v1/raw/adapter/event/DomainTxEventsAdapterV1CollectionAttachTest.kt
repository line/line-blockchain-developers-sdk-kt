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

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftAttached
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftRootChanged
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainTxEventsAdapterV1CollectionAttachTest {
    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_attach_nft tx-result, then extract 'EventCollectionNftAttached' `() {

        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_attach_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftAttached>().isNotEmpty())
        val eventCollectionNftAttached = actual.first() as EventCollectionNftAttached
        assertEquals("d036ce60", eventCollectionNftAttached.contractId)
        assertEquals("1000000100000002", eventCollectionNftAttached.parentTokenId)
        assertEquals("1000000100000003", eventCollectionNftAttached.childTokenId)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionNftAttached.holderAddress)
        assertEquals(0, eventCollectionNftAttached.msgIndex)
        assertEquals("EventCollectionNftAttached", eventCollectionNftAttached.eventName)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("d036ce60", eventCollectionNftRootChanged.contractId)
        assertEquals(1, eventCollectionNftRootChanged.tokenIds.size)
        assertEquals(listOf("1000000100000003"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("1000000100000003", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("1000000100000002", eventCollectionNftRootChanged.newRootTokenId)
        assertEquals(0, eventCollectionNftRootChanged.msgIndex)
        assertEquals("EventCollectionNftRootChanged", eventCollectionNftRootChanged.eventName)
    }

    @Test
    fun `given collection_attach_from_nft tx-result, then extract 'EventCollectionNftAttached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_attach_from_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftAttached>().isNotEmpty())
        val eventCollectionNftAttached = actual.first() as EventCollectionNftAttached
        assertEquals("61e14383", eventCollectionNftAttached.contractId)
        assertEquals("100000010000000c", eventCollectionNftAttached.parentTokenId)
        assertEquals("100000010000000b", eventCollectionNftAttached.childTokenId)
        assertEquals("tlink17dz3hqn6nd5j6euymaw3ft9phgspmuhfjqazph", eventCollectionNftAttached.holderAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionNftAttached.proxyAddress)
        assertEquals(0, eventCollectionNftAttached.msgIndex)
        assertEquals("EventCollectionNftAttached", eventCollectionNftAttached.eventName)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("61e14383", eventCollectionNftRootChanged.contractId)
        assertEquals(1, eventCollectionNftRootChanged.tokenIds.size)
        assertEquals(listOf("100000010000000b"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("100000010000000b", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("100000010000000c", eventCollectionNftRootChanged.newRootTokenId)
        assertEquals(0, eventCollectionNftRootChanged.msgIndex)
        assertEquals("EventCollectionNftRootChanged", eventCollectionNftRootChanged.eventName)
    }

    @Test
    fun `given collection_attach_nft_already_has_children tx-result, then extract 'EventCollectionNftAttached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_attach_nft_already_has_children.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftAttached>().isNotEmpty())
        val eventCollectionNftAttached = actual.first() as EventCollectionNftAttached
        assertEquals("803820e6", eventCollectionNftAttached.contractId)
        assertEquals("1000000100000005", eventCollectionNftAttached.parentTokenId)
        assertEquals("1000000100000001", eventCollectionNftAttached.childTokenId)
        assertEquals("tlink1nq492tmyhcdz5dp52r7hht6f3w9f3m5wwxwyxv", eventCollectionNftAttached.holderAddress)
        assertEquals(0, eventCollectionNftAttached.msgIndex)
        assertEquals("EventCollectionNftAttached", eventCollectionNftAttached.eventName)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("803820e6", eventCollectionNftRootChanged.contractId)
        assertEquals(listOf("1000000100000001", "1000000100000007"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("1000000100000001", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("1000000100000005", eventCollectionNftRootChanged.newRootTokenId)
        assertEquals(0, eventCollectionNftRootChanged.msgIndex)
        assertEquals("EventCollectionNftRootChanged", eventCollectionNftRootChanged.eventName)
    }
}
