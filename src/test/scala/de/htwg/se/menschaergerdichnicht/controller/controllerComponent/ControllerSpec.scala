package de.htwg.se.menschaergerdichnicht.controller.controllerComponent

import com.google.inject.Guice
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.menschaergerdichnicht.util.UndoManager
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by Anastasia on 26.06.17.
 */
class ControllerSpec extends FlatSpec with Matchers {
  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  val c = new Controller(injector.getInstance(classOf[PlayersInterface]),
    injector.getInstance(classOf[PlayingInterface]))

  "A Controller" should "have players" in {
    c.players
  }

  "A Controller" should "have a PlayingField" in {
    c.playingField
  }

  "A Controller" should "have a message" in {
    c.message
  }
  "A Controller" should "have a gameState" in {
    c.gameState
  }

  "A Controller" should "have an undoManager" in {
    c.undoManager
  }

  "A Controller" should "add a Player" in {
    c.addPlayer("tester")
  }

  "A Controller" should "start the Game" in {
    c.startGame()
  }

  //  "A Controller" should "choose a Token" in{
  //    c.chooseToken(1)
  //  }
}
