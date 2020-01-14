package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._ 
import edu.holycross.shot.dse._ 
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class CiteObjJsonSpec extends FlatSpec {

  val vectorOfCite2Urns:String = """
  {"cite2Urns":[{"urnString":"urn:cite2:hmt:compimg.v1:"},{"urnString":"urn:cite2:hmt:vaimg.2017a:"}]}
  """

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

  val vectorWithStatsString:String = """
  {"citeObjects":[{"citeObject":{"urn":"urn:cite2:hmt:textblock.testB:1","label":"Text-block 1"},"citePropertyValues":[{"propertyDefLabel":"ImageROI","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testB.imageroi:1","propertyValue":"urn:cite2:hmt:vaimg.testB:VA012RN_0013@0.0551,0.2305,0.5115,0.494"},{"propertyDefLabel":"Image","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testB.image:1","propertyValue":"urn:cite2:hmt:vaimg.testB:VA012RN_0013"},{"propertyDefLabel":"Notes","propertyDefVocab":"","propertyType":"StringType","propertyUrn":"urn:cite2:hmt:textblock.testB.notes:1","propertyValue":" "},{"propertyDefLabel":"Folio","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testB.folio:1","propertyValue":"urn:cite2:hmt:msA.2017a:12r"}]},{"citeObject":{"urn":"urn:cite2:hmt:textblock.testA:1","label":"Text-block 1"},"citePropertyValues":[{"propertyDefLabel":"ImageROI","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testA.imageroi:1","propertyValue":"urn:cite2:hmt:vaimg.testA:VA012RN_0013@0.0551,0.2305,0.5115,0.494"},{"propertyDefLabel":"Image","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testA.image:1","propertyValue":"urn:cite2:hmt:vaimg.testA:VA012RN_0013"},{"propertyDefLabel":"Notes","propertyDefVocab":"","propertyType":"StringType","propertyUrn":"urn:cite2:hmt:textblock.testA.notes:1","propertyValue":" "},{"propertyDefLabel":"Folio","propertyDefVocab":"","propertyType":"Cite2UrnType","propertyUrn":"urn:cite2:hmt:textblock.testA.folio:1","propertyValue":"urn:cite2:hmt:msA.2017a:12r"}]}],"stats":{"total":"3","showing":"2"}}
  """

  val someCiteObjectsString:String = """
  {"citeObjects":[{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:1r","label":"Escorial Omega 1.12 folio 1r"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:1r","propertyValue":"1.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1r","propertyValue":"true","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}]},{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:1v","label":"Escorial Omega 1.12 folio 1v"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:1v","propertyValue":"2.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:1v","propertyValue":"true","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:1v","propertyValue":"verso","propertyDefType":"ControlledVocabType"}]},{"citeObject":{"urn":"urn:cite2:hmt:e4.v1:2r","label":"Escorial Omega 1.12 folio 2r"},"citePropertyValues":[{"propertyDefUrn":"urn:cite2:hmt:e4.v1.sequence:","propertyDefLabel":"Page sequence","propertyDefVocab":"","propertyType":"NumericType","propertyUrn":"urn:cite2:hmt:e4.v1.sequence:2r","propertyValue":"3.0","propertyDefType":"NumericType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.fakeboolean:","propertyDefLabel":"Boolean for Testing","propertyDefVocab":"","propertyType":"BooleanType","propertyUrn":"urn:cite2:hmt:e4.v1.fakeboolean:2r","propertyValue":"false","propertyDefType":"BooleanType"},{"propertyDefUrn":"urn:cite2:hmt:e4.v1.rv:","propertyDefLabel":"Recto or Verso","propertyDefVocab":"recto,verso","propertyType":"ControlledVocabType","propertyUrn":"urn:cite2:hmt:e4.v1.rv:2r","propertyValue":"recto","propertyDefType":"ControlledVocabType"}]}]}
  """

  val dsesForCiteObject:String = """
{
  "citeObjects": [
    {
      "citeObject": {
        "urn": "urn:cite2:hmt:msA.v1:12r",
        "label": "Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 12r"
      },
      "citePropertyValues": [
        {
          "propertyDefLabel": "ImageROI",
          "propertyDefVocab": "",
          "propertyType": "Cite2UrnType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.imageROI:12r",
          "propertyValue": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.208,0.2087,0.086,0.0225"
        },
        {
          "propertyDefLabel": "Image",
          "propertyDefVocab": "",
          "propertyType": "Cite2UrnType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.image:12r",
          "propertyValue": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013"
        },
        {
          "propertyDefLabel": "Text",
          "propertyDefVocab": "",
          "propertyType": "CtsUrnType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.text:12r",
          "propertyValue": "urn:cts:greekLit:tlg0012.tlg001.allen:1.1-1.25"
        },
        {
          "propertyDefLabel": "Manuscript siglum",
          "propertyDefVocab": "",
          "propertyType": "StringType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.siglum:12r",
          "propertyValue": "msA"
        },
        {
          "propertyDefLabel": "Page sequence",
          "propertyDefVocab": "",
          "propertyType": "NumericType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.sequence:12r",
          "propertyValue": "8.0"
        },
        {
          "propertyDefLabel": "Recto or Verso",
          "propertyDefVocab": "recto,verso",
          "propertyType": "ControlledVocabType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.rv:12r",
          "propertyValue": "recto"
        },
        {
          "propertyDefLabel": "Codex URN",
          "propertyDefVocab": "",
          "propertyType": "Cite2UrnType",
          "propertyUrn": "urn:cite2:hmt:msA.v1.codex:12r",
          "propertyValue": "urn:cite2:hmt:codex:msA"
        }
      ]
    }
  ],
  "dse": {
    "dseRecords": [
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.57426499,0.19896789,0.20962199,0.02350917",
        "label": "DSE record for scholion msA 1.4",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.4",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol3"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.09125620,0.11955275,0.70064910,0.06909404",
        "label": "DSE record for scholion msA 1.1",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.1",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol0"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.56166476,0.49541284,0.21114929,0.02637615",
        "label": "DSE record for scholion msA 1.9",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.9",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol8"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.57311951,0.24827982,0.22451317,0.04644495",
        "label": "DSE record for scholion msA 1.6",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.6",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol5"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.16265750,0.18721330,0.62733868,0.02752294",
        "label": "DSE record for scholion msA 1.3",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.3",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol2"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.3116,0.3273,0.0255",
        "label": "DSE record for Iliad 1.5",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.5",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il14"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.56166476,0.39650229,0.25200458,0.10149083",
        "label": "DSE record for scholion msA 1.8",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.8",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol7"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.0611,0.2252,0.4675,0.0901",
        "label": "DSE record for Iliad 1.1",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.1",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il10"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.4069,0.3664,0.0278",
        "label": "DSE record for Iliad 1.10",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.10",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il19"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.16265750,0.17631881,0.62733868,0.02494266",
        "label": "DSE record for scholion msA 1.2",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.2",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol1"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1652,0.2905,0.3463,0.0255",
        "label": "DSE record for Iliad 1.4",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.4",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il13"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1592,0.3874,0.3664,0.0278",
        "label": "DSE record for Iliad 1.9",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.9",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il18"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1642,0.2725,0.3323,0.0248",
        "label": "DSE record for Iliad 1.3",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.3",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il12"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.3288,0.3373,0.0255",
        "label": "DSE record for Iliad 1.6",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.6",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il15"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.56510118,0.20871560,0.22451317,0.05160550",
        "label": "DSE record for scholion msA 1.5",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.5",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol4"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.56204658,0.29386468,0.23864070,0.10693807",
        "label": "DSE record for scholion msA 1.7",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.7",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol6"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1612,0.3701,0.3604,0.0255",
        "label": "DSE record for Iliad 1.8",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.8",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il17"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1642,0.3498,0.3534,0.0255",
        "label": "DSE record for Iliad 1.7",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.7",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il16"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.57082856,0.50802752,0.22336770,0.06192661",
        "label": "DSE record for scholion msA 1.10",
        "passage": "urn:cts:greekLit:tlg5026.msA.hmt:1.10",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:schol9"
      },
      {
        "imageroi": "urn:cite2:hmt:vaimg.2017a:VA012RN_0013@0.1632,0.2523,0.3323,0.0248",
        "label": "DSE record for Iliad 1.2",
        "passage": "urn:cts:greekLit:tlg0012.tlg001.msA:1.2",
        "surface": "urn:cite2:hmt:msA.v1:12r",
        "urn": "urn:cite2:hmt:va_dse.v1:il11"
      }
    ]
  },
  "stats": {
    "total": "1",
    "showing": "1"
  }
}
  """

  val cite2UrnStringJson:String = """
      {"urnString":"urn:cite2:hmt:e4.v1:1r"}
  """
  val cite2UrnStringJsonEmpty:String = """
      {"urnString":""}
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

  it should "create an Option[Cite2Urn] from Json" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:Option[Cite2Urn] = cjo.cite2UrnString(cite2UrnStringJson)
    assert(cpi == Some(Cite2Urn("urn:cite2:hmt:e4.v1:1r")))
  }

  it should "create an Option[Cite2Urn] with value None from empty Json" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpi:Option[Cite2Urn] = cjo.cite2UrnString(cite2UrnStringJsonEmpty)
    assert(cpi == None)
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

  it should "create a vector of CiteObjects" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val vco:Vector[CiteObject] = cjo.vectorOfCiteObjects(someCiteObjectsString)
    assert(vco.size == 3)
    assert(vco(0).urn == Cite2Urn("urn:cite2:hmt:e4.v1:1r"))
    assert(vco(0).label == "Escorial Omega 1.12 folio 1r")
    assert(vco(2).urn == Cite2Urn("urn:cite2:hmt:e4.v1:2r"))
    assert(vco(0).propertyList.size == 3)
  }

  it should "create a vector of CiteObjects that has stats" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val vco:Vector[CiteObject] = cjo.vectorOfCiteObjects(vectorWithStatsString)
    assert(vco.size == 2)
    assert(vco(0).urn == Cite2Urn("urn:cite2:hmt:textblock.testB:1"))
    assert(vco(0).label == "Text-block 1")
    assert(vco(1).urn == Cite2Urn("urn:cite2:hmt:textblock.testA:1"))
    assert(vco(1).label == "Text-block 1")
  }

  it should "create a vector of CiteObjects and report its stats" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val vco:Vector[CiteObject] = cjo.vectorOfCiteObjects(vectorWithStatsString)
    assert(vco.size == 2)
    assert(vco(0).urn == Cite2Urn("urn:cite2:hmt:textblock.testB:1"))
    assert(vco(0).label == "Text-block 1")
    assert(vco(1).urn == Cite2Urn("urn:cite2:hmt:textblock.testA:1"))
    assert(vco(1).label == "Text-block 1")
    val stats:Option[Map[String,Int]] = cjo.statsForVectorOfCiteObjects(vectorWithStatsString)
    assert( stats != None )
    assert( stats.get("total") == 3)
    assert( stats.get("showing") == 2)
  }

  it should "report None when a Vector of Cite Object has no stats, or mysterious ones" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val vco:Vector[CiteObject] = cjo.vectorOfCiteObjects(someCiteObjectsString)
    assert(vco.size == 3)
    assert(vco(0).urn == Cite2Urn("urn:cite2:hmt:e4.v1:1r"))
    assert(vco(0).label == "Escorial Omega 1.12 folio 1r")
    assert(vco(2).urn == Cite2Urn("urn:cite2:hmt:e4.v1:2r"))
    assert(vco(0).propertyList.size == 3)
    val stats:Option[Map[String,Int]] = cjo.statsForVectorOfCiteObjects(someCiteObjectsString)
    assert( stats == None )
  }

  it should "create a vector of Cite2Urns" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val vcu:Vector[Cite2Urn] = cjo.vectorOfCite2Urns(vectorOfCite2Urns)
    assert(vcu.size == 2)
    assert(vcu(0) == Cite2Urn("urn:cite2:hmt:compimg.v1:"))
    assert(vcu(1) == Cite2Urn("urn:cite2:hmt:vaimg.2017a:"))
  }

  it should "parse a VectorOfDsePassages as part of a VectorOfCiteObjects reply" in {
     val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val optDse:Option[Vector[DsePassage]] = cjo.dsesForVectorOfCiteObjects(dsesForCiteObject)
    assert(optDse != None)
    assert(optDse.get.size == 20)
    assert(optDse.get(0).surface == Cite2Urn("urn:cite2:hmt:msA.v1:12r"))
    assert(optDse.get(1).urn == Cite2Urn("urn:cite2:hmt:va_dse.v1:schol0"))
  }

  
}
