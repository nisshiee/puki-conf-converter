package rms.bo.pcc.convert

import util.parsing.combinator._
import scalaz._, Scalaz._

object Parser extends RegexParsers {

  override def skipWhitespace = false

  def parse(src: String) = parseAll(page, src + "\n")

  def eol = opt('\r') ~> '\n'

  def header = ("""\*{1,3}""".r <~ rep(' ')) ~ """[^\r\n]*""".r <~ eol ^^ {
    case "*"   ~ n => Header1(n)
    case "**"  ~ n => Header2(n)
    case "***" ~ n => Header3(n)
  }

  def itemList =
    itemList3 | orderedItemList3 |
    itemList2 | orderedItemList2 |
    itemList1 | orderedItemList1

  def itemList1 = rep1(
    "-" ~> not('-') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol ^^ ListedItem.apply |
    itemList2 | orderedItemList2 |
    itemList3 | orderedItemList3
  ) ^^ ItemList1.apply

  def orderedItemList1 = rep1(
    "+" ~> not('+') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol ^^ ListedItem.apply |
    itemList2 | orderedItemList2 |
    itemList3 | orderedItemList3
  ) ^^ OrderedItemList1.apply

  def itemList2 = rep1(
    "--" ~> not('-') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol ^^ ListedItem.apply |
    itemList3 | orderedItemList3
  ) ^^ ItemList2.apply

  def orderedItemList2 = rep1(
    "++" ~> not('+') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol ^^ ListedItem.apply |
    itemList3 | orderedItemList3
  ) ^^ OrderedItemList2.apply

  def itemList3 = rep1("---" ~> not('-') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol) ^^ {
    _ ∘ ListedItem.apply |> ItemList3.apply
  }

  def orderedItemList3 = rep1("+++" ~> not('+') ~> rep(' ') ~> """[^\r\n]*""".r <~ eol) ^^ {
    _ ∘ ListedItem.apply |> OrderedItemList3.apply
  }

  def paragraphPrefix = not("|" | " " | "*" | "-")

  def paragraph = rep1(paragraphPrefix ~> """[^\r\n]+""".r <~ eol) ^^ {
    case ls => Paragraph(ls)
  }

  def emptyLine = eol ^^ { case _ => EmptyLine }

  def blockQuote = rep1(' ' ~> """[^\r\n]*""".r <~ eol) ^^ {
    case ls => BlockQuote(ls)
  }

  def table = rep1(headerRow | bodyRow) ^^ Table.apply

  def headerRow = '|' ~> rep1(opt('~') ~> """[^\r\n\|]*""".r <~ '|') <~ 'h' <~ eol ^^ {
    list => HeaderRow(list map HeaderColumn.apply)
  }

  def bodyRow = '|' ~> rep1((headerColumn | bodyColumn) <~ '|') <~ eol ^^ BodyRow.apply

  def headerColumn = '~' ~> opt("""\s+""".r) ~> """[^\r\n\|]*""".r ^^ HeaderColumn.apply
  def bodyColumn = not('~') ~> """[^\r\n\|]*""".r ^^ BodyColumn.apply

  def page: Parser[List[Component]] = rep1(
    table |
    blockQuote |
    emptyLine |
    header |
    itemList |
    paragraph
  )


}

