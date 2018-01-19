package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class CiteJsonSpec extends FlatSpec {

  "The CiteJson Library" should "compile" in {
    val cjo:CiteJson = CiteJson("string")
    assert(cjo.exists)
  }

  it should "work with Circe" in {
    val rawJson: String = """{
        "foo": "bar",
        "baz": 123,
        "list of stuff": [ 4, 5, 6 ]
}"""
    val parseResult = parse(rawJson)
    parseResult match {
      case Left(failure) => {
        fail(s"${failure}") 
      }
      case Right(json) => {
        println(s"${json}")
        succeed
      }
      case _ => { fail("neither left nor right?")}
    }
  }

}
