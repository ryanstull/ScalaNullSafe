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

	val a = A(B(C(D(E("test")))))
	val aWithNull = A(B(null))
	val nul = null //Refer to this null to prevent constant folding

	@Benchmark
	def fastButUnsafe: String = a.b.c.d.e.s

	@Benchmark
	def explicitSafeSuccess: String =
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
	def explicitSafeFailure: String =
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
	def optionSafeSuccess: Option[String] = Option(a).
		flatMap(a => Option(a.b)).
		flatMap(b => Option(b.c)).
		flatMap(c => Option(c.d)).
		flatMap(d => Option(d.e)).
		flatMap(e => Option(e.s))

	@Benchmark
	def optionSafeFailure: Option[String] = Option(aWithNull).
		flatMap(a => Option(a.b)).
		flatMap(b => Option(b.c)).
		flatMap(c => Option(c.d)).
		flatMap(d => Option(d.e)).
		flatMap(e => Option(e.s))

	@Benchmark
	def loopSafeSuccess: Option[String] =
		for {
			aOpt <- Option(a)
			b <- Option(aOpt.b)
			c <- Option(b.c)
			d <- Option(c.d)
			e <- Option(d.e)
			s = e.s
		} yield s

	@Benchmark
	def loopSafeFailure: Option[String] =
		for {
			aOpt <- Option(aWithNull)
			b <- Option(aOpt.b)
			c <- Option(b.c)
			d <- Option(c.d)
			e <- Option(d.e)
			s = e.s
		} yield s

	@Benchmark
	def tryCatchSafeSuccessful: String =
		try {
			a.b.c.d.e.s
		} catch {
			case _: NullPointerException => null
		}

	@Benchmark
	def tryCatchSafeFailure: String =
		try {
			aWithNull.b.c.d.e.s
		} catch {
			case _: NullPointerException => nul
		}

	@Benchmark
	def nullSafeNavigatorSuccess: String = {
		import NullSafeNavigator._
		a.?(_.b).?(_.c).?(_.d).?(_.e).?(_.s)
	}

	@Benchmark
	def nullSafeNavigatorFailure: String = {
		import NullSafeNavigator._
		aWithNull.?(_.b).?(_.c).?(_.d).?(_.e).?(_.s)
	}

	@Benchmark
	def ScalaNullSafeSuccess: String = ?(a.b.c.d.e.s)

	@Benchmark
	def ScalaNullSafeFailure: String = ?(aWithNull.b.c.d.e.s)
}
