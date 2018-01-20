package edu.holycross.shot.citejson

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import cats.syntax.either._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import org.scalatest.FlatSpec

class Ohco2CatalogEntryJsonSpec extends FlatSpec {

  val catEntry:String = """
  {
    "citationScheme":"book/line",
    "workTitle":"Iliad",
    "exemplarLabel":"",
    "versionLabel":"Greek. Allen, ed. Perseus Digital Library. Creative Commons Attribution 3.0 License",
    "groupName":"Homeric Epic",
    "urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:",
    "lang":"grc"
  }"""

  val wholeCat:String = """
  {"ctsCatalog":[{"citationScheme":"book/line","workTitle":"Iliad","exemplarLabel":"","versionLabel":"Greek. Allen, ed. Perseus Digital Library. Creative Commons Attribution 3.0 License","groupName":"Homeric Epic","urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:","lang":"grc"},{"citationScheme":"book/line/token","workTitle":"Iliad","exemplarLabel":"Syntactical Tokens","versionLabel":"Perseus Greek, following Allen","groupName":"Homeric Epic","urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.tokens:","lang":"grc"},{"citationScheme":"book/line/lemma","workTitle":"Iliad","exemplarLabel":"Lemmatized Tokens","versionLabel":"Perseus Greek, following Allen","groupName":"Homeric Epic","urn":"urn:cts:greekLit:tlg0012.tlg001.perseus_grc2.lemmata:","lang":"grc"},{"citationScheme":"book/section","workTitle":"Histories","exemplarLabel":"","versionLabel":"Greek, Godley, ed.","groupName":"Herodotus","urn":"urn:cts:greekLit:tlg0016.tlg001.grc:","lang":"grc"},{"citationScheme":"book/section","workTitle":"Histories","exemplarLabel":"","versionLabel":"English, trans. Godley","groupName":"Herodotus","urn":"urn:cts:greekLit:tlg0016.tlg001.eng:","lang":"eng"},{"citationScheme":"section/paragraph","workTitle":"Life of Pericles","exemplarLabel":"","versionLabel":"Greek, ed. Ziegler","groupName":"Plutarch","urn":"urn:cts:greekLit:tlg0007.tlg012.ziegler:","lang":"grc"},{"citationScheme":"book/chapter/section","workTitle":"Factorum Et Dictorum Memorabilium","exemplarLabel":"","versionLabel":"Latin","groupName":"Valerius Maximus","urn":"urn:cts:latinLit:phi1038.phi001.omar:","lang":"lat"},{"citationScheme":"letter/section","workTitle":"Epistulae Ad Atticum","exemplarLabel":"","versionLabel":"Latin","groupName":"M. Tullius Cicero","urn":"urn:cts:latinLit:phi0474.phi052.omar:","lang":"lat"},{"citationScheme":"surah/ayah","workTitle":"The Quran","exemplarLabel":"","versionLabel":"Text from http://tanzil.net Creative Commons Attribution 3.0 License","groupName":"Classical Arabic examples","urn":"urn:cts:citedemo:arabic.quran.v1:","lang":"ara"},{"citationScheme":"letter/poem/line/seg","workTitle":"Divān","exemplarLabel":"","versionLabel":"Farsi. Muhammad Qazwini and Qasim Gani [1941]. Text from Open Persian (dh.uni-leipzig.de) Creative Commons Attribution 3.0 License","groupName":"Hafez","urn":"urn:cts:farsiLit:hafez.divan.perseus-far1:","lang":"far"},{"citationScheme":"letter/poem/line/seg","workTitle":"Divān","exemplarLabel":"","versionLabel":"English. H. Wilberforce Clarke [1891]. Text from Open Persian [dh.uni-leipzig.de] Creative Commons Attribution 3.0 License","groupName":"Hafez","urn":"urn:cts:farsiLit:hafez.divan.perseus-eng1:","lang":"eng"},{"citationScheme":"letter/poem/line/seg","workTitle":"Divān","exemplarLabel":"","versionLabel":"German. Joseph von Hammer-Purgstall [1812]. Text from Open Persian [dh.uni-leipzig.de] Creative Commons Attribution 3.0 License","groupName":"Hafez","urn":"urn:cts:farsiLit:hafez.divan.perseus-ger1:","lang":"deu"}]}
"""

  "The ohco2json library" should "parse a single catalog entry" in {
    val cjo:Ohco2Json = Ohco2Json()
    assert(cjo.exists)
    val catEntryObject:CatalogEntry = cjo.o2CatalogEntry(catEntry)
    assert(catEntryObject.urn == CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:"))
  }

  it should "parse a whole catalog into a vector of catalog entries" in {
    val cjo:Ohco2Json = Ohco2Json() 
    val o2cat:Catalog = cjo.o2Catalog(wholeCat)
    assert(o2cat.size == 12)
  }





}
