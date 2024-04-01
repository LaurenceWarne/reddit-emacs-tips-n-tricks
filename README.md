# reddit-emacs-tips-n-tricks

This repo contains tools for parsing top comments from the weekly r/emacs [Weekly Tips, Tricks & c. Thread](https://www.reddit.com/r/emacs/search?q=Weekly+tips&restrict_sr=on&sort=new&t=all).  You can check out all of the top comments [here](./out.md).

## Running Locally

The easiest way is with [pip-run](https://github.com/jaraco/pip-run):

```bash
GITHUB_TOKEN=??? CLIENT_ID=??? CLIENT_SECRET=??? GIT_REPO=??? GITHUB_USERNAME=??? GITHUB_EMAIL=??? pip-run praw -- bin/run.py --skip-pushing
```

Note `CLIENT_ID` and `CLIENT_SECRET` are needed to access the reddit API, and the Github variables are needed if you want to push to a repository (they can be stubbed with the `--skip-pushing` option).
