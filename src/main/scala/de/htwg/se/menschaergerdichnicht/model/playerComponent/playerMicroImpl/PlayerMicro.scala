package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMicroImpl

import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import spray.json.DefaultJsonProtocol

case class UpdateCurrentPlayer(player: Player)

class PlayerMicro(url: String) extends PlayersInterface {

}
