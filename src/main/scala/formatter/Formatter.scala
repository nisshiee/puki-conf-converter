package rms.bo.pcc.formatter

object Formatter {

  def format[T](t: T)(implicit f: Formatter[T]) = f format t
}

trait Formatter[T] {
  def format(t: T): String
}
