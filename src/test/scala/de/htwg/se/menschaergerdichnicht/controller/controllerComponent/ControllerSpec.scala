package de.htwg.se.menschaergerdichnicht.controller.controllerComponent

import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by Anastasia on 26.06.17.
 */
class ControllerSpec extends FlatSpec with Matchers {
  val c = Controller()

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
