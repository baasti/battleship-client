package model

import helper.{ServerHelper, ConsoleHelper}
import model.HitType.HitType

/**
 * Created by Basti on 04.02.15.
 */
class Player(
              val id: Int,
              val ships: List[ShipListItem],
              val shots: Board = Board.generateEmptyBoard()
              )
{

  def this(id: Int, b: Board) = {
    this(id, Nil, b)
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

    shots.setShot(xCoord.toInt, yCoord.toInt, shootResponse.hitType)

    (new Player(
        this.id,
        shots.setShot(xCoord.toInt, yCoord.toInt, shootResponse.hitType)
      ),
      shootResponse
    )
  }

}
