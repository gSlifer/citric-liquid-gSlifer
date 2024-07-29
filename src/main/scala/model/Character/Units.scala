package cl.uchile.dcc.citric
package model.Character

import scala.util.Random

/** The `Units` trait represents the base of a unit in the game
 *
 * other units like PlayerCharacter, should extend from this class.
 *
 * units have stars, victories, can earn victories and win/lose stars
 *
 * units also have their current HP, they can lose this HP (or be healed)
 * if a unit loses all their HP, it will be Knocked Out.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
trait Units {
  /** Initialization of default stars (0). */
  private var stars: Int = 0

  /** Initialization of default victories (0). */
  private var victories: Int = 0

  /** Initialization of default state (not KO). */
  private var isKO: Boolean = false

  /**
   * takeDamage Method
   *
   * setter for the norma currentHP.
   * a Unit can take a certain amount of damage,
   * if the HP of the unit reaches 0, it goes into KO state.
   * this method can be also used to heal a unit using negative damage.
   *
   * @param damage the damage the unit takes.
   */
  def takeDamage(damage: Int): Unit

  /**
   * earnStars Method
   *
   * a Unit can earn a certain amount of stars.
   *
   * @param amount the amount of stars the unit gained.
   */
  def earnStars(amount: Int): Unit = {
    stars += amount
  }

  /**
   * winVictory Method
   *
   * a Unit can earn a win, adding 1 to their victories counter
   */
  def winVictory(amount: Int): Unit = {
    victories += amount
  }

  /**
   * getStars Method
   *
   * getter for the unit stars.
   *
   * @return The current amount of stars the unit has.
   */
  def getStars: Int = {
    stars
  }

  /**
   * getVictories Method
   *
   * getter for the unit victories.
   *
   * @return The current amount of victories the unit has.
   */
  def getVictories: Int = {
    victories
  }

  /**
   * getCurrHP Method
   *
   * getter for the unit current HP.
   *
   * @return The current amount of HP the unit has.
   */
  def getCurrHP: Int

  /**
   * setCurrHP Method
   *
   * setter for the unit current HP.
   *
   * @param hp The current amount of HP the unit has.
   */
  def setCurrHP(hp: Int): Unit

  /**
   * getStatus Method
   *
   * getter for the unit status (KO or not).
   *
   * @return true if the unit is KO, false otherwise.
   */
  def getStatus: Boolean = {
    isKO
  }

  /**
   * setStatus Method
   *
   * setter for the unit status (KO or not).
   *
   * @param stat true if the unit is KO, false otherwise.
   */
  def setStatus(stat: Boolean): Unit = {
    isKO = stat
  }

  /**
   * getName Method
   *
   * getter for the WildUnit name.
   *
   * @return The name of the WildUnit.
   */
  def getName: String

  /**
   * getAttack Method
   *
   * getter for the WildUnit attack.
   *
   * @return The attack of the WildUnit.
   */
  def getAttack: Int

  /**
   * getDefense Method
   *
   * getter for the WildUnit defense.
   *
   * @return The defense of the WildUnit.
   */
  def getDefense: Int

  /**
   * getEvasion Method
   *
   * getter for the WildUnit evasion.
   *
   * @return The evasion of the WildUnit.
   */
  def getEvasion: Int

  /**
   * DoAttack Method
   *
   * calculates the damage an unit does with an attack.
   *
   * @return The damage value of an attack.
   */
  def DoAttack: Int

  /**
   * DoDefend Method
   *
   * calculates the damage an unit gets after blocking an attack.
   *
   * @param attackValue the damage an unit is about to take.
   * @return The new damage value of an attack (reduced).
   */
  def DoDefend(attackValue: Int): Int

  /**
   * DoEvade Method
   *
   * calculates if a unit dodges an attack or not.
   *
   * @param attackValue the damage an unit is about to take.
   * @return true if the evasion is successful, false otherwise.
   */
  def DoEvade(attackValue: Int): Boolean

  /** The amount of victories an unit gives upon defeat. */
  def victoryValue(x: AbstractUnits): Int
}
