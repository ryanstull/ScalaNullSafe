package com.ryanstull

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
  * @author ryan
  * @since 12/5/18.
  */
package object nullsafe {

	def ?[A](expr: A): A = macro outer[A]
	def outer[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[A] = {
		import c.universe._

		val tree = expr.tree
		//		println("Initial: " + showRaw(tree))
		val result = rewriteToNullSafe(c)(tree, tree)
		//		println(result)
		c.Expr(result)
	}

	def opt[A](expr: A): Option[A] = macro optImpl[A]
	def optImpl[A: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[A]): c.Expr[Option[A]] = {
		import c.universe._

		val tree = expr.tree
		val result = rewriteToNullSafe(c)(tree,tree)
		c.Expr[Option[A]](q"Option($result)")
	}

	private def rewriteToNullSafe[A: c.WeakTypeTag](c: blackbox.Context)(tree: c.universe.Tree,accumulator: c.universe.Tree): c.universe.Tree = {
		import c.universe._
		import mutable.{Queue => MQueue}

		def decomposeExp(tree: Tree): (Ident , MQueue[Tree => Tree]) = {
			def loop(tree: Tree, accumulator: (Ident, MQueue[Tree => Tree]) = (null,MQueue.empty[Tree => Tree])): (Ident, MQueue[Tree => Tree]) = {
				tree match {
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


		//		def canBeNull(tree: Tree): Boolean = {
		//			val sym = tree.symbol
		//			val tpe = tree.tpe
		//
		//			sym != null &&
		//				!sym.isModule && !sym.isModuleClass &&
		//				!sym.isPackage && !sym.isPackageClass &&
		//				!(tpe <:< typeOf[AnyVal])
		//		}

		//		def nullGuarded(originalPrefix: Tree, prefixTree: Tree, whenNonNull: Tree => Tree): Tree =
		//			if (canBeNull(originalPrefix)) {
		//				val prefixVal = c.freshName()
		//				q"""
		//				   |val $prefixVal = $prefixTree
		//				   |if()
		//				   |
		//				 """
		//					If(
		//						Apply(Select(Ident(prefixVal), eqOp), List(nullTree)),
		//						noneTree,
		//						whenNonNull(Ident(prefixVal))
		//					)
		//				)
		//			} else prefixTree

		//		tree match {
		////			case Apply(Select(qualifier, predicate), args) =>
		////				val newResult = ifExpression(qualifier)
		////				rewriteToNullSafe(c)(qualifier,newResult)
		//			case Select(qualifier, predicate) =>
		//				val newResult = ifExpression(qualifier,predicate)
		//				rewriteToNullSafe(c)(qualifier,newResult)
		//			case _: Ident => accumulator
		//			case _ => accumulator
		//		}

		def buildIfGaurded(prefix: Tree,ident: Ident,selects: MQueue[Tree => Tree]): Tree = {
			if(ident == null) {
				val baseName = c.freshName

				val res = ValDef(Symbol.apply(baseName))

				val part2 =
					if(selects.nonEmpty){
						q"""
						   if($baseName != null) {
							 ${buildIfGaurded(Ident(baseName),null,selects)}
						   } else null
						"""
					} else EmptyTree

				Block(res,part2)
			} else {
				reify {
					if (c.Expr(ident).splice != null) {
						c.Expr(buildIfGaurded(ident,null,selects)).splice
					} else null
				}
			}.tree
		}

		val (baseTerm, selections) = decomposeExp(tree)

		println(selections)

		buildIfGaurded(baseTerm,baseTerm,selections)
	}
}
