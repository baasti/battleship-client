package model


class Ship(val name: String, val form: IndexedSeq[IndexedSeq[Boolean]])

object Ship {

  def parseShiplist(l: List[Map[String, Any]], s: List[ShipListItem]): List[ShipListItem] = {
    l match {
      case Nil
        => s
      case x::xs
        => parseShiplist(xs,
            ShipListItem(
              new Ship(
                x.get("name").get.asInstanceOf[String],
                coordinateListToArray(x.get("form").get.asInstanceOf[List[List[Double]]])
              ),
              x.get("number").get.asInstanceOf[Double].toInt
            )::s)
    }
  }

  def coordinateListToArray(l: List[List[Double]]): IndexedSeq[IndexedSeq[Boolean]] = {
    println(l)
    val xMax = l.map(l => l.head.toInt).reduceLeft((x, y) => if (x > y) x else y)
    val yMax = l.map(l => l.tail.head.toInt).reduceLeft((x, y) => if (x > y) x else y)

    for (i <- 0 to yMax)
      yield for (j <- 0 to xMax)
        yield if (l.count(item => item.head.toInt == j && item.tail.head.toInt == i) != 0) { true } else { false }
  }
}
