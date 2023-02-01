/*
 * Copyright (c) 2022 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

rootProject.name = "developers-tx-result-adapter"

// libs
listOf(
    "developers-tx-result-adapter",
    "developers-tx-result-adapter-spring-support",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/libs/$it")
}
