package code 
package snippet 

import scala.xml.{NodeSeq, Text}
import net.liftweb.http.provider.HTTPRequest
import net.liftweb.http.S
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._
import org.scalafoursquare.auth.OAuthFlow
import org.scalafoursquare.call.{HttpCaller,Caller,AuthApp}

class HelloWorld {	
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date
  lazy val oauth: OAuthFlow = new OAuthFlow(
      "SFF4JV0MVOUN2KZNP5E5Z2Z1DHMEAVKTQ2K5G5ON0HHMRSCO",
      "EWCYTWHYTCHR2RNHEXIAJDCWSZY0NKAYS4A2SGNQ5ZEROGZS",
      "http://schonberger.org:8080/callback")


  // replace the contents of the element with id "time" with the date
  def howdy = "#time *" #> date.map(_.toString)
  def fillauth = {
    val auth_url: String = oauth.authenticateUrl
    "#callurl [href]" #> auth_url
  }
  def ho = "#callback *" #> ("woot")
  def calling(space: NodeSeq): NodeSeq = {

    val callback: String = (S.param("code")) openOr "No callback code.";
    val access_code = oauth.accessTokenCaller(callback).get
    val caller = new HttpCaller(
        "SFF4JV0MVOUN2KZNP5E5Z2Z1DHMEAVKTQ2K5G5ON0HHMRSCO",
         "EWCYTWHYTCHR2RNHEXIAJDCWSZY0NKAYS4A2SGNQ5ZEROGZS")
    val authApp = new AuthApp(caller, access_code)
    val sf_foursquare = Some("4e756cdc922ef20af59bcf10")
    val response = authApp.addCheckin(sf_foursquare).get
    val user = authApp.userDetail("self").get.response.get.user
    val user_string = "Welcome " + user.firstName + " " + user.lastName.get
    bind("n", space, "callbackname" -> Text(user_string))
  }
  
  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */
}

