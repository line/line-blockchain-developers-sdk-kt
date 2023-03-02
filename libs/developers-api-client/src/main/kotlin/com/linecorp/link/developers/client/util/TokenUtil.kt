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

package com.linecorp.link.developers.client.util

import com.linecorp.link.developers.client.request.TOKEN_ID_REGEX
import com.linecorp.link.developers.client.request.TOKEN_TYPE_REGEX

object TokenUtil {
    fun filterInvalidItemTokenIdentifier(tokenIdentifier: String): Boolean {
        return !TOKEN_ID_REGEX.matches(tokenIdentifier)
    }

    fun filterInvalidItemTokenType(tokenType: String): Boolean {
        return !TOKEN_TYPE_REGEX.matches(tokenType)
    }

    fun splitTokenId(tokenId: String): Pair<String, String?> {
        require(tokenId.isBlank() || tokenId.length < 8 || tokenId.length > 16 || (tokenId.length in 9..15)) {
            "Invalid tokenId - invalid length"
        }

        return if (tokenId.length == 8) {
            Pair(tokenId.take(8), null)
        } else {
            Pair(tokenId.take(8), tokenId.takeLast(8))
        }
    }
}
