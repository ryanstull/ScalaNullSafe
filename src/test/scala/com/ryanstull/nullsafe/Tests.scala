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

	"A single element" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		assert(?(a) eq a)
	}

	"A single element" should "return the right value" in {
		val a = A(null)

		assert(?(a) eq a)
	}

	"Simple field access" should "not cause an NPE" in {
		val a = A(null)

		assert(?(a.b.c) == null)
	}

	"Simple field access" should "return the right value" in {
		val c = C(null)
		val a = A(B(c))

		assert(?(a.b.c) eq c)
	}

	"Deeply nested field access" should "not cause an NPE" in {
		val a = A(B(C(null)))

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
		val a = A(null)

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
}
