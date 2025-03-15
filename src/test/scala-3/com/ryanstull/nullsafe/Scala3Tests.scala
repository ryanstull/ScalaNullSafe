package com.ryanstull.nullsafe

import org.scalatest.flatspec.AnyFlatSpec
import Tests.{A,B,C}

extension (a: A){
  def getBExtension: B = a.b
}

extension (b: B){
  def getCExtension: C = b.c
}

class Scala3Tests extends AnyFlatSpec {
  "Extension methods" should "work" in {
    val a = null.asInstanceOf[A]

    ?(a.getBExtension)
  }

  "Multiple extension methods" should "work" in {
    val a = null.asInstanceOf[A]

    ?(a.getBExtension.getCExtension.d.e)
  }
} 