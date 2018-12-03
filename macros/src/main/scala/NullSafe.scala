/**
  * @author ryan
  * @since 11/27/18.
  */

import scala.language.experimental.macros
import scala.reflect.macros.blackbox


object NullSafe {

	def ?[A](expr: A): A = macro outer[A]

	def outer[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		val res = inner(c)(expr.tree)
		c.Expr(res)
	}

	private def inner[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		tree match {
			case s @ Select(qualifier,_) =>
				If(q"$qualifier != null",
					s,
					Literal(Constant(null))
				)
			case _ => tree
		}
	}

}