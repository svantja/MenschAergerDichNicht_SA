package de.htwg.se.menschaergerdichnicht.model.fileIoComponent

import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface

/**
 * Created by Anastasia on 26.06.17.
 */
trait FileIoInterface {
  def save(plfield: PlayingInterface): Unit
  def load: Option[PlayingInterface]
}
