package actor.save

import akka.actor.{Actor, ActorLogging}
import akka.stream.Materializer
import ru.test.fedosov.habt.digest.common.Post.{Post, PostService}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class SaveActor(postService: PostService, implicit val executionContext: ExecutionContext) extends Actor with ActorLogging {

  object Commands {
    def getList(list: List[Post]) = Future {
      postService.saveList(list)
    }
  }

  override def receive: Receive = {
    case SaveAction(list) =>
      val data = Commands
        .getList(list)
        .onComplete({
          case Success(savedList) => log.info(s"save success $savedList")
          case Failure(e) => log.error(e.getMessage)
        })
  }
}
