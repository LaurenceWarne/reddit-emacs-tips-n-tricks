import $ivy.`com.goyeau::mill-scalafix::0.2.9`
import com.goyeau.mill.scalafix.ScalafixModule
import mill._
import mill.scalalib._

object updateLambda extends ScalaModule with ScalafixModule {

  def scalaVersion = "2.13.8"

  def scalafixIvyDeps = Agg(ivy"com.github.liancheng::organize-imports:0.6.0")

  val zioLambdaVersion = "1.0.0-RC6"

  def ivyDeps =
    Agg(
      ivy"dev.zio::zio-lambda:$zioLambdaVersion",
      ivy"dev.zio::zio-lambda-event:$zioLambdaVersion",
      ivy"dev.zio::zio-lambda-response:$zioLambdaVersion",
      ivy"dev.zio::zio-aws-cloudwatch:5.17.271.1",
      ivy"dev.zio::zio-aws-netty:5.17.271.1",
      ivy"dev.zio::zio-process:0.7.1"
    )
 
  def buildBootstrap =
    T {
      val jar      = assembly()
      val fileName = artifactName() + "-native-image"
      val outPath  = T.dest / fileName
      val resDir = resources().headOption
      val res_conf_path = resDir.map(_.path / "resource_config.json")
      val refl_conf_path = resDir.map(_.path / "reflection_config.json")
      val resConf =
        // https://www.graalvm.org/22.0/reference-manual/native-image/Reflection/
        res_conf_path.map(p => s"-H:ResourceConfigurationFiles=$p").toList ++
        refl_conf_path.map(p => s"-H:ReflectionConfigurationFiles=$p")
      os.proc(
        "native-image","--enable-http","--enable-https",
        resConf, "-jar", jar.path, outPath
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
      os.proc("zip", "-j", zipPath, bootstrapFilePath, outPath).call()
      println(s"Output bootstrap zip file to $outPath")
    }

  def zipParserExe =
    T {
      import scala.sys.process._
      "mkdir -p /tmp/zip-crap/bin/ && cp $(stack path --local-install-root)/bin/reddit-emacs-tips-n-tricks-exe /tmp/zip-crap/bin/ && (cd /tmp/zip-crap && zip -r reddit-emacs-tips-n-tricks-exe.zip bin/) && cp /tmp/zip-crap/reddit-emacs-tips-n-tricks-exe.zip .".!
    }
}
