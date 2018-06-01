package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMicroImpl

import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import de.htwg.se.menschaergerdichnicht.util.Http
import spray.json.DefaultJsonProtocol


case class UpdateCurrentPlayer(player: Player)

case class Add(name: String)

object PlayerJsonProtocol extends DefaultJsonProtocol {
  implicit val pushFormat = jsonFormat1(Add)
  //implicit val playerFormat = jsonFormat2(Player)
  //implicit val updatePlayerFormat = jsonFormat1(UpdateCurrentPlayer)
}

class PlayerMicro(url: String) extends PlayersInterface {

  def this() = this("http://localhost:8081/players/")

  import PlayerJsonProtocol._
  import spray.json._

  override def removePlayer(): PlayersInterface = {
    Http.get(url + "removePlayer")
    this
  }

  override def addPlayer(name: String): PlayersInterface = {
    val jsonVal = Add(name).toJson
    Http.get(url + "add/" + name)
    this
  }

//  override def updateCurrentPlayer(player: PlayerInterface): PlayersInterface = {
//    val jsonVal = UpdateCurrentPlayer(Player2(player.getName, player.getDiced)).toJson
//    Http.get(url + "updateCurrentPlayer", jsonVal)
//    this
//  }

  override def nextPlayer(): PlayersInterface = {
    Http.get(url + "nextPlayer")
    this
  }

//  override def getCurrentPlayer: Player = {
//    Http.getResult(url + "getCurrentPlayer").convertTo[Player]
//  }

  override def updateCurrentPlayer(player: PlayerInterface): PlayersInterface = ???

  override def getAllPlayer = ???

  override def getCurrentPlayer = ???
}
