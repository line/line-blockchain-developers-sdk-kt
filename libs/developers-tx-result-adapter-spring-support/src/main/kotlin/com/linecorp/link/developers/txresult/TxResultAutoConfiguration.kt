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

package com.linecorp.link.developers.txresult

import com.linecorp.link.developers.txresult.ChainProperties.Companion.DEFAULT_HRP_PREFIX
import com.linecorp.link.developers.txresult.adapter.TxResultAdapter
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import com.linecorp.link.developers.txresult.core.model.TxMessage
import com.linecorp.link.developers.txresult.core.model.TxResult
import com.linecorp.link.developers.txresult.core.model.TxResultSummary
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxMessageAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxResultAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxSummaryAdapterV1
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import com.linecorp.link.developers.txresult.v1.raw.model.RawTransactionResult
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.linecorp.link.developers"])
@EnableConfigurationProperties(value = [ChainProperties::class])
class TxResultAutoConfiguration {

    @ConditionalOnMissingBean(
        name = ["jsonRawTransactionResultAdapter"],
        type = ["com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter"]
    )
    @Bean
    fun jsonRawTransactionResultAdapter(): TxResultAdapter<String, RawTransactionResult> {
        return JsonRawTransactionResultAdapter();
    }

    @ConditionalOnMissingBean(
        name = ["txSummaryAdapterV1"],
        type = ["com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxSummaryAdapterV1"]
    )
    @Bean
    fun txSummaryAdapterV1(chainProperties: ChainProperties?): TxResultAdapter<RawTransactionResult, TxResultSummary> {
        return DomainTxSummaryAdapterV1(chainProperties?.bech32Hrp ?: DEFAULT_HRP_PREFIX)
    }

    @ConditionalOnMissingBean(
        name = ["txMessageAdapterV1"],
        type = ["com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxMessageAdapterV1"]
    )
    @Bean
    fun txMessageAdapterV1(): TxResultAdapter<RawTransactionResult, Set<TxMessage>> {
        return DomainTxMessageAdapterV1()
    }

    @ConditionalOnMissingBean(
        name = ["txEventsAdapterV1"],
        type = ["com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxEventsAdapterV1"]
    )
    @Bean
    fun txEventsAdapterV1(): TxResultAdapter<RawTransactionResult, List<TransactionEvent>> {
        return DomainTxEventsAdapterV1()
    }

    @ConditionalOnMissingBean(
        name = ["txResultAdapterV1"],
        type = ["com.linecorp.link.developers.txresult.v1.raw.adapter.DomainTxResultAdapterV1"]
    )
    @Bean
    fun txResultAdapterV1(
        txResultSummaryAdapter: TxResultAdapter<RawTransactionResult, TxResultSummary>,
        txMessageAdapter: TxResultAdapter<RawTransactionResult, Set<TxMessage>>,
        txEventsAdapter: TxResultAdapter<RawTransactionResult, List<TransactionEvent>>
    ): TxResultAdapter<RawTransactionResult, TxResult> {
        return DomainTxResultAdapterV1(
            txResultSummaryAdapter = txResultSummaryAdapter,
            txMessageAdapter = txMessageAdapter,
            txEventsAdapter = txEventsAdapter
        )
    }

}
