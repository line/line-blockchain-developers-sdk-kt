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

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NonceGeneratorTest {
    private lateinit var nonceGenerator: NonceGenerator

    @BeforeEach
    fun setUp() {
        nonceGenerator = DefaultStringNonceGenerator()
    }


    @Test
    fun test_newNonce() {
        val nonce1 = nonceGenerator.newNonce()
        assertTrue(nonce1.length == 8)

        val nonce2 = nonceGenerator.newNonce()
        assertTrue(nonce1 != nonce2)

        val nonceSet = (0..1000000).asSequence().map {
            nonceGenerator.newNonce()
        }.toSet()

        assertTrue(nonceSet.size >= 1000000)
    }
}
