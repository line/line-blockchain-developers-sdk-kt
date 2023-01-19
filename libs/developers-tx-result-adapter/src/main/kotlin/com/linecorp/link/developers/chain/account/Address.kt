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

package com.linecorp.link.developers.chain.account

import com.linecorp.link.developers.chain.account.Type.ACCOUNT
import com.linecorp.link.developers.chain.account.Type.Companion.DEFAULT_BECH32_HRP_PREFIX
import com.linecorp.link.developers.chain.account.Type.Companion.toHrpPrefix
import com.linecorp.link.developers.chain.crypto.Bech32Utils
import org.bitcoinj.core.Bech32
import org.bouncycastle.jcajce.provider.digest.RIPEMD160
import org.bouncycastle.jcajce.provider.digest.SHA256

/**
 * Address represents the account's address.
 *
 * There are three ways to initiate a address
 * - with a public key: `Address(pubKey: PubKey)`, `Address.of(PubKey pubKey)`
 * - with an address raw body: `Address(body: ByteArray)`, `Address.of(byte[] body)`
 * - with a stringified address: `Address(bech32Address: String)`, `Address.of(String bech32Address)`
 *
 * @property type type of the address
 * @property body raw-bytes body of the address
 */
internal class Address private constructor(val type: Type, val body: ByteArray) {

    /**
     * returns bech32 formatted string of the address
     *
     * @return <a href="https://github.com/bitcoin/bips/blob/master/bip-0173.mediawiki">bech32</a> formatted string
     */
    fun toBech32(hrpPrefix: String /*= DEFAULT_BECH32_HRP_PREFIX*/): String { // dev3 comment out default arg
        if (body.isEmpty()) return ""

        // using `convertBits`, makes each byte hold 5 bits for `Bech32` class to recognize.
        val bech32data = Bech32Utils.convertBits(body, 0, body.size, 8, 5, true)

        return Bech32.encode("${hrpPrefix}${type.toHrpPrefix()}", bech32data)
    }

    /**
     * stringifies the address
     *
     * @return stringified address
     */
    override fun toString(): String {
        return toBech32(DEFAULT_BECH32_HRP_PREFIX)  // dev3 add DEFAULT_BECH32_HRP_PREFIX
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address
        return type == other.type && body.contentEquals(other.body)
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

    companion object {
        private const val ADDRESS_SIZE_IN_BYTE = 20

        /**
         * initiates an account address using address [type] and address raw [body]
         *
         * @param type address type
         * @param body address body
         * @return new address corresponding to the given [body]
         */
        @JvmStatic
        @JvmName("of")
        operator fun invoke(type: Type, body: ByteArray): Address {
            require(body.size == ADDRESS_SIZE_IN_BYTE) {
                "Illegal body size(required $ADDRESS_SIZE_IN_BYTE, given ${body.size})"
            }

            return Address(type, body)
        }

        /**
         * initiates an account address using address raw [body]
         *
         * @param body address body
         * @return new address corresponding to the given [body]
         */
        @JvmStatic
        @JvmName("of")
        operator fun invoke(body: ByteArray): Address = invoke(ACCOUNT, body)

        /**
         * initiates an address from [bech32Address]
         *
         * @param bech32Address Bech32 address
         * @return new address corresponding to the given [bech32Address]
         */
        @JvmStatic
        @JvmName("of")
        // dev3 comment out default arg
        operator fun invoke(bech32Address: String, hrpPrefix: String /*= DEFAULT_BECH32_HRP_PREFIX*/): Address {
            val bech32Data = Bech32.decode(bech32Address)

            require(bech32Data.hrp.startsWith(hrpPrefix)) {
                "Illegal HRP prefix('${bech32Data.hrp}' does not start with '$hrpPrefix')"
            }

            val type = Type(bech32Data.hrp.substring(hrpPrefix.length))
            // the output of the bech32Data.data is 32 bytes length and each byte holds the 5 bits
            // using `convertBits` trims bits so the body will finally 20 bytes.
            val body = Bech32Utils.convertBits(bech32Data.data, 0, bech32Data.data.size, 5, 8, true)

            return invoke(type, body)
        }

        // #TEMP dev3
        @JvmStatic
        @JvmName("of")
        operator fun invoke(bech32Address: String): Address {
            return invoke(bech32Address, DEFAULT_BECH32_HRP_PREFIX)
        }

        /**
         * initiates an address using public key bytes
         *
         * @param pubKey public key object
         * @return new address corresponding to the given public key
         */
        @JvmStatic
        @JvmName("of")
        operator fun invoke(pubKey: PubKey): Address {

            // ripemd160(sha256(pubkey)) -> 20 length of bytes
            val body = RIPEMD160.Digest().digest(SHA256.Digest().digest(pubKey.body))

            return invoke(pubKey.type, body)
        }
    }
}
