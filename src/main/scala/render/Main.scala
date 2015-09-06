package render
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import game.GameState._
import game.Chip._
import scala.scalajs.js.Dynamic.global

@JSExport
object Main {

  val DESK_SQUARE = 50
  val DESK_STEP = 48
  val DESK_TEXT_SHIFT = 15
  val STICK_STEP = 15
  val STICK_WIDTH = 12
  val STICK_LENGTH = 30
  val CHIPS_SIZE = 18
  val CHIPS_STEP = 20

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    val renderer = canvas.getContext("2d")
                         .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 400

    renderer.font = "20px sans-serif"
    renderer.textAlign = "center"
    renderer.textBaseline = "middle"

    val canvasHTML = dom.document.getElementById("bdy").asInstanceOf[dom.raw.HTMLCanvasElement]
    
    def repaint() = {

      renderer.clearRect(0, 0, canvas.width, canvas.height)
      renderBegin(renderer, 130, 80)
      renderInfo(renderer, 120, 110, "Сейчас ходит игрок - " + whoseMove.num)    
      renderSticks(renderer, 270, 80)
      renderDesk(renderer, 80, 140)
      renderInfo(renderer, 450, 80, "Счет броска - " + count)
      renderInfo(renderer, 600, 80, if(thrown) "Бросок выполнен" else "")      
      renderChips(renderer, 110, 300)  
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


  def renderInfo(r: dom.CanvasRenderingContext2D, x: Int, y: Int, s: String): Unit = {
    
    r.fillStyle = "green"
    r.fillText(s, x, y)
  } 


  def renderBegin(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {

    if(begin) {
      renderInfo(r, x, y, "Определение первого хода")    
    } else {
      renderInfo(r, x, y, "Первый ход у " + firstMove.num + " игрока")
    }
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

        val x = x0 + i * DESK_STEP
        val y = y0 + j * DESK_STEP
        r.fillStyle = "yellow"
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
            renderChipBlue(r, x, y, 0)
          } else {
            renderChipRed(r, x, y, 0)
          }
        }
      }
    }
  }


  def renderChips(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {
    
    for(l <- 1 to 10) {
      renderChipBlue(r, x, y, l)
      renderChipRed(r, x, y, l)
    }
  }


  def renderChipBlue(r: dom.CanvasRenderingContext2D, x: Int, y: Int, num: Int): Unit = {
    
    r.fillStyle = "blue"
    r.fillRect(x + num * CHIPS_STEP, y, CHIPS_SIZE, CHIPS_SIZE)
  }


  def renderChipRed(r: dom.CanvasRenderingContext2D, x: Int, y: Int, num: Int): Unit = {
    
    r.fillStyle = "red"
    r.beginPath();
    r.arc((x + CHIPS_STEP/2 + num * CHIPS_STEP).toDouble, (y + CHIPS_STEP*2).toDouble, CHIPS_SIZE/2, 0.0, 360.0)
    r.fill();
  }
}
