package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import Helpers._

/**
 * snippetとして認識されるメソッド型
 * 
 * val render : () => CssSel
 * val render : NodeSeq => NodeSeq
 * val render : () => NodeSeq
 */
class ApplyableSnippetMethods {
  
  // old style
  def case1(xhtml:NodeSeq) : NodeSeq = bind( "c", xhtml, "result" -> "これはOK" )
  def case2 = <font color="red">{"これもOK。入力NodeSeqは省略できるが全コンテンツ入れ替えになる"}</font>
  
  // new style ( designer friendly template )
  def case3 = ".result" #> "これはOK"

  def badcase1(xhtml:NodeSeq) = "これはNG。戻り型はNodeSeq" // Text("これはOK")
  def badcase2(xhtml:NodeSeq) = ".result" #> "これはNG。 NodeSeq => CssSel 型は認識されないし、そもそも意味がない。"
}

object ApplyableSnippetMethods {
  def case4 : CssSel = ".result" #> "これはOK。シングルトンで良いケースでのみ使用"
}
