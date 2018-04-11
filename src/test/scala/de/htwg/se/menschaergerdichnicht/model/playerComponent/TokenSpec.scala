package de.htwg.se.menschaergerdichnicht.model.playerComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Token }
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by anhauser on 13.06.2017.
 */
class TokenSpec extends FlatSpec with Matchers {
  val token = new Token(Player("test", 0), (Field(), 0), 0)

  "A Token" should "have a number" in {
    token.number
  }
  it should "be between 1 and 4" in {
    assert(token.number >= 1 && token.number <= 4)
  }

  "A Token" should "have a tokenId" in {
    token.tokenId
  }
  it should "be at least 1" in {
    assert(token.tokenId >= 1)
  }

  "A Token" should "have a color" in {
    token.color
  }
  it should "be a color" in {
    token.getColor()
  }

  "A Token.finished" should "be false" in {
    token.finished
  }

  "A Token" should "get Color" in {
    token.getColor()
  }

  "A Token" should "set Player" in {
    token.setPlayer(Player("test", 0))
  }

  "A Token" should "get Player" in {
    token.getPlayer()
  }

  "A Token" should "set Position" in {
    token.setPosition(Field(), 0)
  }

  "A Token" should "get Position" in {
    token.getPosition()
  }

  "A Token" should "set Counter" in {
    token.setCounter(0)
  }

  "A Token" should "get Counter" in {
    token.getCounter()
  }

  "A Token" should "set Finished" in {
    token.setFinished(true)
  }

  "A Token" should "get Finished" in {
    token.getFinished()
  }

  "A Token" should "have object Token" in {
    object Token
  }
}
