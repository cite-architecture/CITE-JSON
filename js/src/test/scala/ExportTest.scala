package edu.holycross.shot.citejson

import org.scalatest._
import edu.holycross.shot.cite._

class ExportTest extends FlatSpec {

   "The Cite JSON library" should "compile" in {
    val groupLevel = CtsUrn("urn:cts:greekLit:tlg0012:")
    assert(groupLevel.textGroup == "tlg0012")
  }

}
