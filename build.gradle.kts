import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    java
    kotlin("jvm") version "1.2.50"
    `maven-publish`
    maven
}

group = "com.rnett.ligraph.eve"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/exposed")
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("https://jitpack.io")
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")

    compile("org.postgresql:postgresql:42.2.2.jre7")
    compile("org.jetbrains.exposed:exposed:0.10.2")
    compile("com.github.salomonbrys.kotson:kotson:2.5.0")
    compile("com.github.kizitonwose.time:time:1.0.1")
    compile("org.slf4j:slf4j-simple:1.7.25")

    compile("io.ktor:ktor-client-core:0.9.3-alpha-5")
    compile("io.ktor:ktor-client-apache:0.9.3-alpha-5")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

artifacts.add("archives", sourcesJar)

publishing {
    publications {
        create("default", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar)
        }
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}
kotlin {
    experimental.coroutines = Coroutines.ENABLE
}