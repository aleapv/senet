name := "senet"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.4",
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0"
)

enablePlugins(ScalaJSPlugin)

scalaJSStage in Global := FastOptStage

skip in packageJSDependencies := false