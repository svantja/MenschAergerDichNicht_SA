package de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldMicroImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.Field
import play.api.libs.json.Json
import spray.json.DefaultJsonProtocol

class FieldHttpServer(var field: FieldInterface) extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  import FieldJsonProtocol._
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route: Route = get {
    path("field" / "setToken") {
      entity(as[SetToken]) {
        jsValue => {
          field.setToken(jsValue.token)
          complete(StatusCodes.OK)
        }
      }
    } ~
      path("field" / "getToken") {
        complete(StatusCodes.OK -> Json.toJson(field.getToken).toString())
      } ~
      path("field" / "removeToken") {
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

object FieldHttpServerMain {
  def main(args: Array[String]): Unit = {
    val fieldServer = new FieldHttpServer(new Field)
  }
}
