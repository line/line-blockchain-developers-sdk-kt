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

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.linecorp.link.developers.jackson.JacksonObjectMapperFactory
import com.linecorp.link.developers.jackson.TransactionEventDeserializer
import com.linecorp.link.developers.txresult.core.model.TransactionEvent
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@AutoConfigureBefore(
    value = [JacksonAutoConfiguration::class]
)
class WebClientConfiguration {

    @Bean
    @Primary
    fun lbdWebClient(objectMapper: ObjectMapper): WebClient {
        val strategies = buildWebClientStrategies(objectMapper)
        return WebClient.builder().exchangeStrategies(strategies).build()
    }

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper(): ObjectMapper {
        return JacksonObjectMapperFactory().create()
    }

    @Bean
    @Primary
    fun jacksonObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        val simpleModule = SimpleModule().addDeserializer(
            TransactionEvent::class.java,
            TransactionEventDeserializer()
        )
        return Jackson2ObjectMapperBuilder().modules(kotlinModule(), simpleModule)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
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
