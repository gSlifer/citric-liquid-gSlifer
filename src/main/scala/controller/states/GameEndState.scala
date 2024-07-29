package cl.uchile.dcc.citric
package model.states

import cl.uchile.dcc.citric.controller.GameController

/** The `GameEndState` class represents the state where a player wins the game and the game ends
 *
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class GameEndState (controller: GameController) extends GameState(controller){
  override def EndGameMsg(): Unit = {
    println("¡Fin del juego!")
  }
}
