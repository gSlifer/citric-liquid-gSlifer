package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter

import cl.uchile.dcc.citric.model.Norma.NormaController

/** The `HomePanel` class represents the panel that can be the home to one player.
 *
 * it extends the abstract class GamePanel
 *
 * This specific panel when activated can heal the player who activated it and check
 * if the player can level up his norma.
 *
 * The owner of this panel also has the option to stop his movement if he passes here,
 * this is not implemented yet.
 *
 * The logic to give ownership of a HomePanel is implemented and tested but not used yet.
 * (this step should be done when starting a game, assigning all players a home panel)
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class HomePanel extends GamePanel {

  /** Initialization of default owner of the panel. */
  private val default = new PlayerCharacter("placeholder",0,0,0,0)
  private var owner = default
  private val normaControl = new NormaController

  /**
   * activate Method
   *
   * Activate the effect of the panel.
   * in this case, the panel heals the player 1 HP and does a norma check of the player
   *
   * @param player the player to be affected.
   */
  override def activate(player: PlayerCharacter): Unit = {
    player.takeDamage(-1)
    normaControl.checkNorma(player)
  }

  /**
   * assignOwner Method
   *
   * sets the ownership of this panel to a certain player
   *
   * @param player the player who is going to be the owner.
   */
  def assignOwner(player: PlayerCharacter): Unit = {
    owner = player
  }

  /**
   * getOwner Method
   *
   * getter for the owner of the panel.
   *
   * @return The owner of the panel.
   */
  def getOwner: PlayerCharacter = {
    owner
  }
}