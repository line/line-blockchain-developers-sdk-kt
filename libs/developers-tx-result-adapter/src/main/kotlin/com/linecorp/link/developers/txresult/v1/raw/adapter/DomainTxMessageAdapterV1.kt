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
import com.linecorp.link.developers.txresult.core.model.TxMessage
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult

class DomainTxMessageAdapterV1: TxResultAdapter<RawTransactionResult, Set<TxMessage>> {
    override fun adapt(input: RawTransactionResult): Set<TxMessage> {
        val tx = input.tx
        val rawMessages = tx.value.msg

        return rawMessages.mapIndexed { index, rawMessage ->
            val type = rawMessage.type
            @Suppress("UNCHECKED_CAST") val details = rawMessage.value as Map<String, Any>
            TxMessage(msgIndex = index, requestType = type, details = details)
        }.toSet()
    }
}

