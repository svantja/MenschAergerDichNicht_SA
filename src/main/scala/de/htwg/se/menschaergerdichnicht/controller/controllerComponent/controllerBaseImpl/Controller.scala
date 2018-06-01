package de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.{ControllerInterface, PlayersChanged}
import de.htwg.se.menschaergerdichnicht.util.{Observable, UndoManager}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Players
import play.api.libs.json.{JsNull, JsNumber, JsValue, Json}
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.{PlayerSlick, PlayingFieldSlick, TokenSlick}

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


    players.players.map{p => (PlayerSlick.create(p), p.tokens.map(t => TokenSlick.create(t)))}
  }

  def load(): Unit = {
    //TODO: alte ids speichern bevor read

    val old = players.players.flatMap(p => p.tokens)
    val all_players = players.players.map{p => PlayerSlick.read(p.playerId).get}
    //alte Tokens setzen
    all_players.foreach(p => (p.tokens = ArrayBuffer(old.filter(_.getPlayer() == p).map(t => t) : _*)))
    //Tokens aus DB laden und setzen
    all_players.map(p => p.tokens.map(t => TokenSlick.read(t.tokenId)))

    players.players.map{p => (p.tokens.foreach(t => TokenSlick.delete(t.tokenId)), PlayerSlick.delete(p.playerId))}

    all_players.foreach(p => println("Name: " + p.getName() + ", diced: " + p.getDiced() + "Tokens:" ))
    players.players.map{p => all_players.iterator}

    println("test:" + players.players)

    println(all_players.map(p => p.tokens.map(i => (i.tokenId, i.getPlayer().playerId))))
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

