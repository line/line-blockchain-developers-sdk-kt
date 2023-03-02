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

package com.linecorp.link.developers.client.api

const val V1 = "/v1"
const val V2 = "/v2"
const val USER_REQUESTS_PATH = "$V1/user-requests/{requestSessionToken}"

// time
const val TIME_API_PATH = "$V1/time"

// service
const val SERVICE_DETAIL_API_PATH = "$V1/services/{serviceId}"

// service-token
const val SERVICE_TOKENS_PATH = "$V1/service-tokens"
const val SERVICE_TOKEN_PATH = "$V1/service-tokens/{contractId}"
const val SERVICE_TOKEN_BY_TX_HASH_PATH = "$V1/service-tokens/by-txHash/{txHash}"
const val SERVICE_TOKEN_BURN_PATH = "$V1/service-tokens/{contractId}/burn-from"
const val SERVICE_TOKEN_MINT_PATH = "$V1/service-tokens/{contractId}/mint"
const val SERVICE_TOKEN_HOLDERS_PATH = "$SERVICE_TOKEN_PATH/holders"

// transaction
const val V1_TRANSACTION_PATH = "$V1/transactions/{txHash}"
const val V2_TRANSACTION_PATH = "$V2/transactions/{txHash}"


// memo
const val MEMO_PATH = "$V1/memos"
const val MEMO_BY_TX_HASH_PATH = "$V1/memos/{txHash}"

// wallet
const val V1_WALLET_LIST_PATH = "$V1/wallets"
const val V2_WALLET_LIST_PATH = "$V2/wallets"
const val V1_WALLET_PATH = "$V1_WALLET_LIST_PATH/{walletAddress}"
const val V2_WALLET_PATH = "$V2_WALLET_LIST_PATH/{walletAddress}"
const val WALLET_SERVICE_TOKENS_BALANCE_PATH = "$V1_WALLET_PATH/service-tokens"
const val WALLET_BASE_COIN_BALANCE_PATH = "$V1_WALLET_PATH/base-coin"
const val WALLET_SERVICE_TOKEN_BALANCE_PATH = "$WALLET_SERVICE_TOKENS_BALANCE_PATH/{contractId}"
const val WALLET_FUNGIBLE_TOKENS_BALANCE_PATH = "$V1_WALLET_PATH/item-tokens/{contractId}/fungibles"
const val WALLET_FUNGIBLE_TOKEN_BALANCE_PATH = "$WALLET_FUNGIBLE_TOKENS_BALANCE_PATH/{tokenType}"
const val V1_WALLET_TRANSACTIONS_PATH = "$V1_WALLET_PATH/transactions"
const val V2_WALLET_TRANSACTIONS_PATH = "$V2_WALLET_PATH/transactions"

const val WALLET_NON_FUNGIBLE_TOKENS_BALANCE_PATH =
    "$V1_WALLET_PATH/item-tokens/{contractId}/non-fungibles"

const val WALLET_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH =
    "$V1_WALLET_PATH/item-tokens/{contractId}/non-fungibles/{tokenType}"

const val WALLET_NON_FUNGIBLE_TOKEN_BALANCE_PATH =
    "$WALLET_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH/{tokenIndex}"

// transfer
const val SERVICE_TOKEN_TRANSFER_PATH =
    "$WALLET_SERVICE_TOKEN_BALANCE_PATH/transfer"

const val BASE_COIN_TRANSFER_PATH = "$WALLET_BASE_COIN_BALANCE_PATH/transfer"

const val WALLET_NON_FUNGIBLE_TOKEN_TRANSFER_PATH = "$WALLET_NON_FUNGIBLE_TOKEN_BALANCE_PATH/transfer"
const val WALLET_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH =
    "$V1_WALLET_PATH/item-tokens/{contractId}/non-fungibles/batch-transfer"

const val WALLET_FUNGIBLE_TOKEN_TRANSFER_PATH = "$WALLET_FUNGIBLE_TOKEN_BALANCE_PATH/transfer"

// item-tokens
const val ITEM_TOKENS_PATH = "$V1/item-tokens"
const val ITEM_TOKEN_PATH = "$V1/item-tokens/{contractId}"
const val FUNGIBLE_TOKENS_PATH = "$V1/item-tokens/{contractId}/fungibles"
const val FUNGIBLE_TOKEN_PATH = "$V1/item-tokens/{contractId}/fungibles/{tokenType}"
const val FUNGIBLE_TOKEN_HOLDERS_PATH = "$V1/item-tokens/{contractId}/fungibles/{tokenType}/holders"
const val FUNGIBLE_TOKEN_CREATE_PATH = "$V1/item-tokens/{contractId}/fungibles"
const val FUNGIBLE_TOKEN_UPDATE_PATH = "$V1/item-tokens/{contractId}/fungibles/{tokenType}"
const val FUNGIBLE_TOKEN_MINT_PATH = "$V1/item-tokens/{contractId}/fungibles/{tokenType}/mint"
const val FUNGIBLE_TOKEN_BURN_PATH = "$V1/item-tokens/{contractId}/fungibles/{tokenType}/burn"

const val NON_FUNGIBLE_TOKENS_PATH = "$V1/item-tokens/{contractId}/non-fungibles"
const val NON_FUNGIBLE_TOKEN_TYPE_PATH = "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}"
const val NON_FUNGIBLE_TOKEN_ID_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}"

