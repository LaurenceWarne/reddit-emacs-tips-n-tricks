import mill._
import mill.scalalib._

object updateLambda extends ScalaModule {

  def scalaVersion = "2.13.8"

  def ivyDeps =
    Agg(
      ivy"dev.zio::zio-lambda:1.0.0-RC6",
      ivy"dev.zio::zio-lambda-event:1.0.0-RC6",
      ivy"dev.zio::zio-lambda-response:1.0.0-RC6"
    )
}
