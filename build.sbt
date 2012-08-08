name := "puki-conf-converter"

organization := "rms.bo"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies := Seq(
   "org.scalaz" %% "scalaz-core" % "6.0.4"
  ,"net.databinder.dispatch" %% "core" % "0.9.0"
  ,"com.typesafe.akka" % "akka-actor" % "2.0.2"
  ,"com.github.scala-incubator.io" %% "scala-io-core" % "0.4.0"
  ,"com.github.scala-incubator.io" %% "scala-io-file" % "0.4.0"
  ,"org.scala-tools.time" % "time_2.9.1" % "0.5"
  ,"joda-time" % "joda-time" % "2.1"
  ,"org.joda" % "joda-convert" % "1.2"
  ,"org.specs2" %% "specs2" % "1.11" % "test"
  ,"org.mockito" % "mockito-all" % "1.9.0" % "test"
  ,"junit" % "junit" % "4.10" % "test"
  ,"org.pegdown" % "pegdown" % "1.1.0" % "test"
)

testOptions in (Test, test) += Tests.Argument("console", "html", "junitxml")

initialCommands := """
import scalaz._
import Scalaz._
import scalax.io._
import scalax.file._
import ImplicitConversions._
import org.scala_tools.time.Imports._
import dispatch._
"""
