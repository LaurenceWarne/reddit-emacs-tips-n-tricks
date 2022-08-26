package updateLambda

import java.nio.file.Paths
import java.io.{File => JFile}

import zio.Console._
import zio._
import zio.lambda._
import zio.process._
import zio.lambda.event._

object Handler extends ZLambda[KinesisEvent, String] {

  val repoName = "reddit-emacs-tips-n-tricks"
  val repoUri  = s"https://github.com/LaurenceWarne/$repoName"

  override def apply(event: KinesisEvent, context: Context): Task[String] = {
    for {
      _ <- printLine(event)
      _ <- performClone

      wrkDir = Paths.get(s"/tmp/$repoName").toFile
      _ <- runCommandInDir(wrkDir, "git", "status").flatMap(printStdoutStderr)
      proc <-
        runCommandInDir(wrkDir, repoName + "-exe").flatMap(printStdoutStderr)

    } yield "Handler ran successfully"
  }

  val performClone: Task[Unit] =
    runCommandInDir(
      Paths.get("/tmp").toFile,
      "git",
      "clone",
      repoUri
    ).flatMap(printStdoutStderr)

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
