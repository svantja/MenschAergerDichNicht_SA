package de.htwg.se.menschaergerdichnicht.model.fileIoComponent

import java.lang.NullPointerException

import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import Helpers._
import com.google.inject.internal.InternalFlags.NullableProvidesOption
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.ControllerInterface
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.GameState.{GameState, NONE}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.PlayersInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.controllerBaseImpl.Controller
import org.bson.BsonDocument
import org.mongodb.scala.bson.BsonDocument
import play.api.libs.json.{JsValue, Json}
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.ToFromString

import scala.util.{Failure, Success, Try}

case class MongoDB(collectionName: String, c: ControllerInterface) extends Dao[PlayersInterface, Long] {

  def this() = this("players")
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("mensch")
  val collection: MongoCollection[Document] = database.getCollection(collectionName)
  var count = try {
    val result: Document = collection.find().sort(BsonDocument("_id:-1")).limit(1).headResult()
    val json = (Json.parse(result.toJson()) \ "_id").get
    json.toString().toInt
  } catch {
    case _: NullPointerException => 0
  }


  override def create(data: PlayersInterface): Long = {
    count += 1
    collection.insertOne(Document("_id" -> count, "players" -> BsonDocument(data.toString())))
    count.toLong
  }

  override def read(id: Long): Try[PlayersInterface] = {
    try {
      val result: Document = collection.find(equal("_id", id.toInt)).first().headResult()
      val json = (Json.parse(result.toJson()) \ "players").get
      Success(c.fromType(json))
    }
  }

  override def delete(id: Long): Unit = {
    collection.deleteOne(equal("_id", id.toInt)).results()
  }

//  override def iterIds: Iterable[Long] = {
//    val results: Seq[Document] = collection.find().results()
//    results.map(doc => (Json.parse(doc.toJson()) \ "_id").get.toString().toLong).toVector
//  }

}

/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.concurrent.TimeUnit

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import org.mongodb.scala._

object Helpers {

  implicit class DocumentObservable[C](val observable: Observable[Document]) extends ImplicitObservable[Document] {
    override val converter: (Document) => String = (doc) => doc.toJson
  }

  implicit class GenericObservable[C](val observable: Observable[C]) extends ImplicitObservable[C] {
    override val converter: (C) => String = (doc) => doc.toString
  }

  trait ImplicitObservable[C] {
    val observable: Observable[C]
    val converter: (C) => String

    def results(): Seq[C] = Await.result(observable.toFuture(), Duration(10, TimeUnit.SECONDS))

    def headResult() = Await.result(observable.head(), Duration(10, TimeUnit.SECONDS))

    def printResults(initial: String = ""): Unit = {
      if (initial.length > 0) print(initial)
      results().foreach(res => println(converter(res)))
    }

    def printHeadResult(initial: String = ""): Unit = println(s"${initial}${converter(headResult())}")
  }

}
