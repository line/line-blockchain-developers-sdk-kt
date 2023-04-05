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

import com.linecorp.link.developers.txresult.core.event.item.EventCollectionCreated
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyApproved
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyDisapproved
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DomainTxEventsAdapterV1CollectionTest {
    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given collection_create tx-result, then extract 'EventCollectionCreated' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_create.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionCreated>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionCreated
        assertEquals("fee15a74", eventCollectionCreated.contractId)
        assertEquals("testContract", eventCollectionCreated.name)
        assertEquals("link17k4j8nfr47urlzfz6h7hzdaankpkz0dgce0xkz", eventCollectionCreated.creatorAddress)
        assertEquals(0, eventCollectionCreated.msgIndex)
        assertEquals("EventCollectionCreated", eventCollectionCreated.eventName)
    }

    @Test
    fun `given collection_approve_proxy tx-result, then extract 'EventCollectionProxyApproved' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_approve_proxy.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionProxyApproved>().isNotEmpty())
        val eventCollectionProxyApproved = actual.first() as EventCollectionProxyApproved
        assertEquals("fee15a74", eventCollectionProxyApproved.contractId)
        assertEquals("link1ygceu3trpkkz9gcyr7m3zzv8n82zd3fawea59p", eventCollectionProxyApproved.approverAddress)
        assertEquals("link17k4j8nfr47urlzfz6h7hzdaankpkz0dgce0xkz", eventCollectionProxyApproved.proxyAddress)
        assertEquals(0, eventCollectionProxyApproved.msgIndex)
        assertEquals("EventCollectionProxyApproved", eventCollectionProxyApproved.eventName)
    }

    @Test
    fun `given collection_disapprove_proxy tx-result, then extract 'EventCollectionProxyDisapproved' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_disapprove_proxy.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionProxyDisapproved>().isNotEmpty())
        val eventCollectionProxyDisapproved = actual.first() as EventCollectionProxyDisapproved
        assertEquals("bf365bab", eventCollectionProxyDisapproved.contractId)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionProxyDisapproved.approverAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionProxyDisapproved.proxyAddress)
        assertEquals(0, eventCollectionProxyDisapproved.msgIndex)
        assertEquals("EventCollectionProxyDisapproved", eventCollectionProxyDisapproved.eventName)
    }
}
