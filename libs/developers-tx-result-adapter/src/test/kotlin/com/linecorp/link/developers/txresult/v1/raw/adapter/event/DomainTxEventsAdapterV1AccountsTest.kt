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

import com.linecorp.link.developers.txresult.core.event.account.EventAccountCreated
import com.linecorp.link.developers.txresult.core.event.account.EventEmptyMsgCreated
import com.linecorp.link.developers.txresult.util.RawTransactionLoader
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainTxEventsAdapterV1AccountsTest {
    private lateinit var underTest: DomainTxEventsAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxEventsAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun `given create_account tx-result, then extract 'EventAccountCreated' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/create_account.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventAccountCreated>().isNotEmpty())
        assertEquals(
            "link16p22ehyh478fjjwc49pcm5srn2fxaezfsf25gd",
            (actual.first() as EventAccountCreated).createdAddress
        )
    }

    @Test
    fun `given empty_msg tx-result, then extract 'EventEmptyMsgCreated' `() {
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/empty_msg.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val actual = underTest.adapt(rawTransactionResult)

        assert(actual.filterIsInstance<EventEmptyMsgCreated>().isNotEmpty())
    }
}
