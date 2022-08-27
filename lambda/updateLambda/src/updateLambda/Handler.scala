package updateLambda

import java.nio.file.{Path => JPath, Paths}
import java.io.{File => JFile}

import zio.Console._
import zio._
import zio.lambda._
import zio.process._
import zio.lambda.event._

object Handler extends ZLambda[KinesisEvent, String] {

  val repoName   = "reddit-emacs-tips-n-tricks"
  val username   = "LaurenceWarne"
  val repoDomain = s"github.com/$username/$repoName"
  val repoUri    = "https://" ++ repoDomain

  override def apply(event: KinesisEvent, context: Context): Task[String] = {
    for {
      _        <- printLine(event)
      _        <- initGitIdentity
      repoPath <- performClone

      wrkDir <-
        ZIO.attempt(Paths.get(repoPath.toString, repoName)).map(_.toFile)
      _ <- runCommandInDir(wrkDir, "git", "status").flatMap(printStdoutStderr)
      proc <-
        runCommandInDir(wrkDir, repoName + "-exe").flatMap(printStdoutStderr)
      token <- ZIO.attempt(sys.env("GH_PAT"))

      msg = "Update from Lambda"
      proc <- runCommandInDir(wrkDir, "git", "commit", "-a", "-m", s"'$msg'")
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

  val initGitIdentity: Task[Unit] = for {
    _ <-
      Command("git", "config", "--global", "user.name", "updateLambda").run
        .flatMap(
          printStdoutStderr
        )
    _ <-
      Command(
        "git",
        "config",
        "--global",
        "user.email",
        "updateLambda@users.noreply.github.com"
      ).run
        .flatMap(
          printStdoutStderr
        )
  } yield ()

  val performClone: Task[JPath] =
    for {
      path <- ZIO.attempt(Paths.get("/tmp"))
      proc <- runCommandInDir(
        path.toFile,
        "git",
        "clone",
        repoUri
      )
    } yield path

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
