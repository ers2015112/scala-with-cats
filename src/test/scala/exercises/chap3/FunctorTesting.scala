package exercises.chap3

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

import cats.Functor
import cats.instances.option._
import cats.instances.function._ // for Functor
import cats.syntax.functor._
import cats.instances.list._
    
class FunctorTesting  extends AnyFreeSpec with Matchers  {
  "Functors must" - {
      "List int map creates list of incremented values" in {
          List(1,2,3).map(n => n + 1) mustBe List(2,3,4)
      }
      "Apply successive maps" in {
          List(1, 2, 3).
            map(n => n + 1).
            map(n => n * 2).
            map(n => s"${n}!") mustBe List("4!", "6!", "8!")
      }
      "Compute futures correctly" in {
            import scala.concurrent.{Future, Await}
            import scala.concurrent.ExecutionContext.Implicits.global
            import scala.concurrent.duration._
            
            val future: Future[String] =
            Future(123).
            map(n => n + 1).
            map(n => n * 2).
            map(n => s"${n}!")
            Await.result(future, 1.second) mustBe "248!"
            // res2: String = "248!"
      }
  }
  "cats functors should compose and lift" in {


    val list1 = List(1,2,3)

    val list2 = Functor[List].map(list1)(_ * 2)
    list2 mustBe List(2,4,6)

    val option1 = Option(123)

    val option2 = Functor[Option].map(option1)(_.toString())
    option2 mustBe Some("123")

    val func = (x: Int) => x + 1

    val liftedFunc = Functor[Option].lift(func)

    liftedFunc(Option(1)) mustBe Some(2)
  }

  "Cats functors with functions must" in {

  
    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => s"${a}!"
    val func4 = func1.map(func2).map(func3)

    func4(123) mustBe "248!"
  }

  "Functor math" in {
    def doMath[F[_]](start: F[Int])
      (implicit functor: Functor[F]): F[Int] =
        start.map(n => n + 1 * 2)


      doMath(Option(20)) mustBe Some(22)
  }
}
