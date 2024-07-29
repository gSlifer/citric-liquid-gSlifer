package cl.uchile.dcc.citric
package model.states

import model.Board.Panel
import model.Character.PlayerCharacter
import cl.uchile.dcc.citric.controller.GameController

/** The `ActionsState` class represents the state where a player activates the panel that he is on
 *
 * this state transitions to the TurnEndState
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class ActionsState (controller: GameController) extends GameState(controller){
  /**
   * endCurrentTurn Method
   *
   * method to transition to the TurnEndState state.
   */
  override def endCurrentTurn(): Unit = {
    controller.state = new TurnEndState(controller)
  }

  /**
   * act Method
   *
   * method to activate a panel in the board.
   *
   * @param player   the player that activates the panel.
   * @param position the panel that is activated.
   */
  override def act(player: PlayerCharacter, position: Panel): Unit = {
    position.activate(player)
  }
}
