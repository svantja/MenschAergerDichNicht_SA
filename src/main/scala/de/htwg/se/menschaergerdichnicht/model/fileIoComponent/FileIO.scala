package de.htwg.se.menschaergerdichnicht.model.fileIoComponent

import play.api.libs.json.JsValue

//trait FileIO {
//  def save(cstate: Board): Unit
//
//  def load: Board
//}

trait ToFromJson[TValue] extends ToFrom[TValue, JsValue]

trait ToFromString[TValue] extends ToFrom[TValue, String]

trait ToFrom[TValue, TType] {
  def toType: TType

  def fromType(input: TType): TValue
}


