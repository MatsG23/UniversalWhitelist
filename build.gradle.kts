import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


plugins {
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "de.mats"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("dev.jorel.CommandAPI:commandapi-shade:6.2.0")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    withType<ShadowJar> {
        archiveClassifier.set("")
    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(project.properties)
        }
    }
}
