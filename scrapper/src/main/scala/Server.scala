import actor.parse.ParseActor
import actor.pull.{PostActor, RequestAction}
import actor.save.SaveActor
import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.ibm.couchdb.CouchDb
import ru.test.fedosov.habt.digest.common.Post.PostService

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}


object Server extends App {
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val couch = CouchDb(host = "127.0.0.1", port = 5984, https = false, username = "admin", password = "password")

  val host = "0.0.0.0"
  val port = 9000
  val saveActor = system.actorOf(Props(new SaveActor(new PostService(), executionContext)))
  val parseActor = system.actorOf(Props(new ParseActor(saveActor)))
  val pullActor = system.actorOf(Props(new PostActor(parseActor, executionContext, materializer)), name = "pull")

  system.scheduler.schedule(0 second, 1 seconds, pullActor, RequestAction())
}