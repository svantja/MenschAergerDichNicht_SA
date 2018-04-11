package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMockImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.{ Field, House, TargetField }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{ PlayerInterface, PlayersInterface, TokenInterface }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Players, Token }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anastasia on 25.06.17.
 */
case class PlayerMock() extends PlayerInterface {
  var playerId = 1
  val house = House(Player("Birgit", 3))
  val target = TargetField(Player("Birgit", 3))
  var finished = false
  var tokens = ArrayBuffer[TokenInterface]()
  def getTokens(): ArrayBuffer[TokenInterface] = ArrayBuffer()
  def setFinished(finished: Boolean) = false
  def getFinished(): Boolean = false
  def setName(name: String) = "Birgit"
  def getName(): String = "Birgit"
  def setDiced(diced: Int) = 3
  def getDiced(): Int = 3
  def addTokens(): ArrayBuffer[TokenInterface] = ArrayBuffer()
  def getFreeHouse(): Field = Field()
  def getTokenById(tokenId: Int): Token = Token(Player("Birgit", 3), (Field(), 1), 1)
  def getAvailableTokens(): ArrayBuffer[String] = ArrayBuffer()
}

case class PlayersMock() extends PlayersInterface {
  def addPlayer(player: PlayerInterface): PlayersInterface = this
  def removePlayer(): PlayersInterface = this
  def updateCurrentPlayer(player: PlayerInterface): PlayersInterface = this
  def nextPlayer(): PlayersInterface = this
  def getCurrentPlayer: PlayerInterface = Player("Birgit", 3)
  def getAllPlayer: Vector[PlayerInterface] = Vector()
}
