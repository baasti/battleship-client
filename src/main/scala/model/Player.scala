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
    ConsoleHelper.printShip(s)

    val coords = ConsoleHelper.getShipCoordinatesFromConsole()

    val result = ServerHelper.setShip(s.name, coords._2, coords._1, coords._3, id)

    result match {
      case None => placeShip(s)
      case Some(s: String) => s
    }
  }

  def shoot(): (Player, ShootResponse) = {
    val coords = ConsoleHelper.getCoordinatesFromConsole()
    val xCoord = coords._2
    val yCoord = CharacterCoordinate(coords._1)

    if(!shots.isUnshot(xCoord.toInt, yCoord.toInt)) {
      println("Da hast du schonmal hingeschossen..")
      shoot()
    } else {
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

}
