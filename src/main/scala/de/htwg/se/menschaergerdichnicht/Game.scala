package de.htwg.se.menschaergerdichnicht

import com.google.inject.Guice
import de.htwg.se.menschaergerdichnicht.aview.tui.Tui
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.ControllerInterface
import de.htwg.se.menschaergerdichnicht.aview.HttpServer

import scala.io.StdIn.readLine

// should try idea ultimate for worksheets
// datenstrukturen sollten runterskalieren, auf skalierbarkeit achten
// fuer einfache testfaelle

object Game {

  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = controller.tui
  //auskommentiert wegen docker
  val wui = new HttpServer(controller, tui)


  def main(args: Array[String]): Unit = {

    var input: String = ""
    tui.update
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
