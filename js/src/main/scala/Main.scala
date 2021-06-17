package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import scala.scalajs.js

object Main {
  def main(): Unit = {

    val mini = CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1.1")
    println(mini)

    println("Match function: " + mini.~~(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1")))
  }
}
