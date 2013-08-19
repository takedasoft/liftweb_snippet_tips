package code
package snippet

import scala.xml.{NodeSeq, Text, Elem,UnprefixedAttribute,Null}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

/**
 * Radioボタンのいろいろな生成方法
 */
class ComplexRadio {
  
  object radioChoice extends RequestVar("設定しない")
  object settingValue extends RequestVar("")
  
  def simple = {
    "type=radio" #> SHtml.radio(Seq("設定する","設定しない"), Full(radioChoice), radioChoice(_) ).toForm &
    "type=submit" #> SHtml.submit("save", () => () )
  }
  
  def layout = {
    val holder = SHtml.radio(Seq("設定する","設定しない"), Full(radioChoice), radioChoice(_) )
    
    "type=radio" #> holder.items.map( i => i.xhtml ++ <span> -- {i.key}</span> ) &
    "type=submit" #> SHtml.submit("save", () => () )
  }
  
  def render = {
    val holder = SHtml.radio(Seq("設定する","設定しない"), Full(radioChoice), radioChoice(_) )
    
    "type=radio" #> { 
      holder("設定する") ++ <span>設定する</span> ++ SHtml.text( settingValue, settingValue(_) ) ++ <br/> ++
      holder("設定しない") ++ <span>設定しない</span>
    } &
    "type=submit" #> SHtml.submit("save", () => () )
  }
  
  def withlabel1 = {
    val holder = SHtml.radio(Seq("設定する","設定しない"), Full(radioChoice), radioChoice(_) )
    "type=radio" #> { 
      ( holder("設定する").asInstanceOf[Elem] % new UnprefixedAttribute("id","radio1",Null) ) ++ <label for="radio1">設定する</label> ++ 
      ( holder("設定しない").asInstanceOf[Elem] % new UnprefixedAttribute("id","radio2",Null) ) ++ <label for="radio2">設定しない</label>
    } &
    "type=submit" #> SHtml.submit("save", () => () )
  }

  def withlabel2 = {
    def addDomId(domid:String) = ("input [id]" #> domid)
    val holder = SHtml.radio(Seq("設定する","設定しない"), Full(radioChoice), radioChoice(_) )
    "type=radio" #> { 
      addDomId("radio3")(holder("設定する")) ++ <label for="radio3">設定する</label> ++ 
      addDomId("radio4")(holder("設定しない")) ++ <label for="radio4">設定しない</label>
    } &
    "type=submit" #> SHtml.submit("save", () => () )
  }

}
