plugins {
    id("io.ebean") version "12.15.0"
}

dependencies {
    compileOnly(project(":configuration", configuration = "shadow"))

    implementation("com.h2database:h2:2.1.214")
    implementation("io.ebean:ebean-core:12.15.0")
}