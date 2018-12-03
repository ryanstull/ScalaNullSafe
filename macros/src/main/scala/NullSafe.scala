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

		val first = firstTerm(c)(tree)
		val result = addNullCheck(c)(first,first)

		c.Expr(result)
	}

	private def firstTerm[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		tree match {
			case term @ (_:Select | _:Ident) => term
			case _ => throw new IllegalArgumentException()
		}
	}

	private def addNullCheck[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree,result: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		def wrapInNullCheck(tree: c.universe.Tree): c.universe.Tree = {
			q"if($tree != null) $result else null"
		}

		tree match {
			case Select(qualifier, _) =>
				val newResult = wrapInNullCheck(qualifier)
				addNullCheck(c)(qualifier,newResult)
			case i: Ident => wrapInNullCheck(i)
			case _ => throw new IllegalArgumentException()
		}
	}
}