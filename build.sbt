
lazy val scala212 = "2.12.8"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala212, scala211)


ThisBuild / organization := "com.ryanstull"
ThisBuild / version      := "0.1"
ThisBuild / scalaVersion := scala212

lazy val root = (project in file("."))
	.settings(
		name := "ScalaNullSafe",
		crossScalaVersions := supportedScalaVersions,
		libraryDependencies ++= Seq(
			"org.scala-lang" % "scala-reflect" % scalaVersion.value,
			"org.scalactic" %% "scalactic" % "3.0.5" % "test",
			"org.scalatest" %% "scalatest" % "3.0.5" % "test"
		)
	)

addCommandAlias("bench","benchmarks/jmh:run")
addCommandAlias("quick-bench","benchmarks/jmh:run -wi 3 -i 2")

lazy val benchmarks = (project in file("benchmarks"))
	.settings(
		name := "benchmarks",
		sourceDirectory in Jmh := (sourceDirectory in Test).value,
		classDirectory in Jmh := (classDirectory in Test).value,
		dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
		compile in Jmh := (compile in Jmh).dependsOn(compile in Test).value,
		run in Jmh := (run in Jmh).dependsOn(Keys.compile in Jmh).evaluated
	).dependsOn(root % "test->test").enablePlugins(JmhPlugin)