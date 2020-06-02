import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import cats.Monoid
import cats.instances.int._
// for Monoid
import cats.instances.option._ // for Monoid

class MonoidTesting extends AnyFreeSpec with Matchers {

    "Monoid and Semi groups must" - {
        "Compose options properly" in {
            val a = Option(22)
            // a: Option[Int] = Some(22)
            val b = Option(20)
            // b: Option[Int] = Some(20)
            Monoid[Option[Int]].combine(a, b) mustBe Some(42)
        }
        "Use semigroup syntax" in {
            import cats.instances.string._ // for Monoid
            import cats.syntax.semigroup._ // for |+|
            "Hi " |+| "there" |+| Monoid[String].empty mustBe "Hi there"
            import cats.instances.int._ // for Monoid
            1 |+| 2 |+| Monoid[Int].empty mustBe 3
        }
    }
  
}
