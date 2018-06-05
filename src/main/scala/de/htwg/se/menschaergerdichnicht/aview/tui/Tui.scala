package de.htwg.se.menschaergerdichnicht.aview.tui

import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.{ ControllerInterface, GameState }
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState._
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.menschaergerdichnicht.util.Observer
import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
/**
 * Created by Anastasia on 01.05.17.
 */
class Tui(controller: ControllerInterface) extends Observer with LazyLogging {

  val DEFAULT_TOKEN = 17

  private val NEWLINE = System.getProperty("line.separator")

  var home_one = ArrayBuffer("o", "o", "o", "o")
  var home_two = ArrayBuffer("o", "o", "o", "o")
  var home_three = ArrayBuffer("o", "o", "o", "o")
  var home_four = ArrayBuffer("o", "o", "o", "o")
  var playing_field = ArrayBuffer("O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O",
    "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O")
  var field = ArrayBuffer(
    home_one(0), home_one(1), " ", " ", playing_field(8), playing_field(9), playing_field(10), " ", " ", home_two(0), home_two(1), //11
    home_one(2), home_one(3), " ", " ", playing_field(7), "o", playing_field(11), " ", " ", home_two(2), home_two(3), //22
    " ", " ", " ", " ", playing_field(6), "o", playing_field(12), " ", " ", " ", " ", //33
    " ", " ", " ", " ", playing_field(5), "o", playing_field(13), " ", " ", " ", " ", //44
    playing_field(0), playing_field(1), playing_field(2), playing_field(3), playing_field(4), " ", playing_field(14), playing_field(15), playing_field(16), playing_field(17), playing_field(18), //55
    playing_field(39), "o", "o", "o", "o", " ", "o", "o", "o", "o", playing_field(19), //66
    playing_field(38), playing_field(37), playing_field(36), playing_field(35), playing_field(34), "o", playing_field(24), playing_field(23), playing_field(22), playing_field(21), playing_field(20), //77
    " ", " ", " ", " ", playing_field(33), "o", playing_field(25), " ", " ", " ", " ", //88
    " ", " ", " ", " ", playing_field(32), "o", playing_field(26), " ", " ", " ", " ", //99
    home_four(0), home_four(1), " ", " ", playing_field(31), "o", playing_field(27), " ", " ", home_three(0), home_three(1), //110
    home_four(2), home_four(3), " ", " ", playing_field(30), playing_field(29), playing_field(28), " ", " ", home_three(2), home_three(3)
  )

  printTui()

  def processInputLine(input: String): Boolean = {
    var continue = true
    input match {
      case "q" => continue = false
      case "start" => controller.startGame()
      case "r" => controller.startGame()
      case "ready" => controller.startGame()
      case "new" => controller.newGame()
      case _ => processMoreParameters(input)
    }
    continue
  }

  private def processMoreParameters(input: String): Unit = {
    input.split(" ").toList match {
      case "add" :: player :: Nil => controller.addPlayer(player)
      case "move" :: tokenId :: Nil => controller.chooseToken(strToInt(tokenId, DEFAULT_TOKEN))
      case _ => controller.message = "False input"
    }
  }

  def strToInt(s: String, default: Int): Int = {
    try {
      s.toInt
    } catch {
      case _: Exception => default
    }
  }

