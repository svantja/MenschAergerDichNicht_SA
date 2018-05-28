package de.htwg.se.menschaergerdichnicht.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.se.menschaergerdichnicht.aview.tui.Tui
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.ControllerInterface

class HttpServer(controller: ControllerInterface, tui: Tui) {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route: Route = get {
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>HTWG Mensch</h1>"))
    }
    path("Mensch") {
      printHtmlTui
    } ~
      path("Mensch" / Segment) { command => {
        tui.processInputLine(command)
        printHtmlTui
      }
      }
  }
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  def printHtmlTui: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>HTWG MenschSpiel</h1>" + "<pre>" + tui.printingTui() + "</pre>"))
  }

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}
