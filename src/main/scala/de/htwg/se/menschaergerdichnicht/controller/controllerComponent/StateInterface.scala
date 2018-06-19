package de.htwg.se.menschaergerdichnicht.controller.controllerComponent

import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState.GameState
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.{ToFromJson, ToFromString}
import play.api.libs.json.JsValue

class StateInterface extends ToFromString[GameState] {
  override def toType = ???

  override def fromType(input: String): GameState = input match{
    case "NONE" => GameState.NONE
    case "PREPARE" => GameState.PREPARE
    case "ONGOING" => GameState.ONGOING
    case "DICED" => GameState.DICED
    case "SELECT" => GameState.SELECT
    case "FINISHED" => GameState.FINISHED
    case "SAVED" => GameState.SAVED
    case "LOADED" => GameState.LOADED
    case "NOTLOADED" => GameState.NOTLOADED
    case _ => throw new IllegalArgumentException
  }
}
