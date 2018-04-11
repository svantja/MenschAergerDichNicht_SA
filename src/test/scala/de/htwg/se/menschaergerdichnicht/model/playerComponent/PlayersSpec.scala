package de.htwg.se.menschaergerdichnicht.model.playerComponent

import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Players }
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class PlayersSpec extends FlatSpec with Matchers {
  val player = Player("hans", 3)
  val player2 = Player("brigitte", 2)
  val players: Players = Players(0, players = Vector(player, player2))
  "Players" should "add a Player" in {
    players.addPlayer(player)
    players.addPlayer(player2)
  }

  "Players" should "update current Player" in {
    players.updateCurrentPlayer(player2)
  }

  "Players" should "get the next Player" in {
    players.nextPlayer()
  }

  "Players" should "get the current Player" in {
    players.getCurrentPlayer
  }

  "Players" should "get all Player" in {
    players.getAllPlayer
  }

  "Players" should "remove a Player" in {
    players.removePlayer()
  }
}
