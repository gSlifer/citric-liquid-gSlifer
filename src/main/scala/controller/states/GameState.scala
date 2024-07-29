package cl.uchile.dcc.citric
package model.states

import model.Character.PlayerCharacter
import model.Board.Panel
import cl.uchile.dcc.citric.controller.GameController

/** The `GameState` class represents the states of a game
 *
 * this class acts as a base for all the other states
 * it has empty implementations for all the public methods in all other states. this is made
 * in order to prevent calling a method that shouldn't be called unless the controller is in
 * the correct state. for example startgame() should be called only if the controller.state is
 * an instance of GameStartState, calling this method in any other state won't do anything
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class GameState protected(val controller: GameController){
  /**
   * startGame Method
   *
   * method to transition to the ChapterStartState state.
   * this should only work in the GameStartState state.
   */
  def startGame(): Unit = {
  }

  /**
   * startChapter Method
   *
   * method to transition to the PlayerTurnState state.
   * this should only work in the ChapterStartState state.
   */
  def startChapter(): Unit = {
  }

  /**
   * endPlayerTurns Method
   *
   * method to transition to the ChapterStartState state.
   * this should only work in the TurnEndState state.
   */
  def endPlayerTurns(): Unit = {
  }

  /**
   * goRecover Method
   *
   * method to transition to the RecoveryState state.
   * this should only work in the PlayerTurnState state.
   */
  def goRecover(): Unit = {
  }

  /**
   * goMove Method
   *
   * method to transition to the MoveState state.
   * this should only work in the RecoveryState state and PlayerTurnState state.
   */
  def goMove(): Unit = {
  }

  /**
   * endCurrentTurn Method
   *
   * method to transition to the TurnEndState state.
   * this should only work in the RecoveryState state and ActionsState state.
   */
  def endCurrentTurn(): Unit = {
  }

  /**
   * endMove Method
   *
   * method to transition to the ActionsState state.
   * this should only work in the MoveState state.
   */
  def endMove(): Unit = {
  }

  /**
   * nextPlayerTurn Method
   *
   * method to transition to the PlayerTurnState state.
   * this should only work in the TurnEndState state.
   */
  def nextPlayerTurn(): Unit = {
  }

  /**
   * shufflePlayers Method
   *
   * method to shuffle a list of players in a random order.
   * this should only work in the GameStartState state.
   *
   * @param players the list of players to be shuffled.
   * @return the same list of players.
   */
  def shufflePlayers(players: List[PlayerCharacter]): List[PlayerCharacter] = {
    players
  }

  /**
   * assignHomes Method
   *
   * method to assign a starting point to each player.
   * this should only work in the GameStartState state.
   *
   * @param players the list of players to be assigned a home.
   */
  def assignHomes(players: List[PlayerCharacter]): Unit = {
  }

  /**
   * advanceChapter Method
   *
   * method to advance to the next chapter.
   * this should only work in the ChapterStart state.
   *
   * @param currentChapter the number of the current chapter.
   * @return 0, the chapter doesn't advance.
   */
  def advanceChapter(currentChapter: Int): Int = {
    0
  }

  /**
   * recoveryCheck Method
   *
   * method to check if a player can recover or not.
   * this should only work in the RecoveryState state.
   *
   * @param player the player that does a roll for recovery.
   */
  def recoveryCheck(player: PlayerCharacter): Unit = {
  }

  /**
   * movePlayer Method
   *
   * method to move a player in the board.
   * this should only work in the MoveState state.
   *
   * @param steps the amount of spaces that the player can move.
   * @param playerIndex the index of the player that is moving.
   * @param position the starting position for the player.
   */
  def movePlayer(steps: Int, playerIndex: Int, position: Panel): Unit = {
  }

  /**
   * act Method
   *
   * method to activate a panel in the board.
   * this should only work in the ActionsState state.
   *
   * @param player the player that activates the panel.
   * @param position the panel that is activated.
   */
  def act(player: PlayerCharacter, position: Panel): Unit = {
  }

  /**
   * goNext Method
   *
   * method to decide if the next transition should be to a PlayerTurnState or ChapterStartState.
   * this should only work in the TurnEndState state.
   *
   * @param playerIndex the player that is currently playing.
   */
  def goNext(playerIndex: Int): Unit = {
  }

  /**
   * assignObservers Method
   *
   * method to assign the controller as a observer to each player.
   * this should only work in the GameStartState state.
   *
   * @param players the list of players in a game.
   */
  def assignObservers(players:List[PlayerCharacter]): Unit = {
  }

  /**
   * EndGameMsg Method
   *
   * method to print a final message.
   * this should only work in the GameEndState state.
   */
  def EndGameMsg(): Unit = {
  }
}
