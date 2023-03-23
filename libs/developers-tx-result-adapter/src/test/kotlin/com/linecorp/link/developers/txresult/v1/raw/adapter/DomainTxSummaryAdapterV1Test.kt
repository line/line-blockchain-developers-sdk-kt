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

package com.linecorp.link.developers.txresult.v1.raw.adapter

import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DomainTxSummaryAdapterV1Test {
    private lateinit var underTest: DomainTxSummaryAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxSummaryAdapterV1("tlink")
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun test() {

        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/raw_transaction1.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val txResultSummary = underTest.adapt(rawTransactionResult)

        assertEquals(235816, txResultSummary.height)
        assertEquals("61AB8A054D47CA05E4ABE591B929282CBCD7DACD5A4C8259020C566F0EC186BE", txResultSummary.txHash)
        assertEquals(0, txResultSummary.txIndex)
        assertEquals("", txResultSummary.result.codeSpace)
        assertEquals(0, txResultSummary.result.code)

        assertTrue(rawTransactionResult.getSignerAddresses("tlink")
            .contains("tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq"))
    }
}
