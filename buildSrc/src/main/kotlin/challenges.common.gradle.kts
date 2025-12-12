import org.gradle.api.tasks.testing.logging.TestExceptionFormat

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")


plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.findLibrary("kotlin-reflect").get())
    testImplementation(libs.findBundle("kotest").get())
}

kotlin {
    jvmToolchain(libs.findVersion("java").get().toString().toInt())
}

tasks.test {
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
    systemProperty("kotest.framework.classpath.scanning.autoscan.disable", "true")
    systemProperty("kotest.framework.disable.test.nested.jar.scanning", "true")
    systemProperty("kotest.framework.discovery.jar.scan.disable", "true")
    if (project.hasProperty("kotest.tags")) systemProperty("kotest.tags", project.findProperty("kotest.tags")!!)

    useJUnitPlatform {
        includeEngines("kotest")
    }
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

// Export test fixtures for other projects, see https://stackoverflow.com/a/61682321/
configurations {
    create("test")
}

tasks.register<Jar>("testArchive") {
    archiveBaseName.set("std-test")
    from(project.the<SourceSetContainer>()["test"].output)
}

artifacts {
    add("test", tasks["testArchive"])
}
