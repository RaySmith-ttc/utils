import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.21"
    id("maven-publish")
}

allprojects {
    group = "ru.raysmith"
    version = "1.2.6"

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"

                sourceCompatibility = JavaVersion.VERSION_1_8.toString()
                targetCompatibility = JavaVersion.VERSION_1_8.toString()
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
        withType<Test> {
            useJUnitPlatform()
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
    @Suppress("UnstableApiUsage") withSourcesJar()
    @Suppress("UnstableApiUsage") withJavadocJar()
}

val jupiterVersion = "5.8.2"
dependencies {
//    implementation(kotlin("stdlib", "1.6.10"))

    testImplementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:3.22.0")
}