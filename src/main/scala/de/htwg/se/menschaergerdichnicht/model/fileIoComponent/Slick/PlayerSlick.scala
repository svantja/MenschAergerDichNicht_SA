package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick

import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Dao
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, TokenInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players, Token}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

object PlayerSlick extends Dao[PlayerInterface, Long]{

  import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.SlickSB._

  case class PlayerData(id: Long, playerId: Int, name: String, diced: Int)

  class PlayerTable(tag: Tag) extends Table[PlayerData](tag, "PLAYER"){
    def id = column[Long]("PLAYER_ID", O.PrimaryKey)
    def playerId = column[Int]("PLAYERID")
    def name = column[String]("NAME")
    def diced = column[Int]("DICED")
    def * = (id, playerId, name, diced).mapTo[PlayerData]
  }


  val players = TableQuery[PlayerTable]

  val setup = DBIO.seq(
    // Create the tables, including primary and foreign keys
    (players.schema).create)

  Await.result(db.run(setup), Duration.Inf)

  var idDirty: Long = 0

  override def create(player: PlayerInterface): Long = {
    idDirty += 1
    Await.result(db.run(players += PlayerData(player.playerId, player.playerId, player.getName(), player.getDiced())), Duration.Inf)
    idDirty
  }


  override def read(id: Long): Try[PlayerInterface] = {
    val tokensQuery = players.filter(_.id === id)
    val tokensFuture = SlickSB.db.run(tokensQuery.result)



    val tokenVector = Await.result(tokensFuture, Duration.Inf).map { tok =>
      Player(tok.name, tok.diced)
    }
    println(id + "hhhhhhhhhhhhh")
    Await.result(tokensFuture, Duration.Inf).headOption match {
      case Some(value) => Success(Player(value.name, value.diced))
      case None => Failure(new Exception("ID:" + id + " doesn't exist"))
    }
  }

  override def delete(id: Long): Unit = {
    val playerQuery = players.filter(_.id === id)
    val playersFuture = SlickSB.db.run(playerQuery.delete)
    val tokenVector = Await.result(playersFuture, Duration.Inf)
    //Await.result(db.run(players.filter(_.playerId == id.toInt).delete), Duration.Inf)
  }

  def iterIds: Iterable[Long] = Await.result(db.run(players.map(_.id).result), Duration.Inf)

}

