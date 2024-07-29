package cl.uchile.dcc.citric
package model.Norma

/** The `Norma` trait represents the base of the norma leveling system
 *
 * each norma level that extends from this trait must have
 * a certain amount of stars and victories required to level up
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
trait Norma {
  /** stars required to level up to this norma level */
  val starsRequired: Int
  /** victories required to level up to this norma level */
  val victoriesRequired: Int
}
