package com.ryanstull

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.quoted.*

package object nullsafe:
  // Implementation using Scala 3 macros
  private object MacroImplementations:
    inline def rewriteToNullSafe[A](inline expr: A)(inline default: A, checkLast: Boolean = false): A =
      ${ rewriteToNullSafeImpl('expr, 'default, 'checkLast) }
      
    def rewriteToNullSafeImpl[A: Type](expr: Expr[A], default: Expr[A], checkLast: Expr[Boolean])(using Quotes): Expr[A] =
      import quotes.reflect.*
      
      // This will be the main implementation using Scala 3's metaprogramming API
      // It will need to analyze the expression and build a null-safe version
      
      // This is a simplified placeholder implementation
      val tree = expr.asTerm
      
      // Implement similar logic to your Scala 2 implementation
      '{ 
        if ($expr != null) $expr 
        else $default 
      }
      
  // Public API methods
  inline def ?[A](inline expr: A): A = 
    MacroImplementations.rewriteToNullSafe(expr)(null.asInstanceOf[A])
    
  inline def ?[A](inline expr: A, inline default: A): A =
    MacroImplementations.rewriteToNullSafe(expr)(default)

  // Implement other API methods like opt, notNull, isNull, etc. similarly