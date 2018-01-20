package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2JsonSpec extends FlatSpec {

  val catEntry:String = """
  {
    "citationScheme":"book/line",
    "workTitle":"Iliad",
    "exemplarLabel":"None",
    "versionLabel":"Some(Greek. Allen, ed. Perseus Digital Library. Creative Commons Attribution 3.0 License)",
    "groupName":"Homeric Epic",
    "urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:",
    "lang":"grc"
  }"""

  "The Ohco2Json Library" should "compile" in {
    val cjo:Ohco2Json = Ohco2Json()
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
        succeed
      }
      case _ => { fail("neither left nor right?")}
    }
  }

  it should "respond with Left or Right" in {
    val cjo:Ohco2Json = Ohco2Json()
    val parseResult:Either[io.circe.ParsingFailure,io.circe.Json] = parse(catEntry)
    parseResult match {
      case Left(failure) => {
        fail(s"${failure}") 
      }
      case Right(json) => {
        succeed
      }
      case _ => { fail("neither left nor right?")}
    }
  }

  it should "respond to getOrElse" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val doc: Json = parse(catEntry).getOrElse(Json.Null)
    assert(doc != Json.Null)
  }




}
