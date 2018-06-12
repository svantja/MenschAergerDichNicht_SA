package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick

import de.htwg.se.menschaergerdichnicht.Game.injector
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Dao
import de.htwg.se.menschaergerdichnicht.model.fieldComponent.FieldInterface
import de.htwg.se.menschaergerdichnicht.model.playerComponent.{PlayerInterface, TokenInterface}
import de.htwg.se.menschaergerdichnicht.model.playerComponent.playerBaseImpl.{Player, Players, Token}
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent
import de.htwg.se.menschaergerdichnicht.controller.controllerComponent.ControllerInterface
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class playerQuery(controller: ControllerInterface){
  object PlayerSlick extends Dao[PlayerInterface, Long]{

    import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.Slick.SlickSB._

    case class PlayerData(id: Long, playerId: Int, name: String, diced: Int, finished: Boolean)

    class PlayerTable(tag: Tag) extends Table[PlayerData](tag, "PLAYER"){
      def id = column[Long]("PLAYER_ID", O.PrimaryKey)
      def playerId = column[Int]("PLAYERID")
      def name = column[String]("NAME")
      def diced = column[Int]("DICED")
      def finished = column[Boolean]("FINISHED")
      def * = (id, playerId, name, diced, finished).mapTo[PlayerData]
    }

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

    val players = TableQuery[PlayerTable]

    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      (players.schema ++ tokens.schema).create)

    Await.result(db.run(setup), Duration.Inf)

    var idDirty: Long = 0

    override def create(player: PlayerInterface): Long = {
      Await.result(db.run(players += PlayerData(player.playerId, player.playerId, player.getName(), player.getDiced(), player.finished)), Duration.Inf)
      Await.result(db.run(tokens ++= player.tokens.map(token => TokenData(token.tokenId, token.getPlayer().playerId, token.color.toString, token.position._2, token.counter))), Duration.Inf)
      //creation of field here.. else error
      println("test")

      idDirty
    }


    override def read(id: Long): Try[PlayerInterface] = {
      val playersQuery = players.filter(_.id === id)
      val playerssFuture = SlickSB.db.run(playersQuery.result)

      Await.result(playerssFuture, Duration.Inf).headOption match {
        case Some(value) => {
          val play = controller.players.players.filter(_.tokens.head.getPlayer().playerId == id).head
          play.setDiced(value.diced)
          play.setName(value.name)
          play.setFinished(value.finished)
          val tokensQuery = tokens.filter(_.playerId === id)
          val tokensFuture = SlickSB.db.run(tokensQuery.result)
          val toke = Await.result(tokensFuture, Duration.Inf)
          controller.players.players.map(_.tokens.map(t => toke.map(nt =>
            if(nt.id == t.tokenId && nt.counter != 0)
              (t.setPosition((controller.playingField.playingField(nt.positionId), nt.positionId)),t.setCounter(nt.counter))
            else if(nt.id == t.tokenId)(t.setPosition((play.house.house(nt.positionId), nt.positionId)),t.setCounter(nt.counter)))))

          Success(play)
        }
        case None => Failure(new Exception("ID:" + id + " doesn't exist"))
      }
    }

    override def delete(id: Long): Unit = {
      val playerQuery = players.filter(_.id === id)
      val tokensQuery = tokens.filter(_.playerId === id)
      val tokensFuture = SlickSB.db.run(tokensQuery.delete)
      val playersFuture = SlickSB.db.run(playerQuery.delete)
      val tokenVector = Await.result(tokensFuture, Duration.Inf)
      val playerVector = Await.result(playersFuture, Duration.Inf)
      //Await.result(db.run(players.filter(_.playerId == id.toInt).delete), Duration.Inf)
    }

    def iterIds: Iterable[Long] = Await.result(db.run(players.map(_.id).result), Duration.Inf)

  }


}

