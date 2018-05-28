package de.htwg.se.menschaergerdichnicht.model.fieldComponent

import de.htwg.se.menschaergerdichnicht.model.playerComponent.{ PlayerInterface, PlayersInterface, TokenInterface }

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Anastasia on 25.06.17.
 */
trait FieldInterface {
  var tokenId: Int
  def setToken(token: TokenInterface)
  def getToken(): Int
  def removeToken()
}

trait HouseInterface {
  val house: ArrayBuffer[FieldInterface]
  def isFull(player: PlayerInterface): Boolean
}

trait PlayingInterface {
  val playingField: ArrayBuffer[FieldInterface]
  def getField(id: Int): FieldInterface
  def moveToken(token: TokenInterface, num: Int, players: PlayersInterface): Unit
  def kickToken(tokenId: Int, player: PlayerInterface, players: PlayersInterface): Boolean
  def moveToTarget(token: TokenInterface, i: Int): Unit
  def moveToStart(token: TokenInterface, players: PlayersInterface): Unit
}

trait TargetInterface {
  val targetField: ArrayBuffer[FieldInterface]
  def isFull(player: PlayerInterface): Boolean
}