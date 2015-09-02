package game

import scala.util.Random

object GameState {
	
  var gWhoseMove = 1 // 1 - first, 2- second
  var gBegin = true // game begin
  var gFirstMove = 0 // whose first move
  var gCount = 0 // sticks count
  var gDesk: Array[(Int,String)] = initDesk
  var chips: Array[(Int,Int)] = initChips


  def switchMove(): Unit = {
    
    if(gWhoseMove == 1) {
      gWhoseMove = 2
    } else {
      gWhoseMove = 1
    }
  }


  def onclick(): Unit = {
    
    if(gBegin) {
      defineFirstMove
    }
  }


  def throwStick(): Unit = {
    gCount = Random.nextInt(5)
  }


  def isFirstMove(count: Int): Boolean = {
    if(count == 1) {
      return true
    } else {
      return false
    }
  }


  def defineFirstMove(): Unit = {
    
    throwStick
    
    if(isFirstMove(gCount)) {
      gFirstMove = gWhoseMove
      gBegin = false
    } else {
      switchMove
    }
  }


  def initDesk: Array[(Int,String)] = {

    val d = new Array[(Int,String)](30)

    for(i <- 1 to 30) {
      d(i - 1) = (i, i.toString)
    }

    d(14) = (15, "φ")
    d(25) = (26, "V")
    d(26) = (27, "≡")
    d(27) = (28, "%")
    d(28) = (29, "Θ")
    d
  }


  def initChips: Array[(Int,Int)] = {
    
    val c = new Array[(Int,Int)](20)

    for(l <- 1 to 20) {
      
      if(l <= 10) {
	c(l - 1) = (l, 1)
      } else {
	c(l - 1) = (l - 10, 2)
      }
    }
    c
  }
}
