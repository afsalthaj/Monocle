package monocle

import scalaz.Monoid

final case class Getter[S, A](get: S => A) {

  // Compose
  def composeFold[B](other: Fold[A, B]): Fold[S, B] = asFold composeFold other
  def composeGetter[B](other: Getter[A, B]): Getter[S, B] =
    Getter(other.get compose get)
  def composeLens[B, C, D](other: Lens[A, B, C, D]): Getter[S, C] = composeGetter(other.asGetter)
  def composeIso[B, C, D](other: Iso[A, B, C, D]): Getter[S, C] = composeGetter(other.asGetter)

  // Optics transformation
  def asFold: Fold[S, A] = new Fold[S, A]{
    def foldMap[B: Monoid](s: S)(f: A => B): B = f(get(s))
  }

}