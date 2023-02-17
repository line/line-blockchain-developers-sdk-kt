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

package com.linecorp.link.developers.client.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.TreeMap

interface RequestQueryParameterSorter : QueryParameterSorter, Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = orderedQueryParameters(request)
        return chain.proceed(request.newBuilder().url(httpUrl).build())
    }

    fun orderedQueryParameters(request: Request): HttpUrl
}

class DefaultRequestQueryParameterOrderer : RequestQueryParameterSorter {
    override fun sort(queryParams: Map<String, List<String?>>): Map<String, List<String?>> {
        return TreeMap<String, List<String?>>(queryParams)
    }

    override fun orderedQueryParameters(request: Request): HttpUrl {
        val queryParams = sort(queryParameters(request))
        return when {
            queryParams.isEmpty() -> {
                request.url
            }
            else -> {
                val newUrlBuilder = request.url.newBuilder()
                newUrlBuilder.clearAllQueryParameters(request.url)
                queryParams
                    .filter { it.value.first()?.isNotBlank() ?: false }
                    .forEach {
                        newUrlBuilder.addQueryParameter(it.key, it.value.first())
                    }
                newUrlBuilder.build()
            }
        }
    }

    private fun queryParameters(request: Request): Map<String, List<String?>> {
        return request.url.queryParameterNames.map {
            it to request.url.queryParameterValues(it)
        }.toMap()
    }

    companion object {
        fun createDefaultInstance() = DefaultRequestQueryParameterOrderer()
    }
}

fun HttpUrl.Builder.clearAllQueryParameters(httpUrl: HttpUrl) {
    httpUrl.queryParameterNames.forEach {
        this.removeAllQueryParameters(it)
    }
}
