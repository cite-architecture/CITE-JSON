package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class CiteLibraryJsonSpec extends FlatSpec {

  val citeLibraryMetadataJson:String = """{"name":"scs-akka test library","urn":"urn:cite2:cex:demo.2017_1:servicetest","license":"CC Share Alike.  For details, see more info."}"""

  "The CiteLibraryJson Library" should "parse a Json expression of a CiteLibrary's metadata" in {
    val clj:CiteLibraryJson = CiteLibraryJson()
    assert(clj.exists)
    val libMD:Map[String,String] = clj.parseLibraryInfo(citeLibraryMetadataJson)
    assert(libMD("urn") == "urn:cite2:cex:demo.2017_1:servicetest")
    assert(libMD("name") == "scs-akka test library")
    assert(libMD("license") == "CC Share Alike.  For details, see more info.")
  }

}
