package rms.bo.pcc.formatter

import rms.bo.pcc.model._

import scalaz._, Scalaz._

trait Components {

  implicit lazy val Header1Formatter = formatterXml[Header1] {
    case Header1(n) => <h1>{ n }</h1>
  }

  implicit lazy val Header2Formatter = formatterXml[Header2] {
    case Header2(n) => <h2>{ n }</h2>
  }

  implicit lazy val Header3Formatter = formatterXml[Header3] {
    case Header3(n) => <h3>{ n }</h3>
  }

  implicit def HeaderFormatter(
     implicit f1: Formatter[Header1]
    ,f2: Formatter[Header2]
    ,f3: Formatter[Header3]
  ) = formatter[Header] {
    case h: Header1 => f1 format h
    case h: Header2 => f2 format h
    case h: Header3 => f3 format h
  }

  implicit lazy val ParagraphFormatter = formatterXml[Paragraph] {
    case Paragraph(l) => <p>{ l.mkString }</p>
  }

  implicit lazy val BlockQuoteFormatter = formatterXml[BlockQuote] {
    case BlockQuote(l) => <ac:macro ac:name="noformat"><ac:plain-text-body>{ l.mkString("\n") }</ac:plain-text-body></ac:macro>
  }
}

