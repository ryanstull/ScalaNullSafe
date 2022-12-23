# ScalaNullSafe

The purpose of this macro is to provide a quick, easy, readable/writable, and efficient way to make code null-safe in scala.

[![Scala CI](https://github.com/ryanstull/ScalaNullSafe/actions/workflows/test.yml/badge.svg)](https://github.com/ryanstull/ScalaNullSafe/actions/workflows/test.yml)

### Quick comparison of null-safe implementations:

| Implementation      	| Null-safe 	| Readable & Writable | Efficient 	|
|----------------------	|-----------	|-------------------	|-----------	|
| 🎉 **ScalaNullSafe** 🎉        	| ✔️         	| ✔️                 	| ✔️         	|
| Normal access        	| ⛔         	| ✔️                 	| ✔️         	|
| Explicit null-checks 	| ✔️         	| ⛔                 	| ✔️         	|
| Option flatMap       	| ✔️         	| ⚠️                 	| ⛔         	|
| For loop flatMap     	| ✔️         	| ⚠️                 	| ⛔         	|
| Null-safe navigator  	| ✔️         	| ⚠️                 	| ⚠️         	|
| Try-catch NPE        	| ✔️         	| ✔️                 	| ⚠️         	|
| Monocle Optional (lenses)| ✔️         	| 💀	                  | ⛔         	|
| thoughtworks NullSafe DSL| ✔️         	| ✔️	                  | ⚠         	|

Key: ✔️ = Good, ⚠️ = Sub-optimal, ⛔ = Bad

## How to use

Add the dependency:

`libraryDependencies += "com.ryanstull" %% "scalanullsafe" % "1.2.6"`

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
and another that just checks if a property is defined.

```scala
opt(a.b.c.d.e.s) //Returns None
notNull(a.b.c.d.e.s) //Returns false

opt(a2.b.c.d.e.s) //Returns Some("Hello")
notNull(a2.b.c.d.e.s) //Returns true
```

## How it works

### `?` macro

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

### `opt` macro

The `opt` macro is very similar, translating `opt(a.b.c)` into:

```scala
if(a != null){
  val b = a.b
  if(b != null){
    Option(b.c)
  } else None
} else None
```

### `notNull` macro

And the `notNull` macro, translating `notNull(a.b.c)` into:

```scala
if(a != null){
  val b = a.b
  if(b != null){
    b.c != null
  } else false
} else false
```

### Safe translation

All of the above work for method invocation as well as property access, and the two can be intermixed. For example: 

`?(someObj.methodA().field1.twoArgMethod("test",1).otherField)`
 
 will be translated properly.
 
Also the macro will make the arguments to method and function calls null-safe as well:

`?(a.b.c.method(d.e.f))`

So you don't have to worry if `d` or `e` would be null.

### Custom default for `?`

For the `?` macro, you can also provide a custom default instead of `null`, by passing it in as the second
parameter.  For example

```scala
case class Person(name: String)

val person: Person = null

assert(?(person.name,"") == "")
```

### `??` macro

There's also a `??` ([null coalesce operator](https://en.wikipedia.org/wiki/Null_coalescing_operator)) which is used to select the first non-null value from a var-args list of expressions.

```scala
case class Person(name: String)

val person = Person(null)

assert(??(person.name)("Bob") == "Bob")

val person2: Person = null
val person3 = Person("Sally")

assert(??(person.name,person2.name,person3.name)("No name") == "Sally")
```

The null-safe coalesce operator also rewrites each arg so that it's null safe.  So you can pass in `a.b.c` as an expression
without worrying if `a` or `b` are `null`. To be more explicit, the `??` macro would translate `??(a.b.c,a2.b.c)(default)` into

```scala
{
    val v1 = if(a != null){
      val b = a.b
      if(b != null){
        val c = b.c
        if(c != null){
          c
        } else null
      } else null
    } else null
    if(v1 != null) v1
    else {
        val v2 = if(a2 != null){
          val b = a2.b
          if(b != null){
            val c = b.c
            if(c != null){
              c
            } else null
          } else null
        } else null
        if (v2 != null) v2
        else default
    }
}
```

Compared to the `?` macro in the case of a single arg, the `??` macro check that that _entire_ expression is not null. Whereas
the `?` macro would just check that the preceding elements (e.g. `a` and `b` in `a.b.c`) aren't null before returning the default value.

### Efficient null-checks

The macro is also smart about what it checks for null, so anything that is `<: AnyVal` will not be checked for null.  For example

```scala
case class A(b: B)
case class B(c: C)
case class C(s: String)

?(a.b.c.s.asInstanceOf[String].charAt(2).*(2).toString.getBytes.hashCode())
```

Would be translated to:

```scala
if (a != null)
  {
    val b = a.b;
    if (b != null)
      {
        val c = b.c;
        if (c != null)
          {
            val s = c.s;
            if (s != null)
              {
                val s2 = s.asInstanceOf[String].charAt(2).$times(2).toString();
                if (s2 != null)
                  {
                    val bytes = s2.getBytes();
                    if (bytes != null)
                      bytes.hashCode()
                    else
                      null
                  }
                else
                  null
              }
            else
              null
          }
        else
          null
      }
    else
      null
  }
else
  null
```

## Performance

Here's the result of running the included jmh benchmarks:

![Throughput](throughput.png)

```
[info] Benchmark                             Mode  Cnt    Score   Error   Units
[info] Benchmarks.fastButUnsafe             thrpt   20  230.157 ± 0.572  ops/us
[info] Benchmarks.ScalaNullSafeAbsent       thrpt   20  428.124 ± 1.625  ops/us
[info] Benchmarks.ScalaNullSafePresent      thrpt   20  232.066 ± 0.575  ops/us
[info] Benchmarks.explicitSafeAbsent        thrpt   20  429.090 ± 0.842  ops/us
[info] Benchmarks.explicitSafePresent       thrpt   20  231.400 ± 0.660  ops/us
[info] Benchmarks.optionSafeAbsent          thrpt   20  139.369 ± 0.272  ops/us
[info] Benchmarks.optionSafePresent         thrpt   20  129.394 ± 0.102  ops/us
[info] Benchmarks.loopSafeAbsent            thrpt   20  114.330 ± 0.113  ops/us
[info] Benchmarks.loopSafePresent           thrpt   20   59.513 ± 0.097  ops/us
[info] Benchmarks.nullSafeNavigatorAbsent   thrpt   20  274.222 ± 0.441  ops/us
[info] Benchmarks.nullSafeNavigatorPresent  thrpt   20  181.356 ± 1.538  ops/us
[info] Benchmarks.tryCatchSafeAbsent        thrpt   20  254.158 ± 0.686  ops/us
[info] Benchmarks.tryCatchSafePresent       thrpt   20  230.081 ± 0.659  ops/us
[info] Benchmarks.monocleOptionalAbsent     thrpt   20   77.755 ± 0.800  ops/us
[info] Benchmarks.monocleOptionalPresent    thrpt   20   36.446 ± 0.506  ops/us
[info] Benchmarks.nullSafeDslAbsent         thrpt   30  228.660 ± 0.475  ops/us
[info] Benchmarks.nullSafeDslPresent        thrpt   30  119.723 ± 0.506  ops/us
[success] Total time: 3909 s, completed Feb 24, 2019 3:03:02 PM
```

You can find the source code for the JMH benchmarks [here](https://github.com/ryanstull/ScalaNullSafe/blob/ebc0ed592fa5997a9c7b868cf8cdcea590e8ae07/benchmarks/src/test/scala/com/ryanstull/nullsafe/Benchmarks.scala#L18).  If you want to run the benchmarks yourself, just run `sbt bench`, or `sbt quick-bench` for a shorter run.

These benchmarks compare all of the known ways (or at least the ways that I know of) to handle null-safety in scala.  It demonstrates 
that the explicit null-safety is the highest performing and that the 'ScalaNullSafe' macro has 
equivalent performance.

## Notes

* Using the `?` macro on an expression whose type is `<: AnyVal`, will result in returning the corresponding java wrapper
type.  For example `?(a.getInt)` will return `java.lang.Integer` instead of `Int` because the return type for this macro must
be nullable.  The conversions are the default ones defined in `scala.Predef`

* If you're having trouble with resolving the correct method when using the `?` macro with a default arg, try explicitly
specifying the type of the default
