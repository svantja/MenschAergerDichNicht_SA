package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldMockImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.TokenInterface

/**
 * Created by Anastasia on 26.06.17.
 */
case class FieldMock() extends FieldInterface {
  var tokenId = 1
  def setToken(token: Int) = token
  def getToken(): Int = 1
  def removeToken() = 1
}
