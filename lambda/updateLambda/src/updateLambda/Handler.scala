package updateLambda

import java.io.{File => JFile}
import java.nio.file.{Path => JPath, Paths}
import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter

import scala.util.matching.Regex

import zio.Console._
import zio._
import zio.json.DeriveJsonDecoder
import zio.json.JsonDecoder
import zio.lambda._
import zio.lambda.event._
import zio.process._

case class ScheduledEvent(time: Instant)

object ScheduledEvent {
  implicit val decoder: JsonDecoder[ScheduledEvent] =
    DeriveJsonDecoder.gen[ScheduledEvent]
}

object Handler extends ZLambda[ScheduledEvent, String] {

  val repoName   = "reddit-emacs-tips-n-tricks"
  val username   = "LaurenceWarne"
  val repoDomain = s"github.com/$username/$repoName"
  val repoUri    = "https://" ++ repoDomain
  val formatter =
    DateTimeFormatter.ofPattern("YYYY-MM-dd").withZone(ZoneId.of("UTC"))
  val numberPattern: Regex = """^.*?(\d+).*\n.*?(\d+)[\s\S]*$""".r

  override def apply(event: ScheduledEvent, context: Context): Task[String] = {
    for {
      wrkDir <- ZIO.attempt(Paths.get("/tmp", repoName).toFile())
      _ <- ZIO.unlessZIO(ZIO.attempt(wrkDir.exists())) {
        for {
          cloneDir <- ZIO.attempt(wrkDir.getParentFile())
          repoPath <- performClone(cloneDir)
          _        <- initGitIdentity(wrkDir)
        } yield ()
      }

      procStdout <-
        runCommandInDir(wrkDir, repoName + "-exe").flatMap(_.stdout.string)

      _ <- procStdout match {
        case numberPattern(posts, comments)
            if posts.toIntOption.exists(_ > 200) =>
          for {
            token <- ZIO.attempt(sys.env("GH_PAT"))
            msg = s"Weekly update from ${formatter.format(event.time)}"
            proc <- runCommandInDir(wrkDir, "git", "commit", "-a", "-m", msg)
              .flatMap(printStdoutStderr)
            _ <- runCommandInDir(
              wrkDir,
              "git",
              "push",
              s"https://$username:$token@$repoDomain",
              "HEAD:master"
            ).flatMap(printStdoutStderr)
          } yield ()
        case _ =>
          printLine(s"Not enough comments found, proc stdout: '$procStdout'")
      }

    } yield "Handler ran successfully"
  }

  def initGitIdentity(path: JFile): Task[Unit] =
    for {
      _ <- runCommandInDir(path, "git", "config", "user.name", "weeklyupdate")
        .flatMap(
          printStdoutStderr
        )
      _ <- runCommandInDir(
        path,
        "git",
        "config",
        "user.email",
        "weeklyupdate@users.noreply.github.com"
      )
        .flatMap(
          printStdoutStderr
        )
    } yield ()

  def performClone(path: JFile): Task[Unit] =
    for {
      _ <- runCommandInDir(
        path,
        "git",
        "clone",
        repoUri
      ).flatMap(printStdoutStderr)
      _ <- runCommandInDir(path, "git", "pull").flatMap(printStdoutStderr)
    } yield ()

  def printStdoutStderr(proc: Process): Task[Unit] =
    proc.stdout.string.flatMap(s => printLine("standard output: " + s)) *>
      proc.stderr.string.flatMap(s => printLine("standard error: " + s))

  def runCommandInDir(
      directory: JFile,
      processName: String,
      args: String*
  ): IO[CommandError, Process] =
    Command(processName, args: _*)
      .copy(workingDirectory = Some(directory))
      .run
}
