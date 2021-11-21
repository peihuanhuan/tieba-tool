import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
}

group = "net.peihuan"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


	implementation("io.github.microutils:kotlin-logging:2.0.11")


	implementation("com.google.code.gson:gson:2.8.8")
	implementation("joda-time:joda-time:2.10.12")
	implementation("org.apache.commons:commons-io:1.3.2")

	implementation("mysql:mysql-connector-java")
	implementation("p6spy:p6spy:3.9.1")
	implementation("com.baomidou:mybatis-plus-boot-starter:3.4.3.4")


	implementation("io.github.bonigarcia:webdrivermanager:3.4.0")
	implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
	// implementation("org.seleniumhq.selenium:selenium-java:4.0.0")
	// implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0")
	// implementation("org.seleniumhq.selenium:selenium-remote-driver:4.0.0")
	// implementation("org.seleniumhq.selenium:selenium-api:4.0.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
