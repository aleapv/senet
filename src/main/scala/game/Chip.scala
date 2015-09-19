package game

case class Chip (
  num: Int,
  taken: Boolean,
  pos: Int
)

object Chip {

  def movedChip(old:Chip,count:Int):Chip={
    Chip(old.num,true,old.pos-count)
  }
}
