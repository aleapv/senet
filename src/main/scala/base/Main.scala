package base
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import scala.util.Random

@JSExport
object Main {
  @JSExport
  def main(canvas: html.Canvas): Unit = {

    /*setup*/
    val renderer = canvas.getContext("2d")
                         .asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = 400

    renderer.font = "20px sans-serif"
    renderer.textAlign = "center"
    renderer.textBaseline = "middle"
    
    /*variables*/
    var whoseMove = 1 // 1 - first, 2- second
    var firstMove = 0
    var count = 0
    var desk = new Array[(Int,String)](30)
    var i = 0;
    for(i <- 1 to 30) {
      desk(i-1) = (i,i.toString)
    }
    desk(14) = (15,"φ") 
    desk(25) = (26,"V") 
    desk(26) = (27,"≡")
    desk(27) = (28,"%")
    desk(28) = (29,"Θ")

    def run() = {
      renderer.clearRect(0, 0, canvas.width, canvas.height)

      // render whoseMove
      renderer.fillStyle = "green"
      renderer.fillText("whoseMove - " + whoseMove, 100, 50)

      renderer.fillText(" count - " + count, 100, 30)

      if(firstMove != 0) renderer.fillText("firstMove φ- " + firstMove, 100, 80)

      List(1,2,3,4).foldLeft(count)((st: Int, x: Int) => {
          if(st > 0 && st < 5) {
            renderer.fillStyle = "black"
          } else {
            renderer.fillStyle = "yellow"
          }
          renderer.fillRect(100 + x*10, 100, 4, 20)
          st - 1
        }
      )

      // render desk
      for(j <- 0 to 2) {
        for(i <- 1 to 10) {
          val s = 50
          val x = 90 + i*s
          val y = 130 + j*s
          renderer.fillStyle = "yellow"
          renderer.fillRect(x, y, s - 2, s - 2)
          renderer.fillStyle = "blue"
          if(j == 1) {
            renderer.fillText(desk(30 - i - j*10)._2, x + 15, y + 15)
          } else {
            renderer.fillText(desk(i - 1 + j*10)._2, x + 15, y + 15)          
          }
        }
      }
    }

    dom.setInterval(run _, 20)

    canvas.onclick = (e: dom.MouseEvent) => {
      if(throwStick == 1) {
        firstMove = whoseMove
      }

      if(whoseMove == 1) {
        whoseMove = 2
      } else {
        whoseMove = 1
      }
    }

    def throwStick(): Int = {
      count = Random.nextInt(5)
      count
    }
  }
}
