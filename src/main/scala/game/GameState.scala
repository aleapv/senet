package game

import scala.util.Random
import scala.collection.mutable.Queue

object GameState {


  def switchMove(
    whoseMove: Player,
    player1: Player,
    player2: Player): Player = {
    
    if(whoseMove == player1) {
      player2
    } else {
      player1
    }
  }


  def throwStick(
    player: Player,
    whoseMove: Player,
    thrown: Boolean,
    begin: Boolean,
    player1: Player,
    player2: Player):
      (
        Player,
        Boolean,
        Boolean,
        Int,
        String) = { // whoseMove, begin, thrown, count, message

    val count = Random.nextInt(5)
    val switchedMove = switchMove(whoseMove, player1, player2)

    if((player.num != whoseMove.num) || thrown) {
      (whoseMove, begin, thrown, 0, "Недопустимый ход")
    }

    else if(begin) {
      if(isFirstMove(count)) {
        (player, false, false, count, "Первый ход у " + player.num + " игрока")
      } else {
        (switchedMove, true, false, count, "Сейчас ходит игрок - " + switchedMove.num)
      }
    }

    else {
      (player, false, true, count, "Игрок " + player.num +  " выбросил " + count)
    }
  }


  def isFirstMove(count: Int): Boolean = {
    if(count == 1) {
      return true
    } else {
      return false
    }
  }


  def newChi(
    player: Player,
    whoseMove: Player,
    thrown: Boolean,
    begin: Boolean,
    count: Int,
    desk: Array[Field],
    player1: Player,
    player2: Player
  ): (Chip, Player, Boolean, String) = {

    // Chip, whoseMove, thrown, message

    val field = desk(29 - count + 1)
    val chip = getNewChip(player, count)
    val switchedMove = switchMove(whoseMove, player1, player2)

    if(player != whoseMove || begin || !thrown) {
      (null,null,thrown, "Невозможно поставить новую фишку")
    } 

    else if(field.chip != null) {
      (null, switchedMove, false, "Эта клетка уже занята!")
    }

    else {
      (chip, switchedMove, false, "Новая фишка поставлена")
    }
  }


  def getNewChip(player: Player, count: Int): Chip = {

    var i = 0
    while(player.chips(i).taken == true){
      i += 1
    }
    player.chips(i)
  }


  def moveChi(
    chip: Chip,
    whoseMove: Player,
    thrown: Boolean,
    begin: Boolean,
    count: Int,
    desk: Array[Field],
    player1: Player,
    player2: Player
  ): (Chip, Player, Boolean, String) = {

    // Chip, whoseMove, thrown, message

    val field = desk(chip.pos - count)
    val switchedMove = switchMove(whoseMove, player1, player2)

    if(field.chip != null) {
      (null, switchedMove, false, "Эта клетка уже занята!")
    }

    else {
      (chip, switchedMove, false, "Фишка перемещена")
    }
  }
}
