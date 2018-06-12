package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldMicroImpl

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{FieldInterface, fieldMicroImpl}
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.PlayingField
import de.htwg.se.menschaergerdichnicht.model.playerComponent.TokenInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Token
import de.htwg.se.menschaergerdichnicht.util.Http
import spray.json.DefaultJsonProtocol

case class SetToken(token: Int)

object FieldJsonProtocol extends DefaultJsonProtocol {
  implicit val fieldFormat = jsonFormat1(SetToken)
}

class FieldMicro(url: String) extends FieldInterface{

  def this() = this("http://localhost:8081/field/")

  import FieldJsonProtocol._
  import spray.json._

  override var tokenId: Int = -1

  override def setToken(token: Int): Unit = {
    Http.get(url + "setToken", SetToken(tokenId).toJson)
  }

  override def getToken(): Int = Http.getResult(url + "getToken").convertTo[Int]

  override def removeToken(): Unit = Http.getResult(url + "removeToken").convertTo[Unit]
}
