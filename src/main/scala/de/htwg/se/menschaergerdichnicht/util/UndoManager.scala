package de.htwg.se.menschaergerdichnicht.util

import scala.collection.mutable
import scala.util.{ Failure, Try }

/**
 * Created by Anastasia on 06.06.17.
 */
class UndoManager {

  var undoStack: mutable.Stack[Command] = mutable.Stack()
  var redoStack: mutable.Stack[Command] = mutable.Stack()

  def action(c: Command): Try[_] = {
    val event = c.action()
    if (event.isSuccess) {
      undoStack.push(c)
      redoStack.clear()
    }
    event
  }

  def undo(c: Command): Try[_] = {
    if (undoStack.nonEmpty) {
      val tmp = undoStack.pop()
      val event = tmp.undo()
      if (event.isSuccess) {
        redoStack.push(tmp)
      }
      event
    } else {
      Failure(new Exception("Not possible to undo right now!"))
    }
  }

  def redo(): Try[_] = {
    if (redoStack.nonEmpty) {
      val tmp = redoStack.pop()
      val event = tmp.action()
      if (event.isSuccess) {
        undoStack.push(tmp)
      }
      event
    } else {
      Failure(new Exception("Not possible to redo right now!"))
    }
  }
}
