val modId: String = property("mod_id") as String

plugins {
    id("idea")
    id("java")
    id("net.neoforged.gradle.userdev") version "7.0.119"
}

base {
    archivesName = "elytrathrusters"
    group = "org.zornco"
    version = "0.1.0"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
}

idea {

}
sourceSets.main {
    java {
        srcDir("src/main/java")
    }

    resources {
        srcDir("src/main/resources")
        srcDir("src/generated/resources")
    }
}
minecraft.modIdentifier("elytrathrusters")

runs {
    // applies to all the run configs below
    configureEach {
        // Recommended logging data for a userdev environment
        systemProperty("forge.logging.markers", "") // 'SCAN,REGISTRIES,REGISTRYDUMP'

        // Recommended logging level for the console
        systemProperty("forge.logging.console.level", "debug")

        if(!System.getenv().containsKey("CI")) {
            // JetBrains Runtime Hotswap
            //jvmArgument("-XX:+AllowEnhancedClassRedefinition")
        }

        modSource(sourceSets.main.get())
    }

    create("client") {
        // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
        systemProperty("forge.enabledGameTestNamespaces", modId)

        programArguments("--username", "Zorn_Taov")
        programArguments("--width", "1920")
        programArguments("--height", "1080")
    }

    create("server") {
        systemProperty("forge.enabledGameTestNamespaces", modId)
        modSource(project.sourceSets.test.get())
    }

    create("gameTestServer") {
        systemProperty("forge.enabledGameTestNamespaces", modId)
        modSource(project.sourceSets.test.get())
    }

    create("data") {
        dataGenerator(true)

        programArguments("--mod", modId)
        programArguments("--all")
        programArguments("--output", project.file("src/generated/resources").absolutePath)
        programArguments("--existing", project.file("src/main/resources").absolutePath)
    }
}

dependencies {
    // compileOnly("net.minecraft:neoform_joined:1.20.4-20231207.154220")

    implementation(libraries.neoforge)
    testImplementation(libraries.neoforge)
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}