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

@file:Suppress("unused")

package com.linecorp.link.developers.client.api

import com.linecorp.link.developers.client.request.BatchTransferNonFungibleOfUserRequest
import com.linecorp.link.developers.client.request.BatchTransferNonFungibleRequest
import com.linecorp.link.developers.client.request.BurnFromServiceTokenRequest
import com.linecorp.link.developers.client.request.COMMIT_SESSION_TOKEN_PATH
import com.linecorp.link.developers.client.request.CreateItemTokenCollectionRequest
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKENS_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_BURN_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_CREATE_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_HOLDERS_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_MINT_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_PATH
import com.linecorp.link.developers.client.request.FUNGIBLE_TOKEN_UPDATE_PATH
import com.linecorp.link.developers.client.request.FungibleTokenBurnRequest
import com.linecorp.link.developers.client.request.FungibleTokenCreateUpdateRequest
import com.linecorp.link.developers.client.request.FungibleTokenMintRequest
import com.linecorp.link.developers.client.request.ISSUE_SESSION_TOKEN_FOR_ITEM_TOKEN_PROXY
import com.linecorp.link.developers.client.request.ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PATH
import com.linecorp.link.developers.client.request.ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PROXY
import com.linecorp.link.developers.client.request.ITEM_TOKENS_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_FT_MEDIA_REFRESH_STATUS_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_FT_MEDIA_RESOURCE_REFRESH_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_FT_THUMBNAIL_REFRESH_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_FT_THUMBNAIL_REFRESH_STATUS_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_NFT_MEDIA_REFRESH_STATUS_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_NFT_MEDIA_RESOURCE_REFRESH_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_STATUS_PATH
import com.linecorp.link.developers.client.request.ITEM_TOKEN_PATH
import com.linecorp.link.developers.client.request.IssueServiceTokenRequest
import com.linecorp.link.developers.client.request.MEMO_BY_TX_HASH_PATH
import com.linecorp.link.developers.client.request.MEMO_PATH
import com.linecorp.link.developers.client.request.MemoRequest
import com.linecorp.link.developers.client.request.MintServiceTokenRequest
import com.linecorp.link.developers.client.request.MultiMintItemTokenWithMultiRecipientsRequest
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKENS_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_BURN_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_CHILDREN_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_HOLDER_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_ID_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_MINT_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_MULTI_MINT_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_MULTI_RECIPIENTS_MULTI_MINT_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_PARENT_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_ROOT_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_TYPE_HOLDERS_PATH
import com.linecorp.link.developers.client.request.NON_FUNGIBLE_TOKEN_TYPE_PATH
import com.linecorp.link.developers.client.request.NonFungibleTokenBurnRequest
import com.linecorp.link.developers.client.request.NonFungibleTokenCreateUpdateRequest
import com.linecorp.link.developers.client.request.NonFungibleTokenItemTokenAttachRequest
import com.linecorp.link.developers.client.request.NonFungibleTokenItemTokenDetachRequest
import com.linecorp.link.developers.client.request.NonFungibleTokenMintRequest
import com.linecorp.link.developers.client.request.NonFungibleTokenMultiMintRequest
import com.linecorp.link.developers.client.request.OrderBy
import com.linecorp.link.developers.client.request.REQUEST_SESSION_TOKEN_PATH
import com.linecorp.link.developers.client.request.SERVICE_DETAIL_API_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKENS_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_BURN_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_BY_TX_HASH_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_HOLDERS_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_MINT_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_PATH
import com.linecorp.link.developers.client.request.SERVICE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.TIME_API_PATH
import com.linecorp.link.developers.client.request.TX_MESSAGES_PATH
import com.linecorp.link.developers.client.request.TransferFungibleTokenRequest
import com.linecorp.link.developers.client.request.TransferNonFungibleOfUserRequest
import com.linecorp.link.developers.client.request.TransferNonFungibleRequest
import com.linecorp.link.developers.client.request.TransferServiceTokenRequest
import com.linecorp.link.developers.client.request.TransferTokenOfUserRequest
import com.linecorp.link.developers.client.request.USER_DETAIL_PATH
import com.linecorp.link.developers.client.request.USER_FUNGIBLE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_FUNGIBLE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_FUNGIBLE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.USER_ITEM_TOKEN_IS_PROXY_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKEN_BALANCES_WITH_TYPE_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH
import com.linecorp.link.developers.client.request.USER_NON_FUNGIBLE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.USER_SERVICE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_SERVICE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.request.USER_SERVICE_TOKEN_IS_PROXY_PATH
import com.linecorp.link.developers.client.request.USER_SERVICE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.UpdateFungibleTokenResourceRequest
import com.linecorp.link.developers.client.request.UpdateNonFungibleTokenResourceRequest
import com.linecorp.link.developers.client.request.UpdateServiceTokenRequest
import com.linecorp.link.developers.client.request.UserAssetProxyRequest
import com.linecorp.link.developers.client.request.UserServiceTokenTransferRequest
import com.linecorp.link.developers.client.request.V1_TRANSACTION_PATH
import com.linecorp.link.developers.client.request.V1_USER_TRANSACTIONS_PATH
import com.linecorp.link.developers.client.request.V1_WALLET_LIST_PATH
import com.linecorp.link.developers.client.request.V1_WALLET_PATH
import com.linecorp.link.developers.client.request.V1_WALLET_TRANSACTIONS_PATH
import com.linecorp.link.developers.client.request.V2_TRANSACTION_PATH
import com.linecorp.link.developers.client.request.V2_USER_TRANSACTIONS_PATH
import com.linecorp.link.developers.client.request.V2_WALLET_TRANSACTIONS_PATH
import com.linecorp.link.developers.client.request.WALLET_FUNGIBLE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.WALLET_FUNGIBLE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.request.WALLET_FUNGIBLE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.WALLET_NON_FUNGIBLE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.WALLET_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH
import com.linecorp.link.developers.client.request.WALLET_NON_FUNGIBLE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.request.WALLET_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH
import com.linecorp.link.developers.client.request.WALLET_NON_FUNGIBLE_TOKEN_TRANSFER_PATH
import com.linecorp.link.developers.client.request.WALLET_SERVICE_TOKENS_BALANCE_PATH
import com.linecorp.link.developers.client.request.WALLET_SERVICE_TOKEN_BALANCE_PATH
import com.linecorp.link.developers.client.response.FungibleBalance
import com.linecorp.link.developers.client.response.FungibleToken
import com.linecorp.link.developers.client.response.FungibleTokenHolder
import com.linecorp.link.developers.client.response.GenericResponse
import com.linecorp.link.developers.client.response.ItemToken
import com.linecorp.link.developers.client.response.ItemTokenType
import com.linecorp.link.developers.client.response.ItemTokensMediaResourceStatus
import com.linecorp.link.developers.client.response.Memo
import com.linecorp.link.developers.client.response.NonFungibleBalance
import com.linecorp.link.developers.client.response.NonFungibleBalanceWithTypeAmountList
import com.linecorp.link.developers.client.response.NonFungibleId
import com.linecorp.link.developers.client.response.NonFungibleTokenHolder
import com.linecorp.link.developers.client.response.NonFungibleTokenType
import com.linecorp.link.developers.client.response.NonFungibleTokenTypeHolder
import com.linecorp.link.developers.client.response.ProxyStatus
import com.linecorp.link.developers.client.response.RequestSession
import com.linecorp.link.developers.client.response.RequestSessionStatus
import com.linecorp.link.developers.client.response.ServiceDetail
import com.linecorp.link.developers.client.response.ServiceToken
import com.linecorp.link.developers.client.response.ServiceTokenBalance
import com.linecorp.link.developers.client.response.ServiceTokenHolder
import com.linecorp.link.developers.client.response.SimpleServiceToken
import com.linecorp.link.developers.client.response.TokenIndex
import com.linecorp.link.developers.client.response.TransactionResponse
import com.linecorp.link.developers.client.response.TxMessageListResponse
import com.linecorp.link.developers.client.response.TxResultResponse
import com.linecorp.link.developers.client.response.UpdateTokenMediaRefreshResponse
import com.linecorp.link.developers.client.response.UserIdAddress
import com.linecorp.link.developers.client.response.WalletResponse
import com.linecorp.link.developers.client.util.toEpochMilli
import com.linecorp.link.developers.txresult.core.model.TxResult
import java.time.LocalDateTime
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

