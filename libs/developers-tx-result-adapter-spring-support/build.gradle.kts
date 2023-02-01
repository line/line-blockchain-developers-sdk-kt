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

plugins {
    kotlin()
    id("org.springframework.boot") version "2.5.14"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.6.21"
    id("signing")
    `maven-publish`
}

kotlinDependencies()
logDependencies()

dependencies {
    implementation(project(Libs.projectTxResultAdapter))
    implementation(Libs.coroutinesCore)
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")

    // for com.linecorp.link.developers.network.account package
    implementation("com.google.guava:guava:31.1-android")
    implementation("org.bitcoinj:bitcoinj-core:0.15.6")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("org.springframework:spring-webflux:5.3.24")
    testImplementation("org.springframework.boot:spring-boot-starter-reactor-netty:2.7.7")
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    test {
        useJUnitPlatform()
    }
}

repositories {
    mavenCentral()
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.map { it.allSource })
}

val javadocJar by tasks.creating(Jar::class) {
    val javadoc = tasks.named("javadoc")
    dependsOn(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc)
}

publishing {
    repositories {
        maven {
            name = "ossRelease"
            url = uri(releasesRepoUrl)
            credentials {
                username = System.getenv(MAVEN_USERNAME)
                password = System.getenv(MAVEN_PASSWORD)
            }
        }
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["kotlin"])

            artifact(sourcesJar)
            artifact(javadocJar)

            groupId = publishGroupId
            artifactId = "developers-tx-result-adapter"
            version = System.getenv("DEPLOY_VERSION")

            pom {
                packaging = "jar"
                name.set("pom: nexus-deploy-test-name")
                description.set("pom: nexus-deploy-test-desc")
                url.set(gitRepositoryUrl)

                licenses {
                    license {
                        name.set(publishLicense)
                        url.set(publishLicenseUrl)
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                        email.set(developerEmail)
                    }
                }
                scm {
                    url.set(gitRepositoryUrl)
                    connection.set(scmConnectionUrl)
                    developerConnection.set(developerConnectionUrl)
                }
            }

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}

signing {
    val secretKey = System.getenv(GPG_PRIVATE_KEY)
    val password = System.getenv(GPG_PASSPHRASE)
    useInMemoryPgpKeys(secretKey, password)
    sign(publishing.publications[project.name])
}
