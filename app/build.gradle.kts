plugins {
  // Apply the shared build logic from a convention plugin. The shared code is located in buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts
  id("buildsrc.convention.kotlin-jvm")

  // Apply the Application plugin to add support for building an executable JVM application
  application

  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
  additionalEditorconfig.set(
    mapOf(
      "indent_size" to "2",
    ),
  )
}

application {
  // Define the Fully Qualified Name for the application main class.
  // (Note that Kotlin compiles `App.kt` to a class with FQN `com.example.app.AppKt`.)
  mainClass = "pl.edu.mimuw.kotlin.task1.App"
}

tasks.named<JavaExec>("run") {
  standardInput = System.`in`
  doFirst {
    logging.captureStandardOutput(LogLevel.QUIET)
    logging.captureStandardError(LogLevel.QUIET)
  }
}

dependencies {
  testImplementation(kotlin("test"))
}
