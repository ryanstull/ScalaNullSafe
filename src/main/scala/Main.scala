

/**
  * @author ryan
  * @since 11/27/18.
  */
object Main {

	def main(args: Array[String]): Unit = {

		case class C(d: String)
		case class B(c: C)
		case class A(b: B)

		import NullSafe.?

		val a = null.asInstanceOf[A]

		val q = ?(a.b.c.d)

		println(q)
	}
}
