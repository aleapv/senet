package game

import GameState._

class Chip(num: Int, taken: Boolean, pos: Int)

object Chip {


  def newChip(player: Player): Unit = {

    if(player == whoseMove) {
      if(!begin && thrown) {
        val f = desk(29 - count + 1)
        if(f.chip == null) {
          desk(29 - count + 1) = Field(f.num,f.text,player,getNewChip(player))
        } else {
          addMessage(messages, "Эта клетка уже занята!")
        }
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
    player.chips(i) = Chip(i, true, player.chips(i).pos - count)
    player.chips(i)
  }


  def move(c: Chip): Unit = {

    if(!begin && thrown) {
      val f0 = desk(c.pos)
      val f = desk(c.pos - count)
      if(f.chip == null) {
        desk(c.pos - count) =
          Field(f.num,f.text,whoseMove,
            Chip(c.num,c.taken,c.pos + count))
        desk(0).chip.num=2
        desk(c.pos) = Field(f0.num,f0.text,null,null)
      } else {
        addMessage(messages, "Эта клетка уже занята!")
      }


      switchMove()
      thrown = false
    }
  }
}
