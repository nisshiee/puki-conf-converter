package rms.bo.pcc.model

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

sealed trait ItemList extends Component
case class ItemList1(items: List[ListedItem1]) extends ItemList
case class ItemList2(items: List[ListedItem2]) extends ItemList with ListedItem1
case class ItemList3(items: List[ListedItem]) extends ItemList with ListedItem2

sealed trait OrderedItemList extends Component
case class OrderedItemList1(items: List[ListedItem1]) extends OrderedItemList
case class OrderedItemList2(items: List[ListedItem2]) extends OrderedItemList with ListedItem1
case class OrderedItemList3(items: List[ListedItem]) extends OrderedItemList with ListedItem2

sealed trait ListedItem1
sealed trait ListedItem2 extends ListedItem1

case class ListedItem(value: String) extends ListedItem2


