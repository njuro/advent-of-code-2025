plugins { kotlin("jvm") version "2.2.20" }

group = "com.github.njuro"

version = "1.0"

repositories { mavenCentral() }

dependencies {
    implementation("org.ojalgo:ojalgo:56.1.1")
    testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(23) }
