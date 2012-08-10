import rms.bo.pcc.convert._
import scalax.io._
import scalax.file._
import ImplicitConversions._

object App extends App {
  val inputFile: Path = "test.txt"
  val input = inputFile.slurpString
  val result = Parser.parse(input)
  val output = result.get map { e => e.toString + "\n" }
  val outputFile: Path = "testoutput.txt"
  outputFile.writeStrings(output, "\n")
}
