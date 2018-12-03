/**
  * @author ryan
  * @since 11/27/18.
  */
object Main {

	def main(args: Array[String]): Unit = {

		case class A(s: String)
		case class B(a: A)

		import NullSafe.?

		val b = null.asInstanceOf[B]

		println(?(b.a))
	}
}
