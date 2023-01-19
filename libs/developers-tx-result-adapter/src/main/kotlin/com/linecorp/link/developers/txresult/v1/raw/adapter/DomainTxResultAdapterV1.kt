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

import com.linecorp.link.developers.txresult.adapter.TxResultAdapter
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.core.model.TxMessage
import com.linecorp.link.developers.txresult.core.model.TxResult
import com.linecorp.link.developers.txresult.core.model.TxResultSummary
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult

class DomainTxResultAdapterV1(
    private val txResultSummaryAdapter: TxResultAdapter<RawTransactionResult, TxResultSummary>,
    private val txMessageAdapter: TxResultAdapter<RawTransactionResult, Set<TxMessage>>,
    private val txEventsAdapter: TxResultAdapter<RawTransactionResult, Set<TransactionEvent>>,
) : TxResultAdapter<RawTransactionResult, TxResult> {
    override fun adapt(input: RawTransactionResult): TxResult {
        return TxResult(
            summary = txResultSummaryAdapter.adapt(input),
            txMessages = txMessageAdapter.adapt(input),
            txEvents = txEventsAdapter.adapt(input),
        )
    }
}



