
name := "ScalaNullSafe"

lazy val commonSettings = Seq(
	version := "0.1",
	organization := "com.ryanstull",
	scalaVersion := "2.11.7"
)

lazy val root = (project in file("."))
	.settings(commonSettings: _*)
	.settings(
		name := "ScalaNullSafe"
	).dependsOn(macros)

lazy val macros = (project in file("macros"))
    .settings(commonSettings: _*)
	.settings(
		name := "macros",
		libraryDependencies ++= Seq(
			"org.scala-lang" % "scala-reflect" % "2.11.7"
		)
	)