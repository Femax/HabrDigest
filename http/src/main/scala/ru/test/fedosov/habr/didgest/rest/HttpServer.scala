package ru.test.fedosov.habr.didgest.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.ibm.couchdb.CouchDb
import ru.test.fedosov.habt.digest.common.Post.PostService

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.io.StdIn

object HttpServer extends App {
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val couch = CouchDb(host = "127.0.0.1", port = 5984, https = false, username = "admin", password = "password")

  val host = "0.0.0.0"
  val port = 9001


  val bindingFuture = Http().bindAndHandle(new PostActor(new PostService).route, host, port)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}
