package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import code.model.User

/**
 * LiftらしいFORMの基本形。
 * 
 */
class LoginFormEasy {
  
  def render = {
    
    println("---- render ---")
    
    var login :String = ""
    var password:String = ""
    
    def process : Unit = {
      User.authenticate( login, password ) match {
        case Full(user) => 
          S.notice( "ようこそ %s さん".format(user.nickname) )
          S.redirectTo("mypage")
        case _ =>
          S.error( "ログインできません" )
          //再度 renderが実行される。
      }
    }

    "name=login" #> SHtml.text( login, (s) => { println( "-- set login %s".format(s) ); login = s } ) &
    "name=password" #> SHtml.password( password,(s) => { println( "-- set password %s".format(s) ); password = s } ) &
    "type=submit" #> SHtml.button( <span>Join!</span>, { () => println("-- submit! --"); process } )
    
    /* 他にも書き方色々
    "name=login" #> SHtml.onSubmit( login = _ ) &
    "name=password" #> SHtml.onSubmit( password = _ ) &
    "type=submit" #> SHtml.onSubmitUnit( () => {
      User.authenticate( login, password ) match {
        case Full(user) => 
          S.notice( "ようこそ %s さん".format(user.nickname) )
          S.redirectTo("mypage")
        case _ =>
          S.error( "ログインできません" )
      }
    } )
    */
   /*
    "name=login" #> SHtml.text( login, login = _ , "placeholder"->"ex)user1" ) &
    "name=password" #> SHtml.password( password, password = _ , "maxlength"->"10") &
    "type=submit" #> SHtml.button( <span>Join!</span>, process _ )
    */
    
  }
  
}
