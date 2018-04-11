package de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerMockImpl

import com.google.inject.Guice
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.aview.tui.Tui
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.ControllerInterface
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.PlayingField
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Players
import play.api.libs.json.JsValue

import scala.util.{ Success, Try }

/**
 * Created by Anastasia on 25.06.17.
 */
case class ControllerMock() extends ControllerInterface {

  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  var players = Players()
  var playingField = injector.getInstance(classOf[PlayingInterface])
  var message = ""
  var gameState = FINISHED
  def addPlayer(name: String): Try[_] = Success()
  def startGame(): Try[_] = Success()
  def chooseToken(tokenId: Int): Try[_] = Success()
  def gameStatus: GameState = FINISHED
  def newGame(): Try[_] = Success()

  override def toJson: JsValue = ???
}
