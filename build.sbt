name := "Cross-compiled CITE JSON library"

crossScalaVersions in ThisBuild := Seq( "2.12.8")
scalaVersion := (crossScalaVersions in ThisBuild).value.last

// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}
    )
    
val circeVersion = "0.13.0-M2"


lazy val crossed = crossProject(JSPlatform, JVMPlatform).in(file("."))
.settings(
      name := "citejson",
      organization := "edu.holycross.shot",
      version := "3.0.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += Resolver.bintrayRepo("eumaeus", "maven"),
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      retrieveManaged := true,
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "edu.holycross.shot.cite" %%% "xcite" % "4.2.0",
        "edu.holycross.shot" %%% "citeobj" % "7.4.0",
        "edu.holycross.shot" %%% "citerelations" % "2.6.0",
        "edu.holycross.shot" %%% "ohco2" % "10.18.2",
        "edu.holycross.shot" %%% "dse" % "6.0.3",
        "edu.holycross.shot" %%% "scm" % "7.2.0"
      ),
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core",
        "io.circe" %%% "circe-generic",
        "io.circe" %%% "circe-parser"
      ).map(_ % circeVersion)
    ).
    jvmSettings(
      tutTargetDirectory := file("docs"),
      tutSourceDirectory := file("shared/src/main/tut")
    ).
    jsSettings(
      skip in packageJSDependencies := false,
      scalaJSUseMainModuleInitializer in Compile := true
    )

lazy val crossedJVM = crossed.jvm.enablePlugins(TutPlugin)
lazy val crossedJS = crossed.js
