name := "Cross-compiled CITE JSON library"

// Depends on libraries that only work under 2.12?
crossScalaVersions in ThisBuild := Seq( "2.12.4")
scalaVersion := (crossScalaVersions in ThisBuild).value.last

lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}

    )

val circeVersion = "0.9.0"


lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "citejson",
      organization := "edu.holycross.shot",
      version := "2.4.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      retrieveManaged := true,
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "edu.holycross.shot.cite" %%% "xcite" % "3.3.0",
        "edu.holycross.shot" %%% "citeobj" % "7.0.0",
        "edu.holycross.shot" %%% "citerelations" % "2.0.4",
        "edu.holycross.shot" %%% "ohco2" % "10.7.0",
        "edu.holycross.shot" %%% "dse" % "3.0.0",
        "edu.holycross.shot" %%% "scm" % "6.0.0"
      ),
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core",
        "io.circe" %%% "circe-generic",
        "io.circe" %%% "circe-optics",
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
