package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.TokenInterface

/**
 * Created by Anastasia on 01.05.17.
 */

case class Field() extends FieldInterface {

  var tokenId: Int = -1

  def setToken(token: Int) { this.tokenId = token }

  def getToken(): Int = tokenId

  def removeToken(): Unit = { this.tokenId = -1 }

}
