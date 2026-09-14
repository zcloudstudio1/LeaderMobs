plugins {
    id("base-conventions")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}


dependencies {
    compileOnly(project(":common"))

    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

