plugins {
    `java-gradle-plugin`
    groovy
    id("com.gradle.plugin-publish") version "0.9.8"
    kotlin("jvm") version "1.4.30"
}

group = "de.motionmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.ow2.asm:asm:9.1")
    implementation("org.ow2.asm:asm-commons:9.1")
    implementation("org.ow2.asm:asm-util:9.1")
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.19.0")
    testImplementation("junit", "junit", "4.12")
}

pluginBundle {
    website = "https://github.com/motionmc/motiondoc-gradle"
    vcsUrl = "https://github.com/motionmc/motiondoc-gradle"
    description = "MotionDoc Generator Plugin"
    tags = mutableListOf("documentation")
    plugins.create("motiondoc") {
        id = "de.motionmc.motiondoc"
    }
}