package prchecklist.services

import prchecklist.models.GitHubTypes._
import prchecklist.utils.GitHubHttpClient

import scalaz.concurrent.Task

class GitHubService(val gitHubHttpClient: GitHubHttpClient) {
  def getRepo(owner: String, name: String): Task[Repo] =
    getRepo(s"$owner/$name")

  def getRepo(repoFullName: String): Task[Repo] = {
    // TODO: check API
    gitHubHttpClient.getJson(s"/repos/$repoFullName")
  }

  // https://developer.github.com/v3/issues/comments/#create-a-comment
  def addIssueComment(repoFullName: String, issueNumber: Int, body: String): Task[Unit] = {
    gitHubHttpClient.postJson(
      s"/repos/$repoFullName/issues/$issueNumber/comments",
      IssueComment(body)
    )
  }

  // https://developer.github.com/v3/repos/statuses/#create-a-status
  def setCommitStatus(repoFullName: String, sha: String, status: CommitStatus): Task[Unit] = {
    gitHubHttpClient.postJson(
      s"/repos/$repoFullName/statuses/$sha",
      status
    )
  }
}
