package monocle.function

import monocle.std.function._
import monocle.syntax._
import org.specs2.scalaz.Spec


class FunctionExample extends Spec {

  "curry curries a function" in {
    def f(a: Int, b: Int): Int = a + b

    (f _ applyIso curry get)(1)(2) ==== 3
  }

  "uncurry uncurries a function" in {
    def f(a: Int)(b: Int): Int = a + b

    (f _ applyIso uncurry get)(1, 2) ==== 3
  }

  "curry and uncurry should work with functions up to 5 arguments" in {
    def f(a: Int)(b: Int)(c: Int)(d: Int)(e: Int): Int =
      a + b + c + d + e

    (f _ applyIso uncurry get)(1, 2, 3, 4, 5) ==== 15
  }

  "If we compose with the curried function, it should also compose in the uncurried version" in {
    def f(a: Int, b: Int): Int =
      2 * a + 3 * b

    /**
     * Note: We can only stay in the same function type, because curry is a SimpleIso.
     * So we can't for example modify by applying the first argument.
     **/
    /**
     * Here we increase the first argument by one, and then apply the function,
     * Which is easier to do when the function is curried rather than uncurried,
     * so we do the modification through the Iso.
     **/
    (f _ applyIso curry modify (_ compose (_ + 1)))(5, 7) ==== (2 * 6 + 3 * 7)

  }

  "flip exchanges the the first 2 parameters of a function" in {
    def f(a: Int, b: Double): Double = a + b

    (f _ applyIso curry composeIso flip composeIso uncurry get)(3.2, 1) ==== 4.2
  }

  "Increase the second argument of a 2 argument function" in {
    def f(a: Int, b: Int): Int =
      2 * a + 3 * b

    /**
     * If we wanted to increase the second argument instead, we could use flip.
     */
    (f _ applyIso curry composeIso flip modify (_ compose (_ + 1)))(5, 7) ==== (2 * 5 + 3 * 8)
  }

}
