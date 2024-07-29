package cl.uchile.dcc.citric
package model

import model.Character.Wilds.{Chicken, RoboBall, Seagull, WildUnit}

import cl.uchile.dcc.citric.model.Character.PlayerCharacter

import scala.util.Random

class WildUnitTest extends munit.FunSuite {

  private val chicken1 = new Chicken
  private val roboball1 = new RoboBall
  private val seagull1 = new Seagull

  test("Creating a Chicken WildUnit") {
    assertEquals(chicken1.getName, "Chicken")
    assertEquals(chicken1.getCurrHP, 3)
    assertEquals(chicken1.getAttack, -1)
    assertEquals(chicken1.getDefense, -1)
    assertEquals(chicken1.getEvasion, 1)
    assertEquals(chicken1.victoryValueOnAbstractUnits(chicken1), 1)
  }

  test("Creating a Robo Ball WildUnit") {
    assertEquals(roboball1.getName, "RoboBall")
    assertEquals(roboball1.getCurrHP, 3)
    assertEquals(roboball1.getAttack, -1)
    assertEquals(roboball1.getDefense, 1)
    assertEquals(roboball1.getEvasion, -1)
    assertEquals(roboball1.victoryValueOnAbstractUnits(roboball1), 1)
  }

  test("Creating a Seagull WildUnit") {
    assertEquals(seagull1.getName, "Seagull")
    assertEquals(seagull1.getCurrHP, 3)
    assertEquals(seagull1.getAttack, 1)
    assertEquals(seagull1.getDefense, -1)
    assertEquals(seagull1.getEvasion, -1)
    assertEquals(seagull1.victoryValueOnAbstractUnits(seagull1), 1)
  }

  test("A WildUnit should be able to get the same attack rolls with a fixed seed") {
    val A = new Chicken(11)
    val B = new Chicken(11)
    for (_ <- 1 to 10) {
      assertEquals(A.DoAttack, B.DoAttack)
    }
  }

  test("WildUnit can be knocked out") {
    assertEquals(roboball1.getCurrHP, 3)
    assertEquals(roboball1.getStatus, false)
    roboball1.takeDamage(3)
    assertEquals(roboball1.getCurrHP, 0)
    assertEquals(roboball1.getStatus, true)
  }

  test("WildUnit can take damage") {
    assertEquals(chicken1.getCurrHP, 3)
    chicken1.takeDamage(1)
    assertEquals(chicken1.getCurrHP, 2)
  }

  test("WildUnit can earn stars") {
    chicken1.earnStars(1)
    val currentStars = chicken1.getStars
    assertEquals(currentStars, 4)
  }

  test("WildUnit can make an attack") {
    val attackValue = seagull1.DoAttack
    assertEquals(attackValue, 5)
  }

  test("WildUnit can defend from an attack") {
    val attackValue = 1
    val newAttackValue = roboball1.DoDefend(attackValue)
    assertEquals(newAttackValue, 1)

    val attackValue2 = 10
    val newAttackValue2 = chicken1.DoDefend(attackValue2)
    assertEquals(newAttackValue2, 8)
  }

  test("WildUnit can evade an attack") {
    val evade1 = chicken1.DoEvade(3)
    val evade2 = roboball1.DoEvade(10)
    assertEquals(evade1, true)
    assertEquals(evade2, false)
  }

  test("WildUnits can battle") {
    val ch1 = new PlayerCharacter("ch1", 5, 1, 1, 1, new Random(22))
    var atk: Int = 0

    atk = seagull1.DoAttack
    assertEquals(atk, 6)
    assertEquals(ch1.getCurrHP, 5)
    ch1.recieveAttack("defend", atk, seagull1)
    assertEquals(ch1.getCurrHP, 4)

    atk = ch1.DoAttack
    assertEquals(atk, 2)
    assertEquals(seagull1.getCurrHP, 3)
    seagull1.recieveAttack("defend", atk, ch1)
    assertEquals(seagull1.getCurrHP, 2)

    atk = seagull1.DoAttack
    assertEquals(atk, 5)
    assertEquals(ch1.getCurrHP, 4)
    ch1.recieveAttack("defend", atk, seagull1)
    assertEquals(ch1.getCurrHP, 3)

    atk = seagull1.DoAttack
    assertEquals(atk, 4)
    assertEquals(ch1.getCurrHP, 3)
    ch1.recieveAttack("defend", atk, seagull1)
    assertEquals(ch1.getCurrHP, 2)

    atk = seagull1.DoAttack
    assertEquals(atk, 6)
    assertEquals(ch1.getCurrHP, 2)
    ch1.recieveAttack("defend", atk, seagull1)
    assertEquals(ch1.getCurrHP, 1)

    assertEquals(seagull1.getStars, 2)
    assertEquals(ch1.getStars, 0)
    ch1.earnStars(5)
    assertEquals(ch1.getStars, 5)
    atk = seagull1.DoAttack
    assertEquals(atk, 4)
    assertEquals(ch1.getCurrHP, 1)
    ch1.recieveAttack("defend", atk, seagull1)
    assertEquals(ch1.getCurrHP, 0)
    assertEquals(seagull1.getStars, 7)
  }

  test("WildUnit can win an a battle") {
    assertEquals(chicken1.getStars, 4)
    assertEquals(roboball1.getStars, 2)
    chicken1.winBattle(roboball1)
    assertEquals(chicken1.getStars, 6)
  }
}
