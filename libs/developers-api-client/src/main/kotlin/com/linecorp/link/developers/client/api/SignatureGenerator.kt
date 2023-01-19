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

import java.util.TreeMap
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64


interface SignatureGenerator {
    fun generate(
        httpMethod: String,
        path: String,
        timestamp: Long,
        nonce: String,
        flatQueryParam: String,
        body: Map<String, Any?> = emptyMap(),
    ): String

    fun generate(
        httpMethod: String,
        path: String,
        timestamp: Long,
        nonce: String,
        queryParam: Map<String, List<String?>> = emptyMap(),
        body: Map<String, Any?> = emptyMap(),
    ): String
}


class DefaultSignatureGenerator(
    private val serviceApiSecret:  String,
    private val queryParameterFlattener: QueryParameterFlattener,
    private val requestBodyFlattener: RequestBodyFlattener,
) : SignatureGenerator {
    override fun generate(
        httpMethod: String,
        path: String,
        timestamp: Long,
        nonce: String,
        queryParam: Map<String, List<String?>>,
        body: Map<String, Any?>,
    ): String {
        val flattenQueryParam = queryParameterFlattener.flatten(queryParam)

        return generate(
            httpMethod,
            path,
            timestamp,
            nonce,
            flattenQueryParam,
            body,
        )
    }

    override fun generate(
        httpMethod: String,
        path: String,
        timestamp: Long,
        nonce: String,
        flatQueryParam: String,
        body: Map<String, Any?>,
    ): String {
        val data = signatureTarget(body, nonce, timestamp, httpMethod, path, flatQueryParam)
        val rawHmac = rawSignature(serviceApiSecret, data)
        return Base64.encodeBase64String(rawHmac)
    }

    private fun rawSignature(serviceApiSecret: String, data: String): ByteArray? {
        val signingKey = SecretKeySpec(serviceApiSecret.toByteArray(), HMAC_512_SECRET_ALGORITHM)
        val mac = Mac.getInstance(HMAC_512_SECRET_ALGORITHM)
        mac.init(signingKey)
        return mac.doFinal(data.toByteArray())
    }

    private fun signatureTarget(
        body: Map<String, Any?>,
        nonce: String,
        timestamp: Long,
        httpMethod: String,
        path: String,
        flatQueryParam: String
    ): String {
        val bodyTreeMap = sortBody(body)
        val flattenBody = requestBodyFlattener.flatten(bodyTreeMap)
        val stringBuilder = StringBuilder()
        stringBuilder.append("$nonce$timestamp$httpMethod$path")
        if (flatQueryParam.isNotBlank()) {
            if ("?" in flatQueryParam) {
                stringBuilder.append(flatQueryParam)
            } else {
                stringBuilder.append("?").append(flatQueryParam)
            }
        }
        if (flattenBody.isNotBlank()) {
            if (!stringBuilder.contains('?')) {
                stringBuilder.append("?").append(flattenBody)
            } else {
                stringBuilder.append("&").append(flattenBody)
            }
        }

        return stringBuilder.toString()
    }

    private fun sortBody(body: Map<String, Any?>): TreeMap<String, Any?> {
        val bodyTreeMap = TreeMap<String, Any?>()
        bodyTreeMap.putAll(body)
        return bodyTreeMap
    }

    companion object {
        private const val HMAC_512_SECRET_ALGORITHM = "HmacSHA512"

        fun createDefaultInstance(apiKeySecret:  String): SignatureGenerator {
            return DefaultSignatureGenerator(
                apiKeySecret,
                DefaultOrderedQueryParameterFlattener(),
                DefaultRequestBodyFlattener()
            )
        }
    }
}
