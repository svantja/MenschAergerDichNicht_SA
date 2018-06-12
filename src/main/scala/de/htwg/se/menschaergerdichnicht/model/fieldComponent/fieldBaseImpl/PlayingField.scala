package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import akka.actor.{Actor, ActorSystem, Props}
import javax.inject.Inject
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{FieldInterface, PlayingInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface, TokenInterface}

import scala.collection.mutable.ArrayBuffer

case class PlayingField @Inject()() extends PlayingInterface {

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
        playingField(newPosition).setToken(token.tokenId)
      } else {
        val toBeKicked = playingField(newPosition).tokenId
        if (kickToken(toBeKicked, token.getPlayer(), players)) {
          token.setPosition((playingField(newPosition), newPosition))
          playingField(newPosition).setToken(token.tokenId)
        }
      }
    }
    token.setCounter(token.getCounter() + num)
  }


  //TODO: einzige Problem: was passiert wenn man sich selber kicken will?
  class KickActor(token: TokenInterface, tokenId: Int, player: PlayerInterface) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case "player one" => {
        val player = token.getPlayer()
        val free = player.house.house(tokenId - 1)
        free.setToken(token.tokenId)
        token.setPosition((free, tokenId - 1))
        token.setCounter(0)
      }
      case "player two" => {
        val player = token.getPlayer()
        val free = player.house.house(tokenId - 5)
        free.setToken(token.tokenId)
        token.setPosition((free, tokenId - 5))
        token.setCounter(0)
      }
      case "player three" => {
        val player = token.getPlayer()
        val free = player.house.house(tokenId - 9)
        free.setToken(token.tokenId)
        token.setPosition((free, tokenId - 9))
        token.setCounter(0)
      }
      case "player four" => {
        val player = token.getPlayer()
        val free = player.house.house(tokenId - 13)
        free.setToken(token.tokenId)
        token.setPosition((free, tokenId - 13))
        token.setCounter(0)
      }
    }
  }

  class TokenActor(token: TokenInterface, tokenId: Int, player: PlayerInterface) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case "kick" => {
        val system = ActorSystem("KickTokenSystem")
        val helloActor = system.actorOf(Props(new KickActor(token, tokenId, player)), name = token.number.toString)
        if (tokenId <= 4) {
          helloActor ! "player one"
        } else if (tokenId > 4 && tokenId <= 8) {
          helloActor ! "player two"
        } else if (tokenId > 8 && tokenId <= 12) {
          helloActor ! "player three"
        } else if (tokenId > 4 && tokenId <= 8) {
          helloActor ! "player four"
        }
      }

    }
  }

  case class PlayerActor(p: PlayerInterface, tokenId: Int, player: PlayerInterface) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case "kick" => {
        val system = ActorSystem("KickTokenSystem")
        p.getTokens().map(token =>
          (if (token.tokenId == tokenId && token.getPlayer() != player) (system.actorOf(Props(new TokenActor(token, tokenId, player)), name = token.number.toString)) ! "kick"))
      }
    }
  }

  def kickToken(tokenId: Int, player: PlayerInterface, players: PlayersInterface): Boolean = {
    val system = ActorSystem("KickTokenSystem")
    players.getAllPlayer.map(p => (system.actorOf(Props(new PlayerActor(p, tokenId, player)), name = p.getName())) ! "kick")
    true
  }


  def moveToTarget(token: TokenInterface, i: Int): Unit = {
    val player = token.getPlayer()
    if (!token.getFinished()) {
      if (i <= 3) {
        if (token.counter + i <= 44) {
          val target = player.target.targetField(i)
          if (target.tokenId == -1) {
            target.setToken(token.tokenId)
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

  def moveToStart(token: TokenInterface, players: PlayersInterface): Unit = {
    token.position._1.tokenId = -1

    var newPosition = 0

    token.getPlayer().playerId match {
      case 1 =>
        newPosition = 0
      case 2 =>
        newPosition += 10
      case 3 =>
        newPosition += 20
      case 4 =>
        newPosition += 30
      case _ => token.position._1.tokenId = token.tokenId
    }

    if (playingField(newPosition).tokenId == -1) {
      token.setPosition((playingField(newPosition), newPosition))
      token.setCounter(1)
      playingField(newPosition).setToken(token.tokenId)
    } else {
      val toBeKicked = playingField(newPosition).tokenId
      if (kickToken(toBeKicked, token.getPlayer(), players)) {
        token.setPosition((playingField(newPosition), newPosition))
        token.setCounter(1)
        playingField(newPosition).setToken(token.tokenId)
      }
    }
  }

}

