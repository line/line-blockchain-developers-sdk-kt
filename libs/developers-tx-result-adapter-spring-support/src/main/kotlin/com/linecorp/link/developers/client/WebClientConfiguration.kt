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

package com.linecorp.link.developers.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.link.developers.jackson.JacksonObjectMapperFactory
import com.linecorp.link.developers.txresult.v1.raw.adapter.JsonRawTransactionResultAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Primary
    @Bean
    fun lbdWebClient(objectMapper: ObjectMapper): WebClient {
        val strategies = buildWebClientStrategies(objectMapper)
        return WebClient.builder().exchangeStrategies(strategies).build()
    }

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper {
        return JacksonObjectMapperFactory().create()
    }

    private fun buildWebClientStrategies(objectMapper: ObjectMapper): ExchangeStrategies {
        return ExchangeStrategies
            .builder()
            .codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
                clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
            }.build()
    }
}
