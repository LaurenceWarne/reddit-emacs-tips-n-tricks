# reddit-emacs-tips-n-tricks

This repo contains tools for parsing top comments from the weekly r/emacs [Weekly Tips, Tricks & c. Thread](https://www.reddit.com/r/emacs/search?q=Weekly+tips&restrict_sr=on&sort=new&t=all).  You can check out all of the top comments:

- As an org mode file [here](./out.org).
- As an md file [here](./out.md).

## Running Locally

The easiest way is with [pip-run](https://github.com/jaraco/pip-run):

```bash
CLIENT_ID=??? CLIENT_SECRET=??? pip-run requests -- bin/run.py
```

Note `CLIENT_ID` and `CLIENT_SECRET` are needed to access the reddit API.
