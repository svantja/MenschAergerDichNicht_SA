package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMicroImpl

import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import de.htwg.se.menschaergerdichnicht.util.Http
import spray.json.DefaultJsonProtocol


case class UpdateCurrentPlayer(player: Player)
case class Add(player: Player)

object PlayerJsonProtocol extends DefaultJsonProtocol {
  implicit val playerFormat = jsonFormat2(Player)
  implicit val addFormat = jsonFormat1(Add)
  //implicit val playerFormat = jsonFormat2(Player)
  implicit val updatePlayerFormat = jsonFormat1(UpdateCurrentPlayer)
}

class PlayerMicro(url: String) extends PlayersInterface {

  def this() = this("http://localhost:8081/players/")

  import PlayerJsonProtocol._
  import spray.json._

  override def removePlayer(): PlayersInterface = {
    Http.get(url + "removePlayer")
    this
  }

  override def addPlayer(player: PlayerInterface): PlayersInterface = {
    val jsonVal = Add(Player(player.getName, 0)).toJson
    Http.get(url + "add/" + player)
    this
  }

  override def nextPlayer(): PlayersInterface = {
    Http.get(url + "nextPlayer")
    this
  }

  override def getCurrentPlayer: Player = {
    Http.getResult(url + "getCurrentPlayer").convertTo[Player]
  }

  override def updateCurrentPlayer(player: PlayerInterface): PlayersInterface = {
    val jsonVal = UpdateCurrentPlayer(Player(player.getName, player.getDiced)).toJson
    Http.get(url + "updateCurrentPlayer", jsonVal)
    this
  }

  override def getAllPlayer: Vector[Player] = {
    Http.getResult(url + "getAllPlayers").convertTo[Vector[Player]]
  }

}
