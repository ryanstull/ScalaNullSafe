# ScalaNullSafe

The purpose of this macro is to provide a quick, easy, readable/writable, and efficient way to make code null-safe in scala.

### Quick comparison of null-safe implementations:

| Implementation      	| Null-safe 	| Readable & Writable 	| Efficient 	|
|----------------------	|-----------	|-------------------	|-----------	|
| ScalaNullSafe        	| ✔️         	| ✔️                 	| ✔️         	|
| Normal access        	| ⛔         	| ✔️                 	| ✔️         	|
| Explicit null-checks 	| ✔️         	| ⛔                 	| ✔️         	|
| Option flatMap       	| ✔️         	| ⚠️                 	| ⛔         	|
| For loop flatMap     	| ✔️         	| ⚠️                 	| ⛔         	|
| Null-safe navigator  	| ✔️         	| ⚠️                 	| ⚠️         	|
| Try-catch NPE        	| ✔️         	| ✔️                 	| ⚠️         	|

Key: ✔️ = Good, ⚠️ = Problematic, ⛔ = Bad

## How to use

Add the dependency:

`libraryDependencies += "com.ryanstull" %% "scalanullsafe" % "1.0.0"`

Example use:

```scala
import com.ryanstull.nullsafe._

case class A(b: B)
case class B(c: C)
case class C(d: D)
case class D(e: E)
case class E(s: String)

val a = A(B(C(null)))
?(a.b.c.d.e.s) //No NPE! Just returns null

val a2 = A(B(C(D(E("Hello")))))
?(a2.b.c.d.e.s) //Returns "Hello"
```

There's also a variant that returns an `Option[A]` when provided an expression of type `A`,
and another that checks if a property is defined.

```scala
opt(a.b.c.d.e.s) //Returns None
notNull(a.b.c.d.e.s) //Returns false

opt(a2.b.c.d.e.s) //Returns Some("Hello")
notNull(a2.b.c.d.e.s) //Returns true
```

## How it works

The macro works by translating an expression, inserting null-checks before each intermediate result is used, turning
`?(a.b.c)`, for example, into

```scala
if(a != null){
  val b = a.b
  if(b != null){
    b.c
  } else null
} else null
```

Or for a longer example, translating `?(a.b.c.d.e.s)` into:

```scala
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
```

The `opt` macro is very similar, translating `opt(a.b.c)` into:

```scala
if(a != null){
  val b = a.b
  if(b != null){
    Some(b.c)
  } else None
} else None
```

And the `notNull` macro, translating `notNull(a.b.c)` into:

```scala
if(a != null){
  val b = a.b
  if(b != null){
    b.c != null
  } else false
} else false
```

## Performance

Here's the result of running the included jmh benchmarks:
```
[info] Benchmark                             Mode  Cnt    Score   Error   Units
[info] Benchmarks.ScalaNullSafeAbsent       thrpt   20  428.124 ± 1.625  ops/us
[info] Benchmarks.ScalaNullSafePresent      thrpt   20  232.066 ± 0.575  ops/us
[info] Benchmarks.explicitSafeAbsent        thrpt   20  429.090 ± 0.842  ops/us
[info] Benchmarks.explicitSafePresent       thrpt   20  231.400 ± 0.660  ops/us
[info] Benchmarks.fastButUnsafe             thrpt   20  230.157 ± 0.572  ops/us
[info] Benchmarks.loopSafeAbsent            thrpt   20  114.330 ± 0.113  ops/us
[info] Benchmarks.loopSafePresent           thrpt   20   59.513 ± 0.097  ops/us
[info] Benchmarks.nullSafeNavigatorAbsent   thrpt   20  274.222 ± 0.441  ops/us
[info] Benchmarks.nullSafeNavigatorPresent  thrpt   20  181.356 ± 1.538  ops/us
[info] Benchmarks.optionSafeAbsent          thrpt   20  139.369 ± 0.272  ops/us
[info] Benchmarks.optionSafePresent         thrpt   20  129.394 ± 0.102  ops/us
[info] Benchmarks.tryCatchSafeAbsent        thrpt   20  254.158 ± 0.686  ops/us
[info] Benchmarks.tryCatchSafePresent       thrpt   20  230.081 ± 0.659  ops/us
[success] Total time: 3909 s, completed Feb 24, 2019 3:03:02 PM
```

All of the `Present` benchmarks are where the value was actually defined and the `Absent`
ones are where one of the intermediate values, was null; or in other words, where an NPE would have occurred.

These benchmarks compare all of the known ways (or at least the ways that I know of) to handle null-safety in scala.  It demonstrates 
that the explicit null safety is the highest performing and that the 'ScalaNullSafe' macro has 
equivalent performance.

## Notes

* Using the `?` macro on an expression whose type is `<: AnyVal`, will result in returning the corresponding java wrapper
type.  For example `?(a.getInt)` will return `java.lang.Integer` instead of `Int` because the return type for this macro must
be nullable.  The conversions are the default ones defined in `scala.Predef`