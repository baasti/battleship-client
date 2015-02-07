package model

import model.HitType.HitType

/**
 * Created by Basti on 07.02.15.
 */
case class PlayerAction(coords: (Int, Int), hitType: HitType, destroyed: Option[String])

object PlayerAction {
  def parseFromJSON(j: List[Map[String, Any]]): List[PlayerAction] = {
    println(j)
    j match {
      case Nil => Nil
      case x::xs => {
        val coords = x.get("coordinates").get.asInstanceOf[List[Double]]
        PlayerAction(
          (coords.head.toInt, coords.tail.head.toInt),
          HitType.withName(x.get("type").get.asInstanceOf[String]),
          if(x.contains("shiptype")) Some(x.get("shiptype").get.asInstanceOf[String]) else None
        ) :: parseFromJSON(xs)

      }
    }
  }
}