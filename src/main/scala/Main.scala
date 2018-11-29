/**
  * @author ryan
  * @since 11/27/18.
  */
object Main {

	def main(args: Array[String]): Unit = {

		case class A(s: String)
		case class B(a: A)

		import NullSafe.?

		val m = Map("a" -> 2)

		val b = ?(m.get("a"))

		println(?(b))
	}
}
