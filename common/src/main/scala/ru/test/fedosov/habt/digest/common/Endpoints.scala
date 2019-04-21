package ru.test.fedosov.habt.digest.common

import ru.test.fedosov.habt.digest.common.Post.Post

sealed class  Endpoint(val url: String){
  type dataClass
}

//case class PostEndpoint() extends Endpoint[Post]("/post")
object PostEndpoint extends Endpoint("/post"){
  override type dataClass = Post
}

object Main{
  PostEndpoint.url
}