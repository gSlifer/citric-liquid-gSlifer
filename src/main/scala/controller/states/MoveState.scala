package cl.uchile.dcc.citric
package model.states

import model.Board.Panel
import cl.uchile.dcc.citric.controller.GameController

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/** The `MoveState` class represents the state where a moves in the board
 *
 * the movement is decided by a random roll of a dice.
 * in the case it exists more than one option to move, the game decides randomly the move to make.
 * this state transitions to the ActionsState.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class MoveState (controller: GameController) extends GameState(controller){

  /**
   * endMove Method
   *
   * method to transition to the ActionsState state.
   */
  override def endMove(): Unit = {
    controller.state = new ActionsState(controller)
  }

  /** Initialization of the movement validation.
   *  This is in order to break the loop of movement when a player wants to stop in a home space
   */
  private var MovementValidation: Boolean = true

  /** Initialization of the previous position of the player
   *  This is in order to prevent the player going back and forth between the same panels
   */
  private var previousPosition: Panel = _

  /** Initialization of the random seed for the game movement. */
  private val RNG = new Random(280202)

  /**
   * movePlayer Method
   *
   * method to move a player in the board.
   *
   * @param steps       the amount of spaces that the player can move.
   * @param playerIndex the index of the player that is moving.
   * @param position    the starting position for the player.
   */
  override def movePlayer(steps: Int, playerIndex: Int, position: Panel): Unit = {
    var remainingSteps = steps
    var newposition = position
    while (remainingSteps > 0 && MovementValidation) {
      val validDirections = getAvailablePositions(newposition, previousPosition)
      val direction = chooseDirection(validDirections)
      previousPosition = newposition
      newposition = moveTo(previousPosition, direction, playerIndex)
      remainingSteps -= 1
      if (checkIfHome(playerIndex)) {
        chooseStop()
      }
    }
  }

  /**
   * getAvailablePositions Method
   *
   * method to obtain all the possible panels that a player can move to.
   *
   * @param position the position to be checked.
   * @param previousPosition the previous position (that the player can't move to).
   */
  private def getAvailablePositions(position: Panel, previousPosition: Panel): ArrayBuffer[Panel] = {
    position.nextPanels.filterNot(_ == previousPosition)
  }

  /**
   * moveTo Method
   *
   * method to move the position of a player.
   *
   * @param direction the new position of a player.
   * @param playerIndex the current index of the player to move.
   * @return the new position.
   */
  private def moveTo(previousPosition: Panel, direction: Panel, playerIndex: Int): Panel = {
    controller.setPosition(playerIndex, direction, previousPosition)
    direction
  }

  /**
   * chooseDirection Method
   *
   * method to choose randomly a direction to follow when the movement has multiple options.
   *
   * @param value the list of possible movements.
   * @return the panel to move to.
   */
  private def chooseDirection(value: ArrayBuffer[Panel]): Panel = {
    val validDirections = value.filter(_ != null)
    val numOptions = validDirections.length
    println(s"Tienes $numOptions opciones disponibles para moverte:")
    validDirections.zipWithIndex.foreach { case (direction, index) =>
      println(s"$index: $direction")
    }
    val randomIndex = RNG.nextInt(numOptions)
    val randomDirection = validDirections(randomIndex)
    println(s"Opción seleccionada: $randomDirection")
    randomDirection
  }

  /**
   * checkIfHome Method
   *
   * method to choose check if the player moving is in his own home space.
   *
   * @param playerIndex the current index of the player to check.
   * @return true if the current position is his own home, false otherwise.
   */
  private def checkIfHome(playerIndex: Int): Boolean = {
    playerIndex match {
      case 0 => controller.getPosition(playerIndex) == controller.startingpoint1
      case 1 => controller.getPosition(playerIndex) == controller.startingpoint2
      case 2 => controller.getPosition(playerIndex) == controller.startingpoint3
      case 3 => controller.getPosition(playerIndex) == controller.startingpoint4
    }
  }

  /**
   * chooseStop Method
   *
   * method to choose randomly if the player stops in a home space.
   * it has 50% chance to stop and 50% chance to not stop.
   */
  private def chooseStop(): Unit = {
    println("¿Quieres detenerte? (yes/no)")

    val randomBoolean = RNG.nextBoolean()
    val input = if (randomBoolean) "yes" else "no"

    input match {
      case "yes" =>
        println("Deteniendo el movimiento.")
        MovementValidation = false
      case "no" =>
        println("Continuando el movimiento.")
    }
  }
}
