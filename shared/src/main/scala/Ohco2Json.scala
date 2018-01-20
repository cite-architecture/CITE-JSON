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

      def catalogEntry(jsonString:String):CatalogEntry = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          if(doc == Json.Null) throw new CiteException(s"Unable to parse: \n ${jsonString}")
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
  }


}
