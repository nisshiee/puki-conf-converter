package rms.bo.pcc.convert

sealed trait Component

sealed trait Header extends Component
case class Header1(name: String) extends Header
case class Header2(name: String) extends Header
case class Header3(name: String) extends Header

case class Paragraph(lines: List[String]) extends Component

object EmptyLine extends Component

case class BlockQuote(lines: List[String]) extends Component

case class Table(rows: List[Row]) extends Component

sealed trait Row
case class HeaderRow(cols: List[HeaderColumn]) extends Row
case class BodyRow(cols: List[Column]) extends Row

sealed trait Column
case class HeaderColumn(value: String) extends Column
case class BodyColumn(value: String) extends Column
