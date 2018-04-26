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
  * @constructor create a new [[CiteLibraryJson]] service
  */
  @JSExportAll case class CiteLibraryJson() {

    /** True if this class exists 
    */
      def exists:Boolean = {
      	true
      }

      /** True if this node is URN-similar to a second URN.
      *
      * @param str String expected to be "true" or "false"
      */
      def parseBoolean(str:String):Boolean = {
        val b:Boolean = {
          str match {
            case "true" => true
            case _ => false
          }
        }
        b
      }

      /** Returns a Map("urn" -> String, "name" -> String, "license" -> String)
      *
      * @param str JSON string
      */
      def parseLibraryInfo(jsonString:String):Map[String,String] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val cursor: HCursor = doc.hcursor
          val urnString = {
            cursor.get[String]("urn") match { 
                case Right(s) => {
                  val testUrn = Cite2Urn(s)
                  s
                }
                case Left(er) => throw new CiteJsonException(s"Unable to get URN: ${er}")
            }  
          }
          val nameString = {
            cursor.get[String]("name") match {
                case Right(s) => s
                case Left(er) => throw new CiteJsonException(s"Unable to get Name: ${er}")
            }  
          }
          val licenseString = {
            cursor.get[String]("license") match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to get License: ${er}")
            }
          }
          val returnMap:Map[String,String] = Map(
              "name" -> nameString,
              "urn" -> urnString,
              "license" -> licenseString
          ).toMap
          returnMap
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Returns a Map(Cite2Urn -> String)
      *
      * @param str JSON string
      */
      def parseLabelMap(jsonString:String):Map[Cite2Urn,String] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val cursor: HCursor = doc.hcursor
          val stringMap:Map[String,String] = {
            cursor.downField("labelMap").as[Map[String,String]] match {
              case Right(s) => s
              case Left(er) => throw new CiteJsonException(s"Unable to get License: ${er}")
            }
          }
          val labelMap = stringMap.map {case (key, value) => (Cite2Urn(key), value )}
          labelMap
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }
  }

}
