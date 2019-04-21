

name := "HabrDigest"

enablePlugins(DockerComposePlugin)

version := "0.1"

scalaVersion := "2.12.8"
scalacOptions += "-Ypartial-unification" // 2.11.9+
val scalaJSReactVersion = "1.2.0"
val scalaCssVersion = "0.5.5"
val reactJSVersion = "16.3.2"

scalaJSUseMainModuleInitializer := true



lazy val dependency = new {

  private val akkaActorV = "2.5.19"
  private val akkaStreamV = "2.5.19"
  private val akkaHttpV = "10.1.3"
  private val scalaScrapperV = "2.1.0"
  private val couchDbV = "1.0.3.1"
  private val scalatestV = "3.0.5"
  private val sprayJsonV = "10.1.8"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaActorV
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaStreamV
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpV
  val scalaScrapper = "net.ruippeixotog" %% "scala-scraper" % scalaScrapperV
  //https://github.com/GuyIncognito1986/couchdb-scala couch db driver for scala 2.12.*
  val couchDb = "io.github.guyincognito1986" %% "couchdb-scala" % couchDbV
  val akkaSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % sprayJsonV

  val scalatest = "org.scalatest" %% "scalatest" % scalatestV % "test"
}

lazy val jsDepenndency = new {

}

lazy val akkaDep = Seq(dependency.akkaActor, dependency.akkaStream, dependency.couchDb, dependency.scalatest)

lazy val global = project
  .in(file("."))
  .aggregate(
    scrapper,
    http,
    front
  )

lazy val common = project
  .in(file("./common"))
  .settings(
    name := "common",
    libraryDependencies ++= Seq(dependency.couchDb)
  )

lazy val scrapper = project
  .in(file("./scrapper"))
  .settings(
    name := "scrapper",
    libraryDependencies ++= akkaDep
      ++ Seq(dependency.akkaHttp, dependency.scalaScrapper)
  ).dependsOn(
  common
)

lazy val http = project
  .in(file("./http"))
  .settings(
    name := "http",
    libraryDependencies ++= akkaDep ++ Seq(
      dependency.akkaHttp,
      dependency.akkaSprayJson
    )
  ).dependsOn(
  common
)


lazy val front = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    name := "front-scalajs",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % "0.9.2",
      "com.github.japgolly.scalajs-react" %%% "core" % "1.4.1",
      "com.github.japgolly.scalajs-react" %%% "extra" % "1.4.1"),
    scalaJSUseMainModuleInitializer := true,
    npmDependencies in Compile ++= Seq(
      "react" -> "16.7.0",
      "react-dom" -> "16.7.0")
  )
  .dependsOn(
    common
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
