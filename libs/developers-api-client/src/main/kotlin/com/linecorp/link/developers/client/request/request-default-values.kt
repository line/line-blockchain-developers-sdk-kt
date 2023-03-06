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
@file:Suppress("MagicNumber")
package com.linecorp.link.developers.client.request

const val MIN_LIMIT_1 = 1
const val MAX_LIMIT_50 = 50

const val DEFAULT_PAGE_1 = 1


val NAME_LENGTH_RANGE = 3..20
val SYMBOL_LENGTH_RANGE = 2..5
val META_LENGTH_RANGE = 0..1000
val MEMO_LENGTH_RANGE = 0..1000
