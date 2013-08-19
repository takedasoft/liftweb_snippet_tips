package code
package model

import net.liftweb.util._
import net.liftweb.common._

/**
 * Mock User
 */
class User {
  var login : String = _
  var password:String = _
  var nickname:String = _
}

object User {
  def authenticate( login:String, password:String ) : Box[User] = {
    if( login == "user1" && password == "pass1" ) {
      val u = new User
      u.login = login
      u.password = password
      u.nickname = "testuser1"
      Full(u)
    }else Empty
  }
}
