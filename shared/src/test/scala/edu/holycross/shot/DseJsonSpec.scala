package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import edu.holycross.shot.dse._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class DseJsonSpec extends FlatSpec {

  val dseRecordJson1:String = """{
      "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.57426499,0.19896789,0.20962199,0.02350917",
      "label": "DSE record for scholion msA 1.4",
      "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.4",
      "surface": "urn:cite2:hmt:msA.v1:12r",
      "urn": "urn:cite2:hmt:va_dse.v1:schol3"
    }"""

  val vectorOfDsePassagesJson1:String = """
  {
  "dseRecords": [
    {
      "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901",
      "label": "DSE record for Iliad 1.1",
      "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.1",
      "surface": "urn:cite2:hmt:msA.v1:12r",
      "urn": "urn:cite2:hmt:va_dse.v1:il10"
    }
  ]
}
  """
  val vectorOfDsePassagesJson2:String = """
  {
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
  """

val hmtTest1:String = """
{
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
"""

  "The DseJson Library" should "compile" in {
    val cjo:DseJson = DseJson()
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

  it should "parse a Json to a single DSE Record object" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:DsePassage = cjo.parseDsePassage(dseRecordJson1)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.57426499,0.19896789,0.20962199,0.02350917")
    val objUrn = Cite2Urn("urn:cite2:hmt:va_dse.v1:schol3")
    assert(vdr.imageroi == imageRoiUrn)
    assert(vdr.urn == objUrn)
  }

  it should "parse Json into a Vector of DSE Records with one element" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DsePassage] = cjo.parseVectorOfDsePassages(vectorOfDsePassagesJson1)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901")
    assert(vdr(0).imageroi == imageRoiUrn)
    assert(vdr.size == 1)
  }

  it should "parse Json into a Vector of DSE Records with more than one element" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DsePassage] = cjo.parseVectorOfDsePassages(vectorOfDsePassagesJson2)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901")
    assert(vdr(0).imageroi == imageRoiUrn)
    assert(vdr.size == 2)
  }

   it should "parse Json from real HMT data" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DsePassage] = cjo.parseVectorOfDsePassages(hmtTest1)
    assert(vdr.size == 1)
  }
 


}
