package de.htwg.se.menschaergerdichnicht.aview

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class GameSimulation extends Simulation{


  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("de,en-US;q=0.7,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")

  val headers_0 = Map(
    "Accept" -> "*/*",
    "Pragma" -> "no-cache")

  val headers_4 = Map("Upgrade-Insecure-Requests" -> "1")

  val uri2 = "http://detectportal.firefox.com/success.txt"

  val scn = scenario("Add two Players and Start")
    .exec(http("add a")
      .get("/Mensch/add%20a")
      .headers(headers_4))
    .exec(http("add b")
      .get("/Mensch/add%20b")
      .headers(headers_4))
    .exec(http("start")
      .get("/Mensch/ready")
      .headers(headers_4))
    .exec(http("ready")
      .get("/Mensch/ready")
      .headers(headers_4))




  val scn_2 = scenario("Add, Start, New Game, Save And Load")
    .exec(http("add a")
      .get("/Mensch/add%20a")
      .headers(headers_4))
    .exec(http("add b")
      .get("/Mensch/add%20b")
      .headers(headers_4))
    .exec(http("add c")
      .get("/Mensch/add%20c")
      .headers(headers_4))
    .exec(http("add d")
      .get("/Mensch/add%20d")
      .headers(headers_4))
    .exec(http("start")
      .get("/Mensch/ready")
      .headers(headers_4))
    .exec(http("ready")
      .get("/Mensch/ready")
      .headers(headers_4))
    .exec(http("save")
      .get("/Mensch/save")
      .headers(headers_4))
    .exec(http("load")
      .get("/Mensch/load")
      .headers(headers_4))
    .exec(http("ready")
      .get("/Mensch/ready")
      .headers(headers_4))
    .exec(http("save")
      .get("/Mensch/save")
      .headers(headers_4))
    .exec(http("load")
      .get("/Mensch/load")
      .headers(headers_4))


  val scn_3 = scenario("Add one Player and start")
    .exec(http("add a")
      .get("/Mensch/add%20a")
      .headers(headers_4))
    .exec(http("add b")
      .get("/Mensch/add%20b")
      .headers(headers_4))
    .exec(http("start")
      .get("/Mensch/ready")
      .headers(headers_4))
    .exec(http("ready")
      .get("/Mensch/ready")
      .headers(headers_4))


  setUp(
    scn.inject(rampUsers(10) over (10 seconds)),
    scn_2.inject(rampUsers(1) over (10 seconds)),
    scn_3.inject(rampUsers(10) over (10 seconds))
  ).protocols(httpProtocol)

}
