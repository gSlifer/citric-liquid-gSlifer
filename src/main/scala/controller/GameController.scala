package cl.uchile.dcc.citric
package controller

import model.Board.{GameBoard, Panel}
import model.Character.PlayerCharacter
import model.observer.Observer
import model.states.{GameEndState, GameStartState, GameState}

import scala.util.Random

/** The `GameController` class represents the controller of the game.
 *
 * here all the transitions between states are implemented
 * it also acts as an observer for all the players in the game
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class GameController extends Observer{

  /** Initialization of the state of the game, GameStartState by default. */
  var state: GameState = new GameStartState(this)

  /** Initialization of the list of players, empty by default. */
  private var players: List[PlayerCharacter] = List()

  /** Initialization of the game board, defaulted to the object GameBoard created. */
  val gameboard: Array[Array[Panel]] = GameBoard.createBoard()

  /** Initialization of the current chapyer count, 0 by default. */
  private var currentChapter: Int = 0

  /** Initialization of the index of the current player, 0 by default. */
  private var currentPlayerIndex: Int = 0

  /** Initialization of the starting points (all HomePanel). */
  val startingpoint1: Panel = gameboard(2)(2)
  val startingpoint2: Panel = gameboard(2)(6)
  val startingpoint3: Panel = gameboard(6)(2)
  val startingpoint4: Panel = gameboard(6)(6)

  /** Initialization of the positions of each player, all of them start in his own starting point by default. */
  private var playerPositions:List[Panel] = List(startingpoint1, startingpoint2, startingpoint3, startingpoint4)

  /** Initialization of a default player. used only if controller doesn't have any player. */
  private val defaultPlayer = new PlayerCharacter("", 0,0,0,0, new Random())

  /**
   * addPlayer Method
   *
   * method to add a player to the list of players.
   *
   * @param player the player to add.
   */
  def addPlayer(player: PlayerCharacter): Unit = {
    players = players :+ player
  }

  /**
   * currentPlayer Method
   *
   * method to get the player that is currently playing.
   *
   * @return the player that is currently playing.
   */
  def currentPlayer: PlayerCharacter = {
    if (players.nonEmpty) players(currentPlayerIndex)
    else defaultPlayer
  }

  /**
   * setPlayers Method
   *
   * method to set the list of players to be a specific list.
   *
   * @param newPlayers the list of players to set.
   */
  private def setPlayers(newPlayers: List[PlayerCharacter]): Unit = {
    players = newPlayers
  }

  /**
   * setChapter Method
   *
   * method to set the current chapter count to a specific int.
   *
   * @param newChapter the chapter Int to set.
   */
  private def setChapter(newChapter: Int): Unit = {
    currentChapter = newChapter
  }

  /**
   * getCurrentChapter Method
   *
   * method to get the current chapter count.
   *
   * @return the number of chapter that is currently going.
   */
  def getCurrentChapter: Int ={
    currentChapter
  }

  /**
   * setPosition Method
   *
   * method to set the position of a player to be a specific panel.
   *
   * @param index the index of the current player.
   * @param position the panel that represents the new position of the player.
   */
  def setPosition(index: Int, position: Panel, previousPosition: Panel): Unit = {
    playerPositions = playerPositions.updated(index, position)
    previousPosition.removeCharacter(currentPlayer)
    position.addCharacter(currentPlayer)
  }

  /**
   * getPosition Method
   *
   * method to get the position of a player.
   *
   * @param index the index of the current player.
   * @return the panel that the player is currently on.
   */
  def getPosition(index: Int): Panel = {
    playerPositions(index)
  }

  /**
   * getPlayerIndex Method
   *
   * method to get the the index of the current player.
   *
   * @return the index of the current player.
   */
  def getPlayerIndex: Int = {
    currentPlayerIndex
  }

  /**
   * setPlayerIndex Method
   *
   * method to set the the index of the current player to be a specific Int.
   *
   * @param newIndex the index to set.
   */
  def setPlayerIndex(newIndex: Int): Unit = {
    currentPlayerIndex = newIndex
  }

  /**
   * getPlayers Method
   *
   * method to get the list of players.
   *
   * @return the the list of players.
   */
  def getPlayers: List[PlayerCharacter] = {
    players
  }

  /**
   * controllerStartGame Method
   *
   * method to perform all the necessary setup at the start of the game.
   * here the list of players is shuffled, all the players are assigned a starting point,
   * this controller is made observer of all players, and concludes by transitioning
   * its state to the ChapterStartState.
   */
  def controllerStartGame(): Unit = {
    val shuffledPlayers = state.shufflePlayers(players)
    setPlayers(shuffledPlayers)
    state.assignHomes(players)
    state.assignObservers(players)
    state.startGame()
  }

  /**
   * controllerStartChapter Method
   *
   * method to start a chapter.
   * here the currentchapter is increased by 1.
   * concludes by transitioning its state to the PlayerTurnState.
   */
  def controllerStartChapter(): Unit = {
    val newChapter = state.advanceChapter(currentChapter)
    setChapter(newChapter)
    state.startChapter()
  }

  /**
   * controllerGoRecover Method
   *
   * method to transition to the RecoveryState.
   */
  def controllerGoRecover(): Unit = {
    state.goRecover()
  }

  /**
   * controllerTryRecover Method
   *
   * method to do a roll for a recovery check.
   * if the player succeeds, we call the controllerGoMove method.
   * if the player fails, we call the controllerEndPlayerTurn method.
   */
  def controllerTryRecover(): Unit = {
    state.recoveryCheck(currentPlayer)
    if (currentPlayer.getStatus) {
      controllerEndPlayerTurn()
    } else {
      controllerGoMove()
    }
  }

  /**
   * controllerGoMove Method
   *
   * method to move a player.
   * here the player rolls a dice and moves that amount of spaces.
   * concludes by transitioning its state to the ActionsState.
   */
  def controllerGoMove(): Unit = {
    state.goMove()
    val roll = currentPlayer.rollDice()
    state.movePlayer(roll, currentPlayerIndex, getPosition(currentPlayerIndex))
    state.endMove()
  }

  /**
   * controllerDoActions Method
   *
   * method to activate the panel the player is currently on.
   * concludes by transitioning its state to the TurnEndState.
   */
  def controllerDoActions(): Unit = {
    state.act(currentPlayer, getPosition(currentPlayerIndex))
    state.endCurrentTurn()
  }

  /**
   * controllerEndPlayerTurn Method
   *
   * method to end a player turn. transitions the state to the TurnEndState
   * calls the method controllerGoNextTurn.
   */
  private def controllerEndPlayerTurn(): Unit = {
    state.endCurrentTurn()
    controllerGoNextTurn()
  }

  /**
   * controllerGoNextTurn Method
   *
   * method to end a player turn. transitions the state to the PlayerTurnState or ChapterTurnState
   */
  def controllerGoNextTurn(): Unit = {
    state.goNext(currentPlayerIndex)
  }

  /**
   * update Method
   *
   * method to observe a player norma level, if it reaches level 6, transitions to the GameEndState
   */
  override def update(): Unit = {
    if (currentPlayer.getNormaLevel == 6) {
      state = new GameEndState(this)
    }
  }
}
