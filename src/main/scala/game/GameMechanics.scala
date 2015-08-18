package game

/**
 * Created by aleapv on 16.08.2015.
 */
import sticks.Sticks.Color._
import sticks.Sticks._

object GameMechanics {

  case class Game(player1: Player,
                  player2: Player)

  def isFirstMove(l: List[Stick]): Boolean = {
    val c = count(l)
    if(c == 1) true else false
  }
  def count(l: List[Stick]):Int = {
    val c = l.foldRight(0)((x,y)=>(if(x.c == Black) 1 else 0) + y)
    if(c == 0) 5 else c
  }
}
