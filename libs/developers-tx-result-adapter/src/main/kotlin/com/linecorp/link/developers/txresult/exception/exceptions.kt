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

package com.linecorp.link.developers.txresult.exception

class InvalidTokenIdException(
    private val invalidTokenId: String,
) : IllegalArgumentException("Invalid token id, given token id is $invalidTokenId") {
    companion object {
        private const val serialVersionUID: Long = -3461907449838860405L
    }
}

class InvalidTokenTypeException(
    private val invalidTokenType: String,
) : IllegalArgumentException("Invalid token type, given token type is $invalidTokenType") {
    companion object {
        private const val serialVersionUID: Long = 4346781270243096184L
    }
}

class InvalidTxResultException(
    message: String
): IllegalArgumentException(message) {
    companion object {
        private const val serialVersionUID: Long = 274441773293973851L
    }
}

class TxResultAdaptFailedException(
    message: String,
    cause: Exception,
): RuntimeException(message, cause) {
    companion object {
        private const val serialVersionUID: Long = -7702239710398779639L
    }
}

class InvalidTxResultJsonFormatException(
    input: String,
    cause: Exception
): RuntimeException("Invalid tx-result json format, input: $input", cause) {
    companion object {
        private const val serialVersionUID: Long = -6042083468803683368L
    }
}
