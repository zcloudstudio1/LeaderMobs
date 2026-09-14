plugins {
    `java-library`
    id("com.gradleup.shadow")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
    jar {
        enabled = false
    }
    shadowJar {
        minimize()
    }
}

