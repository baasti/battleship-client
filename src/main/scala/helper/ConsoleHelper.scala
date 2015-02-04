package helper

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
      Integer.parseInt(splittedInput(1))
    )
  }


  def getInputFromConsole(prompt: String): String = {
    print(s"$prompt ")
    readLine().trim match {
      case ""  => getInputFromConsole(prompt)
      case x:String => x
    }
  }

  def printArray[T](a: Array[Array[T]], printTile: T => String): Unit = {
    val yAxis = Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
    var i = 0
    println("  0 1 2 3 4 5 6 7 8 9")
    a.foreach(x => {
      print(yAxis(i) + " ")
      i += 1
      x.foreach(y => { print(printTile(y) + " ")})
      println("")
    })
    println("")
  }

}
