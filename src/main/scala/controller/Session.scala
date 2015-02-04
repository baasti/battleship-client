package controller

import helper.ServerHelper
import model.{Ship, ShipListItem, Player}

/**
 * Created by Basti on 04.02.15.
 */
class Session {

  def registerWithServer(): Unit = {
    val id = scala.util.Random.nextInt(1000)

    val shipsOption = ServerHelper.registerWithServer(id)

    shipsOption match {
      case None => println("stirb")
      case Some(ships) => placeShips(new Player(id, ships))
    }
  }

  def placeShips(p: Player): Unit = {
    val board = p.placeShip(p.ships.head.ship)

    println(board)

    p.ships match {
      case ShipListItem(_, 1) :: Nil => shoot()
      case ShipListItem(s: Ship, i: Int) :: Nil => placeShips(new Player(p.id, ShipListItem(s, i - 1) :: Nil))
      case ShipListItem(_, 1) :: xs  => placeShips(new Player(p.id, p.ships.tail))
      case ShipListItem(s: Ship, i: Int) :: xs => placeShips(new Player(p.id, ShipListItem(s, i - 1) :: xs))
      case _ => println("???")
    }
  }

  def shoot(): Unit = {
    println("LETS GO")
  }

}
