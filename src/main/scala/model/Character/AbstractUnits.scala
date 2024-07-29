package cl.uchile.dcc.citric
package model.Character

import scala.util.Random

/** The `AbstractUnit` abstract class represents a unit in the game
 *
 * it extends the Units trait.
 * implements methods common for all units
 *
 * @param name The name of the unit.
 * @param maxHp The maximum health points a unit can have.
 * @param attack The unit's capability to deal damage to opponents.
 * @param defense The unit's capability to resist or mitigate damage from opponents.
 * @param evasion The unit's skill to completely avoid certain attacks.
 * @param randomNumberGenerator A utility to generate random numbers.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
abstract class AbstractUnits (private val name: String,
                     private var maxHp: Int,
                     private val attack: Int,
                     private val defense: Int,
                     private val evasion: Int,
                     private val randomNumberGenerator: Random)
                     extends Units{

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
  def takeDamage(damage: Int): Unit = {
    val currentHp = getCurrHP - damage
    if (currentHp <= 0) {
      setCurrHP(0)
      setStatus(true)
    } else {
      setCurrHP(currentHp)
    }
  }

  /**
   * getCurrHP Method
   *
   * getter for the unit current HP.
   *
   * @return The current amount of HP the unit has.
   */
  def getCurrHP: Int = {
    maxHp
  }

  /**
   * setCurrHP Method
   *
   * setter for the unit current HP.
   *
   * @param hp The current amount of HP the unit has.
   */
  def setCurrHP(hp: Int): Unit = {
    maxHp = hp
  }

  /**
   * getName Method
   *
   * getter for the WildUnit name.
   *
   * @return The name of the WildUnit.
   */
  def getName: String = {
    name
  }

  /**
   * getAttack Method
   *
   * getter for the WildUnit attack.
   *
   * @return The attack of the WildUnit.
   */
  def getAttack: Int = {
    attack
  }

  /**
   * getDefense Method
   *
   * getter for the WildUnit defense.
   *
   * @return The defense of the WildUnit.
   */
  def getDefense: Int = {
    defense
  }

  /**
   * getEvasion Method
   *
   * getter for the WildUnit evasion.
   *
   * @return The evasion of the WildUnit.
   */
  def getEvasion: Int = {
    evasion
  }

  /**
   * DoAttack Method
   *
   * calculates the damage an unit does with an attack.
   *
   * @return The damage value of an attack.
   */
  def DoAttack: Int = {
    val totalAttack = attack + randomNumberGenerator.nextInt(6) + 1
    totalAttack
  }

  /**
   * DoDefend Method
   *
   * calculates the damage an unit gets after blocking an attack.
   *
   * @param attackValue the damage an unit is about to take.
   * @return The new damage value of an attack (reduced).
   */
  def DoDefend(attackValue: Int): Int = {
    val totalDefense = defense + randomNumberGenerator.nextInt(6) + 1
    val damage = Math.max(1, attackValue - totalDefense)
    damage
  }

  /**
   * DoEvade Method
   *
   * calculates if a unit dodges an attack or not.
   *
   * @param attackValue the damage an unit is about to take.
   * @return true if the evasion is successful, false otherwise.
   */
  def DoEvade(attackValue: Int): Boolean = {
    val totalEvasion = evasion + randomNumberGenerator.nextInt(6) + 1
    if (totalEvasion > attackValue) {
      true
    } else {
      false
    }
  }

  /**
   * recieveAttack Method
   *
   * makes a player take an attack.
   *
   * @param action either defend or evade the upcoming attack.
   * @param damage the damage value of the attack.
   * @param attacker the unit that attacked.
   *
   */
  def recieveAttack(action: String, damage: Int, attacker: AbstractUnits): Unit = {
    if (action == "evade" && DoEvade(damage) == false) {
      takeDamage(damage)
    } else if (action == "defend"){
      val newdamage = DoDefend(damage)
      takeDamage(newdamage)
    }
    if (getCurrHP == 0) {
      attacker.winBattle(this)
    }
  }

  /**
   * winBattle Method
   *
   * gives the amount of stars and victories according to the unit defeated.
   *
   * @param loser the unit that was defeated.
   *
   */
  def winBattle(loser: AbstractUnits): Unit = {
    val winningStars = loser.getStars
    earnStars(winningStars)
    val winningVictories = victoryValue(loser)
    winVictory(winningVictories)
  }

  /** The amount of victories an unit gives upon defeat. (use of double dispatch) */
  def victoryValue(x: AbstractUnits): Int = x.victoryValueOnAbstractUnits(this)
  def victoryValueOnAbstractUnits(y: AbstractUnits): Int
}
