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
import com.linecorp.link.developers.chain.crypto.Amino
import com.linecorp.link.developers.chain.crypto.Bech32Utils
import org.bitcoinj.core.Bech32

/**
 * PubKey represents the account's public key.
 *
 * There are three ways to initiate a address
 * - with a private key bytes: `createFromPrivateKey(byte[] pubKeyBytes)`
 * - with an public key raw body: `new PubKey(byte[] body)`
 * - with a stringified public key: `new PubKey(String bech32pubKey)`
 */
internal class PubKey {

    /**
     * type of the public key
     */
    val type: Type

    /**
     * encoding type of the public key
     */
    val encodingType: String = PUB_KEY_TYPE_SECP256K1

    /**
     * raw-bytes body of the public key
     */
    val body: ByteArray

    /**
     * initiates an public key using public key [type] and raw [body]
     */
    constructor(type: Type, body: ByteArray) {
        this.type = type
        this.body = body
        checkBodySize()
    }

    /**
     * initiates an account public key using raw public key [body]
     */
    constructor(body: ByteArray) : this(ACCOUNT, body)

    private fun checkBodySize() {
        val bodySize = body.size
        require(bodySize == PUBLIC_KEY_SIZE_IN_BYTE) {
            "public key is $PUBLIC_KEY_SIZE_IN_BYTE bytes, but input is $bodySize bytes"
        }
    }

    /**
     * returns bech32 formatted string of the public key
     *
     * @return <a href="https://github.com/bitcoin/bips/blob/master/bip-0173.mediawiki">bech32</a> formatted string
     */
    @Suppress("MagicNumber")
    fun toBech32(hrpPrefix: String /*= DEFAULT_BECH32_HRP_PREFIX*/): String {   // dev3 comment out default arg
        val encodedBody: ByteArray = Amino.addAminoPrefix(encodingType, body)

        // using `convertBits`, makes each byte hold 5 bits for `Bech32` class to recognize.
        val bech32data = Bech32Utils.convertBits(
            encodedBody, 0, encodedBody.size, 8, 5, true
        )

        val hrp = "${hrpPrefix}${type.toHrpPrefix()}$BECH32_HRP_SUFFIX"
        return Bech32.encode(hrp, bech32data)
    }

    /**
     * stringifies the public key
     *
     * @return stringified public key
     */
    override fun toString(): String = toBech32(DEFAULT_BECH32_HRP_PREFIX) // dev3 add DEFAULT_BECH32_HRP_PREFIX

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PubKey
        return type == other.type && body.contentEquals(other.body)
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

    companion object {
        private const val BECH32_HRP_SUFFIX = "pub"
        private const val PUB_KEY_TYPE_SECP256K1 = "tendermint/PubKeySecp256k1"
        internal const val PUBLIC_KEY_SIZE_IN_BYTE = 33
    }
}
