
lazy val scala212 = "2.12.8"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala212, scala211)

ThisBuild / scalaVersion := scala212

inThisBuild(List(
	organization := "com.ryanstull",
	homepage := Some(url("https://github.com/ryanstull/ScalaNullSafe")),
	licenses := List("MIT" -> url("https://opensource.org/licenses/MIT")),
	developers := List(
		Developer(
			"rstull123135",
			"Ryan Stull",
			"rstull1250@gmail.com",
			url("http://ryanstull.com")
		)
	)
))


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
		run in Jmh := (run in Jmh).dependsOn(Keys.compile in Jmh).evaluated,
		skip in publish := true
	).dependsOn(root % "test->test").enablePlugins(JmhPlugin)