plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

val artefactVersion: String by project

group = "com.onixbyte"
version = artefactVersion

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val devkitVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation(platform("com.onixbyte:devkit-bom:$devkitVersion"))
    implementation("com.onixbyte:devkit-core")
    implementation("com.onixbyte:devkit-utils")
    implementation("com.onixbyte:guid")
    implementation("com.onixbyte:simple-jwt-facade")
    implementation("com.onixbyte:simple-jwt-authzero")
    implementation("com.onixbyte:simple-jwt-spring-boot-starter")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter:1.10.4")
    // annotationProcessor("com.mybatis-flex:mybatis-flex-processor:1.10.4")
    implementation("com.zaxxer:HikariCP")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
