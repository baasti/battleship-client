package model

import helper.{ServerHelper, ConsoleHelper}

/**
 * Created by Basti on 04.02.15.
 */
class Player(
              val id: Int,
              val ships: List[ShipListItem],
              val shots: IndexedSeq[IndexedSeq[Option[Boolean]]] = for(i <- 0 to 9) yield for(j <- 0 to 9) yield None
              )
{

  def placeShip(s: Ship): String = {
    println("You're now placing a " + s.name)
    println("Form: " + s.form)

    val coords = ConsoleHelper.getShipCoordinatesFromConsole("Please enter Coordinates (Letter Number Direction): ")

    val result = ServerHelper.setShip(s.name, coords._2, coords._1, coords._3, id)

    result match {
      case None => placeShip(s)
      case Some(s: String) => s
    }
  }

}
