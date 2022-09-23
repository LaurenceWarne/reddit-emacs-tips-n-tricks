package updateLambda

import java.io.{File => JFile}
import java.nio.file.{Path => JPath, Paths}
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

import scala.util.matching.Regex

import zio.Console._
import zio._
import zio.json.{DeriveJsonDecoder, JsonDecoder}
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
  val repoDomain = s"github.com/LaurenceWarne/$repoName"
  val repoUri    = "https://" ++ repoDomain
  val formatter =
    DateTimeFormatter.ofPattern("YYYY-MM-dd").withZone(ZoneId.of("UTC"))
  val numberPattern: Regex = """^.*?(\d+).*\n.*?(\d+)[\s\S]*$""".r

  override def apply(event: ScheduledEvent, context: Context): Task[String] = {
    for {
      wrkDir     <- ZIO.attempt(Paths.get("/tmp", repoName).toFile())
      ghUsername <- ZIO.attempt(sys.env("GH_USERNAME"))
      _ <- ZIO.unlessZIO(ZIO.attempt(wrkDir.exists())) {
        for {
          cloneDir <- ZIO.attempt(wrkDir.getParentFile())
          _        <- performClone(cloneDir)
          ghEmail  <- ZIO.attempt(sys.env("GH_EMAIL"))
          _        <- initGitIdentity(wrkDir, ghUsername, ghEmail)
        } yield ()
      }

      procStdout <-
        runCommandInDir(wrkDir, repoName + "-exe").flatMap(_.stdout.string)

      _ <- procStdout match {
        case numberPattern(posts, comments)
            if posts.toIntOption.exists(_ > 200) =>
          for {
            ghPAT <- ZIO.attempt(sys.env("GH_PAT"))
            msg = s"Weekly update from ${formatter.format(event.time)}"
            _ <- runCommandInDir(wrkDir, "git", "commit", "-a", "-m", msg)
              .flatMap(printStdoutStderr)
            _ <- runCommandInDir(
              wrkDir,
              "git",
              "push",
              s"https://$ghUsername:$ghPAT@$repoDomain",
              "HEAD:master"
            ).flatMap(printStdoutStderr)
          } yield ()
        case _ =>
          printLine(s"Not enough comments found, proc stdout: '$procStdout'")
      }
    } yield "Handler ran successfully"
  }

  private def initGitIdentity(
      path: JFile,
      username: String,
      email: String
  ): Task[Unit] =
    for {
      _ <- runCommandInDir(path, "git", "config", "user.name", username)
        .flatMap(
          printStdoutStderr
        )
      _ <- runCommandInDir(path, "git", "config", "user.email", email)
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
