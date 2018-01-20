package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2CatalogEntryJsonSpec extends FlatSpec {

  val catEntry:String = """
  {
    "citationScheme":"book/line",
    "workTitle":"Iliad",
    "exemplarLabel":"",
    "versionLabel":"Greek. Allen, ed. Perseus Digital Library. Creative Commons Attribution 3.0 License",
    "groupName":"Homeric Epic",
    "urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:",
    "lang":"grc"
  }"""


  "The ohco2json library" should "parse a single catalog entry" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val catEntryObject:CatalogEntry = cjo.catalogEntry(catEntry)
    assert(catEntryObject.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:"))
  }





}
