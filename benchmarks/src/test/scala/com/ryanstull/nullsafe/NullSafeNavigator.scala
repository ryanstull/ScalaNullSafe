package com.ryanstull.nullsafe

/**
  * @author ryan
  * @since 2/22/19.
  */
object NullSafeNavigator {
	implicit class nullSafe[A](val a: A) extends AnyVal {
		def ?[B >: Null](f: A => B): B = if (a == null) null else f(a)
	}
}
