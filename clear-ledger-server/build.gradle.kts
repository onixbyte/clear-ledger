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
    // options.compilerArgs.add("--enable-preview")
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
    implementation("com.onixbyte:property-guard-spring-boot-starter:$devkitVersion")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
