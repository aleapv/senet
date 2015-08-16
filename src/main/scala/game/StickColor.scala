package game

/**
 * Created by aleapv on 16.08.2015.
 */
sealed trait StickColor

object StickColor {
  object Black extends StickColor
  object White extends StickColor

  def getColor(c: StickColor): Short = c match {
    case Black => 1
    case White => 2
  }
}
