import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport.npmDependencies

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
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    name := "front-scalajs",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % "0.9.2",
      "com.github.japgolly.scalajs-react" %%% "core" % "1.1.1"),
    npmDependencies in Compile ++= Seq(
      "react" -> "15.6.1",
      "react-dom" -> "15.6.1")
  )
  .dependsOn(
    http
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
