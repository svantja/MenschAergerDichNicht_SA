package de.htwg.se.menschaergerdichnicht.model.playerComponent.playerMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, PlayersInterface}
import play.api.libs.json.Json
import spray.json.DefaultJsonProtocol



class PlayerHttpServer(var players: PlayersInterface, var player: PlayerInterface) extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  import PlayerJsonProtocol._

  implicit val system = ActorSystem("my-system1")
  implicit val materializer = ActorMaterializer

  implicit val executionContext = system.dispatcher

  val route: Route = get {

    path("players" / "toString") {
      complete(StatusCodes.OK -> Json.toJson(players.toString).toString())
    }
    path("players" / "removePlayer") {
      complete(StatusCodes.OK -> Json.toJson(players.removePlayer).toString())
    }
    path("players" / "nextPlayer") {
      complete(StatusCodes.OK -> Json.toJson(players.nextPlayer).toString())
    }
    path("players" / "updateCurrentPlayer" / Segment) { inputString => {
      val jsonString = Json.parse(inputString)
      val name = (jsonString \ "name").as[String]
      val diced = (jsonString \ "diced").as[Int]
      val newPlayer = new Player(name, diced)
      println("New Player : -> " + newPlayer)
      players = players.updateCurrentPlayer(newPlayer)
      complete(StatusCodes.OK)
    }}
    //path("players" / "getCurrentPlayer") {
    //  complete(StatusCodes.OK -> players.getCurrentPlayer.asInstanceOf[Player])
    //}

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)

    def unbind = {
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ = system.terminate())
    }

  }

  object PlayerHttpServerMain {
    def main(args: Array[String]): Unit = {
      val player = new PlayerHttpServer(new Players(), new Player("", 0))
    }
  }

}
