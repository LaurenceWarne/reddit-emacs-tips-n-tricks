import mill._
import mill.scalalib._

object updateLambda extends ScalaModule {

  def scalaVersion = "2.13.8"

  val zioLambdaVersion = "1.0.0-RC6"

  def ivyDeps =
    Agg(
      ivy"dev.zio::zio-lambda:$zioLambdaVersion",
      ivy"dev.zio::zio-lambda-event:$zioLambdaVersion",
      ivy"dev.zio::zio-lambda-response:$zioLambdaVersion",
      ivy"dev.zio::zio-process:0.7.1"
    )

  def buildBoostrap =
    T {
      val jar      = assembly()
      val fileName = artifactName() + "-native-image"
      val outPath  = T.dest / fileName
      os.proc(
        "native-image",
        "--enable-http",
        "--enable-https",
        "-jar",
        jar.path,
        outPath
      ).call()
      val bootstrapFilePath = T.dest / "bootstrap"
      os.write(
        bootstrapFilePath,
        s"""#!/usr/bin/env bash
          |set -euo pipefail
          |
          |./$fileName""".stripMargin
      )
      val zipPath = T.dest / "reddit-tips-n-tricks.zip"
      os.proc("zip", zipPath, bootstrapFilePath, outPath).call()
    }
}
