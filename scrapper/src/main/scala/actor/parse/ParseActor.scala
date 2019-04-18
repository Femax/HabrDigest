package actor.parse

import actor.save.SaveAction
import akka.actor.{Actor, ActorLogging, ActorRef}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elementList
import persistent.post.Post

class ParseActor(val saveActor: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case ParseAction(htmlBody) => {
      println(s"parse receive ${htmlBody.length} messages")
      val posts = htmlBody.map(ele => {
        val title = ele >> elementList(".post__title") >> text("a")
        val tags = ele >> allText(".inline-list__item_hub")
        val user = ele >> allText(".user-info__nickname_small")
        val body = ele >> allText(".post__title")
        Post(title.mkString, tags, user, body)
      }).filter(it => it.title.length > 0)
      log.info(posts.toString())
      saveActor ! SaveAction(posts)
    }
  }
}
