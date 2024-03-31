## u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h2rdkkz) 
**Votes:** 36

Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.

## u/gausby [🔗](https://www.reddit.com/r/emacs/comments/e5dzv6/comment/f9jp341) 
**Votes:** 36

Shouldn't it be `/etc/tips/trick` ?

(…I'll show myself out)

## u/celeritasCelery [🔗](https://www.reddit.com/r/emacs/comments/gienra/comment/fqe6yop) 
**Votes:** 34

> I had received the advice to not install anything and just start with plain emacs so I can learn emacs. ... it was miserable. 
> Enter Doom Emacs. I decided to give Doom Emacs a try because it was also highly recommended in my initial RFC, especially since it is designed for Vim users. In short I love it.

This is why I disagree with the subreddits de facto advice to “learn vanilla first”. People who have used emacs for a long time don’t realize how much **time** it takes to get it to level of a normal modern editor people are used to. I recommend distros to *everyone* who is new unless they are that certain personality type that wants to do everything themselves.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/10qo7vb/comment/j6rmvvf) 
**Votes:** 33

When you have an active region, `undo` will only undo changes in that region instead of the whole file.

## u/zupatol [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/iodig8c) 
**Votes:** 28

After years of using emacs, I wrote my second ever elisp function to open on github the code I'm browsing in emacs.

```elisp
(defun open-on-github ()
  (interactive)
  (let
      ((repo-url (magit-git-string "remote" "get-url" "--push" "origin"))
       (commit-hash (magit-git-string "rev-parse" "HEAD"))
       (start-line (if (use-region-p)
                       (line-number-at-pos (region-beginning))
                     (line-number-at-pos)))
       (end-line (if (use-region-p) (line-number-at-pos (region-end)))))
    (unless repo-url (error  "not in a git repo"))
    (browse-url
     (concat
      (substring repo-url 0 -4)
      "/blob/"
      commit-hash
      "/"
      (substring buffer-file-name (length (projectile-project-root)))
      "#L" (number-to-string start-line)
      (if (and (use-region-p) (< 0 (- end-line start-line)))
          (concat "..L" (number-to-string end-line)))
      ))))
```

## u/unbelievable_sc2 [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f81dslh) 
**Votes:** 27

I often use the compile feature to compile and run my projects to see the results in the compilation buffer. This works well unless you are waiting for user input in your terminal. I recently found out that you can invoke the compile with an additional non nil value to start compilation in comint-mode which allows for user input! 
The drawback is, that you then no longer can press q to close the window or g to recompile. Because of that I added a simple lambda, that switches to compilation-mode to the compilation-finish-functions. So I can give input while compiling and running, and after compilation I can close the window as usual with q.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/txh85s/comment/i3m1liu) 
**Votes:** 26

[removed]

## u/howardthegeek [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/ioeh1ly) 
**Votes:** 21

I just learned that in eshell, $$ is replaced with the output from the last command.

## u/Gangsir [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hepqmq1) 
**Votes:** 21

back-to-indentation. Before I found this function I would always do some awkward triple key combo like C-a M-f M-b.

It's just bound to M-m. Jumps you right to the first non-white space character on the line. What's even spicier is that it works in reverse too - if you're at the front of the line it jumps you forward, if you're at the end or middle it jumps backward.

It still works even on lines that aren't indented, same as C-a in that case.

So useful, especially for resetting point during macros that need to start at the first char on the line.

## u/AndreaSomePostfix [🔗](https://www.reddit.com/r/emacs/comments/12cd23k/comment/jf167qh) 
**Votes:** 19

org-mode is amazing!

