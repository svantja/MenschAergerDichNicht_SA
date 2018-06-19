package de.htwg.se.menschaergerdichnicht.model.playerComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.ToFromJson

/**
 * Created by Anastasia on 25.06.17.
 */
trait TokenInterface extends ToFromJson[TokenInterface] {
  var position: (FieldInterface, Int)
  var counter: Int
  var number: Int
  var tokenId: Int
  var color: Any
  var finished: Boolean
  def getColor(): Any
  def setPlayer(player: PlayerInterface)
  def getPlayer(): PlayerInterface
  def setPosition(position: (FieldInterface, Int))
  def getPosition(): (FieldInterface, Int)
  def setCounter(counter: Int)
  def getCounter(): Int
  def setFinished(finished: Boolean)
  def getFinished(): Boolean
}
