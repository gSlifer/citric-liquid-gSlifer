package cl.uchile.dcc.citric
package model

import model.Character.PlayerCharacter

import cl.uchile.dcc.citric.model.Character.Wilds.RoboBall
import cl.uchile.dcc.citric.model.Norma.NormaController

import scala.util.Random

class PlayerCharacterTest extends munit.FunSuite {
  private val name = "testPlayer"
  private val maxHp = 10
  private val attack = 1
  private val defense = 1
  private val evasion = 1
  private val randomNumberGenerator = new Random(11)
  private var character: PlayerCharacter = _
  override def beforeEach(context: BeforeEach): Unit = {
    character = new PlayerCharacter(
      name,
      maxHp,
      attack,
      defense,
      evasion,
      randomNumberGenerator
    )
  }

  private val controller = new NormaController

  test("A character should have correctly set their attributes") {
    assertEquals(character.getName, name)
    assertEquals(character.getCurrHP, maxHp)
    assertEquals(character.getAttack, attack)
    assertEquals(character.getDefense, defense)
    assertEquals(character.getEvasion, evasion)
  }

  // 1. Set a seed and test the result is always the same.
  // A seed sets a fixed succession of random numbers, so you can know that the next numbers
  // are always the same for the same seed.
  test("A character should be able to roll a dice with a fixed seed") {
    val other = new PlayerCharacter(name, maxHp, attack, defense, evasion, new Random(11))
    for (_ <- 1 to 10) {
      assertEquals(character.rollDice(), other.rollDice())
    }
  }

  // 2. Test invariant properties, e.g. the result is always between 1 and 6.
  test("A character should be able to roll a dice") {
    for (_ <- 1 to 10) {
      assert(character.rollDice >= 1 && character.rollDice <= 6)
    }
  }

  test("PlayerCharacter should have a default Random number generator") {
    val defaultCharacter = new PlayerCharacter(name, maxHp, attack, defense, evasion)
    assertNotEquals(character.getRNG, defaultCharacter.getRNG)
  }

  test("PlayerCharacter should have a default norm of 1") {
    val defaultNorma = character.getNormaLevel
    assertEquals(defaultNorma, 1)
  }

  test("PlayerCharacter should have a default norm objective") {
    val currentNormaObjective = character.getNormaObjective
    assertEquals(currentNormaObjective, "Stars")
  }

  test("PlayerCharacter can change norm objective") {
    val VictoryNormaObjective = "Victories"
    val StarsNormaObjective = "Stars"
    val InvalidNormaObjective = "XD"

    character.changeNormaObjective(VictoryNormaObjective)
    assertEquals(character.getNormaObjective, VictoryNormaObjective)

    character.changeNormaObjective(StarsNormaObjective)
    assertEquals(character.getNormaObjective, StarsNormaObjective)

    character.changeNormaObjective(InvalidNormaObjective)
    assertEquals(character.getNormaObjective, StarsNormaObjective)
  }

  test("PlayerCharacter should have default victories and stars of 0") {
    val defaultVictories = character.getVictories
    val defaultStars = character.getStars
    assertEquals(defaultVictories, 0)
    assertEquals(defaultStars, 0)
  }

  test("PlayerCharacter can win victories") {
    character.winVictory(1)
    val currentVictories = character.getVictories
    assertEquals(currentVictories, 1)
  }

  test("PlayerCharacter can earn stars") {
    character.earnStars(1)
    val currentStars = character.getStars
    assertEquals(currentStars, 1)
  }

  test("PlayerCharacter can upgrade norm level (victories)") {
    val VictoryNormaObjective = "Victories"
    character.changeNormaObjective(VictoryNormaObjective)

    character.winVictory(1)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 2)

