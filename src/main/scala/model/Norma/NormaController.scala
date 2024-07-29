package cl.uchile.dcc.citric
package model.Norma

import model.Character.PlayerCharacter

/** The `NormaController` class represents the controller of the norma leveling
 *
 * here all the norma levels are initialized and the conditions
 * to level up the norma of a player are checked
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class NormaController {

  /** Initialization of all norma levels. */
  private val normaLv2 = new NormaLv2()
  private val normaLv3 = new NormaLv3()
  private val normaLv4 = new NormaLv4()
  private val normaLv5 = new NormaLv5()
  private val normaLv6 = new NormaLv6()

  /**
   * RequisitoStars Method
   *
   * checks how many stars a player need in order to level up his norma
   *
   * @param player the player who wants to know his requirements.
   * @return the amount of stars required to level up in this particular level
   *
   */
  def RequisitoStars(player: PlayerCharacter): Int = {
    val level = player.getNormaLevel
    val Required = level match {
      case 1 => normaLv2.starsRequired
      case 2 => normaLv3.starsRequired
      case 3 => normaLv4.starsRequired
      case 4 => normaLv5.starsRequired
      case 5 => normaLv6.starsRequired
      case _ => 999
    }
    Required
  }

  /**
   * RequisitoVictories Method
   *
   * checks how many victories a player need in order to level up his norma
   *
   * @param player the player who wants to know his requirements.
   * @return the amount of victories required to level up in this particular level
   *
   */
  def RequisitoVictories(player: PlayerCharacter): Int = {
    val level = player.getNormaLevel
    val Required = level match {
      case 1 => normaLv2.victoriesRequired
      case 2 => normaLv3.victoriesRequired
      case 3 => normaLv4.victoriesRequired
      case 4 => normaLv5.victoriesRequired
      case 5 => normaLv6.victoriesRequired
      case _ => 999
    }
    Required
  }

  /**
   * checkNorma Method
   *
   * the method checks if a player has the requirements to upgrade his norma level.
   * if the requirements are met, the method adds 1 to his norma.
   *
   * @param player the player to make a norma check.
   * @return true if the player leveled up its norma or false otherwise.
   */
  def checkNorma(player: PlayerCharacter): Boolean = {
    if (player.getNormaObjective == "Stars") {
      if (player.getNormaLevel <= 5 && player.getStars >= RequisitoStars(player)) {
        player.upgradeNorma()
        true
      } else {
        false
      }
    } else {
      if (player.getNormaLevel <= 5 && player.getVictories >= RequisitoVictories(player)) {
        player.upgradeNorma()
        true
      } else {
        false
      }
    }
  }
}
