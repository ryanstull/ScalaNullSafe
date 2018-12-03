

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
