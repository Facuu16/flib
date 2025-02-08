plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://repo.unnamed.team/repository/unnamed-public/")
}

dependencies {
    api("team.unnamed:inject:2.0.1")
    api("io.github.classgraph:classgraph:4.8.174")
    api("net.kyori:adventure-api:4.17.0")
    api("net.kyori:adventure-text-minimessage:4.17.0")
    api("net.kyori:adventure-platform-bukkit:4.3.3")
    api("com.github.ben-manes.caffeine:caffeine:2.9.3")
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        annotationProcessor("org.projectlombok:lombok:1.18.34")

        compileOnly("org.projectlombok:lombok:1.18.34")
        compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    }

    java {
        withJavadocJar()
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }

    group = "io.github.facuu16"
    version = "1.0.0"

    tasks.build {
        dependsOn(tasks.shadowJar)
    }

    tasks.test {
        useJUnitPlatform()
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = "flib-${artifactId}"
                from(components["java"])
            }
        }

        repositories {
            maven(rootProject.layout.buildDirectory.dir("repo"))
        }
    }
}

subprojects {
    dependencies {
        compileOnly(project(path = rootProject.path, configuration = "shadow"))
    }
}