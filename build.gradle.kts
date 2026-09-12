import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.publish)
    alias(libs.plugins.versions)
    `maven-publish`
    signing
}

group = "ru.raysmith"
version = "4.5.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        freeCompilerArgs.add("-Xallow-holdsin-contract")
    }

    jvm {
        withSourcesJar()
    }

    js {
        browser()
        nodejs()
    }

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<PublishToMavenRepository> {
        dependsOn(check)
    }

    named<DependencyUpdatesTask>("dependencyUpdates") {
        revision = "release"
        outputFormatter = "json"
        gradleReleaseChannel = GradleReleaseChannel.CURRENT.id

        rejectVersionIf {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
                candidate.version.contains(it, ignoreCase = true)
            }
            val regex = "^[0-9,.v-]+(?!(-?(r|Beta|Alpha)))$".toRegex()
            (stableKeyword || regex.matches(candidate.version)).not()
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = false)
    signAllPublications()

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

signing {
    publishing.publications.forEach {
        sign(it)
    }
}
