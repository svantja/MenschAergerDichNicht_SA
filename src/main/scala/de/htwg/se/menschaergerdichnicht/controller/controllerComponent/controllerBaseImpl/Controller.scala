package de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.{ControllerInterface, PlayersChanged}
import de.htwg.se.menschaergerdichnicht.util.{Observable, UndoManager}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Players
import play.api.libs.json.{JsNull, JsNumber, JsValue, Json}
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.{playerQuery}

import scala.collection.mutable.ArrayBuffer
import scala.util._
/**
 * Created by Anastasia on 01.05.17.
 */
case class Controller () extends ControllerInterface {

  var players = Players()

  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  var playingField = injector.getInstance(classOf[PlayingInterface])

  var message = ""
  var gameState: GameState = NONE
  var undoManager = new UndoManager

  def addPlayer(name: String): Try[_] = undoManager.action(AddPlayer(name, this))

  def startGame(): Try[_] = undoManager.action(Play(this))

  def newGame(): Try[_] = undoManager.action(NewGame(this))

  def save(): Unit = {
    //TODO: häääää
    players.players.map{p => this.selectPlayer.PlayerSlick.create(p)}
    playingField.playingField.map(f => this.selectField.PlayingFieldSlick.create(f))
  }

  def load(): Unit = {
    //read data from DB
    val all_players = players.players.map{p => this.selectPlayer.PlayerSlick.read(p.playerId).get}
    var num = 0
    playingField.playingField.map(f => (this.selectField.PlayingFieldSlick.read(num), num += 1, println(num + "nummer")))

    //delete old data from DB
    players.players.foreach(p => this.selectPlayer.PlayerSlick.delete(p.playerId))
    num = 0
    playingField.playingField.map(f => (this.selectField.PlayingFieldSlick.delete(num), num += 1))



    println(playingField.playingField.map(f => f.tokenId))

    println(players.players.map(p => (p.playerId, p.tokens, p.getDiced(), p.tokens.map(t => t.tokenId))))
    this.tui.printTui()
  }

  def chooseToken(tokenId: Int): Try[_] = undoManager.action(ChooseToken(tokenId, this))

  override def gameStatus: GameState = ???

  def toJson: JsValue = {
    for (player <- players.players) {
      println(player.getName(), player.getDiced())
    }
    Json.obj(
      "current" -> players.currentPlayer,
      "state" -> gameState,
      "players" -> Json.toJson(
        for (player <- players.players) yield {
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

