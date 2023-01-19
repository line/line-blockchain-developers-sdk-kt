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

package com.linecorp.link.developers.txresult.helper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.file.Files
import java.util.stream.Collectors
import kotlin.io.path.toPath

object RawTransactionLoader {
    private val objectMapper = jacksonObjectMapper()

    fun loadRawTransactionResult(path: String): Map<String, Any?> {
        val typeReference = object : TypeReference<Map<String, Any?>>() {}
        val inputStream = this::class.java.classLoader.getResourceAsStream(path)
        return objectMapper.readValue(inputStream, typeReference)
    }

    fun loadRawTransactionResultInJsonText(path: String): String {
        val toPath = this::class.java.classLoader.getResource(path)!!.toURI().toPath()
        return Files.readAllLines(toPath).stream().collect(Collectors.joining())
    }
}
