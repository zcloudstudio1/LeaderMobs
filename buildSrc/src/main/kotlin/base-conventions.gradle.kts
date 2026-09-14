plugins {
    `java-library`
    id("com.gradleup.shadow")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileJava {
        options.release.set(21)
        options.encoding = Charsets.UTF_8.name()
    }
    jar {
        enabled = false
    }
    // Hooks and Adventure providers are loaded reflectively; do not minimize them.
    shadowJar {
        mergeServiceFiles()
    }
}

