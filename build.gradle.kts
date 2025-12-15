plugins { kotlin("jvm") version "2.2.20" }

group = "com.github.njuro"

version = "1.0"

repositories { mavenCentral() }

dependencies {
    implementation("com.google.ortools:ortools-java:9.14.6206")
    testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(23) }
