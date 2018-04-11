import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.PlayingField
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.{FieldInterface, HouseInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.Player

import scala.collection.mutable.ArrayBuffer


print("hello")

val p = PlayingField()

for (i <- p.playingField) {
  println(i.getToken())
}














