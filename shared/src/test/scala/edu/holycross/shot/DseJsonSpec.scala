package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import edu.holycross.shot.dse._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class DseJsonSpec extends FlatSpec {

  val dseRecordJson1:String = """
  {"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.1","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il10","label":"DSE record for Iliad 1.1"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il10","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il10","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il10","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1"}]}}
  """

  val vectorOfDseRecordsJson1:String = """
{"dseRecords":[{"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.1","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il10","label":"DSE record for Iliad 1.1"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il10","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il10","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il10","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1"}]}}]}
  """
  val vectorOfDseRecordsJson2:String = """
 {"dseRecords":[{"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.1","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il10","label":"DSE record for Iliad 1.1"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il10","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il10","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il10","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.1"}]}},{"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.2","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.2","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il11","label":"DSE record for Iliad 1.2"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il11","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il11","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il11","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.2"}]}},{"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1642,0.2725,0.3323,0.0248","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.3","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.3","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il12","label":"DSE record for Iliad 1.3"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il12","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1642,0.2725,0.3323,0.0248"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il12","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il12","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.3"}]}}]} 
  """

val hmtTest1:String = """
{"dseRecords":[{"imageroi":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248","surface":"urn:cite2:hmt:msA.v1:12r","label":"DSE record for Iliad 1.2","passage":"urn:cts:greekLit:tlg0012.tlg001.msA:1.2","citeObject":{"citeObject":{"urn":"urn:cite2:hmt:va_dse.v1:il11","label":"DSE record for Iliad 1.2"},"citePropertyValues":[{"propertyDefLabel":"Image region of interest","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.imageroi:il11","propertyValue":"urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248"},{"propertyDefLabel":"Artifact surface","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.surface:il11","propertyValue":"urn:cite2:hmt:msA.v1:12r"},{"propertyDefLabel":"Text passage","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:va_dse.v1.passage:il11","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.2"}]}}]}
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
    val vdr:DseRecord = cjo.parseDseRecord(dseRecordJson1)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901")
    val objUrn = Cite2Urn("urn:cite2:hmt:va_dse.v1:il10")
    assert(vdr.imageroi == imageRoiUrn)
    assert(vdr.citeObject.urn == objUrn)
  }

  it should "parse Json into a Vector of DSE Records with one element" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DseRecord] = cjo.parseVectorOfDseRecords(vectorOfDseRecordsJson1)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901")
    assert(vdr(0).imageroi == imageRoiUrn)
    assert(vdr.size == 1)
  }

  it should "parse Json into a Vector of DSE Records with more than one element" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DseRecord] = cjo.parseVectorOfDseRecords(vectorOfDseRecordsJson2)
    val imageRoiUrn:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901")
    assert(vdr(0).imageroi == imageRoiUrn)
    assert(vdr.size == 3)
  }

   it should "parse Json from real HMT data" in {
    val cjo:DseJson = DseJson()
    assert(cjo.exists)
    val vdr:Vector[DseRecord] = cjo.parseVectorOfDseRecords(hmtTest1)
    assert(vdr.size == 1)
  }
 


}
