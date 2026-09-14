plugins {
    id("base-conventions")
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly(project(":common"))

    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
}

tasks {
    shadowJar {
        relocate("net.kyori.adventure", "com.github.xhexed.leadermobs.adventure")
        relocate("net.kyori.examination", "com.github.xhexed.leadermobs.adventure.examination")
        exclude("META-INF/*.SF", "META-INF/*.RSA", "META-INF/*.DSA")
    }
}