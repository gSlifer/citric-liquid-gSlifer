package cl.uchile.dcc.citric
package controller

import model.Board.{BonusPanel, HomePanel}
import model.Character.PlayerCharacter
import model.states._

import scala.util.Random

class StateTest extends munit.FunSuite {
  val gamecontrollertest = new GameController
  val player1 = new PlayerCharacter("Player1", 10, 1, 1, 1, new Random(1))
  val player2 = new PlayerCharacter("Player2", 10, 1, 1, 1, new Random(279))
  val player3 = new PlayerCharacter("Player3", 10, 1, 1, 1, new Random(3))
  val player4 = new PlayerCharacter("Player4", 10, 1, 1, 1, new Random(4))
  gamecontrollertest.addPlayer(player1)
  gamecontrollertest.addPlayer(player2)
  gamecontrollertest.addPlayer(player3)
  gamecontrollertest.addPlayer(player4)

  test("Test start of a game, the turns are decided randomly") {
    assertEquals(gamecontrollertest.getPlayers.size, 4)
    gamecontrollertest.controllerStartGame()

    val listOfPlayers: List[PlayerCharacter] = List(player1, player2, player3, player4)
    assertNotEquals(gamecontrollertest.getPlayers, listOfPlayers)
    //the seed is set so the order is consistent
    //this prevents the test failing in case the shuffle leaves the list the same
    assertEquals(gamecontrollertest.getPlayers(0), player4)
    assertEquals(gamecontrollertest.getPlayers(1), player2)
    assertEquals(gamecontrollertest.getPlayers(2), player1)
    assertEquals(gamecontrollertest.getPlayers(3), player3)
  }


  test("Test start of a game, all players have a starting point(home)") {
    gamecontrollertest.startingpoint1 match {
      case homePanel: HomePanel =>
        val owner1 = homePanel.getOwner
        assertEquals(gamecontrollertest.getPlayers(0), owner1)
    }
    gamecontrollertest.startingpoint2 match {
      case homePanel: HomePanel =>
        val owner2 = homePanel.getOwner
        assertEquals(gamecontrollertest.getPlayers(1), owner2)
    }
    gamecontrollertest.startingpoint3 match {
      case homePanel: HomePanel =>
        val owner3 = homePanel.getOwner
        assertEquals(gamecontrollertest.getPlayers(2), owner3)
    }
    gamecontrollertest.startingpoint4 match {
      case homePanel: HomePanel =>
        val owner4 = homePanel.getOwner
        assertEquals(gamecontrollertest.getPlayers(3), owner4)
    }
  }

  test("Test start of a game, all players have a observer assigned") {
    val observersOf1 = player1.getObservers.head
    assertEquals(observersOf1.isInstanceOf[GameController], true)

    val observersOf2 = player2.getObservers.head
    assertEquals(observersOf2.isInstanceOf[GameController], true)

    val observersOf3 = player3.getObservers.head
    assertEquals(observersOf3.isInstanceOf[GameController], true)

    val observersOf4 = player4.getObservers.head
    assertEquals(observersOf4.isInstanceOf[GameController], true)

    assertEquals(gamecontrollertest.state.isInstanceOf[ChapterStartState], true)
  }

  test("Test start of a game, transition to ChapterStartState") {
    assertEquals(gamecontrollertest.state.isInstanceOf[ChapterStartState], true)
  }

  test("Test start of a chapter, chapter number increases") {
    gamecontrollertest.controllerStartChapter()
    assertEquals(gamecontrollertest.getCurrentChapter, 1)
  }

  test("Test start of a chapter, transition to PlayerTurnState") {
    assertEquals(gamecontrollertest.state.isInstanceOf[PlayerTurnState], true)
  }

  test("Test to move a character in the board") {
    gamecontrollertest.controllerGoMove()
    val cpi = gamecontrollertest.getPlayerIndex
    //the position of the player after the move should be different than the starting point
    assertNotEquals(gamecontrollertest.startingpoint1, gamecontrollertest.getPosition(cpi))
  }

  test("Test end of move phase, transition to ActionsState") {
    assertEquals(gamecontrollertest.state.isInstanceOf[ActionsState], true)
  }

