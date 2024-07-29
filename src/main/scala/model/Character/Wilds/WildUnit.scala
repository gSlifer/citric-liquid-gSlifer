package cl.uchile.dcc.citric
package model.Character.Wilds

import model.Character.AbstractUnits

import scala.util.Random

/** The `WildUnit` abstract class represents a unit a Player can encounter during the game
 *
 * it extends the Unit Abstract Class.
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

abstract class WildUnit(private var name: String,
               private var maxHp: Int,
               private var attack: Int,
               private var defense: Int,
               private var evasion: Int,
               private val randomNumberGenerator: Random)
               extends AbstractUnits (name, maxHp, attack, defense, evasion, randomNumberGenerator){

  /** The amount of victories this type of unit gives upon defeat. */
  def victoryValueOnAbstractUnits(y: AbstractUnits): Int = 1

}
