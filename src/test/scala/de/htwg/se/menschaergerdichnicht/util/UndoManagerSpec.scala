package de.htwg.se.menschaergerdichnicht.util

import com.google.inject.Guice
import de.htwg.se.menschaergerdichnicht.MenschAergerDichNichtModule
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.{AddPlayer, Controller}
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.PlayingInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by Anastasia on 26.06.17.
 */
class UndoManagerSpec extends FlatSpec with Matchers {
  val undoManager = new UndoManager()
  val injector = Guice.createInjector(new MenschAergerDichNichtModule)
  val c = new Controller(injector.getInstance(classOf[PlayersInterface]),
    injector.getInstance(classOf[PlayingInterface]))
  val command = AddPlayer("tester", c)
  "An UndoManager" should "have an undoStack" in {
    undoManager.undoStack
  }

  "An UndoManager" should "have an redoStack" in {
    undoManager.redoStack
  }

  "An UndoManager" should "have an action" in {
    undoManager.action(command)
  }

  "An UndoManager" should "have an undo" in {
    undoManager.undo(command)
  }

  "An UndoManager" should "have a redo" in {
    undoManager.redo()
  }
}
