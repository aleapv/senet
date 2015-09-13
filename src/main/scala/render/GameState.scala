package render

import game._
import game.Player._
import scala.collection.mutable.Queue

object GameState {

  var gCount: Int = 0
  var gBegin: Boolean = true
  var gThrown: Boolean = false
  var gPlayer1: Player = Player(1, initChips())
  var gPlayer2: Player = Player(2, initChips())
  var gWhoseMove: Player = gPlayer1
  var gMessages = Queue(
    "Сейчас ходит игрок - " + gWhoseMove.num,
    "Здравствуйте, уважаемые игроки",
    "Определение первого хода")
  var gDesk: Array[Field] = initDesk


  def addMessage(s:String):Unit = {

    gMessages.dequeue()
    gMessages += s
  }


  def initDesk: Array[Field] = {

    val d = new Array[Field](30)

    for(i <- 1 to 30) {
      d(i - 1) = Field(i, i.toString, null, null)
    }

    d(14) = d(14).copy(text = "φ")
    d(25) = d(25).copy(text = "V")
    d(26) = d(26).copy(text = "≡")
    d(27) = d(27).copy(text = "%")
    d(28) = d(28).copy(text = "Θ")
    d
  }
}