@Suppress("TooManyFunctions")
interface ApiClient {
    @GET(TIME_API_PATH)
    suspend fun time(): GenericResponse<Unit>

    /**
     * Retrieve service information
     */
    @GET(SERVICE_DETAIL_API_PATH)
    suspend fun serviceDetail(@Path("serviceId") serviceId: String): GenericResponse<ServiceDetail>

    /**
     *  Issue a service token
     */
    @POST(SERVICE_TOKENS_PATH)
    suspend fun issueServiceToken(
        @Body request: IssueServiceTokenRequest
    ): GenericResponse<TransactionResponse>

    @GET(SERVICE_TOKEN_BY_TX_HASH_PATH)
    suspend fun serviceTokenByTxHash(
        @Path("txHash") txHash: String,
        @Query("isOnlyContractId") isOnlyContractId: Boolean = false,
    ): GenericResponse<SimpleServiceToken>

    /**
     * List all service tokens
     */
    @GET(SERVICE_TOKENS_PATH)
    suspend fun serviceTokens(): GenericResponse<Collection<ServiceToken>>

    /**
     * Retrieve service token information
     */
    @GET(SERVICE_TOKEN_PATH)
    suspend fun serviceToken(@Path("contractId") contractId: String): GenericResponse<ServiceToken>

