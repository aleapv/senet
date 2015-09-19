import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

name := "senet"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.2",
  "com.github.japgolly.fork.scalaz" %%% "scalaz-core" % "7.1.2",
  "com.github.japgolly.fork.scalaz" %%% "scalaz-effect" % "7.1.2"  
)

bootSnippet := "render.Main().main(document.getElementById('canvas'));"

updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

initialCommands += "import scalaz._, Scalaz._"