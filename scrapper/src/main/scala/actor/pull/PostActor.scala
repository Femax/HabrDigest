package actor.pull

import actor.parse.ParseAction
import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.Materializer
import akka.util.ByteString
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scala.concurrent.ExecutionContext

class PostActor(val parseActor: ActorRef,
                implicit val executionContext: ExecutionContext,
                implicit val materializer: Materializer) extends Actor with ActorLogging {

  import akka.pattern.pipe


  //  TODO  provide response of future in actor
  override def preStart(): Unit = {
    Http(context.system).singleRequest(
      HttpRequest(uri = "https://habr.com/ru/top/")
    ).pipeTo(self)
  }

  /**
    * get posts from habr
    **/
  def receive: Receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        val browser = JsoupBrowser()
        val doc = browser.parseString(body.utf8String)
        val list = doc >> elementList(".content-list__item")
        parseActor ! ParseAction(list)
      }
    case resp@HttpResponse(code, headers, entity, _) =>
      log.info("Request failed, response code: " + code)
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        log.info("Got response, body: " + body.utf8String)
      }
      resp.discardEntityBytes()
  }


}
