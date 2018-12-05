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

	"Simple field access" should "not cause an NPE" in {

		val a = A(null)

		?(a.b.c)
	}

	"Deeply nested field access" should "not cause an NPE" in {
		val a = A(B(C(null)))

		?(a.b.c.d.e.s)
	}

	"No-op method calls" should "not cause an NPE" in {
		val b = null.asInstanceOf[B]

		?(b.getEmptyC)
	}

	"Mixing field access and no-op method calls" should "not cause an NPE" in {
		val a = A(null)

		?(a.b.getEmptyC.d.e)
	}

	"Function calls with args" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		?(a.getAB(""))
	}

	"Mixing field access, various arities of function calls" should "not cause an NPE" in {
		val a = null.asInstanceOf[A]

		?(a.getAB("").getEmptyC.d.e.s)
	}
}
