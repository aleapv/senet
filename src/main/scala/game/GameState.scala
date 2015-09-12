package game

import scala.util.Random
import Player._
import scala.collection.mutable.Queue

object GameState {
	
  var begin = true
  var firstMove: Player = null
  var count = 0
  var desk: Array[Field] = initDesk
  var player1 = Player(1, initChips())
  var player2 = Player(2, initChips())
  var whoseMove = player1
  var thrown = false
  var messages = Queue(
    "Сейчас ходит игрок - " + whoseMove.num,
    "Здравствуйте, уважаемые игроки",
    "Определение первого хода")

  def switchMove(): Unit = {
    
    if(whoseMove == player1) {
      whoseMove = player2
    } else {
      whoseMove = player1
    }
    addMessage(messages, "Сейчас ходит игрок - " + whoseMove.num)
  }


  def throwSticks(player: Player): Unit = {

    if(player == whoseMove && !thrown) {
      if(begin) {
        defineFirstMove()
      } else {
        throwSticks()
      }
    }
  }


  def throwSticks(): Unit = {
    count = Random.nextInt(5)
    thrown = true
    addMessage(messages, "Счет броска - " + count)
    if(count == 0) {
      switchMove()
      thrown = false
    }
  }


  def isFirstMove(count: Int): Boolean = {
    if(count == 1) {
      return true
    } else {
      return false
    }
  }


  def defineFirstMove(): Unit = {
    
    throwSticks()
    
    if(isFirstMove(count)) {
      firstMove = whoseMove
      begin = false
      addMessage(messages, "Первый ход у " + firstMove.num + " игрока")
    } else {
      switchMove()
    }
    thrown = false
  }


  def initDesk: Array[Field] = {

    val d = new Array[Field](30)

    for(i <- 1 to 30) {
      d(i - 1) = Field(i, i.toString, null, null)
    }

    d(14) = Field(15, "φ", null, null)
    d(25) = Field(26, "V", null, null)
    d(26) = Field(27, "≡", null, null)
    d(27) = Field(28, "%", null, null)
    d(28) = Field(29, "Θ", null, null)
    d
  }

  def addMessage(m:Queue[String], s:String):Unit = {

    m.dequeue()
    m += s
  }
}
