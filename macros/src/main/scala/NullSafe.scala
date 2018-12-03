/**
  * @author ryan
  * @since 11/27/18.
  */

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object NullSafe {

	def ?[A](expr: A): A = macro outer[A]

	def outer[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		val tree = expr.tree

		val res = first(c)(tree)

		val res2 = addNullCheck(c)(res,res)

		c.Expr(res2)
	}

	def first[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		tree match {
			case select: Select => select
			case ident: Ident => ident
			case _ => throw new IllegalArgumentException()
		}
	}

	def addNullCheck[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree,result: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		tree match {
			case Select(qual, _) =>
				val t = q"if($qual != null) $result else null"
				addNullCheck(c)(qual,t)
			case i: Ident => q"if($i != null) $result else null"
		}
	}
}