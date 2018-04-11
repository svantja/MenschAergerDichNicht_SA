import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player

case class Dice() {
  var dice: Int = 0
  def rollDice(player: Player) : Int = {
    val r = scala.util.Random;
    if (player.house.isFull(player)) {
      for (i <- 1 to 3) {
        do {
          dice = r.nextInt(7)
        } while(dice == 0)

        if (dice == 6) { return dice }
      }
    } else {
      do {
        dice = r.nextInt(7)
      } while(dice == 0)
      return dice
    }
    9
  }
}

val dice = new Dice
val player = new Player("test", 0)
player.house

player.house.isFull(player)


