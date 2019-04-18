package persistent.post

import cats.effect.{ContextShift, IO}
//import cats.implicits._
import doobie.imports._
import scalaz.Scalaz._
import scalaz._

class PostService(implicit val cs: ContextShift[IO], implicit val xa: Transactor[IO]) {

  def insertOne(post: Post): Update0 = {
    sql"insert into post (title, tags, user, body) values (${post.title}, ${post.tags}, ${post.user}, ${post.body})"
      .update
  }

  def insertTwo(title: String, tags: List[String], user: String, body: String): Update0 = {
    sql"insert into post (title, tags, user, body) values ($title,$tags,$user,$body)"
      .update
  }

  def saveList(posts: List[Post]) = {
    posts
      .traverse(it => (insertTwo _).tuple(it).run)
      .transact(xa)
      .run
  }
}
