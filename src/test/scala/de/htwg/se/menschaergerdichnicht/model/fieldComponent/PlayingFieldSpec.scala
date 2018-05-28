package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.PlayingField
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Players }
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class PlayingFieldSpec extends FlatSpec with Matchers {
  val playingField = PlayingField()
  val player = Player("hans", 1)
  val players = Players()
  "A PlayingField().playingField" should "be an ArrayBuffer" in {
    PlayingField().playingField
  }

  "A PlayingField().playingField.length" should "be 40" in {
    playingField.playingField.length
  }

  "A PlayingField" should "return id" in {
    playingField.getField(0)
  }

  "A PlayingField" should "move Token" in {
    playingField.moveToken(player.tokens(0), player.getDiced(), players)
  }

  "A PlayingField" should "kick Token" in {
    playingField.kickToken(playingField.playingField(1).getToken(), player, players)
  }

  "A PlayingField" should "move to target" in {
    playingField.moveToTarget(player.tokens(0), 1)
  }

  "A PlayingField" should "move to start" in {
    playingField.moveToStart(player.tokens(0), players)
  }
}
