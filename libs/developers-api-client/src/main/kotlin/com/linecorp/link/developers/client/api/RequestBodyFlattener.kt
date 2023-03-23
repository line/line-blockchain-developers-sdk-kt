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
import org.apache.commons.lang3.StringUtils

interface RequestBodyFlattener {
    fun flatten(body: Map<String, Any?>): String
}

class DefaultRequestBodyFlattener : RequestBodyFlattener {
    override fun flatten(body: Map<String, Any?>): String {
        val bodyTreeMap = TreeMap<String, Any?>()
        bodyTreeMap.putAll(body)

        @Suppress("UNCHECKED_CAST")
        return if (bodyTreeMap.isEmpty()) {
            ""
        } else {
            bodyTreeMap.filterValues { it != null }.map { (k, v) ->
                when (v) {
                    is String -> "$k=$v"
                    is List<*> -> {
                        resolveListPropertyValue(v, k)
                    }
                    else -> throw IllegalArgumentException("Fail to flatten request body - invalid property value: $v")
                }
            }.joinToString("&")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun resolveListPropertyValue(propertyValue: Any?, propertyName: String): String {
        val listTreeMap = TreeMap<String, String?>()
        propertyValue as List<Map<String, String>>
        propertyValue.forEachIndexed { index, map ->
            map.keys.union(listTreeMap.keys).forEach { key ->
                val value = map[key] ?: StringUtils.EMPTY
                if (listTreeMap[key] == null) {
                    listTreeMap[key] = "${",".repeat(index)}$value"
                } else {
                    listTreeMap[key] = "${listTreeMap[key]},$value"
                }
            }
        }
        return listTreeMap.map { (key, value) -> "$propertyName.$key=$value" }.joinToString("&")
    }
}
