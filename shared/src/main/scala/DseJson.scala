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

      /** Returns a Vector[DseRecord]
      *
      * @param jsonString JSON string
      */
      def parseVectorOfDseRecords(jsonString:String):Vector[DseRecord] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val vectorOfDseRecs:Vector[DseRecord] = parseVectorOfDseRecords(doc)
          vectorOfDseRecs
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseVectorOfDseRecords: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a Vector[DseRecord]
      * @param doc io.circe.Json
      */
      def parseVectorOfDseRecords(doc:io.circe.Json):Vector[DseRecord] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val drString = cursor.downField("dseRecords").as[List[Json]]
          val drList:List[Json] = {
            drString match {
              case Right(ccd) => ccd
              case _ => throw new CiteJsonException("Failed to parse JSON into list of DseRecord JSON objects.")
            }
          }
          // Make Vector here!
          val drVec:Vector[DseRecord] = {
            drList.map( dr => {
              val dseRec:DseRecord = parseDseRecord(dr)
              dseRec
            }).toVector
          }
          // Done Make Vector!
          drVec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }


      /** Returns a DseRecord
      *
      * @param jsonString JSON string
      */
      def parseDseRecord(jsonString:String):DseRecord = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val dseRec:DseRecord = parseDseRecord(doc)
          dseRec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseDseRecord: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a DseRecord
      * @param doc io.circe.Json
      */
      def parseDseRecord(doc:io.circe.Json):DseRecord = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor

          // Get label
         val labelString = cursor.get[String]("label")
          val label:String = { 
            labelString match {
              case Right(str) => str
              case Left(er) => throw new CiteJsonException(s"Unable to make label string: ${er}")
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

          // Get CiteObject
          val citeObjString = cursor.get[io.circe.Json]("citeObject")
          val cjo:CiteObjJson = CiteObjJson()
          val citeObject:CiteObject = {
            citeObjString match {
              case Right(dj) => {
                cjo.citeObject(dj)
              }
              case Left(er) => throw new CiteJsonException(s"Unable to make (CiteObject): ${er}")
            }
          }

          // make record
          val dseRec:DseRecord = DseRecord(
            label,
            txtUrn,
            imgRoiUrn,
            surfRoiUrn,
            citeObject 
          ) 
          dseRec

        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }


  }
}
