package de.htwg.se.menschaergerdichnicht.controller.controllerComponent

/**
 * Created by Anastasia on 25.06.17.
 */
object GameState extends Enumeration {
  type GameState = Value
  val NONE, PREPARE, ONGOING, FINISHED, DICED, SELECT, SAVED, LOADED, NOTLOADED = Value

  val map = Map[GameState, String](
    NONE -> "",
    PREPARE -> "Prepare",
    ONGOING -> "",
    DICED -> "",
    SELECT -> "",
    FINISHED -> "Game is finished!",
    SAVED -> "Game saved",
    LOADED -> "A new Game is loaded",
    NOTLOADED -> "Could not load"
  )

  def message(gameState: GameState): Unit = {
    map(gameState)
  }
}
