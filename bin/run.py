"""
Usage: python3 bin/run.py [--all]

https://www.reddit.com/dev/api/#GET_search
"""
import sys, itertools, json, logging, os, datetime, collections, re, html

import requests
from collections import namedtuple

HANDLER = logging.StreamHandler()
HANDLER.setFormatter(logging.Formatter("%(asctime)s %(name)s %(levelname)s: %(message)s"))
HANDLER.setLevel(logging.INFO)
LOGGER = logging.Logger(__name__)
LOGGER.addHandler(HANDLER)
MIN_UPVOTES = 8
FILE = "comments.json"
OUT = "out"
CLIENT_ID = os.environ["CLIENT_ID"]
CLIENT_SECRET = os.environ["CLIENT_SECRET"]
AUTH_URL = "https://www.reddit.com/api/v1/access_token"
BASE_EMACS_SUB_URL = "https://oauth.reddit.com/r/emacs"

MarkdownType = collections.namedtuple("MarkdownType", "header code_start code_end link bold ext single_line_code bof")
GithubMD = MarkdownType("##", "```", "```", lambda text, link: f"[{text}]({link})", lambda s: f"**{s}**", "md", "`", "")
OrgMD = MarkdownType(
    header = "**",
    code_start = "#+BEGIN_SRC elisp",
    code_end = "#+END_SRC",
    link = lambda text, link: f"[[{link}][{text}]]",
    bold = lambda s: f"*{s}*",
    ext = "org",
    single_line_code = "~",
    bof = "#+OPTIONS: toc:nil\n"
)

def get_headers(token=None):
    return {
        "user-agent": "linux:reddit-tips-n-tricks:1.0 (by u/PriorOutcome)",
        "Accept": "application/json"
    } | ({"Authorization": f"bearer {token}"} if token else {})


def post_comments(post_id, headers):
    response = requests.get(f"{BASE_EMACS_SUB_URL}/comments/{post_id}", headers=headers)
    json_response = response.json()
    assert all(e["data"]["after"] is None for e in json_response)
    return [e["data"] for chunk in json_response for e in chunk["data"]["children"]]


def search_emacs_subreddit(headers, count, after=None):
    after_param = f"&after={after}" if after else ""
    url = f"{BASE_EMACS_SUB_URL}/search?q=weekly%20tips&restrict_sr=1&sort=new&show=all&limit=100&count={count}{after_param}"
    LOGGER.info(f"request URL: {url}")
    response = requests.get(url, headers=headers)
    data = response.json()["data"]
    posts = [e["data"] for e in data["children"]]
    return posts, data["after"]


def get_comments(token, read_all):
    headers = get_headers(token)
    comments_from_posts = lambda posts: [
        c for post in posts for c in post_comments(post["name"].removeprefix("t3_"), headers=headers)
        if post["title"].strip().lower().startswith("weekly tips")  # Early were called 'Weekly tips/trick/etc/ thread'
    ]
    posts, after = search_emacs_subreddit(headers, 0)
    total_posts, calls = len(posts), 1
    all_comments = comments_from_posts(posts)
    log = lambda : LOGGER.info("Completed API call %d, posts: %d, new offset: %s, total_comments: %d", calls, len(posts), after, len(all_comments))
    log()
    if not read_all: return all_comments
    while after:
        calls += 1
        posts, after = search_emacs_subreddit(headers, total_posts, after)
        total_posts += len(posts)
        all_comments += comments_from_posts(posts)
        log()
    LOGGER.info("Found a total of %d posts", total_posts)    
    return all_comments


def comment_to_md(content, username, post_id, comment_id, upvotes, md_type=GithubMD):
    link = md_type.link("🔗", f"https://www.reddit.com/r/emacs/comments/{post_id}/comment/{comment_id}")
    title = f'{md_type.header} u/{username or "???"} {link} \n{md_type.bold("Votes")} {upvotes}\n'

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
    split = (markdown + ("\n```" if quote_last_line else "")).split("```")
    code_it = itertools.cycle([md_type.code_start, md_type.code_end])
    md = "".join([a + b for a, b in zip(split[:-1], code_it)] + [split[-1]])
    single_line_code_converted = re.sub(r"(?<!`)(`)(?!`)", md_type.single_line_code, md)
    heading_escaped = re.sub(r"^(\*+)", r"\\\1", md, flags=re.M)
    return title + html.unescape(heading_escaped)


def refine_comments(comments, md_type=GithubMD):
    valid = {}
    for comment in comments:
        ups = comment["ups"]
        name = comment["name"]  # they put the id here apparently
        body = comment.get("body", None)
        if ups >= MIN_UPVOTES and body:
            LOGGER.info("Found comment %s with %d upvotes", name, ups)
            author = comment.get("author", "???")
            parse_dt = lambda c: datetime.datetime.fromtimestamp(c).replace(tzinfo=datetime.timezone.utc)
            valid[name] = {
                "author": author,
                "upvotes": ups,
                "body": comment_to_md(body, author, comment["parent_id"], name, ups, md_type),
                "created_datetime": str(parse_dt(comment["created_utc"]))
            }
    return valid
    

def persisted_comments():
    try:
        with open(FILE, "r") as f:
            return json.load(f)
    except FileNotFoundError:
        return {}


def run(all_posts, md_type=OrgMD):
    auth_response = requests.post(
        AUTH_URL,
        data={"grant_type": "client_credentials"},
        auth=(CLIENT_ID, CLIENT_SECRET),
        headers=get_headers()
    )
    token = auth_response.json()["access_token"]

    LOGGER.info("Fetching posts...")
    raw_comments = get_comments(token, all_posts)
    comments = refine_comments(raw_comments, md_type)
    LOGGER.info("Found %d applicable comments", len(comments))
    existing_comments = persisted_comments()
    new_comments = sum(1 for p in comments if p not in existing_comments)
    LOGGER.info("Of which %d are new", new_comments)

    for comment, attribs in comments.items(): existing_comments[comment] = attribs
    LOGGER.info("Giving a total of %d comments", len(existing_comments))
    with open(FILE, "w") as f:
        f.write(json.dumps(existing_comments, default=str))

    s = "\n\n".join(t[1]["body"] for t in sorted(existing_comments.items(), key=lambda t: t[1]["upvotes"], reverse=True))
    with open(f"{OUT}.{md_type.ext}", "w") as f:
        f.write(md_type.bof + s)


def main():
    all_posts = "--all" in sys.argv
    run(all_posts)


if __name__ == "__main__":
    main()
