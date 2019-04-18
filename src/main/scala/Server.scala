import actor.parse.ParseActor
import actor.pull.{PostRequest, RequestAction}
import actor.save.SaveActor
import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import cats.effect.IO
import doobie.imports.Transactor
import persistent.post.PostService

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}


object Server extends App {
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val cs = IO.contextShift(ExecutionContext.global)
  implicit val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", ""
  )
  val host = "0.0.0.0"
  val port = 9000
  val saveActor = system.actorOf(Props(new SaveActor(new PostService())))
  val parseActor = system.actorOf(Props(new ParseActor(saveActor)))
  val pullActor = system.actorOf(Props(new PostRequest(parseActor)), name = "pull")

  system.scheduler.schedule(0 second, 5 seconds, pullActor, RequestAction())
}