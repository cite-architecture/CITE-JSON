package edu.holycross.shot
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.cite._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

package citejson {

  /** A Class for creating service requests resolving to binary image data
  * and following the IIIF-API.
  *
  * @constructor create a new [[CiteJson]] service
  * @param jsonString String a JSON expression of a CITE/CTS Object
  */
  @JSExportAll case class CiteJson(jsonString:String) {
      def exists:Boolean = {
      	true
      }
  }


}
