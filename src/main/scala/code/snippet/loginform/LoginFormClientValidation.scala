package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import code.model.User

/**
 * "普通のサーブレット"な考え方のSnippet
 */
class LoginFormClientValidation {
  
  object login extends SessionVar("")
  object password extends SessionVar("")
  private def clearSession = {
    login("")
    password("")
  }

  def render : CssSel = if(S.get_?) doGet else doPost
  
  private def doGet : CssSel = {
    "name=login" #> SHtml.text( login.is, login(_) , "placeholder"->"ex)user1" ) &
    "name=password" #> SHtml.password( password.is, password(_) , "maxlength"->"10") &
    "type=submit" #> SHtml.button( <span>Join !</span>, ()=>(), "onclick"->"""if( confirm("よろしいですか？") ){ submit(); }""" )
  }
  
  private def doPost : Nothing = {
    User.authenticate( login, password ) match {
      case Full(user) => 
        S.notice( "ようこそ %s さん".format(user.nickname) )
        S.redirectTo("mypage")
      case _ =>
        S.error( "ログインできません" )
        S.redirectTo("clientvalidation")
    }
  }
}
