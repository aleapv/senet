/**
 * Created by aleapv on 16.08.2015.
 */
object MainTest {
  def main(args: Array[String]) {
    import game.Desk._
    println(row1.fields)
    println(row2.fields)
    println(row3.fields)

    import game.Chips._
    chips.map(print(_))

    println
    import game.sticks.Sticks._

    import game.GameMechanics._
    println(count(throwSticks))
  }
}
