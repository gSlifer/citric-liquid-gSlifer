package cl.uchile.dcc.citric
package model.Board

/**
 * The `GameBoard` object represents the board for a game of 99.7% Citric Liquid.
 *
 * The board comes already defined and resembles Practice Field from a similar title.
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
object GameBoard {
  /** Definition of a trait to simplify the creation of an array of panels. */
  private sealed trait PanelType
  private case object Neutral extends PanelType
  private case object Home extends PanelType
  private case object Bonus extends PanelType
  private case object Drop extends PanelType
  private case object Encounter extends PanelType
  private case object Empty extends PanelType

  /** Definition of panel positions in a grid made of arrays. */
  private val panelLayout: Array[Array[PanelType]] = Array(
    Array(Empty, Empty, Empty, Bonus, Neutral, Encounter, Empty, Empty, Empty),
    Array(Empty, Empty, Empty, Neutral, Empty, Bonus, Empty, Empty, Empty),
    Array(Empty, Empty, Home, Drop, Bonus, Neutral, Home, Empty, Empty),
    Array(Drop, Neutral, Bonus, Empty, Empty, Empty, Encounter, Neutral, Bonus),
    Array(Neutral, Empty, Neutral, Empty, Empty, Empty, Neutral, Empty, Neutral),
    Array(Bonus, Neutral, Encounter, Empty, Empty, Empty, Bonus, Neutral, Drop),
    Array(Empty, Empty, Home, Neutral, Bonus, Encounter, Home, Empty, Empty),
    Array(Empty, Empty, Empty, Bonus, Empty, Neutral, Empty, Empty, Empty),
    Array(Empty, Empty, Empty, Drop, Neutral, Bonus, Empty, Empty, Empty),
  )

  // Crea un tablero en función de la disposición de paneles

  /**
   * createBoard Method
   *
   * the method creates a board connecting all the selected panels
   * with the panels they have adjacent.
   *
   * @return the board with all the specified panels.
   */
  def createBoard(): Array[Array[Panel]] = {
    // board dimensions
    val numRows = panelLayout.length
    val numCols = panelLayout.headOption.map(_.length).getOrElse(0)

    // Create an array of panels based on the dimensions of the board
    val board: Array[Array[Panel]] = Array.ofDim[Panel](numRows, numCols)

    // Initializes panels based on the panel types specified in panelLayout
    for (i <- 0 until numRows) {
      for (j <- 0 until numCols) {
        panelLayout(i)(j) match {
          case Neutral => board(i)(j) = new NeutralPanel
          case Home => board(i)(j) = new HomePanel
          case Bonus => board(i)(j) = new BonusPanel
          case Drop => board(i)(j) = new DropPanel
          case Encounter => board(i)(j) = new EncounterPanel
          case Empty => board(i)(j) = null
        }
      }
    }

    // Connect existing panels
    for (i <- 0 until numRows) {
      for (j <- 0 until numCols) {
        val panel = board(i)(j)
        if (panel != null) {
          if (i > 0 && board(i - 1)(j) != null) panel.connectTo(board(i - 1)(j))
          if (i < numRows - 1 && board(i + 1)(j) != null) panel.connectTo(board(i + 1)(j))
          if (j > 0 && board(i)(j - 1) != null) panel.connectTo(board(i)(j - 1))
          if (j < numCols - 1 && board(i)(j + 1) != null) panel.connectTo(board(i)(j + 1))
        }
      }
    }

    board
  }
}
