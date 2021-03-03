import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.31"
    id("maven-publish")
}

allprojects {
    group = "ru.raysmith"
    version = "1.0.0-rc.1"

    tasks {
        withType<KotlinCompile> {
            println(this)
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
        }
    }

    repositories {
        mavenCentral()
        jcenter()
    }
}

tasks {
    test {
        useJUnitPlatform()
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