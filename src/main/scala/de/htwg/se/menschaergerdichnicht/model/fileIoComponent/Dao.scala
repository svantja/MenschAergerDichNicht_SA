package de.htwg.se.menschaergerdichnicht.model.fileIoComponent

import scala.util.Try

trait Dao[DATA, ID] {
  def create(data: DATA): ID

  def read(id: ID): Try[DATA]

  def delete(id: ID)
}