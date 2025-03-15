lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.12"
lazy val scala3 = "3.3.5"
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
			}
		},
		Test / unmanagedSourceDirectories += {
			val sourceDir = (Test / sourceDirectory).value
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => sourceDir / "scala-2"
				case Some((3, _)) => sourceDir / "scala-3"
			}
		},
		libraryDependencies ++= Seq(
			"org.scalatest" %% "scalatest" % "3.2.19" % Test,
			"org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test,
			"org.scalactic" %% "scalactic" % "3.2.19" % Test
		),
		libraryDependencies ++= {
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => Seq(
					"org.scala-lang" % "scala-reflect" % scalaVersion.value
				)
				case Some((3, _)) => Seq(
					"org.scala-lang" %% "scala3-staging" % scalaVersion.value
				)
			}
		},
	)

addCommandAlias("bench", "benchmarks/jmh:run -wi 20 -i 20")
addCommandAlias("quick-bench", "benchmarks/jmh:run -wi 3 -i 2")

lazy val benchmarks = (project in file("benchmarks"))
	.settings(
		name := "benchmarks",
		crossScalaVersions := supportedScalaVersions,
		Test / unmanagedSourceDirectories += {
			val sourceDir = (Test / sourceDirectory).value
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => sourceDir / "scala-2"
				case Some((3, _)) => sourceDir / "scala-3"
			}
		},
		sourceDirectory in Jmh := (sourceDirectory in Test).value,
		classDirectory in Jmh := (classDirectory in Test).value,
		dependencyClasspath in Jmh := (dependencyClasspath in Test).value,
		compile in Jmh := (compile in Jmh).dependsOn(compile in Test).value,
		run in Jmh := (run in Jmh).dependsOn(Keys.compile in Jmh).evaluated,
		skip in publish := true,
		libraryDependencies ++= Seq(
			"dev.optics" %% "monocle-core" % "3.3.0" % "test",
			"dev.optics" %% "monocle-macro" % "3.3.0" % "test",
		),
		libraryDependencies ++= {
			CrossVersion.partialVersion(scalaVersion.value) match {
				case Some((2, _)) => Seq(
					"com.thoughtworks.dsl" %% "keywords-nullsafe" % "1.5.5",
					compilerPlugin("com.thoughtworks.dsl" %% "compilerplugins-bangnotation" % "1.5.5"),
					compilerPlugin("com.thoughtworks.dsl" %% "compilerplugins-reseteverywhere" % "1.5.5")
				)
				case _ => Nil
			}
		}
	).dependsOn(root % "test->test").enablePlugins(JmhPlugin)

updateOptions := updateOptions.value.withGigahorse(false)