package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.Dice
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DiceSpec extends FlatSpec with Matchers {

  "A Dice" should "return values between 0 and 6" in {
    val player = Player("test", 0)
    val w = Dice()
    assert(!(w.rollDice(player) > 6))
    assert(!(w.rollDice(player) < 0))
  }

  "A Dice.dice" should "be integer" in {
    val w = Dice()
    w.dice
  }

}
