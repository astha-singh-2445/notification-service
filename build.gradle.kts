plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("java")
	id("com.diffplug.spotless") version "6.22.0"
}

group = "com.singh.astha"
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

spotless {
	java {
		removeUnusedImports()
		eclipse("4.29").configFile("spotless.xml")
		trimTrailingWhitespace()
		endWithNewline()
	}
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.google.auth:google-auth-library-oauth2-http:1.6.0")
	// https://mvnrepository.com/artifact/org.json/json
	implementation("org.json:json:20220320")

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// https://mvnrepository.com/artifact/org.apache.commons/commons-text
	implementation("org.apache.commons:commons-text:1.10.0")

	implementation("org.springframework.boot:spring-boot-starter-security")

	// https://mvnrepository.com/artifact/com.auth0/java-jwt
	implementation("com.auth0:java-jwt:3.19.1")

	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}