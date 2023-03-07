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

import com.google.common.primitives.Bytes
import org.bitcoinj.core.VarInt
import org.bouncycastle.jcajce.provider.digest.SHA256.Digest
import java.util.Arrays

internal object Amino {
    private const val DISAMBIGUATION_BYTE_LENGTH = 3
    private const val TYPE_PREFIX_LENGTH = 4
    private const val SECP256K1_KEY_SIZE = 33

    fun addAminoPrefix(name: String, body: ByteArray): ByteArray {
        val prefix = makeAminoPrefix(name, body)
        return Bytes.concat(prefix, body)
    }

    fun makeAminoPrefix(name: String, body: ByteArray): ByteArray {
        val hash = Digest().digest(name.toByteArray())
        val disambiguationByteStart = getNonZeroValueIndex(hash)
        val typePrefixStart = getNonZeroValueIndex(
            hash,
            disambiguationByteStart + DISAMBIGUATION_BYTE_LENGTH,
            hash.size
        )
        require(body.size <= SECP256K1_KEY_SIZE) {
            "The body size is ${body.size} bytes. Currently, amino encoding is only supported for secp256k1 keys."
        }
        val sizePrefix = VarInt(body.size.toLong()).encode()
        return Bytes.concat(
            Arrays.copyOfRange(hash, typePrefixStart, typePrefixStart + TYPE_PREFIX_LENGTH),
            sizePrefix
        )
    }

    fun getNonZeroValueIndex(hash: ByteArray): Int {
        return getNonZeroValueIndex(hash, 0, hash.size)
    }

    fun getNonZeroValueIndex(hash: ByteArray, start: Int, end: Int): Int {
        require(!(start < 0 || end > hash.size)) {
            "Invalid index (start: $start, end: $end, length: ${hash.size})"
        }
        for (i in start until end) {
            if (hash[i].toInt() != 0x00) {
                return i
            }
        }
        throw IllegalArgumentException("input hash is empty")
    }
}
