/**
  * @author ryan
  * @since 12/5/18.
  */

import org.scalatest.FlatSpec


class FirstSpec extends FlatSpec {

	case class D(e: String){
		def getB(string: String): B = B(null)
	}
	case class C(d: D){
		def f(q: String): Null = null
	}
	case class B(c: C)
	case class A(b: B) {
		def getC: C = C(null)
	}

	"" should "" in {
		import NullSafe.?

		case class A(s: String)

		val a = A("")

		?(a)
	}
}