  test("Realize actions in the board, transition to End of turn for the current player") {
    gamecontrollertest.controllerDoActions()
    assertEquals(gamecontrollertest.state.isInstanceOf[TurnEndState], true)
  }

  test("Go nextTurn, this should start a new player turn since the player 1 turn just finished") {
    gamecontrollertest.controllerGoNextTurn()
    assertEquals(gamecontrollertest.state.isInstanceOf[PlayerTurnState], true)
  }

  test("turn of the second player, this player should be different from the previous one") {
    assertNotEquals(gamecontrollertest.currentPlayer, gamecontrollertest.getPlayers.head)
  }

  test("turn of the second player, we force him to go recover") {
    gamecontrollertest.currentPlayer.takeDamage(10)
    assertEquals(gamecontrollertest.currentPlayer.getCurrHP, 0)
    assertEquals(gamecontrollertest.currentPlayer.getStatus, true)
    gamecontrollertest.controllerGoRecover()
    assertEquals(gamecontrollertest.state.isInstanceOf[RecoveryState], true)
  }

  test("turn of the second player, we do a recovery check") {
    // in normal circumstances a player should wait to the next turn for a recovery check
    gamecontrollertest.controllerTryRecover()
    // the recovery should transition to end of the turn or move state
    assertEquals(gamecontrollertest.state.isInstanceOf[RecoveryState], false)
    // when it succeeds, it goes to the move phase and immediately to the actions phase
    assertEquals(gamecontrollertest.state.isInstanceOf[ActionsState], true)

    gamecontrollertest.controllerDoActions()
    gamecontrollertest.controllerGoNextTurn()
  }

  test("turn of the third player, we force him to go recover") {
    gamecontrollertest.currentPlayer.takeDamage(10)
    assertEquals(gamecontrollertest.currentPlayer.getCurrHP, 0)
    assertEquals(gamecontrollertest.currentPlayer.getStatus, true)
    gamecontrollertest.controllerGoRecover()
    assertEquals(gamecontrollertest.state.isInstanceOf[RecoveryState], true)
  }

  test("turn of the third player") {
    gamecontrollertest.controllerTryRecover()
    // the recovery should transition to end of the turn or move state
    assertEquals(gamecontrollertest.state.isInstanceOf[RecoveryState], false)
    // when it fails, it goes to the next turn
    assertEquals(gamecontrollertest.state.isInstanceOf[PlayerTurnState], true)
  }

  test("turn of the fourth player") {
    gamecontrollertest.controllerGoMove()
    gamecontrollertest.controllerDoActions()
    gamecontrollertest.controllerGoNextTurn()
    // this time when the turn ends, it transitions to chapter start
    assertEquals(gamecontrollertest.state.isInstanceOf[ChapterStartState], true)
  }

  test("Test start of a chapter, chapter number increases") {
    gamecontrollertest.controllerStartChapter()
    assertEquals(gamecontrollertest.getCurrentChapter, 2)
    assertEquals(gamecontrollertest.state.isInstanceOf[PlayerTurnState], true)
  }

  test("turn of the first player (again)") {
    gamecontrollertest.controllerGoMove()
    gamecontrollertest.controllerDoActions()
    gamecontrollertest.controllerGoNextTurn()
    assertEquals(gamecontrollertest.state.isInstanceOf[PlayerTurnState], true)
  }

  test("forcing a player to win to test observer"){
    val wingamecontrollertest = new GameController
    val player5 = new PlayerCharacter("Player5", 100, 1, 1, 1, new Random(10))
    val player6 = new PlayerCharacter("Player6", 100, 1, 1, 1, new Random(20))
    val player7 = new PlayerCharacter("Player7", 100, 1, 1, 1, new Random(30))
    val player8 = new PlayerCharacter("Player8", 100, 1, 1, 1, new Random(40))
    wingamecontrollertest.addPlayer(player5)
    wingamecontrollertest.addPlayer(player6)
    wingamecontrollertest.addPlayer(player7)
    wingamecontrollertest.addPlayer(player8)
    wingamecontrollertest.state.assignObservers(wingamecontrollertest.getPlayers)

    val imaginaryHome = new HomePanel
    player5.earnStars(201)
    imaginaryHome.activate(player5)
    imaginaryHome.activate(player5)
    imaginaryHome.activate(player5)
    imaginaryHome.activate(player5)
    imaginaryHome.activate(player5)
    imaginaryHome.activate(player5)
    assertEquals(player5.getNormaLevel, 6)

    assertEquals(wingamecontrollertest.state.isInstanceOf[GameEndState], true)
    wingamecontrollertest.state.EndGameMsg()
  }

