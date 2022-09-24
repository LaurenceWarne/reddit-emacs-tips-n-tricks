package updateLambda

import java.io.{File => JFile}
import java.nio.file.{Path => JPath, Paths}
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import scala.util.matching.Regex

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient
import zio.Console._
import zio._
import zio.aws.cloudwatch._
import zio.aws.cloudwatch.model._
import zio.aws.core.config.AwsConfig
import zio.aws.netty.NettyHttpClient
import zio.json.{DeriveJsonDecoder, JsonDecoder}
import zio.lambda._
import zio.lambda.event._
import zio.process._

object Handler extends ZLambda[ScheduledEvent, String] {

  private val repoName   = "reddit-emacs-tips-n-tricks"
  private val repoDomain = s"github.com/LaurenceWarne/$repoName"
  private val repoUri    = "https://" ++ repoDomain
  private val formatter =
    DateTimeFormatter.ofPattern("YYYY-MM-dd").withZone(ZoneId.of("UTC"))
  private val numberPattern: Regex = """^.*?(\d+).*\n.*?(\d+)[\s\S]*$""".r

  val client = NettyHttpClient.default >>> AwsConfig.default >>> CloudWatch.live

  override def apply(event: ScheduledEvent, context: Context): Task[String] = {
    val layer: ZLayer[Any, Throwable, GitRepoService] = ZLayer {
      for {
        ghUsername <- envOrErr("GH_USERNAME")
        ghEmail    <- envOrErr("GH_EMAIL")
        ghToken    <- envOrErr("GH_PAT")
      } yield RepoConfig(ghUsername, ghEmail, ghToken)
    } >>> GitRepoServiceImpl.layer
    invoke(event, context).provideLayer(layer)
  }

  def invoke(
      event: ScheduledEvent,
      context: Context
  ): ZIO[GitRepoService, Throwable, String] = {
    for {
      gitService <- ZIO.service[GitRepoService]
      wrkDir     <- ZIO.attempt(Paths.get("/tmp", repoName).toFile())
      _ <- ZIO.unlessZIO(ZIO.attempt(wrkDir.exists())) {
        for {
          cloneDir <- ZIO.attempt(wrkDir.getParentFile())
          _        <- gitService.clone(cloneDir, repoUri)
          _        <- gitService.createGitIdentity(wrkDir)
        } yield ()
      }

      procStdout <-
        Command(repoName + "-exe")
          .copy(workingDirectory = Some(wrkDir))
          .run
          .flatMap(_.stdout.string)

      _ <- procStdout match {
        case numberPattern(posts, comments)
            if posts.toIntOption.exists(_ > 200) =>
          val msg = s"Weekly update from ${formatter.format(event.time)}"
          gitService.commit(wrkDir, msg) *> gitService.push(wrkDir, repoDomain)
        case _ =>
          printLine(s"Not enough comments found, proc stdout: '$procStdout'")
      }
    } yield "Handler ran successfully"
  }

  private def envOrErr(variable: String): ZIO[Any, Throwable, String] =
    for {
      opt <- System.env(variable)
      variable <-
        ZIO.fromOption(opt).mapError(_ => EnvVarNotFoundError(variable))
    } yield variable
}

case class ScheduledEvent(time: Instant)

object ScheduledEvent {
  implicit val decoder: JsonDecoder[ScheduledEvent] =
    DeriveJsonDecoder.gen[ScheduledEvent]
}

case class EnvVarNotFoundError(name: String) extends Throwable