    /**
     * List all service token holders
     */
    @GET(SERVICE_TOKEN_HOLDERS_PATH)
    suspend fun serviceTokenHolders(
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<ServiceTokenHolder>>

    /**
     * Update service token information
     */
    @PUT(SERVICE_TOKEN_PATH)
    suspend fun updateServiceToken(
        @Path("contractId") contractId: String,
        @Body request: UpdateServiceTokenRequest
    ): GenericResponse<TransactionResponse>

    /**
     * Mint a service token
     */
    @POST(SERVICE_TOKEN_MINT_PATH)
    suspend fun mintServiceToken(
        @Path("contractId") contractId: String,
        @Body request: MintServiceTokenRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Burn a service token
     */
    @POST(SERVICE_TOKEN_BURN_PATH)
    suspend fun burnFromServiceToken(
        @Path("contractId") contractId: String,
        @Body requestFrom: BurnFromServiceTokenRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve transaction detail
     */
    @GET(V1_TRANSACTION_PATH)
    suspend fun transactionV1(@Path("txHash") txHash: String): GenericResponse<TxResultResponse>

    @GET(V2_TRANSACTION_PATH)
    suspend fun transactionV2(@Path("txHash") txHash: String): GenericResponse<TxResult>

    // memo
    /**
     * Save the text
     */
    @Deprecated("This API will be removed soon")
    @POST(MEMO_PATH)
    suspend fun saveMemo(@Body request: MemoRequest): GenericResponse<TransactionResponse>

    /**
     * Query memo of a transaction by tx-hash
     */
    @Deprecated("This API will be removed soon")
    @GET(MEMO_BY_TX_HASH_PATH)
    suspend fun queryMemo(@Path("txHash") txHash: String): GenericResponse<Memo>

    // wallet
    /**
     * List all service wallets
     */
    @GET(V1_WALLET_LIST_PATH)
    suspend fun wallets(): GenericResponse<Collection<WalletResponse>>

    /**
     * Retrieve service wallet information
     */
    @GET(V1_WALLET_PATH)
    suspend fun wallet(@Path("walletAddress") walletAddress: String): GenericResponse<WalletResponse>

    /**
     * Retrieve service wallet transaction history
     * By default 1 day transactions of given wallet will be returned
     */
    @GET(V1_WALLET_TRANSACTIONS_PATH)
    suspend fun transactionOfWalletV1(
        @Path("walletAddress") walletAddress: String,
        @Query("after") after: Long? = LocalDateTime.now().minusDays(1).toEpochMilli(),
        @Query("before") before: Long? = LocalDateTime.now().toEpochMilli(),
        @Query("msgType") msgType: String? = null,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TxResultResponse>>

    @GET(V2_WALLET_TRANSACTIONS_PATH)
    suspend fun transactionOfWalletV2(
        @Path("walletAddress") walletAddress: String,
        @Query("after") after: Long? = LocalDateTime.now().minusDays(1).toEpochMilli(),
        @Query("before") before: Long? = LocalDateTime.now().toEpochMilli(),
        @Query("msgType") msgType: String? = null,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TxResult>>

    /**
     * Retrieve balance of all service tokens (service wallet)
     */
    @GET(WALLET_SERVICE_TOKENS_BALANCE_PATH)
    suspend fun serviceTokenBalancesOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<ServiceTokenBalance>>

    /**
     * Retrieve balance of a specific service token (service wallet)
     */
    @GET(WALLET_SERVICE_TOKEN_BALANCE_PATH)
    suspend fun serviceTokenBalanceOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
    ): GenericResponse<ServiceTokenBalance>

    /**
     * Transfer service tokens (service wallet)
     */
    @POST(SERVICE_TOKEN_TRANSFER_PATH)
    suspend fun transferServiceToken(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Body request: TransferServiceTokenRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve balance of all fungibles (service wallet)
     */
    @GET(WALLET_FUNGIBLE_TOKENS_BALANCE_PATH)
    suspend fun fungibleTokensBalanceOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<FungibleBalance>>

    /**
     * Retrieve balance of a specific fungible (service wallet)
     */
    @GET(WALLET_FUNGIBLE_TOKEN_BALANCE_PATH)
    suspend fun fungibleTokenBalanceOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
    ): GenericResponse<FungibleBalance>

    /**
     * Transfer a fungible (service wallet)
     */
    @POST(WALLET_FUNGIBLE_TOKEN_TRANSFER_PATH)
    suspend fun transferFungibleTokenOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: TransferFungibleTokenRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve balance of all non-fungibles (service wallet)
     */
    @GET(WALLET_NON_FUNGIBLE_TOKENS_BALANCE_PATH)
    suspend fun nonFungibleTokenBalancesOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<NonFungibleBalance>>

    /**
     * Retrieve balance of specific type of non-fungibles (service wallet)
     */
    @GET(WALLET_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH)
    suspend fun nonFungibleTokenBalancesOfWalletByType(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TokenIndex>>

    /**
     * Retrieve balance of a specific non-fungible (service wallet)
     */
    @GET(WALLET_NON_FUNGIBLE_TOKEN_BALANCE_PATH)
    suspend fun nonFungibleTokenBalanceOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
    ): GenericResponse<TokenIndex>

    /**
     * Transfer a non-fungible (service wallet)
     */
    @POST(WALLET_NON_FUNGIBLE_TOKEN_TRANSFER_PATH)
    suspend fun transferNonFungibleTokenOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: TransferNonFungibleRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Batch transfer non-fungibles (service wallet)
     */
    @POST(WALLET_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH)
    suspend fun batchTransferNonFungibleTokenOfWallet(
        @Path("walletAddress") walletAddress: String,
        @Path("contractId") contractId: String,
        @Body request: BatchTransferNonFungibleRequest,
    ): GenericResponse<TransactionResponse>

    // item-tokens
    @POST(ITEM_TOKENS_PATH)
    suspend fun createItemTokenCollection(
        @Body request: CreateItemTokenCollectionRequest
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve item token contract list
     */
    @GET(ITEM_TOKENS_PATH)
    suspend fun itemTokens(): GenericResponse<Collection<ItemToken>>

    /**
     * Retrieve item token contract information
     */
    @GET(ITEM_TOKEN_PATH)
    suspend fun itemToken(
        @Path("contractId") contractId: String,
    ): GenericResponse<ItemToken>

    // fungibles
    /**
     * List all fungibles
     */
    @GET(FUNGIBLE_TOKENS_PATH)
    suspend fun fungibleTokens(
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<FungibleToken>>

    /**
     * Retrieve fungible information
     */
    @GET(FUNGIBLE_TOKEN_PATH)
    suspend fun fungibleToken(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
    ): GenericResponse<FungibleToken>

    /**
     * Retrieve all fungible holders
     */
    @GET(FUNGIBLE_TOKEN_HOLDERS_PATH)
    suspend fun fungibleTokenHolders(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<FungibleTokenHolder>>

    /**
     * Create a fungible
     */
    @POST(FUNGIBLE_TOKEN_CREATE_PATH)
    suspend fun createFungible(
        @Path("contractId") contractId: String,
        @Body request: FungibleTokenCreateUpdateRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Update fungible information
     */
    @PUT(FUNGIBLE_TOKEN_UPDATE_PATH)
    suspend fun updateFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: FungibleTokenCreateUpdateRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Mint a fungible
     */
    @POST(FUNGIBLE_TOKEN_MINT_PATH)
    suspend fun mintFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: FungibleTokenMintRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Burn a fungible
     */
    @POST(FUNGIBLE_TOKEN_BURN_PATH)
    suspend fun burnFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: FungibleTokenBurnRequest
    ): GenericResponse<TransactionResponse>

    // non-fungibles
    /**
     * List all non-fungibles
     */
    @GET(NON_FUNGIBLE_TOKENS_PATH)
    suspend fun nonFungibleTokenTypes(
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<ItemTokenType>>

    /**
     * Create a non-fungible
     */
    @POST(NON_FUNGIBLE_TOKENS_PATH)
    suspend fun createNonFungibleType(
        @Path("contractId") contractId: String,
        @Body request: NonFungibleTokenCreateUpdateRequest
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve a non-fungible token type
     */
    @GET(NON_FUNGIBLE_TOKEN_TYPE_PATH)
    suspend fun nonFungibleTokenType(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<NonFungibleTokenType>

    /**
     * Update a non-fungible token type
     */
    @PUT(NON_FUNGIBLE_TOKEN_TYPE_PATH)
    suspend fun updateNonFungibleTokenType(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: NonFungibleTokenCreateUpdateRequest
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve non-fungible information
     */
    @GET(NON_FUNGIBLE_TOKEN_ID_PATH)
    suspend fun nonFungibleToken(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
    ): GenericResponse<NonFungibleId>

    /**
     * Update non-fungible information
     */
    @PUT(NON_FUNGIBLE_TOKEN_ID_PATH)
    suspend fun updateNonFungibleToken(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: NonFungibleTokenCreateUpdateRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Mint a non-fungible
     */
    @POST(NON_FUNGIBLE_TOKEN_MINT_PATH)
    suspend fun mintNonFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: NonFungibleTokenMintRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Retrieve holders of a specific non-fungible token type
     */
    @GET(NON_FUNGIBLE_TOKEN_TYPE_HOLDERS_PATH)
    suspend fun nonFungibleTokenTypeHolders(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<NonFungibleTokenTypeHolder>>

    /**
     * Retrieve the holder of a specific non-fungible
     */
    @GET(NON_FUNGIBLE_TOKEN_HOLDER_PATH)
    suspend fun nonFungibleTokenHolder(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String
    ): GenericResponse<NonFungibleTokenHolder>

    /**
     * Mint multiple non-fungibles
     */
    @POST(NON_FUNGIBLE_TOKEN_MULTI_MINT_PATH)
    suspend fun multiMintNonFungible(
        @Path("contractId") contractId: String,
        @Body request: NonFungibleTokenMultiMintRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Mint multiple non-fungibles to multi recipients
     */
    @POST(NON_FUNGIBLE_TOKEN_MULTI_RECIPIENTS_MULTI_MINT_PATH)
    suspend fun multiMintNonFungibleToMultiRecipients(
        @Path("contractId") contractId: String,
        @Body request: MultiMintItemTokenWithMultiRecipientsRequest,
    ): GenericResponse<TransactionResponse>


    /**
     * Burn a non-fungible
     */
    @POST(NON_FUNGIBLE_TOKEN_BURN_PATH)
    suspend fun burnNonFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: NonFungibleTokenBurnRequest
    ): GenericResponse<TransactionResponse>

    /**
     * List the children of a non-fungible
     */
    @GET(NON_FUNGIBLE_TOKEN_CHILDREN_PATH)
    suspend fun nonFungibleTokenChildren(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<NonFungibleId>>

    /**
     * Retrieve the parent of a non-fungible
     */
    @GET(NON_FUNGIBLE_TOKEN_PARENT_PATH)
    suspend fun nonFungibleTokenParent(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
    ): GenericResponse<NonFungibleId>

    /**
     * Retrieve the root of a non-fungible
     */
    @GET(NON_FUNGIBLE_TOKEN_ROOT_PATH)
    suspend fun nonFungibleTokenRoot(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
    ): GenericResponse<NonFungibleId>

    /**
     * Attach a non-fungible to another
     */
    @POST(NON_FUNGIBLE_TOKEN_PARENT_PATH)
    suspend fun attachNonFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: NonFungibleTokenItemTokenAttachRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Detach a non-fungible from the parent
     */
    @HTTP(method = "DELETE", hasBody = true, path = NON_FUNGIBLE_TOKEN_PARENT_PATH)
    suspend fun detachNonFungible(
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: NonFungibleTokenItemTokenDetachRequest,
    ): GenericResponse<TransactionResponse>

    @GET(ITEM_TOKEN_FT_MEDIA_REFRESH_STATUS_PATH)
    suspend fun fungibleItemTokensMediaResourceStatus(
        @Path("contractId") contractId: String,
        @Path("requestId") requestId: String,
    ): GenericResponse<ItemTokensMediaResourceStatus>

    @GET(ITEM_TOKEN_NFT_MEDIA_REFRESH_STATUS_PATH)
    suspend fun nonFungibleItemTokensMediaResourceStatus(
        @Path("contractId") contractId: String,
        @Path("requestId") requestId: String,
    ): GenericResponse<ItemTokensMediaResourceStatus>

    @GET(ITEM_TOKEN_FT_THUMBNAIL_REFRESH_STATUS_PATH)
    suspend fun fungibleItemTokensThumbnailStatus(
        @Path("contractId") contractId: String,
        @Path("requestId") requestId: String,
    ): GenericResponse<ItemTokensMediaResourceStatus>

    @GET(ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_STATUS_PATH)
    suspend fun nonFungibleItemTokensThumbnailStatus(
        @Path("contractId") contractId: String,
        @Path("requestId") requestId: String,
    ): GenericResponse<ItemTokensMediaResourceStatus>

    // user api
    /**
     * Retrieve user information
     */
    @GET(USER_DETAIL_PATH)
    suspend fun userDetail(
        @Path("userId") userId: String,
    ): GenericResponse<UserIdAddress>

    /**
     * Retrieve user wallet transaction history
     */
    @GET(V1_USER_TRANSACTIONS_PATH)
    suspend fun transactionOfUserV1(
        @Path("userId") userId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TxResultResponse>>

    /**
     * Retrieve user wallet transaction history
     */
    @GET(V2_USER_TRANSACTIONS_PATH)
    suspend fun transactionOfUserV2(
        @Path("userId") userId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TxResult>>

    /**
     * Retrieve balance of all service tokens (user wallet)
     */
    @GET(USER_SERVICE_TOKENS_BALANCE_PATH)
    suspend fun serviceTokenBalancesOfUser(
        @Path("userId") userId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<ServiceTokenBalance>>

    /**
     * Retrieve balance of a specific service token (user wallet)
     */
    @GET(USER_SERVICE_TOKEN_BALANCE_PATH)
    suspend fun serviceTokenBalanceOfUser(
        @Path("userId") userId: String,
        contractId: String
    ): GenericResponse<ServiceTokenBalance>

    /**
     * Retrieve balance of all fungibles (user wallet)
     */
    @GET(USER_FUNGIBLE_TOKENS_BALANCE_PATH)
    suspend fun fungibleTokenBalancesOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<FungibleBalance>>

    /**
     * Retrieve balance of a specific fungible (user wallet)
     */
    @GET(USER_FUNGIBLE_TOKEN_BALANCE_PATH)
    suspend fun fungibleTokenBalanceOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        tokenType: String
    ): GenericResponse<FungibleBalance>

    /**
     * Retrieve balance of all non-fungibles (user wallet)
     */
    @GET(USER_NON_FUNGIBLE_TOKENS_BALANCE_PATH)
    suspend fun nonFungibleTokenBalancesOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<NonFungibleBalance>>

    /**
     * Retrieve balance of specific type of non-fungibles (user wallet)
     */
    @GET(USER_NON_FUNGIBLE_TOKEN_BALANCES_BY_TYPE_PATH)
    suspend fun nonFungibleTokenBalancesOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<Collection<TokenIndex>>

    /**
     * Retrieve balance of a specific non-fungible (service wallet)
     */
    @GET(USER_NON_FUNGIBLE_TOKEN_BALANCE_PATH)
    suspend fun nonFungibleTokenBalanceOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
    ): GenericResponse<TokenIndex>

    /**
     * Retrieve the status of a session token
     */
    @GET(REQUEST_SESSION_TOKEN_PATH)
    suspend fun requestSessionToken(
        @Path("requestSessionToken") requestSessionToken: String,
    ): GenericResponse<RequestSessionStatus>

    /**
     * Commit a transaction signed by a user wallet
     */
    @POST(COMMIT_SESSION_TOKEN_PATH)
    suspend fun commitRequestSession(
        @Path("requestSessionToken") requestSessionToken: String,
    ): GenericResponse<TransactionResponse>

    /**
     * Issue session token for service-token-proxy setting
     */
    @POST(ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PROXY)
    suspend fun issueSessionTokenForServiceTokenProxy(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("requestType") requestType: String,
        @Body requestUser: UserAssetProxyRequest,
    ): GenericResponse<RequestSession>

    /**
     * Issue a session token for service token transfer
     */
    @POST(ISSUE_SESSION_TOKEN_FOR_SERVICE_TOKEN_PATH)
    suspend fun issueSessionTokenForServiceTokenTransfer(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("requestType") requestType: String,
        @Body request: UserServiceTokenTransferRequest,
    ): GenericResponse<RequestSession>

    /**
     * Issue a session token for proxy setting
     */
    @POST(ISSUE_SESSION_TOKEN_FOR_ITEM_TOKEN_PROXY)
    suspend fun issueSessionTokenForItemTokenProxy(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("requestType") requestType: String,
        @Body requestUser: UserAssetProxyRequest
    ): GenericResponse<RequestSession>

    /**
     * Retrieve whether the proxy set or not for service-token
     */
    @GET(USER_SERVICE_TOKEN_IS_PROXY_PATH)
    suspend fun isProxyOfServiceToken(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
    ): GenericResponse<ProxyStatus>


    /**
     * Retrieve whether the proxy set or not
     */
    @GET(USER_ITEM_TOKEN_IS_PROXY_PATH)
    suspend fun isProxyOfItemToken(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
    ): GenericResponse<ProxyStatus>

    @POST(USER_SERVICE_TOKEN_TRANSFER_PATH)
    suspend fun transferServiceTokenOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Body request: TransferTokenOfUserRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Transfer a fungible (user wallet)
     */
    @POST(USER_FUNGIBLE_TOKEN_TRANSFER_PATH)
    suspend fun transferFungibleTokenOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Body request: TransferTokenOfUserRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Transfer a non-fungible (user wallet)
     */
    @POST(USER_NON_FUNGIBLE_TOKEN_TRANSFER_PATH)
    suspend fun transferNonFungibleTokenOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Path("tokenType") tokenType: String,
        @Path("tokenIndex") tokenIndex: String,
        @Body request: TransferNonFungibleOfUserRequest,
    ): GenericResponse<TransactionResponse>

    /**
     * Batch transfer non-fungibles (user wallet)
     */
    @POST(USER_NON_FUNGIBLE_TOKEN_BATCH_TRANSFER_PATH)
    suspend fun batchTransferNonFungibleTokenOfUser(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Body request: BatchTransferNonFungibleOfUserRequest,
    ): GenericResponse<TransactionResponse>

    @GET(USER_NON_FUNGIBLE_TOKEN_BALANCES_WITH_TYPE_PATH)
    suspend fun nonFungibleBalancesWithType(
        @Path("userId") userId: String,
        @Path("contractId") contractId: String,
        @Query("limit") limit: Int = 10,
        @Query("pageToken") pageToken: String?,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC,
    ): GenericResponse<NonFungibleBalanceWithTypeAmountList>

    @PUT(ITEM_TOKEN_FT_MEDIA_RESOURCE_REFRESH_PATH)
    suspend fun updateFungibleItemTokensMediaResource(
        @Path("contractId") contractId: String,
        @Body request: UpdateFungibleTokenResourceRequest,
    ): GenericResponse<UpdateTokenMediaRefreshResponse>

    @PUT(ITEM_TOKEN_NFT_MEDIA_RESOURCE_REFRESH_PATH)
    suspend fun updateNonFungibleItemTokensMediaResource(
        @Path("contractId") contractId: String,
        @Body request: UpdateNonFungibleTokenResourceRequest,
    ): GenericResponse<UpdateTokenMediaRefreshResponse>

    @PUT(ITEM_TOKEN_FT_THUMBNAIL_REFRESH_PATH)
    suspend fun updateFungibleItemTokensThumbnail(
        @Path("contractId") contractId: String,
        @Body request: UpdateFungibleTokenResourceRequest,
    ): GenericResponse<UpdateTokenMediaRefreshResponse>

    @PUT(ITEM_TOKEN_NFT_THUMBNAIL_REFRESH_PATH)
    suspend fun updateNonFungibleItemTokensThumbnail(
        @Path("contractId") contractId: String,
        @Body request: UpdateNonFungibleTokenResourceRequest,
    ): GenericResponse<UpdateTokenMediaRefreshResponse>

    @GET(TX_MESSAGES_PATH)
    suspend fun getTxMessages(
        @Path("txHash") txHash: String,
        @Query("limit") limit: Int = 10,
        @Query("pageToken") pageToken: String?,
        @Query("orderBy") orderBy: OrderBy = OrderBy.ASC
    ): GenericResponse<TxMessageListResponse>
}
