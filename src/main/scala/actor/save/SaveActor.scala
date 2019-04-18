package actor.save

import akka.actor.{Actor, ActorLogging}
import persistent.post.PostService

class SaveActor(postService: PostService) extends Actor with ActorLogging {
  override def receive: Receive = {
    case SaveAction(list) =>
      println("123")
      postService.saveList(list)
  }
}
