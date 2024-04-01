provider "aws" {
  region     = "us-east-1"
}

variable "github_username" {
  description = "Github username the lambda will use"
  type        = string
  default     = "emacs-reddit-tips-n-tricks-bot"
}

variable "github_pat" {
  description = "The GH PAT token, note this can't be a password since password authentication was removed by Github on August 13, 2021."
  type        = string
}

variable "github_user_email" {
  description = "The GH email the lambda will use"
  type        = string
  default     = "emacstipsntricksbot@gmail.com"
}

variable "github_repo" {
  description = "The GH repo"
  type        = string
  default     = "github.com/LaurenceWarne/reddit-emacs-tips-n-tricks"
}

variable "client_id" {
  description = "Reddit application client id"
  type        = string
}

variable "client_secret" {
  description = "Reddit application client secret"
  type        = string
}

data "aws_iam_policy_document" "lambda_assume_role_policy" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
  }
}

data "archive_file" "python_lambda_package" {
  type        = "zip"
  source_file = "bin/run.py"
  output_path = "run.zip"
}

resource "aws_iam_role" "lambda_role" {
  name                = "reddit-tips-and-tricks-lambda-role"
  assume_role_policy  = data.aws_iam_policy_document.lambda_assume_role_policy.json
  managed_policy_arns = ["arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"]
}

resource "aws_lambda_function" "reddit_tips_and_tricks_lambda" {
  function_name    = "reddit-tips-and-tricks-lambda"
  filename         = "run.zip"
  source_code_hash = data.archive_file.python_lambda_package.output_base64sha256
  role             = aws_iam_role.lambda_role.arn
  runtime          = "python3.12"
  handler          = "run.handler"
  memory_size      = 512
  timeout          = 300
  layers = [
    "arn:aws:lambda:us-east-1:553035198032:layer:git-lambda2:8"
  ]
  environment {
    variables = {
      GH_USERNAME   = var.github_username
      GH_PAT        = var.github_pat
      GH_EMAIL      = var.github_user_email
      REPO          = var.github_repo
      CLIENT_ID     = var.client_id
      CLIENT_SECRET = var.client_secret
    }
  }
}

resource "aws_cloudwatch_event_rule" "reddit_tips_and_tricks_lambda_rule" {
  name                = "run-reddit-tips-and-tricks-function"
  description         = "Schedule reddit tips and tricks lambda function"
  schedule_expression = "rate(7 days)"
}

resource "aws_lambda_permission" "allow_cloudwatch" {
  statement_id  = "AllowExecutionFromCloudWatch"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.reddit_tips_and_tricks_lambda.function_name
  principal     = "events.amazonaws.com"
  source_arn    = aws_cloudwatch_event_rule.reddit_tips_and_tricks_lambda_rule.arn
}
