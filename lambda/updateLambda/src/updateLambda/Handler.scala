package updateLambda

import zio.Console._
import zio._
import zio.lambda._
import zio.lambda.event._

object Handler extends ZLambda[KinesisEvent, String] {

  override def apply(event: KinesisEvent, context: Context): Task[String] =
    for {
      _ <- printLine(event)
    } yield "Handler ran successfully"
}
