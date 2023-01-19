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

package com.linecorp.link.developers.txresult.core.model

import com.linecorp.link.developers.txresult.core.model.TxSuccessResult.FAILED
import com.linecorp.link.developers.txresult.core.model.TxSuccessResult.SUCCEEDED
import org.apache.commons.lang3.StringUtils

data class TxResult(
    val summary: TxResultSummary,
    val txMessages: Set<TxMessage>,
    val txEvents: Set<TransactionEvent>,
)

data class TxResultSummary(
    val height: Long,
    val txIndex: Int,
    val txHash: String,
    val timestamp: Long,
    val signers: Set<TxSigner>,
    val result: TxStatusResult,
)

data class TxSigner(
    val address: String,
)

data class TxStatusResult(
    val code: Int,
    val codeSpace: String = StringUtils.EMPTY,
) {
    val result: TxSuccessResult
        get() = if (code == 0) SUCCEEDED else FAILED
}

data class TxMessage(
    val msgIndex: Int,
    val requestType: String,
    val details: Any,
)

interface TransactionEvent {
    val eventName: String
        get() = this::class.java.simpleName
}

enum class TxSuccessResult {
    SUCCEEDED, FAILED
}

enum class HrpPrefix(val prefix: String) {
    TEST_NET("tlink"), MAIN_NET("link")

}
