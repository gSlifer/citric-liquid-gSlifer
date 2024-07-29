package cl.uchile.dcc.citric
package model.observer

/** The `Observer` trait represents the base of a observer
 *
 * an obsever checks for a certaing change in an observable
 * here, it will be used to check if the normaLevel of a PlayerCharacter reaches level 6
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
trait Observer {
  /**
   * update Method
   *
   * method to observe a player norma level, if it reaches level 6, transitions to the GameEndState
   */
  def update(): Unit
}
