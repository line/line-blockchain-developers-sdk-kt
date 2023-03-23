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

package com.linecorp.link.developers.client.api.retrofit

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.link.developers.client.api.ApiClient
import com.linecorp.link.developers.client.api.ApiClientFactory
import com.linecorp.link.developers.client.api.ApiKeySecret
import com.linecorp.link.developers.client.api.DefaultRequestHeadersAppender
import com.linecorp.link.developers.client.api.DefaultRequestQueryParameterOrderer
import com.linecorp.link.developers.client.api.RequestHeadersAppender
import com.linecorp.link.developers.client.api.RequestQueryParameterSorter
import com.linecorp.link.developers.jackson.JacksonObjectMapperFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitApiClientFactory : ApiClientFactory {
    override fun build(
        baseUrl: String,
        enableLogging: Boolean,
        requestHeadersAppender: RequestHeadersAppender,
        requestQueryParameterSorter: RequestQueryParameterSorter,
        jacksonObjectMapper: ObjectMapper,
    ): ApiClient {
        val okHttp3Client: OkHttpClient =
            httpClient(requestHeadersAppender, requestQueryParameterSorter, enableLogging)
        val retrofit = retrofit(baseUrl, okHttp3Client, jacksonObjectMapper)
        return retrofit.create(ApiClient::class.java)
    }

    override fun buildDefaultApiClient(
        baseUrl: String,
        enableLogging: Boolean,
        apiKeySecret: ApiKeySecret
    ): ApiClient {
        val okHttp3Client: OkHttpClient =
            httpClient(
                DefaultRequestHeadersAppender.createDefaultInstance(apiKeySecret),
                DefaultRequestQueryParameterOrderer.createDefaultInstance(),
                enableLogging
            )
        val retrofit = retrofit(baseUrl, okHttp3Client, createObjectMapper())
        return retrofit.create(ApiClient::class.java)
    }

    private fun retrofit(
        baseUrl: String,
        okHttp3Client: OkHttpClient,
        jacksonObjectMapper: ObjectMapper = createObjectMapper(),
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper))
            .client(okHttp3Client)
            .build()
    }

    private fun httpClient(
        requestHeadersAppender: RequestHeadersAppender,
        requestQueryParameterSorter: RequestQueryParameterSorter,
        enableLogging: Boolean,
    ): OkHttpClient {
        val logLevel =
            if (!enableLogging) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.BODY
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(logLevel)

        return OkHttpClient.Builder()
            .addInterceptor(requestHeadersAppender)
            .addInterceptor(requestQueryParameterSorter)
            .addInterceptor(loggingInterceptor).build()
    }

    private fun createObjectMapper(): ObjectMapper {
        return JacksonObjectMapperFactory().create()
    }
}
