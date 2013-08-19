package code
package snippet

import scala.collection.mutable.HashMap
import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import code.model.Product

/**
 * Productの行列をまとめて編集するFORM
 */
class LineEditor {
  
  object changes extends RequestVar( new HashMap[Long,Product] )
  private def get(id:Long) =  changes.getOrElseUpdate(id, new Product(id,"",0))
  private def updateTitle(id:Long,title:String) = 
    changes += id ->get(id).title(title)
  private def updatePrice(id:Long,priceString:String) = 
    asInt(priceString).foreach( price => 
      changes += id -> get(id).price(price)
    )
  
  def render = {
    ".products *" #> Product.findAll.map( p => {
        ".id" #> p.id.toString &
        ".title" #> SHtml.text( p.title, updateTitle(p.id, _) ) &
        ".price" #> SHtml.text( p.price.toString, updatePrice(p.id,_) ) 
    } ) &
    "type=submit" #> { 
      SHtml.submit("save", () => {
        changes.is.values.foreach( p => p.save )
        S.notice("更新しました")
        S.redirectTo("lineeditor")
      } ) ++ 
      SHtml.submit("reset all", () => Product.reset ) }
  }
  
}
