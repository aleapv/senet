package render
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import game.GameState._
import game.Chip._
import scala.scalajs.js.Dynamic.global
import scala.collection.mutable.Queue

@JSExport
object Main {

  val DESK_SQUARE = 90
  val DESK_STEP = 88
  val DESK_TEXT_SHIFT = 15
  val STICK_STEP = 40
  val STICK_WIDTH = 20
  val STICK_LENGTH = 80
  val CHIPS_SIZE = 38
  val CHIPS_STEP = 40
  val TEXT_SIZE = 20

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
      renderText(renderer, 180, 40, messages)
      renderSticks(renderer, 370, 40)
      renderDesk(renderer, 50, 140)
      renderChips(renderer, 50, 450)
    }

    dom.setInterval(repaint _, 20)

    canvasHTML.onkeydown = (e: dom.KeyboardEvent) => {

      e.keyCode.toInt match {
        case 65 => throwSticks(player1)
        case 83 => newChip(player1)
        case 74 => throwSticks(player2)
        case 75 => newChip(player2)
        case 49 => move(whoseMove.chips(0))
        case 50 => move(whoseMove.chips(1))
        case 51 => move(whoseMove.chips(2))
        case 52 => move(whoseMove.chips(3))
        case 53 => move(whoseMove.chips(4))
        case 54 => move(whoseMove.chips(5))
        case 55 => move(whoseMove.chips(6))
        case 56 => move(whoseMove.chips(7))
        case 57 => move(whoseMove.chips(8))
        case 48 => move(whoseMove.chips(9))
      }
    }
  }


  def renderText(r: dom.CanvasRenderingContext2D, x: Int, y: Int, q: Queue[String]): Unit = {
    
    r.fillStyle = "darkblue"
    q.foldLeft((y,TEXT_SIZE / 2))((h,text)=>{
      r.font = h._2.toString() + "px sans-serif"
      r.fillText(text, x, h._1)
      (h._1 + TEXT_SIZE, h._2 + (TEXT_SIZE*0.25).toInt)
    })
  }


  def renderSticks(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {

    List(1,2,3,4).foldLeft(count)((st: Int, n: Int) => {

      if(st > 0 && st < 5) {
        r.fillStyle = "black"
      } else {
        r.fillStyle = "yellow"
      }
      r.fillRect(x + n * STICK_STEP, y, STICK_WIDTH, STICK_LENGTH)
      st - 1
    })
  }


  def renderDesk(r: dom.CanvasRenderingContext2D, x0: Int, y0: Int): Unit = {
    
    for(j <- 0 to 2) {
     
      for(i <- 1 to 10) {

        val x = x0 + (i-1) * DESK_STEP
        val y = y0 + j * DESK_STEP
        r.fillStyle = "lightgreen"
        r.fillRect(x, y, DESK_SQUARE, DESK_SQUARE)
        r.fillStyle = "blue"
        val pos =
          if(j != 1) {
            i - 1 + j * 10
          } else {
            30 - i - j * 10
          }
        r.fillText(desk(pos).text, x + DESK_TEXT_SHIFT, y + DESK_TEXT_SHIFT)

        val chip = desk(pos).chip
        val player = desk(pos).player
        if(chip != null) {
          if(player == player1) {
            renderChipBlue(r, x, y + CHIPS_STEP, 0)
          } else {
            renderChipRed(r, x, y + (CHIPS_STEP*1.5).toInt, 0)
          }
        }
      }
    }
  }


  def renderChips(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {
    
    for(l <- 0 to 9) {
      if(!player1.chips(l).taken) renderChipBlue(r, x, y, l)
      if(!player2.chips(l).taken) renderChipRed(r, x, y + CHIPS_STEP*2, l)
    }
  }


  def renderChipBlue(r: dom.CanvasRenderingContext2D, x: Int, y: Int, num: Int): Unit = {
    
    r.fillStyle = "gray"
    r.fillRect(x + num * CHIPS_STEP, y, CHIPS_SIZE, CHIPS_SIZE)
    r.fillStyle = "darkblue"
    r.font = TEXT_SIZE.toString() + "px sans-serif"
    r.fillText((num + 1).toString(), x + CHIPS_STEP/2 + num * CHIPS_STEP, y + CHIPS_STEP/2)
  }


  def renderChipRed(r: dom.CanvasRenderingContext2D, x: Int, y: Int, num: Int): Unit = {
    
    r.fillStyle = "pink"
    r.beginPath();
    r.arc((x + CHIPS_STEP/2 + num * CHIPS_STEP).toDouble, (y).toDouble, CHIPS_SIZE/2, 0.0, 360.0)
    r.fill();
    r.fillStyle = "darkblue"
    r.font = TEXT_SIZE.toString() + "px sans-serif"
    r.fillText((num + 1).toString(), x + CHIPS_STEP/2 + num * CHIPS_STEP, y)
  }
}
