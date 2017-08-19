import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "kata",
      scalaVersion := "2.12.1",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "kata",
    libraryDependencies ++= Seq(scalaTest % Test,
      "commons-io"       % "commons-io"        % "2.5")
  )
