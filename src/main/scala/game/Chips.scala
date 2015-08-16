package game

/**
 * Created by aleapv on 16.08.2015.
 */
object Chips {

  trait Chip
  case class Spool() extends Chip
  case class Cone() extends Chip

  val chips = List.fill(5)(Spool).++(List.fill(5)(Cone))
}