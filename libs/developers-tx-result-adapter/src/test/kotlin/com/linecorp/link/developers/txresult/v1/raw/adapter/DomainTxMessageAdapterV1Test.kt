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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DomainTxMessageAdapterV1Test {
    private lateinit var underTest: DomainTxMessageAdapterV1
    private lateinit var jsonRawTransactionResultAdapter: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = DomainTxMessageAdapterV1()
        jsonRawTransactionResultAdapter = JsonRawTransactionResultAdapter()
    }

    @Test
    fun test() {
        // "msg": [
        //   {
        //     "type": "collection/MsgMintFT",
        //     "value": {
        //       "from": "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq",
        //       "contractId": "61e14383",
        //       "to": "tlink147zxfhrxaxhsqljpz8raqrgnxj7f3wnthdjeky",
        //       "amount": [
        //         {
        //           "tokenId": "0000000100000000",
        //           "amount": 1000
        //         }
        //       ]
        //     }
        //   }
        // ],
        val rawTransactionInJsonText =
            RawTransactionLoader.loadRawTransactionResultInJsonText("raw-transaction/raw_transaction1.json")

        val rawTransactionResult = jsonRawTransactionResultAdapter.adapt(rawTransactionInJsonText)

        assertNotNull(rawTransactionResult)

        val txResultMessages = underTest.adapt(rawTransactionResult)

        assertEquals(1, txResultMessages.size)
        assertNotNull(txResultMessages.find { it.requestType == "collection/MsgMintFT" })
        val txResultMessage = txResultMessages.find { it.requestType == "collection/MsgMintFT" }
        val details = txResultMessage?.details as Map<String, String>
        assertEquals(details["from"], "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq")
        assertEquals(details["contractId"], "61e14383")
        assertEquals(details["to"], "tlink147zxfhrxaxhsqljpz8raqrgnxj7f3wnthdjeky")
        val amounts = (details["amount"] as List<Map<String, Any>>)
        assertEquals(amounts[0]["tokenId"], "0000000100000000")
        assertEquals(amounts[0]["amount"], 1000)
    }
}
