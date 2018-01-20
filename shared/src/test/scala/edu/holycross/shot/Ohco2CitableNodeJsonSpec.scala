package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2CitableNodeJsonSpec extends FlatSpec {

  val oneNodeJson:String = """{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1","text":"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος"} """

  val someNodesJson:String = """
{"citableNodes":[{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1","text":"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2","text":"οὐλομένην, ἣ μυρί᾽ Ἀχαιοῖς ἄλγε᾽ ἔθηκε, "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.3","text":"πολλὰς δ᾽ ἰφθίμους ψυχὰς Ἄϊδι προΐαψεν "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.4","text":"ἡρώων, αὐτοὺς δὲ ἑλώρια τεῦχε κύνεσσιν "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5","text":"οἰωνοῖσί τε πᾶσι, Διὸς δ᾽ ἐτελείετο βουλή, "}]}
  """


  "The Ohco2Json library" should "parse a single CitableNode" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val cn:CitableNode = cjo.o2CitableNode(oneNodeJson) 
    assert(cn.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1"))
    assert(cn.text == "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
  }




}
