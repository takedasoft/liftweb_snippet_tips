package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import code.model.User

/**
 * S.paramでリクエストパラメータを受け取る
 */
class LoginFormParam {
  
  def render = {
    
    if( S.post_? ){
      User.authenticate( S.param("login").openOr(""), S.param("password").openOr("") ) match {
        case Full(user) => 
          S.notice( "ようこそ %s さん".format(user.nickname) )
          S.redirectTo("mypage")
        case _ =>
          S.error( "ログインできません" )
      }
    }
    
    PassThru //何も変換しないCssSel
  }
  
}
