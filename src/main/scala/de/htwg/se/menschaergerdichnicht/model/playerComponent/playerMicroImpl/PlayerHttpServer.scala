package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import play.api.libs.json.Json
import spray.json.DefaultJsonProtocol

class PlayerHttpServer(var players: PlayersInterface, var player: PlayerInterface) extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  import PlayerJsonProtocol._

  implicit val system = ActorSystem("my-system1")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route: Route = get {

    path("players" / "toString") {
      complete(StatusCodes.OK -> Json.toJson(players.toString).toString())
    } ~
      path ("players" / "add" / Segment) { input => {
        players = players.addPlayer(input)
        println("lalallaaaaaa" + players.getAllPlayer)
        complete(StatusCodes.OK)
      }} ~
      path("players" / "removePlayer") {
        players = players.removePlayer()
        complete(StatusCodes.OK)
      } ~
//      path("players" / "updateCurPlayer") {
//        entity(as[UpdateCurrentPlayer]) {
//          jsValue => {
//            players = players.updateCurrentPlayer(jsValue.player)
//            complete(StatusCodes.OK)
//          }
//        }
//      }
      path("players" / "nextPlayer") {
        players = players.nextPlayer
        complete(StatusCodes.OK)
      }
  }
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

object PlayerHttpServerMain {
  def main(args: Array[String]): Unit = {
    val player = new PlayerHttpServer(new Players(), new Player("", 0))
  }
}
