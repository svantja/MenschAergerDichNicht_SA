package de.htwg.se.menschaergerdichnicht.model.playerComponent

import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by svloeger on 13.06.2017.
 */
class PlayerSpec extends FlatSpec with Matchers {

  val player = new Player("test", 0)

  "A Player" should "have an ID" in {
    player.playerId
  }
  it should "be at least 1" in {
    assert(player.playerId >= 1)
  }

  "A Player" should "have a House" in {
    player.house
  }

  "A Player" should "have a target" in {
    player.target
  }

  "A Player" should "have finished" in {
    player.finished
  }

  "A Player" should "have tokens" in {
    player.tokens
  }

  "A Player" should "get Tokens" in {
    player.getTokens()
  }

  "A Player" should "set Finished" in {
    player.setFinished(true)
  }

  "A Player" should "get Finished" in {
    player.getFinished()
  }

  "A Player" should "set Name" in {
    player.setName("test")
  }

  "A Player" should "set Diced" in {
    player.setDiced(1)
  }

  "A Player" should "get Diced" in {
    player.getDiced()
  }

  "A Player" should "add Tokens" in {
    player.addTokens()
  }

  "A Player" should "get token by id" in {
    player.getTokenById(1)
  }
  it should "return null" in {
    player.getTokenById(0)
  }

  "A Player" should "get available tokens" in {
    player.getAvailableTokens()
  }

}
