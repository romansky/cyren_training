// 1. turn range 1..100 to be inside below case class
case class Holder(number: Int){
  def getNum = number + 100
}

//2. count the sum of .getNum of above list

//3. use pattern matching for above

//4. use .filter to find all numbers above 150

//5. do the same with pattern matching

//6. filter for all the people above the age of 34
// see classes below

case class Person(
                 name: String,
                 age: Int
                 )


val people = List(
 Person("moshe1", 30),
 Person("moshe2", 31),
 Person("moshe3", 32),
 Person("moshe4", 33),
 Person("moshe5", 34),
 Person("moshe6", 35),
 Person("moshe7", 36),
 Person("moshe8", 37),
 Person("moshe9", 38),
 Person("moshe10", 39),
 Person("moshe11", 40)
)

def findPeopleBelow34(in: List[Person]): List[Person] = ???
assert(findPeopleBelow34(people) == List(Person("moshe1", 30),
  Person("moshe2", 31),
  Person("moshe3", 32),
  Person("moshe4", 33)))

//7. increase the age of all  the people above by 1

//8. find all the people who have 4/5/6 in their name

def findPepleWith45or6InName(in: List[Person]): List[Person] = ???

assert(findPepleWith45or6InName(people) == List(
    Person("moshe4", 33),
    Person("moshe5", 34),
    Person("moshe6", 35)
))

//9. do (8) using regex (if you haven't done so already)

//10. do (8) using recursion + tail recursion



