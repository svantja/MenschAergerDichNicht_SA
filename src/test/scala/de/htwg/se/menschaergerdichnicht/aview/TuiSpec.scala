package de.htwg.se.menschaergerdichnicht.aview

import com.google.inject.Guice
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.aview.tui.Tui
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by Anastasia on 26.06.17.
 */
class TuiSpec extends FlatSpec with Matchers {
  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  val c = new Controller(injector.getInstance(classOf[PlayersInterface]),
    injector.getInstance(classOf[PlayingInterface]))
  val tui = new Tui(c)
  "A Tui" should "have a controller" in {
    tui
  }

  "A Tui" should "processInputLine q" in {
    tui.processInputLine("q")
  }
  it should "also process other params" in {
    tui.processInputLine("test")
  }

  "A Tui" should "convert string to int" in {
    tui.strToInt("2", 2)
  }

}
