repositories {
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly(project(":configuration"))
    api("co.aikar:acf-bukkit:0.5.1-SNAPSHOT")
}