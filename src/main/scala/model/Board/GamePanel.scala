package cl.uchile.dcc.citric
package model.Board

import model.Character.PlayerCharacter
import scala.collection.mutable.ArrayBuffer

/** The `GamePanel` abstract class represents the base of any panel to be created.
 *
 * it extends the trait Panel
 *
 * this has all the variables, values and methods implemented, so all the specific panels can
 * extend this class and get them.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
abstract class GamePanel extends Panel {

  /** Initialization of default players in the panel (None). */
  val characters: ArrayBuffer[PlayerCharacter] = ArrayBuffer.empty

  /** Initialization of default adjacent panels (None). */
  var nextPanels: ArrayBuffer[Panel] = ArrayBuffer.empty

  /**
   * addCharacter Method
   *
   * adds a character to the array of characters in the panel
   *
   * @param player the character to be added.
   */
  def addCharacter(player: PlayerCharacter): Unit = {
    characters += player
  }

  /**
   * removeCharacter Method
   *
   * removes a character from the array of characters in the panel
   *
   * @param player the character to be removed.
   */
  def removeCharacter(player: PlayerCharacter): Unit = {
    characters -= player
  }

  /**
   * connectTo Method
   *
   * connects a panel to another one.
   * The connection has to be in both ways
   * (E.g. if we connect panel A to panel B, panel B has to be connected to panel A)
   *
   * @param panel the panel to be connected.
   */
  def connectTo(panel: Panel): Unit = {
    if (!nextPanels.contains(panel)) {
      nextPanels += panel
      panel.connectTo(this)
    }
  }

  /**
   * connectTo Method
   *
   * disconnects a panel to another one.
   * The disconnection has to be in both ways
   * (E.g. if we disconnect panel A with panel B, panel B has to be disconnected with panel A)
   *
   * @param panel the panel to be disconnected.
   */
  def disconnectFrom(panel: Panel): Unit = {
    if (nextPanels.contains(panel)) {
      nextPanels -= panel
      panel.disconnectFrom(this)
    }
  }
}