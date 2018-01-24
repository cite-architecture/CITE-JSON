package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2NgramHistoJsonSpec extends FlatSpec {

  val oneNodeJson:String = """{"s":"ἔπεα πτερόεντα προσηύδα","count":"55"}"""

  val someNodesJson:String = """{"ngramHisto":[{"s":"ἔπεα πτερόεντα προσηύδα","count":"55"},{"s":"δ᾽ ἠμείβετ᾽ ἔπειτα","count":"47"},{"s":"Ὣς ἔφαθ᾽ οἳ","count":"43"}]}"""


  "The Ohco2Json library" should "parse a single Json expression into a StringCount" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val sc:StringCount = cjo.o2StringCount(oneNodeJson)
    assert(sc.s == "ἔπεα πτερόεντα προσηύδα")
    assert(sc.count == 55)
  }

  it should "parse a vector of StringCounts" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val sc:Vector[StringCount] = cjo.o2VectorOfStringCounts(someNodesJson) 
    assert(sc.size == 3)
    assert(sc(0).s == "ἔπεα πτερόεντα προσηύδα")
    assert(sc(0).count == 55)
    assert(sc(2).s == "Ὣς ἔφαθ᾽ οἳ")
    assert(sc(2).count == 43)
  }

  it should "not fail when there is no data, but produce an empty vector" in {
    val noNodesJson:String= """{}"""
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val sc:Vector[StringCount] = cjo.o2VectorOfStringCounts(noNodesJson) 
    assert(sc.size == 0)
  }



}
