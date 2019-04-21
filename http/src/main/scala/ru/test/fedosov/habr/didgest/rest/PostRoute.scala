package ru.test.fedosov.habr.didgest.rest

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import ru.test.fedosov.habt.digest.common.Endpoints
import ru.test.fedosov.habt.digest.common.Post.{Post, PostService}
import spray.json.DefaultJsonProtocol


trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val postFormat = jsonFormat4(Post)
}


class PostRoute(val postService: PostService) extends Directives with JsonSupport {

  // format: OFF
  val route =
    pathPrefix(Endpoints.postEndpoint.url) {
      get {
        pathSingleSlash {
          val posts = postService.getList()
          complete(posts) // will render as JSON
        }
      } ~
        post {
          entity(as[Post]) { post => // will unmarshal JSON to Order
            val itemsCount = post.body.length
            val itemNames = post.title
            complete(s"Ordered $itemsCount items: $itemNames")
          }
        }
    }
  // format: ON
}