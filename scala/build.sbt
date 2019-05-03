name := "kata"

version := "1.0"

scalaVersion := "2.12.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies += "commons-io" % "commons-io" % "2.6"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

logLevel := Level.Debug

scalacOptions += "-deprecation"