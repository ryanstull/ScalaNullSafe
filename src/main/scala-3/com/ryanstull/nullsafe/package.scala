package com.ryanstull

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.quoted.*

package object nullsafe:
  import com.ryanstull.nullsafe.MacroImplementations.*

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Byte): Byte | Null = ${ qMarkImpl[Byte | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Short): Short | Null = ${ qMarkImpl[Short | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Char): Char | Null = ${ qMarkImpl[Char | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Int): Int | Null = ${ qMarkImpl[Int | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Long): Long | Null = ${ qMarkImpl[Long | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Float): Float | Null = ${ qMarkImpl[Float | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Double): Double | Null = ${ qMarkImpl[Double | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Boolean): Boolean | Null = ${ qMarkImpl[Boolean | Null]('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?[A <: AnyRef](inline expr: A): A = ${ qMarkImpl('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr    Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @param default Custom default value to return in the case where NPE would have occurred.
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns `default` instead.
    */
  inline def ?[A](inline expr: A, inline default: A): A = ${ qMarkDefaultImpl('expr, 'default) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @return The value of the expression. If there would have been a NullPointerException due to method/field
    *         access on `null`, returns null instead.
    */
  inline def ?(inline expr: Unit): Unit = ${ qMarkDefaultImpl('expr, '{ () }) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @tparam A Type of the expression
    * @return The value of the expression wrapped in Some. If the expression is null, or if there would have been a
    *         NullPointerException due to method/field access on `null`, returns None instead.
    */
  inline def opt[A](inline expr: A): Option[A] = ${ optImpl('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @tparam A Type of the expression
    * @return `true` if the value of the expression is not null and there wouldn't have been any NullPointerExceptions
    *         due to method/field access on `null`, false otherwise.
    */
  inline def notNull[A](inline expr: A): Boolean = ${ notNullImpl('expr) }

  /**
    * Translates an expression that could cause a NullPointerException due to method/field access on `null`
    * and adds explicit null-checks to avoid that.
    *
    * @param expr Some expression that might cause a NullPointerExpression due to method/field access on `null`
    * @tparam A Type of the expression
    * @return `true` if the value of the expression is null or there would have been any NullPointerExceptions
    *         due to method/field access on `null`, false otherwise.
    */
  inline def isNull[A](inline expr: A): Boolean = ${ isNullImpl('expr) }

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
  inline def ??[A <: AnyRef](inline exprs: A*)(default: A) = ${ doubleQMarkImpl('exprs)('default) }

  private object MacroImplementations:
    def qMarkImpl[A: Type](expr: Expr[A])(using Quotes): Expr[A] = {
      import quotes.reflect.*
      rewriteToNullSafe(expr.asTerm)('{null}.asTerm, a => a).asExprOf[A]
    }

    def qMarkDefaultImpl[A: Type](expr: Expr[A], default: Expr[A])(using Quotes): Expr[A] = {
      import quotes.reflect.*
      rewriteToNullSafe(expr.asTerm)(default.asTerm, a => a).asExprOf[A]
    }

    def optImpl[A: Type](expr: Expr[A])(using Quotes): Expr[Option[A]] = {
      import quotes.reflect.*
      rewriteToNullSafe(expr.asTerm)('{ None: Option[A] }.asTerm, a => '{Option(${a.asExprOf[A]})}.asTerm).asExprOf[Option[A]]
    }

    def notNullImpl[A](expr: Expr[A])(using Quotes): Expr[Boolean] = {
      import quotes.reflect.*
      rewriteToNullSafe(expr.asTerm)('{false}.asTerm, a => Apply(Select.unique(a, "!="), List(Literal(NullConstant())))).asExprOf[Boolean]
    }

    def isNullImpl[A](expr: Expr[A])(using Quotes): Expr[Boolean] = {
      import quotes.reflect.*
      rewriteToNullSafe(expr.asTerm)('{true}.asTerm, a => Apply(Select.unique(a, "=="), List(Literal(NullConstant())))).asExprOf[Boolean]
    }

    def freshName = s"x${java.util.UUID.randomUUID().toString.replace("-", "").substring(0, 10)}"

    def doubleQMarkImpl[A: Type](exprs: Expr[Seq[A]])(default: Expr[A])(using Quotes): Expr[A] = {
      import quotes.reflect.*

      def genValue(exprs: Seq[Term]): Term = {
        if (exprs.nonEmpty) {
          val head = exprs.head
          val safeTerm = rewriteToNullSafe(head)('{null}.asTerm, a => a)

          val name = freshName
          val valSymbol = Symbol.newVal(Symbol.spliceOwner, name, safeTerm.tpe, Flags.Synthetic, Symbol.noSymbol)
          val valDef = ValDef(valSymbol, Some(safeTerm))
          val ident = Ref(valSymbol)

          Block(
            List(valDef),
            If(
              Apply(
                Select.unique(ident, "!="),
                List(Literal(NullConstant()))
              ),
              ident,
              genValue(exprs.tail)
            )
          )
        } else default.asTerm
      }

      exprs match
        case Varargs(seqExprs) => genValue(seqExprs.map(_.asTerm)).asExprOf[A]
        case _ => report.errorAndAbort("Wrong type")
    }

    private def rewriteToNullSafe(using Quotes)(expr: quotes.reflect.Term)
                                 (default: quotes.reflect.Term, finalTransformation: quotes.reflect.Term => quotes.reflect.Term, checkLast: Boolean = false): quotes.reflect.Term = {
      import quotes.reflect.*

      def isPackageOrModule(sub: Term): Boolean = {
        val sym = sub.symbol
        sym != null &&
          (sym.isValDef && sym.flags.is(Flags.Module)) || (sym.isTypeDef && sym.flags.is(Flags.Module)) ||
          sym.isPackageDef || (sym.isTypeDef && sym.flags.is(Flags.Package))
      }

      def decomposeExp(term: Term): (Term, ListBuffer[Term => Term]) = {
        @tailrec
        def loop(term: Term, transformations: ListBuffer[Term => Term] = ListBuffer.empty, canFoldInto: Boolean = false): (Term, ListBuffer[Term => Term]) = {

          def rewriteArgsToNullSafe(list: List[Term]): List[Term] = {
            list.map(term => rewriteToNullSafe(term)('{null}.asTerm, a => a))
          }

          def incorporateTransformation(transformation: Term => Term, dontCheckForNotNull: Boolean = false): Unit = {
            def nullable(term: Term): Boolean = {
              def isAnyRef(sub: Term): Boolean = sub.tpe <:< TypeRepr.of[AnyRef]

              isAnyRef(term) && !isPackageOrModule(term)
            }

            if (transformations.nonEmpty && (dontCheckForNotNull || (!nullable(term) && canFoldInto))) {
              val prev = transformations.head
              transformations.update(0, transformation.andThen(prev))
            } else {
              transformations.prepend(transformation)
            }
          }

          def incorporateBase(t: Term, ignoreCanFold: Boolean = false): Term = {
            if (transformations.nonEmpty && (canFoldInto || ignoreCanFold)) {
              val trans = transformations.remove(0)
              trans.apply(t)
            } else t
          }

          term match
            case Inlined(_, _, body) => loop(body)
            case t: Ident => (t, transformations)
            case t @ Typed(Literal(_), _) => (t, transformations)
            case t@Select(qualifier, _) if isPackageOrModule(qualifier) => (t, transformations) //Selects from packages
            case t@(_: Literal | _: This) => (incorporateBase(t, ignoreCanFold = true), transformations)
            case t if t.symbol != null && t.symbol.flags.is(Flags.Static) && !t.symbol.flags.is(Flags.Implicit) => (incorporateBase(t), transformations) //Static methods call
            case TypeApply(s @ Select(qualifier, termName), types) => //Casting
              val transformation = (qual: Term) => TypeApply(Select.copy(s)(qual, termName), types)
              incorporateTransformation(transformation, dontCheckForNotNull = true)
              loop(qualifier, transformations, canFoldInto = true)
            case s @ Select(qualifier, predName) => //Select
              val transformation = (qual: Term) => Select.copy(s)(qual, predName)
              incorporateTransformation(transformation)
              loop(qualifier, transformations, canFoldInto = true)
            case apply@Apply(_@(_: This | _: Ident | Select(_: This | _: New, _)), List(_: Literal)) => //Function with literal arg
              (incorporateBase(apply), transformations)
            case Apply(prefix@(_: This | _: Ident | Select(_: This | _: New, _)), List(arg)) => //Function with one arg
              val transformation = (arg: Term) => Apply(prefix, List(arg))
              incorporateTransformation(transformation)
              loop(arg, transformations, canFoldInto = true)
            case t@Apply(s@Select(_, _), List(arg)) if t.symbol != null && t.symbol.isDefDef && t.symbol.flags.is(Flags.Implicit) => //Implicit def
              val transformation = (qual: Term) => Apply(s, List(qual))
              incorporateTransformation(transformation)
              loop(arg, transformations, canFoldInto = true)
            case Apply(prefix@(_: This | _: Ident | Select(_: This | _: New, _)), args) => //Function with multiple args
              val applyWithSafeArgs = Apply(prefix, rewriteArgsToNullSafe(args))
              (incorporateBase(applyWithSafeArgs), transformations)
            case Apply(s @ Select(qualifier, predicate), Nil) => //Method with zero arg
              val transformation = (qual: Term) => Apply(Select.copy(s)(qual, predicate), Nil)
              incorporateTransformation(transformation)
              loop(qualifier, transformations, canFoldInto = true)
            case Apply(s @ Select(qualifier, predicate), List(arg))  => //Method with one arg
              val transformation = (qual: Term) => Apply(Select.copy(s)(qual, predicate), List(rewriteToNullSafe(arg)('{null}.asTerm, (a: Term) => a, false)))
              incorporateTransformation(transformation)
              loop(qualifier, transformations, canFoldInto = true)
            case Apply(s @ Select(qualifier, predicate), args) => //Method with multiple args
              val transformation = (qual: Term) => Apply(Select.copy(s)(qual, predicate), rewriteArgsToNullSafe(args))
              incorporateTransformation(transformation)
              loop(qualifier, transformations)
            case _ =>
              report.error(
                s"""Unsupported term type: ${term.getClass.getName} in ${term.show(using Printer.TreeStructure)}
                   |If you believe this is something that should be supported, please submit an issue at: https://github.com/ryanstull/ScalaNullSafe/issues""".stripMargin)
              (term, ListBuffer.empty)
        }

        loop(term)
      }

      def buildIfGuardedExp(prefix: Term, selects: ListBuffer[Term => Term]): Term = {
        var checkFinal = checkLast

        def needsCaching(tree: Tree): Boolean = {
          val sym = tree.symbol
          sym.isDefDef || (tree match {
            case Select(qual, _) if !isPackageOrModule(qual) => true
            case _ => false
          })
        }

        def newVal(prefix: Term)(using Quotes): Term = {
          val name = freshName
          val valSymbol = Symbol.newVal(Symbol.spliceOwner, name, prefix.tpe, Flags.Synthetic, Symbol.noSymbol)
          val valDef = ValDef(valSymbol, Some(prefix))
          Block(List(valDef), ifStatement(Ref(valSymbol)))
        }

        def ifStatement(prefix: Term): Term = {
          If(
            Apply(
              Select.unique(prefix, "!="),
              List(Literal(NullConstant()))
            ),
            next(prefix),
            default
          )
        }

        def next(prefix: Term): Term = {
          val nextTree = if (selects.nonEmpty) {
            val res = selects.head.apply(prefix)
            selects.remove(0, 1)
            res
          } else prefix

          if (selects.isEmpty && !checkFinal) {
            finalTransformation(nextTree)
          } else {
            if (selects.isEmpty)
              checkFinal = false
            newVal(nextTree)
          }
        }

        if (selects.isEmpty) finalTransformation(prefix)
        else ifStatement(prefix)
      }

      val (prefix, selections) = decomposeExp(expr)
      buildIfGuardedExp(prefix, selections)
    }