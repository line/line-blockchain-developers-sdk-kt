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

/**
 * Defines the types of Addresses and PubKeys
 */
internal enum class Type {
    /**
     * Account
     */
    ACCOUNT,

    /**
     * Validator Consensus
     */
    VALIDATOR_CONSENSUS,

    /**
     * Validator Operator
     */
    VALIDATOR_OPERATOR;

    companion object {
        // dev3
        // internal const val DEFAULT_BECH32_HRP_PREFIX = "link"
        internal lateinit var DEFAULT_BECH32_HRP_PREFIX: String

        private const val ACCOUNT_HRP_PREFIX = ""
        private const val VALIDATOR_CONSENSUS_HRP_PREFIX = "valcons"
        private const val VALIDATOR_OPERATOR_HRP_PREFIX = "valoper"

        /**
         * Gets the type from the [hrpTypePrefix]
         */
        operator fun invoke(hrpTypePrefix: String): Type {

            return when (hrpTypePrefix) {
                ACCOUNT_HRP_PREFIX -> ACCOUNT
                VALIDATOR_CONSENSUS_HRP_PREFIX -> VALIDATOR_CONSENSUS
                VALIDATOR_OPERATOR_HRP_PREFIX -> VALIDATOR_OPERATOR
                else -> throw IllegalArgumentException("Illegal HRP Type ('$hrpTypePrefix' is not supported)")
            }
        }

        /**
         * Converts to HRP type prefix
         */
        fun Type.toHrpPrefix(): String {
            return when (this) {
                ACCOUNT -> ACCOUNT_HRP_PREFIX
                VALIDATOR_CONSENSUS -> VALIDATOR_CONSENSUS_HRP_PREFIX
                VALIDATOR_OPERATOR -> VALIDATOR_OPERATOR_HRP_PREFIX
            }
        }
    }

}
