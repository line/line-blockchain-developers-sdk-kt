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

import java.security.SecureRandom
import org.apache.commons.lang3.RandomStringUtils

interface NonceGenerator {
    fun newNonce(): String
}

class DefaultStringNonceGenerator : NonceGenerator {
    override fun newNonce(): String {
        return RandomStringUtils.random(8, 0, 0, true, true, null, SecureRandom())
    }

    companion object {
        fun createDefaultInstance() = DefaultStringNonceGenerator()
    }
}
