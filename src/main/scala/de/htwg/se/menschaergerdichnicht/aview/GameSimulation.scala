package de.htwg.se.menschaergerdichnicht.aview


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

    val scn = scenario("GameSimulation")
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

    setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

}
