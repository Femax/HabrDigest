package ru.test.fedosov.habr.digest.front

import org.scalajs.dom.document
import ru.test.fedosov.habr.digest.front.modules.main.Main

import scala.scalajs.js.annotation.JSExport

object App extends {


  @JSExport
  def main(args: Array[String]): Unit = {
    Main().renderIntoDOM(document.getElementById("root"))
  }

}