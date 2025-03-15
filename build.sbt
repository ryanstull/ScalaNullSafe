lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.12"
lazy val scala3 = "3.1.3"
lazy val supportedScalaVersions = List(scala211, scala212, scala213, scala3)

ThisBuild / scalaVersion := scala3

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
		Compile / unmanagedSourceDirectories += {
			val sourceDir = (Compile / sourceDirectory).value
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => sourceDir / "scala-2"
				case Some((3, _)) => sourceDir / "scala-3"
				case _ => sourceDir / "scala-2.13"
			}
		},
		Test / unmanagedSourceDirectories += {
			val sourceDir = (Test / sourceDirectory).value
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => sourceDir / "scala-2"
				case Some((3, _)) => sourceDir / "scala-3"
				case _ => sourceDir / "scala-2.13"
			}
		},
		libraryDependencies ++= {
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => Seq(
					"org.scala-lang" % "scala-reflect" % scalaVersion.value
				)
				case Some((3, _)) => Seq(
					"org.scala-lang" %% "scala3-staging" % scalaVersion.value
				)
				case _ => Nil
			}
		},
		libraryDependencies ++= Seq(
			"org.scalatest" %% "scalatest" % "3.2.19" % Test,
			"org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test,
			"org.scalactic" %% "scalactic" % "3.2.19" % Test
		)
	)

addCommandAlias("bench", "benchmarks/jmh:run")
addCommandAlias("quick-bench", "benchmarks/jmh:run -wi 3 -i 2")

val monocleVersion = "1.6.0-RC1"

lazy val benchmarks = (project in file("benchmarks"))
	.settings(
		name := "benchmarks",
		crossScalaVersions := supportedScalaVersions,
		sourceDirectory in Jmh := (sourceDirectory in Test).value,
		classDirectory in Jmh := (classDirectory in Test).value,
		dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
		compile in Jmh := (compile in Jmh).dependsOn(compile in Test).value,
		run in Jmh := (run in Jmh).dependsOn(Keys.compile in Jmh).evaluated,
		skip in publish := true,
		libraryDependencies ++= Seq(
			"com.github.julien-truffaut" %% "monocle-core" % monocleVersion % "test",
			"com.github.julien-truffaut" %% "monocle-macro" % monocleVersion % "test",
			"com.thoughtworks.dsl" %% "keywords-nullsafe" % "1.5.5"
		),
		addCompilerPlugin("com.thoughtworks.dsl" %% "compilerplugins-bangnotation" % "1.5.5"),
		addCompilerPlugin("com.thoughtworks.dsl" %% "compilerplugins-reseteverywhere" % "1.5.5")
	).dependsOn(root % "test->test").enablePlugins(JmhPlugin)

updateOptions := updateOptions.value.withGigahorse(false)