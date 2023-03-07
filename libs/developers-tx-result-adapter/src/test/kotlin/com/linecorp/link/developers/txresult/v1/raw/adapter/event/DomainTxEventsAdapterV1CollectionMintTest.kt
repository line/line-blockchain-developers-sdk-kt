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

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftMinted
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainTxEventsAdapterV1CollectionMintTest {

    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_mint_ft tx-result, then extract 'EventCollectionFtMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_mint_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtMinted>().isNotEmpty())
        val eventCollectionFtMinted = actual.first() as EventCollectionFtMinted
        assertEquals("d036ce60", eventCollectionFtMinted.contractId)
        assertEquals("0000000100000000", eventCollectionFtMinted.tokenId)
        assertEquals("10000", eventCollectionFtMinted.amount)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionFtMinted.toAddress)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionFtMinted.minterAddress)
        assertEquals(0, eventCollectionFtMinted.msgIndex)
        assertEquals("EventCollectionFtMinted", eventCollectionFtMinted.eventName)
    }

    @Test
    fun `given collection_mint_nft tx-result, then extract 'EventCollectionNftMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_mint_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftMinted>().isNotEmpty())
        val eventCollectionNftMinted = actual.first() as EventCollectionNftMinted
        assertEquals("61e14383", eventCollectionNftMinted.contractId)
        assertEquals(listOf("1000000100000007"), eventCollectionNftMinted.tokenIds)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionNftMinted.minterAddress)
        assertEquals("tlink12v6t8c3reucj3ahfvx9tvghpltwchh7uvj5frl", eventCollectionNftMinted.toAddress)
        assertEquals(0, eventCollectionNftMinted.msgIndex)
        assertEquals("EventCollectionNftMinted", eventCollectionNftMinted.eventName)
    }

    @Test
    fun `given collection_multi_mint_nft tx-result, then extract 'EventCollectionNftMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_multi_mint_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftMinted>().isNotEmpty())

        assertEquals(4, actual.size)
        val eventMintList = actual.map { it as EventCollectionNftMinted }

        val eventMintNft1 = eventMintList.find { it.tokenIds.contains("1000000100000005") }
        assertNotNull(eventMintNft1)
        assertEquals("803820e6", eventMintNft1.contractId)
        assertEquals(listOf("1000000100000005"), eventMintNft1.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.toAddress)
        assertEquals(0, eventMintNft1.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft1.eventName)


        val eventMintNft2 = eventMintList.find { it.tokenIds.contains("1000000100000006") }
        assertNotNull(eventMintNft2)
        assertEquals("803820e6", eventMintNft2.contractId)
        assertEquals(listOf("1000000100000006"), eventMintNft2.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft2.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft2.toAddress)
        assertEquals(1, eventMintNft2.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft2.eventName)

        val eventMintNft3 = eventMintList.find { it.tokenIds.contains("1000000100000007") }
        assertNotNull(eventMintNft3)
        assertEquals("803820e6", eventMintNft3.contractId)
        assertEquals(listOf("1000000100000007"), eventMintNft3.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft3.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft3.toAddress)
        assertEquals(2, eventMintNft3.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft3.eventName)

        val eventMintNft4 = eventMintList.find { it.tokenIds.contains("1000000100000008") }
        assertNotNull(eventMintNft4)
        assertEquals("803820e6", eventMintNft4.contractId)
        assertEquals(listOf("1000000100000008"), eventMintNft4.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft4.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft4.toAddress)
        assertEquals(3, eventMintNft4.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft4.eventName)
    }

    @Test
    fun `given collection_multi_recipient_multi_mint_nft tx-result, then extract 'EventCollectionNftMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText(
                "raw-transaction/collection_multi_recipient_multi_mint_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftMinted>().isNotEmpty())

        assertEquals(3, actual.size)
        val eventMintList = actual.map { it as EventCollectionNftMinted }

        val eventMintNft1 = eventMintList.find { it.tokenIds.contains("1000000100000009") }
        assertNotNull(eventMintNft1)
        assertEquals("803820e6", eventMintNft1.contractId)
        assertEquals(listOf("1000000100000009"), eventMintNft1.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.toAddress)
        assertEquals(0, eventMintNft1.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft1.eventName)


        val eventMintNft2 = eventMintList.find { it.tokenIds.contains("100000010000000a") }
        assertNotNull(eventMintNft2)
        assertEquals("803820e6", eventMintNft2.contractId)
        assertEquals(listOf("100000010000000a"), eventMintNft2.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft2.minterAddress)
        assertEquals("tlink1cqvmpjyyfk4p0xr7zz2g2273jzpw3swwtqk3kf", eventMintNft2.toAddress)
        assertEquals(1, eventMintNft2.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft2.eventName)

        val eventMintNft3 = eventMintList.find { it.tokenIds.contains("100000010000000b") }
        assertNotNull(eventMintNft3)
        assertEquals("803820e6", eventMintNft3.contractId)
        assertEquals(listOf("100000010000000b"), eventMintNft3.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft3.minterAddress)
        assertEquals("tlink1nq492tmyhcdz5dp52r7hht6f3w9f3m5wwxwyxv", eventMintNft3.toAddress)
        assertEquals(2, eventMintNft3.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft3.eventName)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `given collection_multi_mint_nft collection_multi_recipient_multi_mint_nft_different_recipients-result, then extract 'EventCollectionNftMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader
                .loadRawTransactionResultInJsonText(
                    "raw-transaction/collection_multi_recipient_multi_mint_nft_different_recipients.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftMinted>().isNotEmpty())

        assertEquals(3, actual.size)
        val eventMintList = actual.map { it as EventCollectionNftMinted }

        val eventMintNft1 = eventMintList.find { it.tokenIds.contains("100000010000000c") }
        assertNotNull(eventMintNft1)
        assertEquals("803820e6", eventMintNft1.contractId)
        assertEquals(listOf("100000010000000c"), eventMintNft1.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.minterAddress)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft1.toAddress)
        assertEquals(0, eventMintNft1.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft1.eventName)

        val eventMintNft2 = eventMintList.find { it.tokenIds.contains("100000010000000d") }
        assertNotNull(eventMintNft2)
        assertEquals("803820e6", eventMintNft2.contractId)
        assertEquals(listOf("100000010000000d"), eventMintNft2.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft2.minterAddress)
        assertEquals("tlink1cqvmpjyyfk4p0xr7zz2g2273jzpw3swwtqk3kf", eventMintNft2.toAddress)
        assertEquals(1, eventMintNft2.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft2.eventName)

        val eventMintNft3 = eventMintList.find { it.tokenIds.contains("100000010000000e") }
        assertNotNull(eventMintNft3)
        assertEquals("803820e6", eventMintNft3.contractId)
        assertEquals(listOf("100000010000000e"), eventMintNft3.tokenIds)
        assertEquals("tlink1uly93jzy4qlpf6k803uz4tke6auwl3ukhns90t", eventMintNft3.minterAddress)
        assertEquals("tlink1nq492tmyhcdz5dp52r7hht6f3w9f3m5wwxwyxv", eventMintNft3.toAddress)
        assertEquals(2, eventMintNft3.msgIndex)
        assertEquals("EventCollectionNftMinted", eventMintNft3.eventName)
    }
}
