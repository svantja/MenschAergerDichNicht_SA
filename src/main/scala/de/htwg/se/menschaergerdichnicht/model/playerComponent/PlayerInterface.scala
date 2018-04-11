package de.htwg.se.menschaergerdichnicht.model.playerComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{ FieldInterface, HouseInterface }
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.{ Field, House, TargetField }
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{ Player, Players, Token }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anastasia on 25.06.17.
 */
trait PlayerInterface {
  var playerId: Int
  val house: HouseInterface
  val target: TargetField
  var finished: Boolean
  var tokens: ArrayBuffer[TokenInterface]
  def getTokens(): ArrayBuffer[TokenInterface]
  def setFinished(finished: Boolean)
  def getFinished(): Boolean
  def setName(name: String)
  def getName(): String
  def setDiced(diced: Int)
  def getDiced(): Int
  def addTokens(): ArrayBuffer[TokenInterface]
  def getFreeHouse(): FieldInterface
  def getTokenById(tokenId: Int): TokenInterface
  def getAvailableTokens(): ArrayBuffer[String]
}

trait PlayersInterface {
  def removePlayer(): PlayersInterface
  def updateCurrentPlayer(player: PlayerInterface): PlayersInterface
  def nextPlayer(): PlayersInterface
  def getCurrentPlayer: PlayerInterface
  def getAllPlayer: Vector[PlayerInterface]
}

