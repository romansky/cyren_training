// immutable map usage example

val initialConfig = Map("port" → "8080", "name" → "myServer")


object ConfigManager {

  type SubscriberFunc = (Map[String, String] ⇒ Unit)

  private var subscribers = List.empty[SubscriberFunc]

  def subscribeChanges(subscriber: SubscriberFunc) =
    subscribers = subscriber :: subscribers

  def updateConfig(newConfig: Map[String, String]) =
    subscribers.foreach {
      subscriber: SubscriberFunc ⇒ subscriber(newConfig)
    }

}


class ServerPortChanger {

  def changeServerPort(newPort: String) = ???

  def subsribeToConfig = ConfigManager
    .subscribeChanges {
      change: Map[String, String] ⇒
        change.get("port") match {
          case Some(newPort: String) ⇒ changeServerPort(newPort)
          case _ ⇒
        }
    }

}

var myLocalMap = Map.empty[String, String]


myLocalMap
ConfigManager.updateConfig(initialConfig)
myLocalMap

// times
val times: Int ⇒ Int ⇒ Int = { firstInt: Int ⇒
  val _firstInt = firstInt * 100000
  (anotherInt: Int) ⇒ _firstInt * anotherInt
}

val intiail = times(5)
intiail(10)
intiail(20)
