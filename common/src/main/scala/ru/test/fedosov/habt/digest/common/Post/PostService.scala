package ru.test.fedosov.habt.digest.common.Post

import com.ibm.couchdb.api.Documents
import com.ibm.couchdb.{CouchDb, TypeMapping}

class PostService(implicit val couch: CouchDb) {
  val typeMapping = TypeMapping(classOf[Post] -> "Post")
  val dbName = "couchdb-scala-basic-example"
  val db = couch.db(dbName, typeMapping)
  val client = couch.client
  val documents = new Documents(client, dbName, typeMapping) {
    if (!couch.dbs.getAll.unsafeRun().contains(dbName))
      couch.dbs.create(dbName).unsafeRun()
    println("created")
  }

  def saveList(posts: List[Post]): List[Post] = {
    db.docs.createMany(posts).unsafeRun()
    val data = documents.getMany.includeDocs[Post].build.query.unsafeRun()
    couch.client.client.shutdown
    data.rows.map(it => it.doc.doc).toList
  }

  def getList(): List[Post] = {
    val data = documents.getMany.includeDocs[Post].build.query.unsafeRun()
    couch.client.client.shutdown
    data.rows.map(it => it.doc.doc).toList
  }
}
