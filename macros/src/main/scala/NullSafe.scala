/**
  * @author ryan
  * @since 11/27/18.
  */

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object NullSafe {

	def ?[A](a: A): A = macro nullSafeImpl[A]

	def nullSafeImpl[A: c.WeakTypeTag](c: blackbox.Context)(a: c.Expr[A]): c.Expr[A] = {
		import c.universe._

		println(show(a.tree))

		a
	}
}
