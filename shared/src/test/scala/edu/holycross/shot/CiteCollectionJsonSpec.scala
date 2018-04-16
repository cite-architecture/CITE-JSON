package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class CiteCollectionJsonSpec extends FlatSpec {

  val citeLibraryMetadataJson:String = """{"name":"scs-akka test library","urn":"urn:cite2:cex:demo.2017_1:servicetest","license":"CC Share Alike.  For details, see more info."}"""

  val citePropertyDefJson:String = """
  {"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}}
  """

  val citeCollectionDefJson:String = """
 {"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"urn:cite2:hmt:e4.v1.sequence:","labellingProperty":"urn:cite2:hmt:e4.v1.label:","license":"CC-attribution-share-alike","urn":"urn:cite2:hmt:e4.v1:","collectionLabel":"Pages of the Omega 1.12 manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.rv:","label":"Recto or Verso","propertyType":"ControlledVocabType","vocabularyList":"recto,verso"}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.sequence:","label":"Page sequence","propertyType":"NumericType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.fakeboolean:","label":"Boolean for Testing","propertyType":"BooleanType","vocabularyList":""}}]}} 
  """

  val vectorOfCiteCollectionDefsJson:String = """
  {"citeCollectionDefs":[{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"urn:cite2:hmt:msA.v1.sequence:","labellingProperty":"urn:cite2:hmt:msA.v1.label:","license":"CC-attribution-share-alike","urn":"urn:cite2:hmt:msA.v1:","collectionLabel":"Pages of the Venetus A manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.siglum:","label":"Manuscript siglum","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.sequence:","label":"Page sequence","propertyType":"NumericType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.rv:","label":"Recto or Verso","propertyType":"ControlledVocabType","vocabularyList":"recto,verso"}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.codex:","label":"Codex URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.text:","label":"Text","propertyType":"CtsUrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.image:","label":"Image","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:msA.v1.imageROI:","label":"ImageROI","propertyType":"Cite2UrnType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"urn:cite2:hmt:e4.v1.sequence:","labellingProperty":"urn:cite2:hmt:e4.v1.label:","license":"CC-attribution-share-alike","urn":"urn:cite2:hmt:e4.v1:","collectionLabel":"Pages of the Omega 1.12 manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.rv:","label":"Recto or Verso","propertyType":"ControlledVocabType","vocabularyList":"recto,verso"}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.sequence:","label":"Page sequence","propertyType":"NumericType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4.v1.fakeboolean:","label":"Boolean for Testing","propertyType":"BooleanType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:vaimg.2017a.label:","license":"CC-attribution-share-alike","urn":"urn:cite2:hmt:vaimg.2017a:","collectionLabel":"Images of the Venetus A manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:vaimg.2017a.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:vaimg.2017a.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:vaimg.2017a.rights:","label":"Rights","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:e4img.2017a.label:","license":"CC-attribution-share-alike","urn":"urn:cite2:hmt:e4img.2017a:","collectionLabel":"Images of the Omega 1.12 manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:e4img.2017a.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4img.2017a.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:e4img.2017a.rights:","label":"Rights","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"urn:cite2:fufolio:msChad.2017a.sequence:","labellingProperty":"urn:cite2:fufolio:msChad.2017a.name:","license":"CC-attribution-share-alike","urn":"urn:cite2:fufolio:msChad.2017a:","collectionLabel":"CITE Collection capturing Folio Sides of the St. Chad Manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.sequence:","label":"Sequence","propertyType":"NumericType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.siglum:","label":"Siglum","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.description:","label":"Description","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.rv:","label":"Recto/Verso","propertyType":"ControlledVocabType","vocabularyList":"recto,verso"}},{"citePropertyDef":{"urn":"urn:cite2:fufolio:msChad.2017a.name:","label":"Name","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:textblock.2017a.label:","license":"CC 3.0 NC-BY-SA","urn":"urn:cite2:hmt:textblock.2017a:","collectionLabel":"Defined text-blocks on the Venetus A manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.folio:","label":"Folio","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.image:","label":"Image","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.imageroi:","label":"ImageROI","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.2017a.notes:","label":"Notes","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:textblock.testA.label:","license":"CC 3.0 NC-BY-SA","urn":"urn:cite2:hmt:textblock.testA:","collectionLabel":"Defined text-blocks on the Venetus A manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.folio:","label":"Folio","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.image:","label":"Image","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.imageroi:","label":"ImageROI","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testA.notes:","label":"Notes","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:textblock.testB.label:","license":"CC 3.0 NC-BY-SA","urn":"urn:cite2:hmt:textblock.testB:","collectionLabel":"Defined text-blocks on the Venetus A manuscript"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.urn:","label":"URN","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.folio:","label":"Folio","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.image:","label":"Image","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.imageroi:","label":"ImageROI","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:textblock.testB.notes:","label":"Notes","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:cite:datamodels.v1.label:","license":"Public domain","urn":"urn:cite2:cite:datamodels.v1:","collectionLabel":"CITE data models"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:cite:datamodels.v1.urn:","label":"Data model","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:cite:datamodels.v1.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:cite:datamodels.v1.description:","label":"Description","propertyType":"StringType","vocabularyList":""}}]}},{"citeCollectionDef":{"citeCollectionInfo":{"orderingProperty":"","labellingProperty":"urn:cite2:hmt:binaryimg.v1.label:","license":"Public domain","urn":"urn:cite2:hmt:binaryimg.v1:","collectionLabel":"Collections of binary images"}},"citeProperties":{"citeCollectionPropertyDefs":[{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.urn:","label":"Binary Image","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.label:","label":"Label","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.collection:","label":"Image Collection","propertyType":"Cite2UrnType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.protocol:","label":"Protocol","propertyType":"ControlledVocabType","vocabularyList":"iiif,iipImageString"}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.path:","label":"Path on Server","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.url:","label":"Service URN Base","propertyType":"StringType","vocabularyList":""}},{"citePropertyDef":{"urn":"urn:cite2:hmt:binaryimg.v1.rights:","label":"Rights","propertyType":"StringType","vocabularyList":""}}]}}]}
  """

  val dataModelsJson:String = """
  {"dataModels":[{"dataModel":{"collection":"urn:cite2:hmt:vaimg.2017a:","description":"CITE architecture model of citable images.  See documentation at <http://cite-architecture.github.io/imagemodel/>.","label":"Citable image model","model":"urn:cite2:cite:datamodels.v1:imagemodel"}},{"dataModel":{"collection":"urn:cite2:hmt:vaimg.2017a:","description":"CITE architectur model of binary images manipulable by URN reference.  See <TBA>.","label":"Citable image model","model":"urn:cite2:cite:datamodels.v1:binaryimg"}}]}
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

  it should "be able to set a CitePropertyType" in {
    val cpt:CitePropertyType = CtsUrnType
    assert (cpt.toString == "CtsUrnType" )
  }

  it should "be able to parse strings to boolean values" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    assert(cjo.parseBoolean("true") == true)
    assert(cjo.parseBoolean("false") == false)
  }

  it should "parse a CitePropertyDefinition" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val cpd:CitePropertyDef = cjo.citePropertyDef(citePropertyDefJson)
    assert(cpd.urn == Cite2Urn("urn:cite2:hmt:msA.v1.urn:"))
  }

  it should "parse a CiteCollection Definition" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val ccd:CiteCollectionDef = cjo.citeCollectionDef(citeCollectionDefJson)
    assert(ccd.urn == Cite2Urn("urn:cite2:hmt:e4.v1:"))
  }

  it should "parse a vector of CiteCollectionDefinitions into  CiteCatalog" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val citecat:CiteCatalog = cjo.citeCatalog(vectorOfCiteCollectionDefsJson)
    assert(citecat.collections.size == 10)
  }

  it should "parse a vector of DataModels" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val dataModelsVec:Vector[DataModel] = cjo.dataModels(dataModelsJson)
    assert(dataModelsVec.size == 2)
    assert(dataModelsVec(0).collection == Cite2Urn("urn:cite2:hmt:vaimg.2017a:"))
    assert(dataModelsVec(1).model == Cite2Urn("urn:cite2:cite:datamodels.v1:binaryimg"))
  }

  it should "parse a Json expression of a CiteLibrary's metadata" in {
    val cjo:CiteObjJson = CiteObjJson()
    assert(cjo.exists)
    val libMD:Map[String,String] = cjo.parseLibraryInfo(citeLibraryMetadataJson)
    assert(libMD("urn") == "urn:cite2:cex:demo.2017_1:servicetest")
    assert(libMD("name") == "scs-akka test library")
    assert(libMD("license") == "CC Share Alike.  For details, see more info.")
  }


}
