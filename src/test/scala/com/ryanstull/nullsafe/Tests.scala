package com.ryanstull.nullsafe

import org.scalatest.FlatSpec

/**
  * @author ryan
  * @since 12/5/18.
  */
class Tests extends FlatSpec {

	case class E(s: String)
	case class D(e: E)
	case class C(d: D)
	case class B(c: C)
	case class A(b: B)

	"Simple field access" should "not cause an NPE" in {

		val a = A(null)

		?(a.b.c)
	}

	"Deeply nested field access" should "not cause an NPE" in {
		val a = A(B(C(null)))

		?(a.b.c.d.e.s)
	}
}
