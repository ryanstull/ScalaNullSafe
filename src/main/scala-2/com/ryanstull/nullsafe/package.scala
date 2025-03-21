package com.ryanstull

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
	* @author ryan
	* @since 12/5/18.
	*/
package object nullsafe {

	import MacroImplementations._

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Byte): java.lang.Byte = macro qMarkImpl[java.lang.Byte]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Byte, default: java.lang.Byte): java.lang.Byte = macro qMarkImplDefault[java.lang.Byte]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Short): java.lang.Short = macro qMarkImpl[java.lang.Short]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Short, default: java.lang.Short): java.lang.Short = macro qMarkImplDefault[java.lang.Short]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Char): java.lang.Character = macro qMarkImpl[java.lang.Character]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Char, default: java.lang.Character): java.lang.Character = macro qMarkImplDefault[java.lang.Character]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Int): java.lang.Integer = macro qMarkImpl[java.lang.Integer]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Int, default: java.lang.Integer): java.lang.Integer = macro qMarkImplDefault[java.lang.Integer]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Long): java.lang.Long = macro qMarkImpl[java.lang.Long]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Long, default: java.lang.Long): java.lang.Long = macro qMarkImplDefault[java.lang.Long]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Float): java.lang.Float = macro qMarkImpl[java.lang.Float]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Float, default: java.lang.Float): java.lang.Float = macro qMarkImplDefault[java.lang.Float]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Double): java.lang.Double = macro qMarkImpl[java.lang.Double]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Double, default: java.lang.Double): java.lang.Double = macro qMarkImplDefault[java.lang.Double]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Boolean): java.lang.Boolean = macro qMarkImpl[java.lang.Boolean]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?(expr: Boolean, default: java.lang.Boolean): java.lang.Boolean = macro qMarkImplDefault[java.lang.Boolean]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?[A <: AnyRef](expr: A): A = macro qMarkImpl[A]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @param default Custom default value to return in the case where NPE would have occurred.
		* @tparam A Type of the expression
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns `default` instead.
		*/
	def ?[A](expr: A, default: A): A = macro qMarkImplDefault[A]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @return The value of the expression. If there would have been a NullPointerException due to method/field
		*         access on `null`, returns null instead.
		*/
	def ?(expr: Unit): Unit = macro qMarkUnitImpl[Unit]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
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
		* @param exprs   A variable length number of expressions that might be null
		* @param default Default value to return if all the expressions are null or would have thrown and NPE.
		* @tparam A Type of the expression
		* @return The first non-null value going from left to right.
		*/
	def ??[A <: AnyRef](exprs: A*)(default: A): A = macro doubleQMarkImpl[A]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return The value of the expression wrapped in Some. If the expression is null, or if there would have been a
		*         NullPointerException due to method/field access on `null`, returns None instead.
		*/
	def opt[A](expr: A): Option[A] = macro optImpl[A]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return `true` if the value of the expression is not null and there wouldn't have been any NullPointerExceptions
		*         due to method/field access on `null`, false otherwise.
		*/
	def notNull[A](expr: A): Boolean = macro notNullImpl[A]

	/**
		* Translates an expression that could cause a NullPointerException due to method/field access on `null`
		* and adds explicit null-checks to avoid that.
		*
		* @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
		* @tparam A Type of the expression
		* @return `true` if the value of the expression is null or there would have been any NullPointerExceptions
		*         due to method/field access on `null`, false otherwise.
		*/
	def isNull[A](expr: A): Boolean = macro isNullImpl[A]

	def debugMaco[A](expr: A): A = macro debugMacoImpl[A]

	//Putting the implementations in an object to avoid namespace pollution.
	private[this] object MacroImplementations {

		def debugMacoImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
			import c.universe._
			println(showRaw(expr.tree))
			expr
		}

		def qMarkImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"null", checkLast = false, a => a)
			c.Expr(result)
		}

		def qMarkImplDefault[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A], default: c.Expr[A]): c.Expr[A] = {
			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(default.tree, checkLast = false, a => a)
			c.Expr(result)
		}

		def qMarkUnitImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"()", checkLast = false, a => a)
			c.Expr(result)
		}

		def doubleQMarkImpl[A: c.WeakTypeTag](c: blackbox.Context)(exprs: c.Expr[A]*)(default: c.Expr[A]): c.Expr[A] = {
			import c.universe._

			def genValue(exprs: Seq[Tree]): Tree = {
				if (exprs.nonEmpty) {
					val head = exprs.head
					val safeTree = rewriteToNullSafe(c)(head)(q"null", checkLast = false, a => a)

					val freshNameTerm = TermName(c.freshName)
					val (identExpr, valDef) = (c.Expr(Ident(freshNameTerm)), ValDef(Modifiers(Flag.SYNTHETIC), freshNameTerm, TypeTree(safeTree.tpe), safeTree))

					Block(
						valDef,
						reify {
							if (identExpr.splice != null) identExpr.splice
							else c.Expr(genValue(exprs.tail)).splice
						}.tree
					)
				} else default.tree
			}

			c.Expr(
				genValue(exprs.map(_.tree))
			)
		}

		def optImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Option[A]] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"None", checkLast = false, a => q"Option($a)")
			c.Expr[Option[A]](result)
		}

		def notNullImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Boolean] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"false", checkLast = false, a => q"$a != null")
			c.Expr[Boolean](result)
		}

		def isNullImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Boolean] = {
			import c.universe._

			val tree = expr.tree
			val result = rewriteToNullSafe(c)(tree)(q"true", checkLast = false, a => q"$a == null")
			c.Expr[Boolean](result)
		}

		private def rewriteToNullSafe[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree)
																									 (default: c.universe.Tree, checkLast: Boolean = false, finalTransform: c.universe.Tree => c.universe.Tree): c.universe.Tree = {
			import c.universe._

			def isPackageOrModule(sub: Tree): Boolean = {
				val sym = sub.symbol

				sym != null &&
					sym.isModule || sym.isModuleClass ||
					sym.isPackage || sym.isPackageClass
			}

			def decomposeExp(tree: Tree): (Tree, ListBuffer[Tree => Tree]) = {

				@tailrec
				def loop(tree: Tree, transformations: ListBuffer[Tree => Tree] = ListBuffer.empty, canFoldInto: Boolean = false): (Tree, ListBuffer[Tree => Tree]) = {
					def rewriteArgsToNullSafe(list: List[Tree]): List[Tree] = {
						list.map(tree => rewriteToNullSafe(c)(tree)(q"null", checkLast = false, a => a))
					}

					def incorporateTransformation(transformation: Tree => Tree, dontCheckForNotNull: Boolean = false): Unit = {
						def nullable(tree: Tree): Boolean = {
							def isAnyRef(sub: Tree): Boolean = sub.tpe <:< typeOf[AnyRef]

							isAnyRef(tree) && !isPackageOrModule(tree)
						}

						if (transformations.nonEmpty && (dontCheckForNotNull || (!nullable(tree) && canFoldInto))) {
							val prev = transformations.head
							transformations.update(0, transformation.andThen(prev))
						} else {
							transformations.prepend(transformation)
						}
					}

					def incorporateBase(t: Tree, ignoreCanFold: Boolean = false): Tree = {
						if (transformations.nonEmpty && (canFoldInto || ignoreCanFold)) {
							val trans = transformations.remove(0)
							trans.apply(t)
						} else t
					}

					tree match {
						case t: Ident => (t, transformations)
						case t@Select(qualifier, _) if isPackageOrModule(qualifier) => (t, transformations) //Selects from packages
						case t@(_: Literal | _: This) => (incorporateBase(t, ignoreCanFold = true), transformations)
						case t if t.symbol != null && t.symbol.isStatic && !t.symbol.isImplicit => (incorporateBase(t), transformations) //Static methods call
						case TypeApply(Select(qualifier, termName), types) => //Casting
							val transformation = (qual: Tree) => TypeApply(Select(qual, termName), types)
							incorporateTransformation(transformation, dontCheckForNotNull = true)
							loop(qualifier, transformations, canFoldInto = true)
						case Select(Apply(implicitClass, qualifier :: Nil), predName) if implicitClass.symbol.isImplicit => //Implicit class
							val transformation = (qual: Tree) => Select(Apply(implicitClass,List(qual)), predName)
							incorporateTransformation(transformation)
							loop(qualifier, transformations)
						case Select(qualifier, predName) => //Select
							val transformation = (qual: Tree) => Select(qual, predName)
							incorporateTransformation(transformation)
							loop(qualifier, transformations, canFoldInto = true)
						case apply@Apply(_@(_: This | _: Ident | Select(_: This | _: New, _)), List(_: Literal)) => //Function with literal arg
							(incorporateBase(apply), transformations)
						case Apply(prefix@(_: This | _: Ident | Select(_: This | _: New, _)), List(arg)) => //Function with one arg
							val transformation = (arg: Tree) => Apply(prefix, List(arg))
							incorporateTransformation(transformation)
							loop(arg, transformations, canFoldInto = true)
						case t@Apply(s@Select(_, _), List(arg)) if t.symbol != null && t.symbol.isStatic && t.symbol.isImplicit => //Implicit def
							val transformation = (qual: Tree) => Apply(s, List(qual))
							incorporateTransformation(transformation)
							loop(arg, transformations, canFoldInto = true)
						case Apply(prefix@(_: This | _: Ident | Select(_: This | _: New, _)), args) => //Function with multiple args
							val applyWithSafeArgs = Apply(prefix, rewriteArgsToNullSafe(args))
							(incorporateBase(applyWithSafeArgs), transformations)
						case Apply(Select(qualifier, predicate), Nil) => //Method with zero arg
							val transformation = (qual: Tree) => Apply(Select(qual, predicate), Nil)
							incorporateTransformation(transformation)
							loop(qualifier, transformations, canFoldInto = true)
						case Apply(Select(qualifier, predicate), List(arg))  => //Method with one arg
							val transformation = (qual: Tree) => Apply(Select(qual, predicate), List(rewriteToNullSafe(c)(arg)(q"null", checkLast = false, a => a)))
							incorporateTransformation(transformation)
							loop(qualifier, transformations, canFoldInto = true)
						case Apply(Select(qualifier, predicate), args) => //Method with multiple args
							val transformation = (qual: Tree) => Apply(Select(qual, predicate), rewriteArgsToNullSafe(args))
							incorporateTransformation(transformation)
							loop(qualifier, transformations)
						case _ => throw new IllegalArgumentException
					}
				}

				loop(tree)
			}

			def buildIfGuardedExp(prefix: Tree, selects: ListBuffer[Tree => Tree]): Tree = {
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
					val nextTree = if (selects.nonEmpty) {
						val res = selects.head.apply(prefix)
						selects.remove(0, 1)
						res
					} else prefix

					if (selects.isEmpty && !checkFinal) {
						finalTransform(nextTree)
					} else {
						if (selects.isEmpty)
							checkFinal = false
						newVal(nextTree)
					}
				}

				if (selects.isEmpty) finalTransform(prefix)
				else if (needsCaching(prefix)) newVal(prefix)
				else ifStatement(prefix)
			}

			val (baseTerm, selections) = decomposeExp(tree)
			buildIfGuardedExp(baseTerm, selections)
		}
	}

}