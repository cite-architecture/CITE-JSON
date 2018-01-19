package edu.holycross.shot

package citejson {

  case class CiteException(message: String = "", cause: Option[Throwable] = None) extends Exception(message) {
    cause.foreach(initCause)
  }

}
