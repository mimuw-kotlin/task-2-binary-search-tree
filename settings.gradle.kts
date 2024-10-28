// The settings file is the entry point of every Gradle build.
// Its primary purpose is to define the subprojects.
// It is also used to configure some project-wide configuration, like plugins management, dependencies management, etc.
// https://docs.gradle.org/current/userguide/settings_file_basics.html

dependencyResolutionManagement {
  // Use Maven Central as a default repository (where Gradle will download dependencies) in all subprojects
  repositories {
    mavenCentral()
  }
}

plugins {
  // Use the Foojay Toolchains Plugin to automatically download JDKs required by subprojects
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":app")

rootProject.name = "task1"