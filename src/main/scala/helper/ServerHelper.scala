package helper

import java.net.URL

import model.{ShipListItem, Ship, Player}
import uk.co.bigbeeconsultants.http.HttpClient
import uk.co.bigbeeconsultants.http.header.MediaType._
import uk.co.bigbeeconsultants.http.request.RequestBody

import scala.util.parsing.json.{JSON, JSONObject}


object ServerHelper {
  val httpClient = new HttpClient()

  val urls = Map(
    "register" -> new URL("http://localhost:9000/register"),
    "setship"  -> new URL("http://localhost:9000/place"),
    "poll"     -> new URL("http://localhost:9000/opponent"),
    "shoot"    -> new URL("http://localhost:9000/shoot")
  )

  def registerWithServer(p: Player): Option[List[ShipListItem]] = {
    val requestBody = RequestBody(new JSONObject(Map("userid" -> p.id)).toString(),
      APPLICATION_JSON)
    val response = httpClient.post(urls.get("register").get, Some(requestBody))

    if(!response.status.isSuccess) {
      None
    } else {
      val jsonShipList = JSON.parseFull(response.body.asString).get.asInstanceOf[Map[String, List[Map[String, Any]]]].get("ships").get
      println(Ship.parseShiplist(jsonShipList, Nil))
      None
    }

  }



}