const val NON_FUNGIBLE_TOKEN_MINT_PATH = "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/mint"
const val NON_FUNGIBLE_TOKEN_MULTI_MINT_PATH = "$V1/item-tokens/{contractId}/non-fungibles/multi-mint"
const val NON_FUNGIBLE_TOKEN_MULTI_RECIPIENTS_MULTI_MINT_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/multi-recipients/multi-mint"
const val NON_FUNGIBLE_TOKEN_BURN_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/burn"
const val NON_FUNGIBLE_TOKEN_TYPE_HOLDERS_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/holders"
const val NON_FUNGIBLE_TOKEN_HOLDER_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/holder"
const val NON_FUNGIBLE_TOKEN_CHILDREN_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/children"
const val NON_FUNGIBLE_TOKEN_PARENT_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/parent"

const val NON_FUNGIBLE_TOKEN_ROOT_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/root"

const val ITEM_TOKEN_NFT_MEDIA_REFRESH_STATUS_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/media-resources/{requestId}/status"
const val ITEM_TOKEN_FT_MEDIA_REFRESH_STATUS_PATH =
    "$V1/item-tokens/{contractId}/fungibles/media-resources/{requestId}/status"

const val ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_STATUS_PATH =
    "$V1/item-tokens/{contractId}/non-fungibles/thumbnails/{requestId}/status"
const val ITEM_TOKEN_FT_THUMBNAIL_REFRESH_STATUS_PATH =
    "$V1/item-tokens/{contractId}/fungibles/thumbnails/{requestId}/status"



// user api path
const val V1_USERS = "$V1/users"
const val V2_USERS = "$V2/users"
const val USER_DETAIL_PATH = "$V1_USERS/{userId}"
const val V1_USER_TRANSACTIONS_PATH = "$V1_USERS/{userId}/transactions"
const val V2_USER_TRANSACTIONS_PATH = "$V2_USERS/{userId}/transactions"
const val USER_BASE_COIN_BALANCE_PATH = "$V1_USERS/{userId}/base-coin"
const val USER_SERVICE_TOKENS_BALANCE_PATH = "$V1_USERS/{userId}/service-tokens"
const val USER_SERVICE_TOKEN_BALANCE_PATH = "$V1_USERS/{userId}/service-tokens/{contractId}"

const val USER_FUNGIBLE_TOKENS_BALANCE_PATH = "$V1_USERS/{userId}/item-tokens/{contractId}/fungibles"
const val USER_FUNGIBLE_TOKEN_BALANCE_PATH = "$V1_USERS/{userId}/item-tokens/{contractId}/fungibles/{tokenType}"

const val USER_NON_FUNGIBLE_TOKENS_BALANCE_PATH = "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles"
const val USER_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}"
const val USER_NON_FUNGIBLE_TOKEN_BALANCE_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}"

const val USER_NON_FUNGIBLE_TOKEN_BALANCES_WITH_TYPE_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles/with-type"

const val REQUEST_SESSION_TOKEN_PATH = "$V1/user-requests/{requestSessionToken}"
const val COMMIT_SESSION_TOKEN_PATH = "$V1/user-requests/{requestSessionToken}/commit"
const val ISSUE_SESSION_TOKEN_FOR_BASE_COIN_PATH = "$V1_USERS/{userId}/base-coin/request-transfer"
const val ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PATH =
    "$V1_USERS/{userId}/service-tokens/{contractId}/request-transfer"
const val ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PROXY = "$V1_USERS/{userId}/service-tokens/{contractId}/request-proxy"
const val ISSUE_SESSION_TOKEN_FOR_ITEM_TOKEN_PROXY = "$V1_USERS/{userId}/item-tokens/{contractId}/request-proxy"
const val USER_SERVICE_TOKEN_IS_PROXY_PATH = "$V1_USERS/{userId}/service-tokens/{contractId}/proxy"
const val USER_ITEM_TOKEN_IS_PROXY_PATH = "$V1_USERS/{userId}/item-tokens/{contractId}/proxy"
const val USER_SERVICE_TOKEN_TRANSFER_PATH =
    "$V1_USERS/{userId}/service-tokens/{contractId}/transfer"
const val USER_FUNGIBLE_TOKEN_TRANSFER_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/fungibles/{tokenType}/transfer"
const val USER_NON_FUNGIBLE_TOKEN_TRANSFER_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles/{tokenType}/{tokenIndex}/transfer"
const val USER_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH =
    "$V1_USERS/{userId}/item-tokens/{contractId}/non-fungibles/batch-transfer"

const val ITEM_TOKEN_FT_MEDIA_RESOURCE_REFRESH_PATH= "$V1/item-tokens/{contractId}/fungibles/media-resources"
const val ITEM_TOKEN_FT_THUMBNAIL_REFRESH_PATH= "$V1/item-tokens/{contractId}/fungibles/thumbnails"
const val ITEM_TOKEN_NFT_MEDIA_RESOURCE_REFRESH_PATH= "$V1/item-tokens/{contractId}/non-fungibles/media-resources"
const val ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_PATH= "$V1/item-tokens/{contractId}/non-fungibles/thumbnails"
