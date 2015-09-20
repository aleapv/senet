package render

import org.scalajs.dom
import scala.collection.mutable.Queue
import Main._
import GameState._

trait Render[A] {

  def xcr(step:Int,num:Int,x:Int):Int
  def ycr(step:Int,y:Int):Int
  def xcrt(step:Int,num:Int,x:Int):Int
  def ycrt(step:Int,y:Int):Int
}

object Render {

  val DESK_SQUARE = 90
  val DESK_STEP = 88
  val DESK_TEXT_SHIFT = 15
  val STICK_STEP = 40
  val STICK_WIDTH = 20
  val STICK_LENGTH = 80
  val CHIPS_SIZE = 38
  val CHIPS_STEP = 40
  val TEXT_SIZE = 20

  def xcr[A](d:Draw[A])(implicit s:Render[A]):Int=s.xcr(d.step,d.num,d.x)

  def ycr[A](d:Draw[A])(implicit s:Render[A]):Int=s.ycr(d.step,d.y)

  def xcrt[A](d:Draw[A])(implicit s:Render[A]):Int=s.xcrt(d.step,d.num,d.x)

  def ycrt[A](d:Draw[A])(implicit s:Render[A]):Int=s.ycrt(d.step,d.y)

  implicit val chipBlueStep = new Render[ChipBlue]{

    def xcr(step: Int,num:Int,x:Int):Int = x + num * step

    def ycr(step: Int,y:Int):Int = y

    def xcrt(step: Int,num:Int,x:Int):Int = x + step/2 + num * step

    def ycrt(step: Int,y:Int):Int = y + step/2
  }

  implicit val chipRedStep = new Render[ChipRed]{

    def xcr(step: Int,num:Int,x:Int):Int = x + step/2 + num*step

    def ycr(step: Int,y:Int):Int = y

    def xcrt(step: Int,num:Int,x:Int):Int = x + step/2 + num * step

    def ycrt(step: Int,y:Int):Int = y
  }

  def fillText(r: dom.CanvasRenderingContext2D, x: Int, y: Int, text: String, color: String)
      : Unit = {
    r.fillStyle = color
    r.fillText(text, x, y)
  }

  def fillRect(r: dom.CanvasRenderingContext2D,
    x: Int, y: Int, w: Int, h: Int, color: String)
      : Unit = {
    r.fillStyle = color
    r.fillRect(x, y, w, h)
  }

  def colorStick(count: Int, num: Int): String = {
    if(num <= count && count != 5) "black" else "yellow"
  }

  def posByArray(i:Int,j:Int):Int = {
    if(j != 1) i - 1 + j * 10 else 30 - i - j * 10
  }

  def renderMessages(r: dom.CanvasRenderingContext2D, x: Int, y: Int, q: Queue[String]): Unit = {
    
    q.foldLeft((y,TEXT_SIZE / 2))((h,text)=>{
      r.font = h._2.toString() + "px sans-serif"
      fillText(r, x, h._1, text, "darkblue")
      (h._1 + TEXT_SIZE, h._2 + (TEXT_SIZE*0.25).toInt)
    })
  }


  def renderSticks(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {

    for( a <- 1 to 4){
      fillRect(r, x + a*STICK_STEP, y, STICK_WIDTH, STICK_LENGTH, colorStick(gCount, a))
    }
 }


  def renderDesk(r: dom.CanvasRenderingContext2D, x0: Int, y0: Int): Unit = {
    
    for(j <- 0 to 2) {
      for(i <- 1 to 10) {

        val x = x0 + (i-1) * DESK_STEP
        val y = y0 + j * DESK_STEP
        val pos = posByArray(i,j)

        fillRect(r, x, y, DESK_SQUARE, DESK_SQUARE, "lightgreen")
        fillText(r, x + DESK_TEXT_SHIFT, y + DESK_TEXT_SHIFT, gDesk(pos).text, "blue")
        renderDeskChips(r, x, y, pos)
      }
    }
  }

  def renderDeskChips(r: dom.CanvasRenderingContext2D, x: Int, y: Int, pos: Int): Unit = {
    
    val chip = gDesk(pos).chip
    val player = gDesk(pos).player
    if(chip != null) {
      if(player == gPlayer1) {
        renderChipBlue(r, x, y + CHIPS_STEP, 0, chip.num.toString,CHIPS_STEP,CHIPS_SIZE)
      } else {
        renderChipRed(r, x, y + (CHIPS_STEP*1.5).toInt, 0, chip.num.toString,CHIPS_STEP,CHIPS_SIZE)
      }
    }
  }

  def renderChips(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {
    
    for(l <- 0 to 9) {
      if(!gPlayer1.chips(l).taken) renderChipBlue(r, x, y, l, (l+1).toString(),CHIPS_STEP,CHIPS_SIZE)
      if(!gPlayer2.chips(l).taken) renderChipRed(r, x, y + CHIPS_STEP*2, l, (l+1).toString,CHIPS_STEP,CHIPS_SIZE)    }
  }

  def renderChipBlue(
    r: dom.CanvasRenderingContext2D,
    x: Int,
    y: Int,
    num: Int,
    text: String,
    step:Int,
    size:Int): Unit = {
    val drawChipBlue = Draw[ChipBlue](step,num,x,y)
    fillRect(r, xcr(drawChipBlue), ycr(drawChipBlue), size, size, "gray")
    fillText(r, xcrt(drawChipBlue), ycrt(drawChipBlue), text, "darkblue")
  }


  def renderChipRed(
    r: dom.CanvasRenderingContext2D,
    x: Int,
    y: Int,
    num: Int,
    text:String,
    step:Int,
    size:Int): Unit = {
    val drawChipRed = Draw[ChipRed](step,num,x,y)

    r.fillStyle = "pink"
    r.beginPath();
    r.arc(xcr(drawChipRed).toDouble, ycr(drawChipRed).toDouble, size/2, 0.0, 360.0)
    r.fill();

    fillText(r, xcrt(drawChipRed), ycrt(drawChipRed), text, "darkblue")
  }

  def renderRules(r: dom.CanvasRenderingContext2D, x: Int, y: Int): Unit = {
    fillText(
      r, x, y,
      "Игрок1: A - Бросить палочки, S - Новая фишка, Игрок2: J, K",
      "darkblue")
    fillText(
      r, x, y + (TEXT_SIZE*1.5).toInt,
      "Переместить фишку - 1, 2, 3, 4, 5, 6, 7, 8, 9, 0",
      "darkblue")
  }
}

case class Draw[A](step:Int,num:Int,x:Int,y:Int)
case class ChipBlue()
case class ChipRed()

