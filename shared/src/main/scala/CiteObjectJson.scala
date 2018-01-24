package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser.decode

package citejson {

  /** A Class for creating service requests resolving to binary image data
  * and following the IIIF-API.
  *
  * @constructor create a new [[CiteObjJson]] service
  */
  @JSExportAll case class CiteObjJson() {
      def exists:Boolean = {
      	true
      }


      def parseBoolean(str:String):Boolean = {
        val b:Boolean = {
          str match {
            case "true" => true
            case _ => false
          }
        }
        b
      }

      def citeCollectionDef(jsonString:String):CiteCollectionDef = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val collDef:CiteCollectionDef = citeCollectionDef(doc)
          collDef
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citeCollectionDef(doc:io.circe.Json):CiteCollectionDef = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get URN
          val urnString = cursor.downField("citeCollectionDef").downField("citeCollectionInfo").get[String]("urn")
          val collUrn:Cite2Urn = {
            urnString match {
              case Right(s) => Cite2Urn(s)
              case Left(er) => throw new CiteException(s"Unable to make URN: ${er}")
            }          
          }
          // Get collectionLabel
          val collLabelString = cursor.downField("citeCollectionDef").downField("citeCollectionInfo").get[String]("collectionLabel")
          val collLabel:String = {
            collLabelString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to find Collection Label: ${er}")
            }          
          }
          // Get license
          val collLicenceString = cursor.downField("citeCollectionDef").downField("citeCollectionInfo").get[String]("license")
          val collLicense:String = {
            collLicenceString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to find Collection License: ${er}")
            }          
          }
          // Get labelling propoerty
          val collLabelPropString = cursor.downField("citeCollectionDef").downField("citeCollectionInfo").get[String]("labellingProperty")
          val collLabelProperty:Option[Cite2Urn] = {
            collLabelPropString match {
              case Right(s) => {
                  if (s == "") {
                    None
                  } else {
                    Some(Cite2Urn(s)) 
                  }
                }
              case Left(er) => throw new CiteException(s"Unable to find Collection Labelling Property: ${er}")
            }          
          }
          // Get ordering property
          val collOrderPropString = cursor.downField("citeCollectionDef").downField("citeCollectionInfo").get[String]("orderingProperty")
          val collOrderProperty:Option[Cite2Urn] = {
            collOrderPropString match {
              case Right(s) => {
                  if (s == "") {
                    None
                  } else {
                    Some(Cite2Urn(s)) 
                  }
                }
              case Left(er) => throw new CiteException(s"Unable to find Collection Ordering Property: ${er}")
            }          
          }

          // Get Vector of Properties
          val propertyDefsString = cursor.downField("citeProperties").downField("citeCollectionPropertyDefs").as[List[Json]]
          val propDefList:List[Json] = {
            propertyDefsString match {
              case Right(cds) => cds
              case _ => throw new CiteException("Failed to parse catalog into list of Catalog Entry JSON objects.")
            }
          }
          val propertyDefs:Vector[CitePropertyDef] = propDefList.map( pd =>{
            citePropertyDef(pd)
          }).toVector

          val returnCCD:CiteCollectionDef = CiteCollectionDef(collUrn,collLabel,propertyDefs,collLicense,collLabelProperty,collOrderProperty)
          returnCCD
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citePropertyDef(jsonString:String):CitePropertyDef = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val propDef:CitePropertyDef = citePropertyDef(doc)
          propDef
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citePropertyDef(doc:io.circe.Json):CitePropertyDef = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get URN
          val urnString = cursor.downField("citePropertyDef").get[String]("urn")
          val propUrn:Cite2Urn = {
            urnString match {
              case Right(s) => Cite2Urn(s)
              case Left(er) => throw new CiteException(s"Unable to make URN: ${er}")
            }          
          }
          // Get Label
          val labelString = cursor.downField("citePropertyDef").get[String]("label")
          val propLabel:String = {
            labelString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make PropertyLabel: ${er}")
            }          
          }

          // Get Property Type
          val propTypeString = cursor.downField("citePropertyDef").get[String]("propertyType")
          val propType:CitePropertyType = {
            propTypeString match {
              case Right(s) => {
                s match {
                  case "CtsUrnType" => CtsUrnType 
                  case "Cite2UrnType" => Cite2UrnType 
                  case "NumericType" => NumericType 
                  case "BooleanType" => BooleanType 
                  case "StringType" => StringType 
                  case "ControlledVocabType" => ControlledVocabType 
                  case _ => throw new CiteException(s"${s} is not a CitePropertyType.")
                } 
              }
              case Left(er) => throw new CiteException(s"Unable to make PropertyLabel: ${er}")
            }
          }

           // Get Controlled Vocabulary
          val contVocabString = cursor.downField("citePropertyDef").get[String]("vocabularyList")
          val controlledVocab:Vector[String] = {
            contVocabString match {
              case Right(s) => {
                s match {
                  case "" => Vector.empty
                  case _ => {
                    s.split(",").toVector
                  }
                }
              } 
              case Left(er) => throw new CiteException(s"Unable to make PropertyLabel: ${er}")
            }          
          }

          val returnCPD:CitePropertyDef = CitePropertyDef(propUrn,propLabel,propType,controlledVocab)
          returnCPD
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citeCatalog(jsonString:String):CiteCatalog = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val citeCat:CiteCatalog = citeCatalog(doc)
          citeCat 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citeCatalog(doc:io.circe.Json):CiteCatalog = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get Vector of CiteCollectionDefs
          val collectionDefsString = cursor.downField("citeCollectionDefs").as[List[Json]]
          val collDefList:List[Json] = {
            collectionDefsString match {
              case Right(ccd) => ccd
              case _ => throw new CiteException("Failed to parse catalog into list of Collection Def JSON objects.")
            }
          }
          val collectionDefs:Vector[CiteCollectionDef] = collDefList.map( cd => {
            citeCollectionDef(cd)
          }).toVector
          
          val citeCat:CiteCatalog = CiteCatalog(collectionDefs)
          citeCat

        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

  }

}
