package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick

import slick.jdbc.H2Profile.api._

object SlickSB {
  val db = Database.forConfig("h2mem1")
}