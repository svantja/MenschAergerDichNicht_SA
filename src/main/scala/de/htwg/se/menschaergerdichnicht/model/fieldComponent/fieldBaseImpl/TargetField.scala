package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{ FieldInterface, TargetInterface }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayerInterface

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anastasia on 01.05.17.
 */
case class TargetField(player: PlayerInterface) extends TargetInterface {

  val targetField = new ArrayBuffer[FieldInterface]

  for (i <- 1 to 4) {
    targetField += Field()
  }

  def isFull(player: PlayerInterface): Boolean = {
    for (field <- player.target.targetField) {
      if (field.tokenId == -1) {
        return false
      }
    }
    true
  }
}
