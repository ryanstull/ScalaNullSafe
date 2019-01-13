package com.ryanstull.nullsafe

import org.scalatest.FlatSpec

/**
  * @author ryan
  * @since 12/5/18.
  */
class Tests extends FlatSpec {

	case class E(s: String)
	case class D(e: E){
		def getE: E = E(null)
	}
	case class C(d: D)
	case class B(c: C){
		def getEmptyC: C = C(null)
	}
	case class A(b: B){
		def getAB(string: String) = B(null)
	}

	def getAnA: A = A(null)

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

		assert(?(a.getAB("")) == null)
	}

	"Mixing field access, various arities of function calls" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a.getAB("").getEmptyC.d.e.s) == null)
	}

	"A simple method call" should "not cause an NPE" in {
		assert(?(getAnA).isInstanceOf[A])
	}

	"Having a method call as the first element" should "not cause an NPE" in {
		assert(?(getAnA.b) == null)
	}

	"Constructors" should "not cause an NPE" in {
		?(new A(null).b.c)
	}

}