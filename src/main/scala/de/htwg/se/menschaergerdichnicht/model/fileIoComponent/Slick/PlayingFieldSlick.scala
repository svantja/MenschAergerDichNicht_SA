package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick


import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Dao
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.fieldBaseImpl.{Field, PlayingField}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, TokenInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players, Token}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

object PlayingFieldSlick extends Dao[FieldInterface, Long]{

  import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.SlickSB._

  case class FieldData(id: Long, tokenId: Int, unique: Long = 0L)

  class FieldTable(tag: Tag) extends Table[FieldData](tag, "TOKENS"){
    def id = column[Long]("ID")
    def tokenId = column[Int]("TOKENID")
    def unique = column[Long]("TOKEN_ID", O.PrimaryKey)
    def * = (id, tokenId, unique).mapTo[FieldData]
  }

  val fields = TableQuery[FieldTable]

  val setup = DBIO.seq(fields.schema.create)

  Await.result(db.run(setup), Duration.Inf)

  var idDirty: Int = -1

  override def create(field: FieldInterface): Long = {
    idDirty += 1
    Await.result(db.run(fields += FieldData(idDirty, field.tokenId, idDirty)), Duration.Inf)
    idDirty
  }


  override def read(id: Long): Try[FieldInterface] = {
    val tokensQuery = fields.filter(_.id === id)
    val tokensFuture = SlickSB.db.run(tokensQuery.result)

    val tokenVector = Await.result(tokensFuture, Duration.Inf).map { f =>
      Field()
    }

    Await.result(tokensFuture, Duration.Inf).headOption match {
      case Some(value) => Success(Field())
      case None => Failure(new Exception("ID:" + id + " doesn't exist"))
    }
  }

  override def delete(id: Long): Unit = {
    val playerQuery = fields.filter(_.id === id)
    val playersFuture = SlickSB.db.run(playerQuery.delete)
    val tokenVector = Await.result(playersFuture, Duration.Inf)
  }

  def iterIds: Iterable[Long] = Await.result(db.run(fields.map(_.id).result), Duration.Inf)
}

