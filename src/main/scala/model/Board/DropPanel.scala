package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter

/** The `DropPanel` class represents the panel that removes stars from a player.
 *
 * it extends the abstract class GamePanel
 *
 * This specific panel makes a player throw a dice(1-6) and remove
 * the result multiplied by the norma level of the player.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class DropPanel extends GamePanel {
  /**
   * activate Method
   *
   * Activate the effect of the panel.
   * in this case, remove stars from the player.
   *
   * @param player the player to be affected.
   */
  override def activate(player: PlayerCharacter): Unit = {
    val dice = player.rollDice()
    val starsLost = dice * player.getNormaLevel
    player.earnStars(-starsLost)
  }
}
