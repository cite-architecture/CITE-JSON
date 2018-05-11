package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.dse._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser.decode
import io.circe.optics.JsonPath._

package citejson {

  /** A Class for creating service requests resolving to binary image data
  * and following the IIIF-API.
  *
  * @constructor create a new [[Ohco2Json]] service
  */
  @JSExportAll case class Ohco2Json() {
      def exists:Boolean = {
      	true
      }

      /** Given a JSON string, construct a single CtsUrn
      * @param jsonString
      */
      def o2CtsUrnString(jsonString:String):Option[CtsUrn] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val ctsUrn:Option[CtsUrn] = o2CtsUrnString(doc)
          ctsUrn
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"Failed with string param ${jsonString} :: ${e}")
        }
      }

       /** Given a JSON string, construct a single CtsUrn
      * @param jsonString
      */
      def o2CtsUrnString(doc:io.circe.Json):Option[CtsUrn] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
         // Get URN
          val urnString = cursor.get[String]("urnString")
          val urn:Option[CtsUrn] = { 
            urnString match {
              case Right(str) => {
                str.size match {
                  case n if (n > 0) => Some(CtsUrn(str))
                  case _ => None
                }
              }
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          urn 
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a single Ohco2 CatalogEntry
      * @param jsonString
      */
      def o2CatalogEntry(jsonString:String):CatalogEntry = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val catEntry:CatalogEntry = o2CatalogEntry(doc)
          catEntry
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct a single Ohco2 CatalogEntry
      * @param doc
      */
      def o2CatalogEntry(doc:io.circe.Json):CatalogEntry = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          // Get URN
          val urnString = cursor.get[String]("urn")
          val urn:CtsUrn = { 
            urnString match {
              case Right(s) => CtsUrn(s)
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          // Get citationScheme
          val citationSchemeString = cursor.get[String]("citationScheme")
          val citationScheme:String = {
            citationSchemeString match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make citationScheme: ${er}")
            }
          } 
          // Get lang
          val langString = cursor.get[String]("lang")
          val lang:String = {
            langString match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make lang: ${er}")
            }
          } 
          // Get groupName
          val groupNameString = cursor.get[String]("groupName")
          val groupName:String = {
            groupNameString match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make groupName: ${er}")
            }
          } 
          // Get workTitle
          val workTitleString = cursor.get[String]("workTitle")
          val workTitle:String = {
            workTitleString match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make workTitle: ${er}")
            }
          } 
          // Get versionLabel 
          val versionLabelString = cursor.get[String]("versionLabel")
          val versionLabel:Option[String] = {
            versionLabelString match {
              case Right(s) if s.size > 0 => Some(s)
              case Right(s) if s.size == 0 => None
              case Left(er) => throw new CiteJsonException(s"Unable to make versionLabel: ${er}")
            }
          } 
          // Get exemplarLabel
          val exemplarLabelString = cursor.get[String]("exemplarLabel")
          val exemplarLabel:Option[String] = {
            exemplarLabelString match {
              case Right(s) if s.size > 0 => Some(s)
              case Right(s) if s.size == 0 => None
              case Left(er) => throw new CiteJsonException(s"Unable to make versionLabel: ${er}")
            }
          } 
          // Get online
          val online:Boolean = true 

          // Make catalog entry
          val newCat:CatalogEntry = CatalogEntry(
            urn = urn, 
            citationScheme = citationScheme, 
            lang = lang, 
            groupName = groupName, 
            workTitle = workTitle, 
            versionLabel = versionLabel, 
            exemplarLabel = exemplarLabel, 
            online = online)

          newCat
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct an Ohco2 Catalog
      * @param jsonString
      */
      def o2Catalog(jsonString:String):edu.holycross.shot.ohco2.Catalog = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2Cat:Catalog = o2Catalog(doc)
          o2Cat
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct an Ohco2 Catalog
      * @param doc
      */
      def o2Catalog(doc:Json):edu.holycross.shot.ohco2.Catalog = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val jsonList = cursor.downField("ctsCatalog").as[List[Json]]
          val ceList:List[Json] = jsonList match {
            case Right(jl) => jl
            case _ => throw new CiteJsonException("Failed to parse catalog into list of Catalog Entry JSON objects.")
          }
          val entryVector:Vector[CatalogEntry] = ceList.map(ce => o2CatalogEntry(ce)).toVector
          val o2Cat:Catalog = Catalog(entryVector)
          o2Cat
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a Vector of CtsUrns
      * @param jsonString
      */
      def o2ReffVector(jsonString:String):Vector[CtsUrn] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2RefVec:Vector[CtsUrn] = o2ReffVector(doc)
          o2RefVec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct an Ohco2 Catalog
      * @param doc
      */
      def o2ReffVector(doc:Json):Vector[CtsUrn] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val jsonList = cursor.downField("reff").as[List[String]]
          val ceList:List[String] = jsonList match {
            case Right(jl) => jl
            case _ => throw new CiteJsonException("Failed to parse catalog into list of URNs.")
          }
          val entryVector:Vector[CtsUrn] = ceList.map(ce => CtsUrn(ce)).toVector
          entryVector
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a CitableNode
      * @param jsonString
      */
      def o2CitableNode(jsonString:String):CitableNode = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2CitNode:CitableNode = o2CitableNode(doc)
          o2CitNode
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

       /** Given a io.circe.Json object, construct a CitableNode
      * @param doc
      */
      def o2CitableNode(doc:Json):CitableNode = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          // Get URN
          val urnString = cursor.get[String]("urn")
          val urn:CtsUrn = { 
            urnString match {
              case Right(s) => CtsUrn(s)
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          // Get Text
          val textString = cursor.get[String]("text")
          val text:String = { 
            textString match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          val cn:CitableNode = CitableNode(urn, text)
          cn
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a Vector of CitableNodes
      * @param jsonString
      */
      def o2VectorOfCitableNodes(jsonString:String):Vector[CitableNode] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2CitNodes:Vector[CitableNode] = o2VectorOfCitableNodes(doc)
          o2CitNodes
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

       /** Given a io.circe.Json object, construct a Vector of CitableNodes
      * @param doc
      */
      def o2VectorOfCitableNodes(doc:Json):Vector[CitableNode] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val jsonList = cursor.downField("citableNodes").as[List[Json]]
          val ceList:List[Json] = jsonList match {
            case Right(jl) => jl
            case _ => throw new CiteJsonException("Failed to parse catalog into list of Catalog Entry JSON objects.")
          }
          val nodeVector:Vector[CitableNode] = ceList.map(ce => o2CitableNode(ce)).toVector
          nodeVector
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a StringCount
      * @param jsonString
      */
      def o2StringCount(jsonString:String):StringCount = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2StrCnt:StringCount = o2StringCount(doc)
          o2StrCnt
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON object, construct a StringCount
      * @param doc
      */
      def o2StringCount(doc:Json):StringCount = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val js = cursor.get[String]("s")
          val s:String = { 
            js match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          val jc = cursor.get[String]("count")
          val c:Int = { 
            jc match {
              case Right(c) => c.toInt
              case Left(er) => throw new CiteJsonException(s"Unable to make URN: ${er}")
            }
          }
          StringCount(s,c)
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a Vector of StringCounts
      * @param jsonString
      */
      def o2VectorOfStringCounts(jsonString:String):Vector[StringCount] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val o2VecStrCnt:Vector[StringCount] = o2VectorOfStringCounts(doc)
          o2VecStrCnt
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Given a JSON string, construct a Vector of StringCounts
      * @param doc
      */
      def o2VectorOfStringCounts(doc:Json):Vector[StringCount] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          // Get a list of stringcount Jsons
          val possibleSCJsonList = cursor.downField("ngramHisto").as[List[Json]]

          val sCJsonList:List[Json] = possibleSCJsonList match {
            case Right(scl) => scl
            case _ => {
                val emptyList:List[Json] = List() 
                emptyList
            }
          }
          val vectorStrCnt:Vector[StringCount] = sCJsonList.map( scJson => {
            val sc:StringCount = o2StringCount(scJson)
            sc
          }).toVector
          vectorStrCnt
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

        def dsesForCorpus(jsonString:String):Option[Vector[DseRecord]] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val dseRec:Option[Vector[DseRecord]] = dsesForCorpus(doc)
          dseRec 
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }     

      def dsesForCorpus(doc:io.circe.Json):Option[Vector[DseRecord]] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val dseRecsJson = cursor.downField("dse").as[Json]
          //println(s"dseRecsJson: ${dseRecsJson}")
          val returnVec:Option[Vector[DseRecord]] = {
            dseRecsJson match {
              case Right(j) => {
                val cjo:DseJson = DseJson()
                val testParse = cjo.parseVectorOfDseRecords(j)
                testParse.size match {
                  case n if (n > 0) => Some(testParse)
                  case _ => None
                }
              }
              case Left(_) => None
            }
          }
          returnVec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      } 

  }

}
