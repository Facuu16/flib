plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }

    group = "io.github.facuu16.flib"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.unnamed.team/repository/unnamed-public/")
        maven("https://jitpack.io")
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        annotationProcessor("org.projectlombok:lombok:1.18.34")

        compileOnly("org.projectlombok:lombok:1.18.34")
        compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

        implementation("team.unnamed:inject:2.0.1")
        implementation("io.github.classgraph:classgraph:4.8.174")
        implementation("net.kyori:adventure-api:4.17.0")
        implementation("net.kyori:adventure-text-minimessage:4.17.0")
        implementation("net.kyori:adventure-platform-bukkit:4.3.3")
        implementation("com.github.ben-manes.caffeine:caffeine:2.9.3")
    }

    tasks.build {
        dependsOn(tasks.shadowJar)
    }

    tasks.test {
        useJUnitPlatform()
    }
}

subprojects {
    dependencies {
        compileOnly(rootProject)
    }
}