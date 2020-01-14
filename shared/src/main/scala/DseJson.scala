package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.scm._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.dse._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser.decode

package citejson {

  /** A Class for creating service requests resolving to binary image data
  * and following the IIIF-API.
  *
  * @constructor create a new [[CiteLibraryJson]] service
  */
  @JSExportAll case class DseJson() {

    /** True if this class exists 
    */
      def exists:Boolean = {
      	true
      }

      /** Returns a Vector[DsePassage]
      *
      * @param jsonString JSON string
      */
      def parseVectorOfDsePassages(jsonString:String):Vector[DsePassage] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val vectorOfDseRecs:Vector[DsePassage] = parseVectorOfDsePassages(doc)
          vectorOfDseRecs
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseVectorOfDsePassages: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a Vector[DsePassage]
      * @param doc io.circe.Json
      */
      def parseVectorOfDsePassages(doc:io.circe.Json):Vector[DsePassage] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val drString = cursor.downField("dseRecords").as[List[Json]]
          val drList:List[Json] = {
            drString match {
              case Right(ccd) => ccd
              case _ => throw new CiteJsonException("Failed to parse JSON into list of DsePassage JSON objects.")
            }
          }
          // Make Vector here!
          val drVec:Vector[DsePassage] = {
            drList.map( dr => {
              val dseRec:DsePassage = parseDsePassage(dr)
              dseRec
            }).toVector
          }
          // Done Make Vector!
          drVec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }


      /** Returns a DsePassage
      *
      * @param jsonString JSON string
      */
      def parseDsePassage(jsonString:String):DsePassage = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val dseRec:DsePassage = parseDsePassage(doc)
          dseRec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseDsePassage: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a DsePassage
      * @param doc io.circe.Json
      */
      def parseDsePassage(doc:io.circe.Json):DsePassage = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get label
          val labelString = cursor.get[String]("label")
          val label: String = { 
            labelString match {
              case Right(str) => str
              case Left(er) => throw new CiteJsonException(s"Unable to make label: ${er}")
            }
          }

          // Get label
          val urnString = cursor.get[String]("urn")
          val urn:Cite2Urn = { 
            urnString match {
              case Right(str) => Cite2Urn(str)
              case Left(er) => throw new CiteJsonException(s"Unable to make Cite2Urn out of ${urnString}: ${er}")
            }
          }

          // Get passage
         val txtUrnString = cursor.get[String]("passage")
          val txtUrn:CtsUrn = { 
            txtUrnString match {
              case Right(str) => CtsUrn(str)
              case Left(er) => throw new CiteJsonException(s"Unable to make (passage) URN: ${er}")
            }
          }

          // Get imageroi
          val imgUrnString = cursor.get[String]("imageroi")
          val imgRoiUrn:Cite2Urn = { 
            imgUrnString match {
              case Right(str) => Cite2Urn(str)
              case Left(er) => throw new CiteJsonException(s"Unable to make (imagroi) URN: ${er}")
            }
          }
          
          // Get Surface
          val surfUrnString = cursor.get[String]("surface")
          val surfRoiUrn:Cite2Urn = { 
            surfUrnString match {
              case Right(str) => Cite2Urn(str)
              case Left(er) => throw new CiteJsonException(s"Unable to make (surface) URN: ${er}")
            }
          }

          // make record
          val dseRec:DsePassage = DsePassage(
            urn,
            label,
            txtUrn,
            imgRoiUrn,
            surfRoiUrn
          ) 
          dseRec

        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }


  }
}
