package de.htwg.se.menschaergerdichnicht.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Inject
import de.htwg.se.menschaergerdichnicht.model.fileIoComponent.FileIoInterface
import de.htwg.se.menschaergerdichnicht.model.fieldComponent._
import play.api.libs.json._

import scala.io.Source

/**
 * Created by Anastasia on 26.06.17.
 */
abstract class FileIO @Inject() extends FileIoInterface {

  //  override def save(plfield: PlayingInterface): Unit = {
  //    import java.io._
  //    val pw = new PrintWriter(new File("player.json"))
  //    pw.write(Json.prettyPrint(plfieldToJson(plfield)))
  //    pw.close()
  //  }
  //
  //  override def load: Option[PlayingInterface] = {
  //    var plfieldOption: Option[PlayingInterface] = None
  //    val source: String = Source.fromFile("player.json").getLines().mkString
  //    val json: JsValue = Json.parse(source)
  //    plfieldOption match {
  //      case Some(plfield) => {
  //        val _plfield = plfield
  //        val field = (json \ "field").as[Int]
  //        plfieldOption = Some(_plfield)
  //      }
  //      case None =>
  //    }
  //    plfieldOption
  //  }
  //
  //  def plfieldToJson(plfield: PlayingInterface) = {
  //  }

}
