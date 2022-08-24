package updateLambda

import zio.Console._
import zio._
import zio.lambda._
import zio.process._
import zio.lambda.event._
import sys.process._

object Handler extends ZLambda[KinesisEvent, String] {

  override def apply(event: KinesisEvent, context: Context): Task[String] = {
    for {
      _      <- printLine(event)
      result <- Command("git", "status").run
      _      <- printLine(result)
    } yield "Handler ran successfully"
  }
}
