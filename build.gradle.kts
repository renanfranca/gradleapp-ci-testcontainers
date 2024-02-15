plugins {
  java
  jacoco
  checkstyle
  alias(libs.plugins.jib)
  alias(libs.plugins.protobuf)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.git.properties)
  // jhipster-needle-gradle-plugins
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

jacoco {
  toolVersion = libs.versions.jacoco.get()
}


checkstyle {
  configFile = rootProject.file("checkstyle.xml")
  toolVersion = libs.versions.checkstyle.get()
}

// Workaround for https://github.com/gradle/gradle/issues/27035
configurations.checkstyle {
  resolutionStrategy.capabilitiesResolution.withCapability("com.google.collections:google-collections") {
    select("com.google.guava:guava:0")
  }
}


jib {
  from {
    image = "eclipse-temurin:21-jre-jammy"
    platforms {
      platform {
        architecture = "amd64"
        os = "linux"
      }
    }
  }
  to {
    image = "gradleapp:latest"
  }
  container {
    entrypoint = listOf("bash", "-c", "/entrypoint.sh")
    ports = listOf("8081")
    environment = mapOf(
     "SPRING_OUTPUT_ANSI_ENABLED" to "ALWAYS",
     "JHIPSTER_SLEEP" to "0"
    )
    creationTime = "USE_CURRENT_TIMESTAMP"
    user = "1000"
  }
  extraDirectories {
    paths {
      path {
        setFrom("src/main/docker/jib")
      }
    }
    permissions = mapOf("/entrypoint.sh" to "755")
  }
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.asProvider().get()}"
  }
}


defaultTasks "bootRun"

springBoot {
  mainClass = "tech.jhipster.gradleapp.GradleappApp"
}


gitProperties {
  failOnNoGitDirectory = false
  keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
}

// jhipster-needle-gradle-plugins-configurations

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repositories
}

group = "tech.jhipster.gradleapp"
version = "0.0.1-SNAPSHOT"

ext {
  // jhipster-needle-gradle-properties
}

dependencies {
  implementation(libs.protobuf.java)
  implementation(libs.commons.lang3)
  implementation(platform(libs.spring.boot.dependencies))
  implementation(libs.spring.boot.starter)
  implementation(libs.spring.boot.configuration.processor)
  implementation(libs.spring.boot.starter.validation)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.actuator)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.hikariCP)
  implementation(libs.hibernate.core)
  runtimeOnly(libs.postgresql)
  // jhipster-needle-gradle-dependencies

  testImplementation(libs.protobuf.java.util)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.reflections)
  testImplementation(libs.postgresql)
  // jhipster-needle-gradle-test-dependencies
}

tasks.test {
  filter {
    includeTestsMatching("*Test.*")
    excludeTestsMatching("*IT.*")
  }
  useJUnitPlatform()
  finalizedBy("jacocoTestReport")
}

val integrationTest = task<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"
  shouldRunAfter("test")
  filter {
    includeTestsMatching("*IT.*")
    excludeTestsMatching("*Test.*")
  }
  useJUnitPlatform()
}

tasks.jacocoTestReport {
  dependsOn("test", "integrationTest")
  reports {
    xml.required.set(true)
    html.required.set(false)
  }
}
