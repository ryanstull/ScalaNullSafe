import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
  * @author ryan
  * @since 12/5/18.
  */
object NullSafe {

	def ?[A](expr: A): A = macro outer[A]

	def outer[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		import c.universe._


		val tree = expr.tree
		println(showRaw(tree))

		val result = addNullCheck(c)(tree,tree)

		println(result)

		c.Expr(result)
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
			case _ => result
		}
	}
}
