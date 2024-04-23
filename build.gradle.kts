import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.21"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
}

group = "ru.sfedu"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven(url="https://jcenter.bintray.com")
}

extra["springCloudAzureVersion"] = "4.13.0"

dependencies {
    //security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.azure.spring:spring-cloud-azure-starter-active-directory")

    //data
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.6.0")

    //web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-maven-allopen")

    //excel
    implementation("com.github.opendevl:json2flat:1.0.3")

    //logging
    implementation("io.github.microutils:kotlin-logging:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.14.1")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations.all{
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencyManagement {
    imports {
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
