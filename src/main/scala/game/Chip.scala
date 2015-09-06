package game

import GameState._

case class Chip(num: Int, taken: Boolean, pos: Int)

object Chip {


  def newChip(player: Player): Unit = {

    if(player == whoseMove) {
      if(!begin && thrown) {
        val f = desk(29 - count)
        desk(29 - count + 1) = Field(f.num,f.text,player,getNewChip(player))

        switchMove()
        thrown = false
      }
    }
  }


  def getNewChip(player: Player): Chip = {

    var i = 0
    while(player.chips(i).taken == true){
      i += 1
    }
    player.chips(i) = Chip(i, true, count)
    player.chips(i)
  }


  def move(c: Chip): Unit = {

    if(!begin && thrown) {

      val f = desk(29 - c.pos)
      desk(29 - c.pos) =
        Field(f.num,f.text,whoseMove,
          Chip(c.num,c.taken,c.pos + count))

      switchMove()
      thrown = false
    }
  }
}
