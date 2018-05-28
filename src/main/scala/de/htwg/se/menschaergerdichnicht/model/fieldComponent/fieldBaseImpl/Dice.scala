package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl

import javax.inject.Inject

import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayerInterface

case class Dice() {
  var dice: Int = 0
  def rollDice(player: PlayerInterface): Int = {
    var value: Int = 0
    val start = 1
    val end   = 6
    val r = new scala.util.Random
    start + r.nextInt( (end - start) + 1 )
    if (player.house.isFull(player)) {
      for (i <- 1 to 3 if dice != 6){
        value = start + r.nextInt( (end - start) + 1 )
        if(value == 6) {
          dice = value
        }
      }
    }else {
      dice = start + r.nextInt( (end - start) + 1 )
    }
    dice
  }
}
