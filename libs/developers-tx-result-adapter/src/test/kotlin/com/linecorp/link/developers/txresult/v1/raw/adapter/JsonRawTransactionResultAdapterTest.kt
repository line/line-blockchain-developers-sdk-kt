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

import com.linecorp.link.developers.txresult.helper.RawTransactionLoader
import kotlin.test.assertTrue
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class JsonRawTransactionResultAdapterTest {
    private lateinit var underTest: JsonRawTransactionResultAdapter

    @BeforeEach
    fun setUp() {
        underTest = JsonRawTransactionResultAdapter()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "./raw-transaction/coin_transfer.json",
            "./raw-transaction/collection_approve.json",
            "./raw-transaction/collection_approve_proxy.json",
            "./raw-transaction/collection_attach_from_nft.json",
            "./raw-transaction/collection_attach_nft.json",
            "./raw-transaction/collection_burn_ft.json",
            "./raw-transaction/collection_burn_ft_from.json",
            "./raw-transaction/collection_burn_nft.json",
            "./raw-transaction/collection_burn_nft_from.json",
            "./raw-transaction/collection_create.json",
            "./raw-transaction/collection_detach_from_nft.json",
            "./raw-transaction/collection_detach_nft.json",
            "./raw-transaction/collection_dis_approve.json",
            "./raw-transaction/collection_disapprove_proxy.json",
            "./raw-transaction/collection_issue_ft.json",
            "./raw-transaction/collection_issue_nft.json",
            "./raw-transaction/collection_mint_ft.json",
            "./raw-transaction/collection_modify.json",
            "./raw-transaction/collection_modify_ft.json",
            "./raw-transaction/collection_modify_nft.json",
            "./raw-transaction/collection_modify_nft_type.json",
            "./raw-transaction/collection_msgMintNft.json",
            "./raw-transaction/collection_transfer_from_ft.json",
            "./raw-transaction/collection_transfer_from_nft.json",
            "./raw-transaction/collection_transfer_ft.json",
            "./raw-transaction/collection_transfer_nft.json",
            "./raw-transaction/create_account.json",
            "./raw-transaction/empty_msg.json",
            "./raw-transaction/raw_transaction1.json",
            "./raw-transaction/token_approve.json",
            "./raw-transaction/token_burn.json",
            "./raw-transaction/token_burn_from.json",
            "./raw-transaction/token_issue.json",
            "./raw-transaction/token_mint.json",
            "./raw-transaction/token_modify.json",
            "./raw-transaction/token_transfer.json",
            "./raw-transaction/token_transfer_from.json",
        ]
    )
    fun test(txResultFilePath: String) {
        val rawTransactionInJsonText = RawTransactionLoader.loadRawTransactionResultInJsonText(txResultFilePath)
        val rawTransactionResult = underTest.adapt(rawTransactionInJsonText)
        assertNotNull(rawTransactionResult)
        assertTrue(rawTransactionResult.txhash.isNotBlank())
    }


}
