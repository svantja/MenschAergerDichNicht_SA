package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.House
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class HouseFieldSpec extends FlatSpec with Matchers {
  val player = Player("test", 0)
  "A House" should "have a Player" in {
    House(player)
  }

  "A House.house" should "be ArrayBuffer" in {
    House(player).house
  }

  "A House.house" should "have a length of 4" in {
    House(player).house.length
  }

  "A House" should "return true" in {
    player.house.isFull(player)
  }
  it should "return false" in {
    player.house.house(0).removeToken()
    player.house.isFull(player)
  }

  "A House.house at index 0" should "be Field" in {
    House(player).house
  }

  "Each Field in House.house" should "get Token" in {
    for (h <- House(player).house) {
      h.getToken()
    }
  }
}
