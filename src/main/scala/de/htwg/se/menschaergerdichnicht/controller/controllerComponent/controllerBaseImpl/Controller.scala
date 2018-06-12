package de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.menschaergerdichnicht.{MenschAergerDichNichtMicroModule, MenschAergerDichNichtModule}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.{ControllerInterface, PlayersChanged}
import de.htwg.se.menschaergerdichnicht.util.{Observable, UndoManager}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Players
import play.api.libs.json.{JsNull, JsNumber, JsValue, Json}

import scala.util._
/**
 * Created by Anastasia on 01.05.17.
 */
case class Controller (
                        var players: PlayersInterface,
                        var playingField: PlayingInterface,
                        var undoManager: UndoManager) extends ControllerInterface {

//  var players = Players()

//  val injector = Guice.createInjector(new MenschAergerDichNichtMicroModule)
//  var playingField = injector.getInstance(classOf[PlayingInterface])
//  var players = injector.getInstance(classOf[PlayersInterface])

  @Inject()
  def this(players: PlayersInterface, playingField: PlayingInterface) = {
    this(players, playingField, new UndoManager)
  }

  var players2 = new Players()
  var message = ""
  var gameState: GameState = NONE
//  var undoManager = new UndoManager

  def addPlayer(name: String): Try[_] = undoManager.action(AddPlayer(name, this))

  def startGame(): Try[_] = undoManager.action(Play(this))

  def newGame(): Try[_] = undoManager.action(NewGame(this))

  def chooseToken(tokenId: Int): Try[_] = undoManager.action(ChooseToken(tokenId, this))

  override def gameStatus: GameState = ???

  def toJson: JsValue = {
    for (player <- players2.players) {
      println(player.getName(), player.getDiced())
    }
    Json.obj(
      "current" -> players2.currentPlayer,
      "state" -> gameState,
      "players" -> Json.toJson(
        for (player <- players2.players) yield {
          Json.obj(
            "playerId" -> player.playerId,
            "name" -> player.getName(),
            "diced" -> player.getDiced(),
            "token" -> Json.toJson(
              for (token <- player.tokens) yield {
                Json.obj(
                  "tokenId" -> token.tokenId,
                  "position" -> token.position._2,
                  "count" -> token.counter
                )
              }
            )
          )
        }
      )

    )
  }
}

