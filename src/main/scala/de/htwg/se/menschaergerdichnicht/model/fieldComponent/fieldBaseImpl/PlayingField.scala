package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import javax.inject.Inject

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{ FieldInterface, PlayingInterface }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{ PlayerInterface, PlayersInterface, TokenInterface }

import scala.collection.mutable.ArrayBuffer

case class PlayingField @Inject() () extends PlayingInterface {

  val playingField = new ArrayBuffer[FieldInterface]

  for (i <- 1 to 40) {
    playingField += Field()
  }

  def getField(id: Int): FieldInterface = playingField(id)

  def moveToken(token: TokenInterface, num: Int, players: PlayersInterface): Unit = {
    if (token.counter + num >= 41) {
      val move = (token.counter + num) - 41
      moveToTarget(token, move)
    } else {
      val oldPosition = token.getPosition()._2
      var newPosition = oldPosition + num
      if (newPosition > 39) {
        newPosition = newPosition - 40
      }
      token.position._1.tokenId = -1
      if (playingField(newPosition).tokenId == -1) {
        token.setPosition((playingField(newPosition), newPosition))
        playingField(newPosition).setToken(token)
      } else {
        val toBeKicked = playingField(newPosition).tokenId
        if (kickToken(toBeKicked, token.getPlayer(), players)) {
          token.setPosition((playingField(newPosition), newPosition))
          playingField(newPosition).setToken(token)
        }
      }
    }
    token.setCounter(token.getCounter() + num)
  }

  //TODO: kick token on start field, remove warnings
  //TODO: add akka...
  // einzelne Aktoren durchsuchen die jeweiligen Player
  // schicken "STOP" message wenn Token gefunden wurde
  // beenden sich wenn keiner gefunden wurde -> auch nachricht
  def kickToken(tokenId: Int, player: PlayerInterface, players: PlayersInterface): Boolean = {
    for (p <- players.getAllPlayer) {
      for (token <- p.getTokens()) {
        if (token.tokenId == tokenId) {
          if (token.getPlayer() != player) {
            val player = token.getPlayer()
            if (tokenId > 4 && tokenId <= 8) {
              val free = player.house.house(tokenId - 5)
              free.setToken(token)
              token.setPosition((free, tokenId - 5))
              token.setCounter(0)
            } else if (tokenId > 8 && tokenId <= 12) {
              val free = player.house.house(tokenId - 9)
              free.setToken(token)
              token.setPosition((free, tokenId - 9))
              token.setCounter(0)
            } else if (tokenId > 12 && tokenId <= 16) {
              val free = player.house.house(tokenId - 13)
              free.setToken(token)
              token.setPosition((free, tokenId - 13))
              token.setCounter(0)
            } else if (tokenId >= 0 && tokenId <= 4) {
              val free = player.house.house(tokenId - 1)
              free.setToken(token)
              token.setPosition((free, tokenId - 1))
              token.setCounter(0)
            }
            return true
          }
        }
      }
    }
    return false
  }

  def moveToTarget(token: TokenInterface, i: Int): Unit = {
    val player = token.getPlayer()
    if (!token.getFinished()) {
      if (i <= 3) {
        if (token.counter + i <= 44) {
          val target = player.target.targetField(i)
          if (target.tokenId == -1) {
            target.setToken(token)
            token.position._1.tokenId = -1
            token.setPosition(target, i)
            token.setFinished(true)
            if (player.target.isFull(player)) {
              player.setFinished(true)
            }
          }
        }
      }
    }
  }

  def moveToStart(token: TokenInterface): Unit = {
    token.position._1.tokenId = -1
    token.getPlayer().playerId match {
      case 1 =>
        token.setPosition(playingField(0), 0); token.setCounter(1); playingField(0).setToken(token)
      case 2 =>
        token.setPosition(playingField(10), 10); token.setCounter(1); playingField(10).setToken(token)
      case 3 =>
        token.setPosition(playingField(20), 20); token.setCounter(1); playingField(20).setToken(token)
      case 4 =>
        token.setPosition(playingField(30), 30); token.setCounter(1); playingField(30).setToken(token)
      case _ => token.position._1.tokenId = token.tokenId
    }
  }
}

