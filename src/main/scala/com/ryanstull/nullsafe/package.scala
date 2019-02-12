package com.ryanstull

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
  * @author ryan
  * @since 12/5/18.
  */
package object nullsafe {

	def ?(expr: Byte): java.lang.Byte = macro qMarkImpl[java.lang.Byte]
	def ?(expr: Short): java.lang.Short = macro qMarkImpl[java.lang.Short]
	def ?(expr: Char): java.lang.Character = macro qMarkImpl[java.lang.Character]
	def ?(expr: Int): java.lang.Integer = macro qMarkImpl[java.lang.Integer]
	def ?(expr: Long): java.lang.Long = macro qMarkImpl[java.lang.Long]
	def ?(expr: Float): java.lang.Float = macro qMarkImpl[java.lang.Float]
	def ?(expr: Double): java.lang.Double = macro qMarkImpl[java.lang.Double]
	def ?(expr: Boolean): java.lang.Boolean = macro qMarkImpl[java.lang.Boolean]

	def ?(expr: Unit): Unit = macro qMarkImpl[Unit]
	def ?[A <: AnyRef](expr: A): A = macro qMarkImpl[A]
	def qMarkImpl[A : c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		import c.universe._

		val tree = expr.tree
		val result = rewriteToNullSafe(c)(tree)(Literal(Constant(null)),a => a)
		c.Expr(result)
	}

	def opt[A](expr: A): Option[A] = macro optImpl[A]
	def optImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Option[A]] = {
		import c.universe._

		val tree = expr.tree
		val result = rewriteToNullSafe(c)(tree)(q"None",a => q"Some($a)")
		c.Expr[Option[A]](result)
	}

	private def rewriteToNullSafe[A: c.WeakTypeTag]
		(c: blackbox.Context)(tree: c.universe.Tree)(default: c.universe.Tree, finalTransform: c.universe.Tree => c.universe.Tree): c.universe.Tree = {
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
					case t @ (_: Literal | _:Ident | _:This | Apply(Select(New(_), _), _)) => (t, MQueue.empty)
					case t if t.symbol.isStatic => (t, MQueue.empty)
					case t @ Select(qualifier, _) if isPackageOrModule(qualifier) => (t, MQueue.empty)
					case Select(qualifier, predName) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Select(qual,predName))
						res
					case Apply(fun: Ident, List(arg)) =>
						val res = loop(arg, accumulator)
						res._2 += ((qual: c.universe.Tree) => Apply(fun, List(qual)))
						res
					case Apply(Select(qualifier, predicate), args) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Apply(Select(qual, predicate), args))
						res
					case _ => throw new IllegalArgumentException
				}
			}
			loop(tree)
		}

		def buildIfGuardedExp(prefix: Tree,selects: MQueue[Tree => Tree]): Tree = {

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
				val nextTree = selects.dequeue().apply(prefix)
				if(selects.isEmpty) finalTransform(nextTree)
				else newVal(nextTree)
			}

			if(selects.isEmpty) finalTransform(prefix)
			else if (needsCaching(prefix)) newVal(prefix)
			else ifStatement(prefix)
		}

		val (baseTerm, selections) = decomposeExp(tree)
		buildIfGuardedExp(baseTerm,selections)
	}
}