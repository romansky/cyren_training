// 1. Create a list containing all integers within 20-100

val list = (20 until 100).toList

// 2. Sum of said list
list.sum

// 3. Turn this list into Map(indice -> indice as string)
list.zipWithIndex.map {
  case (indice, value) ⇒ indice -> value
}.toMap

// 4. Write function that finds all duplicates in given list

val findDupsList = List("one","two","three","two","three")
def dupFinder(list: List[String]): List[String] =
  list.foldLeft(Map.empty[String, Int])({
    case (map: Map[String, Int], value: String) ⇒
      val count = map.getOrElse(value, 0) + 1
      map + (value → count)
  }).filter(_._2 > 1).keys.toList

var m = Map.empty[String,Int]

findDupsList.foreach {
  item ⇒
    val newValue: Int = m.getOrElse(item, 0) + 1
    m = m + (item → newValue)
}

m

def dupFinder2(list: List[String]): List[String] =
  list.groupBy(x ⇒ x).filter(_._2.length > 1).keys.toList

assert(dupFinder(findDupsList).length == 2)
assert(dupFinder2(findDupsList).length == 2)

// 5. see below

trait Tester[T] {

  def test(t: T): Boolean

}

def check[T](values: List[T], tester: Tester[T]): List[Boolean] =
  values.map(tester.test)

// for class TestMe, return the result within
// for Strings which are shorter than 5 chars
// for lists Strings which are shorter than 5 chars (using flatMap)
// for ints which are less than 100
//  + with _.map
//  + with for {}


// test cases

class TestMe(val x: Int){
  def result = x < 20
}

val testsMes = (0 until 100).map(i ⇒ new TestMe(i)).toList
val strings = List("a","aa","aaa","bbbbb","dddddddd","e","ff")
val strings2 = List(List("a"),List("aa"),List("aaa"),List("bbbbb"),List("dddddddd","e","ff"))
val ints = List(123,1234,1,55,12,15,400)

class TestMeTester extends Tester[TestMe]{
  override def test(t: TestMe): Boolean = t.result
}
class StringsTester extends Tester[String]{
  override def test(t: String): Boolean = t.length < 5
}

class StringsOfStringsListTester extends Tester[List[String]]{
  override def test(t: List[String]): Boolean =
    t.exists(_.length < 5)
}

class IntsTester extends Tester[Int]{
  override def test(i: Int): Boolean = i < 100
}

assert(check(testsMes, new TestMeTester()).count(_ == true) == 20)
assert(check(strings, new StringsTester()).count(_ == true) == 5)
assert(check(strings2, new StringsOfStringsListTester()).count(_ == true) == 4)
assert(check(ints, new IntsTester()).count(_ == true) == 4)
