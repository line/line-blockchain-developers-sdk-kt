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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {

    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    buildscript {
        repositories {
            mavenCentral()
            mavenLocal()
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }
    }
}

subprojects {
    // Non-existent type correction
    // https://kotlinlang.org/docs/reference/kapt.html#non-existent-type-correction
    this.plugins.findPlugin("kapt")?.run {
        if (this is org.jetbrains.kotlin.gradle.plugin.KaptExtension) {
            this.correctErrorTypes = true
        }
    }
}


plugins {
    base
    java
    kotlin("jvm") version Versions.kotlin apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

group = projectGroupId

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


fun setPublishingContext() {
    checkEnvironmentAndThrow()
    version = System.getenv(DEPLOY_VERSION)
    nexusPublishing {
        repositories {
            sonatype {
                nexusUrl.set(uri(releasesRepoUrl))
                snapshotRepositoryUrl.set(uri(snapshotRepoUrl))
                username.set(System.getenv(SONATYPE_USERNAME))
                password.set(System.getenv(SONATYPE_PASSWORD))
            }
        }
    }
}

if (project.gradle.startParameter.taskNames.contains("publish")) {
    setPublishingContext()
}