I discovered \`org-copy-visible\` the other day, when I wanted to send somebody only the outline of my notes.

That function (which is bound to C-c C-x v by default) let you copy just the outline for the selected region: very useful!

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8m9rlj) 
**Votes:** 19

With an active region, you can freely toggle between rectangle mark mode and normal, you don't need to get rid of your active region to switch between the two.

## u/TeMPOraL_PL [🔗](https://www.reddit.com/r/emacs/comments/txh85s/comment/i3ov7vq) 
**Votes:** 19

`shortdoc` - one of the new things in Emacs 28.1 - is great for maintaining your own "cheat sheets" of Elisp functions as you discover them. For example, eval this in your Emacs session:

```elisp
(define-short-documentation-group my-datetime
  "Querying current time"
  (current-time
   :eval (current-time))
  (float-time
   :eval (float-time))
  
  "Time formats"
  (time-convert
   :eval (time-convert (current-time))
   :eval (time-convert (current-time) 'list)
   :eval (time-convert (current-time) 100000)
   :eval (time-convert (current-time) 200000)
   :eval (time-convert (current-time) t)
   :eval (time-convert (current-time) 'integer))
  
  (float-time
   :eval (float-time (current-time))))

```
After this, the `my-datetime` group will show as an option in `M-x shortdoc-display-group`. Additionally, if you view help for any of the functions mentioned above, the Help buffer will refer back to the `my-datetime` shortdoc group!

The example used here is a cheatsheet I just started writing for myself, as I've been researching the built-in time functions. For additional instructions on use, see `define-short-documentation-group` macro. For use examples, jump to the source of `define-short-documentation-group` and scroll down a bit - the default shortdoc groups are defined there.

## u/thehaas [🔗](https://www.reddit.com/r/emacs/comments/e5dzv6/comment/f9k6yyf) 
**Votes:** 19

After using Emacs for maybe 10 years I finally started using registers and I really should have started earlier. For those who don't know:

&#x200B;

* Highlight text and C-x r s <char> to save to register <char>
* C-x r i <char> to put the contents of the register at the cursor point. The text is still there -- use it over and over again

It seems like quite a few keystrokes but it's really not. Of course you can re-assign them to other keys if you don't like the defaults.

## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/v2by7z/comment/iauyzbl) 
**Votes:** 18

It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/).  There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.

## u/SamTheComputerSlayer [🔗](https://www.reddit.com/r/emacs/comments/sijcap/comment/hvbbnjq) 
**Votes:** 18

Just figured this out, maybe a bit of a hack...

In flyspell, I was annoyed I had to use mouse-2 when I wanted to correct a word, and I didn't want to sacrifice a major-mode keybinding to do it from the keyboard. But flyspell actually creates an overlay for misspelled words and attaches a keymap to it, which you can do I just realized- very cool. So I just bound `flyspell-correct-at-point` to "<return>" in the `flyspell-mouse-map`, and now return corrects words when my cursor is on a misspelled word!

But the fact you can attach keymaps to overlays just seems so useful, will definitely use in the future.

## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/p6mwx2/comment/h9e6uqq) 
**Votes:** 18

Here's a popular Emacs config I just rediscovered.  Some cool stuff here.  https://github.com/angrybacon/dotemacs

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h2rizey) 
**Votes:** 18

I have two org mode link tips:

1. `(setq org-return-follows-link t)` lets you press RET to follow a
   link. Don't worry, the traditional behavior of RET, namely inserting
   a newline, is still easy: `C-q C-j`.

2. I often want to see what the destination of a link is. I used to
   use one of these methods:
   
   - mouse hover,
   - running and canceling `org-insert-link` (`C-c C-l`, read the
```elisp
 destination, `C-g`),
```
   - `org-toggle-link-display`, which toggles between the neat formatting of 
```elisp
 links and the raw source and is pretty ugly.
 
```
   But a better option is to use `display-local-help` (`C-h .`), which
   will show the tooltip in the echo area. And, you can even have the
   tooltip at point echoed automatically with `(setq
   help-at-pt-display-when-idle t)`. There is a delay controlled by
   the variable `help-at-pt-timer-delay` which I like to set to 0.
   Beware that just using `(setq help-at-pt-timer-delay 0)` has no effect,
   you need to use `customize-set-variable` or manually cancel the
   timer and set a new one (see below).
   
   Now, these `help-at-pt` variables aren't specifically for org
   links, they control the behavior of all tooltips, and I quickly
   realized I really only wanted to change the behavior in org mode
   buffers. You can do that as follows:
   
```elisp
   (defun echo-area-tooltips ()
     "Show tooltips in the echo area automatically for current buffer."
     (setq-local help-at-pt-display-when-idle t
                 help-at-pt-timer-delay 0)
     (help-at-pt-cancel-timer)
     (help-at-pt-set-timer))

   (add-hook 'org-mode-hook #'echo-area-tooltips)
```

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/12rlq4a/comment/jgwlxuw) 
**Votes:** 17

Often when literate programming I want to split up a code block, maybe copy-pasted with multiple functions in it, into separate blocks so I can put some text in between them. The command, with cursor within a `BEGIN_SRC` block, is `org-babel-demarcate-block` `(C-c C-v d)`.

## u/alvarogonzalezs [🔗](https://www.reddit.com/r/emacs/comments/12jexep/comment/jg34ody) 
**Votes:** 17

I'm a big user of `ffap`. I use this function with `M-x` each time I want to open a file whose name is under the cursor.

But this week I discovered `ffap-bindings`. This function replaces some key bindings to use `ffap` when it makes sense. For example, it replaces `find-file` with `find-file-at-point`, so the usual keybindings are enriched at no cost.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/x27yc9/comment/imi3kzz) 
**Votes:** 17

Update from a couple of weeks ago: after some grinding, I've set the parsing of past comments from this thread to auto update on a weekly basis here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

I've also fixed the broken highlighting of some code snippets, and hopefully parsed all past threads.  There's 200+ comments there (sorted by upvotes), so ctrl-f ing e.g. \`magit\` may help you if you're looking for something specific

## u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/un4wf8/comment/i86hwzi) 
**Votes:** 17

Something amazin that I have been using recently is `ripgrep-all` as the `consult-ripgrep` command to search in pdfs. 

It is amazing with the `orderless` dispatchers to control the search filtering.
I use `!` to exclude a string and `=` to match exactly.

Also amazing with `embark-collect` which allows collapsing features.
Or within the collect buffer use `consult-line` to further filter.
And even open the pdf.

## u/AffectionateAd8985 [🔗](https://www.reddit.com/r/emacs/comments/sd10q9/comment/hu9xfed) 
**Votes:** 17

`(add-hook 'org-mode-hook (lambda () (org-next-visible-heading 1)))`

Move to first heading when open org files, with `org-use-speed-commands`, I can quick browse org file with only `n/p` keys.

## u/laralex [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f5pgfu3) 
**Votes:** 17

A small basic thing, but once I'd discovered it, I started using dired.
```C-x C-j``` is most likely bound to ```dired-jump```, and this function opens dired for this window's file, without promting for directory (and this prompt was an issue for my workflow when using ```C-x d```). That makes finding and switching files just as convenient as in OS GUI. I've also bound a few keys when in dired mode (I find them decent):

```a``` - prompt a name and create empty file

```d``` - prompt a name and create empty dir

```u``` - go to parent dir (the key is a mnemonic to "go Up in directory tree)

```j``` - if it's a dir go into it, otherwise find this file (key is near to 'u' so jumping up and down is not a big deal with one hand, also the 'j' is the easiest key for me as a touchtyper) 

```n```/```p``` - move one entry down/up, which resembles ```C-n```/```C-p```

## u/vkazanov [🔗](https://www.reddit.com/r/emacs/comments/1bdm6mc/comment/kuo1f9y) 
**Votes:** 16

A dump of my Emacs-related principles after 18 years of tinkering:

1. Don't try to replicate a static IDE setup, Emacs is fluid. 
2. Emacs Lisp is inevitable for Emacser to make this fluidity possible.
3. Language-agnostic is better than language-specific. 
4. Embrace display-alist, fast window manipulation, winner-mode. 
5. .emacs.el reset every couple of years to accomodate innovation. 
6. Org-mode/org-roam for all documentation, projects, tips. The agenda is not set in stone. Use queries, filters, tweak, evolve things. 
7. A contextual dwim is always better than many keybindings. 
8. Use completion everywhere on everything (vertico is magic).  
9. Contribute to the core and favourite packages.

I am a beginner though, things might change.

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/1758wua/comment/k4g09iw) 
**Votes:** 16

`(delete-blank-lines)` `(C-x C-o)` is massively useful; I use it every day for text cleanup. Press it once and it deletes all but one blank line. Press it twice and it deletes that one, too.

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/vnals8/comment/ie7p6ja) 
**Votes:** 16

I recently discovered `thing-at-point-looking-at`, which seems much easier to use on its own than to fully define a new kind of "thing."

For a while I've been wanting to conveniently identify a Jira ticket identifier at point so I can browse to it.  Ticket IDs are basically a sequence of letters, a hyphen, and a sequence of digits.  First I tried using `symbol-at-point`, but that can include extraneous neighboring characters, like `/` when the ticket ID is part of a URL.  Eventually, while poring over the `thingatpt` source, I found `thing-at-point-looking-at`, which quickly led to:

```elisp
(defun browse-ticket-at-point ()
  (interactive)
  (if (thing-at-point-looking-at (rx (+ alpha) "-" (+ digit)))
      (browse-url (format "https://jirahost/browse/%s" (match-string 0)))
    (error "No ticket at point")))

```
Easy peasy!

## u/hxlr666 [🔗](https://www.reddit.com/r/emacs/comments/rvagin/comment/hr4bbl5) 
**Votes:** 16

Use org capture

## u/TeMPOraL_PL [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnx4z28) 
**Votes:** 16

If you're like me, and your day ends way past midnight, handling those last few tasks in your Org Mode agenda gets tricky. Fortunately, it turns out Org Mode has what I call "25th hour mode".

```elisp
;; consider the current day to end at 3AM
(setq org-extend-today-until 3) 
    
;; make timestamp processing functions aware of this
(setq org-use-effective-time t) 

```
Combined, this allows to extend the day past midnight, with things like agenda views, scheduling commands, repeaters, etc. thinking the current time is 23:59 up until the `org-extend-today-until` limit. With this enabled, if I have a task that has a repeater of  and complete it at 01:00, I no longer have to then manually reschedule the task back one day.

## u/gopar [🔗](https://www.reddit.com/r/emacs/comments/k4gv0x/comment/ge9det9) 
**Votes:** 16

A very simple thing I've done is remap ";" (semicolon) to to "\_" (underscore) in almost all modes. Since I work with mainly Python, this is so much easier than always doing SHIFT-DASH every couple of keystrokes. And if I want a regular semicolon, I just do "C-u ;" and insert a semicolon

I also set this in modes such as C/C++, etc. This works by automatically setting the last character (if it was an underscore) to a semicolon on enter. 

eg. "|" is cursor

int a = 10\*10\_|

turns into

int a = 10\*10;

| (cursor on new line)

&#x200B;

Pretty simple time saver \\o/

## u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/jn6m14/comment/gazzdyz) 
**Votes:** 16

It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.

## u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/xw4muy/comment/ir96qmu) 
**Votes:** 15

I finally got around to writing a small README for my Emacs config, highlighting some homegrown parts that I really like.  I reckon most of these things are pretty standard, but maybe some people here still find it useful: 

  https://gitlab.com/slotThe/dotfiles/-/tree/master/emacs/.config/emacs

## u/meain [🔗](https://www.reddit.com/r/emacs/comments/wwdpju/comment/ilotsc5) 
**Votes:** 15

I use the following snippet to change background color of compilation buffer to a light red if the compilation failed. I use compilation buffer to run tests on a second monitor and this is pretty useful.

```elisp
(defun meain/compilation-colorcode (_buffer string)
    "Change background color of compilation `_BUFFER' to red on failure."
    (unless (string-prefix-p "finished" string) ; Having color for success was distracting
    (face-remap-add-relative 'default 'diff-hl-delete)))
(add-to-list 'compilation-finish-functions 'meain/compilation-colorcode)
```

## u/rucci99 [🔗](https://www.reddit.com/r/emacs/comments/r69w7i/comment/hmryv5o) 
**Votes:** 15

I just found out that Magit can backup changes of uncommitted files automatically. Here's the link to online manual:
[Magit Wip Modes](https://magit.vc/manual/magit/Wip-Modes.html#Wip-Modes).

## u/globalcandyamnesia [🔗](https://www.reddit.com/r/emacs/comments/ooldn6/comment/h67qge6) 
**Votes:** 15

I'm trying to feminize my voice and org mode has been invaluable.

```elisp
(org-babel-do-load-languages 'org-babel-load-lanuages
  '((shell . t)))
    
(setq org-capture-templates
  `(("v" "Voice" entry
    (file+olp+datetree ,(concat my-org-directory "voice/training.org"))
    ,(concat
      "* Record \n"
      "\n"
      "  #+begin_src sh\n"
      "    rec %(format-time-string \"%Y-%m-%d-%H.%M.%S\").aiff\n"
      "  #+end\_src\n"
      "\n"
      "* Play\n"
      "  #+begin_src sh\n"
      "    play %(format-time-string \"%Y-%m-%d-%H.%M.%S\").aiff\n"
      "  #+end_src\n")
    :immediate-finish t
    :jump-to-captured t)))

```
This requires 'SoX' for linux. You can go into the record src block and press \`C-c C-c\` to start recording and \`C-g\` to end. To play back the recording, press \`C-c C-c\` within the play src block. I imagine this might be useful beyond the trans community for basic voice journaling.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/hqxm5v/comment/fy1rq34) 
**Votes:** 15

Migrated to native compiled emacs branch this week. Some hiccups but everything seems to work out of box, including pdf-tools. Great performance improvement.

## u/freesteph [🔗](https://www.reddit.com/r/emacs/comments/eoigxl/comment/fed40nx) 
**Votes:** 15

If you needed more reasons to love Magit I've just found out can add the Git meta-fields in the commit message (`Co-authored-by`, `Signed-off-by`, etc, which I can never remember correctly) by typing `C-c TAB` which will interactively ask you for the field (`C-a` -> `Co-authored-by`) and then also interactively fill the relevant team member with their name and e-mail (probably from the repo's list of committers). Awesome!

## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/y7wrdn/comment/isze25m) 
**Votes:** 14

not emacs per se, but jack rusher did a talk about programming 'ux / ergonomics / pragmatics' with a lot of fun ideas about coding, past (lisp machines, smalltalk ... ) or more recent clojure based tools

https://www.youtube.com/watch?v=8Ab3ArE8W3s

hope you enjoy it

warning: poop emoji

## u/thr33body [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikqxn0r) 
**Votes:** 14

I don’t have any specific tip but I just want to throw it out there that if you are tired of using spacemacs or doom it was much easier to set up my own install than I thought. It only took me a couple of days of active work and now diagnosing problems is so much simpler. Not to say that you should not use either one but I wanted to learn more about emacs and I’ve been really happy with the results.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/sd10q9/comment/hubjy3j) 
**Votes:** 14

I was reading through the org manual, and learnt about two variables `org-agenda-category-icon-alist` and `org-agenda-prefix-format`, the first allows you to set icons for categories (`CATEGORY` property), icons can be images or symbols, this is the code I came up with and the agenda already looks more colorful and clear


```elisp
(setq org-agenda-category-icon-alist nil)
(setq agenda-categories-alist
'(("WORK" "💼") ("SOFTWARE" "💻") ("SETUP" "🐧") ("EMAIL" "✉️")
("HOME" "🏠") ("WOOD" "🪵") ("FAMILY" "👪") ("REPORTS" "📚")
("INCOME" "💰")))
(dolist (icon agenda-categories-alist) (add-to-list 'org-agenda-category-icon-alist
`(,(car icon) ,(cdr icon) nil nil :width (16.) :ascent center)))
(defun format-agenda-prefix () (interactive)
(setcar org-agenda-prefix-format '(agenda . "  %-2i  %?-12t% s")))
(add-hook 'org-agenda-mode-hook 'format-agenda-prefix)

```
Of course for the emojis to show up correctly I use this:

```elisp
(set-fontset-font "fontset-default" 'symbol (font-spec :family "Noto Color Emoji"))

```
I am sure a lot of you know about these, please share your customization

## u/el_tuxo [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnp5rhn) 
**Votes:** 14

Working on a remote server with Tramp in eshell it's so easy that I'm always worried that I could run by mistake a command on the wrong machine.

So I implemented a small function that makes me aware that I'm in a Tramp session by changing the prompt color.

```elisp
(require 'subr-x)  
(defun tuxo/prompt-color-tramp ()  
"Change prompt color if a tramp session is open"  
  (if (file-remote-p default-directory)  
      (set-face-foreground 'eshell-prompt "red")  
      (set-face-foreground 'eshell-prompt "green")))
    
(use-package eshell
  :hook (eshell-post-command . tuxo/prompt-color-tramp))

```
Do you have any suggestions on how I could improve this issue?

## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/q76kok/comment/hgk3wik) 
**Votes:** 14

This is pretty neat: scrolling up/down one line at a time while keeping the position of point:

`(setq scroll-preserve-screen-position 1)`  
`(global-set-key (kbd "M-n") (kbd "C-u 1 C-v"))`  
`(global-set-key (kbd "M-p") (kbd "C-u 1 M-v"))`  


From: http://pragmaticemacs.com/emacs/scrolling-and-moving-by-line/

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/ofen99/comment/h4dxjbz) 
**Votes:** 14

If you want to search and replace but with preview for the matches, don't use `query-replace-regexp` directly. Instead start by searching for your regexp in `isearch-forward-regexp`, which highlights the matches interactively, and once you have the correct regexp, run `isearch-query-replace` (bound to `M-%` in `isearch-mode-map`).

Note that there is also an `isearch-query-replace-regexp` command but you don't need it: `isearch-query-replace` will automatically detect if your isearch session was for regexps. The docstring for `isearch-query-replace` doesn't seem to mention this nice feature.

## u/Tatrics [🔗](https://www.reddit.com/r/emacs/comments/n9q662/comment/gxpeh9v) 
**Votes:** 14

I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \*scratch\* buffer and then evaluate it with \`C-x C-e\`.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what  do you think in general!

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/mg98ki/comment/gstteeo) 
**Votes:** 14

I just discovered the [selected](https://github.com/Kungsgeten/selected.el) package, which is brilliant. It creates a keymap that becomes active any time you have an active region. I have bindings for next-line, previous-line, rectangle-mark-mode, end-of-line, upcase-dwim, exchange-point-and-mark, etc. It makes editing and acting on the active region super easy. Sort of like god-mode or Vim's visual mode.

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/lapujj/comment/glr8pkr) 
**Votes:** 14

You can use EWW to bypass pay-walls on news sites, and other Javascript-enabled nastiness. Plus, eww can copy from what it sees into equivalent orgmode syntax, and it's also compatible with SPRAY for speed-reading. In otherwords, EWW is great for when you just need to READ the internet.

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/fs93hk/comment/fm1fw1x) 
**Votes:** 14

For me, learning about `kill-whole-line` (control-shift-delete) was a revelation.  Compared to the "classic" Emacs method of deleting lines (C-a to go to the beginning of the line if not already there, C-k once to kill to the end of line, C-k again to kill the newline), it feels like a major speedup.

I still use C-k frequently, but it's effectively just a kill-to-end-of-line command.

## u/mullikine [🔗](https://www.reddit.com/r/emacs/comments/eeyhdz/comment/fc1u840) 
**Votes:** 14

Making your own hooks using advice. So a hook doesn't exist where you'd like to be hooking into? You can use advice to create a hook.

```elisp
(defvar eww-restore-history-after-hook '())
(defun eww-restore-history-after-advice (&rest args)
  (run-hooks 'eww-restore-history-after-hook))
(advice-add 'eww-restore-history :after 'eww-restore-history-after-advice)
(add-hook 'eww-restore-history-after-hook (lambda ()
                                        (interactive)
                                        (rename-eww-buffer-unique)) t)
```

## u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/1b20xgn/comment/ksifwh1) 
**Votes:** 13

If you use an LSP server with semantic highlighting, it's worth checking out the value of `font-lock-maximum-decoration`. For example, I use `c++-mode` with `lsp-mode` (with clangd), I decreased `font-lock-maximum-decoration` to `2`, and I didn't notice any highlighting difference (because the lost highlighting by `c++-mode` gets highlighted by `lsp-mode`), while `c++-mode` font-locking become faster (`c++-mode` 's font-locking works well 99.9% of the time, but sometimes it can become slow in some circumstances, these slowdowns seems to be gone).

I use:`(setq font-lock-maximum-decoration '((c-mode . 2) (c++-mode . 2) (t . t)))`

## u/alvarogonzalezs [🔗](https://www.reddit.com/r/emacs/comments/16tes2a/comment/k2gb81l) 
**Votes:** 13

If you need to find all the occurrences of a string in the project files, but only **on some specific type of files**, you can use `consult-ripgrep` with `-- -t` in the search pattern.

For example, If you need occurrences of `fancystr` in files of type `html`, the search pattern should be `fancystr -- -t html`

From `consult-grep` documentation, command line options can be passed to grep, specified behind `--.`  The overall prompt input has the form:

```elisp
#async-input -- rg-opts#filter-string

```
I have just discovered this, and it made my day.

## u/leothrix [🔗](https://www.reddit.com/r/emacs/comments/13jvhp7/comment/jl5zu6z) 
**Votes:** 13

For `use-package` users (which I assume is many of us), did you know that profiling is easy to do? I'm not talking about `esup`, but a built-in capability that makes it very straightforward to find places to optimize your `init.el` for significantly faster start times.

Enable `use-package-compute-statistics` right after you load `use-package`:

```elisp
(setq use-package-compute-statistics t)

```
Restart emacs, and then invoke `use-package-report`. You'll get a table of the load times for each package that `use-package` manages. I discovered this and found an immediate way to cut my startup time in half by fixing a few packages that weren't deferred properly by adding the right `:hook` keyword.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/11rq2gl/comment/jc9t4tc) 
**Votes:** 13

Plain old `query-replace` has many cool features, first of all it respects the active region (if it's active it will only query for replacements in the active region).  There are many useful keys in addition to plain `y`/`n`:

`!`: replaces all remaning matches

`u`: undo last replacement

`E`: changes replacement string on the fly

And many more you can see using `?`.

## u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/11lqkbo/comment/jbe06qv) 
**Votes:** 13

You can toggle vertico's height between 15 and "almost full frame" with this. When vertico is invoked, it will always have a height of 15. But if you have a lot of matches, and like to have a better overview, press the binding, and vertico will show a full frame of matches. This is useful for example when `consult-buffer` presents a lot of buffers.

```elisp
(advice-add 'vertico--setup :before (lambda () (setq vertico-count 15)))
(define-key minibuffer-local-map (kbd "s-'") (lambda ()
  (interactive)
  (let ((vertico-resize t))
    (setq vertico-count (if (= vertico-count 15) (- (frame-height) 5) 15))
    (vertico--exhibit))))

```
Another useful feature is to kill buffers in `consult-buffer` without manually invoking `embark-act`. I miss this feature from Helm, where you can do actions using only one binding (no need to press an intermediate binding which invokes `embark-act`). Note, I just blindly copied some of the logic from `embark`, maybe there are some unnecessary things here.

```elisp
(defun my-embark-M-k (&optional arg)
  (interactive "P")
  (require 'embark)
  (if-let ((targets (embark--targets)))
      (let* ((target
              (or (nth
                  (if (or (null arg) (minibufferp))
                      0
                    (mod (prefix-numeric-value arg) (length targets)))
                  targets)))
            (type (plist-get target :type)))
        (cond
         ((eq type 'buffer)
          (let ((embark-pre-action-hooks))
            (embark--act 'kill-buffer target)))))))

(define-key minibuffer-local-map (kbd "M-k") 'my-embark-M-k)

```
I'm not sure whether these two can be achieved out-of-the box, but I didn't find these functionalities, so I created them.

## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/y1y0kq/comment/is1ygyw) 
**Votes:** 13

I've been using `mu4e` for some years now, and I really love it. However, I'm also a Gmail user, don't love it, but consider myself stuck with it (given budget constraints and it being my email for several years already). This makes me always weary of "the next Google shenanigan" which might break my workflow. One of the things I have learned to cherish about using `mu4e` is its integration with Org, with it's `org-capture` support, which enables me not to confuse my Inbox with my todo list, thus keeping my Inbox clean. So I came up with a preventive function, leveraging `org-protocol` to be able to capture a Gmail message from a bookmarklet on the browser.  Not particularly pretty code, but functional.

```elisp
(with-eval-after-load 'org
  ;; Add org-protocol to capture email in Gmail.
  (add-to-list 'org-protocol-protocol-alist
               '("org-gmail-capture" :protocol "gmail-capture"
                 :function gb/org-protocol-gmail-capture))

  ;; Bookmarklet:
  ;;   javascript:location.href = 'org-protocol://gmail-capture?' +
  ;;       new URLSearchParams({
  ;;           msg: document.getElementById("raw_message_text").innerHTML});
  ;; Must be called from the "Original message" page.
  (defun gb/org-protocol-gmail-capture (info)
    "Process an org-protocol://gmail-capture style url with INFO.

This function detects the raw message text element from the \"Original
message\" page of a Gmail message.  It then parses relevant message
fields and calls the \"email\" org-capture template.

The location for a browser's bookmark looks like this:

  javascript:location.href = \\='org-protocol://gmail-capture?\\=' +
      new URLSearchParams({
          msg: document.getElementById(\"raw_message_text\").innerHTML});

The sub-protocol used to reach this function is set in
`org-protocol-protocol-alist'."
    (when-let* ((parts (org-protocol-parse-parameters info))
                (msg (plist-get parts ':msg)))
      ;; FIXME I'm not sure why this is needed, but the protocol fails on a
      ;; fresh session (before 'org-capture' is loaded), and the new frame
      ;; just flashes on the screen.
      (require 'org-capture)
      (let (subj id to from date
            from-name from-email to-name to-email from-to-name)
        (with-temp-buffer
          (insert msg)
          ;; 'mu4e~view-render-buffer' is responsible in mu4e to view /
          ;; display a new message, it handles decoding, fontification
          ;; etc.  However, the message we get from org-protocol is not
          ;; really the "original" it is rather a html rendered version of
          ;; it.  A "Download original" button exists, but we wouldn't be
          ;; able to retrieve it, since only the browser is logged into
          ;; the Gmail account, so we have to do with whatever the page
          ;; displays.  That given, prettifying the buffer is of little
          ;; use for the fields we are interested in.
          (goto-char (point-min))
          (let ((case-fold-search))
            (while (re-search-forward
                    (rx
                     line-start
                     (group
                      (or
                       "From:"
                       "To:"
                       "Subject:"
                       "Date:"
                       "Message-ID:"))
                     " "
                     (group (zero-or-more not-newline))
                     line-end)
                    nil t)
              (pcase (match-string 1)
                ("From:" (setq from (match-string 2)))
                ("To:" (setq to (match-string 2)))
                ("Subject:" (setq subj (match-string 2)))
                ("Date:" (setq date (match-string 2)))
                ("Message-ID:" (setq id (match-string 2)))))))

        ;; Ensure values
        (when (or (not from) (string-blank-p from))
          (setq from "<none>"))
        (when (or (not to) (string-blank-p to))
          (setq to "<none>"))
        (when (or (not subj) (string-blank-p subj))
          (setq subj "No subject"))
        (unless date (setq date ""))
        (unless id (setq id ""))
        ;; Clean fields
        (setq from (replace-regexp-in-string "&lt;" "<" from t t))
        (setq from (replace-regexp-in-string "&gt;" ">" from t t))
        (setq to (replace-regexp-in-string "&lt;" "<" to t t))
        (setq to (replace-regexp-in-string "&gt;" ">" to t t))
        (setq id (replace-regexp-in-string "&lt;" "" id t t))
        (setq id (replace-regexp-in-string "&gt;" "" id t t))

        (let ((addr (mail-extract-address-components from)))
          (if (car addr)
              (progn
                (setq from-name (car addr))
                (setq from (format "%s <%s>" (car addr) (cadr addr))))
            (setq from-name (cadr addr))
            (setq from (format "<%s>" (cadr addr))))
          (setq from-email (cadr addr)))
        (let ((addr (mail-extract-address-components to)))
          (if (car addr)
              (progn
                (setq to-name (car addr))
                (setq to (format "%s <%s>" (car addr) (cadr addr))))
            (setq to-name (cadr addr))
            (setq to (format "<%s>" (cadr addr))))
          (setq to-email (cadr addr)))
        (setq from-to-name
              (if (member from-email
                          '("myemail1@domain.com"
                            "myemail2@domain.com"
                            "myemail3@domain.com"
                            "myemail4@domain.com"))
                  to-name
                from-name))

        (let ((props `(:type "gmail"
                       :date ,date
                       :from ,from
                       :fromname ,from-name
                       :message-id ,id
                       :subject ,subj
                       :to ,to
                       :toname ,to-name
                       :annotation ,(org-link-make-string
                                     (concat "gmail:" id) subj)
                       :link ,(org-link-make-string (concat "gmail:" id))
                       :description ,(format "%s (%s)" subj from-to-name)
                       :annotation ,(concat "gmail:" id)))
              ;; Avoid call to `org-store-link', see 'org-protocol-capture'.
              (org-capture-link-is-already-stored t))
          (apply #'org-link-store-props props)

          (raise-frame)
          ;; Hard-coding the "e" capture template, since this function is very
          ;; much tailor made for it.
          (org-capture nil "e")))
      ;; Make sure we do not return a string, see 'org-protocol-capture'.
      nil))

  (org-link-set-parameters "gmail" :follow #'gb/org-link-gmail-open)
  (defun gb/org-link-gmail-open (link _)
    (kill-new (concat "rfc822msgid:" link))
    (message "Message id copied to clipboard.")))

```
The corresponding bookmarklet is:

```elisp
javascript:location.href = 'org-protocol://gmail-capture?' + new URLSearchParams({msg: document.getElementById("raw_message_text").innerHTML});

```
This must be called from the "Original message" page (which you can get with the "Show original" menu item). It won't work elsewhere.

The link created is a `gmail:` type link which essentially just copies `rfc822msgid:<messageID>` to the kill-ring/clipboard, which you can paste in Gmail's search bar to go to the message of interest.

Not that it matters much, but the corresponding capture template is:


```elisp
      ("e" "email" entry
       (file+headline (lambda ()
                        (expand-file-name gb/email-capture-file
                                          gb/org-files-directory))
                      "Email")
       "* TODO %?%:description %(org-set-tags \":email:\")
:PROPERTIES:
:Message: %a
:From: %:from
:To:   %:to
:Date: %:date
:END:
:LOGBOOK:
- Created on %U
:END:"
       :empty-lines 1)
```

## u/com4 [🔗](https://www.reddit.com/r/emacs/comments/xq6rpa/comment/iqb2fci) 
**Votes:** 13

In preparation for the inclusion of eglot into Emacs core I've switched away from lsp-mode. As a result I've also switched from flycheck and to flymake. One nice thing about flycheck is that it allowed for stacking checkers. When coding Python I liked to stack flake8 for styles and mypy for types (plus the LSP's since it's already there).

Flymake allows for stacking checkers but it turns out eglot clobbers these checkers when "adding" the LSP's checker. To get stacked Flymake checkers with Eglot you can simply add them back after Eglot has done it's thing.

For example, here is a simple setup for Python which includes Pyright's type checking and flake8 style checking

```elisp
;; Use flake8 as the python style checker by default
(setq python-flymake-command '("flake8" "-"))
    
(use-package eglot
  :hook ((python-mode . eglot-ensure)
         (eglot-managed-mode
	  . (lambda ()
	      ;; re-enable flymake checkers because eglot clobbers
	      ;; them when starting
	      (when (derived-mode-p 'python-mode)
		(add-hook 'flymake-diagnostic-functions 
                          'python-flymake nil t)))))
    
  :custom
  ;; shutdown server after killing last managed buffer
  (eglot-autoshutdown t)
  :bind
  (("C-c l r" . eglot-rename))
  :config
  (add-to-list 'eglot-server-programs
               `(python-mode "pyright-langserver" "-w" "--stdio")))

```
Now all that's left is mypy. Flymake originally used a ["Proc"](https://www.gnu.org/software/emacs/manual/html_mono/flymake.html#The-legacy-Proc-backend) method for creating checkers which existing mypy ones use. So for a bonus tip & trick [I've written a mypy checker in the new style](http://github.com/com4/flymake-mypy). It can be enabled when using eglot like this:

```elisp
(use-package flymake-mypy
  :straight (flymake-mypy
             :type git
             :host github
             :repo "com4/flymake-mypy")
  :hook ((eglot-managed-mode . (lambda ()
				 (when (derived-mode-p 'python-mode)
				   (flymake-mypy-enable))))))

```
After opening a Python buffer and executing `M-x flymake-running-backends` we are greeted with a list of stacked checkers: `Running backends: eglot-flymake-backend, flymake-mypy--run, python-flymake`.

## u/vatai [🔗](https://www.reddit.com/r/emacs/comments/ojzv53/comment/h5584no) 
**Votes:** 13

The emacs lisp tutorial is the real tutorial for emacs ;)

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/mujxm7/comment/gv8jxz5) 
**Votes:** 13

I use, and love, [transient](https://github.com/magit/transient). I have a ton of commands set up, but the below command is for window manipulation. Personally, I bind it to `s-w`. I use [buffer-move](https://github.com/lukhas/buffer-move) for rearranging windows in a frame.

```elisp
(define-transient-command transient-window ()
  "Most commonly used window commands"
  [["Splits"
    ("s" "Horizontal" split-window-below)
    ("v" "Vertical"   split-window-right)
    ("b" "Balance"    balance-windows)
    ("f" "Fit"        fit-window-to-buffer)
   ["Window"
    ("c" "Clone Indirect" clone-indirect-buffer)
    ("t" "Tear Off" tear-off-window)
    ("k" "Kill" delete-window)
    ("K" "Kill Buffer+Win"  kill-buffer-and-window)
    ("o" "Kill Others"  delete-other-windows)
    ("m" "Maximize" maximize-window)]
   ["Navigate"
    ("<left>"  "←" windmove-left  :transient t)
    ("<right>" "→" windmove-right :transient t)
    ("<up>"    "↑" windmove-up    :transient t)
    ("<down>"  "↓" windmove-down  :transient t)]
   ["Move"
    ("S-<left>"  "S-←" buf-move-left  :transient t)
    ("S-<right>" "S-→" buf-move-right :transient t)
    ("S-<up>"    "S-↑" buf-move-up    :transient t)
    ("S-<down>"  "S-↓" buf-move-down  :transient t)]
   ["Undo/Redo"
    ("s-z" "Winner Undo" winner-undo :transient t)
    ("s-Z" "Winner Redo" winner-redo :transient t)]])
```

## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/lvw44q/comment/gpeb8n3) 
**Votes:** 13

Here's a nice eshell command:

```elisp
(defun eshell/history ()
  (interactive)
  (insert
   (completing-read "History: " (delete-dups (ring-elements eshell-history-ring)))))

```
It lets you use your normal completion framework to select an item from history. Suddenly fzf-like history!

## u/mullikine [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fwbtnte) 
**Votes:** 13

## Use chrome DOM for eww

Basically, a lot of websites these days generate the DOM using javascript. You can dump the DOM from chrome and inject it into eww just before it renders.

It's set to wait 3 seconds before dumping the DOM. This allows many pages to load.

Since I'm using the `unbuffer` program, this requires `expect` to be installed on your system. It creates a tty so that chrome doesn't crash when run in this way.

`dump-dom` shell script

```elisp
#!/bin/bash
    
url="$1"
test -n "$url" || exit 1
    
0</dev/tty unbuffer bash -c "chrome --headless --disable-gpu --virtual-time-budget=3000 --dump-dom \"$url\" 2>/dev/null"

```
Make these modifications to `eww-display-html`.

`eww-display-html`

```elisp
(defun eww-display-html (charset url &optional document point buffer encode)
  (unless (fboundp 'libxml-parse-html-region)
    (error "This function requires Emacs to be compiled with libxml2"))
  (unless (buffer-live-p buffer)
    (error "Buffer %s doesn't exist" buffer))
  ;; There should be a better way to abort loading images
  ;; asynchronously.
  (setq url-queue nil)
  ;; If document exists then the html is already parsed into a DOM
  (let* ((html (shell-command-to-string (concat "dom-dump " (shell-quote-argument url))))
         (document
          (or nil ;; document
              (list
               'base (list (cons 'href url))
               (progn
                 (setq encode (or encode charset 'utf-8))
                 (condition-case nil
                     (decode-coding-region (point) (point-max) encode)
                   (coding-system-error nil))
                 (save-excursion
                   ;; Remove CRLF before parsing.
                   (while (re-search-forward "\r$" nil t)
                     (replace-match "" t t)))
                 (save-mark-and-excursion
                   ;; Delete from here to the end. Replace with the new html
                   (kill-region (point) (point-max))
                   (insert (encode-coding-string html 'utf-8)))
                 (libxml-parse-html-region (point) (point-max))))))
         (source (and (null document)
                      (buffer-substring (point) (point-max)))))
    (with-current-buffer buffer
      (setq bidi-paragraph-direction nil)
      (plist-put eww-data :source html)
      (plist-put eww-data :dom document)
      (let ((inhibit-read-only t)
            (inhibit-modification-hooks t)
            (shr-target-id (url-target (url-generic-parse-url url)))
            (shr-external-rendering-functions
             (append
              shr-external-rendering-functions
              '((title . eww-tag-title)
                (form . eww-tag-form)
                (input . eww-tag-input)
                (button . eww-form-submit)
                (textarea . eww-tag-textarea)
                (select . eww-tag-select)
                (link . eww-tag-link)
                (meta . eww-tag-meta)
                (a . eww-tag-a)))))
        (erase-buffer)
        (shr-insert-document document)
        (cond
         (point
          (goto-char point))
         (shr-target-id
          (goto-char (point-min))
          (let ((point (next-single-property-change
                        (point-min) 'shr-target-id)))
            (when point
              (goto-char point))))
         (t
          (goto-char (point-min))
          ;; Don't leave point inside forms, because the normal eww
          ;; commands aren't available there.
          (while (and (not (eobp))
                      (get-text-property (point) 'eww-form))
            (forward-line 1)))))
      (eww-size-text-inputs))))

```
Demonstration:
https://asciinema.org/a/UAAVfp5O8SofJZvKBusTOP8QQ

## u/_hmenke [🔗](https://www.reddit.com/r/emacs/comments/gqsz8u/comment/fruqs1k) 
**Votes:** 13

Any **BibTeX** users here?

- Tired of journals forcing you to download a file to get the BibTeX record of an article?
- Tired of their usually broken formatting?
- The journal doesn't offer BibTeX download in the first place? (Looking at you Nature)

Did you know that doi.org has query interface that gives you the BibTeX record when you call it with the article DOI?  Of course you can access this via Emacs:

```elisp
(require 'url)
(defun user/url-bibtex-from-doi (doi)
  (interactive "sDOI: ")
  (let* ((url (concat "https://doi.org/" doi))
         (url-mime-accept-string "application/x-bibtex"))
    (insert
     (with-current-buffer (url-retrieve-synchronously url)
       (let* ((start url-http-end-of-headers)
              (end (point-max))
              (all (buffer-string))
              (body (buffer-substring start end)))
         (replace-regexp-in-string "^\t" "  " (url-unhex-string body)))))))

```
Just paste the DOI of the article at the prompt and the BibTeX record will be inserted at point.

Here is how I bind it in `bibtex-mode` (plus my other bindings for good measure)
    
```elisp
;; bibtex
(use-package bibtex
  :bind (:map bibtex-mode-map
              ("C-c d" . user/url-bibtex-from-doi)
              ("C-c v" . bibtex-validate)
              ("C-c s" . bibtex-sort-buffer)
              ([down-mouse-3] . imenu))
  :config
  (setq
   bibtex-maintain-sorted-entries t))
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/g11mp9/comment/fnd7zkx) 
**Votes:** 13

If you're not using [`native-comp`](http://akrl.sdf.org/gccemacs.html) feature yet, I strongly recommend you to try it out! Have been using it for two weeks for now and everything seem to run pretty smooth and rock solid! The speed difference is quite noticeable in some interactive aspects like completion, although my machine is quite slow, so this may not be that noticeable on newer machines.

## u/zoechi [🔗](https://www.reddit.com/r/emacs/comments/fwgpkd/comment/fmo9d5v) 
**Votes:** 13

I (Emacs rookie) just found out that native/fast JSON support is not guaranteed when emacs 27+ is used. jansson-dev needs to be installed when Emacs is built https://github.com/emacs-lsp/lsp-mode/issues/1557#issuecomment-608409056

## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/fo1fm3/comment/flcwsu4) 
**Votes:** 13

Happy Birthday from Emacs, let's assume March 24 is your birthday, put this to your init file, when you open Emacs on your birthday, you'll receive a birthday present from Emacs

```elisp
(when (string= "03-24" (format-time-string "%m-%d"))
  (animate-birthday-present user-full-name))
```

## u/loopsdeer [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/fiqj0dn) 
**Votes:** 13

You can store a bookmark on a Magit status window and it WORKS!

I just tried it last night figuring it would err when I tried to open the bookmark later, and it worked and I'm very excited about it.

This is the perfect entry point for projects for me, being reminded of what state source control is in. I used to jump to Dired in the .git root or some main code window but neither were helpful to start my day. This is perfect.

I set the bookmark with \`helm-filtered-bookmarks\` by just being on the Magit window and running that then typing in my name, and this is what was added to my bookmark file:

```elisp
("my magit bookmark title"
 (front-context-string . "Unstaged changes")
 (rear-context-string . "[redacted for reddit]\n\n")
 (position . 124)
 (handler . magit--handle-bookmark)
 (mode . magit-status-mode)
 (filename . "/redacted/path/to/.git")
 (magit-hidden-sections
  (stashes . "refs/stash")
  (unpushed . "@{upstream}..")))

```
I quit Emacs, made an edit in my .git elsewhere, opened Emacs back up and jumped to this bookmark and it showed me the change! 

It's funny how excited I am to remove two keystrokes from the beginning of my day. Probably I am also excited that my random experiment worked. Obviously it's not so random as magit knew exactly what I wanted. Magit is life! </rant>

## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/drw8i3/comment/f6ncyes) 
**Votes:** 13



```elisp
emacs --batch -l cl-lib --eval "(cl-loop (print (eval (read))))"
```

## u/eleven_cupfuls [🔗](https://www.reddit.com/r/emacs/comments/10ktqj0/comment/j5umed8) 
**Votes:** 12

I jump into the built-in Elisp files a lot to see how things work. The indentation there is GNU standard, which uses a mix of tabs and spaces for alignment. The tabs have to be rendered as 8 spaces wide for the alignment to work, and I don't actually want that setting anywhere else. Since the files on Mac are inside the application bundle, I don't want to just add a .dir-locals.el file next to them, either.

[Directory classes](https://www.gnu.org/software/emacs/manual/html_node/emacs/Directory-Variables.html) to the rescue! My config makes a new directory variable class, `builtin-elisp`, with a list saying that `emacs-lisp-mode` should use a `tab-width` of 8. Then it applies that class to the Elisp files in the application bundle:

```elisp
(use-package elisp-mode
  :config
  (dir-locals-set-class-variables
   'builtin-elisp
   '((emacs-lisp-mode . ((tab-width . 8)))))
  (dir-locals-set-directory-class
   (file-name-directory (directory-file-name (invocation-directory)))
   'builtin-elisp))

```
And now when I visit one of those files, the alignment is always correct.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/wf0t0d/comment/iirl0ea) 
**Votes:** 12

Org mode - insert a complete set of export options:

`org-export-insert-default-template`

This inserts all export keywords with default values at beginning of line.

This command is not documented in `info emacs` (v27.1).

## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hf1gzs2) 
**Votes:** 12

Read [SICP](https://mitpress.mit.edu/sites/default/files/sicp/index.html). Preferably in Info, installable through MELPA or: [https://github.com/webframp/sicp-info](https://github.com/webframp/sicp-info)

## u/emacs-noob [🔗](https://www.reddit.com/r/emacs/comments/kvmmq3/comment/gj1kn9i) 
**Votes:** 12

I use Emacs for React development and it's usually great (rjsx-mode). We recently introduced styled components into our app and while they're very handy, not having proper css support inside rjsx-mode was pretty annoying. I was looking for solutions, maybe extending rjsx-mode, but I wasn't up to that task. I then realized the built-in emacs commands and buffers themselves could solve my problem! What I want is for css inside a styled component, which always looks something like this:

```elisp
const myDiv = styled.div` // notice the backtick
    Some css...
 ` // ending backtick

```
to *actually* use scss-mode when editing, and then return to rjsx-mode when finished. The elisp is very simple and leads to a trivial workflow:

```elisp
;; The following 2 functions allow editing styled components with all scss mode features.
(defun edit-styled-component ()
  (interactive)
  (progn
    (save-excursion
      (let ((start (search-backward "`"))
            (end (search-forward "`" nil nil 2))) ; second occurrence, since first is `start'
        (narrow-to-region start end)))
    (scss-mode)))

(spacemacs/set-leader-keys-for-major-mode 'rjsx-mode
  "ms" 'edit-styled-component)

;; When editing is done, use the same key sequence to return to the original file.
(defun return-from-styled-component ()
  (interactive)
  (progn
    (widen)
    (rjsx-mode)))

(spacemacs/set-leader-keys-for-major-mode 'scss-mode
  "ms" 'return-from-styled-component)


```
So now when I edit a styled component I just hit **, m s**, which narrows the region to whatever is enclosed by backticks (i.e. all the css) and actually treats it as a bona fide css buffer, with all my snippets, completion, etc. Then when I'm done I just got **, m s** again to widen back to the original (rjsx) buffer!

## u/rhmatthijs [🔗](https://www.reddit.com/r/emacs/comments/gzivu3/comment/ftgqnbp) 
**Votes:** 12

Working in education, I often find myself having to assign students into groups. This week I made a function in ELisp that helps me do this. Select a region in a buffer that contains a list of students (presumably), call this function, say how many students should be in each group and the function then randomly assigns groups.

```elisp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                 ;;
;; Shuffling things.                                    ;;
;;                                                                 ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    
(defun mcj/shuffle (input)
  " Shuffle a list in place. For some reason does not exist in
Emacs by default. Uses Fisher-Yates shuffle.
"
  (let ((swap (lambda (list-to-swap i1 i2)
                (let ((tmp (elt list-to-swap i1)))
                  (setf (elt list-to-swap i1) (elt list-to-swap i2))
                  (setf (elt list-to-swap i2) tmp)))))
    (dotimes (i (length input) input)
      (funcall swap input i (random (+ i 1))))))

    
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                 ;;
;; Pairing off things (students, say).        ;;
;;                                                                 ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    
(defun mcj/pair-off (input num)
  "Return the elements of input paired off into pairs of length
   num"
  (cond ((< (length input) (* num 2)) (list input))
        (t
         (cons (butlast input (- (length input)num)) (mcj/pair-off (nthcdr num input) num)))))
    
    
    
(defun mcj/pair-off-region (num)
  " Pair off lines in a region"
  (interactive (list
                (read-number "Members per pair (num):" 2)))
  (let ((newcontents
         (mapconcat (lambda (item-pair)
                      (mapconcat (lambda (item) item) item-pair " + "))
                    (mcj/pair-off
                     (mcj/shuffle
                      (split-string
                       (buffer-substring-no-properties (mark) (point)) "[\n]" t ))
                      num)
                    "\n")))
    (delete-region (mark) (point))
    (insert newcontents)))
```

## u/celeritasCelery [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqdnyhk) 
**Votes:** 12

Shells in emacs like `shell-mode` and `eshell` can write multi line input using `comint-accumulate`. Normally bound to `C-c SPC`.

## u/hale314 [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqg7qys) 
**Votes:** 12

I tend to have a lot of function that is defined solely to be added to a hook. Turns out I can customize `defun-declarations-alist` to define a new `hook` property in the `declare` form. Now I can specify the hook that the function is intended for right inside the function definition.

```elisp
;; Need to be done during compilation as well if your functions are getting compiled
(eval-and-compile
  (setf (alist-get 'hook defun-declarations-alist)
        (list (lambda (fun _args hook &optional depth)
                `(add-hook ',hook #',fun ,@(when depth (list depth)))))))
    
(defun ask-about-scratch-buffer ()
  "Confirm that user want to discard the content of the scratch buffer."
  (declare (hook kill-emacs-query-functions))
  (let ((scratch (get-buffer "*scratch*")))
    (or (zerop (buffer-size scratch))
        (progn (pop-to-buffer scratch)
               (y-or-n-p "Scratch buffer is not empty, discard?")))))
;; no longer needed
;; (add-hook 'kill-emacs-query-functions #'ask-about-scratch-buffer)
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/f25er5/comment/fhar795) 
**Votes:** 12

Make a `.desktop` file for emacsclient and make sure to include `-c` in the command line ([my .desktop](https://github.com/cadadr/configuration/blob/master/candy/emacsclient.desktop)). Then, you can associate Emacs with many filetypes with emacsclient, and view them in Emacs.  E.g., this is how I view PDF files. `-c` is useful because it opens in a new frame, thus your window setup is left intact and the OS window is always in the current workspace (useful if you've set up your WM to not focus urgent windows automatically).

In a similar vein, I use [this script](https://github.com/cadadr/configuration/blob/master/emacs.d/extras/mailto.el.in) to redirect `mailto:` links to Emacs so that I can write my mails in `message-mode` within Emacs. I've set it as the mail program in Firefox.  It's probably possible to write a `.desktop` file for this so that you can set it in `mimeapps.list` or whatever as the global mail composer, but IDK how to do that.

With emacsclient and a bit of Elisp, it's really easy to integrate Emacs to a FreeDesktop environment.  I can't speak for Windows or Mac OS tho, because I don't use those often.

## u/MCHerb [🔗](https://www.reddit.com/r/emacs/comments/e8nv40/comment/fadjl3w) 
**Votes:** 12

A narrowing toggle that does what I need most of the time so a single key can do all narrowing and un-narrowing.

```elisp
(defun narrow-dwim ()
  "Toggle narrowing."
  (interactive)
  (cond ((region-active-p)
         ;; If region is highlighted, narrow to that
         (call-interactively #'narrow-to-region)
         (deactivate-mark t))
        ((buffer-narrowed-p)
         ;; Otherwise widen if narrowed
         (widen))
        ((derived-mode-p 'org-mode)
         (call-interactively #'org-narrow-to-subtree))
        (t
         (message "Do not know what to narrow to.")
         (call-interactively #'narrow-to-defun))))
```

## u/bopboa [🔗](https://www.reddit.com/r/emacs/comments/1aky57w/comment/kphrvz3) 
**Votes:** 11

This is how to have a beacon without installing any packages.

```elisp
  (defun pulse-line (_)
    (pulse-momentary-highlight-one-line (point)))
  (setq window-selection-change-functions '(pulse-line))
```

## u/ayy_ess [🔗](https://www.reddit.com/r/emacs/comments/18hc301/comment/kdobd72) 
**Votes:** 11

I recently discovered `(setq read-minibuffer-restore-windows nil)` which resolves my frustration that quitting the minibuffer would discard any changes to the window layout while the minibuffer was open. For example, by default, `M-x C-h k k C-g` quits the just opened help buffer. I'm sure to have missed many more QOL improvements from NEWS.

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/108zin2/comment/j420ea0) 
**Votes:** 11

Do you want a key binding to wrap the selection in some kind of delimiter? Here's a built-in solution:

```elisp
(defvar insert-pair-map
  (let ((map (make-sparse-keymap)))
    (define-key map [t] #'insert-pair)
    map))

(global-set-key (kbd "C-S-w") insert-pair-map)

```
This setups up `C-S-w` to be a prefix map, `insert-pair-map`. The only key binding in `insert-pair-map` is for `[t]`, which means it is the default key binding and any key after the prefix will run the same command: `insert-pair`. Now, `insert-pair` looks at which key was used to invoke it and if it is an opening delimiter it inserts both it and the corresponding closing delimiter (and if the region is active it insert the opening delimiter at the start and the closing delimiter at the end, wrapping the region).

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/s21457/comment/hsgj7a6) 
**Votes:** 11

Prevent horizontal scrolling from going too far left.

I use Emacs on a laptop and quite often scroll with a touchpad. I also don't use line wrapping, as in code it usually looks misleading, so lines can sometimes exceed window width, either because of some long names or because the current window configuration is too narrow.

However, when scrolling text sideways, there's a small annoyance that the scroll can go way too far to the left. E.g. if this is your window, and your text exceeds it:


```elisp
|Short line             |
|Some really long line o|
|Another short line     |

```
What I'd like to is to prevent scrolling any further than that:

```elisp
|line                   |
|eally long line of text|
|r short line           |

```
But Emacs actually allows to scroll as far as one would want to, like here:

```elisp
|                       |
|t                      |
|                       |

```
This doesn't make sense to me, as you can't see anything at all.
Probably handy, when you write in really long lines, and you wish to have some buffer for adding more text without triggering scrolling, but I never needed that. So I wrote such predicate:

```elisp
(defun truncated-lines-p ()
  "Non-nil if any line is longer than `window-width' + `window-hscroll'.

Returns t if any line exceeds the right border of the window.
Used for stopping scroll from going beyond the longest line.
Based on `so-long-detected-long-line-p'."
  (save-excursion
    (goto-char (point-min))
    (let* ((window-width
            ;; this computes a more accurate width rather than `window-width', and respects
            ;; `text-scale-mode' font width.
            (/ (window-body-width nil t) (window-font-width)))
           (hscroll-offset
            ;; `window-hscroll' returns columns that are not affected by
            ;; `text-scale-mode'.  Because of that, we have to recompute the correct
            ;; `window-hscroll' by multiplying it with a non-scaled value and
            ;; dividing it with a scaled width value, rounding it to the upper
            ;; boundary.  Since there's no way to get unscaled value, we have to get
            ;; a width of a face that is not scaled by `text-scale-mode', such as
            ;; `window-divider' face.
            (ceiling (/ (* (window-hscroll) (window-font-width nil 'window-divider))
                        (float (window-font-width)))))
           (line-number-width
            ;; compensate line numbers width
            (if (bound-and-true-p display-line-numbers-mode)
                (- display-line-numbers-width)
              0))
           ;; subtracting 2 for extra space in case some calculations were imprecise
           (threshold (+ window-width hscroll-offset line-number-width -2)))
      (catch 'excessive
        (while (not (eobp))
          (let ((start (point)))
            (save-restriction
              (narrow-to-region start (min (+ start 1 threshold)
                                           (point-max)))
              (forward-line 1))
            (unless (or (bolp)
                        (and (eobp) (<= (- (point) start)
                                        threshold)))
              (throw 'excessive t))))))))

```
This function can calculate window width, and line width, and check if any line in the buffer exceeds the window width screen-wise. By screen-wise I mean that if you've scrolled text to the left, it will begin to return `nil` once all lines don't exceed the right border of the window, thus achieving the described behavior in the diagrams. I then define advice around the `scroll-left` function, and it works pretty good:

```elisp
(define-advice scroll-left (:around (foo &optional arg set-minimum))
  (when (and truncate-lines
             (not (memq major-mode '(vterm-mode term-mode)))
             (truncated-lines-p))
    (funcall foo arg set-minimum)))

```
Though it's not very accurate when using `text-scale-adjust`, as line width is not the same as before, the function, that reports how much the window was scrolled to the left still returns unscaled values. You can see my thoughts in the function's comments. Any suggestions on how to make it more accurate?

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/q76kok/comment/hghtyfo) 
**Votes:** 11

before you load evil `(setq evil-want-minibuffer t)` to use evil-mode in the minibuffer.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/ojzv53/comment/h55vkl6) 
**Votes:** 11

I often find myself wanting to be able to switch between `master` and a feature branch in magit quickly:

```elisp
(defun lw-magit-checkout-last (&optional start-point)
    (interactive)
    (magit-branch-checkout "-" start-point))
(transient-append-suffix 'magit-branch "w"
  '("-" "last branch" lw-magit-checkout-last))

```
So that `C-x g b -` switches to the last branch I was on, similar to `cd -`.

## u/PotentiallyAlice [🔗](https://www.reddit.com/r/emacs/comments/n9q662/comment/gxx6frj) 
**Votes:** 11

I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

```elisp
(defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

```
Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "* TODO test reminder" line into my todo.org. Neat!

## u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/lfww57/comment/gmtk79e) 
**Votes:** 11

From the mailing list, I've just learned of `generic-x.el`, which provides syntax highlighting for `/etc/fstab` or `/etc/passwd` and the like.  I appreciated that vim provided that out of the box and I was surprised that emacs also does, but it's just disabled.

`(require 'generic-x)` to enable it.

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/kvmmq3/comment/gj9ioly) 
**Votes:** 11

Just a cool concept: if you have a keypad on your keyboard which you rarely use, bind its nums to something useful. The results are numlock-sensitive and are NOT the same keycodes as regular numbers, so they're just free keys. For example, `(define-key map (kbd "<kp-1>") 'winum-select-window-1)`

## u/Krautoni [🔗](https://www.reddit.com/r/emacs/comments/ja97xs/comment/g8pgyy1) 
**Votes:** 11

Since I find myself pair programming quite a bit, I made a small helper:

```elisp
(defvar pair-programming--pair-programmer
  nil
  "The current pair programmer as (name email)")

(defun enable-pair-programming-mode ()
  "Sets visuals for pair programming mode and prompt for your buddy."
  (global-display-line-numbers-mode 1)
  (let ((pair-programmer (git-commit-read-ident nil)))
(setq pair-programming--pair-programmer pair-programmer)
(message (concat "Pair programming with " (car pair-programmer)))))

(defun disable-pair-programming-mode ()
  "Disable pair programming visuals and settings."
  (setq pair-programming--pair-programmer nil)
  (global-display-line-numbers-mode -1)
  (message "PP mode disabled"))

(define-minor-mode pair-programming-mode ()
  "Toggle Pair Programming Mode.

This prompts for a pair programmer from your current git commit history.
When you commit with (ma)git, the pair programmer is inserted as a co-author.
Additionally, line number mode is enabled."
  :global t
  :lighter " PP"
  (if pair-programming-mode
  (enable-pair-programming-mode)
(disable-pair-programming-mode)))

(defun insert-pair-programmer-as-coauthor ()
  "Insert your pair programer into the current git commit."
  (when (and pair-programming-mode git-commit-mode)
(pcase pair-programming--pair-programmer
  (`(,name ,email) (git-commit-insert-header "Co-authed-by" name email))
  (_ (error "No pair programmer found or wrong content")))))

(add-hook 'git-commit-setup-hook 'insert-pair-programmer-as-coauthor)
```

It sets up a co-authored-by for git commits, and enables line numbers.

## u/jimm [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvqvedf) 
**Votes:** 11

I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/g5bat3/comment/fo362s8) 
**Votes:** 11

`rx` isn't just an (extensible!) sexp syntax for regexps, it's an optimizing compiler!

Examples:

-   `(rx (or "dude@home.org" "dude@work.com"))` produces
```elisp
`"\\(?:dude@\\(?:home\\.org\\|work\\.com\\)\\)"`
```
-   `(rx (intersection (any (?g . ?z)) (any (?a . ?p))))` produces  `"[g-p]"`

And from Lisp code it's easy to plug in computed portions, using `eval`.
For example I recently used this in some silly code to count mentions
of animals in the bible :P:

```elisp
(defun plural (word)
  (pcase word
    ("mouse" "mice")
    ("wolf" "wolves")
    ("fish" "fishes")
    (word (concat word "s"))))
    
(defun bible-count (word)
  "Count number of times WORD or its plural appears in the bible."
  (with-current-buffer "king-james-bible.txt"
    (count-matches
      (rx-to-string `(word-start (or ,word ,(plural word)) word-end)))))

```
Which I used to make an Org mode table with an Emacs Lisp column formula:

```elisp
#+TBLFM: $2='(bible-count $1)

```
**EDIT**:  A previous version of the `bible-count` function used the `rx` macro and the `eval` construct:

```elisp
(defun bible-count (word)
  "Count number of times WORD or its plural appears in the bible."
  (with-current-buffer "king-james-bible.txt"
    (count-matches (rx word-start
                       (or (eval word) (eval (plural word)))
                       word-end))))

```
This version is incorrect: `eval` is meant to be used only for value known at compile time. It's actual [behavior is very complicated](https://www.reddit.com/r/emacs/comments/g5bat3/weekly_tipstricketc_thread/fo7qdas) and depends on plenty of seemingly extraneous circumstances.

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/fgahb2/comment/fk3in25) 
**Votes:** 11

Most people probably know that `M-t` (`transpose-words`) when used between two words swaps them. But it has other a few other features that are useful:

- You don't have to be between words to use it: if you are on a word, from the second character on, it will swap that word with the next.

- If you are at the end of the buffer and use it you get an error message, "Don’t have two things to transpose", _but_ you additionally get placed at the beginning of the last word in the buffer. So `M-t M-t` used at the end of the buffer will swap the last two words!

- You can use it to swap _non-adjacent_ words too! If you call it with a numeric argument of 0, it will swap the word at the start of the region with the next word after the end of the region. For example, say you want to swap "two" with "five" in the following line:

```elisp
  one five three four two six
    
```
  You can mark the words "five three four" (but don't mark "two") and then `M-0 M-t` will swap "five", the first word in the region, with "two" the first word _after_ the region.

That last trick works with the other `transpose-` commands as well, not just words: `transpose-chars`, `transpose-lines`, `transpose-paragraphs`, `transpose-sentences`, and `transpose-sexps`. Of course, if that `C-0` trick can't be used with any of those commands to swap the two things you want, there is always `transpose-regions`.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f5qf3jw) 
**Votes:** 11

[deleted]

## u/ProfessorSexyTime [🔗](https://www.reddit.com/r/emacs/comments/dlethf/comment/f4wdyaf) 
**Votes:** 11

I feel like a lot of us average Emacs users miss out on a lot of functionality Emacs provides outside of the box. So some things you might find interesting:

#### [Registers](https://www.gnu.org/software/emacs/manual/html_node/emacs/Registers.html)

Someone posted this in a weekly tips/trick/etc thread a few weeks ago, but I thought I'd mention it again.

Registers just let you store *things* in them. This can be

- Cursor positions in buffers (`C-x r SPC [name you want to give the register]`)
- Text in a region (`C-x r s [name you want to give the register]`)
- Rectangles (`C-x r r [name you want to give the register]`)
- State of windows in frames (`C-x r w [name you want to give the register]`)
- State of all frames and their windows (`C-x r f [name you want to give the register]`)
- Numbers (`C-x r n [name you want to give the register]`) and you can increment those numbers (`C-x r + [name of register you want to increment`]
- Whole file names (it's not bound, but you can do `M-: (set-register ?z '(file . "/some_file/somewhere/some_text.txt"))`)
- Keyboard macros instead of saving them (` C-x C-k x  [name you want to give the register]`)

You can insert text, registers, or numbers with `C-x r i [name of register with stuff to insert]`. You can also append or prepend stuff to registers with `append-to-register` or `prepend-to-register`.

`C-x r j [name of register]` can jump to a point, restore a window or frame configuration, or visit a file with a specific name.

You could always use [bookmarks](https://www.gnu.org/software/emacs/manual/html_node/emacs/Bookmarks.html#Bookmarks).

One thing I would like to do is automatically give registers names, say like a1, a2, et cetera then b1, b2 et cetera, and then A1, A2 and yaddah yaddah.

I'm an idiot though and generating that sort of collection escapes me a bit.

I'd rather not do just

```elisp
(loop n from 0 to 99
    collect (concat "a" n))

```
for every alphabetical character. Then it'd be a matter of going through those collections for every register created and making sure they use different names. Was wondering if I could get some help.

#### [Isearch](https://www.emacswiki.org/emacs/IncrementalSearch) and [Query Replace](https://www.gnu.org/software/emacs/manual/html_node/emacs/Query-Replace.html)

Not much to say here, just that I might recommend that one go over the default keys for [isearch](https://www.emacswiki.org/emacs/IncrementalSearch#toc2) and query-replace.

Also I would switch `C-s` and `C-r` to use `isearch-forward-regex` and `isearch-backward-regex`, and `M-%` to use `query-replace-regex` because you can disable the use of regex for both. You can even start a query-replace from isearch with `M-%` or `C-M-%`.

#### `event-apply-*` Keys

I don't really know what I can use these keys for, but with which-key you can press `C-x @` to see them. They allow you to apply control, shift, alt, meta, super, or hyper keys.

## u/badmaxton [🔗](https://www.reddit.com/r/emacs/comments/19ec8v5/comment/kjcu7vp) 
**Votes:** 10

Just added this to the `:init` section of my embark configuration:

```elisp
(define-key minibuffer-local-map [C-tab] 'embark-select)

```
This allows super-convenient marking of entries for later `embark-all` using control-tab, instead of having to go first through the `embark` menu. (By default, this key binding is mapped to `file-cache-minibuffer-complete`, which I never use.)

## u/JDRiverRun [🔗](https://www.reddit.com/r/emacs/comments/1933co6/comment/khe4dq6) 
**Votes:** 10

I have long had convenience bindings for `org-emphasize` like `super-i` for /italic/, that match system bindings.  But I always wanted these to *be smarter*, i.e. do something useful when there is no text selected.  Something like intelligently toggling emphasis depending on whether you were already in the right kind of emphasized text, or just emphasize the word at point if not.

[Check out my solution](https://gist.github.com/jdtsmith/55e6a660dd4c0779a600ac81bf9bfc23) (scroll down to see how it acts).  Will miss this behavior in other apps!

## u/algor512 [🔗](https://www.reddit.com/r/emacs/comments/18xebux/comment/kg4ni5d) 
**Votes:** 10

Recently I discovered that `C-h C-q` (or `M-x help-quick`) opens a small window showing \*Quick Help\* buffer with a nice overview of some basic keybindings. It seems that the content of this buffer is configurable via the variable `help-quick-sections`.

I intend to use it as a cheatsheet, reminding me about rare keybindings I always forget; I believe it is easy to make it context-dependent, just by changing the value of `help-quick-sections`.

## u/camel_case_t [🔗](https://www.reddit.com/r/emacs/comments/18xebux/comment/kgce54q) 
**Votes:** 10

This is maybe more a macOS tip than an Emacs tip, but it always bothered me that `C-f`, `C-b`, etc worked in any text box, but not `M-f`, etc -- turns out that you can easily change that throughout the OS!

I created this file and now Emacs keybindings work everywhere:

```elisp
/* ~/Library/KeyBindings/DefaultKeyBinding.dict */
{
    /* Additional Emacs bindings */
    "~f" = "moveWordForward:";
    "~b" = "moveWordBackward:";
    "~<" = "moveToBeginningOfDocument:";
    "~>" = "moveToEndOfDocument:";
    "~v" = "pageUp:";
    "~d" = "deleteWordForward:";
    "~^h" = "deleteWordBackward:";
    "~\010" = "deleteWordBackward:";  /* Option-backspace */
    "~\177" = "deleteWordBackward:";  /* Option-delete */
}

```
You can read more here: https://developer.apple.com/library/archive/documentation/Cocoa/Conceptual/EventOverview/TextDefaultsBindings/TextDefaultsBindings.html

## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/14l3jn8/comment/jpwn2ts) 
**Votes:** 10

I was converting some old `.odt` notes files to `.org` today, and one of the things I wanted to do was to add two spaces after end of sentence periods for proper sentence navigation.  So there I was figuring out a general enough regexp for the nth time and, of course, I regretted not having taken note of this the last time. So I decided to do some searching for a good regexp and write it down this time, since this was obviously shared by someone somewhere. And it turns out Emacs has us covered, and I never knew: `repunctuate-sentences`. I have no idea if this is new or has always been there. It is new to me. It uses `query-replace-regexp`, so it's the same experience. And also can be configured for exclusions with `repunctuate-sentences-filter`. Neat!

## u/BunnyLushington [🔗](https://www.reddit.com/r/emacs/comments/12zaqju/comment/jhrzybp) 
**Votes:** 10

I found myself debugging [JWTs](https://jwt.io) earlier this week and whomped up a little function to decode them from a region into a help buffer.

```elisp
(defun ii/decode-jwt (start end &optional jwt)
  "Decode JWT in region and print to help buffer."
  (interactive "r")
  (let* ((tok (if jwt jwt
            (buffer-substring start end)))
     (data (s-split "\\." tok))
     (header (car data))
     (claims (cadr data)))
(with-temp-buffer
  (insert (format "%s\n\n%s"
                  (base64-decode-string header t)
                  (base64-decode-string claims t)))
  (json-pretty-print-buffer)
  (with-output-to-temp-buffer "*JWT*"
    (princ (buffer-string)))))
  t)
```

I'd forgotten about `with-output-to-temp-buffer` which is pretty handy.  The `t` at the end is there just to suppress an overly large echo area message.

(This should be obvious but note that the JWT is not validated or verified.  This is intended for debugging only and the JWT should not be trusted.)

## u/noi-gai [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8mo1bz) 
**Votes:** 10

Put the control keys next to space, mimicking mac's command key (which is effectively used as the equivalent of ctrl yet next to the space it's easier to press)

Win - Alt - Ctrl - Space - Ctrl - Alt - Win

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikrx30z) 
**Votes:** 10

I've parsed and prettified some of the comments (I think I'm missing some, but hopefully should be fixed soonish) from past weekly tips and tricks thread here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

If you fancy procrastinating for a bit today...

## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/w3gx6o/comment/ih6ievs) 
**Votes:** 10

Emacs has better long-lines support now??

I just noticed while looking at the latest additions in the NEWS:

>\*\* Emacs is now capable of editing files with arbitrarily long lines. The display of long lines has been optimized, and Emacs no longer chokes when a buffer on display contains long lines.  If you still experience slowdowns while editing files with long lines, this is either due to font locking, which you can turn off with M-x font-lock-mode or C-u C-x x f, or to the current major mode or one of the enabled minor modes, in which case you should open the the file with M-x find-file-literally instead of C-x C-f.  The variable 'long-line-threshold' controls whether and when these display optimizations are used.

That sounds like great news!  Does anyone know what went into it?

(edit to add: this was added some time this week.  I rebuild from master weekly, and check out the NEWS diff each time)

## u/ainstr [🔗](https://www.reddit.com/r/emacs/comments/vcpk6u/comment/ichiccu) 
**Votes:** 10

The other day I discovered that you can access Spotify through dbus. Most of my use-case for spotify is hitting shuffle on ~20 of my playlists; not much searching, discovering, charts, etc. So, I didn't need any of the existing packages that require an auth token or extra local server.

This basically wraps `completing-read` over the alist stored in spotify-playlists. You can probably translate the qdbus call to dbus-send or whatever.

```elisp
;; Inspired by sp.sh: https://gist.github.com/wandernauta/6800547
;; Could use https://codeberg.org/jao/espotify, but don't need all the functionalities
;; Potential Issues: https://community.spotify.com/t5/Desktop-Linux/DBus-OpenUri-issue/td-p/1376397
    
;; Could just write a fn to extract the ID, and use that in spotify-playlists
;; Current way with full uri allows for playlist vs artist, etc.
;; but probably don't need flexiblity for my use case
(defun spotify--clean-uri (raw-uri)
  "Clean RAW-URI into a dbus-acceptable uri."
  (let* ((url-fields (split-string
		      raw-uri
		      (rx (or "/" "?"))))
	 (type (nth 3 url-fields))
	 (id (nth 4 url-fields)))
    (concat "spotify:" type ":" id)))
    
(defvar spotify-playlists
  '(("Artist" . "https://open.spotify.com/playlist/1v4UqI9mEEB4ry3a3uaorO?si=bc675402c7384080"))
  "Alist of spotify playlists for spotify-playlists to select from.
  RAW-URI is from right-click on playlist > Share > Copy Link to Playlist.")
    
(defun spotify--open-uri (raw-uri)
  "Open RAW-URI."
  (let ((prefix "qdbus org.mpris.MediaPlayer2.spotify /org/mpris/MediaPlayer2 org.mpris.MediaPlayer2.Player.OpenUri ")
	(uri (spotify--clean-uri raw-uri)))
    (shell-command (concat prefix uri))))
    
(defun spotify--open-playlist ()
  "Prompt to select and play a playlist from spotify-playlists."
  (let* ((key (completing-read "Playlist: " spotify-playlists))
	 (raw-uri (cdr (assoc key spotify-playlists))))
    (spotify--open-uri raw-uri)
    (message (format "Now Playing: %s" key))))
    
(defun spotify-open-playlist ()
  "Wrapper around `spotify--open-playlist`, to check if spotify is running."
  (interactive)
  (pcase
      (shell-command "pgrep spotify")
    (1 (message "Spotify not running."))
    (0 (spotify--open-playlist))))
```

## u/char1zard4 [🔗](https://www.reddit.com/r/emacs/comments/v2by7z/comment/iarzi1s) 
**Votes:** 10

This week I learned that:

-	You can redefine all yes/no prompts to y/n:
`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-	`C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-	Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:
https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/us7zae/comment/i92mn8w) 
**Votes:** 10

[deleted]

## u/diamondnbond [🔗](https://www.reddit.com/r/emacs/comments/us7zae/comment/i928gaj) 
**Votes:** 10

[I Recently discovered engine-mode.](https://github.com/DiamondBond/emacs/blob/master/config.org#initialize-engine-mode)

## u/shitterwithaclitter [🔗](https://www.reddit.com/r/emacs/comments/s7lac1/comment/htnz373) 
**Votes:** 10

I recently had the idea to start emacs in org-mode but have a src block at the top so I can still write elisp snippets easily. Here's the code for anyone interested:

```elisp
;; start in org-mode with a source block for lisp evaluation
(setq initial-major-mode #'org-mode
      initial-scratch-message "#+begin_src emacs-lisp\n;; This block is for text that is not saved, and for Lisp evaluation.\n;; To create a file, visit it with \\[find-file] and enter text in its buffer.\n\n#+end_src\n\n")
```

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/r69w7i/comment/hmst3ih) 
**Votes:** 10

macros in emacs are like a secret, forgotten art, but I use them with regexp search, orgmode commands to tweak repeating events (or any number of other uses). Learn macros; they gave emacs its name! One usage here: https://orys.us/ug

## u/SamTheComputerSlayer [🔗](https://www.reddit.com/r/emacs/comments/qbvyza/comment/hhinrm4) 
**Votes:** 10

I use a lot of toggles in my config. I used to do it ad-hoc every time, but the pattern ends up the same so I made this macro:
```elisp
(defun deftoggle-var-doc (name)
  (concat "Non-nil if " name " is enabled.\n\n"
      "See " name
      " command for a description of this toggle."))
(defun deftoggle-fun-doc (name doc)
  (concat "Toggle " name " on or off.\n\n" doc))
(defmacro deftoggle (name doc enabler disabler)
  `(progn
 (defvar ,name nil ,(deftoggle-var-doc (symbol-name name)))
 (defun ,name (&optional enable)
   ,(deftoggle-fun-doc (symbol-name name) doc)
   (interactive)
   (if (called-interactively-p 'interactive)
       (progn
         (if ,name
             ,disabler
           ,enabler)
         (setq ,name (not ,name)))
     (progn
       (if enable
           ,enabler
         ,disabler)
       (setq ,name enable))))))
```

It's very similar to define-minor-mode, but with all the hooks, keymaps, and lighters stripped out, so it's less verbose. Here I use it to toggle my theme for example:
```elisp
(deftoggle sam-toggle-theme
  "Toggle theme between light and dark."
  (progn (disable-theme 'dracula)
     (load-theme 'spacemacs-light t))
  (progn (disable-theme 'spacemacs-light)
     (load-theme 'dracula t)))
```

## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/q2g1gq/comment/hfldw8n) 
**Votes:** 10

One of the most useful bindings for me:

```elisp
(use-package avy
  :bind* (("C-j" . avy-goto-char-timer)))
```

## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hexdfiq) 
**Votes:** 10

Replace the binding for `count-words-region` with `count-words`. The latter has better semantics: it only shows words in region if the region is active.

`(global-set-key (kbd "M-=") #'count-words)`

## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/polxft/comment/hcxub77) 
**Votes:** 10

If you write Lisp, there's a couple of old Emacs commands to help you write code while keeping parenthesis balanced: `M-(` (`insert-parenthesis`), and `M-)` (`move-past-close-and-reindent`). They used to be documented in old Emacs manuals, but presumably their description was removed to make room for other content.

With a prefix argument, `M-(` wraps in parenthesis that number of sexps. For example with point represented as "*":

*foo -> C-u 1 M-( -> (foo)

There's more information in EmacsWiki: https://www.emacswiki.org/emacs/InsertPair

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/o0zvb5/comment/h1znz1s) 
**Votes:** 10

I keep forgetting how useful the `pcase` macro is.  Recently I wrote a command that expected to find a single jar file in a certain directory and operate on it.  I started with a more "traditional" implementation:

```elisp
(let ((jars (directory-files some-dir t (rx ".jar" eos))))
  (if (= 1 (length jars))
      (do-something-with (car jars))
    (error "Didn't find exactly one jar file")))

```
Then I remembered `pcase`:

```elisp
(pcase (directory-files some-dir t (rx ".jar" eos))
  (`(,jar) (do-something-with jar))
  (_ (error "Didn't find exactly one jar file")))

```
Much more readable!

## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/ml4wql/comment/gtkc524) 
**Votes:** 10

Skeletons are one of Emacs' killer features, especially when combined with `abbrev-mode`. Here's a macro I wrote to make them a little easier to handle:

```elisp
(defmacro snip (name &rest skeleton)
  (let* ((snip-name (symbol-name `,name))
         (func-name (intern (concat "snip-" snip-name))))
    `(progn
       (define-skeleton ,func-name
         ,(concat snip-name " skeleton")
         ,@skeleton)
       (define-abbrev global-abbrev-table ,snip-name
         "" ',func-name))))

```
Here's a simplistic example using the macro:

```elisp
(snip dd "" (format-time-string "%Y-%m-%d"))

```
Now (assuming you have `abbrev-mode` enabled), type `dd ` into your buffer (that's `d` `d` `SPC`) and it'll be replaced with the current date.

This is just scratching the surface, skeletons are extremely powerful. Once you start using them they become a superpower and can take your Emacs usage to the next level.

## u/jumpUpHigh [🔗](https://www.reddit.com/r/emacs/comments/kvmmq3/comment/gj33uht) 
**Votes:** 10

AucTeX

When you compile your TeX file and there are errors, the message asks you to see error messages using

```elisp
 C-c `

```
This leads to the last error which is sometimes incomprehensible and you are left to yourself to figure out what went wrong.

Instead of getting the last error, you can get an overview of all the errors by setting below variable to `t`:

```elisp
 (setq TeX-error-overview-open-after-TeX-run t)

```
You can pop this up in a separate frame using:

```elisp
  (setq TeX-error-overview-setup 'separate-frame)

```
Related docs are [here](https://www.gnu.org/software/auctex/manual/auctex/Error-overview.html).

This totally changes the way you can handle errors messages.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/ikgfxd/comment/g3zeprg) 
**Votes:** 10

**Suggestion for moderators** - Consider putting a note in the weekly announcement for this thread that using 3 backquotes or tildes to make code blocks doesn't work for those of us using old reddit (so the code people post that way is almost unreadable) - and that indenting by 4 spaces is better for compatibility.
(Am I the only one who still uses old reddit? :-)  )

## u/kastauyra [🔗](https://www.reddit.com/r/emacs/comments/ibwzcu/comment/g1zlh2t) 
**Votes:** 10

# 27.1 do GC if no frame has focus

I am porting my [config](https://github.com/laurynas-biveinis/dotfiles) from 26.3 to 27.1, which had the tweak to do GC whenever a frame loses focus, originally from [MatthewZMD's config](https://github.com/MatthewZMD/.emacs.d) I think:
```elisp
(add-hook 'focus-out-hook #'garbage-collect)
```elisp
27.1 NEWS say more generic (and more correct) `after-focus-change-function` should be used instead. Which pointed out that I do not want to GC on just any frame going out of focus, if another frame is being focused instead. It might be a better idea to GC if no frames at all are focused. Somewhat surprisingly I was not able to find any public dotfiles repo implementing this to copy paste from, so I tried to write my own:
```elisp
(defun dotfiles--gc-on-last-frame-out-of-focus ()
  "GC if all frames are inactive."
  (if (seq-every-p #'null (mapcar #'frame-focus-state (frame-list)))
  (garbage-collect)))

(add-function :after after-focus-change-function
          #'dotfiles--gc-on-last-frame-out-of-focus)
```

## u/Rotatop [🔗](https://www.reddit.com/r/emacs/comments/hij4ga/comment/fwi4ikt) 
**Votes:** 10

I've made it !

After 6 month of emacs, I m able to open a side buffer when I m on ivy without using C-c C-O or hydra or alt-enter but directly with shift + arrow (except for Up because I need to go on buffer)


```elisp
;; Auto create new window
(setq windmove-create-window t)
;; thanks to https://people.gnome.org/~federico/blog/bringing-my-emacs-from-the-past.html
;; Let me switch windows with shift-arrows instead of "C-x o" all the time
(windmove-default-keybindings)
    
;; Ugly hack :
;; What I want is to Shift arrow, then it open the selection on a new splitted window (up left right, down)
(defun tim/ivy-down-other ()
  (interactive)
  (ivy-exit-with-action #'tim/ivy-down-exit))
    
(defun tim/ivy-left-other ()
  (interactive)
  (ivy-exit-with-action #'tim/ivy-left-exit))
    
(defun tim/ivy-right-other ()
  (interactive)
  (ivy-exit-with-action #'tim/ivy-right-exit))
    
(defun tim/ivy-down-exit (ivy-body)
  (split-window-below)
  (other-window 1)
  (tim/reuse-open-goto-line ivy-body))
    
(defun tim/ivy-left-exit (ivy-body)
  (split-window-right)
  (tim/reuse-open-goto-line ivy-body))
    
(defun tim/ivy-right-exit (ivy-body)
  (split-window-right)
  (other-window 1)
  (tim/reuse-open-goto-line ivy-body))
    
    
;; Thanks to
;; https://github.com/abo-abo/swiper/blob/master/doc/ivy.org#actions and
;; https://www.reddit.com/r/emacs/comments/efg362/ivy_open_selection_vertically_or_horizontally/
(defun tim/reuse-open-goto-line (ivy-body)
  (message "reuse-open-goto-line ivy-body: %s" ivy-body)
  (let* ((tim/list (split-string ivy-body ":"))
         (file (car tim/list))
         (tim/number (car (cdr tim/list))))
    
    (condition-case err
        (counsel-projectile-find-file-action file)
      (void-function ; <- that s the error handler name
       (message "open fail with projectile, try find-file. Error was: %s" err)
       (find-file file)))
    ;; Thanks to https://stackoverflow.com/questions/3139970/open-a-file-at-line-with-filenameline-syntax
    (when tim/number
      ;; goto-line is for interactive use
      (goto-char (point-min))
      (forward-line (1- (string-to-number tim/number))))))
  ;; (ivy-resume)) ; It s strange but ivy-resume here change the way that 'ENTER' or ivy-done works afterwards
  ;; Try, as a workaround , in a timer ; no luck
  ;; (run-with-timer 0.1 nil 'ivy-resume))
    
(use-package! ivy
  :bind (:map ivy-minibuffer-map
         ("C-p" . ivy-previous-history-element)
         ("<S-down>" . tim/ivy-down-other)
         ;; no up to avoid changing buffer problems
         ("<S-left>" . tim/ivy-left-other)
         ("<S-right>" . tim/ivy-right-other))

```

Emacs is good

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvqq7ck) 
**Votes:** 10

Undo-tree and kill-ring are two of the best features in Emacs / packages. Change your life today.

## u/rhmatthijs [🔗](https://www.reddit.com/r/emacs/comments/h9zoy9/comment/fuzucay) 
**Votes:** 10

On a Mac: make Emacs detect if you have light or dark mode enabled system wide.

If you have two themes, a light one and a dark one, and you want the dark theme by default unless you have light mode enabled, add this to your init.el:

```elisp
;; If we're on a Mac and the file "~/bin/get_dark.osascript" exists
;; and it outputs "false", activate light mode. Otherwise activate
;; dark mode.
(cond ((and (file-exists-p "~/bin/get_dark.osascript")
            (string> (shell-command-to-string "command -v osascript") "")
            (equal "false\n"
                   (shell-command-to-string "osascript ~/bin/get_dark.osascript")))
       (mcj/theme-set-light))
      (t (mcj/theme-set-dark)))

```
(mcj/theme-set-light and mcj/theme-set-light are functions that enable the light and the dark theme, respectively).

~/bin/get_dark.osascript contains the following:

```elisp
tell application "System Events"
	tell appearance preferences
		get dark mode
	end tell
end tell
```

## u/primitiveinds [🔗](https://www.reddit.com/r/emacs/comments/erro41/comment/ff90d5b) 
**Votes:** 10

I just now figured out that you can interactively pass flags to commands like `counsel-rg` by putting the `--` separator between the flags and the search string, so something like `-g '*.txt' -- whatever` will search for `whatever` only in `txt` files. `counsel` uses a function called `counsel--split-command-args` to split the parts before and after the `--`.

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/eeyhdz/comment/fbxf389) 
**Votes:** 10

Update your hosts file to block internet ads with this function:

```elisp
(defun update-hosts-file ()
  (interactive)
  ;; make a backup in case something goes wrong
  (copy-file "/etc/hosts" "/tmp/" t)
  (let ((coding-system-for-write 'raw-text-unix))
    (with-temp-file  "/sudo:root@localhost:/etc/hosts"
      (insert-file-contents "/etc/hosts")
      (goto-char (point-min))
      ;; This "if block" allows you to keep your custom entries untouched
      (if (re-search-forward "^# This hosts file is" nil t)
          (progn
            (goto-char (line-beginning-position))
            (delete-region (point)(point-max)))
        (goto-char (point-max)))
      ;; get the updated list
      (insert
       (with-current-buffer
           (url-retrieve-synchronously "https://someonewhocares.org/hosts/hosts" t t)
         (goto-char (point-min))
         (re-search-forward "^$")
         (delete-region (point) (point-min))
         (buffer-string))))))
```

## u/primitiveinds [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f5oz3bp) 
**Votes:** 10

This is one of the things that I'm sure exists somewhere but I can't find it so I wrote a few lines. I work in a big monorepo and depend on `projectile` for navigation by adding `.projectile` files here and there, in subdirectories that I consider "projects". However there are some directories that e.g. contain libraries. If I want to work on all of them for e.g. refactoring, going into a project then locks me in that (I mean `projectile-find-file`) and I have to manually go into another project. Also I flood my projectile cache with little things that I might not use a lot. What I did was add some logic to create temporary root directories for projects, where I can then use `counsel-file-jump` and `counsel-ag`. I have some keybindings and with a prefix argument I am prompted to change the temp root. Here's the code:
```elisp
(defvar my/temp-project-root nil)

(defun my/get-or-set-temp-root (reset)
  (let* ((reset-root (if reset my/temp-project-root nil))
     (root
      (if (or reset
              (null my/temp-project-root)
              (not (file-directory-p my/temp-project-root)))
          (read-directory-name "Temp root dir: " reset-root)
        my/temp-project-root)))
(setq my/temp-project-root root)))

(defun my/counsel-file-jump-temp-root (reset)
  (interactive "P")
  (my/get-or-set-temp-root reset)
  (let ((current-prefix-arg nil))
(counsel-file-jump nil my/temp-project-root)))

(defun my/counsel-ag-temp-root (reset)
  (interactive "P")
  (my/get-or-set-temp-root reset)
  (let ((current-prefix-arg nil))
(counsel-ag "" my/temp-project-root)))
```
Also `counsel-file-jump` is so good

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/1aky57w/comment/kpct4cp) 
**Votes:** 9

Many of you probably know of this, but I found "indirect buffers" useful.

When I'm in Vim, I've found it useful to sometimes split a buffer into two windows, and use code folding to view different parts of the same file in the two windows. But this doesn't work in Emacs, because the "folding" and "narrow" states of the buffer are synced between the windows in contrast to Vim. One concrete use case I had: I have a huge Org file, and wanted to narrow `C-x n s` into different headings of the file in different windows.

Indirect buffers solve this. It makes two buffers for one file, and these buffers have separate settings for folding, narrowing, etc. But the buffer contents are still synced, so there's no risk of diverging file states. With default keybindings, I found that `C-x 4 c C-x n s` did what I wanted.

## u/lesliesrussell [🔗](https://www.reddit.com/r/emacs/comments/198rnkj/comment/kibmgv2) 
**Votes:** 9

[transient map for movement](https://gist.github.com/lesliesrussell/46302d413fcf49e9717eeea57fdadcbf)

Defines a transient keymap for movement controls and sets up a global key binding to activate this transient map. This transient map, \`my-movement-transient-map\`, includes bindings for various movement commands like moving forward or backward by a word or character and moving to the next or previous line. The \`activate-my-movement-map\` function is defined to activate this transient map, and it is globally bound to \`C-f\`.

&#x200B;

This setup allows you to press \`C-f\` followed by one of the specified keys (\`f\`, \`b\`, \`c\`, \`l\`, \`n\`, \`p\`) to perform the corresponding movement operation. The \`set-transient-map\` call with a second argument of \`t\` ensures that the transient map stays active until one of its keys is pressed.

&#x200B;

This is a neat way to create a custom, modal-like interface for movement within Emacs, leveraging your Emacs Lisp skills to tailor your editing environment to your preferences. If you have any specific modifications or additional features you'd like to implement, feel free to ask!

I didn't want to drop code in the thread so i put it in a gist

## u/leothrix [🔗](https://www.reddit.com/r/emacs/comments/17qh1hn/comment/k8dlt4c) 
**Votes:** 9

Need to remove an element from a list when you're tinkering with elisp?

Sometimes when I'm adding and removing elements from hooks or variables like `completion-at-point-functions` I'll often need to tinker with the symbols I've added. You could evaluate some form somewhere, but I like to be lazy and just:

```elisp
M-x remove-hook

```
And you've got an interactive interface (using `completing-read`) for removing arbitrary elements from any list-like variable. It's _technically_ for altering hooks, but you can abuse it to fool around with lists, too.

## u/Netherus [🔗](https://www.reddit.com/r/emacs/comments/17qh1hn/comment/k8c4mz7) 
**Votes:** 9

Just recently found out M-u makes the next word upper case, and the same for M-l for lower case. Maybe nothing fancy, but it's kinda handy for me.

## u/frosch03 [🔗](https://www.reddit.com/r/emacs/comments/15yxdz3/comment/jxekm3a) 
**Votes:** 9

Very useful, but I keep forgetting it:

If you have two buffers open in one frame, where one contains just a few lines and otherwise just uses up a lot of space, you can shrink that buffer down just right by using: `C-x -`

And if you want to balance these two buffers again just use `C-x +`

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/15sjm3k/comment/jwff8bw) 
**Votes:** 9

I've been slowly accumulating cases for "flexing" the thing at point as a more general `capitalize-word` replacement:

```elisp
;; Stolen from the wiki somewhere
(defun increment-number-at-point ()
  "Increment the number at point."
  (interactive)
  (skip-chars-backward "0-9")
  (or (looking-at "[0-9]+")
      (error "No number at point"))
  (replace-match (number-to-string (1+ (string-to-number (match-string 0))))))
    
(defun lw-flex ()
  "Perform smart flexing at point.
    
E.g. capitalize or decapitalize the next word, increment number at point."
  (interactive)
  (let ((case-fold-search nil))
    (call-interactively
     (cond ((looking-at "[0-9]+") #'increment-number-at-point)
           ((looking-at "[[:lower:]]") #'capitalize-word)
           ((looking-at "==") (delete-char 1) (insert "!") (forward-char 2))
           ((looking-at "!=") (delete-char 1) (insert "=") (forward-char 2))
           ((looking-at "+") (delete-char 1) (insert "-") (forward-char 1))
           ((looking-at "-") (delete-char 1) (insert "+") (forward-char 1))
           ((looking-at "<=") (delete-char 2) (insert ">=") (forward-char 2))
           ((looking-at ">=") (delete-char 2) (insert "<=") (forward-char 2))
           ((looking-at "<") (delete-char 1) (insert ">") (forward-char 1))
           ((looking-at ">") (delete-char 1) (insert "<") (forward-char 1))
           (t #'downcase-word)))))

```
I bind it to `M-c`.

## u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/11rq2gl/comment/jca66k0) 
**Votes:** 9

I'm once again reminded of the utility of `read-key` for small functions where one wants a nicer interface for choosing an alternative than universal arguments; taking an optional prompt, it simply reads a key from the keyboard and returns it.

For example, I recently wanted a function that prints a set of predefined dates for me into the current buffer, and it was as easy as

```elisp
(defun slot/insert-time ()
  (interactive)
  (let* ((formats '((?i "ISO 8601"  "%Y-%m-%d")
                    (?l "DDmmmYYYY" "%d%b%Y")
                    (?t "Time"      "%H:%M")))
         (key (read-key
               (cl-loop for (key label _) in formats
                        concat (format "[%s] %s "
                                       (propertize (single-key-description key) 'face 'bold)
                                       label)))))
    (->> (alist-get key formats)
         cl-second
         format-time-string
         downcase                     ; Jan -> jan
         insert)))
```

## u/AnugNef4 [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/ja41lso) 
**Votes:** 9

How I got my Info Directory node `C-h i` to display all my installed .info files living under `/opt/homebrew` on an M1 Mac. I run emacs-plus@29 from [d12frosted on github](https://github.com/d12frosted/homebrew-emacs-plus).

init.el snippet

```elisp
(require 'info)
(info-initialize)
(push "/opt/homebrew/share/info" Info-directory-list)

```
run this shell script to update the Directory node.

```elisp
#!/usr/bin/env bash
    
INFO_DIR="/opt/homebrew/share/info"
while read -r f; do
    install-info --debug --keep-old "$f" "$INFO_DIR"/dir
done <<< $(find /opt/homebrew/Cellar -name \*.info)
```

## u/luiggi_oasis [🔗](https://www.reddit.com/r/emacs/comments/zred55/comment/j14grej) 
**Votes:** 9

What's the deal with all these completion framework I keep hearing about? Vertical elm ivy company and whatnot.

I think I have company in my init.el but I'm not even sure I'm actually using it (maybe I am and I'm just unaware). But why are they everywhere? I see them mentioned in at least every any two emacs threads.

## u/andyjda [🔗](https://www.reddit.com/r/emacs/comments/yqciht/comment/iw00xhx) 
**Votes:** 9

I started using `god-mode`, but I found it hard to get used to it at first: there was no easy way to check what command would be triggered by what key-sequence. 

I wrote up a `god-mode`\-specific `describe-key`, which translates `god-mode` key-sequences into commands and shows their usual description. I think it's a great way to get familiar with how the package handles keys, and it allows users to invoke `describe-key` without leaving god-mode (previously, most keys would just show information about the generic `god-mode-self-insert-command`)

I also reached out to the package's maintainers, and this feature (after some tweaking) [just got added to the master branch](https://github.com/emacsorphanage/god-mode). It was a great way to get familiar with `god-mode` code and its behavior, and I'm happy to have made my first contribution to an Emacs package.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/iodmtzu) 
**Votes:** 9

I find it pretty useful (for debugging etc) to override the default projectile mode line indicator and show the projectile project type of the buffer instead, which can be done pretty easily if you're a use-package user with https://elpa.gnu.org/packages/delight.html:

```elisp
(use-package projectile
  :delight '(:eval (format " P[%s]" (projectile-project-type)))
  :config
  (setq foo "bar"))
```

## u/kickingvegas1 [🔗](https://www.reddit.com/r/emacs/comments/x7zfs2/comment/innk62a) 
**Votes:** 9

TIL when working with an Org table that `S-RET` will fill the current cell value with the value above it. <https://lists.gnu.org/archive/html/emacs-orgmode/2010-03/msg00462.html>

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/x7zfs2/comment/inqralq) 
**Votes:** 9

I use follow-mode (built-in to #emacs) to split reading buffers across modern wide screens to use all the real estate. The mode keeps the panes in sync with eachother. http://images.toryanderson.com/follow-mode.gif

## u/jimm [🔗](https://www.reddit.com/r/emacs/comments/tfcmcx/comment/i0vtxte) 
**Votes:** 9

The `git grep` git command is great for finding a regex (or a simple string) everywhere in a git repo. I define the following functions and bind the `git-grep` function to `F2`. It will prompt for a regex and search for that in the repo or, if you give it a numeric prefix like `C-u` it will read the current symbol at point (the word under the cursor) and search for that. Results appear in a grep buffer, so you can use `C-g C-n` and `C-g C-p` to navigate from one result to the next.

```elisp
(defun git-root-dir ()
  "Returns the current directory's root Git repo directory, or
NIL if the current directory is not in a Git repo."
  (let ((dir (locate-dominating-file default-directory ".git")))
    (when dir
      (file-name-directory dir))))
    
(defun git-grep (arg)
  "Runs 'git grep', starting the search in the current
directory's root git repo directory.
    
By default, reads the regex from the minibuffer. With a prefix
argument, initializes the search string with the current symbol
at point."
  (interactive "P")
  (let* ((symbol-at-point (thing-at-point 'symbol))
         (regexp (if (and arg (symbol-at-point))
                   (regexp-quote symbol-at-point)
                     (read-from-minibuffer
                      "Search regexp: " nil nil nil 'grep-find-history)))
    
         (default-directory (git-root-dir))
         (case-ignore-flag (and (isearch-no-upper-case-p regexp t) "-i"))
         (cmd (concat "git grep --extended-regexp --line-number --full-name"
                      " --untracked " case-ignore-flag " -- \"" regexp "\""
                      " | cut -c -240")))
    (while (equal "" regexp)
        (setq regexp (read-from-minibuffer
                      "Search regexp (must not be the empty string): " nil nil nil 'grep-find-history)))
    (grep-find cmd)))
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/rgu8dp/comment/hoqrg9e) 
**Votes:** 9

`bs-show` is an interesting command, it shows a pop-up-like buffer that you can use to quickly act on open buffers. There are a ton of customizations you can make and a bunch of convenient bindings. I've been trying it out instead of `list-buffers` and `ibuffer` and I like it so far, very fast.

## u/blankspruce [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnrdt9x) 
**Votes:** 9

Is there a package similar to wdired or wgrep that would work on magit diffs? 

Particular use cases I have in mind are:

1. You've prepared a commit for pull request and during review someone spotted a mistake that's present in multiple files of that commit. Usually I grep the mistake and edit only affected files with wgrep (there might be some arbitrary reason to not fix similar issue in files not present in the commit).
2. In C++ it happens sometimes that you want to separate declaration and definition and in your commit you forgot to move some definitions to .cpp.
Usually I switch to `foobar.hpp`, kill the necessary part, switch to `foobar.cpp`, yank that part.

## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/qgrpte/comment/hicheof) 
**Votes:** 9

A colleague just showed me Intellij's "[compare with clipboard](https://www.jetbrains.com/help/idea/comparing-files-and-folders.html#clipboard)" feature: it's fairly neat, you select a region, invoke compare-with-clipboard and get a diff of the two selections.

It didn't take me long to implement something similar:

```elisp
(defun ediff-compare-region-clipboard (begin end)
  (interactive "r")
  (save-excursion
    (let ((selected-region (buffer-substring begin end))
          (clipboard-buffer (get-buffer-create "*ediff-clipboard*"))
          (region-buffer (get-buffer-create "*ediff-region*")))
      (with-current-buffer clipboard-buffer
        (insert (car kill-ring)))
      (with-current-buffer region-buffer
        (insert selected-region))
      (ediff-buffers clipboard-buffer region-buffer))))

```
It's not ideal though.  In particular, is there a better way to insert the "clipboard"?  One thing I quickly found was that you might copy the region to compare but then so many editing commands will add to the kill-ring, so I might want to make that part of the process interactive.

## u/yogsototh [🔗](https://www.reddit.com/r/emacs/comments/qgrpte/comment/hi8crmc) 
**Votes:** 9

I just made this nice combination of emacs packages and personal theme to achieve the cool effect of iAWriter

See here: https://her.esy.fun/posts/0021-ia-writer-clone-within-doom-emacs/index.html

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/q76kok/comment/hghp1e4) 
**Votes:** 9

Checkout [Topsy Mode](https://github.com/alphapapa/topsy.el), it creates a header at the top of your buffer to show the name of the first function outside of your visual range. It makes scrolling through code much easier because you get an additional visual queue of your location in the buffer. It's one of those things that you never knew you wanted. It takes about 15 seconds to setup.

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/pb6w2z/comment/haddtq6) 
**Votes:** 9

This one is probably pretty niche but it was a noticeable improvement in my workflow, so I thought I'd share. I sometimes have to fill out forms I get in PDF but that aren't fillable PDFs, merely scans of paper forms. I guess a reasonable person would install The Gimp or something like that, but I fill them out in LaTeX, using `\includegraphics` for the PDF form, and the `textpos` package to place text on top of it. 

This is requires giving the coordinates you want the text placed at, which I used to do by trial and error. And as much as I normally dislike the mouse, I had to admit that for specifying a point in an image it is a much better input device than the keyboard. So I decided to write a function that would let me click on a point in a PDF and insert the coordinates in centimeters at point in the current buffer. Writing it was fairly easy and I think really illustrates the power of a system like Emacs, a Lisp Machine or Smalltalk, where you can instantly find the source code implementing any given functionality. I knew that `pdf-tools` must contain some code to translate mouse clicks into PDF document coordinates, because it lets you place annotations by clicking. I have the text annotation function bound to `a t`, and quick `C-h k` later I was reading the source code. And after that writing the following function was pretty easy:

```elisp
(defun insert-coordinates ()
  "Insert coordinates (in centimeters) of mouse click."
  (interactive)
  (let ((pos (event-start (pdf-util-read-click-event "Click on PDF"))))
    (insert
     (with-selected-window (posn-window pos)
       (let ((pt (pdf-util-scale-pixel-to-points (posn-object-x-y pos))))
         (cl-flet ((f (x) (* 2.54 (/ x 72.0))))
           (format "(%.1fcm,%.1fcm)" (f (car pt)) (f (cdr pt)))))))))
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/mpwapo/comment/gudoljm) 
**Votes:** 9

**Create Rectangular Selection with Meta+Click+Drag**

By default, when you click and drag with the Meta key Emacs creates what it calls a "secondary selection" which is super interesting and useful, but not what this tip is about. In most editors (on the Mac anyway) option+click+drag is used to create a rectangular selection. Emacs, of course, supports this, you just need to remap it.

```elisp
(global-set-key [M-down-mouse-1] #'mouse-drag-region-rectangle)
(global-set-key [M-drag-mouse-1] #'ignore)
(global-set-key [M-mouse-1]      #'mouse-set-point)

```
You can also create a rectangular selection with the command `rectangle-mark-mode`.

Don't forget to bind `replace-rectangle` to something convenient for super easy editing.

## u/a-k-n [🔗](https://www.reddit.com/r/emacs/comments/mg98ki/comment/gsvlfku) 
**Votes:** 9

I just discovered that installing the Emacs macport homebrew formula with \`--with-mac-metal\` will significantly increase the performance of Emacs. It's buttery smooth!

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/mb8u1m/comment/gry6bfs) 
**Votes:** 9

If you want helpful mode to completely take over all help functions, and be able to use it with helm-apropos, then add this to your config:

```elisp
(advice-add 'describe-function :override #'helpful-function)
(advice-add 'describe-variable :override #'helpful-variable)
(advice-add 'describe-command  :override #'helpful-callable)
(advice-add 'describe-key      :override #'helpful-key)
(advice-add 'describe-symbol   :override #'helpful-symbol)
```

## u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/kqsw1k/comment/gi66krb) 
**Votes:** 9

I created a "smart" enter function for C/C++ mode. Here's what it does:

* if you press it on a `for`/`if`/`else`/`switch` (no matter where the cursor is on the line) it will put `{`, an empty line and `}`, and will move the cursor into the body
* if you press it on a `struct`/`class`, it's similar to the previous case, but it puts a closing semicolon too.
* otherwise it will put a `;`, and a newline
* if the `{`, `;` or an empty line is already there, it won't put them again
* In comments, it will put an indented line, without continuing the comment (I configured my RET to continue commenting, so I use M-RET when I want to close the comment)

It is useful with smartparens, because you don't have to skip the closing `)` all the time. For example, if you start writing an `if` (cursor is `|`, the closing `)` was put by smartparens):

```elisp
if (expr|)

```
Then press M-RET, this will be the result:

```elisp
if (expr) {
    |
}

```
Since I created this function I use it all the time, I almost never press `{` `}` or `;`.

At function signatures, it cannot figure out whether you want to create a declaration (closed by `;`) or definition (closed by `{ }`), so it puts a `;`. And the function has a parameter (`force-curly`) with which you can force putting a`{` (I mapped this to M-S-return).

Here's the code, maybe there can be a lot of improvements:

```elisp
(defun my-cc-mode-M-RET-context (force-curly)
  (let ((c
         (if force-curly 'curly
           (let ((s (syntax-ppss)))
             (cond
              ((nth 4 s) 'comment)
              ((and (eolp) (looking-back "[{;]")) 'nop)
              ((save-excursion
                 (skip-syntax-forward " ")
                 (looking-at "\\(for\\|if\\|switch\\|else\\|do\\)\s?")) 'curly)
              ((save-excursion
                 (skip-syntax-backward " ")
                 (skip-syntax-backward "w")
                 (looking-at "\\(for\\|if\\|switch\\|else\\|do\\)\s?")) 'curly)
              ((save-excursion
                 (when (nth 3 s) (skip-syntax-backward "^\"") (backward-char))
                 (skip-syntax-backward " ")
                 (if (looking-back ")")
                     (backward-sexp)
                   (ignore-errors (backward-up-list)))
                 (skip-syntax-backward "(")
                 (skip-syntax-backward " ")
                 (looking-back "for\\|if\\|switch")) 'curly)
              ((save-excursion
                 (skip-syntax-forward " ")
                 (looking-at "struct\\|class")) 'curly+semicolon)
              ((save-excursion
                 (skip-syntax-backward " ")
                 (skip-syntax-backward "w")
                 (if (looking-at "struct\\|class") t
                   (skip-syntax-backward "w")
                   (skip-syntax-backward " ")
                   (looking-back "struct\\|class"))) 'curly+semicolon)
              (t 'semicolon))))))
    (cond
     ((or (eq c 'curly) (eq c 'curly+semicolon))
      (save-excursion
        (end-of-line)
        (if (looking-back "{")
            'nop
          c)))
     ((eq c 'semicolon)
      (save-excursion
        (end-of-line)
        (if (looking-back ";")
            'nop
          c)))
     (t c))))
    
(defun my-cc-mode-M-RET (force-curly)
  (let ((c (my-cc-mode-M-RET-context force-curly)))
    (cond
     ((eq c 'nop)
      (if (save-excursion
            (forward-line)
            (beginning-of-line)
            (looking-at-p "[[:space:]]*$"))
          (progn
            (forward-line)
            (c-indent-line))
      (end-of-line) (newline-and-indent)))
     ((eq c 'comment) (newline-and-indent))
     ((eq c 'semicolon)
      (end-of-line)
      (self-insert-command 1 ?\;)
      (newline-and-indent))
     ((eq c 'curly)
      (save-excursion (end-of-line) (unless (looking-back "\s") (insert " ")) (insert "{") (newline-and-indent) (insert "}") (c-indent-line))
      (end-of-line)
      (newline-and-indent))
     ((eq c 'curly+semicolon)
      (save-excursion (end-of-line) (unless (looking-back "\s") (insert " ")) (insert "{") (newline-and-indent) (insert "};") (c-indent-line))
      (end-of-line)
      (newline-and-indent)))))
    
(define-key c-mode-base-map (kbd "M-RET") (lambda () (interactive) (my-cc-mode-M-RET nil)))
(define-key c-mode-base-map (kbd "<M-S-return>") (lambda () (interactive) (my-cc-mode-M-RET t)))
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/ixjcau/comment/g69no38) 
**Votes:** 9

org-variable-pitch.el users might want to give [`valign`](https://github.com/casouri/valign) a look. It aligns your tables nicely, even with pictures (e.g. LaTeX previews) and links. The significance in context of OVP specifically is that you don't need to add `org-link` to `org-variable-pitch-fixed-faces` because valign-mode handles variable pitch links neatly in tables.

If you don't use OVP but use e.g. latex fragments in tables or just pictures, this one is still very helpful.

Kudos to the author, great little package.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/hv3kzf/comment/fyrgnk7) 
**Votes:** 9

[deleted]

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/hqxm5v/comment/fy0xduj) 
**Votes:** 9

Hippie-expand google search suggestions. Completely inspired from [shell-parse.el](https://github.com/malloc47/shell-parse.el/blob/master/shell-parse.el).

I've added `try-expand-google-completion` to the bottom of my `hippie-expand-try-functions-list`

```elisp
(defun google-suggest--request (query)
  (with-current-buffer
      (url-retrieve-synchronously
       (format "http://suggestqueries.google.com/complete/search?client=firefox&q=%s" query) t t 1)
    (goto-char (point-min))
    (re-search-forward "^$")
    (delete-region (point)(point-min))(buffer-string)))
    
(defun google-suggest--list (result)
  (let* ((q (progn
              (string-match ",\\[\\(.*?\\)\\]" result)
              (match-string 1 result)))
         (r (replace-regexp-in-string "\\\"" "" q))
         (l (split-string r "," t)))
    (when (> (length (car (cdr l))) 0)
      (remove
       (car l)
       (cdr l)))))
    
(defun try-expand-google-completion (old)
  (unless old
    (he-init-string (hippie-word-bg) (point))
    (setq he-expand-list (sort
                          (all-completions
                           he-search-string
                           (lambda (s y n) (google-suggest--list (google-suggest--request s))))
                          'string-lessp)))
  (if (null he-expand-list)
      (progn
        (when old (he-reset-string))
        ())
    (he-substitute-string (car he-expand-list) t)
    (setq he-tried-table (cons (car he-expand-list) (cdr he-tried-table)))
    (setq he-expand-list (cdr he-expand-list))
    t))
```

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/hij4ga/comment/fwt1k27) 
**Votes:** 9

Registers: in Emacs from the beginning, so simple you forget how insanely useful they can be. I use them to save text, windows, and locations. https://orys.us/tv

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvrlu40) 
**Votes:** 9

I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it.  If it isn't, the command operates on the text from point to the end of the buffer.  That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time.  Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add:  Sorry folks, this doesn't work like I thought it did.  See the coments below for details.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/gmkg4g/comment/fr65b4d) 
**Votes:** 9

TIL that I can disable ``company`` for some modes, I needed to do it because completion was really slow for the shell and eshell which have vanilla shell completion anyway. Thus 

```elisp
(use-package company
  :after ispell
  :diminish
  :config
  .
  .
  .
  (setq company-global-modes '(not eshell-mode shell-mode))
  (global company-mode 1))
  
```
works as intended

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqczes1) 
**Votes:** 9

[A beginers guide to emacs 24 or later by sasha chua](https://sachachua.com/blog/wp-content/uploads/2013/05/How-to-Learn-Emacs-v2-Large.png)  this helped me tremendously to get started with emacs.

## u/shiroghost [🔗](https://www.reddit.com/r/emacs/comments/g11mp9/comment/fnd2p6p) 
**Votes:** 9

I use `mu4e` as email client. I guess that it is common to send some email to later discover that you forgot the attachment.

This routines check if a mail **likely** needs an attachment by matching a simple regexp. If this is the case and there is no attach, we are asked to confirm that this is what we want to do.

```elisp
  ;;
  ;; Auto-detect if there is a missing attachment in the
  ;; mail and warn before sending.
  ;;
  ;; See https://notmuchmail.org/pipermail/notmuch/2018/026414.html
  ;;
  (defcustom message-likely-needs-attach-regex "attach\\|file\\|adjunto\\|fichero"
    "regex that matches if a mail likely needs an attach. In
  most cases this just matches a few keywords"
    :type '(regexp))
    
  (defun message-narrow-to-body()
    "Narrow the compose buffer to the body of the mail"
    (interactive)
    (widen)
    (goto-char (point-min))
    (narrow-to-region
     (re-search-forward "--text follows this line--" nil t 1)
     (point-max)))
    
  (defun mail-needs-attach-p ()
    "Count number of attach keywords in buffer and return t 
  if there is any"
    (interactive)
    (save-excursion
      (message-narrow-to-body)
      (let (
            (res (count-matches message-likely-needs-attach-regex)))
        (widen)
        (> res 0))))
    
  (defun mail-number-of-attach ()
    "Count number of attach in buffer."
    (interactive)
    (save-excursion
      (goto-char (point-min))
      (count-matches "<#part [^>]*filename=[^>]*>")))
    
  (defun check-mail-and-send ()
    "Check if mail will likely have a missing attachment. 
     If yes ask for confirmation, if no send it."
    (interactive)
    (if (mail-needs-attach-p)
        (if (> (mail-number-of-attach) 0)
            (message-send-and-exit)
          (if (y-or-n-p "Mail has NO attach. Send it anyway? ")
              (message-send-and-exit)))
        (message-send-and-exit)))
    
  (define-key mu4e-compose-mode-map (kbd "C-c C-c") 'check-mail-and-send)
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/g11mp9/comment/fns15ub) 
**Votes:** 9

There is a [request](https://www.reddit.com/r/bugs/comments/g3c1go/might_gweneorg_be_whitelisted/) (created by /u/Amonwilde) for the Reddit team to whitelist [gwene.org](https://gwene.org) to be able to parse RSS feeds from different subreddits (right now, it does not work properly due to exceeded rate-limits). Upvote, please, if you use GNUS to consume RSS feeds and miss updates from Reddit. Thanks!

## u/zkryrmr [🔗](https://www.reddit.com/r/emacs/comments/fgahb2/comment/fk567v4) 
**Votes:** 9

Here is how you can calculate how many times larger an objects shadow is at solar noon. I was surprised to see how many quality functions related to astronomical calculations come with Emacs.

```elisp
(require 'solar)
(defun height-of-sun-at-noon (date)
"Calculates the height of at solar noon on DATE as '(M D YYYY)."
  (let* ((exact-local-noon (solar-exact-local-noon date))
		 (t0 (solar-julian-ut-centuries (car exact-local-noon)))
		 (ut (cadr exact-local-noon))
		 (hnoon (solar-horizontal-coordinates (list t0 ut)
		  (calendar-latitude)
		  (calendar-longitude) t)))
	hnoon))
    
(defun deg-to-rad (x)
  "Convert X from radians to degrees."
  (/ (* x float-pi) 180))
    
(defun length-of-shadow-at-noon (date)
  "Calculates the relative length of an objects shadow at solar noon on DATE."
  (let ((hn (cadr (height-of-sun-at-noon date))))
	(/ 1 (tan (deg-to-rad hn)))))
```

## u/fabiopapa [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/firc94m) 
**Votes:** 9

This may be common knowledge, but I’m always surprised at how few people know about this.

If you have an `ALTERNATE_EDITOR=''`environment variable, and start emacsclient with no emacs server running, it will start an emacs server and try starting emacsclient again. Also works with a `-a` flag on emacsclient command.

## u/kastauyra [🔗](https://www.reddit.com/r/emacs/comments/ev2q9q/comment/fftpfj0) 
**Votes:** 9

I use Emacs on my laptop either undocked, either with external screens connected. It used to be manual work of dragging and resizing frames around, and then setting up windows, but no more, thanks to the great [dispwatch](https://github.com/mnp/dispwatch) package, which handles the screen changes after Emacs has been started. I [wrote some code](https://github.com/laurynas-biveinis/dotfiles/blob/master/emacs/emacs/setup.el#L154) for the initial frame setup, and now have this, which makes the docking/undocking fully seamless:

```elisp
;;; Window and frame geometry
(defun two-windows ()
  "Make frame contain two vertical windows."
  (interactive)
  (delete-other-windows)
  (split-window-right))
    
(defun six-windows ()
  "Make frame contain 2x3 windows."
  (interactive)
  (delete-other-windows)
  (split-window-below)
  (split-window-right)
  (split-window-right)
  (windmove-down)
  (split-window-right)
  (split-window-right)
  (balance-windows))
    
(cl-defstruct dotfiles--frame-geometry top left height width)
    
(defun dotfiles--add-frame-geometry-to-initial-alist (geometry)
  "Add frame GEOMETRY to `initial-frame-alist'."
  (add-to-list 'initial-frame-alist
               `(top . ,(dotfiles--frame-geometry-top geometry)))
  (add-to-list 'initial-frame-alist
               `(left . ,(dotfiles--frame-geometry-left geometry)))
  (add-to-list 'initial-frame-alist
               `(height . ,(dotfiles--frame-geometry-height geometry)))
  (add-to-list 'initial-frame-alist
               `(width . ,(dotfiles--frame-geometry-width geometry))))
    
(defun dotfiles--move-to-frame-geometry (geometry)
  "Resize and repositon frame to GEOMETRY."
  (set-frame-position nil (dotfiles--frame-geometry-left
                           geometry)
                      (dotfiles--frame-geometry-top geometry))
  (set-frame-size nil (dotfiles--frame-geometry-width geometry)
                  (dotfiles--frame-geometry-height geometry)))
    
(defconst darkstar-laptop-screen '(1680 . 1050))
(defconst darkstar-laptop-geometry
  (make-dotfiles--frame-geometry :top 1 :left 1 :height 65 :width 237))
    
(defconst darkstar-external-screen '(7696 . 1692))
(defconst darkstar-external-geometry
  (make-dotfiles--frame-geometry :top 4 :left 3011 :height 117 :width 426))
    
;; Possible interim states while docking/undocking - ignore
(defconst frame-geometries-to-ignore [(3600 . 1080) (5520 . 1080) (4688 . 1692)
                                      (3600 . 1692) (3008 . 1692)])
    
(defun diagnose-unknown-display-geometry (display-geometry)
  "Diagnose unknown DISPLAY-GEOMETRY."
  (message "Unknown display size %sx%s"
           (car display-geometry) (cdr display-geometry)))
    
(require 'seq)
(let ((display-geometry (cons (display-pixel-width) (display-pixel-height))))
  (cond ((equal display-geometry darkstar-laptop-screen)
         ;; darkstar without external screens: initial frame positioned in the
         ;; top left corner
         (dotfiles--add-frame-geometry-to-initial-alist
          darkstar-laptop-geometry)
         (two-windows))
        ((equal display-geometry darkstar-external-screen)
         ;; darkstar with external screens: initial frame maximized in the
         ;; middle screen
         (dotfiles--add-frame-geometry-to-initial-alist
          darkstar-external-geometry)
         (add-to-list 'initial-frame-alist '(fullscreen . fullboth))
         (add-to-list 'initial-frame-alist '(fullscreen-restore . maximized))
         (six-windows))
        ((seq-position frame-geometries-to-ignore display-geometry) ())
        (t (diagnose-unknown-display-geometry display-geometry))))
    
;;; dispwatch
(require 'dispwatch)
(defun dotfiles--display-changed-hook (new-display-geometry)
  "Reconfigure frame windows on display geometry change to NEW-DISPLAY-GEOMETRY."
  (message "Resizing for %s" new-display-geometry)
  (cond ((equal new-display-geometry darkstar-laptop-screen)
         (dotfiles--move-to-frame-geometry darkstar-laptop-geometry)
         (set-frame-parameter nil 'fullscreen 'maximized)
         (two-windows))
        ((equal new-display-geometry darkstar-external-screen)
         (dotfiles--move-to-frame-geometry darkstar-external-geometry)
         (set-frame-parameter nil 'fullscreen 'fullboth)
         (six-windows))
        ((seq-position frame-geometries-to-ignore new-display-geometry) ())
        (t (diagnose-unknown-display-geometry new-display-geometry))))
    
(add-hook 'dispwatch-display-change-hooks #'dotfiles--display-changed-hook)
(dispwatch-mode 1)

```
Some things here might be overkill (cl-defstruct, seq?), but this was also an Emacs lisp exercise.

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/el8oq7/comment/fdggv7n) 
**Votes:** 9

```elisp
(defun repeat-last-shell-command ()
  (interactive)
  (let ((last-cmd (cadr (assoc 'shell-command command-history))))
    (when (y-or-n-p (concat "execute " last-cmd))
      (async-shell-command last-cmd))))
```

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/ei02j1/comment/fcmosxh) 
**Votes:** 9

Here's how I open pdf files in mupdf with the colors of my current emacs theme:

```elisp
(defun mupdf-reader (file)
  (let* ((background (face-attribute 'default :background))
         (foreground (face-attribute 'default :foreground))
         ;; if it's a hex code then remove the # prefix
         ;; if it's a color name then convert it to hex code
         (backg-color (if (equal (substring background 0 1) "#")
                          (substring background 1)
                        (substring (color-name-to-hex background) 1)))
         (font-color (if (equal (substring foreground 0 1) "#")
                         (substring foreground 1)
                       (substring (color-name-to-hex foreground) 1))))
    (make-process
     :name "book-reader"
     :connection-type 'pipe
     :command (list "mupdf-gl" "-B" font-color "-C" backg-color file))))

```
The above function depends on `color-name-to-hex`, which basically combines two inbuilt functions:

```elisp
(defun color-name-to-hex (name)
  (let ((rgb (color-name-to-rgb name)))
    (when rgb
      (setcdr (last rgb) '(2))
      (apply 'color-rgb-to-hex rgb))))

```
Here's a simple handler function to open all pdf and epub files from emacs with mupdf:

```elisp
(defun mupdf-file-handler (operation &rest args)
  (mupdf-reader (car args))
  (kill-buffer nil)
  ;; the error here is to avoid a bug that tries to
  ;; match the true filename with the deleted buffer
  (error ""))

```
Add the magic filenames to init.el so that emacs will know when to use the handler:

```elisp
(put 'mupdf-file-handler 'safe-magic t)
(put 'mupdf-file-handler 'operations '(insert-file-contents))
(add-to-list 'file-name-handler-alist '("\\(\\.\\(?:epub\\|pdf\\)\\)$" . mupdf-file-handler))
```

## u/human_banana [🔗](https://www.reddit.com/r/emacs/comments/eeyhdz/comment/fbyevye) 
**Votes:** 9

Have multiple machines where some things are available and other aren't?

`when-available` takes a function name and if it exists, executes the rest.

```elisp
(defmacro when-available (func cmd)
  "Execute CMD if FUNC exists as a function."
  `(when (fboundp ,func) ,cmd))

```
So you can deal with different environments.

I mostly use this for things that are nice to have, but aren't required and don't need to be installed if they aren't already.

## u/itistheblurstoftimes [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f8978oj) 
**Votes:** 9

I just started using mu4e and wanted to have some visual indication of which account was associated with each email. Did this quickly but it seems to work well. This is akin to what K9 mail does on android.

The names "account1" refer to the folders in your Maildir for each account, i.e., \~/Maildir/account1

&#x200B;

```elisp
(setq x-mu4e-account-colors '(("account1" . "orange")
			      ("account2" . "red")
			      ("account3" . "blue")))
    
(setq x-mu4e-account-colors-prefix "    ")
    
(defun x-mu4e-header-colors (msg line)
  "Add a small color block to headers view indicating the account for each email"
  (let* ((maildir (nth 1 (s-split "/" (plist-get msg :maildir))))
         (color (cdr (assoc maildir x-mu4e-account-colors))))
    (setq line (concat x-mu4e-account-colors-prefix line))
    (put-text-property 0 (- (length x-mu4e-account-colors-prefix) 2) 'face `(:background ,color) line)
    line))
    
(add-to-list 'mu4e~headers-line-handler-functions 'x-mu4e-header-colors t)
```

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f848ctb) 
**Votes:** 9

A simple ivy function to play soma.fm with mpv in emacs:

```elisp
(defun launcher-somafm ()
  (interactive)
  (let ((stations
	 '(("BAGeL Radio: What alternative rock radio should sound like. [explicit]"
	    . "http://somafm.com/bagel32.pls")
	   ("Beat Blender: A late night blend of deep-house and downtempo chill."
	    . "http://somafm.com/beatblender32.pls")
	   ("Black Rock FM: From the Playa to the world, for the annual Burning Man festival."
	    . "http://somafm.com/brfm32.pls")
	   ("Boot Liquor: Americana Roots music for Cowhands, Cowpokes and Cowtippers"
	    . "http://somafm.com/bootliquor32.pls")
	   ("Christmas Lounge: Chilled holiday grooves and classic winter lounge tracks. (Kid and Parent safe!)"
	    . "http://somafm.com/christmas32.pls")
	   ("Christmas Rocks!: Have your self an indie/alternative holiday season!"
	    . "http://somafm.com/xmasrocks32.pls")
	   ("Cliqhop idm: Blips'n'beeps backed mostly w/beats. Intelligent Dance Music."
	    . "http://somafm.com/cliqhop32.pls")
	   ("Covers: Just covers. Songs you know by artists you don't. We've got you covered."
	    . "http://somafm.com/covers32.pls")
	   ("DEF CON Radio: Music for Hacking. The DEF CON Year-Round Channel."
	    . "http://somafm.com/defcon32.pls")
	   ("Deep Space One: Deep ambient electronic, experimental and space music. For inner and outer space exploration."
	    . "http://somafm.com/deepspaceone32.pls")
	   ("Digitalis: Digitally affected analog rock to calm the agitated heart."
	    . "http://somafm.com/digitalis32.pls")
	   ("Doomed (Special): For Halloween: Dark industrial/ambient music for tortured souls."
	    . "http://somafm.com/specials32.pls")
	   ("Drone Zone: Served best chilled, safe with most medications. Atmospheric textures with minimal beats."
	    . "http://somafm.com/dronezone32.pls")
	   ("Dub Step Beyond: Dubstep, Dub and Deep Bass. May damage speakers at high volume."
	    . "http://somafm.com/dubstep32.pls")
	   ("Fluid: Drown in the electronic sound of instrumental hiphop, future soul and liquid trap."
	    . "http://somafm.com/fluid32.pls")
	   ("Folk Forward: Indie Folk, Alt-folk and the occasional folk classics."
	    . "http://somafm.com/folkfwd32.pls")
	   ("Groove Salad: A nicely chilled plate of ambient/downtempo beats and grooves."
	    . "http://somafm.com/groovesalad32.pls")
	   ("Groove Salad Classic: The classic (early 2000s) version of Groove Salad"
	    . "http://somafm.com/gsclassic32.pls")
	   ("Illinois Street Lounge: Classic bachelor pad, playful exotica and vintage music of tomorrow."
	    . "http://somafm.com/illstreet32.pls")
	   ("Indie Pop Rocks!: New and classic favorite indie pop tracks."
	    . "http://somafm.com/indiepop32.pls")
	   ("Jolly Ol' Soul: Where we cut right to the soul of the season."
	    . "http://somafm.com/jollysoul32.pls")
	   ("Left Coast 70s: Mellow album rock from the Seventies. Yacht not required."
	    . "http://somafm.com/seventies32.pls")
	   ("Lush: Sensuous and mellow vocals, mostly female, with an electronic influence."
	    . "http://somafm.com/lush32.pls")
	   ("Metal Detector: From black to doom, prog to sludge, thrash to post, stoner to crossover, punk to industrial."
	    . "http://somafm.com/metal32.pls")
	   ("Mission Control: Celebrating NASA and Space Explorers everywhere."
	    . "http://somafm.com/missioncontrol32.pls")
	   ("PopTron: Electropop and indie dance rock with sparkle and pop."
	    . "http://somafm.com/poptron32.pls")
	   ("Secret Agent: The soundtrack for your stylish, mysterious, dangerous life. For Spies and PIs too!"
	    . "http://somafm.com/secretagent32.pls")
	   ("Seven Inch Soul: Vintage soul tracks from the original 45 RPM vinyl."
	    . "http://somafm.com/7soul32.pls")
	   ("SF 10-33: Ambient music mixed with the sounds of San Francisco public safety radio traffic."
	    . "http://somafm.com/sf103332.pls")
	   ("Sonic Universe: Transcending the world of jazz with eclectic, avant-garde takes on tradition."
	    . "http://somafm.com/sonicuniverse32.pls")
	   ("Space Station Soma: Tune in, turn on, space out. Spaced-out ambient and mid-tempo electronica."
	    . "http://somafm.com/spacestation32.pls")
	   ("Suburbs of Goa: Desi-influenced Asian world beats and beyond."
	    . "http://somafm.com/suburbsofgoa32.pls")
	   ("The Trip: Progressive house / trance. Tip top tunes."
	    . "http://somafm.com/thetrip32.pls")
	   ("ThistleRadio: Exploring music from Celtic roots and branches"
	    . "http://somafm.com/thistle32.pls")
	   ("Underground 80s: Early 80s UK Synthpop and a bit of New Wave."
	    . "http://somafm.com/u80s32.pls")
	   ("Xmas in Frisko: SomaFM's wacky and eclectic holiday mix. Not for the easily offended."
	    . "http://somafm.com/xmasinfrisko32.pls"))))
    (ivy-read "soma.fm " (mapcar 'car stations)
	      :re-builder #'regexp-quote
	      :action (lambda (x) (save-window-excursion
				    (async-shell-command (concat "mpv " (cdr (assoc x stations)))
							 "*soma.fm*"))))))
    
(defun kill-soma ()
  (interactive)
  (kill-process "*soma.fm*")
  (sleep-for 2)
  (kill-buffer "*soma.fm*"))
```

## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/drw8i3/comment/f6ys5ae) 
**Votes:** 9

My fav org-bullets config, looks nice and you immediately know which headline level you have:

```elisp
    (setq org-bullets-bullet-list
      '("⓪" "①" "②" "③" "④" "⑤" "⑥" "⑦" "⑧" "⑨"))
```

## u/c17g [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f65iky4) 
**Votes:** 9

I just found out the great variable `org-extend-today-until`. Basically it defines when your day really ends. If you sleep late, check it out.

Goodbye to the days using `M-x org-todo-yesterday` at midnight, clocking off items before sleep..

## u/hairlesscaveman [🔗](https://www.reddit.com/r/emacs/comments/gmkg4g/comment/fr4gdm6) 
**Votes:** 9

I follow the format of `{ticketnumber}-{short-description}` when creating branches using Magit, but when typing the descriptive name for the branch I often type `SPC` between words instead of dash due to muscle memory when writing sentences. This causes a warning to be shown, because "Whitespace isn't allowed here", and breaks "flow" for me.

The advice below quiets this warning, and inserts a dash whenever space is pressed.

```elisp
(advice-add 'magit-whitespace-disallowed :around (lambda (orig-fun &rest args) (interactive) (insert "-")))
```

## u/w0ntfix [🔗](https://www.reddit.com/r/emacs/comments/11ey9ft/comment/jajfxc9) 
**Votes:** 9

turning off org-elements cache speeds up input latency for me (found from profiling):

```elisp
(setq org-element-use-cache nil)


```
it seems (at least on my org 9.6.1) to update the cache after calls to `org-self-insert-command` (so, a lot!)

## u/jcubic [🔗](https://www.reddit.com/r/emacs/comments/1b20xgn/comment/ksoij65) 
**Votes:** 8

I use this often when working on large files. You can bookmark up to 9 places inside a file and jump to that position. The limitations is that if you add somehing above the bookmark the position is shifted a bit but it's not that hard to find the right place.

It works like this: `C-c 0` creates a bookmark and `C-c <1-9>` jumps into a bookmark. I've written about this on my old blog. I still use this from time to time.

[Faster buffer bookmarking in Emacs](https://jcubic.wordpress.com/2012/01/25/faster-buffer-bookmarking-in-emacs/).

## u/LionyxML [🔗](https://www.reddit.com/r/emacs/comments/1b20xgn/comment/kslwb72) 
**Votes:** 8

A blog post regarding my own Emacs config aiming to get the same user experience on both TUI and GUI.  
[https://www.rahuljuliato.com/posts/lemacs](https://www.rahuljuliato.com/posts/lemacs)

## u/demosthenex [🔗](https://www.reddit.com/r/emacs/comments/1b7uj43/comment/ktogga6) 
**Votes:** 8

M-x ielm  Use the repl while learning elisp coding. I had no idea!

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/18mplfa/comment/ke5xr5j) 
**Votes:** 8

This makes stack-outputs of debug-buffers much more readable:

```elisp
(setopt debugger-stack-frame-as-list t)
```

## u/hunajakettu [🔗](https://www.reddit.com/r/emacs/comments/16tes2a/comment/k2f683f) 
**Votes:** 8

It is the only thing that keeps me sane in a Windows shop.

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/12cd23k/comment/jf3ohpv) 
**Votes:** 8

I work with multiple Git repositories in my day job, but one in particular occupies 95% of my time.  I've often wished I could set up Projectile so that if I run one of its commands while not in any repo, it will behave as if I'd changed to that main repo first.  I couldn't find a built-in way to do that, but got the effect I wanted with some advice:

```elisp
(defun default-to-main-project (dir)
  (or dir *main-project-dir*))

(advice-add 'projectile-ensure-project :override #'default-to-main-project)

```
I lose some of the functionality of `projectile-ensure-project`, but I never used it anyway.

## u/SlowValue [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8u1ebf) 
**Votes:** 8

I put a couple of similar functions on my `<f5>` key (with all combinations of modifier keys) and tend to forget the meanings of those combinations.

So I made a small function to give some help for a list of keybindings.:

```elisp
(defun my-generate-keybind-doc-on-keys (&rest keys)
  "generate help on some given  keybindings (kbd style)."
  (substring
   (apply #'concat
          (mapcar #'(lambda (key)
                      (let* ((fun (key-binding (kbd key)))
                             (fun-name (symbol-name fun))
                             (doc (documentation fun 'raw))
                             (oneline (substring doc 0 (string-match "\n" doc))))
                        (put-text-property 0 (length key) 'face 'font-lock-keyword-face key)
                        (put-text-property 0 (length fun-name) 'face 'font-lock-function-name-face fun-name)
                        (put-text-property 0 (length oneline) 'face 'font-lock-doc-face oneline)
                        (format "%10s : %-30s → %s\n" key fun-name oneline)))
                  keys))
   0 -1))

```
To show that help in the echo buffer, one needs to define a command, which can be bound to a key:

```elisp
(defun my-show-help-on-some-keys ()
  "demo generate help on 3 keys"
  (interactive)
  (message (my-generate-keybind-doc-on-keys "C-s"
                                            "<f1> k"
                                            "C-c !")))

```
Then calling the command `my-show-help-on-some-keys`, shows following message in the echo buffer:

```elisp
   C-s : isearch-forward                → Do incremental search forward.
<f1> k : helpful-key                    → Show help for interactive command bound to KEY-SEQUENCE.
 C-c ! : shell-here                     → Open a shell relative to default-directory.


```
Remark: I know that `which-key` exists, but I'm already above that level of knowledge and don't need `which-key` anymore.

## u/snippins1987 [🔗](https://www.reddit.com/r/emacs/comments/10ktqj0/comment/j67y1pt) 
**Votes:** 8

Context: after finally getting into org-mode (org-roam specifically) and writing a bunch of elisp, I still dot not at all satisfy dealing with *org-table* or *table.el*, it just felt so out-of-place and clunky compare to the otherwise slick experiences that org-mode bring. So I basically gave up and have been linking spreadsheet files into my org files instead.

Obviously, this isn't ideal, as I need to view tables in separated libreoffice calc windows. And the notes is not viewable on GitLab or GitHub, etc.

So I decided to leverage org-babel to render spreadsheets inside my org notes.

For that, I created a bash script called emacs_excel_to_png that utilize ssconvert, ImageMagick, and Firefox. The script converts the spreadsheet into HTML, then the HTML is rendered by Firefox and finally ImageMagick will be used to crop the rendered image. The script will then print an org-link so that the image is showed the result section.

With that, in an org file, we can do something as follows:

```elisp
#+begin_src bash :dir ./ :results raw :var ZOOM=1.3
export ZOOM; emacs_excel_to_png \
"note_files/emacs_excel_to_png.xlsx" \
"note_files/emacs_excel_to_png.png"
#+end_src

```
The content of the emacs_excel_to_png script can be found below:

```elisp
#!/usr/bin/env bash
    
excel_file="$1"
output_file="$2"
    
if [[ "$#" -lt 2 ]]; then
notify-send "Not enough parameters"
exit
fi
    
tmp_dir="/dev/shm"
mkdir -p $tmp_dir/emacs_excel_to_png
    
if ! [[ "$excel_file" = /* ]]; then
excel_file="${PWD}/${excel_file}"
fi
    
if ! [[ "$output_file" = /* ]]; then
output_file="${PWD}/${output_file}"
fi
    
if [[ -z "$ZOOM" ]]; then
ZOOM="1.3"
fi
    
cd $tmp_dir/emacs_excel_to_png || exit
    
# convert xlsx to html
ssconvert --export-type=Gnumeric_html:html40  --import-type=Gnumeric_Excel:xlsx "$excel_file" "$tmp_dir/emacs_excel_to_png/output.html" 2> /dev/null
    
# remove captions for sheets in the output html if there is only one sheet
n_sheets=$(grep -c "<caption>" $tmp_dir/emacs_excel_to_png/output.html)
if [[ "$n_sheets" -eq "1" ]]; then
sed -i '/<caption>/d' $tmp_dir/emacs_excel_to_png/output.html
fi
    
# scaling the table using the ZOOM environment variable 
sed -i "s/<\/style>/body { transform-origin: top left; transform: scale(${ZOOM}, ${ZOOM}); }<\/style>/g" $tmp_dir/emacs_excel_to_png/output.html
    
# using firefox screenshot feature to convert from html to png
# Note: -P screenshot, we need created a seperated profile for taking screenshot so firefox won't complain about already running
/usr/bin/firefox -P screenshot --headless --window-size 3840 --screenshot file://$tmp_dir/emacs_excel_to_png/output.html > /dev/null 2>&1
    
# trim the picture to leave only the data area and invert the colors
convert -trim -negate screenshot.png screenshot.trimmed.png 2> /dev/null
    
# move the resulting pictures into the appropirate place
mv screenshot.trimmed.png "$output_file"
    
# print the output as org-mod pictures link
echo "[[file:${output_file}]]"
    
# cleanup
rm -rf $tmp_dir/emacs_excel_to_png
```

## u/Nondv [🔗](https://www.reddit.com/r/emacs/comments/108zin2/comment/j4ct1y1) 
**Votes:** 8

Maybe not new for anyone but I only recently found out that `C-c <any letter>` is conventionally reserved for user bindings. I was constantly afraid to define my own bindings bc of a potential clash so tended to use M-x instead. Now I finally bind my most used commands.

With the above in mind, Im also afraid to forget my bindings. I use which-key package so I wrote a function "define-my-keybinding letter fn" which binds the letter to `C-c <letter>` and to "my-bindings" keyset (prefix) which itself is bound to `C-c m`. Basically, if i forget what bindings I use, I just press C-c m and which-key shows me all of MY bindings (yes, it shows them with C-c too but it's mixed with mode bindings so not helpful)

## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/z8ltei/comment/izxi6ie) 
**Votes:** 8

A great functionality for calculating some schedule events is `org-evaluate-time-range` (`C-c C-y`). When I need to propose an event of a particular length, I use this to get my length right between two dates.

## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/ywnt6p/comment/ix0a6ui) 
**Votes:** 8

Some weeks ago, u/paretoOptimalDev made an interesting post about using more `next-buffer` and `previous-buffer` instead of `switch-to-buffer` (https://redd.it/ybqp3m). I liked the post and had captured it for later and only now could process it properly.

The reasoning for using `next-buffer` and `previous-buffer` is good, but pretty much everyone complained about their default bindings.

It is worth noting that Emacs 28 has included `repeat-mode` and Emacs 29 has added `next-buffer` and `previous-buffer` to the repeat maps. So, if you're in Emacs 29, just enabling `repeat-mode` gets you a better behavior for this. You can start with `C-x <right>` and, after that, just the arrow keys get you to the next or previous buffer.

If you're still on Emacs 28, you can use:

```elisp
(defvar buffer-navigation-repeat-map
  (let ((map (make-sparse-keymap)))
    (define-key map (kbd "<right>") 'next-buffer)
    (define-key map (kbd "<left>") 'previous-buffer)
    map)
  "Keymap to repeat `next-buffer' and `previous-buffer'.  Used in `repeat-mode'.")
(put 'next-buffer 'repeat-map 'buffer-navigation-repeat-map)
(put 'previous-buffer 'repeat-map 'buffer-navigation-repeat-map)
```

## u/pedzsanReddit [🔗](https://www.reddit.com/r/emacs/comments/ydsjfy/comment/itw7yp2) 
**Votes:** 8

I spent the past week cleaning up my Emacs init files and I bumped into this little gem.  I call it "ZSH man page search mode" because it was the place that I first needed it.

I do `M-x man zshall(1)` fairly frequently (and don't forget to `widen` so you can see all of the pages).  Then I would start searching for what I was looking for.  The man page is nicely structured so if I wanted to find "foo", search for "foo" had too many hits.  What I wanted to find was the place where "foo" is described.

This little search does the trick.  It is probably useful for other man pages and perhaps even other places but for now... I call it "ZSH man page search"

Enjoy!

```lisp
  (defun zsh-manpage-search-regexp (string &optional lax)
"Returns a string to search for entries in the zshall man page"
(format "\n[A-Z ]*\n \\{7\\}%s%s" string (if lax "" "\\_>")))

  (isearch-define-mode-toggle zsh-manpage "z" zsh-manpage-search-regexp "\
  Searching zshall man page for where a concept is described")
```

## u/meedstrom [🔗](https://www.reddit.com/r/emacs/comments/ydsjfy/comment/itwo67r) 
**Votes:** 8

I had the [defrepeater](https://github.com/alphapapa/defrepeater.el) package for a while, but didn't realize you could use it this elegantly!

```elisp
(global-set-key [remap transpose-lines] (defrepeater #'transpose-lines))

```
Something similar is actually in the readme, but I guess I glossed over it back then.

## u/HM0880 [🔗](https://www.reddit.com/r/emacs/comments/wwdpju/comment/illuprk) 
**Votes:** 8

In Org Mode, what is the reason to use `~` for in-line code vs. `=` for monospace text?  I use `=` for both code and monospace since (afaict) Org renders both code and monospace the same way in LaTeX PDF and HTML output, and `=` does not require using shift (unlike `~`).

## u/hairlesscaveman [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikwhvfs) 
**Votes:** 8

Question: I generally work with 3 vertical panes, with my preferred layout as left for code, middle for related test file, and right for test output or magit. However, keeping this layout is tricky; sometimes magit will open in the first pane, or the current pane when I'm focused in the middle, and deadgrep will open just anywhere… well, it's quite hectic and feels random.

Is there any way I can get files to open in panes 1 or 2, and always have things like magit/test-output/deadgrep/etc on pane 3? I've tried "shackle" but I've had no success with it; everything seems to open in a horizontal pane at the bottom of my screen regardless of config.

Any suggestions would be appreciated!

## u/attento_redaz [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/iku77h0) 
**Votes:** 8

Using [zotra](https://github.com/mpedramfar/zotra), [citar](https://github.com/emacs-citar/citar) and some parts of the Org-cite ecosystem I hacked together a highly experimental but pretty comfortable environment for working with "org-biblatex bibliographies" which are basically like [org-bibtex](http://gewhere.github.io/org-bibtex) but with biblatex entries represented as headings with suitable properties instead of bibtex. I have a function which retrieves a biblatex entry corresponding to an url using zotra and adds a corresponding Org heading with the biblatex fields as properties, and the entry becomes available in Citar as soon as I save the document. Citing these entries then works anywhere, even in the same document with a suitable `#+bibliography: my-org-biblatex-file.org` declaration. Exporting the citations also works with the CSL exporter, no conversion is necessary to a proper biblatex bibliography file (but can be easily done if one needs biblatex-based export). Since the bibliography is an Org document, tagging, agenda commands, column view etc. can all be used with the bibliography entries.  In a way it's frightening how much can be achieved building on already existing stuff and with a few lines of Emacs Lisp.

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/w3gx6o/comment/igyt3ff) 
**Votes:** 8

I sometimes want to pick a random choice from a long org-mode checkbox list from among those items not already checked.  I recently whipped up a little helper function for that:

```elisp
(defun random-choice ()
  (interactive)
  (let ((n 0)
        (selected nil))
    (while (search-forward-regexp (rx point "- [" (group nonl) "]" (+ nonl) "\n") nil t)
      (when (and (string= " " (match-string 1)) (zerop (random (setq n (1+ n)))))
        (setq selected (match-beginning 0))))
    (if selected
        (goto-char selected)
      (error "No unfinished choice found"))))
```

## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/w3gx6o/comment/ih3s9fl) 
**Votes:** 8

you can have an org-mode file in source block in an org file

## u/isamert [🔗](https://www.reddit.com/r/emacs/comments/vskthv/comment/if1ua6o) 
**Votes:** 8

I sometimes clone stuff with https instead of ssh, so this fixes that:

```elisp
(defun isamert/git-origin-switch-to-ssh ()
  (interactive)
  (when-let* ((https-origin (s-trim (shell-command-to-string "git config --get remote.origin.url")))
              (it (s-match "https://\\(.*\\)\\.\\(com\\|net\\|org\\)/\\(.*\\)" https-origin))
              (ssh-origin (format "git@%s.%s:%s" (nth 1 it) (nth 2 it) (nth 3 it))))
    (shell-command-to-string (format "git remote set-url origin %s" ssh-origin))))


```
It works for github/gitlab etc. You need to extend the regexp for making it work for more obscure addresses.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/v2by7z/comment/iatuubw) 
**Votes:** 8

I like being able to switch projects quickly, and I'm too lazy to choose a specific file after selecting which project to switch to, so I did this:

```elisp
(defun lw-switch-project ()
(interactive)
(let* ((projects (projectile-relevant-known-projects))
(project (f-expand (completing-read "Switch to project: " projects)))
(windows (window-list))
(opened-files (projectile-project-buffers project))
(all-files (if (> (length windows) (length opened-files))
(append
opened-files
(--> (projectile-project-files project)
(-take (- (length windows)(length opened-files)) it)
(-map (-partial #'f-join project) it)
(-map #'find-file-noselect it)))
opened-files)))
(--each (-zip windows all-files)
(set-window-buffer (car it) (cdr it)))))

```
A little verbose, but basically it prompts for a projectile project, then fills all open windows with recent project files, preferring already open buffers.

## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/txh85s/comment/i3mghuf) 
**Votes:** 8

I wanted to be able to expand yasnippets within other yasnippets (so here tab would jump to the next position instead of trying to expand snippet), surprisingly all I had to do was:

`:bind ("C-<tab>" . yas-expand)`

So C-<tab> expands a snippet within a snippet, and everything just worked as I'd hoped.  Once I'm done with the nested expansion <TAB> just moves on to the outer one. \*shrug\*

## u/rberaldo [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnvaab8) 
**Votes:** 8

A tiny thing I just noticed: in `tex-mode`, you can create a new environment with `C-c C-e`. With the universal argument (`C-u C-c C-e`), however, you can easily change any environment into another.

I created an `enumerate` environment and immediately changed my mind. By chance, I intuitively tried the aforementioned command and I was instantly able to change the environment into `itemize`.

EDIT: markdown

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/pg69k8/comment/hbc84y3) 
**Votes:** 8

[deleted]

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/p28rl5/comment/h8utmh2) 
**Votes:** 8

Imenu is pretty adictive and it's disappointing when some major mode doesn't support it. Luckily, it's fairly easy to cook up some regexps to provide imenu support in a new major mode. For example I recently noticed that customize buffers didn't have imenu support add I wrote this:

```elisp
(defun configure-imenu-Custom ()
  (setq imenu-generic-expression
        '(("Faces" "^\\(?:Show\\|Hide\\) \\(.*\\) face: \\[sample\\]" 1)
          ("Variables" "^\\(?:Show Value\\|Hide\\) \\([^:\n]*\\)" 1))))

(add-hook 'Custom-mode-hook #'configure-imenu-Custom)

```
One subtlety with writing this is that the customize buffers show little triangles instead of the words "Show", "Hide" or "Show Value". To figure out what text is really in the buffer you can use `C-u C-x =` which tells you about any overlays at point.

## u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/p28rl5/comment/h8iin6r) 
**Votes:** 8

Meta:

Apparently, the `&c.` in the title is an abbreviation of the abbreviation `etc.`, which is fine except that the sidebar's link to past threads of this kind is in fact a link to a reddit search which includes as a search term `etc.` but not `&c.`, so this thread will not show up.

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/oxo1xh/comment/h88ph29) 
**Votes:** 8

This isn't a tip or trick, so I guess it is covered by &c. The Init File section of the manual has this example of setting a user email address:

```elisp
(setq user-mail-address "cheney@torture.gov")
```

## u/globalcandyamnesia [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h31xz50) 
**Votes:** 8

If you're using the mark setting commands to expand a selection like `M-@` (mark next word) or `C-M-@` (mark next sexp), you can swap the point and mark (`C-x C-x`) and the selection will be expanded to the left rather than the right.

So if you're in the middle of a sentence, you can press `M-@` a few times to select some words to the right, press `C-xx`, and press `M-@` a few more times to add words before the selection.

## u/11fdriver [🔗](https://www.reddit.com/r/emacs/comments/mpwapo/comment/gufsfeu) 
**Votes:** 8

Sometimes I'm working on programs with functions a few pages long, and `follow-mode` means that I can open two windows of the same buffer side-by-side and have the text flow like a book between them. I can double or even triple the amount of lines I can view at one time.

This has largely superseded what I might have used those code-overview map things for, which is difficult anyway, since I like to use Emacs from the terminal.

It will keep the text aligned as you move through the file, and pairs well with binding `<mouse-5>` and `<mouse-4>` to the `scroll-up/down-line` commands in `xterm-mouse-mode`.

If I'm studying/notetaking, I often end up with a few Emacs-windows arranged in a vertical stack. `winner-mode` or `window-configuration-to-register` are great, but if I want to quickly regain some vertical screen-real-estate without messing up the layout, then it's pretty intuitive to use `follow-mode` and just switch multiple windows to the same buffer, now they behave like one.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/k8zjx5/comment/gf1msbr) 
**Votes:** 8

[deleted]

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/k4gv0x/comment/ge8si78) 
**Votes:** 8

Here's all I need for completions:

```elisp
(use-package icomplete
  :bind
  (:map icomplete-minibuffer-map
        ([C-return] . exit-minibuffer)
        ([return] . minibuffer-try-complete-and-exit))
  :config
  (defun minibuffer-try-complete-and-exit ()
    (interactive)
    (minibuffer-force-complete)
    (setq-local deactivate-mark nil)
    (throw 'exit nil))
  :hook (after-init . icomplete-mode))
    
(use-package orderless
  :ensure t
  :custom
  (completion-styles '(orderless))
  (orderless-matching-styles 'orderless-literal))
```

## u/andrmuel [🔗](https://www.reddit.com/r/emacs/comments/jn6m14/comment/gb502ps) 
**Votes:** 8

This is something I'm not actively using anymore, but it was one of my I-love-emacs moments, so I wanted to share this for a while.

At work, I used to have an org-mode journal where I would take meeting notes. After the meeting, I exported the subtree for the current meeting to a PDF (via ODT) and sent it to the participants via mail.

After a while I extended org-export to get a shortcut (`C-e C-s o M`) to automatically

* export to PDF via ODT
   * using a proper corporate design & logo via ODT\_STYLES\_FILE header
* rename the file to include the current date
* open thunderbird, starting a new message with
   * subject taken from document title (if exporting all) or subtree heading (if exporting subtree
   * pre-filled text
   * the exported PDF already attached

&#8203;

```elisp
;;
;; export and send to mail
;;
(defun org-foo-export-to-foo-and-sendto-mail (org-export-function &optional async subtreep visible-only ext-plist)
  (interactive)
  (when (or (string-equal (file-name-extension (buffer-file-name)) "gpg")
            (string-equal (file-name-extension (buffer-file-name)) "asc"))
    (unless (yes-or-no-p "Really export GPG encrypted file and send via mail? ")
      (throw 'abort-export-mail-gpg "export aborted by user")))
  (unless subtreep
    (unless (yes-or-no-p "Really export everything and send via mail? ")
      (throw 'abort-export-mail-everything "export aborted by user")))
  (let* ((title (if subtreep
                    ;; subtree -> use subtree heading as title
                    (nth 4 (org-heading-components))
                    ;; whole document -> use document title
                    (org-element-map
                        (org-element-parse-buffer)
                        'keyword
                      (lambda (e)
                        (when (string= "TITLE" (org-element-property :key e))
                          (org-element-property :value e)))
                      nil
                      t)))
         (file (file-name-nondirectory (funcall org-export-function async subtreep visible-only ext-plist))) ; note: odt export includes directory in output file name, html export includes no directory
         (newfile (format "%s_%s" (format-time-string "%Y%m%d") file))
         (directory (file-name-directory (buffer-file-name))))
    (rename-file (concat directory file) (concat directory newfile) 1)
    (my/thunderbird-compose-mail
     ""
     (format "Notes: %s" title)
     "Please find attached my notes ...\n\nBest regards, Andreas"
     (format "file://%s%s"
             directory newfile))))
    
(defun org-odt-export-to-odt-and-sendto-mail (&optional async subtreep visible-only ext-plist)
  (interactive)
  (org-foo-export-to-foo-and-sendto-mail 'org-odt-export-to-odt async subtreep visible-only ext-plist))
    
(defun org-odt-export-to-pdf-and-sendto-mail (&optional async subtreep visible-only ext-plist)
  (interactive)
  (let ((org-odt-preferred-output-format "pdf"))
    (org-odt-export-to-odt-and-sendto-mail async subtreep visible-only ext-plist)))
    
(defun org-html-export-to-html-and-sendto-mail (&optional async subtreep visible-only ext-plist)
  (interactive)
  (org-foo-export-to-foo-and-sendto-mail 'org-html-export-to-html async subtreep visible-only ext-plist))
    
(eval-after-load 'org
 '(progn
    (org-export-define-derived-backend 'odt-mail 'odt
      :menu-entry
      '(?o "Export to ODT"
           ((?m "As ODT file and send mail" org-odt-export-to-odt-and-sendto-mail)
            (?M "As PDF file and send mail" org-odt-export-to-pdf-and-sendto-mail))))
    
    (org-export-define-derived-backend 'html-mail 'html
      :menu-entry
      '(?h "Export to HTML"
           ((?m "As HTML file and send mail" org-html-export-to-html-and-sendto-mail))))))
           
(defun my/thunderbird-compose-mail (&optional recipient subject body attachment)
  (interactive)
  (call-process "thunderbird" nil 0 nil "-compose" (format "to='%s',subject='%s',body='%s',attachment='%s'" recipient subject body attachment)))
```

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/jix6od/comment/gaid3f4) 
**Votes:** 8

I love using `dabbrev-expand` (bound by default to `M-/`) to complete text I know is in one of my buffers. It completes one word at a time, but if you want to keep pulling subsequent words from the location where a completion is found you can insert a space and run `dabbrev-expand` again, so `SPC M-/`.

For example if you have the text "The quick brown fox jumps over the lazy dog" in some buffer (and say no other "qu" appears anywhere in your buffers), then `qu M-/ SPC M-/ SPC M-/` will insert "quick brown fox".

This is already great, but I find the key sequence `SPC M-/` awkward to type so I wrote this little function which I bind to `M-'`:

```elisp
(defun dabbrev-next (arg)
"Insert the next ARG words from where previous expansion was found."
(interactive "p")
(dotimes (_ arg)
(insert " ")
(dabbrev-expand 1)))
        
```
Then for "quick grown fox" I can go `qu M-/ M-' M-'` which feels much easier to type (on my keyboard `/` and `'` are very close to each other).

(`M-'` is bound by default to `abbrev-prefix-mark` which I never use, so I didn't mind rebinding it.)

## u/Amonwilde [🔗](https://www.reddit.com/r/emacs/comments/j61aoh/comment/g7wd5gj) 
**Votes:** 8

For some this will be obvious, but I'm sure there will be at least one person who will find this useful. One of the most amazing features of Emacs to me is dabbrev-expand, by default bound to M-/. 

> Expand previous word "dynamically".

> Expands to the most recent, preceding word for which this is a prefix.
I> ifno suitable preceding word is found, words following point are
considered.  If still no suitable word is found, then look in the
> buffers accepted by the function pointed out by variable

This command is essentially omni-autocomplete. Chances are, the term you're trying to complete is in the buffer you're using or another buffer, and you can hit multiple times to cycle through different completions. I find the expander to be quicker and more deterministic than language autocomplete about 70% of the time. It's especially useful in writing, if you use Emacs for things other than programming, as you can complete proper names and specalized vocabulary quickly.

## u/Timesweeper_00 [🔗](https://www.reddit.com/r/emacs/comments/ijmgtx/comment/g3fdvf7) 
**Votes:** 8

FYI lsp-mode has added support for pyright as a language server, Microsoft is deprecating the c# python language server in favor of pylance (proprietary and closed source), which uses pyright.

## u/aartist111 [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvrw4cu) 
**Votes:** 8

Found / c for M-x ibuffer.  It filters buffers by content.
It looks like  'grep -c' .   Very helpful to locate a file quickly for which you remember any word from content
Until now I had only used filters for filenames or modes only. .

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/gqsz8u/comment/frynpvt) 
**Votes:** 8

Make a little mode called `my-minor-mode` and enable it globally. Use it's keymap for your keybindings, without the prefix.  Then assign that keymap to a prefix. This way, you can disable most of your keybindings easily when needed, you can easily switch your prefix key (e.g. go from `C-c` to `H-x` or `<menu>`, etc.), have those keybindings available on multiple prefixes, and you can easily restore a default keybinding via `(define-key my-minor-mode-map <key> nil)`.  Here is how I define a minor mode for myself:

```elisp
;;; The GK minor mode:
    
;; The GK minor mode is at the heart of this configuration.  Almost
;; all keybindings, except unmapping some keys from the global map,
;; and except bindings in specific modes, should be done with this
;; minor modes keymap.  This minor mode is active everywhere, except
;; the Minibuffer and the Fundamental mode buffers.
    
(defgroup GK nil
  "Group for my configuration."
  :group 'emacs
  :prefix "gk-")
    
(defvar gk-minor-mode-map
  (make-sparse-keymap)
  "Where to put all my bindings.")
    
(defvar gk-minor-mode-prefix-map
  (make-sparse-keymap)
  "Prefix map for my bindings.")
    
(fset 'gk-minor-mode-prefix-map gk-minor-mode-prefix-map)
    
(defvar gk-minor-mode-prefix "\C-c"
  "Keymap prefix for `gk-minor-mode'.")
    
(define-minor-mode gk-minor-mode
  "Global minor mode for customisations.
    
\\{gk-minor-mode-map}"
  nil "" gk-minor-mode-map
  (let ((map gk-minor-mode-map))
    (define-key map gk-minor-mode-prefix #'gk-minor-mode-prefix-map)))
    
(define-globalized-minor-mode global-gk-minor-mode gk-minor-mode
  gk-minor-mode)

```
Then just enable it somewhere. I like to use the following setup for enabling global modes at once:

```elisp
;;;; Global modes:
    
;; This module provides utilities for global modes, like turning them on
;; and off collectively with a single command, registering and
;; unregistering them, disabling default modes etc.
    
;; All the modes listed in =gk-global-modes= are toggled on with an
;; =after-init-hook=, so modifications to this variable that happen up
;; until the execution of the named hook will actually determine which
;; modes are turned on.
    
;; =gk-disabled-modes= is a list of modes to disable.
    
;; Each of this lists contain symbols, actually =*-mode= functions.  The
;; ones in the former will be called with =+1= as the argument, and ones
;; in the latter with =-1=.
    
;; Do not use this as a hook, add to =after-init-hook= instead.
    
(defvar gk-global-modes nil "List of global modes to be enabled.")
(defvar gk-disabled-modes nil "List of disabled global modes.")
    
(defvar gk-toggle-global-modes nil)
(defun gk-toggle-global-modes ()
  "Enable or disable the modes listed in `gk-global-modes' at once."
  (interactive)
  (setf gk-toggle-global-modes (not gk-toggle-global-modes))
  (let (errors)
    ;; Enable global modes
    (dolist (mode gk-global-modes)
      (condition-case e
          (funcall mode (if gk-toggle-global-modes 1 -1))
        (error (push `(,mode ,e) errors))))
    ;; Disable modes in gk-disabled-modes
    (dolist (mode gk-disabled-modes)
      (condition-case e
          (funcall mode (if gk-toggle-global-modes -1 1))
        (error (push `(,mode ,e) errors))))
    (when errors
      (warn "Following errors occurred when activating global modes:\n%S"
            errors))))
    
(add-hook 'after-init-hook 'gk-toggle-global-modes)

```
~~That function needs some updating tho. But it works fine as a hook that sets up the global modes.~~ I put `global-gk-minor-mode` into `gk-global-modes` and it's enabled alongside some other ones.

Edit: fix `gk-toggle-global-modes`.

## u/emacsomancer [🔗](https://www.reddit.com/r/emacs/comments/gqsz8u/comment/fs5sq09) 
**Votes:** 8

Preconfigured Emacs for collaborative writing (using a literate, self-generating init):

https://gitlab.com/emacsomancer/collaborative-writing-environment-emacs

Not a huge, lots-of-packages configuration, but with a focus on writing (org-mode, fountain), including version control (magit). 

Each person gets a different colour to indicate the part of the file they’re editing: (Screenshot (from the alternative world in which Cory Doctorow  co-wrote _For the Win_ in Emacs):)

https://imgur.com/a/zvfLpdH

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqcycvb) 
**Votes:** 8

A rudimentary interface for the fabulous [Links web browser](http://links.twibright.com/user_en.html):

```elisp
(defun links-browser (&optional link new-window)
  (interactive)
  (unless link
    (setq link (read-from-minibuffer "url: ")))
  (make-process
   :name "links-browser"
   :connection-type 'pipe
   :command (list "links" "-g" link)))
    
(defun links-search (&optional query)
  (interactive)
  (unless query
    (setq query (read-from-minibuffer "search query: ")))
  (pcase query
    ((pred (string-match "\\`d .*"))
     (links-search--launch "dict" (substring query 2 nil)))
    ((pred (string-match "\\`b .*"))
     (links-search--launch "book" (substring query 2 nil)))
    ((pred (string-match "\\`w .*"))
     (links-search--launch "wiki" (substring query 2 nil)))
    ((pred (string-match "\\`m .*"))
     (links-search--launch "imdb" (substring query 2 nil)))
    ((pred (string-match "\\`y .*"))
     (links-search--launch "yout" (substring query 2 nil)))
    ((pred (string-match "\\`t .*"))
     (links-search--launch "thes" (substring query 2 nil)))
    ((pred (string-match "\\`s .*"))
     (links-search--launch "syno" (substring query 2 nil)))
    (_ (links-search--launch "seax" query))))
    
(defun links-search--launch (engine query)
    (pcase engine
      ("dict" (links-browser (format "https://en.wiktionary.org/wiki/Special:Search?search=%s" query)))
      ("wiki" (links-browser (format "https://en.wikipedia.org/w/index.php?title=Special:Search&search=%s&go=Go" query)))
      ("imdb" (links-browser (format "https://www.imdb.com/find?s=all&q=%s" query)))
      ("yout" (links-browser (format "https://www.youtube.com/results?search_query=%s" query)))
      ("book" (links-browser (format "http://gen.lib.rus.ec/search.php?req=%s&res=100&sort=id&sortmode=DESC" query)))
      ("thes" (links-browser (format "https://www.powerthesaurus.org/%s" query)))
      ("syno" (links-browser (format "https://duckduckgo.com/lite/?q=%s site:macmillandictionary.com" query)))
      ("seax" (links-browser (format "https://search.snopyta.org/?q=%s" query)))))
```

## u/karthink [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqfc1wi) 
**Votes:** 8

AucTex users: You're missing out if you don't use [CDLatex](https://github.com/cdominik/cdlatex). It's primarily a fast input tool for LaTeX, sort of like snippet templates. The difference between setting up Yasnippet templates for LaTeX and CDLatex is that CDLaTeX's TAB key to jump past stuff is _always available_, not just during snippet entry. It's difficult to explain, so here are some demos:

1. [Fast input with cdlatex and preview.el](https://gfycat.com/heavenlynegligiblehoiho)
2. [Fast input with keys displayed](https://gfycat.com/safeidolizedlangur)

I wrote a longer post explaining [how I set up AucTex](https://www.reddit.com/r/emacs/comments/g8ecpj/advice_for_auclatex_what_keybinds_do_you_find/foo64ge/) recently.

CDLaTeX was written by Carsten Dominik, the author of Org-mode and reftex. Thus Org ships with an `org-cdlatex` minor-mode that makes these features available in org-mode.

## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/gdtqov/comment/fq9186h) 
**Votes:** 8

If you want to switch between two themes, depending on time of day (e.g. a light and dark theme), it's as simple as this:

```elisp
;; Light
(load-theme 'modus-operandi t t)
(run-at-time "05:00" (* 60 60 24) (lambda () (enable-theme 'modus-operandi))))

;; Dark
(load-theme 'modus-vivendi t t)
(run-at-time "21:00" (* 60 60 24) (lambda () (enable-theme 'modus-vivendi))))

```
This selects the correct theme when starting Emacs and automatically switch when the times come.

## u/xu-chunyang [🔗](https://www.reddit.com/r/emacs/comments/fwgpkd/comment/fmochkh) 
**Votes:** 8

Prettify the minibuffer prompt on the default value

```elisp
(setq minibuffer-eldef-shorten-default t)
(minibuffer-electric-default-mode)

```
Then try eval

```elisp
(read-string "Year (default 2019): " nil nil "2019")

```
You will notice "Year (default 2019): " is shorten to "Year [2019]: ", and when
you enter anything, the prompt is shorten further to "Year: ". Next time, you
use the minibuffer api, if you provide the default value, don't forget to format
the prompt using that specfic format.

## u/Desmesura [🔗](https://www.reddit.com/r/emacs/comments/fgahb2/comment/fki3lrc) 
**Votes:** 8

Any Emacs Mode for dealing with Coronavirus?

## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/fipuizk) 
**Votes:** 8

An early WIP of a perspective-like buffer grouper/switcher based on automatically grouping buffers by recursive grouping rules written by the user: https://github.com/alphapapa/buffer-groups.el  Defining the grouping rules looks like this:

```elisp
(setf buffer-groups-groups
      (buffer-groups-defgroups
        (group (dir "~/org")
               (auto-indirect))
        (group (dir buffer-groups-emacs-source-directory))
        (group (auto-special))
        (group (mode-match "*Helm*" (rx bos "helm-")))
        (auto-project)))

```
It's based on code from https://github.com/alphapapa/sbuffer.el, which should hit MELPA soon.

## u/nv-elisp [🔗](https://www.reddit.com/r/emacs/comments/eymwl9/comment/fgjj6i8) 
**Votes:** 8

I use a number of Emacs servers daily: terminal $EDITOR, GUI Emacs, elfeed updater, and one to summon a dedicated org-capture client.
This macro allows running code on a group of servers by name.

```elisp
;;;###autoload
(defmacro my/with-servers (servers &rest body)
  "Evaluate BODY on each server in SERVERS.
If SERVERS is the symbol \all\:, evaluate BODY on all servers."
  (declare (indent defun))
  (let* ((files (directory-files server-socket-dir nil nil t))
         (sockets (cond
                   ((eq 'all servers)
                    (seq-filter (lambda (file)
                                  (not (member file '("." ".."))))
                                files))
                   ((and (listp servers)
                         (seq-every-p #'stringp servers))
                    servers)
                   (t (signal 'wrong-type-error `(((stringp), all) ,servers))))))
    `(let (current-server)
       (condition-case err
           (let ((servers (mapc (lambda (socket)
                                  (setq current-server socket)
                                  (server-eval-at socket '(progn ,@body nil)))
                                ',sockets)))
             (format "evaled on %d servers: %s" (length servers) servers))
         (error (message "%s on server %s" err current-server))))))

```
A couple of examples:

```elisp
(defun my/kill-server (name)
  "Kill emacs server named NAME."
  (eval `(my/with-servers (,name)
           (kill-emacs))))
    
(defun my/kill-other-servers ()
  "Kill other Emacs servers."
  (interactive)
  (eval `(my/with-servers all
           (when (not (equal server-name ,server-name))
             (kill-emacs)))))
    
(defun my/reload-init-on-all-servers ()
  "Reload init file on all Emacs servers."
  (interactive)
  (my/with-servers all
    (load-file "~/.emacs.d/init.el")))
```

## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f82dxoa) 
**Votes:** 8

I just put together this little function that kills a buffer's process if there's no input or output for a given time:

```elisp
(defun kill-buffer-process-after-idle (buffer seconds)
  (let (timer)
    (cl-labels
      ((set-timer ()
         (setq timer (run-at-time seconds nil #'done)))
       (reset-timer (_ _)
         (cancel-timer timer)
         (set-timer))
       (done ()
         (with-current-buffer buffer
           (remove-hook 'before-change-functions #'reset-timer t))
         (kill-process (get-buffer-process buffer))))
      (with-current-buffer buffer
        (add-hook 'before-change-functions #'reset-timer nil t))
      (set-timer))))

```
The context is that I occasionally use Spark throughout the day, but having a Spark shell open keeps a substantial amount of resources reserved, and my co-workers and I are encouraged to close our shells when not using them.  This way, I don't have to bother remembering.

## u/jalihal [🔗](https://www.reddit.com/r/emacs/comments/drw8i3/comment/f6mm087) 
**Votes:** 8

TIL about `indent-rigidly` bound to `C-x TAB`. Very useful when indenting copy-pasted python code at different levels!

## u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8mpo5y) 
**Votes:** 8

Does anyone have an `aspell` setup with multiple dictionaries?

When I try `--extra-dict` option I get an error: `Expected language "en" but got "de"`.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j9pr766) 
**Votes:** 8

[deleted]

## u/khourhin [🔗](https://www.reddit.com/r/emacs/comments/x7zfs2/comment/inp54pm) 
**Votes:** 8

Just discovered 'desktop-environment-mode', really useful, particularly if you are using EXWM and want to get back functional standard keys. [https://github.com/DamienCassou/desktop-environment](https://github.com/DamienCassou/desktop-environment)

Thanks Damien !

## u/T_Verron [🔗](https://www.reddit.com/r/emacs/comments/qlpvgu/comment/hjfbuae) 
**Votes:** 8

(Nothing too fancy, I'm sure a lot of people have a similar thing in their toolbox, but it was useful to me again today.)

When writing elisp packages, the compiler expects that all variables and functions are declared. Declaring variables defined somewhere else is easy, one just needs to \`defvar\` them, but declaring functions should mention the file where it is defined.

Inserting all those forms is tedious, especially if the function comes from a package with several files. But emacs already knows where the function comes from, so we can just ask it.

```elisp
(defun tv/add-declare-function (fun)
  (interactive "a") 
  (let* ((buf (car (find-function-noselect fun))) 
         (name (file-name-base (buffer-file-name buf)))) 
    (insert (format "(declare-function %s "%s")\n" fun name))))

```
Call it with M-x, insert the name of the function you want to declare (with completion), and voilà.

## u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/pt2xws/comment/hdtoivy) 
**Votes:** 8

I've been playing around with Emacs 28's [repeat-mode](https://git.savannah.gnu.org/cgit/emacs.git/commit/lisp?id=12409c9064c386a496dcbdca76b790108f6c1cad) a bit.  It allows for not having to press modifiers when executing several conceptually similar actions in a row.  Sadly, the ergonomics of defining these repeat maps are not quite there yet, so I wrote a small macro (my first one ever, actually!) to define the map and set the appropriate symbol property for the function:

```elisp
(defmacro defrepeatmap (symbol &optional pairs docstring)
  "A macro for defining `repeat-map's.
Defines a new repeat-map called SYMBOL with the given DOCSTRING.
The keys are derived via the list PAIRS, whose elements are cons
cells of the form (KEY . DEF), where KEY and DEF must fulfill the
same requirements as if given to `define-key'."
  `(progn
     (defvar ,symbol
       (let ((map (make-sparse-keymap)))
         (--each ,pairs (define-key map (car it) (cdr it)))
         map)
       ,docstring)
     ;; Tell the keys they are in a repeat map.
     (--each (mapcar 'cdr (cdr ,symbol))
       (put it 'repeat-map ',symbol))))
       
```
It can be used like 

```elisp
(defrepeatmap window-repeat-map
  '(("}" . enlarge-window-horizontally)
    ("{" . shrink-window-horizontally )
    ("+" . balance-windows            )
    ("o" . other-window               )
    ("1" . delete-other-windows       )
    ("2" . split-window-below         )
    ("3" . split-window-right         )
    ("0" . delete-window              )
    ("s" . window-swap-states         )
    ("f" . project-find-file          ))
  "Keymap to repeat window key sequences.  Used in `repeat-mode'.")
```

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/pk6akd/comment/hc3bikc) 
**Votes:** 8

I'm using [orderless](https://github.com/oantolin/orderless) for completion but I also want to walk through files with initials only, to do something like [like this](https://imgur.com/a/CJg8MGw) for example. It only requires to ignore the case and ask the minibuffer to use initials. 

```elisp
(use-package orderless
      :ensure t
      :custom
      (completion-styles '(orderless))
      (completion-category-defaults nil)
      (read-file-name-completion-ignore-case t)
      (completion-category-overrides '((file (styles partial-completion))
    				   (minibuffer (initials)))))
```

## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/oxo1xh/comment/h85cv7f) 
**Votes:** 8

Little quality of life improvement if you work with multiple eshell buffers:

```elisp
(defun eshell-buffer-name ()
  (rename-buffer (concat "*eshell*<" (eshell/pwd) ">") t))
    
(add-hook 'eshell-directory-change-hook #'eshell-buffer-name)
(add-hook 'eshell-prompt-load-hook #'eshell-buffer-name)
```

## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/mb8u1m/comment/gs55kqw) 
**Votes:** 8

I use EXWM mode, so I can use Emacs' excellent window managing functionality universally with my standard applications like Firefox.

Firefox, like most web browsers nowadays, has tabs. But tabs are vastly inferior to Emacs' built in buffer management, and I don't think the abstraction belongs at the application level, instead it should be implemented once universally so it can be used with all applications. I think tabs became popular because window managers failed at effectively managing a large number of browser windows.

I use Firefox with the tab bar hidden, and with an extension which will open every new tab in a new window automatically. Now I can use my buffer switching commands like usual, across the 100s of Firefox windows I have, and it works like a breeze. Whatever improvements I add to help me manage buffers, automatically apply to Firefox. For example I recently enabled `midnight-mode` to clean up old buffers that I haven't visited in a few days, and I now have this automatically for Firefox too (I'm bad at manually closing webpages once I open them).

Now, onto my tip/trick. I wanted ibuffer to display the URL of each Firefox window as the file name. So I can search by the window name, or by the URL, while keeping them separated. The file name seems appropriate here as it would otherwise be empty.

To do this, I found an extension that adds the current URL to Firefox's title (I used https://addons.mozilla.org/en-US/firefox/addon/keepass-helper-url-in-title/, but any would work), I then wrote the following function:

```elisp
(defun b3n-exwm-set-buffer-name ()
  (if (and exwm-title (string-match "\\`http[^ ]+" exwm-title))
    (let ((url (match-string 0 exwm-title)))
      (setq-local buffer-file-name url)
      (setq-local exwm-title (replace-regexp-in-string
                              (concat (regexp-quote url) " - ")
                              ""
                              exwm-title))))

  (setq-local exwm-title
              (concat
               exwm-class-name
               "<"
               (if (<= (length exwm-title) 50)
                   exwm-title
                 (concat (substring exwm-title 0 50) "…"))
               ">"))

  (exwm-workspace-rename-buffer exwm-title))

```
I then added this function to the `exwm-update-class-hook` and `exwm-update-title-hook` hooks.

Now, in ibuffer, it looks like this:

```elisp
  Firefox<Weekly tips/trick/etc/ thread : emacs — Mozilla Fi…> https://www.reddit.com/r/emacs/comments/mb8u1m/weekly_tipstricketc_thread/

```
With the buffer name on the left, and the file name (URL) on the right. Perfect :)

I will make some more improvements in the coming days, so if I split the window with `C-x 3` it duplicates the current window, so it works similar to a regular Emacs buffer and I can scroll to different points on the same page. EXWM should make this easy enough with simulation keys.

## u/zackallison [🔗](https://www.reddit.com/r/emacs/comments/ki09cm/comment/ggoehoo) 
**Votes:** 8

I posted this in the emacsclient thread, but I think it deserves to live here as well:


The emacsclient / server system is great.  If you have it listening on tcp and port forward that when connecting to remote machine it adds another level of power.

I use [emacs-vterm](https://github.com/akermu/emacs-libvterm) for a terminal inside emacs, so I've got a lot of commands remapped.  Like `man` runs `emacsclient ... man ..`, which opens the man page in the "other" buffer, so it doesn't interrupt my flow.  I use "scroll-other-window" to navigate the man page while I still have my prompt. `magit`, `dired`, and others map to their `emacsclient` equivalents.

I've written wrapper script for e/emacs client that I've come to call `e`, because it saves keystrokes

#### [The full repo is here e-emacs.sh](https://gitlab.com/zackallison/e-emacs/)

It does the standard things you would expect, starts emacs if it's not already started, open a file in a new buffer / window / terminal and optionally wait for you to finish or have the shell continue.

Then I added some functions that I found useful, starting with piping results from a command into an emacs buffer, such as `find . -name foo\* | e`.  Naturally after that was piping from a buffer to a command: `e [file] | rot13` super secure encryption.  And of course piping into and out of a buffer works as well `find . -name incriminating-evidence\* | e | xargs rm`, so you can verify / tweak the results before passing them through.  Maybe you want to leave the evidence on Two Time Tommy.  Who knows.

The other nice feature is the ability to use templates.  For example to edit a HTTP request and then send it to a server you can do that: `e -t header_template.txt | nc www.example.com 80`  The template file is copied to a temp file which is the one that is edited.

Or the poor man's blog system: `e -t header_template.html body_template.html footer.html > new_page.html`

See the repo for more examples and to download.  The notes of getting it working on remote machines aren't the cleanest.  If you have any suggestions or features that would make your life easier let me know; submit an issue or comment here.

#### [The full repo is here e-emacs.sh](https://gitlab.com/zackallison/e-emacs/)

~~I really should post this on one of the share your stuff posts.  But I get distracted.~~ There, I did it.

In case you can't tell I *really* like emacs and it's client server model.

## u/adt7799 [🔗](https://www.reddit.com/r/emacs/comments/ja97xs/comment/g8op875) 
**Votes:** 8

I find this very useful.

When I have multiple buffers opened and I switch from another program to emacs I always get confused about which buffer the cursor is in. So I created a mapping to

`(global-set-key (kbd "M-l") 'beacon-blink)`

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/fit32vh) 
**Votes:** 8

I just learned yesterday that a shell can pass commands to its surrounding Vterm via escape codes.  I now have this Fish function defined for opening files for editing from the shell:

```elisp
function e --description 'Edit in Emacs'
    if [ "$INSIDE_EMACS" = "vterm" ]
        printf '\e]51;E%s\e\\' "find-file $argv"
    else
       emacsclient -c -a '' $argv &
    end
end

```
Basically, if I am already inside Emacs (Fish shell in a Vterm), `e filename` will open a file for editing in a window split next to my Vterm. If I'm not in Emacs (separate terminal), it will instead run `emacsclient` to edit the file in a new Emacs frame.

## u/itistheblurstoftimes [🔗](https://www.reddit.com/r/emacs/comments/el8oq7/comment/fdh3fyn) 
**Votes:** 8

I had previously written a function to replace org-beginning-of-line to behave this way, but later found `org-special-ctrl-a/e` which does exactly what I wanted for C-a (and C-e). The default behavior drove me crazy and I didn't know there was a built-in fix. 

```elisp
 "Non-nil means `C-a' and `C-e' behave specially in headlines and items. When t, `C-a' will bring back the cursor to the beginning of the headline text, i.e. after the stars and after a possible TODO keyword.  In an item, this will be the position after bullet and check-box, if any.  When the cursor is already at that position,another `C-a' will bring it to the beginning of the line.`C-e' will jump to the end of the headline, ignoring the presence of tags in the headline.  A second `C-e' will then jump to the true end of the line, after any tags.  This also means that, when this variable is non-nil, `C-e' also will never jump beyond the end of the heading of a folded section, i.e. not after the ellipses.When set to the symbol `reversed', the first `C-a' or `C-e' works normally, going to the true line boundary first.  Only a directly following, identical keypress will bring the cursor to the special positions.This may also be a cons cell where the behavior for `C-a' and`C-e' is set separately.
    
"When t, `C-a' will bring back the cursor to the beginning of the headline text, i.e. after the stars and after a possible TODO keyword.  In an item, this will be the position after bullet and check-box, if any.  When the cursor is already at that position,another `C-a' will bring it to the beginning of the line.`C-e' will jump to the end of the headline, ignoring the presence of tags in the headline.  A second `C-e' will then jump to the true end of the line, after any tags.  This also means that, when this variable is non-nil, `C-e' also will never jump beyond the end of the heading of a folded section, i.e. not after the ellipses.When set to the symbol `reversed', the first `C-a' or `C-e' works normally, going to the true line boundary first.  Only a directly following, identical keypress will bring the cursor to the special positions. This may also be a cons cell where the behavior for `C-a' and `C-e' is set separately."
    
When t, `C-a' will bring back the cursor to the beginning of the headline text, i.e. after the stars and after a possible TODO keyword.  In an item, this will be the position after bullet and check-box, if any.  When the cursor is already at that position,another `C-a' will bring it to the beginning of the line.
    
`C-e' will jump to the end of the headline, ignoring the presence of tags in the headline.  A second `C-e' will then jump to the true end of the line, after any tags.  This also means that, when this variable is non-nil, `C-e' also will never jump beyond the end of the heading of a folded section, i.e. not after the ellipses.
    
When set to the symbol `reversed', the first `C-a' or `C-e' works normally, going to the true line boundary first.  Only a directly following, identical keypress will bring the cursor to the special positions. This may also be a cons cell where the behavior for `C-a' and `C-e' is set separately."
```

## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/ei02j1/comment/fcu8ksy) 
**Votes:** 8

Side note: thank you for this weekly threads, I don't always read them but when I feel the need, I'm always happy to. Cheers

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/e8nv40/comment/fadilfw) 
**Votes:** 8

I switched to exwm two days ago (even though I was satisfied with dwm) and I'm liking it so far because it's so good to have a consistent workflow environment.

Initially I started with using workspaces and managing X applications to automatically start in different workspaces but now I'm using a single workspace with winner mode and save window configuration (when the view I want is a few undos away).

I had to convert a few bash scripts I regularly used to elisp but that was easy and fun.

Here's one I wrote for youtube-dl, which I can be run from qutebrowser with a keybinding in config.py. You can choose your desired format using ivy (best format by default, or a combination of formats can be specified with "video+audio" numbers manually).  You have to supply it a link from qutebrowser or another script, otherwise it uses the link in your clipboard.

In qutebrowser:

	config.bind(',y', 'hint links spawn emacsclient -n -e "(ivy-youtube-dl \\"{hint-url}\\")"')
	config.bind(',Y', 'spawn emacsclient -n -e "(ivy-youtube-dl \\"{url}\\")"')

In emacs:

	(defun ivy-youtube-dl (&optional link)
	  (interactive)
	  (let ((link (or link (current-kill 0)))
		(buffer (generate-new-buffer "*ytd-formats*")))
	    (make-process :name "ytd-formats"
			  :buffer buffer
			  :command (list "youtube-dl" "--list-formats" link)
			  :connection-type 'pipe
			  :sentinel `(lambda (p e)
				       (set-buffer ',buffer)
				       (goto-char (point-min))
				       (unless (search-forward "format code" nil t)
					 (kill-buffer)
					 (error "url not supported"))
				       (forward-line 1)
				       (let (list)
					 (while (not (eobp))
					   (setq list (cons
						       (split-string
							(buffer-substring-no-properties
							 (point)
							 (point-at-eol)) "\n" t nil)
						       list))
					   (forward-line 1))
					 (setq list (nreverse list))
					 (kill-buffer "*ytd-formats*")
					 (ivy-read "youtube-dl formats (vid+aud): " list
						   :action (lambda (x)
							     (youtube-dl
							      (substring-no-properties
							       (format "%s" x)
							       (if (string-match "(" (format "%s" x))
								   (match-end 0)
								 nil)
							       (string-match "[[:space:]]" (format "%s" x))) ',link))
						   :sort nil
						   :re-builder #'regexp-quote
						   :preselect "best"))))))

	(defun youtube-dl (fmt link)
	  (let ((buffer (generate-new-buffer "*youtube-dl*")))
	    (with-current-buffer buffer
	      (ansi-color-for-comint-mode-on)
	      (comint-mode))
	    (make-process :name "youtube-dl"
			  :buffer buffer
			  :command (list "youtube-dl" "--flat-playlist" "--format" fmt link)
			  :connection-type 'pty
			  :filter 'comint-output-filter
			  :sentinal (lambda (p e) (message "Process %s %s" p (replace-regexp-in-string "\n\\'" "" e))))))


And this one for downloading files with aria2c (which can download torrents and magnet links in addition to regular files). This function takes a link from another function otherwise uses your clipboard. Can be easily bound with qutebrowser like the above function.

	(defun download-with-aria2c (&optional link)
	  (interactive)
	  (let ((link (or link (current-kill 0)))
		(buffer (generate-new-buffer "*aria2c*")))
	    (with-current-buffer buffer
	      (ansi-color-for-comint-mode-on)
	      (comint-mode))
	    (make-process :name "aria2c"
			  :connection-type 'pty
			  :buffer buffer
			  :command (list "aria2c" link)
			  :filter 'comint-output-filter
			  :sentinel (lambda (p e)
				      (make-process :name "notify"
						    :connection-type 'pipe
						    :command (list "notify-send" (format "%s %s" p e) "download complete"))
				      (message "Process %s %s" p (replace-regexp-in-string "\n\\'" "" e))))))

## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f81qch6) 
**Votes:** 8

I searched for a convenient way to read multiple file names interactively. With helm and ivy you can mark files and afterwards act on them but what about convenient navigation and marking tools you know from dired to choose the file names you want to act on?

Recursive edit to the rescue:

```elisp
(defun read-file-names-with-dired+ (&optional directory)
  (save-window-excursion
    (dired directory)
    (recursive-edit)
    (dired-get-marked-files)))


```
With this you can do `(setq files (read-file-names-with-dired+))` mark the files you are interested in and finish with `C-M-c` which exits the recursive edit.

## u/vjgoh [🔗](https://www.reddit.com/r/emacs/comments/18149ql/comment/kahspwz) 
**Votes:** 8

I used to have a problem where eglot would decide that many mid-hierarchy directories were the project root and spin up a separate instance of clangd for each one (sometimes 10 or 12 total). This was almost certainly due to using emacs' built-in `project` to handle project discovery. At that point, I switched to lsp-mode because I generally find `project` to be impenetrable and poorly documented compared to projectile.

I was forced to go back to eglot, however, because lsp-mode has been failing to parse things well for a while.

Long story short, here's how you force `project` to find the actual project root if the automatic detection doesn't work. With this, eglot started working great, didn't spin up 10 instances of clangd, and has generally been ticking over just fine.

`(setq project-vc-extra-root-markers '(".project.el" ".projectile" ".dir-locals.el"))`

## u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/pfpgm9/comment/hb765zp) 
**Votes:** 8

This is a very simple function, but it has saved me from countless of "do I have to do _this_ again now?" moments.

When editing LaTeX files I often find myself wanting to convert inline math to display math, in order for equations to "pop out" more.  I could not find anything already implemented, so I wrote something that does this whenever the point is inside an inline math (`$`-based, sorry `\( \)` gang) environment: 

```elisp
(defun slot/inline-to-display-math ()
  "Transform inline math to display math."
  (interactive)
  (when (and (texmathp) (equal "$" (car texmathp-why))) ; inline math?
    (let* ((beg   (save-excursion (search-backward "$")))
           (end-$ (search-forward "$"))
           (end   (if (-contains? '(?. ?,) (char-after end-$))
                      (1+ end-$)    ; put punctuation into display-maths
                    end-$)))
      (kill-region beg end)
      ;; insert display math
      (TeX-newline)
      (insert "\\[")
      (TeX-newline)
      (insert-for-yank (string-replace "$" "" (current-kill 0)))
      (TeX-newline)
      (insert "\\]")
      (TeX-newline))))

```
For example, this would transform 

```elisp
The cowedge $\iota \colon P \xRightarrow{\; .. \;} C$ is easily seen to be unique up to unique isomorphism.

```
into 

```elisp
The cowedge
\[
  \iota \colon P \xRightarrow{\; .. \;} C
\]
is easily seen to be unique up to unique isomorphism.

```
I'm a bit surprised by my not finding any function to already insert some string into the simple `\[ \]`-based display math; everything I could find just inserted dollars instead.  I suppose one could insert a `displaymath` environment, but I've never like the look of that.  Oh well.

## u/jumpUpHigh [🔗](https://www.reddit.com/r/emacs/comments/nlefvx/comment/gzjal45) 
**Votes:** 8

Considering the ongoing freenode to librea.chat movement, I tried to use erc for the *n*th time to connect to the debian channel on oftc. I want to authenticate automatically but it doesn't happen. I still need to use `/msg NickServ IDENTIFY mypass`. Can you tell me what to do?



```elisp
 (use-package erc
   :custom
   (erc-autojoin-channels-alist '(("OFTC" "#debian" )))
   (erc-prompt-for-nickserv-password nil)
   (erc-prompt-for-password nil)

   :config
   (add-to-list 'erc-modules 'services)
   (erc-update-modules)
   (erc-autojoin-enable)
   (defun erc-start()
     "Start ERC."
     (interactive)
     (erc :server "irc.oftc.net" :nick "mynick"))
 ) 

```
and my `~/.authinfo` file has an entry

```elisp
 machine irc.oftc.net login "mynick" password "mypass"

```
Edit: Using GNU Emacs 27.1

## u/StrangeAstronomer [🔗](https://www.reddit.com/r/emacs/comments/kqsw1k/comment/gi8tvp8) 
**Votes:** 8

Here's a really simple one, but I find it invaluable.

It took me about 30 years to realise that using C-u with `buffer-menu` (C-x C-b) doesn't show 'special' buffers like `*Messages*`. Less clutter when I want to switch to a file, which is most of the time.

It then took me a few more years to realise that that's my preferred mode, so I created this simple thing to invert the sense - now C-x C-b only shows me files, and I prefix that with C-u to show special buffers too. Sounds stupid and trivial but it floats my boat.

```elisp
(global-set-key (kbd "C-x C-b")        '(lambda (&optional arg)
                                          "runs buffer-menu but with the sense of C-u inverted (ie files-only unless C-u is given)"
                                          (interactive "P")
                                          (setq arg (not arg))
                                          (buffer-menu arg)))
```

## u/martinslot [🔗](https://www.reddit.com/r/emacs/comments/kdgv43/comment/gfwlm9q) 
**Votes:** 8

I need to try to do something custom to eshell so it feels more like home: http://www.modernemacs.com/post/custom-eshell/. Also set som aliases up.

How does your eshell look like?

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/kdgv43/comment/gfxbwgy) 
**Votes:** 8

I tried [marginalia](https://github.com/minad/marginalia) with light annotation and selectrum, it works well. It displays commands' keybindings in the minibuffer. It's especially useful for modes I don't use daily and for which I haven't memorized the keybindings.

## u/??? [🔗](https://www.reddit.com/r/emacs/comments/gmkg4g/comment/fr9whmq) 
**Votes:** 8

[deleted]

## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/g11mp9/comment/fnjep17) 
**Votes:** 8

If you call `occur` with a numeric prefix argument it provides lines of context around each match: if the number is positive it provides that many lines before and after each match, if it is negative, it provides that many lines before each match (in absolute value).

And if you call `occur` with the universal prefix argument, `C-u`, it does something slightly different: it prompts you for a regexp as usual, but instead of collecting entire lines containing matches for the regexp, it collects either matches of the regexp (if it contains no captures), or it prompts you for a replace-like string to format each regexp match (where you can use the capture groups).

For example, I can easily get a list of the packages I configure in my init file with:

```elisp
C-u M-s o (use-package \(\S-+\) RET \1 RET

```
The `\1` is even there by default, so really only `RET` is needed in this case.

**EDIT**: I previously had the regexp `(use-package \(\<.*?\>\)`, which as u/Bodertz pointed out, doesn't do what I want.

## u/SlowValue [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/fis4vb9) 
**Votes:** 8

Setting up **org-mode LaTeX export configuration** will be much easier, if you include a `*.tex` file with your settings. like so:

```elisp
(setq org-latex-classes '(("mydocclass"
                           "\\input{my-org-template.tex}"                ;; <---- HERE --
                           ("\\section{%s}" . "\\section*{%s}")
                           ("\\subsection{%s}" . "\\subsection*{%s}")
                           ("\\subsubsection{%s}" . "\\subsubsection*{%s}")
                           ("\\paragraph{%s}" . "\\paragraph*{%s}")
                           ("\\subparagraph{%s}" . "\\subparagraph*{%s}"))))
    
```
The `my-org-template.tex` has to be located where LaTeX can find it. (i.e in directory `$HOME/texmf/tex/`)

Content of the file can be all things you normaly have to define before the `\begin{document}` statement in a tex document.

Creating a nice LaTeX export theme that way, is much more convenient, debugable and robust.

I hope to see some cool org-LaTeX-export themes popping up. Imagine custom headers and footers with logos and stuff. ;)

## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/erro41/comment/ff5q5px) 
**Votes:** 8

Org mode is amazing. I was looking for a way to move an item from a plain list to another heading in the same file. Here's what I wrote, it's working well for me. Requires the ivy package.

```elisp
(defun org-refile-list-item ()
  (interactive)
  (unless (org-in-item-p)
    (error "not in item"))
  (ivy-read
   "move to heading: "
   (org-map-entries
    (lambda ()
      (nth 4 (org-heading-components))))
   :history 'org-headings
   :action
   (lambda (x)
     (save-excursion
       (org-beginning-of-item)
       (let ((struct (org-list-struct)))
         (kill-region
          (point)(org-list-get-item-end-before-blank (point) struct))
         (delete-region
          (point)(progn (forward-visible-line 1)(point)))
         (org-list-write-struct
          (org-list-struct)(org-list-parents-alist struct)))
       (goto-char (point-min))
       (search-forward x)
       (org-end-of-subtree)
       (newline)
       (yank)
       (org-beginning-of-item)
       (let ((struct (org-list-struct)))
         (org-list-write-struct struct (org-list-parents-alist struct)))))))
```