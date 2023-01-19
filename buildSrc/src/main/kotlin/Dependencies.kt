/*
 * Copyright (c) 2020 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec

object Versions {
    const val kotlin = "1.6.21"
    const val kotlinCoroutines = "1.6.4"
    const val mockito = "3.3.3"
    const val mockK = "1.12.4"
    const val mockitoKotlin = "2.2.0"
    const val retrofit2 = "2.9.0"
    const val okhttp = "4.9.1"
}

object Libs {
    const val projectTxResultAdapter = ":developers-tx-result-adapter"

    // kotlin
    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    // coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"

    // log
    const val slf4jApi = "org.slf4j:slf4j-api:1.7.30"
    const val log4jCore = "org.apache.logging.log4j:log4j-core:2.17.1"
    const val log4jApi = "org.apache.logging.log4j:log4j-api:2.17.1"
    const val kotlinLogging = "io.github.microutils:kotlin-logging:1.7.10"

    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2ConverterJackson = "com.squareup.retrofit2:converter-jackson:${Versions.retrofit2}"
    const val retrofit2ConverterScalas = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit2}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLoggingInterceptor ="com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val guava ="com.google.guava:guava:31.1-jre"
}

fun PluginDependenciesSpec.kotlin() {
    kotlin("jvm")
}


fun Project.kotlinDependencies(
    requiresReflect: Boolean = true
) {
    dependencies {
        "implementation"(Libs.kotlinStdlibJdk8)
        "testImplementation"("org.mockito:mockito-inline:${Versions.mockito}")
        "testImplementation"("io.mockk:mockk:${Versions.mockK}")
        "testImplementation"("com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}")

        if (requiresReflect) {
            "implementation"(Libs.kotlinReflect)
        }
    }
}

fun Project.logDependencies() {
    dependencies {
        "implementation"(Libs.slf4jApi)
        "implementation"(Libs.log4jCore)
        "implementation"(Libs.log4jApi)
        "implementation"(Libs.kotlinLogging)
    }
}
