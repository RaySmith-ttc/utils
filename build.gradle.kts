import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    java
    signing
    `maven-publish`
    kotlin("multiplatform") version "2.0.0"
    id("com.gradleup.nmcp") version "0.0.8"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "ru.raysmith"
version = "3.3.2"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xopt-in=kotlin.RequiresOptIn")
    }

    jvm {
        withJava()
        withSourcesJar()
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
            }
        }
        val jsMain by getting
        val jsTest by getting
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<PublishToMavenRepository> {
        dependsOn(check)
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = project.group.toString()
            version = project.version.toString()
            artifact(project.tasks.register("${name}DokkaJar", Jar::class) {
                group = JavaBasePlugin.DOCUMENTATION_GROUP
                description = "Assembles Kotlin docs with Dokka into a Javadoc jar"
                archiveClassifier.set("javadoc")
                from(tasks.named("dokkaHtml"))
                archiveBaseName.set("${archiveBaseName.get()}-${name}")
            })

            pom {
                packaging = "jar"
                name.set("Utils")
                url.set("https://github.com/RaySmith-ttc/utils")
                description.set("Basic module with utils")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.set("scm:https://github.com/RaySmith-ttc/utils.git")
                    developerConnection.set("scm:git@github.com:RaySmith-ttc/utils.git")
                    url.set("https://github.com/RaySmith-ttc/utils")
                }

                developers {
                    developer {
                        id.set("RaySmith-ttc")
                        name.set("Ray Smith")
                        email.set("raysmith.ttcreate@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "OSSRH"
            val releasesUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().matches(".*(SNAPSHOT|rc.\\d+)".toRegex())) snapshotsUrl else releasesUrl
            credentials {
                username = System.getenv("SONATYPE_USER")
                password = System.getenv("SONATYPE_PASS")
            }
        }
    }
}

signing {
    publishing.publications.forEach {
        sign(it)
    }
}

nmcp {
    publishAllPublications {
        username.set(System.getenv("CENTRAL_SONATYPE_USER"))
        password.set(System.getenv("CENTRAL_SONATYPE_PASS"))
        publicationType.set("AUTOMATIC")
    }
}