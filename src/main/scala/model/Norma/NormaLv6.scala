package cl.uchile.dcc.citric
package model.Norma

/** The `Normalv6` class represents the level 6 of the norma leveling system
 *
 * this specific level has a requirement of 200 stars or 14 wins
 * in order to be accessed. this is the final level and any player
 * that reaches this level wins the game.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class NormaLv6 extends Norma {
  val starsRequired = 200
  val victoriesRequired = 14
}