  def paintStartFields(numPlayer: Int): ArrayBuffer[String] = {
    var f = field
    var home_1 = home_one
    var home_2 = home_two
    var home_3 = home_three
    var home_4 = home_four
    numPlayer match {
      case 1 => {
        for (i <- 0 until home_1.length) home_1(i) = "r"
        f(0) = home_1(0)
        f(1) = home_1(1)
        f(11) = home_1(2)
        f(12) = home_1(3)

      }
      case 2 => {
        for (i <- 0 until home_1.length) home_1(i) = "r"
        for (i <- 0 until home_2.length) home_2(i) = "b"

        f(0) = home_1(0)
        f(1) = home_1(1)
        f(11) = home_1(2)
        f(12) = home_1(3)
        f(9) = home_2(0)
        f(10) = home_2(1)
        f(20) = home_2(2)
        f(21) = home_2(3)
      }
      case 3 => {
        for (i <- 0 until home_1.length) home_1(i) = "r"
        for (i <- 0 until home_2.length) home_2(i) = "b"
        for (i <- 0 until home_3.length) home_3(i) = "g"

        f(0) = home_1(0)
        f(1) = home_1(1)
        f(11) = home_1(2)
        f(12) = home_1(3)
        f(9) = home_2(0)
        f(10) = home_2(1)
        f(20) = home_2(2)
        f(21) = home_2(3)
        f(108) = home_3(0)
        f(109) = home_3(1)
        f(119) = home_3(2)
        f(120) = home_3(3)

      }
      case 4 => {
        for (i <- 0 until home_1.length) home_1(i) = "r"
        for (i <- 0 until home_2.length) home_2(i) = "b"
        for (i <- 0 until home_3.length) home_3(i) = "g"
        for (i <- 0 until home_4.length) home_4(i) = "y"

        f(0) = home_1(0)
        f(1) = home_1(1)
        f(11) = home_1(2)
        f(12) = home_1(3)
        f(9) = home_2(0)
        f(10) = home_2(1)
        f(20) = home_2(2)
        f(21) = home_2(3)
        f(108) = home_3(0)
        f(109) = home_3(1)
        f(119) = home_3(2)
        f(120) = home_3(3)
        f(99) = home_4(0)
        f(100) = home_4(1)
        f(110) = home_4(2)
        f(111) = home_4(3)
      }
      case _ => {}
    }
    return f
  }

  def printPlaying(): ArrayBuffer[String] = {
    var f = field
    val pl = controller.players.getAllPlayer
    var pf = ArrayBuffer("O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O",
      "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O")
    for (p <- pl) {
      for (t <- p.getTokens()) {
        val c = t.getColor()
        if (t.getCounter() != 0) {
          if (t.getFinished()) {
            println("finished")
          } else {
            val pos = t.getPosition()._2
            c match {
              case "red" =>
                pf(pos) = "r"; home_one(t.tokenId - 1) = "o"
              case "blue" =>
                pf(pos) = "b"; home_two(t.tokenId - 5) = "o"
              case "green" =>
                pf(pos) = "g"; home_three(t.tokenId - 9) = "o"
              case "yellow" =>
                pf(pos) = "y"; home_four(t.tokenId - 13) = "o"
              case _ => {}
            }
          }
        } else {
          val pos = t.getPosition()._2
          c match {
            case "red" => home_one(pos) = "r"
            case "blue" => home_two(pos) = "b"
            case "green" => home_three(pos) = "g"
            case "yellow" => home_four(pos) = "y"
            case _ => {}
          }
        }
      }
    }

    f = ArrayBuffer(
      home_one(0), home_one(1), " ", " ", pf(8), pf(9), pf(10), " ", " ", home_two(0), home_two(1), //11
      home_one(2), home_one(3), " ", " ", pf(7), "o", pf(11), " ", " ", home_two(2), home_two(3), //22
      " ", " ", " ", " ", pf(6), "o", pf(12), " ", " ", " ", " ", //33
      " ", " ", " ", " ", pf(5), "o", pf(13), " ", " ", " ", " ", //44
      pf(0), pf(1), pf(2), pf(3), pf(4), " ", pf(14), pf(15), pf(16), pf(17), pf(18), //55
      pf(39), "o", "o", "o", "o", " ", "o", "o", "o", "o", pf(19), //66
      pf(38), pf(37), pf(36), pf(35), pf(34), "o", pf(24), pf(23), pf(22), pf(21), pf(20), //77
      " ", " ", " ", " ", pf(33), "o", pf(25), " ", " ", " ", " ", //88
      " ", " ", " ", " ", pf(32), "o", pf(26), " ", " ", " ", " ", //99
      home_four(0), home_four(1), " ", " ", pf(31), "o", pf(27), " ", " ", home_three(0), home_three(1), //110
      home_four(2), home_four(3), " ", " ", pf(30), pf(29), pf(28), " ", " ", home_three(2), home_three(3)
    )
    return f
  }

