package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.dse._
import edu.holycross.shot.citerelation._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2CitableNodeJsonSpec extends FlatSpec {

  val oneNodeJson:String = """{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1","text":"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος"} """

  val someNodesJson:String = """
{"citableNodes":[{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1","text":"Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος"},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2","text":"οὐλομένην, ἣ μυρί᾽ Ἀχαιοῖς ἄλγε᾽ ἔθηκε,"},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.3","text":"πολλὰς δ᾽ ἰφθίμους ψυχὰς Ἄϊδι προΐαψεν "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.4","text":"ἡρώων, αὐτοὺς δὲ ἑλώρια τεῦχε κύνεσσιν "},{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5","text":"οἰωνοῖσί τε πᾶσι, Διὸς δ᾽ ἐτελείετο βουλή,"}]}
  """

  val dsesForCorpusJson:String = """
  {
  "citableNodes": [
    {
      "urn": "urn:cts:greekLit:tlg0012.tlg001.msA:1.1",
      "text": "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος"
    },
    {
      "urn": "urn:cts:greekLit:tlg0012.tlg001.msA:1.2",
      "text": "οὐλομένην· ἡ μυρί' Ἀχαιοῖς ἄλγε' ἔθηκεν·"
    }
  ],
  "commentary": {
    "citeTriples": []
  },
  "dse": {
    "dseRecords": [
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901",
        "label": "DSE record for Iliad 1.1",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.1",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il10"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248",
        "label": "DSE record for Iliad 1.2",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.2",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il11"
      }
    ]
  }
}
  """

 val hmtTest1:String = """
{
  "citableNodes": [
    {
      "urn": "urn:cts:greekLit:tlg0012.tlg001.msA:1.2",
      "text": "οὐλομένην· ἡ μυρί' Ἀχαιοῖς ἄλγε' ἔθηκεν·"
    }
  ],
  "commentary": {
    "citeTriples": []
  },
  "dse": {
    "dseRecords": [
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248",
        "label": "DSE record for Iliad 1.2",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.2",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il11"
      }
    ]
  }
}
 """


val textAndCommentary:String = """
{"citableNodes":[{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2","text":"οὐλομένην, ἣ μυρί᾽ Ἀχαιοῖς ἄλγε᾽ ἔθηκε, "}],"dse":{"dseRecords":[]},"commentary":{"citeTriples":[{"urn1":"urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6","relation":"urn:cite2:cite:verbs.v1:commentsOn","urn2":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2"},{"urn1":"urn:cts:greekLit:tlg5026.msA.hmt_xml:1.7","relation":"urn:cite2:cite:verbs.v1:commentsOn","urn2":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2"}]}}
"""

val corpusNoCommentary:String = """
{"citableNodes":[{"urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2","text":"οὐλομένην, ἣ μυρί᾽ Ἀχαιοῖς ἄλγε᾽ ἔθηκε, "}],"dse":{"dseRecords":[]},"commentary":{"citeTriples":[]}}
"""


  "The Ohco2Json library" should "parse a single CitableNode" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val cn:CitableNode = cjo.o2CitableNode(oneNodeJson) 
    assert(cn.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1"))
    assert(cn.text == "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
  }

  it should "parse a vector of Citable Nodes" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val vcn:Vector[CitableNode] = cjo.o2VectorOfCitableNodes(someNodesJson) 
    assert(vcn.size == 5)
    assert(vcn(0).urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1"))
    assert(vcn(0).text == "Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος")
    assert(vcn(4).urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.5"))
    assert(vcn(4).text == "οἰωνοῖσί τε πᾶσι, Διὸς δ᾽ ἐτελείετο βουλή,")
  }

  it should "parse a single ctsUrnString" in {
    val urnString:String = """
{"urnString":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1"} """
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val cu:Option[CtsUrn] = cjo.o2CtsUrnString(urnString) 
    assert(cu == Some(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1")))
  }

  it should "parse an empty ctsUrnString to None" in {
    val urnString:String = """
{"urnString":""} """
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val cu:Option[CtsUrn] = cjo.o2CtsUrnString(urnString) 
    assert(cu == None)
  }

  it should "parse a DSE record attached to a corpus" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val optDse:Option[Vector[DsePassage]] = cjo.dsesForCorpus(dsesForCorpusJson)
    assert(optDse != None)
    assert(optDse.get.size == 2)
    assert(optDse.get(0).surface == Cite2Urn("urn:cite2:hmt:msA.v1:12r"))
    assert(optDse.get(1).urn == Cite2Urn("urn:cite2:hmt:va_dse.v1:il11"))
  }

  it should "parse a DSE record from real HMT data attached to a corpus" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val optDse:Option[Vector[DsePassage]] = cjo.dsesForCorpus(hmtTest1)
    assert(optDse != None)
    assert(optDse.get.size == 1)
  }

  it should "parse Commentaries attached to a corpus" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val optCommentary:Option[Vector[CiteTriple]] = cjo.commentaryForCorpus(textAndCommentary)

    val ctZero:CiteTriple = CiteTriple(
      CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6"),
      Cite2Urn("urn:cite2:cite:verbs.v1:commentsOn"),
      CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2")
    )
    val ctOne:CiteTriple = CiteTriple(
      CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt_xml:1.7"),
      Cite2Urn("urn:cite2:cite:verbs.v1:commentsOn"),
      CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2")
    )

    assert(optCommentary != None)
    assert(optCommentary.get.size == 2)
    assert(optCommentary.get(0) == ctZero )
    assert(optCommentary.get(1) == ctOne )
  }

  it should "parse empty Commentaries attached to a corpus to None" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val optCommentary:Option[Vector[CiteTriple]] = cjo.commentaryForCorpus(corpusNoCommentary)

    assert(optCommentary == None)
  }




}
