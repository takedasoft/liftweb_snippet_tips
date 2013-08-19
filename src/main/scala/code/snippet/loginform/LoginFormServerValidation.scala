package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import code.model.User

/**
 * validation、エラーバック時の再表示
 */
class LoginFormServerValidation {

  object login extends RequestVar("")
  object password extends RequestVar("")
  
  def render = {
    
    println("--- render ---")
    
    def process : Unit = {
      
      //simple validation logic
      if( login.length < 1 ) S.error("login is empty")
      if( password.length < 1 ) S.error("password is empty")
      if( S.errors.size > 0 ){
          return // render restart
      }
      User.authenticate( login, password ) match {
        case Full(user) => 
          S.notice( "ようこそ %s さん".format(user.nickname) )
          S.redirectTo("mypage")
        case _ =>
          S.error( "ログインできません" )
          // render restart
      }
    }

    "name=login" #> SHtml.text( login.is, (s) => {println( "-- set login %s".format(s) ); login(s)} , "placeholder"->"ex)user1" ) &
    "name=password" #> SHtml.password( password.is, (s) => {println( "-- set password %s".format(s) ); password(s)} , "maxlength"->"10") &
    "type=submit" #> SHtml.button( <span>Join !</span>, process _ )
  }
  
}
