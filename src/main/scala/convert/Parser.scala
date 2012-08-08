package rms.bo.pcc.convert

import util.parsing.combinator._

object Parser extends RegexParsers {

  override def skipWhitespace = false

  def parse(src: String) = parseAll(page, src + "\n")

  def eol = opt('\r') ~> '\n'

  def header = """\*{1,3}""".r ~ """[^\r\n]*""".r <~ eol ^^ {
    case "*"   ~ n => Header1(n)
    case "**"  ~ n => Header2(n)
    case "***" ~ n => Header3(n)
  }

  def paragraph = rep1("""[^\r\n]+""".r <~ eol) ^^ {
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
    paragraph
  )


}

