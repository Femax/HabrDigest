package persistent.post

import com.ibm.couchdb.api.Documents
import com.ibm.couchdb.{CouchDb, TypeMapping}

class PostService(implicit val couch: CouchDb) {
  val typeMapping = TypeMapping(classOf[Post] -> "Post")
  val dbName = "couchdb-scala-basic-example"
  val db = couch.db(dbName, typeMapping)
  val client = couch.client
  val documents = new Documents(client, dbName, typeMapping)
  //  val postView = CouchView(map =
  //    """
  //      |function(doc) {
  //      |  emit(doc._id, {title: doc.doc.title, user: doc.doc.user, tags: doc.doc.tags, body: doc.doc.body});
  //      |}
  //    """.stripMargin)
  //
  //  val designPost = CouchDesign(
  //    name = "post-design",
  //    views = Map("post-view" -> postView))

  {
    //        couch.dbs.create(dbName).unsafeRun()
    //    db.design.create(designPost).unsafeRun()
    println("created")
  }

  def saveList(posts: List[Post]): List[Post] = {
      db.docs.createMany(posts).unsafeRun()
      val data = documents.getMany.includeDocs[Post].build.query.unsafeRun()
      couch.client.client.shutdown
      data.rows.map(it => it.doc.doc).toList
  }
}
