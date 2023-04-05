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

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftIssued
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DomainTxEventsAdapterV1CollectionIssueTest {

    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_issue_ft tx-result, then extract 'EventCollectionFtIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtIssued>().isNotEmpty())
        val eventCollectionFtIssued = actual.first() as EventCollectionFtIssued
        assertEquals("61e14383", eventCollectionFtIssued.contractId)
        assertEquals("FungibleName", eventCollectionFtIssued.name)
        assertEquals(0, eventCollectionFtIssued.decimals)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtIssued.issuerAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtIssued.receiverAddress)
        assertEquals("00000031", eventCollectionFtIssued.tokenType)
        assertEquals(0, eventCollectionFtIssued.msgIndex)
        assertEquals("EventCollectionFtIssued", eventCollectionFtIssued.eventName)
    }

    @Test
    fun `given collection_issue_ft_2 tx-result, then extract 'EventCollectionFtIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_ft_2.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtIssued>().isNotEmpty())
        val eventCollectionFtIssued = actual.first() as EventCollectionFtIssued
        assertEquals("2d8be688", eventCollectionFtIssued.contractId)
        assertEquals("QAT", eventCollectionFtIssued.name)
        assertEquals(0, eventCollectionFtIssued.decimals)
        assertEquals("link1z9x3cnadjdvxlrlyl9myrau2uxqrpd0hfwslu4", eventCollectionFtIssued.issuerAddress)
        assertEquals("link1z9x3cnadjdvxlrlyl9myrau2uxqrpd0hfwslu4", eventCollectionFtIssued.receiverAddress)
        assertEquals("00000245", eventCollectionFtIssued.tokenType)
        assertEquals(0, eventCollectionFtIssued.msgIndex)
        assertEquals("EventCollectionFtIssued", eventCollectionFtIssued.eventName)
    }

    @Test
    fun `given collection_issue_nft tx-result, then extract 'EventCollectionNftIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftIssued>().isNotEmpty())
        val eventCollectionNftIssued = actual.first() as EventCollectionNftIssued
        assertEquals("61e14383", eventCollectionNftIssued.contractId)
        assertEquals("1000000c", eventCollectionNftIssued.tokenType)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionNftIssued.issuerAddress)
        assertEquals(0, eventCollectionNftIssued.msgIndex)
        assertEquals("EventCollectionNftIssued", eventCollectionNftIssued.eventName)
    }

    @Test
    fun `given collection_issue_nft_2 tx-result, then extract 'EventCollectionNftIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_nft_2.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftIssued>().isNotEmpty())
        val eventCollectionNftIssued = actual.first() as EventCollectionNftIssued
        assertEquals("2d8be688", eventCollectionNftIssued.contractId)
        assertEquals("1000012e", eventCollectionNftIssued.tokenType)
        assertEquals(0, eventCollectionNftIssued.msgIndex)
        assertEquals("EventCollectionNftIssued", eventCollectionNftIssued.eventName)
    }
}
