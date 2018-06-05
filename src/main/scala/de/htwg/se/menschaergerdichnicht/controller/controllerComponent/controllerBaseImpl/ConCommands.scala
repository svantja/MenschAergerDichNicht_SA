package de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{ Guice, Inject }
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.Dice
import de.htwg.se.menschaergerdichnicht.util.Command
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.PlayersChanged
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player

import scala.util.{ Success, Try }

/**
 * Created by Anastasia on 06.06.17.
 */
case class AddPlayer(name: String, c: Controller) extends Command {
  //TODO: future??akka?? weil neuen Spieler anlegen dauert?
  override def action(): Try[_] = {
    val player = Player(name, 0)
    if (c.gameState == NONE || c.gameState == PREPARE) {
      if (c.players.getAllPlayer.length < 4) {
        c.players = c.players.addPlayer(player)
        if (c.players.getAllPlayer.length != player.playerId) {
          println(c.players.getAllPlayer.length)
          println(player.playerId - 1)
          player.playerId = c.players.getAllPlayer.length
          val tokens = player.getTokens()

          for (i <- 0 until tokens.size) {
            if (player.playerId == 1) {
              tokens(i).tokenId = i + 1
              tokens(i).color = "red"
            } else if (player.playerId == 2) {
              tokens(i).tokenId = i + 5
              tokens(i).color = "blue"
            } else if (player.playerId == 3) {
              tokens(i).tokenId = i + 9
              tokens(i).color = "green"
            } else {
              tokens(i).tokenId = i + 13
              tokens(i).color = "yellow"
            }
          }

        }
        println("Spieler " + name + " wurde hinzugefuegt")
        c.gameState = PREPARE
        c.tui.update
        c.publish(new PlayersChanged)
      } else {
        println("Es existieren bereits 4 Spieler")
      }
    } else { println("Spiel wurde bereits gestarted") }
    Success()
  }

  override def undo(): Try[_] = {
    for (p <- c.players.getAllPlayer) {
      println(p.playerId, "gelöscht")
      c.players = c.players.removePlayer()
    }
    println("Geloeschter Spieler: " + name)
    c.gameState = PREPARE
    c.tui.update
    c.publish(new PlayersChanged)
    Success()
  }
}

case class NewGame(c: Controller) extends Command {
  override def action(): Try[_] = {
    for (p <- c.players.getAllPlayer) {
      println(p.playerId, "gelöscht")
      c.players = c.players.removePlayer()
    }
//    c.players.currentPlayer = 0
    c.gameState = NONE
    c.tui.update
    c.publish(new PlayersChanged)
    Success()
  }
  override def undo(): Try[_] = ???
}

case class ChooseToken(tokenId: Int, c: Controller) extends Command {
  val player = c.players.getCurrentPlayer
  val t = player.getTokenById(tokenId)
  val dice = Dice()

  override def action(): Try[_] = {
    println(c.players.getCurrentPlayer)
    println(t)
    t match{
      case Some(token) => {
        if (player.getDiced() == 6) {
          if (token.counter == 0) {
            c.playingField.moveToStart(token, c.players)
            println("Moved Token" + tokenId + " to start")
            player.setDiced(0)
          } else {
            c.playingField.moveToken(token, player.getDiced(), c.players)
            println("Moved Token" + tokenId + " " + player.getDiced() + " fields")
            player.setDiced(0)
          }
        }else {
          c.playingField.moveToken(token, player.getDiced(), c.players)
          println("Moved Token" + tokenId + " " + player.getDiced() + " fields")
          player.setDiced(0)
          c.players = c.players.nextPlayer()
        }
      }
      case None => println("errooooor")
    }

    c.gameState = ONGOING
    c.tui.update
    c.publish(new PlayersChanged)
    Success()
  }

  override def undo(): Try[_] = ???

}

case class Play(c: Controller) extends Command {
  val dice = Dice()

  override def action(): Try[_] = {
    //while (true)
    if(c.gameState == PREPARE){
      c.gameState = ONGOING
    }else if (c.gameState != DICED || c.gameState != SELECT) {
      c.gameState == ONGOING
      val player = c.players.getCurrentPlayer
      if (!player.getFinished()) {
        val num = dice.rollDice(c.players.getCurrentPlayer)
        if (num == 0) {
          player.setDiced(num)
          c.players = c.players.nextPlayer()
          println(c.players.getCurrentPlayer)
          c.gameState == SELECT
        } else {
          if (player.house.isFull(player)) {
            player.setDiced(num)
            c.gameState = SELECT
          } else {
            player.setDiced(num)

            if (num == 6) {
              c.gameState = SELECT
            } else {
              if (player.getAvailableTokens().length == 0) {
                println("Cannot move, must dice a 6")
                c.gameState = ONGOING
                c.players = c.players.nextPlayer()
              } else {
                c.gameState = SELECT
              }
            }
          }
        }
      }
    } else {
      println("Player must move before dicing again!")
    }

    c.tui.update
    c.publish(new PlayersChanged)
    Success()
  }

  override def undo(): Try[_] = ???
}
