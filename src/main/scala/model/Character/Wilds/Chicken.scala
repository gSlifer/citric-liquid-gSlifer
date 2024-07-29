package cl.uchile.dcc.citric
package model.Character.Wilds

import scala.util.Random

/** The `Chicken` class represents the Chicken WildUnit.
 *
 * it extends the abstract class WildUnit
 *
 * This specific wild unit has 3HP, -1ATK, -1DEF and 1EVA.
 * also carries an extra 3 stars.
 *
 * * @param seed A utility to generate the seed for random numbers. Default of 3
 * @param name The name of the chicken.
 * @param maxHp The maximum health points a unit can have. Default of 3
 * @param attack The unit's capability to deal damage to opponents. Default of -1
 * @param defense The unit's capability to resist or mitigate damage from opponents. Default of -1
 * @param evasion The unit's skill to completely avoid certain attacks. Default of 1
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class Chicken ( seed: Int = 3, // Valor predeterminado
                name: String = "Chicken",
                maxHp: Int = 3,
                attack: Int = -1,
                defense: Int = -1,
                evasion: Int = 1
              ) extends WildUnit(name, maxHp, attack, defense, evasion, new Random(seed)) {
  earnStars(3)
}
