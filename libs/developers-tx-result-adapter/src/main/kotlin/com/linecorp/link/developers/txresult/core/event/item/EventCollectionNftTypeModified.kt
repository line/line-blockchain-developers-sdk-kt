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

package com.linecorp.link.developers.txresult.core.event.item

import com.linecorp.link.developers.txresult.util.ItemTokenTypeUtil

data class EventCollectionNftTypeModified(
override val contractId: String,
    val tokenType: String,
    val tokenAttributes: Set<CollectionAttribute>,
    val modifierAddress: String,
) : ItemNonFungibleTokenEvent(
    contractId = contractId,
    tokenTypes = listOf(ItemTokenTypeUtil.tokenType(tokenType)),
)
