plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
}

group = "me.justeli.trim"
version = "2.0"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.github.TechFortress:GriefPrevention:16.18.7")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4-SNAPSHOT") {
        exclude(group = "org.spigotmc", module = "spigot-api")
    }
    implementation("com.cjcrafter:foliascheduler:0.7.0")
    implementation("org.bstats:bstats-bukkit:3.0.0")
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand(
                "project" to mapOf(
                    "groupId" to project.group.toString(),
                    "name" to project.name,
                    "version" to project.version.toString(),
                    "url" to "https://hangar.papermc.io/Eli/ExplosionsTrimGrass"
                ),
                "versions" to mapOf(
                    "minecraft" to mapOf(
                        "major" to "1.21"
                    )
                )
            )
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())

        exclude("META-INF/**")
        relocate("org.bstats", "me.justeli.trim.shaded.org.bstats")
    }

    build {
        dependsOn(shadowJar)
    }
}
