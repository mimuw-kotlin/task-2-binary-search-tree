plugins {
  // The Kotlin DSL Plugin provides a convenient way of developing convention plugins.
  // Convention plugins are located in `src/main/kotlin`, with the file extension `.gradle.kts`,
  // and are applied in the project's `build.gradle.kts` files as required.
  `kotlin-dsl`
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  // add a dependency on Kotlin Gradle Plugin, so that convention plugins can apply KGP.
  implementation(libs.kotlinGradlePlugin)
}
