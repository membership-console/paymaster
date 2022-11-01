plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("groovy")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.6.2"
    id("io.micronaut.test-resources") version "3.6.2"
    id("org.flywaydb.flyway") version "9.6.0"
}

version = "1.0.0-SNAPSHOT"
group = "cc.rits.membership.console.paymaster"

val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    // micronaut
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut:micronaut-validation")

    // annotation
    implementation("jakarta.annotation:jakarta.annotation-api")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")

    // logging
    runtimeOnly("ch.qos.logback:logback-classic")

    // database
    implementation("org.postgresql:postgresql")

    // runtime only
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.codehaus.groovy:groovy-sql")
}


application {
    mainClass.set("cc.rits.membership.console.paymaster.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("cc.rits.membership.console.paymaster.*")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/paymaster"
    user = "paymaster"
    password = "paymaster"
}



