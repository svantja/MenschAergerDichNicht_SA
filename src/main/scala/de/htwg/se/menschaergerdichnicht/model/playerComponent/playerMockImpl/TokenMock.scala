package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMockImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{ PlayerInterface, TokenInterface }

/**
 * Created by Anastasia on 25.06.17.
 */
abstract case class TokenMock() extends TokenInterface {
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
