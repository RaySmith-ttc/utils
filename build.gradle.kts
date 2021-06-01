import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.0"
    id("maven-publish")
}

allprojects {
    group = "ru.raysmith"
    version = "1.0.0-rc.4"

    tasks {
        withType<KotlinCompile> {
            println(this)
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
        withType<Test> {
            useJUnitPlatform()
        }
    }

    repositories {
        mavenCentral()
        jcenter()
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/raysmith-ttc/utils")
            credentials {
                username = System.getenv("git.username")
                password = System.getenv("git.publish-token")
            }
        }
    }
}

val jupiterVersion = "5.4.2"
dependencies {
    implementation(kotlin("stdlib", "1.4.31"))
    testImplementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(gradleTestKit())
}