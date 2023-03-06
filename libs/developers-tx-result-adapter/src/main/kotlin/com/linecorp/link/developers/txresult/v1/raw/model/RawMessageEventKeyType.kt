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

import org.apache.commons.lang3.StringUtils

enum class RawMessageEventKeyType(
    val type: String,
    val eventName: String,
    val candidateEventName: Collection<String> = emptyList()
) {
    AccountMsgCreateAccount(type = "account/MsgCreateAccount", eventName = "create_account"),
    AccountMsgEmpty(type = "account/MsgEmpty", eventName = "message"),

    // coin
    CoinMsgSend(type = "coin/MsgSend", eventName = "transfer"),

    // token
    TokenMsgIssue(type = "token/MsgIssue", eventName = "issue"),
    TokenMsgMint(type = "token/MsgMint", eventName = "mint"),
    TokenMsgBurn(type = "token/MsgBurn", eventName = "burn"),
    TokenMsgBurnFrom(type = "token/MsgBurnFrom", eventName = "burn_from"),
    TokenMsgTransfer(type = "token/MsgTransfer", eventName = "transfer"),
    TokenMsgTransferFrom(type = "token/MsgTransferFrom", eventName = "transfer_from"),
    TokenMsgModify(type = "token/MsgModify", eventName = "modify_token"),
    TokenMsgApprove(type = "token/MsgApprove", eventName = "approve_token"),
    TokenMsgGrantPermission(type = "token/MsgGrantPermission", eventName = StringUtils.EMPTY),
    TokenMsgRevokePermission(type = "token/MsgRevokePermission", eventName = StringUtils.EMPTY),

    // permission
    GrantPermission(type = "", eventName = "grant_perm"),

    // collection
    CollectionMsgCreate(type = "collection/MsgCreate", "create_collection"),
    CollectionMsgIssueFT(type = "collection/MsgIssueFT", eventName = "issue_ft"),
    CollectionMsgIssueNFT(type = "collection/MsgIssueNFT", eventName = "issue_nft"),
    CollectionMsgMintFT(type = "collection/MsgMintFT", eventName = "mint_ft"),
    CollectionMsgMintNFT(type = "collection/MsgMintNFT", eventName = "mint_nft"),
    CollectionMsgBurnFT(type = "collection/MsgBurnFT", eventName = "burn_ft"),
    CollectionMsgBurnFTFrom(type = "collection/MsgBurnFTFrom", eventName = "burn_ft_from"),

    // exceptional case that not enough data from single event
    CollectionOperationBurnNFT(type = "", eventName = "operation_burn_nft"),

    CollectionMsgBurnNFT(type = "collection/MsgBurnNFT", eventName = "burn_nft"),
    CollectionMsgBurnNFTFrom(type = "collection/MsgBurnNFTFrom", eventName = "burn_nft_from"),
    CollectionMsgTransferFT(type = "collection/MsgTransferFT", eventName = "transfer_ft"),
    CollectionMsgTransferFTFrom(type = "collection/MsgTransferFTFrom", eventName = "transfer_ft_from"),
    CollectionMsgTransferNFT(type = "collection/MsgTransferNFT", eventName = "transfer_nft"),
    CollectionMsgTransferNFTFrom(type = "collection/MsgTransferNFTFrom", eventName = "transfer_nft_from"),

    // exceptional case that not enough data from single event
    CollectionOperationTransferNFT(type = "", eventName = "operation_transfer_nft"),

    CollectionMsgAttach(type = "collection/MsgAttach", eventName = "attach"),
    CollectionMsgAttachFrom(type = "collection/MsgAttachFrom", eventName = "attach_from"),
    CollectionMsgDetach(type = "collection/MsgDetach", eventName = "detach"),
    CollectionMsgDetachFrom(type = "collection/MsgDetachFrom", eventName = "detach_from"),

    // exceptional case that not enough data from single event
    CollectionOperationRootChanged(type = "", eventName = "operation_root_changed"),

    CollectionMsgApprove(type = "collection/MsgApprove", eventName = "approve_collection"),
    CollectionMsgModify(
        type = "collection/MsgModify",
        eventName = "modify_collection",
        candidateEventName = setOf("modify_token", "modify_token_type")
    ),
    CollectionMsgDisapprove(type = "collection/MsgDisapprove", eventName = "disapprove_collection"),
    CollectionMsgGrantPermission(type = "collection/MsgGrantPermission", eventName = StringUtils.EMPTY),
    CollectionMsgRevokePermission(type = "collection/MsgRevokePermission", eventName = StringUtils.EMPTY)
}

fun convertToEventType(value: String): RawMessageEventKeyType? {
    return RawMessageEventKeyType.values().singleOrNull { it.type == value }
}
