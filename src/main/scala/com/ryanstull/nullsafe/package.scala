package com.ryanstull

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
  * @author ryan
  * @since 12/5/18.
  */
package object nullsafe {

	def ?[A](expr: A): A = macro qMarkImpl[A]
	def qMarkImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		val tree = expr.tree
		val result = rewriteToNullSafe(c)(tree)
		c.Expr(result)
	}

	def opt[A](expr: A): Option[A] = macro optImpl[A]
	def optImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Option[A]] = {
		import c.universe._

		val tree = expr.tree
		val result = rewriteToNullSafe(c)(tree)
		c.Expr[Option[A]](q"Option($result)")
	}

	private def rewriteToNullSafe[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree): c.universe.Tree = {
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
					case t if t.symbol.isStatic => (t, MQueue.empty)
					case t @ Select(qualifier, _) if isPackageOrModule(qualifier) => (t, MQueue.empty)
					case t @ (_:Ident | _:This | Apply(Select(New(_), _), _) /**Constructors*/) => (t, MQueue.empty)
					case Select(qualifier, predName) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Select(qual,predName))
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
				} else null
			}.tree

			def next(prefix: Tree): Tree = {
				val nextTree = selects.dequeue().apply(prefix)
				if(selects.isEmpty) nextTree
				else newVal(nextTree)
			}

			if(selects.isEmpty) prefix
			else if (needsCaching(prefix)) newVal(prefix)
			else ifStatement(prefix)
		}

		val (baseTerm, selections) = decomposeExp(tree)
		buildIfGuardedExp(baseTerm,selections)
	}
}