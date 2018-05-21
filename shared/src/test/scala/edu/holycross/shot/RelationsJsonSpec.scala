package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import edu.holycross.shot.dse._
import edu.holycross.shot.citerelation._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class RelationsJsonSpec extends FlatSpec {

  val citeTripleJson:String = """
{"urn1":"urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6","relation":"urn:cite2:cite:verbs.v1:commentsOn","urn2":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2"}
  """

  val vectorOfCiteTriplesJson:String = """
  {"citeTriples":[{"urn1":"urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6","relation":"urn:cite2:cite:verbs.v1:commentsOn","urn2":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2"},{"urn1":"urn:cts:greekLit:tlg5026.msA.hmt_xml:1.7","relation":"urn:cite2:cite:verbs.v1:commentsOn","urn2":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2"}]}
  """

  "The RelationsJson Library" should "compile" in {
    val cjo:RelationsJson = RelationsJson()
    assert(cjo.exists)
  }

  it should "work with Circe" in {
    val rawJson: String = """{
        "foo": "bar",
        "baz": 123,
        "list of stuff": [ 4, 5, 6 ]
    }"""
    val parseResult = parse(rawJson)
    parseResult match {
      case Left(failure) => {
        fail(s"${failure}") 
      }
      case Right(json) => {
        succeed
      }
      case _ => { fail("neither left nor right?")}
    }
  }

  it should "parse a Json to a single CiteTriple object" in {
    val cjo:RelationsJson = RelationsJson()
    assert(cjo.exists)
    val ct:CiteTriple = cjo.parseCiteTriple(citeTripleJson)
    val u1:CtsUrn = CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6")
    val verb:Cite2Urn = Cite2Urn("urn:cite2:cite:verbs.v1:commentsOn")
    val u2:CtsUrn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2")
    assert(ct.urn1.asInstanceOf[CtsUrn] == u1)
    assert(ct.urn2.asInstanceOf[CtsUrn] == u2)
    assert(ct.relation == verb)
  }

  it should "parse a Json to a Vector of CiteTriple object" in {
    val cjo:RelationsJson = RelationsJson()
    assert(cjo.exists)
    val vct:Vector[CiteTriple] = cjo.parseVectorOfCiteTriples(vectorOfCiteTriplesJson)
    val ctZero:CiteTriple = CiteTriple(
      CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt_xml:1.6"),
      Cite2Urn("urn:cite2:cite:verbs.v1:commentsOn"),
      CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2")
    )
    val ctOne:CiteTriple = CiteTriple(
      CtsUrn("urn:cts:greekLit:tlg5026.msA.hmt_xml:1.7"),
      Cite2Urn("urn:cite2:cite:verbs.v1:commentsOn"),
      CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.2")
    )

    assert( vct.size == 2 )
    assert( vct(0) == ctZero )  
    assert( vct(1) == ctOne )  
  }

 
 


}
