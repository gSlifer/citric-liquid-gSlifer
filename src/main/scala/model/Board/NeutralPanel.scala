package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter

/** The `NeutralPanel` class represents the panel that does nothing.
 *
 * it extends the abstract class GamePanel
 *
 * This specific panel doesn't do anything when activated.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class NeutralPanel extends GamePanel {
  /**
   * activate Method
   *
   * Activate the effect of the panel.
   * in this case, the panel does Nothing.
   *
   * @param player the player to be affected.
   */
  override def activate(player: PlayerCharacter): Unit = {
    // No effect on the Player
  }
}
