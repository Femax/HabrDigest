name := "HabrDigest"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification" // 2.11.9+


lazy val dependency = new {

  private val akkaActorV = "2.5.13"
  private val akkaStreamV = "2.5.13"
  private val akkaHttpV = "10.1.3"
  private val scalaScrapperV = "2.1.0"
  private val couchDbV = "1.0.3.1"
  private val scalatestV = "3.0.5"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaActorV
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaStreamV
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpV
  val scalaScrapper = "net.ruippeixotog" %% "scala-scraper" % scalaScrapperV
  //https://github.com/GuyIncognito1986/couchdb-scala couch db driver for scala 2.12.*
  val couchDb = "io.github.guyincognito1986" %% "couchdb-scala" % couchDbV
  val scalatest = "org.scalatest" %% "scalatest" % scalatestV % "test"
}

lazy val akkaDep = Seq(dependency.akkaActor, dependency.akkaStream, dependency.couchDb, dependency.scalatest)
lazy val global = project
  .in(file("."))
  .aggregate(
    scrapper,
    http,
    front,
    docker
  )

lazy val scrapper = project
    .in(file("./scrapper"))
  .settings(
    name := "scrapper",
    libraryDependencies ++= akkaDep
      ++ Seq(dependency.akkaHttp, dependency.scalaScrapper)
  )

lazy val http = project
  .settings(
    name := "akka-http",

    libraryDependencies ++= akkaDep ++ Seq(
      dependency.akkaHttp,
    )
  )


lazy val front = project
  .settings(
    name := "front-scalajs",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= Seq()
  )
  .dependsOn(
    http
  )

lazy val docker = project
    .in(file("./docker"))
  .settings(
    name := "docker"
  )

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8")
