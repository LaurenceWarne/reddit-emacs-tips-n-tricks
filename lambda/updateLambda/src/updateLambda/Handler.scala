package updateLambda

import java.io.{File => JFile}
import java.nio.file.{Path => JPath, Paths}
import java.time.Instant

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

  override def apply(event: ScheduledEvent, context: Context): Task[String] = {
    for {
      _      <- printLine(event)
      wrkDir <- ZIO.attempt(Paths.get("/tmp", repoName).toFile())
      _ <- ZIO.unlessZIO(ZIO.attempt(wrkDir.exists())) {
        for {
          cloneDir <- ZIO.attempt(wrkDir.getParentFile())
          repoPath <- performClone(cloneDir)
          _        <- initGitIdentity(wrkDir)
        } yield ()
      }

      proc <-
        runCommandInDir(wrkDir, repoName + "-exe").flatMap(printStdoutStderr)

      token <- ZIO.attempt(sys.env("GH_PAT"))
      msg = "Update from Lambda"
      proc <- runCommandInDir(wrkDir, "git", "commit", "-a", "-m", s"$msg")
        .flatMap(printStdoutStderr)
      _ <- runCommandInDir(
        wrkDir,
        "git",
        "push",
        s"https://$username:$token@$repoDomain",
        "HEAD:master"
      ).flatMap(printStdoutStderr)

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
