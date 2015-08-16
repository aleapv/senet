package game

/**
 * Created by aleapv on 16.08.2015.
 */
object Desk {

  object row1 {
    val fields = fieldsFill(1)
  }
  object row2 {
    val fields = fieldsFill(11)
  }
  object row3 {
    val fields = fieldsFill(21)
  }

  // TODO find library analogue
  def fieldsFill(start: Int) = {
    def iter(acc: Int, l: List[Field]): List[Field] = {
      if(acc <= start + 9) Field(acc) :: iter(acc + 1, l)
      else l
    }
    iter(start, Nil)
  }
}
