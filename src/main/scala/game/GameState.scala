package game

import scala.util.Random
import Player._

object GameState {
	
  var begin = true
  var firstMove: Player = null
  var count = 0
  var desk: Array[Field] = initDesk
  var player1 = Player(1, initChips())
  var player2 = Player(2, initChips())
  var whoseMove = player1
  var thrown = false

  def switchMove(): Unit = {
    
    if(whoseMove == player1) {
      whoseMove = player2
    } else {
      whoseMove = player1
    }
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
}
