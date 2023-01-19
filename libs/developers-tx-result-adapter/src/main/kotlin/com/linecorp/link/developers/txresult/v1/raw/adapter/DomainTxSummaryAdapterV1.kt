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
import com.linecorp.link.developers.txresult.core.model.TxResultSummary
import com.linecorp.link.developers.txresult.core.model.TxSigner
import com.linecorp.link.developers.txresult.core.model.TxStatusResult
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class DomainTxSummaryAdapterV1(
    private val hrpPrefix: String
) : TxResultAdapter<RawTransactionResult, TxResultSummary> {
    override fun adapt(input: RawTransactionResult): TxResultSummary {
        val height: Long = input.height
        val txIndex: Int = input.index
        val txHash: String = input.txhash
        val timestamp: Long = input.timestamp
        val code: Int = input.code
        val codeSpace = input.codespace ?: ""

        return TxResultSummary(
            height = height,
            txIndex = txIndex,
            txHash = txHash,
            timestamp = timestamp,
            signers = txSigners(input),
            result = TxStatusResult(code = code, codeSpace = codeSpace)
        )
    }

    private fun txSigners(input: RawTransactionResult): Set<TxSigner> {
        return try {
            input.getSignerAddresses(hrpPrefix).map {
                TxSigner(address = it)
            }.toSet()
        } catch (e: Exception) {
            logger.error(e) { "Fail to get tx-signers, input raw-transaction: $input"}
            emptySet()
        }
    }
}
