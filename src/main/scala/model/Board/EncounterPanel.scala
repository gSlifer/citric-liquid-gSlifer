package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter

/** The `EncounterPanel` class represents the panel that triggers an encounter with a WildUnit.
 *
 * it extends the abstract class GamePanel
 *
 * This specific panel triggers a combat with a WildUnit, this is not yet implemented,
 * because the combat section is not implemented either.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class EncounterPanel extends GamePanel {
  /**
   * activate Method
   *
   * Activate the effect of the panel.
   * in this case, trigger a combat with a WildUnit.
   * this is not yet implemented, because the combat section is not implemented either.
   *
   * @param player the player to be affected.
   */
  override def activate(player: PlayerCharacter): Unit = {
    // Trigger a combat with a random WildUnit
  }
}
