package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{ FieldInterface, HouseInterface }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayerInterface

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anastasia on 01.05.17.
 */
case class House(player: PlayerInterface) extends HouseInterface {
  val house = new ArrayBuffer[FieldInterface]

  for (i <- 1 to 4) {
    house += Field()
  }

  def isFull(player: PlayerInterface): Boolean = {
    var bool: Boolean = true
    for (field <- player.house.house) {
      if (field.tokenId == -1) {
        bool = false
      }
    }
    bool
  }

}
