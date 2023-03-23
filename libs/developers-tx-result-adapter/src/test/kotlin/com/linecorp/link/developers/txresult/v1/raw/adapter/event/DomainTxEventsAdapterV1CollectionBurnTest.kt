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

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftBurned
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DomainTxEventsAdapterV1CollectionBurnTest {

    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_burn_ft tx-result, then extract 'EventCollectionFtBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtBurned>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionFtBurned
        assertEquals("61e14383", eventCollectionFtBurned.contractId)
        assertEquals("1", eventCollectionFtBurned.amount)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.fromAddress)
        assertEquals(0, eventCollectionFtBurned.msgIndex)
        assertEquals("EventCollectionFtBurned", eventCollectionFtBurned.eventName)
    }

    @Test
    fun `given collection_burn_ft_from tx-result, then extract 'EventCollectionFtBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_ft_from.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtBurned>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionFtBurned
        assertEquals("2d8be688", eventCollectionFtBurned.contractId)
        assertEquals("00000001", eventCollectionFtBurned.tokenType)
        assertEquals("500", eventCollectionFtBurned.amount)
        assertEquals("link1yhjrm7zxn97eu5tnz76j32r76sfq02mtmjttuq", eventCollectionFtBurned.fromAddress)
        assertEquals("link1z9x3cnadjdvxlrlyl9myrau2uxqrpd0hfwslu4", eventCollectionFtBurned.proxyAddress)
        assertEquals(0, eventCollectionFtBurned.msgIndex)
        assertEquals("EventCollectionFtBurned", eventCollectionFtBurned.eventName)
    }

    @Test
    fun `given collection_burn_nft tx-result, then extract 'EventCollectionNftBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftBurned>().isNotEmpty())
        val eventCollectionNftBurned = actual.first() as EventCollectionNftBurned
        assertEquals("61e14383", eventCollectionNftBurned.contractId)
        assertEquals(listOf("1000000100000003"), eventCollectionNftBurned.tokenIds)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionNftBurned.fromAddress)
        assertEquals(0, eventCollectionNftBurned.msgIndex)
        assertEquals("EventCollectionNftBurned", eventCollectionNftBurned.eventName)
    }

    @Test
    fun `given collection_burn_nft_from tx-result, then extract 'EventCollectionFtBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_nft_from.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftBurned>().isNotEmpty())
        val eventCollectionNftBurned = actual.first() as EventCollectionNftBurned
        assertEquals("61e14383", eventCollectionNftBurned.contractId)
        assertEquals(listOf("1000000100000005"), eventCollectionNftBurned.tokenIds)
        assertEquals("tlink17dz3hqn6nd5j6euymaw3ft9phgspmuhfjqazph", eventCollectionNftBurned.fromAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionNftBurned.proxyAddress)
        assertEquals(0, eventCollectionNftBurned.msgIndex)
        assertEquals("EventCollectionNftBurned", eventCollectionNftBurned.eventName)
    }
}
