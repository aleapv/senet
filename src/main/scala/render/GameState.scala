package render

import game._
import game.Player._
import scala.collection.mutable.Queue

object GameState {

  var gCount: Int = 0
  def setgCount(i:Int)= gCount=i

  var gBegin: Boolean = true
  def setgBegin(i:Boolean)= gBegin=i

  var gThrown: Boolean = false
  def setgThrown(i:Boolean)= gThrown=i

  var gPlayer1: Player = Player(1, initChips())
  var gPlayer2: Player = Player(2, initChips())

  def initChips(): Array[Chip] = {
    
    val c = new Array[Chip](10)

    for(l <- 0 to 9) {
      c(l) = Chip(l + 1, false, 30)
    }
    c
  }

  def updateChip(player:Player,chip:Chip)={
    player match {
      case gPlayer1 => gPlayer1.chips.update(chip.num-1, chip)
      case gPlayer2 => gPlayer2.chips.update(chip.num-1, chip)
    }
  }

  var gWhoseMove: Player = gPlayer1
  def setgWhoseMove(i:Player)= gWhoseMove=i

  var gMessages = Queue(
    "Сейчас ходит игрок - " + gWhoseMove.num,
    "Здравствуйте, уважаемые игроки",
    "Определение первого хода")

  def addMessage(s:String):Unit = {

    gMessages.dequeue()
    gMessages += s
  }

  var gDesk: Array[Field] = initDesk

  def initDesk: Array[Field] = {

    val d = new Array[Field](30)

    for(i <- 0 to 29) {
      d(i) = Field(i, fieldText(i + 1), null, null)
    }
    d
  }

  def updategDesk(pos:Int,chip:Chip,player:Player)={
    gDesk.update(pos,
      Field(pos + 1, fieldText(pos + 1), player, chip))
  }

  def setgDesk(chip:Chip,player:Player)={
    updategDesk(chip.pos,chip,player)
  }

  def nullgDesk(pos:Int)={
    updategDesk(pos,null,null)
  }

  def fieldText(index: Int): String = {
    index match {
      case 15 => "φ"
      case 26 => "V"
      case 27 => "≡"
      case 28 => "%"
      case 29 => "Θ"
      case _ => index.toString
    }
  }
}
