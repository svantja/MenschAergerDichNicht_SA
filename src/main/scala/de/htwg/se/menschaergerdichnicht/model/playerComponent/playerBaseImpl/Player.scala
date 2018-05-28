package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.{House, TargetField}
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface, TokenInterface}
import play.api.libs.json.{JsNumber, JsValue, Json}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
/**
 * Created by Anastasia on 29.04.17.
 */
case class Player(var name: String, var diced: Int) extends PlayerInterface {

  var playerId = Player.newIdNum

  val house = House(this)

  val target = TargetField(this)

  var finished: Boolean = false

  var tokens = addTokens()

  def getTokens(): ArrayBuffer[TokenInterface] = tokens

  def setFinished(finished: Boolean) { this.finished = finished }

  def getFinished(): Boolean = finished

  def setName(name: String) { this.name = name }

  def getName(): String = name

  def setDiced(diced: Int) { this.diced = diced }

  def getDiced(): Int = diced

  def addTokens(): ArrayBuffer[TokenInterface] = {
    val tokens = new ArrayBuffer[TokenInterface]
    for (i <- 1 to 4) {
      tokens += new Token(this, (house.house(i - 1), i - 1), 0)
      house.house(i - 1).setToken(tokens(i - 1))
    }
    tokens
  }

  def getTokenById(tokenId: Int): Option[TokenInterface] = {
    var t:Option[TokenInterface] = None
    getTokens().map(token => if(token.tokenId == tokenId) t = Some(token))
    t
  }

  //Future
  def getAvailableTokens(): ArrayBuffer[String] = {
    val tokens = getTokens()
    val listTokens = tokens.map(token => Future(if(!token.getFinished() && (diced == 6 || token.getCounter() > 0)) Some("Token " + token.tokenId) else None))
    val t = Future.sequence(listTokens).map(_.flatten.distinct.toList)
    val b = Await.result(t, 500 millis)
    ArrayBuffer(b : _*)
  }

  override def toString: String = name

}

object Player {
  private var idNumber = 0
  private def newIdNum = {
    idNumber += 1
    idNumber
  }
}

case class Players(var currentPlayer: Int = 0, players: Vector[PlayerInterface] = Vector()) extends PlayersInterface {

  def addPlayer(player: PlayerInterface): Players = {
    copy(players = players :+ player)
  }

  def removePlayer(): Players = {
    copy(players = players.init)
  }
  def updateCurrentPlayer(player: PlayerInterface): Players = {
    copy(players = players.updated(currentPlayer, Player(player.getName(), player.getDiced())))
  }
  def nextPlayer(): Players = {
    copy(currentPlayer = (currentPlayer + 1) % players.length)
    //players(currentPlayer)
  }
  def getCurrentPlayer: PlayerInterface = {
    players(currentPlayer)
  }
  def getAllPlayer: Vector[PlayerInterface] = {
    players
  }

  override def toString: String = {
    var nameList = ""
    players.map(player => if(player == players(currentPlayer)) nameList += "Current > " + player.toString() + "\n" else nameList += "  " + player.toString() + "\n")
    nameList
  }

}
