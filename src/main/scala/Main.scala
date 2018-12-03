

/**
  * @author ryan
  * @since 11/27/18.
  */
object Main {

	case class C(d: String)
	case class B(c: C)
	case class A(b: B)

	def main(args: Array[String]): Unit = {

		import NullSafe.?

		val a = A(B(null))

		val z = ?(a)
		val y = ?(a.b)
		val x = ?(a.b.c)
		val w = ?(a.b.c.d)

		println(
			s"""
			  |$z
			  |$y
			  |$x
			  |$w
			""".stripMargin)
	}
}
