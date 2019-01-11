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

		def decomposeExp(tree: Tree): (Ident , MQueue[Tree => Tree]) = {
			def loop(tree: Tree, accumulator: (Ident, MQueue[Tree => Tree]) = (null,MQueue.empty[Tree => Tree])): (Ident, MQueue[Tree => Tree]) = {
				tree match {
					case Apply(Select(qualifier, predicate), args) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Apply(Select(qual, predicate), args))
						res
					case Select(qualifier, predName) =>
						val res = loop(qualifier, accumulator)
						res._2 += ((qual: c.universe.Tree) => Select(qual,predName))
						res
					case i: Ident => (i, MQueue.empty[Tree => Tree])
					case _ => throw new IllegalArgumentException
				}
			}
			loop(tree)
		}


		def canBeNull(tree: Tree): Boolean = {
			val sym = tree.symbol
			val tpe = tree.tpe

			sym != null &&
				!sym.isModule && !sym.isModuleClass &&
				!sym.isPackage && !sym.isPackageClass &&
				!(tpe <:< typeOf[AnyVal])
		}

		def buildIfGaurded(prefix: Tree,ident: Ident,selects: MQueue[Tree => Tree]): Tree = {
			if(ident == null) {
				if(selects.size == 1) {
					selects.dequeue().apply(prefix)
				} else {
					val baseName = c.freshName

					Block(
						ValDef(Modifiers(), TermName(baseName), TypeTree(), selects.dequeue().apply(prefix)),
						reify {
							if (c.Expr(Ident(TermName(baseName))).splice != null) {
								c.Expr(buildIfGaurded(Ident(TermName(baseName)),null,selects)).splice
							} else null
						}.tree
					)
				}
			} else {
				reify {
					if (c.Expr(ident).splice != null) {
						c.Expr(buildIfGaurded(ident,null,selects)).splice
					} else null
				}
			}.tree
		}

		val (baseTerm, selections) = decomposeExp(tree)

		buildIfGaurded(baseTerm,baseTerm,selections)
	}
}
