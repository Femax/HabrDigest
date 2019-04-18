package ru.test.fedosov.habr.digest.front.modules.main

import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.vdom.html_<^._

object Main {
  val component = Scala.builder[Unit]("Main")
    .renderStatic(<.div("Hello World!"))
    .build

  def apply() = component()
}
