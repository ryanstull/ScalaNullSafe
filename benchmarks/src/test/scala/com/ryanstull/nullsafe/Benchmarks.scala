package com.ryanstull.nullsafe

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

/**
  * @author ryan
  * @since 2/20/19.
  */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Array(Mode.Throughput))
@Fork(1)
@Threads(1)
@Warmup(iterations = 10)
@State(Scope.Thread)
@Measurement(iterations = 20)
class Benchmarks {

	import Tests._

	val a: A = A(B(C(D(E("test")))))
	val aWithNull: A = A(B(null))
	val nul: Null = null //Refer to this null to prevent constant folding

	@Benchmark
	def fastButUnsafe: String = a.b.c.d.e.s

	@Benchmark
	def explicitSafePresent: String =
		if(a != null){
			val b = a.b
			if(b != null){
				val c = b.c
				if(c != null){
					val d = c.d
					if(d != null){
						val e = d.e
						if(e != null){
							e.s
						} else null
					} else null
				} else null
			} else null
		} else null

	@Benchmark
	def explicitSafeAbsent: String =
		if(aWithNull != null){
			val b = aWithNull.b
			if(b != null){
				val c = b.c
				if(c != null){
					val d = c.d
					if(d != null){
						val e = d.e
						if(e != null){
							e.s
						} else null
					} else null
				} else null
			} else null
		} else null

	@Benchmark
	def optionSafePresent: Option[String] = Option(a).
		flatMap(a => Option(a.b)).
		flatMap(b => Option(b.c)).
		flatMap(c => Option(c.d)).
		flatMap(d => Option(d.e)).
		flatMap(e => Option(e.s))

	@Benchmark
	def optionSafeAbsent: Option[String] = Option(aWithNull).
		flatMap(a => Option(a.b)).
		flatMap(b => Option(b.c)).
		flatMap(c => Option(c.d)).
		flatMap(d => Option(d.e)).
		flatMap(e => Option(e.s))

	@Benchmark
	def loopSafePresent: Option[String] =
		for {
			aOpt <- Option(a)
			b <- Option(aOpt.b)
			c <- Option(b.c)
			d <- Option(c.d)
			e <- Option(d.e)
			s = e.s
		} yield s

	@Benchmark
	def loopSafeAbsent: Option[String] =
		for {
			aOpt <- Option(aWithNull)
			b <- Option(aOpt.b)
			c <- Option(b.c)
			d <- Option(c.d)
			e <- Option(d.e)
			s = e.s
		} yield s

	@Benchmark
	def tryCatchSafePresent: String =
		try {
			a.b.c.d.e.s
		} catch {
			case _: NullPointerException => null
		}

	@Benchmark
	def tryCatchSafeAbsent: String =
		try {
			aWithNull.b.c.d.e.s
		} catch {
			case _: NullPointerException => nul
		}

	@Benchmark
	def nullSafeNavigatorPresent: String = {
		import NullSafeNavigator._
		a.?(_.b).?(_.c).?(_.d).?(_.e).?(_.s)
	}

	@Benchmark
	def nullSafeNavigatorAbsent: String = {
		import NullSafeNavigator._
		aWithNull.?(_.b).?(_.c).?(_.d).?(_.e).?(_.s)
	}

	@Benchmark
	def ScalaNullSafePresent: String = ?(a.b.c.d.e.s)

	@Benchmark
	def ScalaNullSafeAbsent: String = ?(aWithNull.b.c.d.e.s)


	import monocle.Optional
	val aGetB = Optional[A,B]{
		case A(b) if b != null => Some(b)
		case _ => None
	}(b => { case A(_) => A(b) })
	val bGetC = Optional[B,C]{
		case B(c) if c != null => Some(c)
		case _ => None
	}(c => { case B(_) => B(c) })
	val cGetD = Optional[C,D]{
		case C(d) if d != null => Some(d)
		case _ => None
	}(d => { case C(_) => C(d) })
	val dGetE = Optional[D,E]{
		case D(e) if e != null => Some(e)
		case _ => None
	}(e => { case D(_) => D(e) })
	val egetS = Optional[E,String]{
		case E(s) if s != null => Some(s)
		case _ => None
	}(s => { case E(_) => E(s) })
	val aGetS = aGetB composeOptional bGetC composeOptional cGetD composeOptional dGetE composeOptional egetS

	@Benchmark
	def monocleOptionalPresent: Option[String] = aGetS.getOption(a)

	@Benchmark
	def monocleOptionalAbsent: Option[String] = aGetS.getOption(aWithNull)
}
