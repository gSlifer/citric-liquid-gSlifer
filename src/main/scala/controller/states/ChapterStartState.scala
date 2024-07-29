package cl.uchile.dcc.citric
package model.states

import cl.uchile.dcc.citric.controller.GameController

/** The `ChapterStartState` class represents the state where a chapter starts
 *
 * every time a new chapter starts, the count of chapters increases by 1
 * this state transitions to the PlayerTurnState
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
class ChapterStartState (controller: GameController) extends GameState(controller){
  /**
   * startChapter Method
   *
   * method to transition to the PlayerTurnState state.
   */
  override def startChapter(): Unit = {
    controller.state = new PlayerTurnState(controller)
  }

  /**
   * advanceChapter Method
   *
   * method to advance to the next chapter.
   *
   * @param currentChapter the number of the current chapter.
   * @return the number of the current chapter, actualized.
   */
  override def advanceChapter(currentChapter: Int): Int = {
    val newChapter = currentChapter + 1
    newChapter
  }
}
