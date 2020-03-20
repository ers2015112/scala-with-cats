package sandbox.json


sealed trait Printable[A] {
  def format(value: A): String = ???
}

object PrintableInstances {
  implicit val printableString: Printable[String] =
    new Printable[String] {
      override def format(value: String): String = value
    }
  implicit val printableInt: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }
  implicit val printableCat: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = s"${value.name} is a ${value.age} year-old ${value.color} cat."
  }
}

object Printable {
  def format[A](value: A)(implicit printable: Printable[A]) = printable.format(value)
  def print[A](value: A)(implicit printable: Printable[A]): Unit = println(printable.format(value))
}

final case class Cat(name: String, age: Int, color: String)


sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A) : Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      override def write(value: String): Json = JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      override def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
       ))
    }

  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
    new JsonWriter[Option[A]] {
      override def write(value: Option[A]): Json =
        value match {
          case Some(v) => writer.write(v)
          case None => JsNull
        }

    }
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }
}

