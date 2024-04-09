import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("maven-publish")
    kotlin("multiplatform") version "1.9.10"
    signing
//    id("com.gradleup.nmcp") version "0.0.4"
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
        withSourcesJar()
    }
    js(IR) {
        browser {
            commonWebpackConfig(Action {
                cssSupport {
                    enabled.set(true)
                }
            })
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
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

allprojects {
    group = "ru.raysmith"
    version = "2.5.1"

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
            dependsOn(check)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = project.name
            groupId = project.group.toString()
            version = project.version.toString()
            from(components["java"])
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
                username = System.getenv("CENTRAL_SONATYPE_USER")
                password = System.getenv("CENTRAL_SONATYPE_PASS")
            }
        }
    }
}

//nmcp {
//    publish("release") {
//        username.set(System.getenv("CENTRAL_SONATYPE_USER"))
//        password.set(System.getenv("CENTRAL_SONATYPE_PASS"))
//        publicationType.set("USER_MANAGED")
//        publicationType.set("AUTOMATIC")
//    }
//}

signing {
    sign(configurations.archives.get())
    sign(publishing.publications["release"])
}