"""
Usage: python3 bin/run.py [--all] [--skip-pushing] [--tmp-directory]

https://praw.readthedocs.io/en/latest/
"""
import sys, itertools, json, logging, os, datetime

import praw

HANDLER = logging.StreamHandler()
HANDLER.setFormatter(logging.Formatter("%(asctime)s %(name)s %(levelname)s: %(message)s"))
HANDLER.setLevel(logging.INFO)
LOGGER = logging.Logger(__name__)
LOGGER.addHandler(HANDLER)
MIN_UPVOTES = 8
FILE = "posts.json"
OUT = "out.md"
CLIENT_ID = os.environ["CLIENT_ID"]
CLIENT_SECRET = os.environ["CLIENT_SECRET"]
REPO = os.environ["GIT_REPO"]
REPO_URL = f"https://{REPO}"
GITHUB_USERNAME = os.environ["GITHUB_USERNAME"]
GITHUB_EMAIL = os.environ["GITHUB_EMAIL"]


def posts(subreddit, read_all):
    found = list(subreddit.search("Weekly Tips", sort="new", limit=None))
    if not read_all: return found
    while found:
        found += list(subreddit.search("Weekly Tips", sort="new", limit=None, params={"after": f"t3_{found[-1]}"}))
    return found


def comment_to_md(content, username, post_id, comment_id, upvotes):
    link = f"[🔗](https://www.reddit.com/r/emacs/comments/{post_id}/comment/{comment_id})"
    title = f'## u/{username or "???"} {link} \n**Votes:** {upvotes}\n'

    last_formatting = None
    triple_quoted, indented = 1, 2
    line_prefix = "    "
    markdown = ""
    for line in content.splitlines():
        new_formatting = last_formatting
        stripped = line.startswith(line_prefix) and line.removeprefix("    ")
        if last_formatting == triple_quoted:
            if "```" in line:
                new_formatting = None
        elif last_formatting == indented:
            if stripped or ("" == line.strip() and last_formatting):
                new_formatting = last_formatting
            else:
                new_formatting = None
        else:
            if "```" in line:
                new_formatting = triple_quoted
            elif stripped:
                new_formatting = indented
            else:
                new_formatting = None

        if ((new_formatting == indented and last_formatting is None) or
            (new_formatting is None and last_formatting == indented)):
            prefix = "```\n"
        else:
            prefix = ""

        markdown += f"\n{prefix}{stripped or line}" 
     
        last_formatting = new_formatting

    quote_last_line = last_formatting == indented
    split = (markdown + ("\n```" if quote_last_line else "")).split("```\n")
    code_it = itertools.cycle(["```elisp\n", "```\n"])
    return title + "".join([a + b for a, b in zip(split[:-1], code_it)] + [split[-1]])


def init_git_repo():
    os.system(f"git clone {REPO_URL}")
    os.chdir([s for s in REPO.split("/") if s][-1])
    os.system(f"git config user.name {GITHUB_USERNAME}")
    os.system(f"git config user.email {GITHUB_EMAIL}")


def update_git_repo():
    os.system(f"git add out.md posts.json")
    os.system(f"git commit -m 'Weekly update from {datetime.date.today()}'")
    os.system(f"git push origin master")


def get_posts(read_all):
    reddit = praw.Reddit(
        client_id=CLIENT_ID,
        client_secret=CLIENT_SECRET,
        user_agent="linux:reddit-tips-n-tricks:1.0 (by u/PriorOutcome)",
        check_for_updates=False
    )
    reddit.read_only = True    
    subreddit = reddit.subreddit("emacs")
    found = posts(subreddit, read_all)

    valid = {}
    for post in found:
        for comment in post.comments:
            ups = comment.ups
            if ups >= MIN_UPVOTES:
                LOGGER.info("Found post %s with %d upvotes", post.id, ups)
                name = comment.author.name if comment.author else "???"
                valid[comment.id] = {
                    "author": name,
                    "upvotes": comment.ups,
                    "body": comment_to_md(comment.body, name, post.id, comment.id, ups)
                }

    return valid
    

def persisted_posts():
    with open(FILE, "r") as f:
        return json.load(f)


def run(all_posts, skip_pushing, tmp_directory):
    LOGGER.info("Fetching posts...")
    if not skip_pushing:
        LOGGER.info("Initialising git repo...")
        if tmp_directory: os.chdir("/tmp")
        init_git_repo()
        LOGGER.info("Done")

    fetched_posts = get_posts(all_posts)
    LOGGER.info("Found %d applicable posts", len(fetched_posts))
    existing_posts = persisted_posts()
    new_posts = sum(1 for p in fetched_posts if p not in existing_posts)
    LOGGER.info("Of which %d are new", new_posts)

    for post, attribs in fetched_posts.items(): existing_posts[post] = attribs
    with open(FILE, "w") as f:
        f.write(json.dumps(existing_posts))

    s = "\n\n".join(t[1]["body"] for t in sorted(existing_posts.items(), key=lambda t: t[1]["upvotes"], reverse=True))
    with open(OUT, "w") as f:
        f.write(s)

    if skip_pushing:
        LOGGER.info("Skipping pushing to git repo")
    elif new_posts > 0:
        LOGGER.info("Pushing to git repo...")
        update_git_repo()
        LOGGER.info("Done")
    else:
        LOGGER.info("Not pushing to git repo since no new posts were found")


def handler(event, context):
    LOGGER.info("event %s", event)
    LOGGER.info("context %s", context)
    run(all_posts=True, skip_pushing=False, tmp_directory=False)


def main():
    all_posts = "--all" in sys.argv
    skip_pushing = "--skip-pushing" in sys.argv
    tmp_directory = "--tmp-directory" in sys.argv
    run(all_posts, skip_pushing, tmp_directory)


if __name__ == "__main__":
    main()
