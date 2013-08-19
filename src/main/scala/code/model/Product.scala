package code
package model

import scala.collection.mutable.HashMap
import net.liftweb.util._
import net.liftweb.common._

/**
 */
class Product(var id:Long, var title:String,  var price:Int ) {
  def id(id:Long):Product = { this.id = id ; this }
  def title(title:String):Product = { this.title = title ; this }
  def price(price:Int):Product = { this.price = price ; this }
  def save : Product = Product.save(this)
}

object Product {
  
  private val _db  = new HashMap[Long,Product]
  
  def findAll:List[Product] = {
    if( _db.size < 1 ){
      Seq( (1L,"killer queen",150),(2L,"hammer to fall",150),(3L,"we will rock you",250),(4L,"we are the champion",250) ).map( p => {
          new Product(p._1,p._2,p._3)
        } ).foreach( p => _db += p.id -> p )
    }
    _db.values.toList.sortWith( (a,b) => a.id <= b.id )
  }
  
  private def save(p:Product) : Product = {
    _db.update(p.id, p)
    p
  }
  
  def reset = {
    _db.clear
  }
}
