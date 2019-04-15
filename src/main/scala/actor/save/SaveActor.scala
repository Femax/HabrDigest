package actor.save

import java.util.concurrent.Future

import akka.actor.{Actor, ActorLogging}
import persistent.post.PostService
import reactivemongo.api.DefaultDB

class SaveActor(postService: PostService) extends Actor with ActorLogging {
  override def receive: Receive = {
    case SaveAction(list) => {
      postService.save(list)
    }
  }
}
