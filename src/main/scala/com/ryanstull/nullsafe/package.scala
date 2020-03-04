package com.ryanstull

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
	* @author ryan
	* @since 12/5/18.
	*/
package object nullsafe {

	import MacroImplementations._

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Byte): java.lang.Byte = macro qMarkImpl[java.lang.Byte]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Byte, default: java.lang.Byte): java.lang.Byte = macro qMarkImplDefault[java.lang.Byte]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Short): java.lang.Short = macro qMarkImpl[java.lang.Short]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Short, default: java.lang.Short): java.lang.Short = macro qMarkImplDefault[java.lang.Short]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Char): java.lang.Character = macro qMarkImpl[java.lang.Character]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Char, default: java.lang.Character): java.lang.Character = macro qMarkImplDefault[java.lang.Character]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Int): java.lang.Integer = macro qMarkImpl[java.lang.Integer]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Int, default: java.lang.Integer): java.lang.Integer = macro qMarkImplDefault[java.lang.Integer]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Long): java.lang.Long = macro qMarkImpl[java.lang.Long]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Long, default: java.lang.Long): java.lang.Long = macro qMarkImplDefault[java.lang.Long]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Float): java.lang.Float = macro qMarkImpl[java.lang.Float]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Float, default: java.lang.Float): java.lang.Float = macro qMarkImplDefault[java.lang.Float]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Double): java.lang.Double = macro qMarkImpl[java.lang.Double]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Double, default: java.lang.Double): java.lang.Double = macro qMarkImplDefault[java.lang.Double]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Boolean): java.lang.Boolean = macro qMarkImpl[java.lang.Boolean]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Boolean, default: java.lang.Boolean): java.lang.Boolean = macro qMarkImplDefault[java.lang.Boolean]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?[A <: AnyRef](expr: A): A = macro qMarkImpl[A]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @tparam A Type of the expression
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?[A](expr: A, default: A): A = macro qMarkImplDefault[A]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Unit): Unit = macro qMarkUnitImpl[Unit]
	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Unit, default: Unit): Unit = macro qMarkImplDefault[Unit]

	/**
		* A <a href="https://en.wikipedia.org/wiki/Null_coalescing_operator">null-coalesce operator</a>. You can provide a
	  	* variable number of expressions in the first param-list and a default value in the second list. It will return the
	  	* first non-null value going from left to right.  What's special about this though, is that all of the expressions
	  	* are translated to be null safe.  So you can just provide `a.b.c` as an expression, without worrying if `a` or `b` is null.
		*
		* @param exprs A variable length number of expressions that might be null
		* @param default Default value to return if all the expressions are null or would have thrown and NPE.
		* @tparam A Type of the expression
		* @return The first non-null value going from left to right.
		*/
	def ??[A <: AnyRef](exprs: A*)(default: A): A = macro doubleQMarkImpl[A]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return The value of the expression wrapped in Some. If the expression is null, or if there would have been a
		*         NullPointerException due to method/field access on `null`, returns None instead.
		*/
	def opt[A](expr: A): Option[A] = macro optImpl[A]

	/**
		*	Translates an expression that could cause a NullPointerException due to method/field access on `null`
		*	and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return `true` if the value of the expression is not null and and there wouldn't have been any NullPointerExceptions
		*        due to method/field access on `null`, false otherwise.
		*/
	def notNull[A](expr: A): Boolean = macro notNullImpl[A]

	//Putting the implementations in an object to avoid namespace pollution.
	private[this] object MacroImplementations {

		def qMarkImpl[A : c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"null", checkLast = false, a => a)
			c.Expr(result)
		}

		def qMarkImplDefault[A : c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A], default: c.Expr[A]): c.Expr[A] = {
			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(default.tree, checkLast = false, a => a)
			c.Expr(result)
		}

		def qMarkUnitImpl[A : c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"()", checkLast = false, a => a)
			c.Expr(result)
		}

		def doubleQMarkImpl[A: c.WeakTypeTag](c: blackbox.Context)(exprs: c.Expr[A]*)(default: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			val safeTrees = exprs.map(expr => rewriteToNullSafe(c)(expr.tree)(q"null", checkLast = true, a => a))

			val identsWithVals = safeTrees.map { safeTree =>
				val freshNameTerm = TermName(c.freshName)
				(Ident(freshNameTerm), ValDef(Modifiers(Flag.SYNTHETIC), freshNameTerm, TypeTree(safeTree.tpe), safeTree))
			}

			def genIfBlock(idents: Seq[Ident]): Tree = {
				val headExpr = c.Expr(idents.head)
				reify {
					if (headExpr.splice != null) headExpr.splice
					else c.Expr(if(idents.size > 1) genIfBlock(idents.tail) else default.tree).splice
				}.tree
			}

			c.Expr(Block(
				identsWithVals.map(_._2) :+ genIfBlock(identsWithVals.map(_._1)): _*
			))
		}

		def optImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Option[A]] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"None", checkLast = false, a => q"Option($a)")
			c.Expr[Option[A]](result)
		}

		def notNullImpl[A : c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Boolean] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"false", checkLast = false, a => q"$a != null")
			c.Expr[Boolean](result)
		}

		private def rewriteToNullSafe[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree)
		 	(default: c.universe.Tree, checkLast: Boolean = false, finalTransform: c.universe.Tree => c.universe.Tree): c.universe.Tree = {
			import c.universe._

			import mutable.{Queue => MQueue}

			def isPackageOrModule(sub: Tree): Boolean = {
				val sym = sub.symbol

				sym != null &&
					sym.isModule || sym.isModuleClass ||
					sym.isPackage || sym.isPackageClass
			}

			def decomposeExp(tree: Tree): (Tree , MQueue[Tree => Tree]) = {

				def isAnyRef(sub: Tree): Boolean = sub.tpe <:< typeOf[AnyRef]

				def nullable(tree: Tree): Boolean = isAnyRef(tree) && !isPackageOrModule(tree)

				def loop(tree: Tree, accumulator: (Tree, MQueue[Tree => Tree]) = (null,MQueue.empty)): (Tree, MQueue[Tree => Tree]) = {
					tree match {
						case t @ (
							_: Literal | _:Ident | _:This | //These shouldn't be further decomposed
							Apply(Select(New(_), _), _)  |  //Neither should constructors
							Select(_ :This, _) | Apply(Select(_ :This, _), _) | Apply(_ :This, _) //Shouldn't check 'this' for null
							) => (t, MQueue.empty)
						case t if t.symbol.isStatic => (t, MQueue.empty) //Static methods calls shouldn't be decomposed
						case t @ Select(qualifier, _) if isPackageOrModule(qualifier) => (t, MQueue.empty) //Nor Selects from packages
						case Select(qualifier, predName) =>
							val res = loop(qualifier, accumulator)
							res._2 += ((qual: Tree) => Select(qual,predName))
							res
						case Apply(fun: Ident, List(arg)) =>
							val res = loop(arg, accumulator)
							res._2 += ((qual: Tree) => Apply(fun, List(qual)))
							res
						case Apply(Select(qualifier, predicate), args) =>
							val res = loop(qualifier, accumulator)
							res._2 += ((qual: Tree) => Apply(Select(qual, predicate), args))
							res
						case _ => throw new IllegalArgumentException
					}
				}
				loop(tree)
			}

			def buildIfGuardedExp(prefix: Tree,selects: MQueue[Tree => Tree]): Tree = {
				var checkFinal = checkLast

				def needsCaching(tree: Tree): Boolean = {
					val sym = tree.symbol

					sym.isMethod || (tree match {
						case Select(qual, _) if !isPackageOrModule(qual) => true
						case _ => false
					})
				}

				def newVal(prefix: Tree): Tree = {
					val baseTermName = TermName(c.freshName)
					val ident = Ident(baseTermName)

					Block(
						ValDef(Modifiers(Flag.SYNTHETIC), baseTermName, TypeTree(prefix.tpe), prefix),
						ifStatement(ident)
					)
				}

				def ifStatement(prefix: Tree): Tree = reify {
					if (c.Expr(prefix).splice != null) {
						c.Expr(next(prefix)).splice
					} else c.Expr(default).splice
				}.tree

				def next(prefix: Tree): Tree = {
					val nextTree = if (selects.nonEmpty) selects.dequeue().apply(prefix) else prefix

					if(selects.isEmpty && !checkFinal){
						finalTransform(nextTree)
					} else {
						if(selects.isEmpty)
							checkFinal = false
						newVal(nextTree)
					}
				}

				if(selects.isEmpty) finalTransform(prefix)
				else if (needsCaching(prefix)) newVal(prefix)
				else ifStatement(prefix)
			}

			val (baseTerm, selections) = decomposeExp(tree)
			buildIfGuardedExp(baseTerm,selections)
		}
	}
}