package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.TargetField
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class TargetFieldSpec extends FlatSpec with Matchers {
  val player = Player("test", 0)

  "A TargetField" should "have a player" in {
    player.target.player should be(player)
  }

  "A TargetField.targetField" should "be ArrayBuffer" in {
    TargetField(player).targetField
  }

  "A TargetField.targetField" should "have a length of 4" in {
    player.target.targetField.length
    assert(player.target.targetField.length == 4)
  }

  "A TargetField" should "return false" in {
    player.target.isFull(player)
  }
  it should "return true" in {
    player.target.targetField(0).setToken(player.tokens(0))
    player.target.targetField(1).setToken(player.tokens(1))
    player.target.targetField(2).setToken(player.tokens(2))
    player.target.targetField(3).setToken(player.tokens(3))

    player.target.isFull(player)
  }
}
