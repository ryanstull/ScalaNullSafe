
name := "ScalaNullSafe"

version := "0.1"
organization := "com.ryanstull"
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"org.scala-lang" % "scala-reflect" % "2.11.7",
	"org.scalactic" %% "scalactic" % "3.0.5" % "test",
	"org.scalatest" %% "scalatest" % "3.0.5" % "test"
)