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

import com.linecorp.link.developers.txresult.core.event.token.EventTokenBurned
import com.linecorp.link.developers.txresult.core.event.token.EventTokenIssued
import com.linecorp.link.developers.txresult.core.event.token.EventTokenMinted
import com.linecorp.link.developers.txresult.core.event.token.EventTokenModified
import com.linecorp.link.developers.txresult.core.event.token.EventTokenProxyApproved
import com.linecorp.link.developers.txresult.core.event.token.EventTokenTransferred
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainTxEventsAdapterV1TokenTest {
    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given token_issue tx-result, then extract 'EventTokenIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_issue.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenIssued>().isNotEmpty())
        val eventTokenIssued = actual.first() as EventTokenIssued
        assertEquals("Gamja", eventTokenIssued.name)
        assertEquals("987654321", eventTokenIssued.amount)
        assertEquals("9be17165", eventTokenIssued.contractId)
        assertEquals("GAMJA", eventTokenIssued.symbol)
        assertEquals("tlink1n9pqyk4jy8d3pd20quryudxw3g47cl99403558", eventTokenIssued.issuerAddress)
        assertEquals("tlink1n9pqyk4jy8d3pd20quryudxw3g47cl99403558", eventTokenIssued.receiverAddress)
        assertEquals(6, eventTokenIssued.decimals)
    }

    @Test
    fun `given token_mint tx-result, then extract 'EventTokenMinted' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_mint.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenMinted>().isNotEmpty())
        val eventTokenIssued = actual.first() as EventTokenMinted
        assertEquals("1000", eventTokenIssued.amount)
        assertEquals("9636a07e", eventTokenIssued.contractId)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventTokenIssued.minterAddress)
        assertEquals("tlink1nf5uhdmtsshmkqvlmq45kn4q9atnkx4l3u4rww", eventTokenIssued.toAddress)
    }

    @Test
    fun `given token_burn tx-result, then extract 'EventTokenBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_burn.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenBurned>().isNotEmpty())
        val eventTokenBurned = actual.first() as EventTokenBurned
        assertEquals("9be17165", eventTokenBurned.contractId)
        assertEquals("1000", eventTokenBurned.amount)
        assertEquals("tlink1xrr7amq5g80afllmfcud59y3w60q58llx2zpe9", eventTokenBurned.fromAddress)
    }

    @Test
    fun `given token_burn_from tx-result, then extract 'EventTokenBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_burn_from.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenBurned>().isNotEmpty())
        val eventTokenBurned = actual.first() as EventTokenBurned
        assertEquals("678c146a", eventTokenBurned.contractId)
        assertEquals("1", eventTokenBurned.amount)
        assertEquals("link16mk739rd3r3q8a8dw7zr3h50xunxcq0wp80gtu", eventTokenBurned.fromAddress)
        assertEquals("link17gx76scz3pe7gtqq8rmf46favtmxn3sgs6qa49", eventTokenBurned.proxyAddress)
    }

    @Test
    fun `given token_modify tx-result, then extract 'EventTokenModified' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_modify.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenModified>().isNotEmpty())
    }

    @Test
    fun `given token_transfer tx-result, then extract 'EventTokenTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_transfer.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenTransferred>().isNotEmpty())
        val eventTokenTransferred = actual.first() as EventTokenTransferred
        assertEquals("9636a07e", eventTokenTransferred.contractId)
        assertEquals("1000", eventTokenTransferred.amount)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventTokenTransferred.fromAddress)
        assertEquals("tlink1nf5uhdmtsshmkqvlmq45kn4q9atnkx4l3u4rww", eventTokenTransferred.toAddress)
    }

    @Test
    fun `given token_transfer_from tx-result, then extract 'EventTokenTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_transfer_from.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenTransferred>().isNotEmpty())
        val eventTokenTransferred = actual.first() as EventTokenTransferred
        assertEquals("9be17165", eventTokenTransferred.contractId)
        assertEquals("1", eventTokenTransferred.amount)
        assertEquals("tlink149nz34tch6wc5xslljt0q2j8rfnxg27dxrneyd", eventTokenTransferred.fromAddress)
        assertEquals("tlink1r3nl5pm7a8effx39hvac09uxz8eay8jlhyj3us", eventTokenTransferred.toAddress)
        assertEquals("tlink1xrr7amq5g80afllmfcud59y3w60q58llx2zpe9", eventTokenTransferred.proxyAddress)
    }

    @Test
    fun `given token_approve tx-result, then extract 'EventTokenProxyApproved' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/token_approve.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventTokenProxyApproved>().isNotEmpty())
        val eventTokenBurned = actual.first() as EventTokenProxyApproved
        assertEquals("f38bb8a6", eventTokenBurned.contractId)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventTokenBurned.approverAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventTokenBurned.proxyAddress)
    }
}
