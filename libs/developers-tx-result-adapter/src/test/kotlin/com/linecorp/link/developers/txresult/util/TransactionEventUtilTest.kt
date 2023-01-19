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

package com.linecorp.link.developers.txresult.util

import com.linecorp.link.developers.txresult.core.event.UnknownTransactionEvent
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionCreated
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionFtMinted
import com.linecorp.link.developers.txresult.core.event.item.EventCollectionNftMinted
import com.linecorp.link.developers.txresult.core.model.AssetType
import com.linecorp.link.developers.txresult.core.model.ContractIdentifier
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TransactionEventUtilTest {

    @Test
    fun `findContractIdentifier()`() {
        val contractIdentifier = TransactionEventUtil.findContractIdentifier(
            EventCollectionCreated(
                contractId = "test1234",
                name = "test contract",
                creatorAddress = "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq"
            )
        )

        assertEquals(ContractIdentifier(AssetType.ITEM_TOKEN, "test1234"), contractIdentifier)
    }

    @Test
    fun `findContractIdentifier(), given ItemFungibleTokenEvent`() {
        val contractIdentifier = TransactionEventUtil.findContractIdentifier(
            EventCollectionFtMinted(
                contractId = "test1234",
                tokenId = "0000000100000000",
                amount = "100",
                toAddress = "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq",
                minterAddress = "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq"
            )
        )

        assertEquals(ContractIdentifier(AssetType.ITEM_TOKEN_FUNGIBLE, "test1234"), contractIdentifier)
    }

    @Test
    fun `findContractIdentifier(), given ItemNonFungibleTokenEvent`() {
        val contractIdentifier = TransactionEventUtil.findContractIdentifier(
            EventCollectionNftMinted(
                contractId = "test1234",
                tokenIds = listOf("1000000100000000"),
                toAddress = "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq",
                minterAddress = "tlink1fr9mpexk5yq3hu6jc0npajfsa0x7tl427fuveq"
            )
        )

        assertEquals(ContractIdentifier(AssetType.ITEM_TOKEN_NON_FUNGIBLE, "test1234"), contractIdentifier)
    }

    @Test
    fun `findContractIdentifier(), given unknownEvent`() {
        val contractIdentifier = TransactionEventUtil.findContractIdentifier(
            UnknownTransactionEvent(
                type = "Unknown-tx-message",
                attributes = emptyList()
            )
        )

        assertNull(contractIdentifier)
    }

}
