package controller

import helper.{ConsoleHelper, ServerHelper}
import model._

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

    ConsoleHelper.printArray(board)

    p.ships match {
      case ShipListItem(_, 1) :: Nil => waitForOpponent(new Player(p.id, Nil))
      case ShipListItem(s: Ship, i: Int) :: Nil => placeShips(new Player(p.id, ShipListItem(s, i - 1) :: Nil))
      case ShipListItem(_, 1) :: xs  => placeShips(new Player(p.id, p.ships.tail))
      case ShipListItem(s: Ship, i: Int) :: xs => placeShips(new Player(p.id, ShipListItem(s, i - 1) :: xs))
      case _ => println("???")
    }
  }

  def waitForOpponent(p: Player): Unit = {

    ServerHelper.waitForOpponent(p) match {
      case PollResponse(map: String, Some(winner: Int), actions: List[PlayerAction]) => {
        println("Du hast leider verloren. Deine Flotte wurde ausgelöscht.")
        println("Gegnernische Aktionen")
        actions.foreach(a => {
          println(
            a.hitType + " auf " + a.coords._1 + ", " + a.coords._2 + "!" +
              (if(a.destroyed != None) { " " + a.destroyed.get + " zerstört!" } else {""})
          )
        })
        println("Deine Karte")
        ConsoleHelper.printArray(map)
      }
      case PollResponse(map: String, _, Nil) => {
        println("Du bist dran!")
        println("Dein Spielfeld: ")
        ConsoleHelper.printArray(map)
        println("Bisherige Schüsse")
        ConsoleHelper.printArray(p.shots)
        shoot(p)
      }
      case PollResponse(map: String, _, actions: List[PlayerAction]) => {
        println("Du bist dran!")
        println("Gegnernische Aktionen")
        actions.foreach(a => {
          println(
            a.hitType + " auf " + a.coords._1 + ", " + a.coords._2 + "!" +
              (if(a.destroyed != None) { " " + a.destroyed.get + " zerstört!" } else {""})
          )
        })
        println("Dein Spielfeld: ")
        ConsoleHelper.printArray(map)
        println("Bisherige Schüsse")
        ConsoleHelper.printArray(p.shots)
        shoot(p)
      }
    }
  }

  def shoot(player: Player): Unit = {

    player.shoot() match {
      case (p: Player, ShootResponse(HitType.Hit, _, _)) => {
        println("Treffer, du bist nochmal dran!")
        ConsoleHelper.printArray(p.shots)
        shoot(p)
      }
      case (p: Player, ShootResponse(_, Some(winner: Int), _)) => {
        println("Treffer und versenkt. Du hast die Flotte deines Gegner ausgelöscht!")
        ConsoleHelper.printArray(p.shots)
      }
      case (p: Player, ShootResponse(HitType.HitAndSunk, _, destroyed: Option[String])) => {
        println("Super, du hast den " + destroyed + " des Gegners zerstört!")
        println("Du bist nochmal dran!")
        ConsoleHelper.printArray(p.shots)
        shoot(p)
      }
      case (p: Player, ShootResponse(HitType.Miss, _, _)) => {
        println("Daneben, der Gegner ist dran..")
        waitForOpponent(p)
      }
    }
  }

}
