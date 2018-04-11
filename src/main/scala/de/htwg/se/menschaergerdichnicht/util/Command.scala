package de.htwg.se.menschaergerdichnicht.util

import scala.util.Try

/**
 * Created by Anastasia on 06.06.17.
 */
trait Command {
  def action(): Try[_]
  def undo(): Try[_]
}
