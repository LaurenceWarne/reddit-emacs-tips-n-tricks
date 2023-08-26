#!/usr/bin/env bash

set -euo pipefail

rm -rf /tmp/zip-crap/bin/

mkdir -p /tmp/zip-crap/bin/

stack build

cp $(stack path --local-install-root)/bin/reddit-emacs-tips-n-tricks-exe /tmp/zip-crap/bin/

(cd /tmp/zip-crap && zip -r reddit-emacs-tips-n-tricks-exe.zip bin/)

cp /tmp/zip-crap/reddit-emacs-tips-n-tricks-exe.zip .

ls -lah reddit-emacs-tips-n-tricks-exe.zip

AWS_REGION=us-east-1 aws s3 cp $(pwd)/reddit-emacs-tips-n-tricks-exe.zip s3://base-miscbucket-8f9wra06edtd

VERSION="$(AWS_REGION=us-east-1 aws lambda publish-layer-version --layer-name ExeLambdaLayer --content S3Bucket=base-miscbucket-8f9wra06edtd,S3Key=reddit-emacs-tips-n-tricks-exe.zip | jq .Version)"

echo "New layer version $VERSION"

AWS_REGION=us-east-1 aws lambda update-function-configuration --function-name arn:aws:lambda:us-east-1:894057498223:function:tips-n-tricks-lambda-LambdaFunction-8SuFZM0tMnyu --layers "arn:aws:lambda:us-east-1:894057498223:layer:ExeLambdaLayer:$VERSION" arn:aws:lambda:us-east-1:553035198032:layer:git-lambda2:8
