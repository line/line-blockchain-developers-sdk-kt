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

import com.linecorp.link.developers.txresult.core.event.item.ItemFungibleTokenEvent
import com.linecorp.link.developers.txresult.core.event.item.ItemNonFungibleTokenEvent
import com.linecorp.link.developers.txresult.core.event.item.ItemTokenEvent
import com.linecorp.link.developers.txresult.core.event.token.ServiceTokenEvent
import com.linecorp.link.developers.txresult.core.model.AssetType
import com.linecorp.link.developers.txresult.core.model.ContractIdentifier
import com.linecorp.link.developers.txresult.core.model.TransactionEvent

object TransactionEventUtil {
    fun findContractIdentifier(event: TransactionEvent): ContractIdentifier? {
        return when (event) {
            is ItemFungibleTokenEvent -> ContractIdentifier(
                contractId = event.contractId,
                assetType = AssetType.ITEM_TOKEN_FUNGIBLE
            )
            is ItemNonFungibleTokenEvent -> ContractIdentifier(
                contractId = event.contractId,
                assetType = AssetType.ITEM_TOKEN_NON_FUNGIBLE
            )
            is ItemTokenEvent -> ContractIdentifier(
                contractId = event.contractId,
                assetType = AssetType.ITEM_TOKEN
            )
            is ServiceTokenEvent -> ContractIdentifier(
                contractId = event.contractId,
                assetType = AssetType.SERVICE_TOKEN
            )
            else -> null
        }
    }
}
