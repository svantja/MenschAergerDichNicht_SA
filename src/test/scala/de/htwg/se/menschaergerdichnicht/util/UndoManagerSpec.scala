package de.htwg.se.menschaergerdichnicht.util

import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.{ AddPlayer, Controller }
import org.scalatest.{ FlatSpec, Matchers }

/**
 * Created by Anastasia on 26.06.17.
 */
class UndoManagerSpec extends FlatSpec with Matchers {
  val undoManager = new UndoManager()
  val c = Controller()
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
