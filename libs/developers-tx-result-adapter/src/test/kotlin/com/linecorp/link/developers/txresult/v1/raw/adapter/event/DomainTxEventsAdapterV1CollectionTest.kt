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

import com.linecorp.link.developers.txresult.core.event.item.CollectionAttribute
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionCreated
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftAttached
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftBurned
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftDetached
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftHolderChanged
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftIssued
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftRootChanged
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTransferred
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftTypeModified
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyApproved
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionProxyDisapproved
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        assertEquals("00000001", eventCollectionFtBurned.tokenType)
        assertEquals("1", eventCollectionFtBurned.amount)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.fromAddress)
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
    }

    @Test
    fun `given collection_issue_ft tx-result, then extract 'EventCollectionFtIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtIssued>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionFtIssued
        assertEquals("61e14383", eventCollectionCreated.contractId)
        assertEquals("FungibleName", eventCollectionCreated.name)
        assertEquals(0, eventCollectionCreated.decimals)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionCreated.issuerAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionCreated.receiverAddress)
    }

    @Test
    fun `given collection_modify_ft tx-result, then extract 'EventCollectionFtModified' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_modify_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtModified>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionFtModified
        assertEquals("d036ce60", eventCollectionCreated.contractId)
        assertEquals(
            setOf(
                CollectionAttribute("name", "FTTEST1")
            ), eventCollectionCreated.tokenAttributes
        )
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionCreated.modifierAddress)
    }

    @Test
    fun `given collection_transfer_ft tx-result, then extract 'EventCollectionFtTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtTransferred>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionFtTransferred
        assertEquals("d036ce60", eventCollectionCreated.contractId)
        assertEquals("10", eventCollectionCreated.amount)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionCreated.fromAddress)
        assertEquals("link1rrjua8zktmqnr6hlsqz7qyx5gxm5z96yt8f5ae", eventCollectionCreated.toAddress)
        assertEquals("", eventCollectionCreated.proxyAddress)
    }

    @Test
    fun `given collection_transfer_from_ft tx-result, then extract 'EventCollectionFtTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_from_ft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionFtTransferred>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionFtTransferred
        assertEquals("bf365bab", eventCollectionCreated.contractId)
        assertEquals("50", eventCollectionCreated.amount)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionCreated.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionCreated.toAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionCreated.proxyAddress)
    }

    @Test
    fun `given collection_attach_nft tx-result, then extract 'EventCollectionNftAttached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_attach_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftAttached>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftAttached
        assertEquals("d036ce60", eventCollectionFtBurned.contractId)
        assertEquals("1000000100000002", eventCollectionFtBurned.parentTokenId)
        assertEquals("1000000100000003", eventCollectionFtBurned.childTokenId)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("00000003"), eventCollectionFtBurned.tokenIndices)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionFtBurned.holderAddress)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("d036ce60", eventCollectionNftRootChanged.contractId)
        assertEquals(listOf("1000000100000003"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("1000000100000003", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("1000000100000002", eventCollectionNftRootChanged.newRootTokenId)
    }

    @Test
    fun `given collection_attach_from_nft tx-result, then extract 'EventCollectionNftAttached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_attach_from_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftAttached>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftAttached
        assertEquals("61e14383", eventCollectionFtBurned.contractId)
        assertEquals("100000010000000c", eventCollectionFtBurned.parentTokenId)
        assertEquals("100000010000000b", eventCollectionFtBurned.childTokenId)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("0000000b"), eventCollectionFtBurned.tokenIndices)
        assertEquals("tlink17dz3hqn6nd5j6euymaw3ft9phgspmuhfjqazph", eventCollectionFtBurned.holderAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.proxyAddress)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("61e14383", eventCollectionNftRootChanged.contractId)
        assertEquals(listOf("100000010000000b"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("100000010000000b", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("100000010000000c", eventCollectionNftRootChanged.newRootTokenId)
    }

    @Test
    fun `given collection_burn_nft tx-result, then extract 'EventCollectionNftAttached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftBurned>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftBurned
        assertEquals("61e14383", eventCollectionFtBurned.contractId)
        assertEquals(listOf("1000000100000003"), eventCollectionFtBurned.tokenIds)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("00000003"), eventCollectionFtBurned.tokenIndices)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.fromAddress)
    }

    @Test
    fun `given collection_burn_nft_from tx-result, then extract 'EventCollectionFtBurned' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_burn_nft_from.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftBurned>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftBurned
        assertEquals("61e14383", eventCollectionFtBurned.contractId)
        assertEquals(listOf("1000000100000005"), eventCollectionFtBurned.tokenIds)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("00000005"), eventCollectionFtBurned.tokenIndices)
        assertEquals("tlink17dz3hqn6nd5j6euymaw3ft9phgspmuhfjqazph", eventCollectionFtBurned.fromAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.proxyAddress)
    }

    @Test
    fun `given collection_detach_nft tx-result, then extract 'EventCollectionNftDetached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_detach_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftDetached>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftDetached
        assertEquals("d036ce60", eventCollectionFtBurned.contractId)
        assertEquals("1000000100000002", eventCollectionFtBurned.exParentTokenId)
        assertEquals("1000000100000003", eventCollectionFtBurned.exChildTokenId)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("00000003"), eventCollectionFtBurned.tokenIndices)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionFtBurned.holderAddress)

        val eventCollectionNftRootChanged = actual.filterIsInstance<EventCollectionNftRootChanged>().first()
        assertNotNull(eventCollectionNftRootChanged)
        assertEquals("d036ce60", eventCollectionNftRootChanged.contractId)
        assertEquals(listOf("1000000100000003"), eventCollectionNftRootChanged.tokenIds)
        assertEquals("1000000100000002", eventCollectionNftRootChanged.oldRootTokenId)
        assertEquals("1000000100000003", eventCollectionNftRootChanged.newRootTokenId)
    }

    @Test
    fun `given collection_detach_from_nft tx-result, then extract 'EventCollectionNftDetached' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_detach_from_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftDetached>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionNftDetached
        assertEquals("61e14383", eventCollectionFtBurned.contractId)
        assertEquals("100000010000000c", eventCollectionFtBurned.exParentTokenId)
        assertEquals("100000010000000b", eventCollectionFtBurned.exChildTokenId)
        assertEquals(listOf("10000001"), eventCollectionFtBurned.tokenTypes)
        assertEquals(listOf("0000000b"), eventCollectionFtBurned.tokenIndices)
        assertEquals("tlink17dz3hqn6nd5j6euymaw3ft9phgspmuhfjqazph", eventCollectionFtBurned.holderAddress)
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionFtBurned.proxyAddress)
    }

    @Test
    fun `given collection_issue_nft tx-result, then extract 'EventCollectionNftIssued' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_issue_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftIssued>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionNftIssued
        assertEquals("61e14383", eventCollectionCreated.contractId)
        assertEquals("1000000c", eventCollectionCreated.tokenType)
    }

    @Test
    fun `given collection_transfer_nft tx-result, then extract 'EventCollectionNftTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTransferred>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionNftTransferred
        assertEquals("d036ce60", eventCollectionCreated.contractId)
        assertEquals(listOf("1000000100000001"), eventCollectionCreated.tokenIds)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionCreated.fromAddress)
        assertEquals("link1rrjua8zktmqnr6hlsqz7qyx5gxm5z96yt8f5ae", eventCollectionCreated.toAddress)
        assertEquals("", eventCollectionCreated.proxyAddress)

        val eventCollectionNftHolderChanged = actual.filterIsInstance<EventCollectionNftHolderChanged>().first()
        assertNotNull(eventCollectionNftHolderChanged)
        assertEquals("d036ce60", eventCollectionNftHolderChanged.contractId)
        assertEquals(listOf("1000000100000001"), eventCollectionNftHolderChanged.tokenIds)
        assertEquals("link153tnef6fp2w95ny00cegj6pfyvsqcsy2dkv6lt", eventCollectionNftHolderChanged.fromAddress)
        assertEquals("link1rrjua8zktmqnr6hlsqz7qyx5gxm5z96yt8f5ae", eventCollectionNftHolderChanged.toAddress)
    }

    @Test
    fun `given collection_transfer_from_nft tx-result, then extract 'EventCollectionNftTransferred' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_transfer_from_nft.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTransferred>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionNftTransferred
        assertEquals("bf365bab", eventCollectionCreated.contractId)
        assertEquals(listOf("100000010000000e", "100000010000000f"), eventCollectionCreated.tokenIds)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionCreated.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionCreated.toAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionCreated.proxyAddress)

        val eventCollectionNftHolderChanged = actual.filterIsInstance<EventCollectionNftHolderChanged>().first()
        assertNotNull(eventCollectionNftHolderChanged)
        assertEquals("bf365bab", eventCollectionNftHolderChanged.contractId)
        assertEquals(listOf("100000010000000e", "100000010000000f"), eventCollectionNftHolderChanged.tokenIds)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionNftHolderChanged.fromAddress)
        assertEquals("link137pmnn2snxdcwa5kmg5rra6u3tf2y5c7emmm7p", eventCollectionNftHolderChanged.toAddress)
    }

    @Test
    fun `given collection_modify_nft_type tx-result, then extract 'EventCollectionNftTypeModified' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_modify_nft_type.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionNftTypeModified>().isNotEmpty())
        val eventCollectionCreated = actual.first() as EventCollectionNftTypeModified
        assertEquals("61e14383", eventCollectionCreated.contractId)
        assertEquals(
            setOf(
                CollectionAttribute("name", "NFT Name"),
                CollectionAttribute("meta", "NFT meta")
            ), eventCollectionCreated.tokenAttributes
        )
        assertEquals("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq", eventCollectionCreated.modifierAddress)
    }

    @Test
    fun `given collection_approve_proxy tx-result, then extract 'EventCollectionProxyApproved' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_approve_proxy.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionProxyApproved>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionProxyApproved
        assertEquals("fee15a74", eventCollectionFtBurned.contractId)
        assertEquals("link1ygceu3trpkkz9gcyr7m3zzv8n82zd3fawea59p", eventCollectionFtBurned.approverAddress)
        assertEquals("link17k4j8nfr47urlzfz6h7hzdaankpkz0dgce0xkz", eventCollectionFtBurned.proxyAddress)
    }

    @Test
    fun `given collection_disapprove_proxy tx-result, then extract 'EventCollectionProxyDisapproved' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/collection_disapprove_proxy.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventCollectionProxyDisapproved>().isNotEmpty())
        val eventCollectionFtBurned = actual.first() as EventCollectionProxyDisapproved
        assertEquals("bf365bab", eventCollectionFtBurned.contractId)
        assertEquals("link1j8jd9nps56txm2w3afcjsktrrjh0ft82eftchd", eventCollectionFtBurned.approverAddress)
        assertEquals("link1he0tp59u36mdjaw560gh8c27pz8fqms88l8nhu", eventCollectionFtBurned.proxyAddress)
    }
}
