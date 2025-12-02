rootProject.name = "buildSrc"


// Taken from https://docs.gradle.org/current/userguide/version_catalogs.html#sec:buildsrc-version-catalog
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
