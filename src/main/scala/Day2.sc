trait Moshe{

  var aList = List[String]()

  def addToList(item: String) = {
      aList ::= item
      aList
    }


}


object Moshe extends Moshe {


  def addToListCustom(aList: List[String]): List[String] = {
    var localList = List.empty[String]
    aList.foreach {
      item ⇒ localList ::= item
    }

    localList
  }

}
object Roman extends Moshe

Moshe.addToList("hat")
Moshe.aList
Roman.aList
Moshe.addToListCustom(List("one","two","three"))

class Test {
  final def test = "yay"
}


case class MyCaseClass(
                      name: String,
                      age: Int
                      ){

  def this(name: String) = this(name, 1)

}


MyCaseClass("name",1)

class MyClass(var param1: String) {
  // constructor

}

//Going functional

var factor = 3

val multi: Function1[Int, Int] = (num: Int) ⇒ num *factor
val l = List(1,2,3,4)
val a = l map multi
factor =5
val a2 = l map multi

// http://blog.kamkor.me/Covariance-And-Contravariance-In-Scala/


// flatmap

val listOfLists = List(List("a"), List("b"), List("c", List("c_inner")))
listOfLists.flatten




// fold left

List(1,2,3,4).foldRight(List(5)) {
  case (i, out) if i < 2 ⇒ i :: out
  case (i, out) ⇒ out
}


List(1,2,3,4).foldLeft(List.empty[Int]) {
  (out, i) ⇒ i :: out
}
















