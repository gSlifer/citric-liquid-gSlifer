package cl.uchile.dcc.citric
package model.Character

import model.observer.Observable

import scala.util.Random

/** The `PlayerCharacter` class represents a character or avatar in the game, encapsulating
  * several attributes such as health points, attack strength, defense capability,
  * and evasion skills. Each player has a unique name, and throughout the game,
  * players can collect stars, roll dice, and progress in levels known as 'norma'.
  * This class not only maintains the state of a player but also provides methods
  * to modify and interact with these attributes.
  *
  * For instance, players can:
  *
  * - Increase or decrease their star count.
  *
  * - Roll a dice, a common action in many board games.
  *
  * - Advance their norma level.
  *
  * Furthermore, the `Player` class has a utility for generating random numbers,
  * which is primarily used for simulating dice rolls. By default, this utility is
  * an instance of the `Random` class but can be replaced if different random
  * generation behaviors are desired.
  *
  * @param name The name of the player. This is an identifier and should be unique.
  * @param maxHp The maximum health points a player can have. It represents the player's endurance.
  * @param attack The player's capability to deal damage to opponents.
  * @param defense The player's capability to resist or mitigate damage from opponents.
  * @param evasion The player's skill to completely avoid certain attacks.
  * @param randomNumberGenerator A utility to generate random numbers. Defaults to a new `Random`
  *                              instance.
  *
  * @author [[https://github.com/danielRamirezL/ Daniel Ramírez L.]]
  * @author [[https://github.com/joelriquelme/ Joel Riquelme P.]]
  * @author [[https://github.com/r8vnhill/ Ignacio Slater M.]]
  * @author [[https://github.com/Seivier/ Vicente González B.]]
  * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
  */
class PlayerCharacter(private val name: String,
                      private var maxHp: Int,
                      private val attack: Int,
                      private val defense: Int,
                      private val evasion: Int,
                      private val randomNumberGenerator: Random = new Random())
              extends AbstractUnits (name, maxHp, attack, defense, evasion, randomNumberGenerator) with Observable{

  /** Initialization of Norma level for the player. */
  private var normaLevel: Int = 1

  /** Default norma objective of stars.
    * this can be changed later on depending on the final rules of the game. */
  private var currentNormaObjective = "Stars"

  /** Rolls a dice and returns a value between 1 to 6. */
  def rollDice(): Int = {
    randomNumberGenerator.nextInt(6) + 1
  }

  /**
   * getNormaObjective Method
   *
   * getter for the norma objective.
   *
   * @return The current norma objective of the player.
   */
  def getNormaObjective: String = {
    currentNormaObjective
  }

  /**
   * changeNormaObjective Method
   *
   * setter for the norma objective.
   *
   * @param newObjective the objective for the norma level upgrade.
   */
  def changeNormaObjective(newObjective: String): Unit = {
    if (newObjective == "Stars") {
      currentNormaObjective = "Stars"
    }
    if (newObjective == "Victories"){
      currentNormaObjective = "Victories"
    } else {
      currentNormaObjective = currentNormaObjective
    }
  }

  /**
   * getNormaLevel Method
   *
   * getter for the norma level.
   *
   * @return The norma level of the player.
   */
  def getNormaLevel: Int = {
    normaLevel
  }

  /**
   * upgradeNorma Method
   *
   * setter for the norma level.
   *
   * levels up the norma level of the player by 1 stage.
   * notifies the observer in the case of norma reaching level 6
   */
  def upgradeNorma(): Unit = {
    normaLevel += 1
    if (normaLevel == 6) {
      notifyObservers()
    }
  }

  /**
   * getRNG Method
   *
   * getter for the RNG seed.
   *
   * @return The RNG seed of the player.
   */
  def getRNG: Random = {
    randomNumberGenerator
  }

  /** The amount of victories an unit gives upon defeat. */
  def victoryValueOnAbstractUnits(y: AbstractUnits): Int = 2

}
