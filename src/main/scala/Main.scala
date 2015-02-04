import helper.ServerHelper
import model.Player

/**
 * Created by Basti on 04.02.15.
 */
object Main extends App {

  ServerHelper.registerWithServer(new Player(1))

}
