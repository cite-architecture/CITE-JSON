name := "Cross-compiled CITE JSON library"

crossScalaVersions := Seq("2.11.8", "2.12.3")

scalaVersion := "2.12.3"

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
      version := "1.0.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "edu.holycross.shot.cite" %%% "xcite" % "2.7.1",
        "edu.holycross.shot" %%% "citeobj" % "5.0.1-m2" from "file:///cite/scala/unmanaged_jars/citeobj_2.12-5.0.1-m2.jar",
        "edu.holycross.shot" %%% "citerelations" % "2.0.1",
        "edu.holycross.shot" %%% "ohco2" % "10.4.0"
      ),
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core",
        "io.circe" %%% "circe-generic",
        "io.circe" %%% "circe-optics",
        "io.circe" %%% "circe-parser"
      ).map(_ % circeVersion)
    ).
    jvmSettings(

    ).
    jsSettings(
      skip in packageJSDependencies := false,
      scalaJSUseMainModuleInitializer in Compile := true
    )

lazy val crossedJVM = crossed.jvm
lazy val crossedJS = crossed.js.enablePlugins(ScalaJSPlugin)
