package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.scm._
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
              case Left(er) => throw new CiteException(s"Unable to make PropertyType: ${er}")
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
              case Left(er) => throw new CiteException(s"Unable to make Vocabulary List: ${er}")
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

      def citePropertyImplementation(jsonString:String):CitePropertyImplementation = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val citePropImpl:CitePropertyImplementation = citePropertyImplementation(doc)
          citePropImpl 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citePropertyImplementation(doc:io.circe.Json):CitePropertyImplementation = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get URN
          val propDefUrnString = cursor.downField("propertyUrn").as[String]
          val propUrn:Cite2Urn = {
            propDefUrnString match {
              case Right(s) => Cite2Urn(s)
              case Left(er) => throw new CiteException(s"Unable to make URN: ${er}")
            }          
          }
          val propDefUrn:Cite2Urn = propUrn.dropSelector

          // Get Label
          val propDefLabelString = cursor.downField("propertyDefLabel").as[String]
          val propDefLabel:String = {
            propDefLabelString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make Property Definition Label: ${er}")
            }          
          }

          // Get Property Type
          val propDefTypeString = cursor.downField("propertyType").as[String]
          val propType:CitePropertyType = {
            propDefTypeString match {
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
              case Left(er) => throw new CiteException(s"Unable to make Property Type: ${er}")
            }
          }

           // Get Controlled Vocabulary
          val contVocabString = cursor.downField("propertyDefVocab").as[String]
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
              case Left(er) => throw new CiteException(s"Unable to make Vocabulary List: ${er}")
            }          
          }

          // Make Cite Property Definition
          val citePropDef:CitePropertyDef = CitePropertyDef(propDefUrn,propDefLabel,propType,controlledVocab)

          // We already have the URN, so…
          // … just get value!
          val propertyValueString = cursor.downField("propertyValue").as[String]
          val propertyValue:Any = {
             propertyValueString match {
              case Right(s) => {
                propType match {
                  case CtsUrnType => CtsUrn(s)
                  case Cite2UrnType => Cite2Urn(s)
                  case NumericType => s.toDouble
                  case BooleanType => s.toBoolean
                  case StringType => s
                  case ControlledVocabType => s
                  case _ => throw new CiteException(s"${propType} is not a CitePropertyType.")
                } 
              }
              case Left(er) => throw new CiteException(s"Unable to make Property Value: ${er}")
            }
          }
          val newCiteProperty:CitePropertyImplementation = CitePropertyImplementation(propUrn, citePropDef, propertyValue)

          newCiteProperty

        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citeObject(jsonString:String):CiteObject = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val citeObj:CiteObject = citeObject(doc)
          citeObj 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def citeObject(doc:io.circe.Json):CiteObject = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get URN
          val urnStr = cursor.downField("citeObject").downField("urn").as[String]
          val urn:Cite2Urn = {
            urnStr match {
              case Right(s) => Cite2Urn(s)
              case Left(er) => throw new CiteException(s"Unable to make CiteObject urn: ${er}")
            }          
          }

          // Get Label
          val labelStr = cursor.downField("citeObject").downField("label").as[String]
          val label:String = {
            labelStr match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make CiteObject label: ${er}")
            }          
          }

          // Get Vector of CitePropertyImplementations
          val propVecJson = cursor.downField("citePropertyValues").as[List[Json]]
          val propVec:Vector[CitePropertyImplementation] = {
            propVecJson match {
              case Right(pv) => {
                pv.map(p => citePropertyImplementation(p)).toVector
              }
              case Left(er) => throw new CiteException(s"Unable to make vector of CitePropertyImplementations: ${er}")
            }
          }

          // Construct CiteObject & Return
          val co:CiteObject = CiteObject(urn,label,propVec)
          co
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def vectorOfCiteObjects(jsonString:String):Vector[CiteObject] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val citeObj:Vector[CiteObject] = vectorOfCiteObjects(doc)
          citeObj 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def vectorOfCiteObjects(doc:io.circe.Json):Vector[CiteObject] = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val vecCiteObjJson = cursor.downField("citeObjects").as[List[Json]]
          val vco:Vector[CiteObject] = {
            vecCiteObjJson match {
              case Right(v) => v.map(co => citeObject(co)).toVector
              case Left(er) => throw new CiteException(s"Unable to make vector of CiteObjects: ${er}")
            } 
          }
          vco
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

     def dataModel(jsonString:String):DataModel = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val dm:DataModel = dataModel(doc)
          dm 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def dataModel(doc:io.circe.Json):DataModel = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // get collection
          val collJson = cursor.downField("dataModel").downField("collection").as[String]
          val coll:Cite2Urn = {
            collJson match {
              case Right(c) => Cite2Urn(c)
              case Left(er) => throw new CiteException(s"Unable to make collection urn: ${er}")
            }
          }
          // get description
          val descJson = cursor.downField("dataModel").downField("description").as[String]
          val desc:String = {
            descJson match {
              case Right(d) => d
              case Left(er) => throw new CiteException(s"Unable to make description: ${er}")
            }
          }
          // get label
          val labelJson = cursor.downField("dataModel").downField("label").as[String]
          val label:String = {
            labelJson match {
              case Right(l) => l
              case Left(er) => throw new CiteException(s"Unable to make label: ${er}")
            }
          }
            // get model
          val modelJson = cursor.downField("dataModel").downField("model").as[String]
          val model:Cite2Urn = {
            modelJson match {
              case Right(m) => Cite2Urn(m)
              case Left(er) => throw new CiteException(s"Unable to make model urn: ${er}")
            }
          }

          val dm:DataModel = DataModel(coll,model,label,desc)
          dm
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

     def dataModels(jsonString:String):Vector[DataModel] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val dms:Vector[DataModel] = dataModels(doc)
          dms 
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      def dataModels(doc:io.circe.Json):Vector[DataModel] = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val vecDMJson = cursor.downField("dataModels").as[List[Json]]
          val vecDM:Vector[DataModel] = {
              vecDMJson match {
                case Right(vdm) => vdm.map( dm => dataModel(dm)).toVector
                case Left(er) => throw new CiteException(s"Unable to make vector of DataModels: ${er}")
              }
          } 
          vecDM
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }
  }

}