  test("testing existence of default currentplayer") {
    val emptygamecontrollertest = new GameController
    val default = emptygamecontrollertest.currentPlayer
    assertEquals(default.getName,"")
  }

  test("testing methods outside expected state") {
    val emptygamecontrollertest = new GameController

    emptygamecontrollertest.state.startChapter()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.endPlayerTurns()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.goRecover()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.goMove()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.endCurrentTurn()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.endMove()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.nextPlayerTurn()
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    emptygamecontrollertest.state.goNext(0)
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)

    emptygamecontrollertest.state.advanceChapter(100)
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    assertEquals(emptygamecontrollertest.getCurrentChapter, 0)

    val playerx = new PlayerCharacter("Player", 1, 1, 1, 1, new Random(1))
    playerx.takeDamage(1)
    assertEquals(playerx.getStatus, true)
    emptygamecontrollertest.state.recoveryCheck(playerx)
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    assertEquals(playerx.getStatus, true)

    val panelx = new BonusPanel
    emptygamecontrollertest.state.act(playerx, panelx)
    assertEquals(emptygamecontrollertest.state.isInstanceOf[GameStartState], true)
    assertEquals(playerx.getStars, 0)

    emptygamecontrollertest.state.EndGameMsg()
  }

  test("testing methods outside expected state (2)") {
    val emptygamecontrollertest2 = new GameController
    emptygamecontrollertest2.addPlayer(player1)
    emptygamecontrollertest2.addPlayer(player2)
    emptygamecontrollertest2.addPlayer(player3)
    emptygamecontrollertest2.addPlayer(player4)
    emptygamecontrollertest2.controllerStartGame()
    assertEquals(emptygamecontrollertest2.state.isInstanceOf[ChapterStartState], true)

    emptygamecontrollertest2.controllerStartGame()
    assertEquals(emptygamecontrollertest2.state.isInstanceOf[ChapterStartState], true)

    val players = emptygamecontrollertest2.getPlayers
    val players2 = emptygamecontrollertest2.state.shufflePlayers(emptygamecontrollertest2.getPlayers)
    assertEquals(players, players2)

    val initialposition = emptygamecontrollertest2.getPosition(0)
    emptygamecontrollertest2.state.movePlayer(5, 0, initialposition)
    assertEquals(emptygamecontrollertest2.getPosition(0), initialposition)
  }

  test("testing movement and gameflow"){
    val movinggamecontoller = new GameController
    val player5 = new PlayerCharacter("Player5", 100, 1, 1, 1, new Random(10))
    val player6 = new PlayerCharacter("Player6", 100, 1, 1, 1, new Random(20))
    val player7 = new PlayerCharacter("Player7", 100, 1, 1, 1, new Random(30))
    val player8 = new PlayerCharacter("Player8", 100, 1, 1, 1, new Random(40))
    movinggamecontoller.addPlayer(player5)
    movinggamecontoller.addPlayer(player6)
    movinggamecontoller.addPlayer(player7)
    movinggamecontoller.addPlayer(player8)
    movinggamecontoller.controllerStartGame()

    // we try running the game for 20 chapters in order to make sure the game works correctly
    // and all options of action and move are tested
    val rounds = 20
    for (_ <- 1 to rounds) {
      movinggamecontoller.controllerStartChapter()

      movinggamecontoller.controllerGoMove()
      movinggamecontoller.controllerDoActions()
      movinggamecontoller.controllerGoNextTurn()

      movinggamecontoller.controllerGoMove()
      movinggamecontoller.controllerDoActions()
      movinggamecontoller.controllerGoNextTurn()

      movinggamecontoller.controllerGoMove()
      movinggamecontoller.controllerDoActions()
      movinggamecontoller.controllerGoNextTurn()

      movinggamecontoller.controllerGoMove()
      movinggamecontoller.controllerDoActions()
      movinggamecontoller.controllerGoNextTurn()
    }
    assertEquals(movinggamecontoller.getCurrentChapter, 20)
  }
}
