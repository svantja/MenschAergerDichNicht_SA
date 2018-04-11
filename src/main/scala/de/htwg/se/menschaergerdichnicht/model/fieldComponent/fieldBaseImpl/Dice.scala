package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import javax.inject.Inject

import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayerInterface

case class Dice() {
  var dice: Int = 0
  def rollDice(player: PlayerInterface): Int = {
    val r = scala.util.Random
    if (player.house.isFull(player)) {
      for (i <- 1 to 3) {
        do {
          dice = r.nextInt(7)
        } while (dice == 0)

        if (dice == 6) { return dice }
      }
    } else {
      do {
        dice = r.nextInt(7)
      } while (dice == 0)
      return dice
    }
    0
  }
}