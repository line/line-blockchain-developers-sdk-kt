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

package com.linecorp.link.developers.client.request

const val PATTERN_TOKEN_NAME = "^[a-zA-Z0-9]{3,20}$"
const val PATTERN_URI_PATH = "[\\w\\.\\-\\~\\/]+"
const val PATTERN_BASE_URI = "^(https:\\/\\/)$PATTERN_URI_PATH(:[0-9]{1,5})?\\/$"
const val PATTERN_BASE_URI_OR_EMPTY = "^($PATTERN_BASE_URI)?$"
val PATTERN_NOT_TOKEN_NAME = "[^a-zA-Z0-9]$".toRegex()
val SYMBOL_REGEX = "^[A-Z][A-Z0-9]{1,4}$".toRegex()
val TOKEN_NAME_REGEX = PATTERN_TOKEN_NAME.toRegex()
val WALLET_ADDRESS_REGEX = "^t?link[a-zA-Z0-9]{39}$".toRegex()
val BASE_URI_OR_EMPTY_REGEX = PATTERN_BASE_URI_OR_EMPTY.toRegex()
val SERVICE_TOKEN_SYMBOL_REGEX = "^[A-Z][A-Z0-9]{1,4}$".toRegex()
val PATTERN_NUMERIC_VALUE_REGEX = "^[0-9]+$".toRegex()

const val CONTRACT_ID_REGEX = "^[a-f0-9]{8}"
val TOKEN_ID_REGEX = "^[a-f0-9]{16}".toRegex()
val TOKEN_TYPE_REGEX = "^[a-f0-9]{8}".toRegex()
