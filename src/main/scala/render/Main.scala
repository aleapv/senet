package render

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import GameState._
import game.GameState._
import game._
import scala.scalajs.js.Dynamic.global
import scala.collection.mutable.Queue
import scalaz.Zip
import scalaz.std.list._
import Chip._
import Render._

@JSExport
object Main {

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    val renderer = canvas.getContext("2d")
                         .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 800

    renderer.textAlign = "center"
    renderer.textBaseline = "middle"

    val canvasHTML = dom.document.getElementById("bdy").asInstanceOf[dom.raw.HTMLCanvasElement]
    
    def repaint() = {

      renderer.clearRect(0, 0, canvas.width, canvas.height)
      renderMessages(renderer, 180, 40, gMessages)
      renderSticks(renderer, 370, 40)
      renderDesk(renderer, 50, 140)
      renderChips(renderer, 50, 450)
      renderRules(renderer, 1000, 40)
    }

    dom.setInterval(repaint _, 20)

    canvasHTML.onkeydown = (e: dom.KeyboardEvent) => {

      e.keyCode.toInt match {
        case 65 => throwSticks(gPlayer1)
        case 83 => newChip(gPlayer1)
        case 74 => throwSticks(gPlayer2)
        case 75 => newChip(gPlayer2)
        case 49 => moveChip(gWhoseMove.chips(0))
        case 50 => moveChip(gWhoseMove.chips(1))
        case 51 => moveChip(gWhoseMove.chips(2))
        case 52 => moveChip(gWhoseMove.chips(3))
        case 53 => moveChip(gWhoseMove.chips(4))
        case 54 => moveChip(gWhoseMove.chips(5))
        case 55 => moveChip(gWhoseMove.chips(6))
        case 56 => moveChip(gWhoseMove.chips(7))
        case 57 => moveChip(gWhoseMove.chips(8))
        case 48 => moveChip(gWhoseMove.chips(9))
      }
    }
  }


  def throwSticks(player: Player):Unit = {
    // whoseMove, begin, thrown, count, message
    val t = throwStick(player, gWhoseMove, gThrown, gBegin, gPlayer1, gPlayer2)
    t._1.map(setgWhoseMove(_))
    t._2.map(setgBegin(_))
    t._3.map(setgThrown(_))
    t._4.map(setgCount(_))
    addMessage(t._5)
  }


  def newChip(player: Player):Unit = {
    // Chip, whoseMove, thrown, messagee

    val t = newChi(player, gWhoseMove, gThrown, gBegin, gCount, gDesk, gPlayer1, gPlayer2)

    t._1.map(chip => {
      val moved = movedChip(chip, gCount)
      updateChip(player, moved)
      setgDesk(moved, player)
    })
    t._2.map(setgWhoseMove(_))
    t._3.map(setgThrown(_))
    addMessage(t._4)
    t._5.map(chip => {
      updateChip(switchMove(player, gPlayer1, gPlayer2), chip)
    })
  }


  def moveChip(chip: Chip):Unit = {
    // Chip, whoseMove, thrown, message

    val t = moveChi(chip,gWhoseMove,gThrown,gBegin,gCount,gDesk,gPlayer1, gPlayer2)
    t._1.map(chip=>{
      val moved = movedChip(chip, gCount)
      updateChip(gWhoseMove, moved)
      setgDesk(moved,gWhoseMove)
      nullgDesk(chip.pos)
    })
    t._2.map(setgWhoseMove(_))
    t._3.map(setgThrown(_))
    addMessage(t._4)
    t._5.map(chip => {
      updateChip(gWhoseMove, chip)
    })
  }  
}
