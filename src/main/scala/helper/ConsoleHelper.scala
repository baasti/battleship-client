package helper

import model.HitType
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

  def printArray[T](s: String) (implicit printTile: T => String): Unit = {
    val topValues = List(" ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    val sideValues = List('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')

    implicit def printTile(s: String): String = {
       s
      }

    def myZip(left: List[Char], right: List[List[Char]]): List[List[Char]] = {
      left match {
        case Nil => Nil
        case x::xs =>  (left.head::right.head) :: myZip(left.tail, right.tail)
      }
    }

    val stringAsArray = s.toCharArray()
    println(s.toCharArray().length / 10)


    val asVector = (for(i <- 0 until stringAsArray.length / 10)
                    yield for(j <- i*10 until (i*10 + 10))
                      yield stringAsArray(j)).map(item => item.toList).toList


    val readyToPrint = topValues::myZip(sideValues, asVector)

    readyToPrint.foreach(item => {
      item.foreach(innerItem => print(innerItem + " "))
      println()
    })
  }

  def printArray(array: IndexedSeq[IndexedSeq[HitType]]): Unit = {
    def printTile(s: HitType): String = {
      s match {
        case HitType.None => "."
        case HitType.Hit => "X"
        case HitType.HitAndSunk => "S"
        case HitType.Miss => "O"
      }
    }

    array.foreach(item => {
      item.foreach(innerItem => print(printTile(innerItem)))
      println()
    })
  }

}
