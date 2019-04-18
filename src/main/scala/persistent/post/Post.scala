package persistent.post

case class Post(title: String,
                    tags: List[String],
                    user: String,
                    body: String)