package cl.uchile.dcc.citric
package model.states

import model.Character.PlayerCharacter
import cl.uchile.dcc.citric.controller.GameController

/** The `RecoveryState` class represents the state where a player is KO
 *
 * this state transitions to the TurnEndState or the goMove depending on its success in the recoveryCheck
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class RecoveryState (controller: GameController) extends GameState(controller){
  /**
   * endCurrentTurn Method
   *
   * method to transition to the TurnEndState state.
   */
  override def endCurrentTurn(): Unit = {
    controller.state = new TurnEndState(controller)
  }

  /**
   * goMove Method
   *
   * method to transition to the MoveState state.
   */
  override def goMove(): Unit = {
    controller.state = new MoveState(controller)
  }

  /**
   * recoveryCheck Method
   *
   * method to check if a player can recover or not.
   *
   * @param player the player that does a roll for recovery.
   */
  override def recoveryCheck(player: PlayerCharacter): Unit = {
    val diceRoll = player.rollDice()
    val recoveryThreshold = Math.max(1, (7 - controller.getCurrentChapter))
    if (diceRoll >= recoveryThreshold) {
      player.setStatus(false)
    } else {
    }
  }
}
