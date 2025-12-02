plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(plugin(libs.plugins.kotlin.jvm))
}

tasks {
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    withType<JavaCompile> {
        sourceCompatibility = libs.versions.java.get()
        targetCompatibility = libs.versions.java.get()
    }
}


// Taken from https://docs.gradle.org/current/userguide/version_catalogs.html#sec:buildsrc-version-catalog
fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
