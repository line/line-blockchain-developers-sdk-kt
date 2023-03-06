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

package com.linecorp.link.developers.chain.crypto

import java.io.ByteArrayOutputStream

@Suppress("MagicNumber")
internal object Bech32Utils {
    @JvmStatic
    fun convertBits(
        input: ByteArray,
        inStart: Int,
        inLen: Int,
        fromBits: Int,
        toBits: Int,
        pad: Boolean
    ): ByteArray {
        var acc = 0
        var bits = 0
        val out = ByteArrayOutputStream(64)
        val maxv = (1 shl toBits) - 1
        val maxAcc = (1 shl (fromBits + toBits - 1)) - 1
        for (i in 0 until inLen) {
            val value = input[i + inStart].toInt() and 0xff
            acc = ((acc shl fromBits) or value) and maxAcc
            bits += fromBits
            while (bits >= toBits) {
                bits -= toBits
                out.write((acc ushr bits) and maxv)
            }
        }
        if (pad) {
            if (bits > 0) {
                out.write((acc shl (toBits - bits)) and maxv)
            }
        } else require(!(bits >= fromBits || ((acc shl (toBits - bits)) and maxv) != 0)) {
            "Could not convert bits, invalid padding"
        }
        return out.toByteArray()
    }
}
