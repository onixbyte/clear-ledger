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
    implementation("com.onixbyte:devkit-core:$devkitVersion")
    implementation("com.onixbyte:devkit-utils:$devkitVersion")
    implementation("com.onixbyte:guid:$devkitVersion")
    implementation("com.onixbyte:simple-jwt-facade:$devkitVersion")
    implementation("com.onixbyte:simple-jwt-authzero:$devkitVersion")
    implementation("com.onixbyte:simple-jwt-spring-boot-starter:$devkitVersion")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter:1.10.4")
    // annotationProcessor("com.mybatis-flex:mybatis-flex-processor:1.10.4")
    implementation("com.zaxxer:HikariCP")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
