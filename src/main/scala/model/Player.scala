package model

import helper.{ServerHelper, ConsoleHelper}
import model.HitType.HitType

/**
 * Created by Basti on 04.02.15.
 */
class Player(
              val id: Int,
              val ships: List[ShipListItem],
              val shots: IndexedSeq[IndexedSeq[HitType]] = for(i <- 0 to 9) yield for(j <- 0 to 9) yield HitType.None
              )
{

  def this(id: Int, shots: IndexedSeq[IndexedSeq[HitType]]) = {
    this(id, Nil, shots)
  }

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

  def shoot(): (Player, ShootResponse) = {
    val coords = ConsoleHelper.getCoordinatesFromConsole("Please enter Coordinates (Letter Number): ")
    println(coords._1 + " " + coords._2)
    val xCoord = coords._2
    val yCoord = CharacterCoordinate(coords._1)

    val shootResponse = ServerHelper.shoot(xCoord, yCoord, this)

    val newShots = for(i <- 0 until shots.length)
                    yield for(j <- 0 until shots(0).length)
                      yield if(yCoord.toInt == i && j == xCoord.toInt) shootResponse.hitType else shots(i)(j)

    (new Player(this.id, newShots), shootResponse)
  }

}
