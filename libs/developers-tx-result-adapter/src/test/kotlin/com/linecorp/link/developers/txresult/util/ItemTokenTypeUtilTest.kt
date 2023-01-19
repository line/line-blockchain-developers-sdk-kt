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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ItemTokenTypeUtilTest {

    @ParameterizedTest
    @ValueSource(strings = ["01234567", "01234567890"])
    fun tokenType(tokenId: String) {
        val tokenType = ItemTokenTypeUtil.tokenType(tokenId)
        val expectedTokenType = tokenId.substring((0..7))
        assertEquals(expectedTokenType, tokenType)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "0123456"])
    fun `tokenType(), when invalid token-type`(invalidTokenId: String) {
        assertThrows(InvalidTokenTypeException::class.java) {
            ItemTokenTypeUtil.tokenType(invalidTokenId)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0123456789012345", "01234567890123456"])
    fun tokenIndex(tokenId: String) {
        val tokenType = ItemTokenTypeUtil.tokenIndex(tokenId)
        val expectedTokenType = tokenId.substring((8..15))
        assertEquals(expectedTokenType, tokenType)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "012345678901234"])
    fun `tokenIndex(), when invalid token-id`(invalidTokenId: String) {
        assertThrows(InvalidTokenIdException::class.java) {
            ItemTokenTypeUtil.tokenIndex(invalidTokenId)
        }
    }
}
