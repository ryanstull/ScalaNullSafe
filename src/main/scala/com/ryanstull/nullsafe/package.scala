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

		def decomposeExp(tree: Tree): (Tree , MQueue[Tree => Tree]) = {
			def loop(tree: Tree, accumulator: (Tree, MQueue[Tree => Tree]) = (null,MQueue.empty[Tree => Tree])): (Tree, MQueue[Tree => Tree]) = {
				tree match {
					case Apply(Select(qualifier, predicate), args) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Apply(Select(qual, predicate), args))
						res
					case Select(qualifier, predName) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Select(qual,predName))
						res
					case i @ (_:Ident | _:This) => (i, MQueue.empty[Tree => Tree])
					case _ => throw new IllegalArgumentException
				}
			}
			loop(tree)
		}

		def buildIfGuarded(prefix: Tree,selects: MQueue[Tree => Tree]): Tree = {

			def ifStatement(tree: Tree): Tree = reify {
				if (c.Expr(tree).splice != null) {
					c.Expr(checkForLastSelect(tree)).splice
				} else null
			}.tree

			def checkForLastSelect(tree: Tree): Tree = {
				if (selects.size == 1) {
					selects.dequeue().apply(tree)
				} else {
					newVal(tree, selects.dequeue())
				}
			}

			def newVal(tree: Tree, processRhs: Tree => Tree): Tree = {
				val baseTermName = TermName(c.freshName)
				val ident = Ident(baseTermName)

				Block(
					ValDef(Modifiers(), baseTermName, TypeTree(), processRhs(tree)),
					ifStatement(ident)
				)
			}

			if(selects.isEmpty) prefix
			else if (prefix.symbol.isMethod) {
				newVal(prefix,f => f)
			} else ifStatement(prefix)
		}

		val (baseTerm, selections) = decomposeExp(tree)
		buildIfGuarded(baseTerm,selections)
	}
}
