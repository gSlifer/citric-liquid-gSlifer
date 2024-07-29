package cl.uchile.dcc.citric
package model.observer

/** The `Observable` trait represents the base of a observable
 *
 * an observable is a class that is monitored by an observer
 * here, it will be used to check if the normaLevel of a PlayerCharacter reaches level 6
 *
 * @author [[https://github.com/gSlifer/ Nicolas Caceres P.]]
 */
trait Observable {
  /** Initialization of the list of observers, empty by default. */
  private var observers: List[Observer] = List()

  /**
   * addObserver Method
   *
   * method to add an observer to the list of observers.
   *
   * @param observer the observer to add.
   */
  def addObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }

  /**
   * getObservers Method
   *
   * method to get the list of observers.
   *
   * @return the list of observers.
   */
  def getObservers: List[Observer] = {
    observers
  }

  /**
   * notifyObservers Method
   *
   * method to notify changes to the observers.
   */
  def notifyObservers(): Unit = {
    observers.foreach(_.update())
  }
}
