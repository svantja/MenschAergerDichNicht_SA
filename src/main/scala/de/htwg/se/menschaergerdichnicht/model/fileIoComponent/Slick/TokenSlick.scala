package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick

import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Dao
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, TokenInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Token, Players,Player}
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

object TokenSlick extends Dao[TokenInterface, Long]{

  import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.SlickSB._

  case class TokenData(id: Long, playeId: Long, color: String, positionId: Int, counter: Int)

  class TokenTable(tag: Tag) extends Table[TokenData](tag, "TOKENS"){
    def id = column[Long]("TOKEN_ID", O.PrimaryKey)
    def playerId = column[Long]("PLAYERID")
    def color = column[String]("COLOR")
    def positionId = column[Int]("POSITIONID")
    def counter = column[Int]("COUNTER")
    def * = (id, playerId, color, positionId, counter).mapTo[TokenData]
  }

  val tokens = TableQuery[TokenTable]

  val setup = DBIO.seq(tokens.schema.create)

  Await.result(db.run(setup), Duration.Inf)

  var idDirty: Long = 0

  override def create(token: TokenInterface): Long = {
    idDirty += 1
    Await.result(db.run(tokens += TokenData(token.tokenId, token.getPlayer().playerId, token.color.toString, token.position._2, token.counter)), Duration.Inf)
    idDirty
  }


  override def read(id: Long): Try[TokenInterface] = {
    val tokensQuery = tokens.filter(_.id === id)
    val tokensFuture = SlickSB.db.run(tokensQuery.result)


  //PlayerSlick.read(tok.playeId).get.players.filter(p => p.playerId == tok.playeId).head
    val tokenVector = Await.result(tokensFuture, Duration.Inf).map { tok =>
      Token(PlayerSlick.read(tok.playeId).get, (PlayingFieldSlick.read(0).get, tok.positionId), tok.counter)
    }

    Await.result(tokensFuture, Duration.Inf).headOption match {
      case Some(value) => Success(Token(PlayerSlick.read(value.playeId).get, (PlayingFieldSlick.read(value.positionId).get, value.positionId), value.counter))
      case None => Failure(new Exception("ID:" + id + " doesn't exist"))
    }
  }

  override def delete(id: Long): Unit = {
    val playerQuery = tokens.filter(_.id === id)
    val playersFuture = SlickSB.db.run(playerQuery.delete)
    val tokenVector = Await.result(playersFuture, Duration.Inf)
  }

  def iterIds: Iterable[Long] = Await.result(db.run(tokens.map(_.id).result), Duration.Inf)
}

