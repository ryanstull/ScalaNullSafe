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

		def decomposeExp(tree: Tree): (Tree , MQueue[(Tree => Tree,Tree)]) = {
			def loop(tree: Tree, accumulator: (Tree, MQueue[(Tree => Tree, Tree)]) = (null,MQueue.empty)): (Tree, MQueue[(Tree => Tree, Tree)]) = {
				tree match {				   //↓ This case captures constructor calls
					case t @ (_:Ident | _:This | Apply(Select(New(_), _), _)) => (t, MQueue.empty)
					case t @ Select(qualifier, predName) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Select(qual,predName)) -> t
						res
					case t @ Apply(Select(qualifier, predicate), args) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Apply(Select(qual, predicate), args)) -> t
						res
					case _ => throw new IllegalArgumentException
				}
			}
			loop(tree)
		}

		def buildIfGuardedExp(prefix: Tree,selects: MQueue[(Tree => Tree, Tree)]): Tree = {

			def advanceToNextNullable(tree: Tree,first: Boolean): Tree = {

				def canBeNull(sub: Tree): Boolean = {
					val sym = sub.symbol
					val tpe = sub.tpe

					sym != null &&
						!sym.isModule && !sym.isModuleClass &&
						!sym.isPackage && !sym.isPackageClass &&
						!(tpe <:< typeOf[AnyVal])
				}

				if(first) {
					tree
				} else {
					var origTree: Tree = null
					var newTree = tree
					do {
						val next = selects.dequeue()
						origTree = next._2
						newTree = next._1.apply(newTree)
					} while (selects.nonEmpty && !canBeNull(origTree))
					newTree
				}
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
					c.Expr(next(prefix,first = false)).splice
				} else null
			}.tree

			def next(tree: Tree,first: Boolean): Tree = {
				val newTree = advanceToNextNullable(tree,first)

				if (selects.isEmpty) newTree
				else if (first) {
					ifStatement(newTree)
				} else {
					newVal(newTree)
				}
			}

			next(prefix, first = true)
		}

		val (baseTerm, selections) = decomposeExp(tree)
		buildIfGuardedExp(baseTerm,selections)
	}
}