package cl.uchile.dcc.citric
package model

import model.Board.{BonusPanel, DropPanel, EncounterPanel, GameBoard, HomePanel, NeutralPanel, Panel}
import model.Character.PlayerCharacter

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class BoardTest extends munit.FunSuite {

  val NoCharacters: ArrayBuffer[PlayerCharacter] = ArrayBuffer.empty
  val NoPanels: ArrayBuffer[Panel] = ArrayBuffer.empty
  val player = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
  val gamePanel = new NeutralPanel

  test("Creating a GamePanel") {
    assertEquals(gamePanel.characters, NoCharacters)
    assertEquals(gamePanel.nextPanels, NoPanels)
  }

  test("Adding and removing characters from GamePanel") {
    gamePanel.addCharacter(player)
    assertEquals(gamePanel.characters, ArrayBuffer(player))

    gamePanel.removeCharacter(player)
    assertEquals(gamePanel.characters, NoCharacters)
  }

  test("Connecting GamePanels") {
    val panel1 = new NeutralPanel
    val panel2 = new NeutralPanel
    val panel3 = new NeutralPanel
    val arr1: ArrayBuffer[Panel] = ArrayBuffer.empty
    val arr2: ArrayBuffer[Panel] = ArrayBuffer.empty
    val arr3: ArrayBuffer[Panel] = ArrayBuffer.empty
    arr1 += panel2
    arr2 += panel1
    arr2 += panel3
    arr3 += panel2
    panel1.connectTo(panel2)
    panel2.connectTo(panel3)

    assertEquals(panel1.nextPanels, arr1)
    assertEquals(panel2.nextPanels, arr2)
    assertEquals(panel3.nextPanels, arr3)

    panel1.disconnectFrom(panel2)
    assertEquals(panel1.nextPanels, NoPanels)
  }

  test("activating a NeutralPanel") {
    val NPanel = new NeutralPanel
    val Nplayer = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    NPanel.activate(Nplayer)
  }

  test("activating a HomePanel") {
    val HPanel = new HomePanel
    val Hplayer = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    HPanel.activate(Hplayer)
    assertEquals(Hplayer.getCurrHP, 11)
    assertEquals(Hplayer.getNormaLevel, 1)
  }

  test("assign owner of a HomePanel") {
    val HPanel2 = new HomePanel
    val Hplayer2 = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    HPanel2.assignOwner(Hplayer2)
    val Howner = HPanel2.getOwner
    assertEquals(Howner, Hplayer2)
  }

  test("activating a EncounterPanel") {
    val EPanel = new EncounterPanel
    val Eplayer = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    EPanel.activate(Eplayer)
  }

  test("activating a DropPanel") {
    val DPanel = new DropPanel
    val Dplayer = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    Dplayer.earnStars(100)
    DPanel.activate(Dplayer)
    assertEquals(Dplayer.getStars, 99)
  }

  test("activating a BonusPanel") {
    val BPanel = new BonusPanel
    val Bplayer = new PlayerCharacter("Player1", 10, 2, 2, 2, new Random(11))
    BPanel.activate(Bplayer)
    assertEquals(Bplayer.getStars, 1)
  }
  test("Creating and connecting GameBoard") {
    val board = GameBoard.createBoard()
    assertEquals(board.length, 9)
    assertEquals(board(0).length, 9)

    val panel1 = board(0)(3)
    val connectedPanels1 = panel1.nextPanels
    assert(connectedPanels1.nonEmpty, "Panel 1 should be connected to other panels")
    assertEquals(connectedPanels1.length, 2)
    assert(connectedPanels1.contains(board(0)(4)), "Panel 1 should be connected to panel in the right")
    assert(connectedPanels1.contains(board(1)(3)), "Panel 1 should be connected to panel below")

    val panel2 = board(0)(4)
    val connectedPanels2 = panel2.nextPanels
    assert(connectedPanels2.nonEmpty, "Panel 2 should be connected to other panels")
    assertEquals(connectedPanels2.length, 2)
    assert(connectedPanels2.contains(board(0)(3)), "Panel 2 should be connected to panel in the left")
    assert(connectedPanels2.contains(board(0)(5)), "Panel 2 should be connected to panel in the right")
  }

  test("Access methods of panels in the board") {
    val board = GameBoard.createBoard()
    val newOwner = new PlayerCharacter("NuevoDueño", 100, 100, 100, 100)
    val panelAt_2_2 = board(2)(2)
    panelAt_2_2 match {
      case homePanel: HomePanel =>
        val newOwner = new PlayerCharacter("NuevoDueño", 100, 100, 100, 100)
        homePanel.assignOwner(newOwner)
        val Howner = homePanel.getOwner
        assertEquals(Howner, newOwner)
    }
  }
}
