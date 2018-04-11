package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Token }
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class FieldSpec extends FlatSpec with Matchers {
  "A Field" should "have a tokenId" in {
    val field = Field()
    field.tokenId
  }

  "A Field" should "remove Token" in {
    Field().removeToken()
  }

  "A Field" should "set Token" in {
    val player = Player("test", 0)
    Field().setToken(Token(player, (Field(), 0), 0))
  }

  "A Field" should "get Token" in {
    Field().getToken()
  }

  "A Field.tokenId" should "be -1" in {
    Field().tokenId
  }

  "A Field.tokenId" should "be integer" in {
    Field().tokenId
  }
}
