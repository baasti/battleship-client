package model

/**
 * Created by Basti on 07.02.15.
 */
case class PollResponse(board: Board, winner: Option[Int], actions: List[PlayerAction])

object PollResponse {
  def parseFromJson(j: Map[String, Any]): PollResponse = {
    new PollResponse(
      Board.parseFromString(j.get("map").get.asInstanceOf[String]),
      if(j.get("won").get.toString == "None") None else Some(j.get("won").get.asInstanceOf[Double].toInt),
      if(j.contains("actions"))
        PlayerAction.parseFromJSON(j.get("actions").get.asInstanceOf[List[Map[String, Any]]])
      else
        Nil
    )
  }
}
