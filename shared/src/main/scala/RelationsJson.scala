package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import edu.holycross.shot.scm._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.citerelation._
import edu.holycross.shot.dse._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser.decode

package citejson {

  /** A Class for creating service requests resolving to binary image data
  * and following the IIIF-API.
  *
  * @constructor create a new [[CiteLibraryJson]] service
  */
  @JSExportAll case class RelationsJson() {

    /** True if this class exists 
    */
      def exists:Boolean = {
      	true
      }

      /** Returns a CiteTriple
      *
      * @param jsonString JSON string
      */
      def parseCiteTriple(jsonString:String):CiteTriple = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val citeTriple:CiteTriple = parseCiteTriple(doc)
          citeTriple
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseVectorOfDseRecords: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a CiteTriple
      *
      * @param doc io.circe.Json
      */
      def parseCiteTriple(doc:io.circe.Json):CiteTriple = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val u1Try = cursor.get[String]("urn1")
          val relTry = cursor.get[String]("relation")
          val u2Try = cursor.get[String]("urn2")

          val u1Str:String = { 
            u1Try match {
              case Right(str) => str
              case Left(er) => throw new CiteJsonException(s"Unable to make urn1 string: ${er}")
            }
          }

          val relStr:String = { 
            relTry match {
              case Right(str) => str
              case Left(er) => throw new CiteJsonException(s"Unable to make relation string: ${er}")
            }
          }

          val u2Str:String = { 
            u2Try match {
              case Right(str) => str
              case Left(er) => throw new CiteJsonException(s"Unable to make urn2 string: ${er}")
            }
          }

          val urn1:Urn = {
            u1Str.take(8) match {
              case "urn:cts:" => CtsUrn(u1Str)
              case _ => Cite2Urn(u1Str)
            }
          }
          val urn2:Urn = {
            u2Str.take(8) match {
              case "urn:cts:" => CtsUrn(u2Str)
              case _ => Cite2Urn(u2Str)
            }
          }
          val relation:Cite2Urn = Cite2Urn(relStr)
          val ct:CiteTriple = CiteTriple(urn1, relation, urn2)
          ct
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }

      /** Returns a Vector[CiteTriple]
      *
      * @param jsonString JSON string
      */
      def parseVectorOfCiteTriples(jsonString:String):Vector[CiteTriple] = {
        try {
          val doc: Json = parse(jsonString).getOrElse(Json.Null)
          val vCiteTriple:Vector[CiteTriple] = parseVectorOfCiteTriples(doc)
          vCiteTriple
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"parseVectorOfDseRecords: Failed with string param ${jsonString} :: ${e}")
        }
      }

      /** Returns a Vector[CiteTriple]
      *
      * @param doc io.circe.Json
      */
      def parseVectorOfCiteTriples(doc:io.circe.Json):Vector[CiteTriple] = {
        try {
          if(doc == Json.Null) throw new CiteJsonException(s"Null JSON: ${doc}")
          // We need a cursor to get stuff
          val cursor: HCursor = doc.hcursor
          val ctString = cursor.downField("citeTriples").as[List[Json]]
          val ctList:List[Json] = {
            ctString match {
              case Right(ccd) => ccd
              case _ => throw new CiteJsonException("Failed to parse JSON into list of DseRecord JSON objects.")
            }
          }
          // Make Vector here!
          val ctVec:Vector[CiteTriple] = {
            ctList.map( ct => {
              val citeTriple:CiteTriple = parseCiteTriple(ct)
              citeTriple
            }).toVector
          }
          // Done Make Vector!
          ctVec
        } catch {
          case e:Exception =>  throw new CiteJsonException(s"${e}")
        }
      }


  }
}
