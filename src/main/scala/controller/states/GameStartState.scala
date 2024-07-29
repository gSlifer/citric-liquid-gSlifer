package cl.uchile.dcc.citric
package model.states

import model.Character.PlayerCharacter
import cl.uchile.dcc.citric.controller.GameController

import cl.uchile.dcc.citric.model.Board.HomePanel

import scala.util.Random

/** The `GameStartState` class represents the state where a game starts
 *
 * at the start of the game, all players are assigned an order and a starting point
 * the controller is added as an observer of all players in order to detect a change in their normaLevel
 * this state transitions to the TurnEndState
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class GameStartState (controller: GameController) extends GameState(controller){

  /** Initialization of the random seed for the game. */
  private val RNG = new Random(1001)

  /**
   * startGame Method
   *
   * method to transition to the ChapterStartState state.
   */
  override def startGame(): Unit = {
    controller.state = new ChapterStartState(controller)
  }

  /**
   * shufflePlayers Method
   *
   * method to shuffle a list of players in a random order.
   *
   * @param players the list of players to be shuffled.
   * @return the shuffled list of players.
   */
  override def shufflePlayers(players: List[PlayerCharacter]): List[PlayerCharacter] = {
    RNG.shuffle(players)
  }

  /**
   * assignHomes Method
   *
   * method to assign a starting point to each player.
   *
   * @param players the list of players to be assigned a home.
   */
  override def assignHomes(players:List[PlayerCharacter]): Unit = {
    controller.startingpoint1 match {
      case homePanel: HomePanel =>
        homePanel.assignOwner(players(0))
    }
    controller.startingpoint2 match {
      case homePanel: HomePanel =>
        homePanel.assignOwner(players(1))
    }
    controller.startingpoint3 match {
      case homePanel: HomePanel =>
        homePanel.assignOwner(players(2))
    }
    controller.startingpoint4 match {
      case homePanel: HomePanel =>
        homePanel.assignOwner(players(3))
    }
  }

  /**
   * assignObservers Method
   *
   * method to assign the controller as a observer to each player.
   *
   * @param players the list of players in a game.
   */
  override def assignObservers(players:List[PlayerCharacter]): Unit = {
    players.foreach(player => player.addObserver(controller))
  }
}
