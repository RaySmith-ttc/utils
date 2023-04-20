import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.7.20"
    id("maven-publish")
}

allprojects {
    group = "ru.raysmith"
    version = "1.4.3"

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }

        withType<PublishToMavenRepository> {
            dependsOn(test)
        }
    }

    repositories {
        mavenCentral()
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/raysmith-ttc/utils")
            credentials {
                username = System.getenv("GIT_USERNAME")
                password = System.getenv("GIT_TOKEN_PUBLISH")
            }
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    val jupiterVersion = "5.9.0"
    testImplementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:3.23.1")
}