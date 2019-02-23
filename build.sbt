
lazy val commonSettings = Seq(
	version := "0.1",
	organization := "com.ryanstull",
	scalaVersion := "2.11.7"
)

lazy val root = (project in file("."))
	.settings(commonSettings: _*)
	.settings(
		name := "ScalaNullSafe",
		libraryDependencies ++= Seq(
			"org.scala-lang" % "scala-reflect" % "2.11.7",
			"org.scalactic" %% "scalactic" % "3.0.5" % "test",
			"org.scalatest" %% "scalatest" % "3.0.5" % "test"
		)
	)

addCommandAlias("bench","benchmarks/jmh:run")

lazy val benchmarks = (project in file("benchmarks"))
	.settings(commonSettings: _*)
	.settings(
		name := "benchmarks",
		sourceDirectory in Jmh := (sourceDirectory in Test).value,
		classDirectory in Jmh := (classDirectory in Test).value,
		dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
		compile in Jmh := (compile in Jmh).dependsOn(compile in Test).value,
		run in Jmh := (run in Jmh).dependsOn(Keys.compile in Jmh).evaluated
	).dependsOn(root % "test->test").enablePlugins(JmhPlugin)