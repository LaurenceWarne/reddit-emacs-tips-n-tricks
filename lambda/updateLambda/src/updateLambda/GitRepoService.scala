package updateLambda

import java.io.{File => JFile, IOException}

import zio.Console._
import zio._
import zio.process._

case class RepoConfig(ghUsername: String, ghEmail: String, ghToken: String)

trait GitRepoService {

  def createGitIdentity(repoDir: JFile): Task[Unit]

  def clone(outputDir: JFile, remoteUri: String): Task[Unit]

  def commit(repoDir: JFile, msg: String): Task[Unit]

  def push(repoDir: JFile, repoDomain: String): Task[Unit]
}

case class GitRepoServiceImpl(repoConfig: RepoConfig) extends GitRepoService {

  implicit class StdCapture(eff: IO[CommandError, Process]) {
    def std: ZIO[Any, Exception, Unit] =
      eff.flatMap { proc =>
        proc.stdout.string.flatMap(s => printLine("standard output: " + s)) *>
          proc.stderr.string.flatMap(s => printLine("standard error: " + s))
      }
  }

  override def createGitIdentity(
      repoDir: JFile
  ): Task[Unit] =
    for {
      _ <- runCommandInDir(
        repoDir,
        "git",
        "config",
        "user.name",
        repoConfig.ghUsername
      ).std
      _ <- runCommandInDir(
        repoDir,
        "git",
        "config",
        "user.email",
        repoConfig.ghEmail
      ).std
    } yield ()

  override def clone(outputDir: JFile, remoteUri: String): Task[Unit] =
    runCommandInDir(
      outputDir,
      "git",
      "clone",
      remoteUri
    ).std *> runCommandInDir(outputDir, "git", "pull").std

  override def commit(repoDir: JFile, msg: String): Task[Unit] =
    runCommandInDir(repoDir, "git", "commit", "-a", "-m", msg).std

  override def push(repoDir: JFile, repoDomain: String): Task[Unit] = {
    val RepoConfig(ghUsername, _, ghToken) = repoConfig
    runCommandInDir(
      repoDir,
      "git",
      "push",
      s"https://$ghUsername:$ghToken@$repoDomain",
      "HEAD:master"
    ).std
  }

  private def runCommandInDir(
      directory: JFile,
      processName: String,
      args: String*
  ): IO[CommandError, Process] =
    Command(processName, args: _*)
      .copy(workingDirectory = Some(directory))
      .run
}

object GitRepoServiceImpl {
  val layer: ZLayer[RepoConfig, Nothing, GitRepoService] =
    ZLayer(ZIO.service[RepoConfig].map(GitRepoServiceImpl(_)))
}
