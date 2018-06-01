package de.htwg.se.menschaergerdichnicht.util

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import spray.json.DefaultJsonProtocol
import spray.json.{JsValue, _}
import play.api.libs.ws.JsonBodyReadables._
import play.api.libs.ws.JsonBodyWritables._

import scala.concurrent.Await
import scala.concurrent.duration._

object Http extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  import scala.concurrent.ExecutionContext.Implicits._

  implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }

  implicit val materializer = ActorMaterializer()
  val wsClient = StandaloneAhcWSClient()

  def get(url: String, body: JsValue): Unit = {
    println(url)
    val result = wsClient.url(url)
      .withBody(Json.parse(body.toString))
      .withRequestTimeout(5 minute)
      .get()
    Await.result(result, 3 seconds)
  }

  def post(url: String, body: JsValue): Unit = {
    println(url)
    val result = wsClient.url(url)
      .withRequestTimeout(5 minute)
      .post(Json.parse(body.toString))
    Await.result(result, 3 seconds)
  }

  def get(url: String): Unit = {
    println(url)
    val result = wsClient.url(url)
      .withRequestTimeout(5 minute)
      .get()
    Await.result(result, 3 seconds)
  }

  def getResult(url: String, body: JsValue): JsValue = {
    println(url)
    val result = wsClient.url(url)
      .withBody(Json.parse(body.toString))
      .withRequestTimeout(5 minute)
      .get()
      .map(response => response.body[play.api.libs.json.JsValue])
    Await.result(result, 5 seconds).toString.parseJson
  }

  def  getResult(url: String): JsValue = {
    println(url)
    val result = wsClient.url(url)
      .withRequestTimeout(5 minute)
      .get()
      .map(response => response.body[play.api.libs.json.JsValue])
    Await.result(result, 5 seconds).toString.parseJson
  }

}
