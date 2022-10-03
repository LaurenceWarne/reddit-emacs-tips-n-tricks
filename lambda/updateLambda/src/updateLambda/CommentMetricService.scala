package updateLambda

import java.time.Instant
import java.time.temporal.TemporalUnit

import zio._
import zio.aws.cloudwatch.CloudWatch
import zio.aws.cloudwatch.model._
import zio.aws.cloudwatch.model.primitives._
import zio.stream.ZSink

trait CommentMetricService {

  def getPointsFrom(days: Int): Task[List[Int]]

  def put(value: Int): Task[Unit]
}

case class CommentMetricServiceImpl(
    cw: CloudWatch,
    clock: Clock,
    metricInfo: LambdaMetricInfo
) extends CommentMetricService {

  override def getPointsFrom(days: Int): Task[List[Int]] =
    for {
      now <- clock.instant
      metricQuery = MetricDataQuery(
        id = MetricId("id"),
        metricStat = MetricStat(
          metric = Metric(
            namespace = metricInfo.namespace,
            metricName = metricInfo.metricName,
            dimensions = metricInfo.dimensions
          ),
          period = Period(3600),
          stat = Stat("Maximum")
        )
      )
      req = GetMetricDataRequest(
        List(metricQuery),
        Timestamp(now.minus(1.day * days)),
        Timestamp(now)
      )
      dataResult <-
        cw.getMetricData(req).run(ZSink.head).mapError(_.toThrowable)
      values <- ZIO.foreach(dataResult)(_.getValues).mapError(_.toThrowable)
    } yield values.fold(List.empty[Int])(ls => ls.map(_.toInt))

  override def put(value: Int): Task[Unit] =
    for {
      timestamp <- clock.instant
      metricDatum = MetricDatum(
        metricName = metricInfo.metricName,
        dimensions = metricInfo.dimensions,
        timestamp = Timestamp(timestamp),
        value = DatapointValue(value)
      )
      putReq = PutMetricDataRequest(metricInfo.namespace, List(metricDatum))
      _ <- cw.putMetricData(putReq).mapError(_.toThrowable)
    } yield ()
}

object CommentMetricServiceImpl {
  val layer: ZLayer[
    CloudWatch with LambdaMetricInfo,
    Nothing,
    CommentMetricService
  ] =
    ZLayer {
      for {
        cw         <- ZIO.service[CloudWatch]
        metricInfo <- ZIO.service[LambdaMetricInfo]
      } yield CommentMetricServiceImpl(cw, Clock.ClockLive, metricInfo)
    }
}

case class LambdaMetricInfo(
    metricName: MetricName,
    namespace: Namespace,
    resourceName: String
) extends Product
    with Serializable {
  def dimensions: List[Dimension] =
    List(
      Dimension(
        DimensionName("FunctionName"),
        DimensionValue(resourceName)
      )
    )
}
