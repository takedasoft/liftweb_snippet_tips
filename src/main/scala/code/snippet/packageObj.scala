package code

package object snippet {
  
  private val mailLike = """([^\-]\S+@\S+\.\S+)""".r

  implicit def string_validations( str: String ) = new {
    def isEmail = str match{
      case mailLike(n) => true
      case _ => false
    }
  }

}
