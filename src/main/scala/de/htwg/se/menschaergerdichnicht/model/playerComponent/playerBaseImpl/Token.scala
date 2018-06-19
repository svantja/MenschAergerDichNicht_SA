package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, TokenInterface}
import play.api.libs.json.JsValue

case class Token(var player: PlayerInterface, var position: (FieldInterface, Int), var counter: Int) extends TokenInterface {

  // player token numbers 1 - 4
  var number = Token.setNumber

  // unique id
  var tokenId = Token.newIdNum

  var color = Token.setColor

  var finished: Boolean = false

  def getColor(): Any = color

  def setPlayer(player: PlayerInterface) { this.player = player }

  def getPlayer(): PlayerInterface = player

  def setPosition(position: (FieldInterface, Int)) { this.position = position }

  def getPosition(): (FieldInterface, Int) = position

  def setCounter(counter: Int) { this.counter = counter }

  def getCounter(): Int = counter

  def setFinished(finished: Boolean) { this.finished = finished }

  def getFinished(): Boolean = finished

  override def toType = ???

  override def fromType(input: JsValue) = ???
}

object Token {

  private var idNumber = 0
  val colorList = List("red", "blue", "green", "yellow")

  private def newIdNum = {
    idNumber += 1;
    idNumber
  }
  private def setNumber = {
    idNumber % 4 + 1
  }

  private def setColor = {
    if (idNumber <= 4) {
      colorList.head
    } else if (idNumber <= 8 && idNumber >= 5) {
      colorList(1)
    } else if (idNumber <= 12 && idNumber >= 9) {
      colorList(2)
    } else if (idNumber <= 16 && idNumber >= 13) {
      colorList(3)
    }
  }
}