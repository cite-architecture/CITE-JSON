package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser.decode

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

      /** Given a JSON string, construct a single Ohco2 CatalogEntry
      * @param jsonString
      */
      def o2CatalogEntry(jsonString:String):CatalogEntry = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val catEntry:CatalogEntry = o2CatalogEntry(doc)
          catEntry
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct a single Ohco2 CatalogEntry
      * @param doc
      */
      def o2CatalogEntry(doc:io.circe.Json):CatalogEntry = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          // Get URN
          val urnString = cursor.get[String]("urn")
          val urn:CtsUrn = { 
            urnString match {
              case Right(s) => CtsUrn(s)
              case Left(er) => throw new CiteException(s"Unable to make URN: ${er}")
            }
          }
          // Get citationScheme
          val citationSchemeString = cursor.get[String]("citationScheme")
          val citationScheme:String = {
            citationSchemeString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make citationScheme: ${er}")
            }
          } 
          // Get lang
          val langString = cursor.get[String]("lang")
          val lang:String = {
            langString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make lang: ${er}")
            }
          } 
          // Get groupName
          val groupNameString = cursor.get[String]("groupName")
          val groupName:String = {
            groupNameString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make groupName: ${er}")
            }
          } 
          // Get workTitle
          val workTitleString = cursor.get[String]("workTitle")
          val workTitle:String = {
            workTitleString match {
              case Right(s) => s
              case Left(er) => throw new CiteException(s"Unable to make workTitle: ${er}")
            }
          } 
          // Get versionLabel 
          val versionLabelString = cursor.get[String]("versionLabel")
          val versionLabel:Option[String] = {
            versionLabelString match {
              case Right(s) if s.size > 0 => Some(s)
              case Right(s) if s.size == 0 => None
              case Left(er) => throw new CiteException(s"Unable to make versionLabel: ${er}")
            }
          } 
          // Get exemplarLabel
          val exemplarLabelString = cursor.get[String]("exemplarLabel")
          val exemplarLabel:Option[String] = {
            exemplarLabelString match {
              case Right(s) if s.size > 0 => Some(s)
              case Right(s) if s.size == 0 => None
              case Left(er) => throw new CiteException(s"Unable to make versionLabel: ${er}")
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
          case e:Exception =>  throw new CiteException(s"${e}")
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
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct an Ohco2 Catalog
      * @param doc
      */
      def o2Catalog(doc:Json):edu.holycross.shot.ohco2.Catalog = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val jsonList = cursor.downField("ctsCatalog").as[List[Json]]
          val ceList:List[Json] = jsonList match {
            case Right(jl) => jl
            case _ => throw new CiteException("Failed to parse catalog into list of Catalog Entry JSON objects.")
          }
          val entryVector:Vector[CatalogEntry] = ceList.map(ce => o2CatalogEntry(ce)).toVector
          val o2Cat:Catalog = Catalog(entryVector)
          o2Cat
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
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
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }

      /** Given a io.circe.Json object, construct an Ohco2 Catalog
      * @param doc
      */
      def o2ReffVector(doc:Json):Vector[CtsUrn] = {
        try {
          if(doc == Json.Null) throw new CiteException(s"Null JSON")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val jsonList = cursor.downField("reff").as[List[String]]
          val ceList:List[String] = jsonList match {
            case Right(jl) => jl
            case _ => throw new CiteException("Failed to parse catalog into list of URNs.")
          }
          val entryVector:Vector[CtsUrn] = ceList.map(ce => CtsUrn(ce)).toVector
          entryVector
        } catch {
          case e:Exception =>  throw new CiteException(s"${e}")
        }
      }
  }

}
