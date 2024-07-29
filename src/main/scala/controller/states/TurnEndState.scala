package cl.uchile.dcc.citric
package model.states

import cl.uchile.dcc.citric.controller.GameController

/** The `TurnEndState` class represents the state where a player ends his turn
 *
 * this state transitions to the ChapterStartState or the PlayerTurnState depending in which player turn it is
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class TurnEndState (controller: GameController) extends GameState(controller){
  /**
   * endPlayerTurns Method
   *
   * method to transition to the ChapterStartState state.
   */
  override def endPlayerTurns(): Unit = {
    controller.state = new ChapterStartState(controller)
  }

  /**
   * nextPlayerTurn Method
   *
   * method to transition to the PlayerTurnState state.
   */
  override def nextPlayerTurn(): Unit = {
    controller.state = new PlayerTurnState(controller)
  }

  /**
   * goNext Method
   *
   * method to decide if the next transition should be to a PlayerTurnState or ChapterStartState.
   * the decision is based in if the player is the last one or not.
   * if the player is the last one, a new chapter starts.
   * if not, the turn of the next player starts.
   *
   * @param playerIndex the player that is currently playing.
   */
  override def goNext(playerIndex: Int): Unit = {
    if (playerIndex == 3) {
      controller.setPlayerIndex(0)
      endPlayerTurns()
    } else {
      controller.setPlayerIndex(controller.getPlayerIndex + 1)
      nextPlayerTurn()
    }
  }
}
