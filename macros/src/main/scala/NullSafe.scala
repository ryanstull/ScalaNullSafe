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
			case term @ (_:Select | _:Ident | _:Apply) => term
			case _ => throw new IllegalArgumentException()
		}
	}

	private def addNullCheck[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree,result: c.universe.Tree): c.universe.Tree = {
		import c.universe._

		def returnResultIfNotNull(tree: c.universe.Tree): c.universe.Tree = {
			q"if($tree != null) $result else null"
		}

		tree match {
			case Apply(Select(qualifier, _), _) =>
				val newResult = returnResultIfNotNull(qualifier)
				addNullCheck(c)(qualifier,newResult)
			case Select(qualifier, _) =>
				val newResult = returnResultIfNotNull(qualifier)
				addNullCheck(c)(qualifier,newResult)
			case _: Ident => result
			case _ => throw new IllegalArgumentException()
		}
	}
}