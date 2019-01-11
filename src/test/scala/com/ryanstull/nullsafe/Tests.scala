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

	"asdm" should "asd" in {
		val a = A(null)
		println(opt(a.b.c.d.e.s).getOrElse("HELLO"))
	}

	"Simple field access" should "not cause an NPE" in {
		val a = A(null)

		assert(?(a.b.c) == null)
	}

	"Deeply nested field access" should "not cause an NPE" in {
		val a = A(B(C(null)))

		assert(?(a.b.c.d.e.s) == null)
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
