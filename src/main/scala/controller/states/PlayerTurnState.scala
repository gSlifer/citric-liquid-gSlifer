package cl.uchile.dcc.citric
package model.states

import cl.uchile.dcc.citric.controller.GameController

/** The `PlayerTurnState` class represents the state where a player starts his turn
 *
 * this state transitions to the RecoveryState or the MoveState depending in the player state (KO or not)
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class PlayerTurnState (controller: GameController) extends GameState(controller) {
  /**
   * goRecover Method
   *
   * method to transition to the RecoveryState state.
   */
  override def goRecover(): Unit = {
    controller.state = new RecoveryState(controller)
  }

  /**
   * goMove Method
   *
   * method to transition to the MoveState state.
   */
  override def goMove(): Unit = {
    controller.state = new MoveState(controller)
  }
}