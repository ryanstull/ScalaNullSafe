package com.ryanstull.nullsafe

import org.scalatest.FlatSpec

import scala.language.implicitConversions

/**
  * @author ryan
  * @since 12/5/18.
  */
class Tests extends FlatSpec {

	import Tests._

	val a: A = A(null)
	def getAnA: A = A(null)
	def multiArgMeth(a: String, b: String): Boolean = a == b

	"This" should "not be checked for null" in {
		?(this.a)
	}

	"Null" should "not cause NPE" in {
		?(null)
	}

	"A single element" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a) eq a)
	}

	"A single element" should "return the right value" in {
		val a = A(null)

		assert(?(a) eq a)
	}

	"A single method call" should "not cause an NPE" in {
		assert(?(getAnA) != null)
	}

	"One field access" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.b) == null)
	}

	"One field access" should "return the right value" in {
		val b = B(null)
		val a = A(b)

		assert(?(a.b) eq b)
	}

	"Two field accesses" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.b.c) == null)
	}

	"Two field accesses" should "return the right value" in {
		val c = C(null)
		val a = A(B(c))

		assert(?(a.b.c) eq c)
	}

	"Deeply nested field access" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.b.c.d.e.s) == null)
	}

	"Deeply nested field access" should "return the right value" in {
		val string = "Hello"
		val a = A(B(C(D(E(string)))))

		assert(?(a.b.c.d.e.s) eq string)
	}

	"No-op method calls" should "not cause an NPE" in {
		val b = null.asInstanceOf[B]

		assert(?(b.getEmptyC) == null)
	}

	"Mixing field access and no-op method calls" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.b.getEmptyC.d.e) == null)
	}

	"Function calls with args" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.getB("")) == null)
	}

	"Mixing field access, various arities of function calls" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.getB("").getEmptyC.d.e.s) == null)
	}

	"Having a method call as the first element" should "not cause an NPE" in {
		assert(?(getAnA.b) == null)
	}

	"Constructors" should "not cause an NPE" in {
		?(new A(null).b.c)
	}

	"Static methods" should "not cause an NPE" in {
		import HasStaticMethod._

		?(staticGetString())
	}

	"Static methods followed by other accesses" should "not cause an NPE" in {
		import HasStaticMethod._

		?(staticGetString().concat("test"))
	}

	"Long package names" should "not cause NPE" in {
		?(System.out.println(1))
	}

	"Getting an subclass of anyval" should "not cause NPE" in {
		val a = A(B(C(D(null))))
		?(a.b.c.d.getInt)
	}

	"Expression of type unit" should "not cause NPE" in {
		val a: A = A(null)

		?(a.b.unit())
	}

	"Methods defined in the local scope" should "not cause NPE" in {
		val a = A(null)

		def getCFromB(b: B): C = ?(b.c)

		?(getCFromB(a.b).d)
	}

	"Implicit methods" should "not cause NPE" in {
		val a = A(null)

		implicit def getCFromB(b: B): C = ?(b.c)

		?(a.b.d)
	}

	"opt" should "work for non-null" in {
		val string = "Hello"

		val a = A(B(C(D(E(string)))))

		assert(opt(a.b.c.d.e.s).contains(string))
	}

	"opt" should "work for null" in {
		val a = A(B(C(D(E(null)))))

		assert(opt(a.b.c.d.e.s).isEmpty)
	}

	"opt(null)" should "equal None" in {
		assert(opt(null).isEmpty)
	}

	"Not null" should "work for non-null" in {
		val a = A(B(C(D(E(null)))))

		assert(notNull(a.b.c.d.e))
	}

	"Not null" should "work for null" in {
		val a = A(B(null))

		assert(!notNull(a.b.c.d.e))
	}

	"Using custom default" should "work for absent case" in {
		val a = A(B(null))

		assert(?(a.b.c.d.e.s,"") == "")
	}

	"Using custom default" should "work for present case" in {
		val a = A(B(C(D(E("Hello")))))

		assert(?(a.b.c.d.e.s,"") == "Hello")
	}

	"Using custom default" should "work for absent case with Int" in {
		val a = A(B(null))

		assert(?(a.b.c.d.getInt,new Integer(3)) == 3)
	}

	"Using custom default" should "work for present case with Int" in {
		val a = A(B(C(D(E("Hello")))))

		assert(?(a.b.c.d.getInt,new Integer(3)) == 0)
	}

	"Using custom default" should "work for absent case with Boolean" in {
		val a = A(B(null))

		assert(?(a.b.c.d.e.getBool,true) == true)
	}

	"Using custom default" should "work for present case with Boolean" in {
		val a = A(B(C(D(E("Hello")))))

		assert(?(a.b.c.d.e.getBool,true) == false)
	}

	"Using custom default" should "work for absent case with Double" in {
		val a = A(B(null))

		assert(?(a.b.c.d.e.getDouble,3.0) == 3.0)
	}

	"Using custom default" should "work for present case with Double" in {
		val a = A(B(C(D(E("Hello")))))

		assert(?(a.b.c.d.e.getDouble,3.0) == 0.0)
	}

	"Using custom default" should "work for absent case with Unit" in {
		val a = A(B(null))

		?(a.b.c.d.e.s.notify(),println("Absent"))
	}

	"??" should "return default NPE would have occurred" in {
		val a = A(B(null))

		assert(??(a.b.c.d.e.s)("Hello") == "Hello")
	}

	"??" should "return default when desired field is null" in {
		val a = A(B(C(D(E(null)))))

		assert(??(a.b.c.d.e.s)("Hello") == "Hello")
	}

	"??" should "return actual field when it is present" in {
		val a = A(B(C(D(E("There")))))

		assert(??(a.b.c.d.e.s)("Hello") == "There")
	}

	"??" should "return second A without drilldown" in {
		val a1 = null
		val a2 = A(B(null))

		assert(??(a1,a2)(A(null)) == A(B(null)))
	}

	"??" should "return second A with drilldown" in {
		val a1 = A(null)
		val a2 = A(B(C(null)))

		assert(??(a1.b.c,a2.b.c)(C(D(null))) == C(null))
	}

	"??" should "return default with drilldown" in {
		val a1: A = null
		val a2 = A(B(null))

		assert(??(a1.b.c.d.e.s,a2.b.c.d.e.s)("Hello") == "Hello")
	}

	"??" should "only evaluate the minimum number of necessary expressions" in {
		val a = A(null)
		val a2 = A(B(C(D(E("123")))))
		def throwExcep = throw new IllegalArgumentException

		??(a.b.c.d.e.s, a2.b.c.d.e.s, throwExcep)("AAA")
	}

	"Casting" should "not fail as the first transformation" in {
		val a: A = null

		?(a.asInstanceOf[A])
	}

	"Casting" should "not fail in the middle of a chain" in {
		val a: A = null

		?(a.b.c.d.e.s.asInstanceOf[String].charAt(2).*(2).toString.getBytes.hashCode())
	}

	"Casting" should "not fail when the data is actually there." in {
		val a: A = A(B(C(D(E("Hello")))))

		?(a.b.c.d.e.s.asInstanceOf[String].charAt(2).*(2))
	}

	"Injecting a local function call" should "work" in {
		val a: A = A(B(C(D(E("Hello")))))

		def useC(c: C): C = c

		?(useC(a.b.c).d.e.s)
	}

	"Injecting a local function call" should "work when null" in {
		val a: A = null

		def useC(c: C): C = c

		?(useC(a.b.c).d.e.s)
	}

	"Injecting a function call with an implicit" should "work" in {
		val a: A = A(B(C(D(E("Hello")))))

		?(a.b.c.d.e.s.nonEmpty)
	}

	"Injecting a method call" should "work" in {
		val a = A(B(null))

		?(a.getB(a.b.c.d.e.s).c)
	}

	"Multi-arg method" should "be null safe" in {
		val a = A(B(C(D(null))))

		?(multiArgMeth(a.b.c.d.e.s,a.b.c.d.e.s))
	}

	"Multi-arg method with predicate" should "be null safe" in {
		val a = A(B(C(null)))

		?(a.b.c.multiArgMeth(a.b.c.d.e.s,a.b.c.d.e.s))
	}

	"Nested function calls" should "allow null as input" in {
		def x(string: String): String = string
		def y(string: String): String = if (string == null) "default" else string
		def z(string: String): String = string

		assert(?(z(y(x(null)))) == "default")
	}
}

//Example of deeply nested domain object
object Tests {
	case class E(s: String){
		def getBool: Boolean = false
		def getDouble: Double = 0.0
	}
	case class D(e: E){
		def getE: E = E(null)
		def getInt: Int = 0
	}
	case class C(d: D){
		def multiArgMeth(s1: String, s2: String): Boolean = s1 == s2
	}
	case class B(c: C){
		def getEmptyC: C = C(null)
		def unit(): Unit = println("hello")
	}
	case class A(b: B){
		def getB(string: String) = B(null)
	}
}