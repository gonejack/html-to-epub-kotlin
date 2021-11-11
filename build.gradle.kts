import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.fir.expressions.builder.buildArgumentList
import org.jetbrains.kotlin.gradle.targets.js.npm.includedRange
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.psi.addRemoveModifier.addAnnotationEntry

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.graalvm.buildtools.native") version "0.9.7.1"
    application
}

group = "me.youi"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/nexus/content/groups/public/")
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("org.apache.tika:tika-core:2.1.0")
    implementation("net.kemitix:epub-creator:1.1.0")
    implementation("net.sf.jmimemagic:jmimemagic:0.1.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
    implementation("commons-cli:commons-cli:1.5.0")
    implementation("org.jsoup:jsoup:1.14.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

application {
    mainClass.set("MainKt")
}

graalvmNative {
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(17))
            })
            resources.includedPatterns.add(".*/*.png")
            resources.includedPatterns.add(".*/*.properties")
        }
    }
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "16"
}

tasks.register("fatJar", Jar::class.java) {
    archiveFileName.set("html-to-epub.jar")
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes("Main-Class" to application.mainClass)
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) }
    ) {
        exclude("META-INF/MANIFEST.MF")
        exclude("META-INF/*.SF")
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.RSA")
    }

    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach {
        println("add from sources: ${it.name}")
    }
    from(sourcesMain.output)
}
