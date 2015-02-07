package model

/**
 * Created by Basti on 07.02.15.
 */
case class PollResponse(map: String, winner: Option[Int], actions: List[PlayerAction])

object PollResponse {
  def parseFromJson(j: Map[String, Any]): PollResponse = {
    println(j)
    new PollResponse(
      j.get("map").get.asInstanceOf[String],
      if(j.get("won").get.asInstanceOf[String] == "None") None else Some(j.get("won").get.asInstanceOf[String].toInt),
      PlayerAction.parseFromJSON(j.get("actions").get.asInstanceOf[List[Map[String, Any]]])
    )
  }
}
