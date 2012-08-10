package rms.bo.pcc

import scala.xml.Elem

package object formatter extends Components
{

  def formatter[T](f: T => String) = new Formatter[T] {
    def format(t: T) = f(t)
  }

  def formatterXml[T](f: T => Elem) = new Formatter[T] {
    def format(t: T) = f(t).toString
  }
}
