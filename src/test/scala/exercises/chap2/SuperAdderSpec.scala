package exercises.chap2

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import cats.instances.int._
import cats.instances.option._


class SuperAdderSpec  extends AnyFreeSpec with Matchers  {
    val sa = new SuperAdder()
    "SuperAdder must" - {
      "function adder adds items in a list" in {

          sa.add(List(1,2,3,4)) mustBe 10
      }
      "function adders adds option ints in a list" in {
          sa.add(List(Option(1), Option(2), Option(3))) mustBe Some(6)
      }
      "function add orders together" in {
          val orders = List(Orders(1.2,1), Orders(1.1,1))
          sa.add(orders) mustBe Orders(2.3,2)
      }
  }
}
