plugins {
    kotlin("multiplatform") version "2.3.0"
    id("org.jetbrains.dokka") version "2.1.0"
    id("com.vanniktech.maven.publish") version "0.36.0"
    id("com.github.ben-manes.versions") version "0.53.0"
    `maven-publish`
    signing
}

group = "ru.raysmith"
version = "4.0.0"

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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
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
