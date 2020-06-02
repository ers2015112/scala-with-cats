package exercises.chap2

import cats.Monoid
import cats.instances._
// for Monoid
import cats.instances.option._ // for Monoid
import cats.syntax.semigroup._ // for |+|

class SuperAdder{
  def add[A](items: List[A])(implicit monoid: Monoid[A]): A = items.foldRight(monoid.empty)(_ |+| _)
}
