import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version = "1.3.11"
val ktor_version = "1.0.0"

buildscript {
    val kotlin_version = "1.3.11"
    repositories {
        jcenter()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/kotlinx")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

apply {
    plugin("kotlinx-serialization")
}

plugins {
    java
    kotlin("jvm") version "1.3.11"
    `maven-publish`
    maven
}

group = "com.rnett.ligraph.eve"
version = "1.0-SNAPSHOT"


val serializiation_version = "0.9.1"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/exposed")
    maven("https://jitpack.io")
    maven("https://kotlin.bintray.com/kotlinx")
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")

    implementation("com.github.rnett:core:1.3.7")

    implementation("org.jetbrains.exposed:exposed:0.11.2")
    implementation("com.github.salomonbrys.kotson:kotson:2.5.0")
    implementation("com.github.kizitonwose.time:time:1.0.1")
    implementation("org.slf4j:slf4j-simple:1.7.25")

    compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializiation_version")
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