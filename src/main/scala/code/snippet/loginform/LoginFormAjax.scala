package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import Helpers._

import js._
import JsCmds._
import JE._

import code.model.User

/**
 * Ajax型のFORM。
 * 
 * このSnippet以外は何も編集する必要がないことに注目。
 */
class LoginFormAjax {
  
  object login extends SessionVar("")
  object password extends SessionVar("")
  
  private def clearSession = {
    login("")
    password("")
  }
  
  def render = {
    
    def testLogin(s:String)  : JsCmd = {
      if( s.isEmail ){ 
        login(s)
        Noop
      }else{
        val msg = "メールアドレスを\n正しく入力してください".encJs
        Run("""alert(%s);$("input[type=text]").css("border-color","red");""".format(msg))
      }
    }
    def testPassword(s:String) : JsCmd = {
      if( s.length >= 4 ){
        password(s)
        S.error("password_error","")
      }else{
        S.error("password_error","パスワードを４文字以上で入力してください")
      }
    }
    
    def process : JsCmd = {
      User.authenticate( login, password ) match {
        case Full(user) => 
          clearSession
          S.redirectTo("mypage")
        case _ =>
          Run("""alert("ログインできません");""")
      }
    }

    "name=login" #> SHtml.ajaxText( login.is, testLogin(_), "placeholder"->"ex)user1@example.com" ) &
    "name=password" #> SHtml.ajaxText( password.is, testPassword(_),"type"->"password", "maxlength"->"10") &
    "type=submit" #> SHtml.ajaxButton( <span>Join !</span>, process _ )
  }
  
}