    character.winVictory(2)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 3)

    character.winVictory(3)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 4)

    character.winVictory(4)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 5)

    character.winVictory(4)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 6)
  }

  test("PlayerCharacter can upgrade norm level (stars)") {
    character.earnStars(10)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 2)

    character.earnStars(20)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 3)

    character.earnStars(40)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 4)

    character.earnStars(50)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 5)

    character.earnStars(80)
    controller.checkNorma(character)
    assertEquals(character.getNormaLevel, 6)

  }

  test("PlayerCharacter with norm lv 6 should not have a required amount of stars/victories to lv up (max lv already)") {
    val testCh = new PlayerCharacter(name, maxHp, attack, defense, evasion, randomNumberGenerator)
    assertEquals(testCh.getNormaLevel, 1)
    testCh.upgradeNorma()
    testCh.upgradeNorma()
    testCh.upgradeNorma()
    testCh.upgradeNorma()
    testCh.upgradeNorma()
    assertEquals(testCh.getNormaLevel, 6)
    val over = controller.RequisitoStars(testCh)
    assertEquals(over, 999)
    val flow = controller.RequisitoVictories(testCh)
    assertEquals(flow, 999)
  }

  test("PlayerCharacter can fail to upgrade norm level (victories)") {
    val VictoryNormaObjective = "Victories"
    character.changeNormaObjective(VictoryNormaObjective)
    controller.checkNorma(character)
    val currentNorma = character.getNormaLevel
    assertEquals(currentNorma, 1)
  }

  test("PlayerCharacter can fail to upgrade norm level (stars)") {
    character.earnStars(5)
    controller.checkNorma(character)
    val currentNorma = character.getNormaLevel
    assertEquals(currentNorma, 1)
  }

  test("PlayerCharacter can take damage") {
    character.takeDamage(1)
    val newHP = character.getCurrHP
    assertEquals(newHP, 9)
  }

  test("PlayerCharacter can be KO") {
    character.takeDamage(10)
    val newHP = character.getCurrHP
    assertEquals(newHP, 0)
    val status = character.getStatus
    assertEquals(status, true)
  }

  test("PlayerCharacter can make an attack") {
    val attackValue = character.DoAttack
    assertEquals(attackValue, 6)
  }

  test("PlayerCharacter can defend from an attack") {
    val attackValue = character.DoAttack
    val newAttackValue = character.DoDefend(attackValue)
    assertEquals(newAttackValue, 1)

    val attackValue2 = 10
    val newAttackValue2 = character.DoDefend(attackValue2)
    assertEquals(newAttackValue2, 7)
  }

  test("PlayerCharacter can evade an attack") {
    val evade1 = character.DoEvade(3)
    val evade2 = character.DoEvade(10)
    assertEquals(evade1, true)
    assertEquals(evade2, false)
  }

  test("PlayerCharacters can battle") {
    val ch1 = new PlayerCharacter("ch1", maxHp, attack, defense, evasion, new Random(22))
    val ch2 = new PlayerCharacter("ch2", maxHp, attack, defense, 99, new Random(23))
    var atk: Int = 0

    atk = ch1.DoAttack
    assertEquals(atk, 6)
    assertEquals(ch2.getCurrHP, 10)
    ch2.recieveAttack("defend", atk, ch1)
    assertEquals(ch2.getCurrHP, 7)

    atk = ch2.DoAttack
    assertEquals(atk, 4)
    assertEquals(ch1.getCurrHP, 10)
    ch1.recieveAttack("evade", atk, ch2)
    assertEquals(ch1.getCurrHP, 6)

    atk = ch1.DoAttack
    assertEquals(atk, 6)
    assertEquals(ch2.getCurrHP, 7)
    ch2.recieveAttack("evade", atk, ch1)
    assertEquals(ch2.getCurrHP, 7)

    atk = ch2.DoAttack
    assertEquals(atk, 5)
    assertEquals(ch1.getCurrHP, 6)
    ch1.recieveAttack("defend", atk, ch2)
    assertEquals(ch1.getCurrHP, 5)

    atk = ch1.DoAttack
    assertEquals(atk, 5)
    assertEquals(ch2.getCurrHP, 7)
    ch2.recieveAttack("defend", atk, ch1)
    assertEquals(ch2.getCurrHP, 4)

    assertEquals(ch2.getStars, 0)
    assertEquals(ch2.getVictories, 0)
    assertEquals(ch1.getStars, 0)
    ch1.earnStars(5)
    assertEquals(ch1.getStars, 5)
    atk = ch2.DoAttack
    assertEquals(atk, 5)
    assertEquals(ch1.getCurrHP, 5)
    ch1.recieveAttack("evade", atk, ch2)
    assertEquals(ch1.getCurrHP, 0)
    assertEquals(ch2.getStars, 5)
    assertEquals(ch2.getVictories, 2)
  }

  test("PlayerCharacter can battle a WildUnit") {
    val OP = new PlayerCharacter("ch1", maxHp, 99, defense, evasion, randomNumberGenerator)
    assertEquals(OP.getStars, 0)
    assertEquals(OP.getVictories, 0)
    val robo = new RoboBall
    var atk: Int = 0

    atk = OP.DoAttack
    assertEquals(atk, 104)
    assertEquals(robo.getCurrHP, 3)
    robo.recieveAttack("defend", atk, OP)
    assertEquals(robo.getCurrHP, 0)

    assertEquals(OP.getStars, 2)
    assertEquals(OP.getVictories, 1)
  }

  test("PlayerCharacter can win an a battle") {
    assertEquals(character.getStars, 0)
    val robo = new RoboBall
    assertEquals(robo.getStars, 2)
    character.winBattle(robo)
    assertEquals(character.getStars, 2)
  }
}
