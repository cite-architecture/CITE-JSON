package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class CiteObjJsonSpec extends FlatSpec {


  val propertyValueString_NumericType:String = """
 {"propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:msA.v1.sequence:1r","propertyValue":"1.0"}
  """

  val propertyValueString_BooleanType:String = """
  {"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1r","propertyValue":"true"}
  """

  val propertyValueString_ControlledVocabType:String = """
  {"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}
  """
  val propertyValueString_StringType:String = """
  {"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"","propertyType":"StringType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1r","propertyValue":"recto","propertyDefType":"StringType"}
  """

  val propertyValueString_CtsUrnType:String = """
  {"propertyDefLabel":"Text URN","propertyDefVocab":"","propertyType":"CtsUrnType","propertyUrn":"urn:cite2:hmt:msA.v1.text:1r","propertyValue":"urn:cts:greekLit:tlg0012.tlg001.msA:1.2","propertyDefType":"CtsUrnType"}
  """

  val propertyValueString_Cite2UrnType:String = """
  {"propertyDefLabel":"Codex URN","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:msA.v1.codex:1r","propertyValue":"urn:cite2:hmt:codex:msA","propertyDefType":"Cite2UrnType"}
  """

  val oneCiteObjectString:String = """
  {"citeObject":{"urn":"urn:cite2:hmt:e4.v1:1r","label":"Escorial Omega 1.12 folio 1r"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:1r","propertyValue":"1.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1r","propertyValue":"true","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}]}
  """

  val someCiteObjectsString:String = """
  {"citeObjects":[{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:1r","label":"Escorial Omega 1.12 folio 1r"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:1r","propertyValue":"1.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1r","propertyValue":"true","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}]},{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:1v","label":"Escorial Omega 1.12 folio 1v"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:1v","propertyValue":"2.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1v","propertyValue":"true","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1v","propertyValue":"verso","propertyDefType":"ControlledVocabType"}]},{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:2r","label":"Escorial Omega 1.12 folio 2r"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:2r","propertyValue":"3.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:2r","propertyValue":"false","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:2r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}]}]}
  """


  "The CiteObjJson Library" should "compile" in {
    val cjo:CiteObjJson = CiteObjJson()
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

  it should "create a CitePropertyImplementation of CtsUrnType" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_CtsUrnType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:msA.v1.text:1r"))
    assert(cpi.propertyDef.propertyType == CtsUrnType)
  }

  it should "create a CitePropertyImplementation of Cite2UrnType" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_Cite2UrnType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:msA.v1.codex:1r"))
    assert(cpi.propertyDef.propertyType == Cite2UrnType)
  }

  it should "create a CitePropertyImplementation of StringType" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_StringType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:e4.v1.rv:1r"))
    assert(cpi.propertyDef.propertyType == StringType)
  }

  it should "create a CitePropertyImplementation of NumericType" in { 
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_NumericType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:msA.v1.sequence:1r"))
    assert(cpi.propertyDef.propertyType == NumericType)
    assert(cpi.propertyValue == 1)
  }

  it should "create a CitePropertyImplementation of BooleanType" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_BooleanType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:e4.v1.fakeboolean:1r"))
    assert(cpi.propertyDef.propertyType == BooleanType)
    assert(cpi.propertyValue == true)
  }

  it should "create a CitePropertyImplementation of ControlledVocabType" in { 
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:CitePropertyImplementation = cjo.citePropertyImplementation(propertyValueString_ControlledVocabType)
    assert(cpi.urn == Cite2Urn("urn:cite2:hmt:e4.v1.rv:1r"))
    assert(cpi.propertyDef.propertyType == ControlledVocabType)
    assert(cpi.propertyValue == "recto")
  }


  it should "create a CiteObject" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val co:CiteObject = cjo.citeObject(oneCiteObjectString)
    assert(co.urn == Cite2Urn("urn:cite2:hmt:e4.v1:1r"))
    assert(co.label == "Escorial Omega 1.12 folio 1r")
    assert(co.propertyList.size == 3)
  }

  it should "create a vector of CiteObjects" in pending


}
