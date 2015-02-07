package helper

import model.{Board, HitType}
import model.HitType.HitType
import model.HitType.HitType

import scala.io.StdIn._

/**
 *  Provides Functions for getting input from the user and writing
 *  output to the console
 */
object ConsoleHelper {

  def getShipCoordinatesFromConsole(s: String): (String, String, String) = {
    val userInput = getInputFromConsole(s)
    val splittedInput = userInput.split(" ")

    (
      splittedInput(0),
      splittedInput(1),
      splittedInput(2)
    )
  }

  def getCoordinatesFromConsole(s: String) = {
    var userInput = getInputFromConsole(s)
    val splittedInput = userInput.split(" ")

    (
      splittedInput(0),
      splittedInput(1)
    )
  }


  def getInputFromConsole(prompt: String): String = {
    print(s"$prompt ")
    readLine().trim match {
      case ""  => getInputFromConsole(prompt)
      case x:String => x
    }
  }


  def printArray(b: Board): Unit = {
    val topValues = Vector(" ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val sideValues = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")

    def getRepresentation(s: HitType): String = {
      s match {
        case HitType.None => "."
        case HitType.Hit => "X"
        case HitType.HitAndSunk => "X"
        case HitType.Miss => "O"
      }
    }

    def prependSideValues(left: List[String], right: IndexedSeq[IndexedSeq[String]]): List[IndexedSeq[String]] = {
      left match {
        case Nil => Nil
        case x::xs => (x +: right.head) :: prependSideValues(xs, right.tail)
      }
    }

    val stringRepresentation = for(i <- 0 until b.board.length)
                                  yield for(j <- 0 until b.board(0).length)
                                    yield getRepresentation(b.board(i)(j))

    val printString = topValues +: prependSideValues(sideValues, stringRepresentation)

    printString.foreach(item => {
      item.foreach(innerItem => print(innerItem + " "))
      println()
    })
  }

}
