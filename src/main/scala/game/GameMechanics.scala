package game

/**
 * Created by aleapv on 16.08.2015.
 */
import sticks.Sticks.Color._
import sticks.Sticks._

object GameMechanics {

  def isFirstMove(l: List[Stick]): Boolean = {
    val c = count(l)
    if(c == 1) true else false
  }
  def count(l: List[Stick]):Int = {
    val c = l.foldRight(0)((x,y)=>(if(x.c == Black) 1 else 0) + y)
    if(c == 0) 5 else c
  }

  // TODO add pure functional state for Game
  // TODO create interface using scalajs
  // TODO Game must save result to DB
  // TODO delete side effects using FreeMonad and MyLogger
  var start: Boolean = false
  var move: Integer = 1
  var sticksThrowed: Boolean = false
  def main(args: Array[String]) {
    import sticks.Sticks._
    while(true){
      Thread.sleep(500)
      if(!start) {
        println("Define whose first move")
        if(move == 1) {
          println("First player throws sticks")
          if(sticksThrowed) {
            if(isFirstMove(sticks)) {
              println("First player first move")
              start = true
            }
            sticksThrowed = false
          }
        }
        else {
          println("Second player throws sticks")
          if(sticksThrowed) {
            if(isFirstMove(sticks)) {
              println("Second player first move")
              start = true
            }
            sticksThrowed = false
          }
        }

      }

    }
  }
}
