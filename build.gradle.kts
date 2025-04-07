plugins {
    kotlin("plugin.jpa") version libs.versions.kotlin.get()
    kotlin("jvm") version libs.versions.kotlin.get()
    id("io.quarkus") version libs.versions.quarkus.get()
}

group = "com.mpolivaha"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(platform("io.quarkus.platform:quarkus-bom:${libs.versions.quarkus.get()}"))
    annotationProcessor("org.mapstruct:mapstruct-processor:${libs.versions.mapstruct.get()}")

    implementation(libs.bundles.quarkus)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${libs.versions.jackson.get()}")
    implementation("org.mapstruct:mapstruct:${libs.versions.mapstruct.get()}")
    implementation("com.github.docker-java:docker-java:${libs.versions.dockerClient.get()}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${libs.versions.kotlin.get()}")
    implementation("org.flywaydb:flyway-core:${libs.versions.flyway.get()}")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
