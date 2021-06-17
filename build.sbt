name := "Cross-compiled CITE JSON library"

crossScalaVersions in ThisBuild := Seq( "2.12.8")
scalaVersion := (crossScalaVersions in ThisBuild).value.last

// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val root = (project in file("."))
  .aggregate(crossed.js, crossed.jvm)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    publish / skip := true
  )
    
val circeVersion = "0.14.1"


lazy val crossed = crossProject(JSPlatform, JVMPlatform).in(file("."))
.settings(
      name := "citejson",
      organization := "edu.holycross.shot",
      version := "3.0.1",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      retrieveManaged := true,
      resolvers += "Nexus" at "https://terracotta.hpcc.uh.edu/nexus/repository/maven-releases/",
      libraryDependencies ++= Seq(
        "org.scalatest" %%% "scalatest" % "3.1.2" % "test",
        "edu.holycross.shot.cite" %%% "xcite" % "4.3.1",
        "edu.holycross.shot" %%% "citeobj" % "7.5.2",
        "edu.holycross.shot" %%% "citerelations" % "2.7.1",
        "edu.holycross.shot" %%% "ohco2" % "10.20.5",
        "edu.holycross.shot" %%% "dse" % "7.1.4",
        "edu.holycross.shot" %%% "scm" % "7.4.1"
      ),
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core",
        "io.circe" %%% "circe-generic",
        "io.circe" %%% "circe-parser"
      ).map(_ % circeVersion),
      credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),

      publishTo := Some("releases" at "https://terracotta.hpcc.uh.edu/nexus/repository/maven-releases/")
    ).
    jvmSettings(
      tutTargetDirectory := file("docs"),
      tutSourceDirectory := file("shared/src/main/tut"),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided",
      )
    ).
    jsSettings(
      scalaJSUseMainModuleInitializer in Compile := true
    )

lazy val crossedJVM = crossed.jvm.enablePlugins(TutPlugin)
lazy val crossedJS = crossed.js
