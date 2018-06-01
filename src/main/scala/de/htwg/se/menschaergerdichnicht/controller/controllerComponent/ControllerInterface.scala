package de.htwg.se.menschaergerdichnicht.controller.controllerComponent


import de.htwg.se.menschaergerdichnicht.aview.tui.Tui

import scala.util._
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.PlayingField
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Players
import de.htwg.se.menschaergerdichnicht.util.Observable
import play.api.libs.json.JsValue

import scala.swing.Publisher

/**
 * Created by Anastasia on 25.06.17.
 */
trait ControllerInterface extends Publisher {

  var players: Players
  var playingField: PlayingInterface
  var message: String
  var gameState: GameState
  var tui = new Tui(this)
  def addPlayer(name: String): Try[_]
  def startGame(): Try[_]
  def chooseToken(tokenId: Int): Try[_]
  def gameStatus: GameState
  def toJson: JsValue
  def newGame(): Try[_]
  def save(): Unit
  def load(): Unit
}

import scala.swing.event.Event

class PlayersChanged extends Event
