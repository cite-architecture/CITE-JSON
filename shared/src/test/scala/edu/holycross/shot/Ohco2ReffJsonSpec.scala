package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2ReffJsonSpec extends FlatSpec {


  val reffJson:String = """
  {"reff":["urn:cts:greekLit:tlg0016.tlg001.grc:1.1","urn:cts:greekLit:tlg0016.tlg001.grc:1.2","urn:cts:greekLit:tlg0016.tlg001.grc:1.3","urn:cts:greekLit:tlg0016.tlg001.grc:1.4","urn:cts:greekLit:tlg0016.tlg001.grc:1.5"]} """

  val emptyReffJson:String = """
  {"reff":[]}
  """

  "The ohco2json library" should "parse a vector of CtsUrns" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val reffVector:Vector[CtsUrn] = cjo.o2ReffVector(reffJson)
    assert(reffVector.size == 5)
    assert(reffVector(0) == CtsUrn("urn:cts:greekLit:tlg0016.tlg001.grc:1.1"))
    assert(reffVector(4) == CtsUrn("urn:cts:greekLit:tlg0016.tlg001.grc:1.5"))
  }

  it should "produce an empty vector if there are no reffs" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val reffVector:Vector[CtsUrn] = cjo.o2ReffVector(emptyReffJson)
    assert(reffVector.size == 0)
  }



}
