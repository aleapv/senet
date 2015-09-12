package game

import GameState._

case class Player(num: Int, chips: Array[Chip])

object Player {

  def initChips(): Array[Chip] = {
    
    val c = new Array[Chip](10)

    for(l <- 0 to 9) {
      c(l) = Chip(l, false, 30)
    }
    c
  }
}
