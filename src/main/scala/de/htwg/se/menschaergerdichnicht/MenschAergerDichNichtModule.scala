package de.htwg.se.menschaergerdichnicht

import com.google.inject.AbstractModule
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent._
import de.htwg.se.menschaergerdichnicht.model.fieldComponent._
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent._
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent._
import net.codingwell.scalaguice.ScalaModule

/**
 * Created by Anastasia on 26.06.17.
 */
class MenschAergerDichNichtModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {

    bind[FieldInterface].to[fieldBaseImpl.Field]
    bind[PlayingInterface].to[fieldBaseImpl.PlayingField]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    //bind[FileIoInterface].to[fileIoJsonImpl.FileIO]
  }
}

class MenschAergerDichNichtMicroModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {

    bind[ControllerInterface].to[controllerBaseImpl.Controller]

//    bind[FieldInterface].to[fieldBaseImpl.Field]
    bind[PlayingInterface].to[fieldBaseImpl.PlayingField]

    bind[PlayersInterface].to[playerBaseImpl.Players]

//    bind[PlayersInterface].to[playerMicroImpl.PlayerMicro]

    bind[FieldInterface].to[fieldMicroImpl.FieldMicro]

  }
}
