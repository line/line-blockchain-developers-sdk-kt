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

package com.linecorp.link.developers.txresult.util

import com.linecorp.link.developers.txresult.exception.InvalidTokenIdException
import com.linecorp.link.developers.txresult.exception.InvalidTokenTypeException


object ItemTokenTypeUtil {
    private const val MINIMUM_TOKEN_ID_LENGTH = 8
    private const val FULL_TOKEN_ID_LENGTH = 16

    @Suppress("MagicNumber", "MemberVisibilityCanBePrivate")
    fun tokenType(tokenId: String): String {
        validateTokenType(tokenId)
        return tokenId.substring((0..7))
    }

    @Suppress("MagicNumber")
    fun tokenIndex(tokenId: String): String {
        validateTokenIdWithIndex(tokenId)
        return tokenId.substring((8..15))
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun validateTokenType(tokenType: String) {
        if (tokenType.isBlank() || tokenType.length < MINIMUM_TOKEN_ID_LENGTH) {
            throw InvalidTokenTypeException(tokenType)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun validateTokenIdWithIndex(tokenId: String) {
        if (tokenId.isBlank() || tokenId.length < FULL_TOKEN_ID_LENGTH) {
            throw InvalidTokenIdException(tokenId)
        }
    }
}