  override def update: Unit = printTui()

  def printTui(): Unit = logger.info(printingTui)

  def printingTui(): String = {
    var r = ""
    if (controller.gameState == NONE) {
      var p = controller.toJson
      println(p)
      r += "\nadd: Add Player, start: Start Game, ready: next round, move: Move selected Token, q: Quit Game"
    }
    if (controller.gameState == PREPARE) {
      var b = controller.toJson
      println(b)
      val p = controller.players
      val f = paintStartFields(controller.players.getAllPlayer.length)
      r += "\n"
      for (i <- 0 to 10) r += "" + f(i)
      r += "\n"
      for (i <- 11 to 21) r += "" + f(i)
      r += "\n"
      for (i <- 22 to 32) r += "" + f(i)
      r += "\n"
      for (i <- 33 to 43) r += "" + f(i)
      r += "\n"
      for (i <- 44 to 54) r += "" + f(i)
      r += "\n"
      for (i <- 55 to 65) r += "" + f(i)
      r += "\n"
      for (i <- 66 to 76) r += "" + f(i)
      r += "\n"
      for (i <- 77 to 87) r += "" + f(i)
      r += "\n"
      for (i <- 88 to 98) r += "" + f(i)
      r += "\n"
      for (i <- 99 to 109) r += "" + f(i)
      r += "\n"
      for (i <- 110 to 120) r += "" + f(i)
      r += "\n"
    }
    if(controller.gameState == SELECT) {
      val current = controller.players.getCurrentPlayer
      var b = controller.toJson
      println(b)
      val f = printPlaying()
      r += "\n"
      for (i <- 0 to 10) r += "" + f(i)
      r += "\n"
      for (i <- 11 to 21) r += "" + f(i)
      r += "\n"
      for (i <- 22 to 32) r += "" + f(i)
      r += "\n"
      for (i <- 33 to 43) r += "" + f(i)
      r += "\n"
      for (i <- 44 to 54) r += "" + f(i)
      r += "\n"
      for (i <- 55 to 65) r += "" + f(i)
      r += "\n"
      for (i <- 66 to 76) r += "" + f(i)
      r += "\n"
      for (i <- 77 to 87) r += "" + f(i)
      r += "\n"
      for (i <- 88 to 98) r += "" + f(i)
      r += "\n"
      for (i <- 99 to 109) r += "" + f(i)
      r += "\n"
      for (i <- 110 to 120) r += "" + f(i)
      r += "\n"
      if(current.getDiced() == 0){
        r += "Cannot move, next player.\n"
      }else{
        r += "Choose token to move\n"
        r += "diced" + current.getDiced() + "\n"
        r += "avaiable tokens: " + current.getAvailableTokens() + "\n"
      }
    }
    if (controller.gameState == ONGOING) {
      var b = controller.toJson
      println(b)
      val f = printPlaying()
      r += "\n"
      for (i <- 0 to 10) r += "" + f(i)
      r += "\n"
      for (i <- 11 to 21) r += "" + f(i)
      r += "\n"
      for (i <- 22 to 32) r += "" + f(i)
      r += "\n"
      for (i <- 33 to 43) r += "" + f(i)
      r += "\n"
      for (i <- 44 to 54) r += "" + f(i)
      r += "\n"
      for (i <- 55 to 65) r += "" + f(i)
      r += "\n"
      for (i <- 66 to 76) r += "" + f(i)
      r += "\n"
      for (i <- 77 to 87) r += "" + f(i)
      r += "\n"
      for (i <- 88 to 98) r += "" + f(i)
      r += "\n"
      for (i <- 99 to 109) r += "" + f(i)
      r += "\n"
      for (i <- 110 to 120) r += "" + f(i)
      r += "\n"
      r += "\ncurrent: " + controller.players.getCurrentPlayer
    }
    r
  }

  def toHtml: String = {
    val game = this.toString
    var result = game.replace(NEWLINE, "<br>")
    result = result.replace("     ", " &nbsp; &nbsp; ")
    result = result.replace("   ", " &nbsp; ")
    result
  }

}
