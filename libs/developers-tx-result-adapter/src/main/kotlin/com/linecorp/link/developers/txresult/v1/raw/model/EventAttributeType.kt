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

package com.linecorp.link.developers.txresult.v1.raw.model

enum class EventAttributeType(vararg val values: String) {
    Amount("amount"),
    From("from"),
    Approver("approver"),
    Sender("sender"),
    To("to"),
    Proxy("proxy"),
    TokenId("tokenId", "token_id"),
    ParentTokenId("to_token_id", "toTokenId"),
    ExParentTokenId("from_token_id", "fromTokenId", "old_root_token_id", "oldRootTokenId"),
    NewRootTokenId("new_root_token_id", "new_rootTokenId"),
    TokenType("tokenType", "token_type"),
    ContractId("contract_id", "contractId"),
    CreateAccountTarget("create_account_target"),
    Recipient("recipient"),
    Owner("owner"),
    Name("name"),
    Meta("meta"),
    Symbol("symbol"),
    Decimals("decimals")
}
