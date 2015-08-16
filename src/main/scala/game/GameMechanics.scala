package game

/**
 * Created by aleapv on 16.08.2015.
 */
object GameMechanics {

  case class Game(player1: Player,
                  player2: Player)

  def defineMove(p1: Player, p2: Player): Player = {
    p1
  }
}
