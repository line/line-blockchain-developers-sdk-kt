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

package com.linecorp.link.developers.txresult

import com.linecorp.link.developers.txresult.adapter.TxResultAdapter
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.core.model.TxResult
import com.linecorp.link.developers.txresult.core.model.TxResultSummary
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [TestDummyApp::class]
)
class ChainPropertiesLoadingAndBeansTest {

    @Autowired
    private lateinit var chainProperties: ChainProperties

    @Autowired
    private lateinit var jsonRawTransactionResultAdapter: TxResultAdapter<String, RawTransactionResult>

    @Autowired
    private lateinit var txSummaryAdapterV1: TxResultAdapter<RawTransactionResult, TxResultSummary>

    @Autowired
    private lateinit var txEventsAdapterV1: TxResultAdapter<RawTransactionResult, Set<TransactionEvent>>

    @Autowired
    private lateinit var txResultAdapterV1: TxResultAdapter<RawTransactionResult, TxResult>


    @Test
    fun check_loaded_chainProperties_and_beans() {
        assertNotNull(chainProperties)
        assertEquals("tlink", chainProperties.bech32Hrp)
        assertEquals("tcony", chainProperties.baseCoinDenomination)
        assertFalse(chainProperties.isMainNet)

        // check beans
        assertNotNull(jsonRawTransactionResultAdapter)
        assertNotNull(txSummaryAdapterV1)
        assertNotNull(txEventsAdapterV1)
        assertNotNull(txResultAdapterV1)
    }

}
