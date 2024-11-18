ThisBuild / scalaVersion := "2.13.12"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """TestDemo""",
    libraryDependencies ++= Seq(
      guice
    )
  )
libraryDependencies ++= Seq(
  "javax.persistence" % "javax.persistence-api" % "2.2", // Thêm JPA API
  "org.hibernate" % "hibernate-core" % "5.4.0.Final",   // Thêm Hibernate (nếu bạn đang dùng Hibernate)
  "org.hibernate" % "hibernate-entitymanager" % "5.4.0.Final" // Thêm Entity Manager
)

libraryDependencies += "com.typesafe.play" %% "play" % "2.8.18"
libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-java-jpa" % "2.8.18"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.33" // Kiểm tra phiên bản mới nhất

//libraryDependencies += "com.h2database" % "h2" % "1.4.200"


libraryDependencies += javaWs % "test"

libraryDependencies += "org.awaitility" % "awaitility" % "3.1.5" % "test"
libraryDependencies += "org.assertj" % "assertj-core" % "3.11.1" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "2.23.4" % "test"

Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

ThisBuild / scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked", "-Xlint:-deprecation")
ThisBuild / javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")


PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"

