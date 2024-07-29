package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter
import scala.math._

/** The `BonusPanel` class represents the panel that gives stars to a player.
 *
 * it extends the abstract class GamePanel
 *
 * This specific panel makes a player throw a dice(1-6) and give
 * the minimum between the result multiplied by the norma level of the player
 * and the result multiplied by 3.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class BonusPanel extends GamePanel {
  /**
   * activate Method
   *
   * Activate the effect of the panel.
   * in this case, the panel gives stars to a player.
   *
   * @param player the player to be affected.
   */
  override def activate(player: PlayerCharacter): Unit = {
    val dice = player.rollDice()
    val starsGained = min(dice * player.getNormaLevel, dice * 3)
    player.earnStars(starsGained)
  }
}