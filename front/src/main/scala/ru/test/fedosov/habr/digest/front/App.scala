package ru.test.fedosov.habr.digest.front

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.Scala.Component
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.document

import scala.scalajs.js.annotation.JSExport

object App {

  val Hello =
    ScalaComponent.builder[String]("Hello")
      .render_P(name => <.div("Hello there ", name))
      .build

  @JSExport
  def main(args: Array[String]): Unit = {
    <.div(Hello("Doe"),Hello("Jhon")).renderIntoDOM(document.getElementById("root"))
  }

}

//object TutorialApp {
//  def main(args: Array[String]): Unit = {
//    println("Hello world!")
//  }
//}
