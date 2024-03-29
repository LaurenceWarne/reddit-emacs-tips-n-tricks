
## u/gausby [🔗](https://www.reddit.com/r/emacs/comments/e5dzv6/comment/f9jp341)
**Votes:** 40

Shouldn't it be `/etc/tips/trick` ?

(…I'll show myself out)


## u/jalihal [🔗](https://www.reddit.com/r/emacs/comments/d8k4ce/comment/f1b3a2f)
**Votes:** 39

I just discovered calc mode. Oh my ...
It does symbolic differentiation, solves linear equations, plots with gnuplot, and does matrix operations, all with a few key strokes!! How is this amazing tool not as popular as org mode or Magit?? I am so excited to explore it more, especially for the "display" options. I can see it taking my latex experience to the next level. 
Any tips on using calc mode?


## u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h2rdkkz)
**Votes:** 37

Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.


## u/celeritasCelery [🔗](https://www.reddit.com/r/emacs/comments/gienra/comment/fqe6yop)
**Votes:** 37

> I had received the advice to not install anything and just start with plain emacs so I can learn emacs. ... it was miserable. 
> Enter Doom Emacs. I decided to give Doom Emacs a try because it was also highly recommended in my initial RFC, especially since it is designed for Vim users. In short I love it.

This is why I disagree with the subreddits de facto advice to “learn vanilla first”. People who have used emacs for a long time don’t realize how much **time** it takes to get it to level of a normal modern editor people are used to. I recommend distros to *everyone* who is new unless they are that certain personality type that wants to do everything themselves.


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/10qo7vb/comment/j6rmvvf)
**Votes:** 33

When you have an active region, `undo` will only undo changes in that region instead of the whole file.


## u/zupatol [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/iodig8c)
**Votes:** 29

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


## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/8xu2zt/comment/e25rqqn)
**Votes:** 26

There are basically two paradigms you can use with Org:

1.  Flat files.  Every heading is a top-level heading.  Use tags, properties, etc. on each one.
2.  Hierarchy.  e.g. datetrees, or manually organized trees.  Tags and properties can apply to entire subtrees.

The issue, as you know, is that cataloging data is time-consuming (just ask a librarian, I guess).  It's easy to spend more time organizing data than inputting or reading it.

So I recommend a hybrid, so to speak.  

*  Use manual hierarchies where it makes sense for you.  e.g. I have a reference file that I put information I want to keep long-term, with hierarchies like Computers/Software/Emacs/Tips.
*  Use flat files where it makes sense.  I have an inbox.org file that's just a long list of top-level headings (that I will supposedly refile to their proper location someday, but mostly I just search the file).
*  Use datetrees where it makes sense.  I have a log.org file that acts as a journal or logbook.  A capture template automatically puts entries in it in the proper datetree hierarchy.

Then, use the variety of tools available to help you find what you need:

* https://github.com/alphapapa/helm-org-rifle
* Org Agenda commands, sparse trees, etc. (use the dispatcher, and see the manual)

Perhaps the best advice is to not get overwhelmed.  Org is huge, with a lot of built-in tools.  It's also a foundation for building your own tools that meet your needs.  Take your time, learn as you go, and over time your tools will become more powerful and useful.  Don't worry about getting everything just right before starting to use the system, because it just doesn't work that way (and that's a good thing, because if it did, it would mean that it was inflexible and limited).  And (theoretically, at least) you can always write some code to retrofit your earlier data into whatever new system you come up with.  :)

Have fun!


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeu5gyb)
**Votes:** 23

Ever had to fix your broken config in a bare bones Emacs? I adopted this from [abo-abo](https://github.com/abo-abo) and bound it to `C-x C-c`:

```elisp
(defun test-emacs ()
  "Test if emacs starts correctly."
  (interactive)
  (if (eq last-command this-command)
      (save-buffers-kill-terminal)
    (require 'async)
    (async-start
     (lambda () (shell-command-to-string
                 "emacs --batch --eval \"
(condition-case e
    (progn
      (load \\\"~/.emacs.d/init.el\\\")
      (message \\\"-OK-\\\"))
  (error
   (message \\\"ERROR!\\\")
   (signal (car e) (cdr e))))\""))
     `(lambda (output)
        (if (string-match "-OK-" output)
            (when ,(called-interactively-p 'any)
              (message "All is well"))
          (switch-to-buffer-other-window "*startup error*")
          (delete-region (point-min) (point-max))
          (insert output)
          (search-backward "ERROR!"))))))

```
This makes sure I never exit when the config is broken, so I can fix it with my full featured Emacs setup. First `C-x C-c` will show if everything is right, second `C-x C-c` will exit.


## u/Gangsir [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hepqmq1)
**Votes:** 22

back-to-indentation. Before I found this function I would always do some awkward triple key combo like C-a M-f M-b.

It's just bound to M-m. Jumps you right to the first non-white space character on the line. What's even spicier is that it works in reverse too - if you're at the front of the line it jumps you forward, if you're at the end or middle it jumps backward.

It still works even on lines that aren't indented, same as C-a in that case.

So useful, especially for resetting point during macros that need to start at the first char on the line.


## u/fogbugz [🔗](https://www.reddit.com/r/emacs/comments/9ajxqj/comment/e4w71hl)
**Votes:** 21

I do something similar. But for me a big epiphany was to realize all my organization files and all my wiki files should live in the same directory to allow for simple hyperlinks across them and a beautiful flat structure.

The only difference is that I don't version control some of my organization files. E.g. my tasks. But that's a .gitignore detail.

I prefer all my tasks to live inside a single org file, instead of letting my agenda view harvest them from multiple files. I keep a separate calendar file with fixed events and holidays.

A second epiphany was to realize the best way to manage my tasks is not the much hyped GTD, but a JIT approach: a kanban board. I've set up my agenda view to mimic a kanban. GTD has some nice ideas: keeping a few or ideally one inbox and differentiating between tasks, tasks with deadlines, tasks with scheduled times and events.

But it does have a big flaw, for my personal lifestyle and for many creative people. It doesn't control flow. It leads to creating way too many tasks, no prioritization, and task lists become outdated frequently. This requires a lot of wasteful updating.

In my case, I split my life into a few key areas, and I limit the number of ongoing tasks for each area. I've also gotten rid of GTD contexts. I'm content with states: TODO, PROG (progress), WAIT (blocked) and DONE.

Lastly, I do really believe organization systems are only half-baked if they don't integrate into a knowledge system. Why? Because some tasks are not actionable. They are just knowledge bits you want to store and reuse in the future. Here they explain it better than I do: https://praxis.fortelabs.co/gtd-x-pkm-8ff720ef6939/

I believe in keeping stuff simple. And learning from Japanese production systems, which got lots of things right and which we seem to be reinventing in bad ways. 


## u/meekale [🔗](https://www.reddit.com/r/emacs/comments/auwzjr/comment/ehdnzzd)
**Votes:** 21

Automatic `chmod +x` when you save a file that starts with a `#!` shebang:

```elisp
(add-hook 'after-save-hook 'executable-make-buffer-file-executable-if-script-p)
```


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/aoqcyl/comment/eg35bxe)
**Votes:** 20

This week someone asked to show commit messages along the file listing in dired, similar to the repo view on github. I hacked together a snippet which does this, if there is interest I will improve it further and make a package for it. Here is the [gist](https://gist.github.com/clemera/9c27bc8a003ef404182bf8d0f7bc00a0). After evaluating press ")" in a dired buffer of a git project to show the commit messages.


## u/howardthegeek [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/ioeh1ly)
**Votes:** 20

I just learned that in eshell, $$ is replaced with the output from the last command.


## u/thehaas [🔗](https://www.reddit.com/r/emacs/comments/e5dzv6/comment/f9k6yyf)
**Votes:** 20

After using Emacs for maybe 10 years I finally started using registers and I really should have started earlier. For those who don't know:

&#x200B;

* Highlight text and C-x r s <char> to save to register <char>
* C-x r i <char> to put the contents of the register at the cursor point. The text is still there -- use it over and over again

It seems like quite a few keystrokes but it's really not. Of course you can re-assign them to other keys if you don't like the defaults.


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8m9rlj)
**Votes:** 19

With an active region, you can freely toggle between rectangle mark mode and normal, you don't need to get rid of your active region to switch between the two.


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/cdu7cd/comment/eu29pqs)
**Votes:** 19

I see a bunch of people using the "old" functions for keyboard macros, but I find the "new" ones more convenient so I thought I'd  advertise them a bit here. I won't list all the things these functions do, so consult the help to get the full story.

The older functions are these:

- `kmacro-start-macro` (`C-x (`): starts recording.
- `kmacro-end-macro` (`C-x )`): stops recording, and with a numeric argument it will also run the macro that number of times (the recording counts as the first run).
- `kmacro-end-and-call-macro` (`C-x e`): executes the last keyboard macro; a positive numeric argument indicates the number of repetitions, an argument of zero runs until an error occurs.
- `kmacro-insert-counter` (`C-x C-k C-i`): each keyboard macro gets its own counter, this function inserts the current value of the counter and increments it by one.
- `kmacro-set-counter` (`C-x C-k C-c`): the macro counter start at zero, this command sets it to the numeric argument --useful to start numbering at 1, for example.

With those commands, for example you can produce:

```elisp
Hello, #1!
Hello, #2!
Hello, #3!
Hello, #4!
Hello, #5!

```
by typing `C-1 C-x C-k C-c C-x ( Hello, # C-x C-k C-i ! RET C-5 C-x )`.

OK, now for the good stuff: all of that functionality can be achieved using just these two newer functions:

- `kmacro-start-macro-or-insert-counter` (`<f3>`): start recording if used outside a macro, and if you pass a numeric argument when you start recording, it sets the initial value of the counter. When used while already recording, it will insert the counter and increment it by one. So this command subsumes  `kmacro-start-macro`,  `kmacro-insert-counter`, and  `kmacro-set-counter`.
- `kmacro-end-or-call-macro` (`<f4>`): stops recording, optionally running the macro as many times as indicated by the numeric argument. When used while _not_ recording, it just runs the last keyboard macro. So this command subsumes `kmacro-end-macro` and `kmacro-end-and-call-macro`.

The example given above becomes `C-1 <f3> Hello, # <f3> ! RET C-5 <f4>`, much smoother!


## u/mogigoma [🔗](https://www.reddit.com/r/emacs/comments/8jaflq/comment/dyybwt5)
**Votes:** 19

Every time I see this package I think to myself "People exit Emacs?"


## u/AndreaSomePostfix [🔗](https://www.reddit.com/r/emacs/comments/12cd23k/comment/jf167qh)
**Votes:** 19

org-mode is amazing!

I discovered \`org-copy-visible\` the other day, when I wanted to send somebody only the outline of my notes.

That function (which is bound to C-c C-x v by default) let you copy just the outline for the selected region: very useful!


## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/p6mwx2/comment/h9e6uqq)
**Votes:** 18

Here's a popular Emacs config I just rediscovered.  Some cool stuff here.  https://github.com/angrybacon/dotemacs


## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/v2by7z/comment/iauyzbl)
**Votes:** 18

It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/).  There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.


## u/TeMPOraL_PL [🔗](https://www.reddit.com/r/emacs/comments/txh85s/comment/i3ov7vq)
**Votes:** 18

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


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/x27yc9/comment/imi3kzz)
**Votes:** 18

Update from a couple of weeks ago: after some grinding, I've set the parsing of past comments from this thread to auto update on a weekly basis here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

I've also fixed the broken highlighting of some code snippets, and hopefully parsed all past threads.  There's 200+ comments there (sorted by upvotes), so ctrl-f ing e.g. \`magit\` may help you if you're looking for something specific


## u/dakra [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeu2f6c)
**Votes:** 17

Sometimes I want to get info about my IP address (e.g. if VPN is
connected or not) or some other IP.

This function uses `request` to display infos from `ipinfo.io`:

```elisp
(defun ipinfo (ip)
  "Return ip info from ipinfo.io for IP."
  (interactive "sEnter IP to query (blank for own IP): ")
  (request
   (concat "https://ipinfo.io/" ip)
   :headers '(("User-Agent" . "Emacs ipinfo.io Client")
              ("Accept" . "application/json")
              ("Content-Type" . "application/json;charset=utf-8"))
   :parser 'json-read
   :success (cl-function
             (lambda (&key data &allow-other-keys)
               (message
                (mapconcat
                 (lambda (e)
                   (format "%10s: %s" (capitalize (symbol-name (car e))) (cdr e)))
                 data "\n"))))
   :error (cl-function (lambda (&rest args &key error-thrown &allow-other-keys)
                         (message "Can't receive ipinfo. Error %S " error-thrown)))))

```
As I like to keep my own init.el clean with use-package
[here](https://github.com/dakra/ipinfo.el) is the same function
packaged.


## u/surelynotmymainacc [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efte8t7)
**Votes:** 17

Not much of as trick, but something helped me as a newbie, which must be very obvious to many. When you want to see how to correctly configure a package or use a function in elisp, and when documentation is not helping you, go to GitHub and search like "use-package package-name", switch over to the code tab and change language to Emacs lisp. This will give you hundreds of examples.


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/bz9rxn/comment/eqrf693)
**Votes:** 17

Make C-j in Lisp Interaction mode produces this:

```elisp
(+ 1 2 3)
;; => 6

```
code:

```elisp
(define-advice eval-print-last-sexp (:around (old-fun &rest args) add-prefix)
  "Prepend ;; =>."
  (let ((op (point)))
    (apply old-fun args)
    (save-excursion
      (goto-char op)
      (forward-line 1)
      (insert ";; => "))))
```


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h2rizey)
**Votes:** 17

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


## u/Ramin_HAL9001 [🔗](https://www.reddit.com/r/emacs/comments/cw1eky/comment/ey8i3z6)
**Votes:** 17

Here's a really old trick that I only just now started using: save window layouts to registers with `C-x r w <Letter>`, which saves it into a `Letter` key register. Switch between window layouts using the usual "register jump" `C-x r j`, that is `C-x r j <Letter>` to go back to the layout in that lettered register.

If you use registers for storing text or other things, be careful to use a different set of letters for text and a different set for window layouts. Personally I never use registers for storing text (`M-y` to rotate through the kill-ring is all I need). But I have been using the window layout one quite often lately.

It feels a little like having the multiple desktops feature common to many Linux desktop environments, but I can use it to switch between different projects I am working on within Emacs without needing multiple instances of Emacs.


## u/alvarogonzalezs [🔗](https://www.reddit.com/r/emacs/comments/12jexep/comment/jg34ody)
**Votes:** 17

I'm a big user of `ffap`. I use this function with `M-x` each time I want to open a file whose name is under the cursor.

But this week I discovered `ffap-bindings`. This function replaces some key bindings to use `ffap` when it makes sense. For example, it replaces `find-file` with `find-file-at-point`, so the usual keybindings are enriched at no cost.


## u/SamTheComputerSlayer [🔗](https://www.reddit.com/r/emacs/comments/sijcap/comment/hvbbnjq)
**Votes:** 17

Just figured this out, maybe a bit of a hack...

In flyspell, I was annoyed I had to use mouse-2 when I wanted to correct a word, and I didn't want to sacrifice a major-mode keybinding to do it from the keyboard. But flyspell actually creates an overlay for misspelled words and attaches a keymap to it, which you can do I just realized- very cool. So I just bound `flyspell-correct-at-point` to "<return>" in the `flyspell-mouse-map`, and now return corrects words when my cursor is on a misspelled word!

But the fact you can attach keymaps to overlays just seems so useful, will definitely use in the future.


## u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/jn6m14/comment/gazzdyz)
**Votes:** 16

It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.


## u/Quasimoto3000 [🔗](https://www.reddit.com/r/emacs/comments/24l8f2/comment/ch88vym)
**Votes:** 16

Dude look into Elpy. It's incredibly simple to set up and very powerful.

I couldn't recommend it more.

Edit: here's all the info you need.

https://github.com/jorgenschaefer/elpy/wiki/Installation

Let me know if you have any questions.


## u/c17g [🔗](https://www.reddit.com/r/emacs/comments/blo25q/comment/emskiq0)
**Votes:** 16

To avoid accidentally inserting text in invisible area (`...`) in Org files, set `org-catch-invisible-edits` to `error` or `show-and-error`. See docs.


## u/cfraizer [🔗](https://www.reddit.com/r/emacs/comments/cmnumy/comment/ew3mpek)
**Votes:** 16

I learned something great today, thanks to [emacs.stackexchange.com](https://emacs.stackexchange.com) user `jue`.

Say, you want to grab a column of stuff from a buffer and put it in another buffer.

Emacs's `rectangle` commands seem perfect for such a thing.
The problem I've always had is not being able to insert the rectangle as new lines in the middle of some other buffer.

For example, say I have a `dired` buffer like this:

```elisp
  total used in directory 20 available 9223372036849890833
  drwxr-xr-x  14 colinf staff  448 Jun  9 16:47 .
  drwxr-xr-x 184 colinf staff 5888 Aug  2 08:52 ..
  drwxr-xr-x   3 colinf staff   96 Jun  9 16:47 .circleci
  drwxr-xr-x  14 colinf staff  448 Aug  5 22:15 .git
  -rw-r--r--   1 colinf staff  342 Jun  9 16:47 .gitignore

```
And I just want to grab the file sizes (`488`, `5888`, `96`, …) and add those as new lines in the middle of some existing `org-mode` files.

```elisp
⋮
** Some heading
- I want to put those file sizes here.
** Some other heading
*** Other subhead
*** Another other subhead
⋮
```
I would always end up with something like
```elisp
⋮
** Some heading
 488
5888** Some other heading
  96*** Other subhead
 448*** Another other subhead
⋮
```
When what I wanted was
```elisp
⋮
** Some heading
 448
5888
  96
 448
 342 
** Some other heading
*** Other subhead
*** Another other subhead
⋮
```

Anyway, what I learned is to:
1. narrow the org buffer to just the current line (`narrow-to-region` usu. `C-x n n`)
2. yank that rectangle (`yank-rectangle` usu. `C-x r y`) into the narrowed buffer
3. undo the narrowing (`widen` usu. `C-x n w`)

It automatically opens up lines for the yanked rectangle.

[Maybe the rest of the Emacs-using world already knew this, but it has bugged me—very slightly and very occasionally—for 20+ Emacs-using years.]


## u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/un4wf8/comment/i86hwzi)
**Votes:** 16

Something amazin that I have been using recently is `ripgrep-all` as the `consult-ripgrep` command to search in pdfs. 

It is amazing with the `orderless` dispatchers to control the search filtering.
I use `!` to exclude a string and `=` to match exactly.

Also amazing with `embark-collect` which allows collapsing features.
Or within the collect buffer use `consult-line` to further filter.
And even open the pdf.


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


## u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/xw4muy/comment/ir96qmu)
**Votes:** 16

I finally got around to writing a small README for my Emacs config, highlighting some homegrown parts that I really like.  I reckon most of these things are pretty standard, but maybe some people here still find it useful: 

  https://gitlab.com/slotThe/dotfiles/-/tree/master/emacs/.config/emacs


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/12rlq4a/comment/jgwlxuw)
**Votes:** 16

Often when literate programming I want to split up a code block, maybe copy-pasted with multiple functions in it, into separate blocks so I can put some text in between them. The command, with cursor within a `BEGIN_SRC` block, is `org-babel-demarcate-block` `(C-c C-v d)`.


## u/shoutouttmud [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efi7pbj)
**Votes:** 15

This is for the evil users out there: 

Usually evil text objects are powerful enough, but sometimes you can use the extra functionality that [expand-region](https://github.com/magnars/expand-region.el) provides. So, sometime in the last month I started using it, and I managed to find a (quite seamless in my opinion) way to integrate it with evil. 
```elisp
  
(defhydra hydra-expand-region ()
   "region: "
   ("k" er/expand-region "expand")
   ("j" er/contract-region "contract"))

(evil-define-key 'visual 'global (kbd "v") #'hydra-expand-region/body)
 
```
The above code should be pretty self-explanatory to anyone familiar with evil and hydra. By pressing v in visual state you "enter" the expand region hydra that lets you call expand and contract region by pressing j/k, effectively creating an additional evil state with a minimal amount of code


## u/Thaodan [🔗](https://www.reddit.com/r/emacs/comments/bz9rxn/comment/eqs7osm)
**Votes:** 15

[Which-Key](https://github.com/justbur/emacs-which-key)
Great to learn keybinds, especially for new emacs users:

```elisp
(use-package which-key
  :config (which-key-mode 1))
```


## u/grabyourmotherskeys [🔗](https://www.reddit.com/r/emacs/comments/2wh9o0/comment/coqx6vl)
**Votes:** 15

I really love org mode. The way to use org mode in my opinion is to set up capture templates directing notes and tasks quickly to the right files (whichever you like).

http://orgmode.org/manual/Capture-templates.html

You can then use the agenda to view, sort, filter, and search your notes and tasks. 

http://orgmode.org/manual/Agenda-commands.html

Once you get used to adding things quickly and finding them quickly it all falls into place. You can even export your notes in various formats including ICS (calendar) files so you can view your appointments on your phone.

One major advantage Evernote has is of course a Mobile app. I get around this by renting a very cheap vps and ssh'ing to it to use emacs (from my phone, my Chromebook, or workstation). I use the vps for other things, too.

The other is the images. I really don't know how to handle that. Org mode has ways to link to files and other things but I haven't tried displaying them or otherwise wiring working with images.

This works for me and I love using emacs for this purpose. I have tried a million other methods and this seems to work for me.

Here is a great tutorial: http://orgmode.org/worg/org-tutorials/orgtutorial_dto.html


## u/laralex [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f5pgfu3)
**Votes:** 15

A small basic thing, but once I'd discovered it, I started using dired.
```C-x C-j``` is most likely bound to ```dired-jump```, and this function opens dired for this window's file, without promting for directory (and this prompt was an issue for my workflow when using ```C-x d```). That makes finding and switching files just as convenient as in OS GUI. I've also bound a few keys when in dired mode (I find them decent):

```a``` - prompt a name and create empty file

```d``` - prompt a name and create empty dir

```u``` - go to parent dir (the key is a mnemonic to "go Up in directory tree)

```j``` - if it's a dir go into it, otherwise find this file (key is near to 'u' so jumping up and down is not a big deal with one hand, also the 'j' is the easiest key for me as a touchtyper) 

```n```/```p``` - move one entry down/up, which resembles ```C-n```/```C-p```


## u/marcowahl [🔗](https://www.reddit.com/r/emacs/comments/c1zl0s/comment/ergqu5u)
**Votes:** 15

 `C-x s d` leads to a diff of the buffer with its file.


## u/swhalemwo [🔗](https://www.reddit.com/r/emacs/comments/d8k4ce/comment/f1b7c7u)
**Votes:** 15

finally found an opportunity to use git-timemachine: I was optimizing a Python function when I realized I needed to be sure it returned the same results as before. jumped to previous commit, C-c C-y f and voila. made me feel like a code wizard.


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/cmnumy/comment/ew3jyr5)
**Votes:** 15

When you are in the middle of inputting some command in the minibuffer and realize you forgot to to invoke the command with prefix arg the following enables you to restart the minibuffer command with prefix arg when pressing C-u in minibuffer (keeping your input). Works for ivy commands, too:

```elisp
 ;; -*- lexical-binding: t; -*-
(require 'ivy)
(defvar minibuffer-this-command+ nil
  "Command minibuffer started with.")

(add-hook 'minibuffer-setup-hook
      (defun minibuffer-set-this-command+ ()
        (setq minibuffer-this-command+ real-this-command)))

(define-key minibuffer-local-map (kbd "C-u") 'minibuffer-restart-with-prefix+)
(define-key ivy-minibuffer-map (kbd "C-u") 'minibuffer-restart-with-prefix+)
(defun minibuffer-restart-with-prefix+ ()
  "Restart current minibuffer/ivy command with prefix argument."
  (interactive)
  (let ((input (ivy--input)))
    (cond ((memq  #'ivy--queue-exhibit post-command-hook)
           (ivy-quit-and-run
             (let ((current-prefix-arg '(4))
                   (ivy-initial-inputs-alist `((,(ivy-state-caller ivy-last) . ,input))))
               (call-interactively (ivy-state-caller ivy-last)))))
          (t
           (ivy-quit-and-run
             (let ((current-prefix-arg '(4)))
               (minibuffer-with-setup-hook (lambda ()
                                             (insert input)
                                             (minibuffer-message "C-u"))
                 (call-interactively minibuffer-this-command+))))))))
```


## u/FOSHavoc [🔗](https://www.reddit.com/r/emacs/comments/cgptj7/comment/eumg1fj)
**Votes:** 15

Turns out you can open a single buffer in two windows side-by-side in a continuous view with `follow-mode`. That is, if your left buffer is showing lines 1-63, the right buffer will be showing you lines 64-126 and they will scroll together as well. There have been many times in the past where I wished my screen was a bit taller and Emacs will now let me do that :)


## u/AffectionateAd8985 [🔗](https://www.reddit.com/r/emacs/comments/sd10q9/comment/hu9xfed)
**Votes:** 15

`(add-hook 'org-mode-hook (lambda () (org-next-visible-heading 1)))`

Move to first heading when open org files, with `org-use-speed-commands`, I can quick browse org file with only `n/p` keys.


## u/freesteph [🔗](https://www.reddit.com/r/emacs/comments/eoigxl/comment/fed40nx)
**Votes:** 15

If you needed more reasons to love Magit I've just found out can add the Git meta-fields in the commit message (`Co-authored-by`, `Signed-off-by`, etc, which I can never remember correctly) by typing `C-c TAB` which will interactively ask you for the field (`C-a` -> `Co-authored-by`) and then also interactively fill the relevant team member with their name and e-mail (probably from the repo's list of committers). Awesome!


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/vnals8/comment/ie7p6ja)
**Votes:** 15

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


## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/y7wrdn/comment/isze25m)
**Votes:** 15

not emacs per se, but jack rusher did a talk about programming 'ux / ergonomics / pragmatics' with a lot of fun ideas about coding, past (lisp machines, smalltalk ... ) or more recent clojure based tools

https://www.youtube.com/watch?v=8Ab3ArE8W3s

hope you enjoy it

warning: poop emoji


## u/TeMPOraL_PL [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnx4z28)
**Votes:** 15

If you're like me, and your day ends way past midnight, handling those last few tasks in your Org Mode agenda gets tricky. Fortunately, it turns out Org Mode has what I call "25th hour mode".

```elisp
;; consider the current day to end at 3AM
(setq org-extend-today-until 3) 

;; make timestamp processing functions aware of this
(setq org-use-effective-time t) 

```
Combined, this allows to extend the day past midnight, with things like agenda views, scheduling commands, repeaters, etc. thinking the current time is 23:59 up until the `org-extend-today-until` limit. With this enabled, if I have a task that has a repeater of  and complete it at 01:00, I no longer have to then manually reschedule the task back one day.


## u/rucci99 [🔗](https://www.reddit.com/r/emacs/comments/r69w7i/comment/hmryv5o)
**Votes:** 15

I just found out that Magit can backup changes of uncommitted files automatically. Here's the link to online manual:
[Magit Wip Modes](https://magit.vc/manual/magit/Wip-Modes.html#Wip-Modes).


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/ofen99/comment/h4dxjbz)
**Votes:** 15

If you want to search and replace but with preview for the matches, don't use `query-replace-regexp` directly. Instead start by searching for your regexp in `isearch-forward-regexp`, which highlights the matches interactively, and once you have the correct regexp, run `isearch-query-replace` (bound to `M-%` in `isearch-mode-map`).

Note that there is also an `isearch-query-replace-regexp` command but you don't need it: `isearch-query-replace` will automatically detect if your isearch session was for regexps. The docstring for `isearch-query-replace` doesn't seem to mention this nice feature.


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


## u/rcoacci [🔗](https://www.reddit.com/r/emacs/comments/ad8gzq/comment/edeqcpa)
**Votes:** 14

Just one thing: which Spacemacs "style" did you choose? If you chose "vim" style you need to be careful when searching for emacs resources or documentation, because the "vim" style is quite different from the standard emacs way of doing things, and that's probably why you had so much problem in the beginning.


## u/thr33body [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikqxn0r)
**Votes:** 14

I don’t have any specific tip but I just want to throw it out there that if you are tired of using spacemacs or doom it was much easier to set up my own install than I thought. It only took me a couple of days of active work and now diagnosing problems is so much simpler. Not to say that you should not use either one but I wanted to learn more about emacs and I’ve been really happy with the results.


## u/Tatrics [🔗](https://www.reddit.com/r/emacs/comments/n9q662/comment/gxpeh9v)
**Votes:** 14

I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \*scratch\* buffer and then evaluate it with \`C-x C-e\`.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what  do you think in general!


## u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/cpq3ru/comment/ewr3jkm)
**Votes:** 14

Small thing, but `C-x C-k C-f` runs `kmacro-set-format`, which you can use to change the format of the counter when they are inserted in a macro.

For example, `%x` for hexadecimal, or `%02d` to prefix the number with a zero if it is less than two digits.  The default is `%d`.

If you set it while recording a macro, it will only use the format in that macro.  If you set it outside of a macro, it will change the default for subsequent macros.


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/c86mn9/comment/esrgf9k)
**Votes:** 14

How to convert any function into a command, for example, `sleep-for`:

```elisp
(put 'sleep-for 'interactive-form
 '(interactive "nSleep for: "))
```


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


## u/username223 [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeu7tyj)
**Votes:** 13

`M-x quick-calc` (which I've bound to `C-=`).  Calc is insanely complex, but this lets you do simple calculations quickly.


## u/olaeCh0thuiNiihu [🔗](https://www.reddit.com/r/emacs/comments/8xu2zt/comment/e26e1uc)
**Votes:** 13

Meta advice: when taking notes, put the notes where you would go look for them.  If you're trying to find some notes, think about where you would try to look first.  If it's not there, remember where you looked and once you find the notes, make a link/copy to where you looked first.  The more you do this, the better you'll get at picking the right spot the first time.

Try to keep things as flat as possible.  Your job is not to present a theses about the perfect taxonomy for your notes, it's just to find things again later.  Don't make lots of folders, directories, scattered Org files, deeply nested lists, etc.  Only start breaking things into groups once you find yourself looking for something and thinking, "All of this X related stuff should just go into a folder or something."  This combines with the first advice in that if everything is flat, there aren't very many places you will want to look for something.  If you come up with a huge tree that would make a biologist cry, you'll have a really hard time remembering where exactly you put something.

This advice was shameless stolen/adapted from GTD.


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/bz9rxn/comment/er6dqx0)
**Votes:** 13

`C-l` `(recenter-top-bottom)` is a known command that "recenters" the window around point by scrolling to the top, center, bottom.

But there's a no so well-known version, `C-M-l` `(reposition-window)`, that intelligently scrolls the buffer so that the function under point is fully visible, etc. It's very useful for programming languages modes.


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/b5n1yh/comment/ejessje)
**Votes:** 13

For cases where `C-u C-x =` does not work (for example mode line or minibuffer prompt ivy-completions, fringe etc.) use a color picker and provide the value to `list-faces-for-color`:



 
```elisp
(defun list-faces-for-color (color &optional distance)
  "List faces which use COLOR as fg or bg color.

            Accept colors within DISTANCE which defaults to 0."
  (interactive (list (read-color "Color: ")
                     (and current-prefix-arg
                          (prefix-numeric-value current-prefix-arg))))
  (with-help-window (get-buffer-create (format " *%s*" this-command))
    (dolist (face (sort
                   (list-faces--for-color color distance)
                   (lambda (f1 f2)
                     (string< (symbol-name f1)
                              (symbol-name f2)))))
      (list-faces--print-face face)
      (terpri))))

(defun list-faces--print-face (face)
  "Print face and its parents if any."
  (with-current-buffer standard-output
    (let ((fchain (cdr (list-faces--inheritance-chain face :foreground)))
          (bchain (cdr (list-faces--inheritance-chain face :background))))
      (insert (propertize (format "%s" face) 'face face))
      (cond (fchain
             (dolist (face fchain)
               (insert " > " (propertize (format "%s" face) 'face face))))
            (bchain
             (dolist (face bchain)
               (insert " > " (propertize (format "%s" face) 'face face))))))))

(defun list-faces--inheritance-chain (face attr)
  "Return inheritence change for face and attr."
  (let ((g (face-attribute face attr)))
    (if (and (stringp g)
             (not (string= "unspecified" g)))
        (list face)
      (let ((inherit (face-attribute face :inherit)))
        (when inherit
          (if (facep inherit)
              (cons face
                    (list-faces--inheritance-chain inherit attr))
            (if (consp inherit)
                (cl-dolist (face inherit)
                  (let ((res nil))
                    (when (and (facep face)
                               (setq res (list-faces--inheritance-chain face attr)))
                      (cl-return res)))))))))))


(defun list-faces--attribute (face attr)
  "Get face attribute of face as defined or inherited."
  (let* ((chain (list-faces--inheritance-chain face attr)))
    (cl-dolist (f (nreverse chain))
      (let ((g (face-attribute f attr)))
        (when (and (stringp g)
                   (not (string= "unspecified" g)))
          (cl-return g))))))



(defun list-faces--for-color (color &optional distance)
  "Return all faces with COLOR as fg or bg withing DISTANCE."
  (let ((faces ())
        (distance (or distance 0)))
    (mapatoms (lambda (atom)
                (when (facep atom)
                  (let ((fg (list-faces--attribute atom :foreground))
                        (bg (list-faces--attribute atom  :background)))
                    (when (or (and fg
                                   (<= (color-distance
                                        fg
                                        color)
                                       distance))
                              (and bg
                                   (<= (color-distance
                                        bg
                                        color)
                                       distance)))
                      (push atom faces))))))
    (delete-dups faces)))
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


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/bdrdx0/comment/el1avex)
**Votes:** 13

This seems almost too basic to mention, but it's handy:

```elisp
(defun add-to-hooks (func &rest hooks)
  (dolist (hook hooks) (add-hook hook func)))

```
Then, for example:

```elisp
(add-to-hooks 'enable-paredit-mode
  'emacs-lisp-mode-hook
  'scheme-mode-hook
  'lisp-mode-hook
  'clojure-mode-hook
  'eval-expression-minibuffer-setup-hook)

(add-to-hooks (lambda () (setq show-trailing-whitespace t))
  'prog-mode-hook
  'org-mode-hook
  'html-mode-hook)
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


## u/vfclists [🔗](https://www.reddit.com/r/emacs/comments/bj0ti2/comment/em9h7wj)
**Votes:** 13

Can this thread be numbered eg `Weekly tips/trick/etc/thread - 0xx` or `Weekly tips/trick/etc/thread - YYYY Week XX`?

It becomes hard to remember which version you noted an interesting tip you want to return to.

I don't know if it is generated by a script it should but it should be simple. It can be made more search engine friendy prefixing or suffixing with `r/emacs` if other subreddits do something similar.


## u/rhmatthijs [🔗](https://www.reddit.com/r/emacs/comments/gzivu3/comment/ftgqnbp)
**Votes:** 13

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


## u/vatai [🔗](https://www.reddit.com/r/emacs/comments/ojzv53/comment/h5584no)
**Votes:** 13

The emacs lisp tutorial is the real tutorial for emacs ;)


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/br7q0y/comment/eohu0bg)
**Votes:** 13

Not very well-known, but Emacs has its own spreadsheet and file format (`.ses`) (`ses-mode`). It supports many advanced features, and formulas are written in Elisp.

&#x200B;

Just another alternative to Org tables.


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


## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/q76kok/comment/hgk3wik)
**Votes:** 13

This is pretty neat: scrolling up/down one line at a time while keeping the position of point:

`(setq scroll-preserve-screen-position 1)`  
`(global-set-key (kbd "M-n") (kbd "C-u 1 C-v"))`  
`(global-set-key (kbd "M-p") (kbd "C-u 1 M-v"))`  


From: http://pragmaticemacs.com/emacs/scrolling-and-moving-by-line/


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/lapujj/comment/glr8pkr)
**Votes:** 13

You can use EWW to bypass pay-walls on news sites, and other Javascript-enabled nastiness. Plus, eww can copy from what it sees into equivalent orgmode syntax, and it's also compatible with SPRAY for speed-reading. In otherwords, EWW is great for when you just need to READ the internet.


## u/celeritasCelery [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqdnyhk)
**Votes:** 13

Shells in emacs like `shell-mode` and `eshell` can write multi line input using `comint-accumulate`. Normally bound to `C-c SPC`.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/fs93hk/comment/fm1fw1x)
**Votes:** 13

For me, learning about `kill-whole-line` (control-shift-delete) was a revelation.  Compared to the "classic" Emacs method of deleting lines (C-a to go to the beginning of the line if not already there, C-k once to kill to the end of line, C-k again to kill the newline), it feels like a major speedup.

I still use C-k frequently, but it's effectively just a kill-to-end-of-line command.


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


## u/leothrix [🔗](https://www.reddit.com/r/emacs/comments/13jvhp7/comment/jl5zu6z)
**Votes:** 13

For `use-package` users (which I assume is many of us), did you know that profiling is easy to do? I'm not talking about `esup`, but a built-in capability that makes it very straightforward to find places to optimize your `init.el` for significantly faster start times.

Enable `use-package-compute-statistics` right after you load `use-package`:

```elisp
(setq use-package-compute-statistics t)

```
Restart emacs, and then invoke `use-package-report`. You'll get a table of the load times for each package that `use-package` manages. I discovered this and found an immediate way to cut my startup time in half by fixing a few packages that weren't deferred properly by adding the right `:hook` keyword.


## u/henry_flower [🔗](https://www.reddit.com/r/emacs/comments/b2uqdm/comment/eiv960e)
**Votes:** 12

A replacement for `C-x k RET`:

```elisp
(defun my-kill-buffer()
  (interactive)
  ;; a list of buffers we don't want to kill accidentally
  (let ((my-holy-buffers '("*scratch*" "*Messages*")))

    (if (member (buffer-name) my-holy-buffers)
        (message "not so fast")
      (kill-buffer (current-buffer))) ))

(global-set-key [f8] 'my-kill-buffer)
```


## u/w0wt1p [🔗](https://www.reddit.com/r/emacs/comments/b5n1yh/comment/ejenls0)
**Votes:** 12

Most of the time when I need to basic calculations, `quick-calc` is enough, no need to fire up `calc`.

But sometimes it is nice to keep the expression as well, for modifying/copying the expression for further calculations.

Also, for example, when planning costs for a project it is nice to keep expressions and results with comments in an org file for future reference.

So I set up this basic function for calculating the expression written on the current line and then **insert the result on the next line**. Bound to <H-return> for easy access without conflicting with other modes.
Nothing fancy, and requires the expression to be on a single line. It can probably be improved a lot, but it does what I intended already.

That said, improvements and suggestions are very welcome, my current elisp skills mostly extends to copy and pasting online example code. :)


```elisp
(defun w0w-calculator ()
  "Calculate math expression on current line using calc-eval"
  (interactive)
  (setq cLine
      (buffer-substring-no-properties
       (line-beginning-position)
       (line-end-position)))
  (save-excursion
    (end-of-line)
    (open-line 1))
  (forward-line 1)
  (insert(calc-eval '("evalv($)" calc-internal-prec 18) 'num cLine)))

(global-set-key (kbd "<H-return>") 'w0w-calculator)    

```
Usage, on a single line, write some math, and then press `<H-return>`

```elisp
125*2+3^2 ;; press <H-return>
259       ;; answer given and inserted in buffer
```


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


## u/ouroboroslisp [🔗](https://www.reddit.com/r/emacs/comments/bj0ti2/comment/em8cyvj)
**Votes:** 12

I've read many blog posts touting the benefits of a literate emacs configuration. They say things about it making code easier to read and understand, and about being able to use org-mode itself to manipulate your code. But among the many benefits, I typically don't see much talk about programmatically exploiting the fact that your code is divided into executable sections based on some category. I also haven't seen many configurations exploit this either.

One exception is [noctuid's config](https://github.com/noctuid/dotfiles/tree/master/emacs/.emacs.d). He uses it to address the problem of [how to gracefully handle errors in your init file](https://emacs.stackexchange.com/questions/669/how-to-gracefully-handle-errors-in-init-file).

This is not a new problem. In fact, this was mentioned by [clemera](https://www.reddit.com/user/clemera) in [the first trial of weekly tips and tricks](https://www.reddit.com/r/emacs/comments/aja311/first_trial_of_a_weekly_emacs_tipstricksetc_thread/). There he suggested binding \`C-x C-c\` to a function that runs your \`init.el\` file in an external process and prevents you from quitting if there are any errors. Although this solution gets the job done and is certainly better than nothing, I was not too fond of it for a couple of reasons.

(1) It makes exiting emacs take a long time because just before exiting you're now loading your *entire* config to check if there are any errors. I like my commands to be snappy and quick.  (2) It happens when I'm exiting emacs. If I'm exiting emacs, it's likely for a good reason (I have something else I want to do). I don't in general like emacs preventing me from quitting it, unless it's just prompting me to save files. (3) You're almost forced to fix the problem right there and then. If you don't and you leave, your \`init.el\` file wont run next time you start emacs.

The way noctuid handled this is wrapping the code in his source blocks with \`with-demoted-errors\` with a [custom tangle function](https://github.com/noctuid/dotfiles/blob/master/emacs/.emacs.d/lisp/noct-util.el). The nice things about this method is you're not forced to fix the problem immediately: if there's an error in your config, it will just skip the error in the section, and run the rest of your config. This means that if you're pressed for time you can get whatever you wanted to get done and quit emacs without fear of not being able to start it up again. Also, it's efficient in the sense that you don't have to run your config two times (one time for setting up emacs and one time at the end to catch errors), it's all done at startup.

In this post I want to point out that what noctuid does is not something we need a literate config to do. I tried literate programming and I (as well as some [others](https://valignatev.com/posts/emacs-org-config/)) have emerged dissatisfied. The drawbacks of a literate configuration are many. Among the most notable are (1) the additional complexity from tangling (2) the annoyance of having to enter an elisp block every time you want to edit the code (3) the additional overhead of having to load org mode when opening your init file (4) *duality* \- having 2 files with the same information that need to be synced (the init elisp file and the init org file).

To achieve something like noctuid and still have an elisp init file, I am considering writing a macro \`elisp-block!\` which will function similarly to org-mode's \`#+BEGIN\_SRC\` syntax except for one key difference. Org source blocks do nothing on their own, they're just syntax markers for org mode whereas \`elisp-block!\` is also a *macro*. What this means is that the *vast majority of the code in our \`init.el\` file will be* *in a macro* (the only thing that can't be in the macro is the \`elisp-block!\` itself)*.* This give us an obscene amount of power.

There are many possibilities of what we can do with this, but I will give a taste:

```elisp
(defvar blacklisted-elisp-block-categories nil
  "Names of elisp blocks that will not be evaled")

(defvar init-file-errors nil "Errors during startup.")

(defmacro elisp-block! (name category &rest code)
  (declare (indent 2))
  `(unless (memq ,category blacklisted-elisp-block-categories)
     (condition-case init-error
         (progn ,@code)
       (error
        (push ,category blacklisted-elisp-block-categories)
        (push init-error init-file-errors)
        (message
         (format "%S in source block %S of category %S\n"
                 (car init-error)
                 ,name
                 ,category))))))

```
\`elisp-block!\` will catch any errors in CODE and print them out instead of raising them (and interrupting execution of your \`init.el\`). It will also tell you the specific block where that happened in the message and store the error in case you want to see what it was later. Finally, it will ensure that subsequent blocks of the same category are not run by adding their categories to \`blacklisted-elisp-blocks\`. In effect, this will ensure that any code that depends on a particular elisp block that has an error will not be run.

The main drawback to \`elisp-block!\` is that it will be littered throughout the \`init.el\`. Yet perhaps this is not so bad, I mean we do this with \`use-package\` as well. In any case, IMO the benefits outweigh the costs.


## u/zoechi [🔗](https://www.reddit.com/r/emacs/comments/fwgpkd/comment/fmo9d5v)
**Votes:** 12

I (Emacs rookie) just found out that native/fast JSON support is not guaranteed when emacs 27+ is used. jansson-dev needs to be installed when Emacs is built https://github.com/emacs-lsp/lsp-mode/issues/1557#issuecomment-608409056


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/c1zl0s/comment/erl5qsa)
**Votes:** 12

If you want to edit the commit that added/deleted a particular line of code, Magit offers `magit-edit-line-commit` that will do the interactive git rebase automatically for you.

`magit-diff-edit-hunk-commit` does the same but from a Magit diff buffer.
`


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


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/fo1fm3/comment/flcwsu4)
**Votes:** 12

Happy Birthday from Emacs, let's assume March 24 is your birthday, put this to your init file, when you open Emacs on your birthday, you'll receive a birthday present from Emacs

```elisp
(when (string= "03-24" (format-time-string "%m-%d"))
  (animate-birthday-present user-full-name))
```


## u/ProfessorSexyTime [🔗](https://www.reddit.com/r/emacs/comments/dlethf/comment/f4wdyaf)
**Votes:** 12

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


## u/jimm [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvqvedf)
**Votes:** 12

I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.


## u/wasamasa [🔗](https://www.reddit.com/r/emacs/comments/br7q0y/comment/eocpxxy)
**Votes:** 12

Last night I wrote some code to improve syntax highlighting for CHICKEN Scheme files. Normally regular `scheme-mode` is sufficient, unfortunately it doesn't handle the non-standard heredoc and `#> ... <#` syntax:

```elisp
(with-eval-after-load 'scheme
  (defun my-scheme-region-extend-function ()
    (when (not (get-text-property (point) 'font-lock-multiline))
      (let* ((heredoc nil)
             (new-beg
              (save-excursion
                (when (and (re-search-backward "#>\\|<#\\|#<[<#]\\(.*\\)$" nil t)
                           (not (get-text-property (point) 'font-lock-multiline)))
                  (let ((match (match-string 0))
                        (tag (match-string 1)))
                    (cond
                     ((equal match "#>") (point))
                     ((string-match-p "^#<[<#]" match) (setq heredoc tag) (point)))))))
             (new-end
              (save-excursion
                (if heredoc
                    (when (and (re-search-forward (concat "^" (regexp-quote heredoc) "$") nil t)
                               (not (get-text-property (point) 'font-lock-multiline)))
                      (point))
                  (when (and (re-search-forward "#>\\|<#" nil t)
                             (not (get-text-property (point) 'font-lock-multiline))
                             (equal (match-string 0) "<#"))
                    (point))))))
        (when (and new-beg new-end)
          (setq font-lock-beg new-beg)
          (setq font-lock-end new-end)
          (with-silent-modifications
            (put-text-property new-beg new-end 'font-lock-multiline t))
          (cons new-beg new-end)))))

  (defun my-scheme-syntax-propertize-foreign (_ end)
    (save-match-data
      (when (search-forward "<#" end t)
        (with-silent-modifications
          (put-text-property (1- (point)) (point)
                             'syntax-table (string-to-syntax "> cn"))))))

  (defun my-scheme-syntax-propertize-heredoc (_ end)
    (save-match-data
      (let ((tag (match-string 2)))
        (when (and tag (re-search-forward (concat "^" (regexp-quote tag) "$") nil t))
          (with-silent-modifications
            (put-text-property (1- (point)) (point)
                               'syntax-table (string-to-syntax "> cn")))))))

  (defun scheme-syntax-propertize (beg end)
    (goto-char beg)
    (scheme-syntax-propertize-sexp-comment (point) end)
    (funcall
     (syntax-propertize-rules
      ("\\(#\\);"
       (1 (prog1 "< cn" (scheme-syntax-propertize-sexp-comment (point) end))))
      ("\\(#\\)>"
       (1 (prog1 "< cn" (my-scheme-syntax-propertize-foreign (point) end))))
      ("\\(#\\)<[<#]\\(.*\\)$"
       (1 (prog1 "< cn" (my-scheme-syntax-propertize-heredoc (point) end)))))
     (point) end)))

(defun my-scheme-mode-setup ()
  (setq font-lock-extend-region-functions
        (cons 'my-scheme-region-extend-function
              font-lock-extend-region-functions)))

(add-hook 'scheme-mode-hook 'my-scheme-mode-setup)
```


## u/sshaw_ [🔗](https://www.reddit.com/r/emacs/comments/br7q0y/comment/eobjxnw)
**Votes:** 12

### Highlighting

Adds a background color to matched text in the buffer. You can have multiple overlays each with a different background color. Some bindings:

```elisp
M-s h .         highlight-symbol-at-point
M-s h l         highlight-lines-matching-regexp
M-s h u         unhighlight-regexp
```


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/fgahb2/comment/fk3in25)
**Votes:** 12

Most people probably know that `M-t` (`transpose-words`) when used between two words swaps them. But it has other a few other features that are useful:

- You don't have to be between words to use it: if you are on a word, from the second character on, it will swap that word with the next.

- If you are at the end of the buffer and use it you get an error message, "Don’t have two things to transpose", _but_ you additionally get placed at the beginning of the last word in the buffer. So `M-t M-t` used at the end of the buffer will swap the last two words!

- You can use it to swap _non-adjacent_ words too! If you call it with a numeric argument of 0, it will swap the word at the start of the region with the next word after the end of the region. For example, say you want to swap "two" with "five" in the following line:

```elisp
  one five three four two six

```
  You can mark the words "five three four" (but don't mark "two") and then `M-0 M-t` will swap "five", the first word in the region, with "two" the first word _after_ the region.

That last trick works with the other `transpose-` commands as well, not just words: `transpose-chars`, `transpose-lines`, `transpose-paragraphs`, `transpose-sentences`, and `transpose-sexps`. Of course, if that `C-0` trick can't be used with any of those commands to swap the two things you want, there is always `transpose-regions`.


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


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/c86mn9/comment/esz8l1y)
**Votes:** 12

The `table` package is very useful and not very popular in Emacs (it's included by default).

Imagine that you have some text that is more or less structured like a table, but not quite. For example it may be some text where you applied a "table-like" layout manually by using a lot of spaces, but the structure is big and things are difficult to maintain. You can select the text, do `M-x table-capture`, and specify empty strings as separators. Emacs will create a pretty table with one cell containing your text. Now you can move the point to parts inside that cell and split around that point with `M-x split-cell`. Once a cell is split, you can navigate each cell with Tab and edit text independently! After you have finished creating a layout, just do `M-x table-release` and everything will be in plain text again, keeping your layout intact.

`table` is a useful package to create complex layouts in code comments (for example, to explain a difficult concept).

For more information, check "Editing Text-based Tables" in the Emacs manual.


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/bwm94g/comment/epyjl3q)
**Votes:** 12

Unlike many applications' [Selection](https://en.wikipedia.org/wiki/Selection_(user_interface)), the Emacs region doesn't need to be highlighted to work. The Emacs region is the range of mark and point, mark is usually an old point, for example, many movement commands such as M-> and C-s automatically stores the starting point as the mark, thus the region is available once the command ends, for example, to select the whole buffer without highlighting, you can use

```elisp
M-> M-<

```
Highlighted region is also called active region. Many commands doesn't care if the region is active or inactive, both of them works.


## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hf1gzs2)
**Votes:** 12

Read [SICP](https://mitpress.mit.edu/sites/default/files/sicp/index.html). Preferably in Info, installable through MELPA or: [https://github.com/webframp/sicp-info](https://github.com/webframp/sicp-info)


## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/y1y0kq/comment/is1ygyw)
**Votes:** 12

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
        (setq from (replace-regexp-in-string "<" "<" from t t))
        (setq from (replace-regexp-in-string ">" ">" from t t))
        (setq to (replace-regexp-in-string "<" "<" to t t))
        (setq to (replace-regexp-in-string ">" ">" to t t))
        (setq id (replace-regexp-in-string "<" "" id t t))
        (setq id (replace-regexp-in-string ">" "" id t t))

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


## u/jacmoe [🔗](https://www.reddit.com/r/emacs/comments/audffp/comment/eh7efpv)
**Votes:** 11

Very cool!

I gave up on getting Emacs set up as a C++ IDE, but I am tempted now :)

&#x200B;

Great that we can have a llvm client/server within Emacs!


## u/ieure [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eewn8ex)
**Votes:** 11

Maybe you already know about `occur`, which shows matching lines in the current buffer -- like grep, but the buffer doesn't need to be backed by a file.

Emacs also has `multi-occur` which does the same thing across multiple buffers (you have to specify each one); and `multi-occur-in-matching-buffers`, which matches lines across buffers whose name matches a regexp'.

Most of the time, I want to search *all* open buffers, so I wrote:

```elisp
(defun multi-occur-global (regexp &optional nlines)
  "Do a `multi-occur' across all buffers."
  (interactive (occur-read-primary-args))
  (thread-first
  ;; Don't occur in *Occur* buffers
  (lambda (buffer) (eq (buffer-local-value 'major-mode buffer) 'occur-mode))
(cl-remove-if (buffer-list))
(multi-occur regexp)))
```

This is great if you have IRC chats or emails open in Emacs and need to quickly find a previous conversation.  And of course `occur` works properly with `next-error` / `previous-error` just like the `grep` commands do.


## u/uptocode [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efgyvey)
**Votes:** 11

Like Magit? Like writing TODOs in your source code? Check out: 

https://github.com/alphapapa/magit-todos

It uses simple programs like grep or rgrep to show the TODOs in your git repo.


## u/shoutouttmud [🔗](https://www.reddit.com/r/emacs/comments/aoqcyl/comment/eg2ujqe)
**Votes:** 11

Evil related:

An interesting thing I read in the past week was this [blog post](http://vimcasts.org/blog/2014/02/follow-my-leader/). What I got out of it is that, although vi keybinds cover almost all keys, leaving you with few free keys to change, a significant amount of combinations of keys are invalid (for example c followed by x doesn't do anything) and thus you can bind them to whatever you want. 

I have not integrated this idea to my evil workflow yet, but it sounds quite promising, and I think I'll start using it at some point. 

(One thing to consider though is that with evil there are packages that add additional text objects. If you added a text object that is represented by x then c followed by x would become a valid combination and thus would clash with your keybinding, forcing you to change it to something else)


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/bz9rxn/comment/eqrg07x)
**Votes:** 11

Run multiline code in comment, e.g.,

```elisp
;; (+ 1
;;    2
;;    3)

```
with the following advice you can use C-x C-e or C-j as usual:

```elisp
(define-advice elisp--preceding-sexp (:around (old-fun) multiline-comment)
  "Support sexp in multiline comment."
  (condition-case err
      (funcall old-fun)
    (scan-error
     (if (nth 4 (syntax-ppss))
         (let ((work-buffer (current-buffer))
               (temp-buffer (generate-new-buffer " *temp*"))
               found sexp error)
           (with-current-buffer temp-buffer
             (delay-mode-hooks (emacs-lisp-mode)))
           (save-excursion
             (comment-normalize-vars)
             (while (and (comment-beginning)
                         (not found))
               (let ((code (buffer-substring-no-properties
                            (point) (line-end-position))))
                 (with-current-buffer temp-buffer
                   (goto-char (point-min))
                   (insert code ?\n)
                   (goto-char (point-max))
                   (condition-case err
                       (setq sexp (funcall old-fun)
                             found t)
                     (scan-error (setq error err)))))
               (when (= -1 (forward-line -1))
                 (error "elisp--preceding-sexp@multiline-comment error"))
               (goto-char (line-end-position))))
           (cond (found sexp)
                 (error (signal (car error) (cdr error)))
                 (t (error "elisp--preceding-sexp@multiline-comment error"))))
       (signal (car err) (cdr err))))))

```
(I already shared this on Emacs China last year, see https://emacs-china.org/t/c-x-c-e/7760)


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/auwzjr/comment/ehdsutf)
**Votes:** 11

To replace a shell command with its output, select the command and type `C-u M-| sh`. It can work because bash supports read program via STDIN, Python and Ruby work as well.


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/drw8i3/comment/f6ncyes)
**Votes:** 11



```elisp
emacs --batch -l cl-lib --eval "(cl-loop (print (eval (read))))"
```


## u/AnugNef4 [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/ja41lso)
**Votes:** 11

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


## u/Gollum999 [🔗](https://www.reddit.com/r/emacs/comments/bdrdx0/comment/el0hkx2)
**Votes:** 11

I just found out that the tables in `org-mode` support Excel-like calculations.  You can use functions from `calc` as well as arbitrary Elisp expressions!

Only complaint is that it's not the most performant. I tested it on a table of ~1200 rows and it took around 8 seconds to update my calculated column. :/


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikrx30z)
**Votes:** 11

I've parsed and prettified some of the comments (I think I'm missing some, but hopefully should be fixed soonish) from past weekly tips and tricks thread here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

If you fancy procrastinating for a bit today...


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/cdu7cd/comment/etweom3)
**Votes:** 11

If you don't like the look of wave (under)lines in Emacs and prefer straight lines: 

```elisp
(mapatoms (lambda (atom)
            (let ((underline nil))
              (when (and (facep atom)
                         (setq underline
                               (face-attribute atom
                                               :underline))
                         (eq (plist-get underline :style) 'wave))
                (plist-put underline :style 'line)
                (set-face-attribute atom nil
                                    :underline underline)))))
```


## u/c17g [🔗](https://www.reddit.com/r/emacs/comments/bb5c1w/comment/ekgnfnv)
**Votes:** 11

If you:

1. Develop code on remote machine;
2. Use SSH to access remote; and
3. Annoyed by `flycheck` not working properly over TRAMP

Then consider using [`sshfs`](https://github.com/libfuse/sshfs) to mount your remote filesystem to local. This way, `flycheck` will regard your files just as a local one, and works properly. Neat trick discovered today when debugging flycheck-flake8 with my colleague (are you reading? :P)


## u/Desmesura [🔗](https://www.reddit.com/r/emacs/comments/d8k4ce/comment/f1ayv4c)
**Votes:** 11

I've found that if you press `C-f`, the cursor goes right.


## u/ainstr [🔗](https://www.reddit.com/r/emacs/comments/vcpk6u/comment/ichiccu)
**Votes:** 11

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


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/bog1b5/comment/enhwf5s)
**Votes:** 11

For commands without default keybindings that I use frequently enough not to want to type out the full name every time, but infrequently enough not to want to dedicate a keybinding to them, I define short, shell-command-like aliases.  Here are some of my current ones:

```elisp
(defalias 'mb 'rename-buffer)
(defalias 'ru 'rename-uniquely)
(defalias 'ar 'align-regexp)
(defalias 'rb 'revert-buffer)
(defalias 'c 'calc)
(defalias 'sh 'shell)
(defalias 'wr 'write-region)
(defalias 'tail 'auto-revert-tail-mode)
(defalias 'pe 'proced)
(defalias 'lp 'list-packages)
(defalias 'mff 'magit-find-file)
(defalias 'eb 'erase-buffer)
(defalias 'cn 'column-number-mode)
(defalias 'jpp 'json-pretty-print)
(defalias 'mexp 'pp-macroexpand-last-sexp)
```


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/c86mn9/comment/esy8p93)
**Votes:** 11

A bunch of useful built-in commands have no default keybinding. Here's a list that includes the binding I use as suggestion:

- List editing commands:
  - `up-list` (`C-M-o` --mnemonic: it gets you Out of a list)
  - `kill-backward-up-list` (`<C-M-backspace>`)
  - `raise-sexp` (`M-R`)
  
- Prose oriented commands:
  - `kill-paragraph` (`M-K`)
  - `mark-end-of-sentence` (`M-E`)
  - `transpose-sentences` (`M-T`)
  - `transpose-paragraphs` (`C-x t`)
  
- Miscellaneous:
  - `browse-url-at-point` (`C-c b`) Of course, if you use `goto-address-mode`, you don't need a keybinding for this one.
  - `info-apropos` (`C-h A`)
  - `proced` (`C-x C-p`) It works on Windows too!

Also, there are a bunch of default keybindings that I think could be better used for a very similar command:

- `C-d`. It is usually bound to `delete-char`, but even the help for `delete-char` thinks this is a bad idea!:
   
   > The command ‘delete-forward-char’ is preferable for interactive use, e.g.
   > because it respects values of ‘delete-active-region’ and ‘overwrite-mode’.

  So I bind `C-d` to `delete-forward-char` and enjoy the `delete-active-region` goodness.
  
- `C-x k`. I use it for `kill-current-buffer` and use `C-x K` for `kill-buffer`.

- `M-z`. It's normally bound to `zap-to-char` but I find `zap-up-to-char` useful more often, so I bind it to `M-z` and put `zap-to-char` on `M-Z`.

- `M-c`, `M-l`, `M-u`. These are bound to `capitalize-word`, `downcase-word` and `upcase-word`, but I think `capitalize-dwim`, `downcase-dwim` and `upcase-dwim` are strictly better: they act like the `-word` versions when no region is active, and like the `-region` versions  when there is an active region.


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


## u/human_banana [🔗](https://www.reddit.com/r/emacs/comments/eeyhdz/comment/fbyevye)
**Votes:** 11

Have multiple machines where some things are available and other aren't?

`when-available` takes a function name and if it exists, executes the rest.

```elisp
(defmacro when-available (func cmd)
  "Execute CMD if FUNC exists as a function."
  `(when (fboundp ,func) ,cmd))

```
So you can deal with different environments.

I mostly use this for things that are nice to have, but aren't required and don't need to be installed if they aren't already.


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/r69w7i/comment/hmst3ih)
**Votes:** 11

macros in emacs are like a secret, forgotten art, but I use them with regexp search, orgmode commands to tweak repeating events (or any number of other uses). Learn macros; they gave emacs its name! One usage here: https://orys.us/ug


## u/eleven_cupfuls [🔗](https://www.reddit.com/r/emacs/comments/10ktqj0/comment/j5umed8)
**Votes:** 11

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


## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/ml4wql/comment/gtkc524)
**Votes:** 11

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


## u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/11lqkbo/comment/jbe06qv)
**Votes:** 11

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


## u/Adorable-Effort [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeuwps6)
**Votes:** 10

`image-mode` can be used to preview TTF and OTF fonts.

Emacs already opens TTF fonts with `image-mode` automatically, but I also wanted it to do the same with OTF.

```elisp
(add-to-list 'auto-mode-alist '("\\.otf\\'" . image-mode))
```


## u/shoutouttmud [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eetvkas)
**Votes:** 10

Let me start this thread off: [Pragmatic emacs](http://pragmaticemacs.com/) is a great source of ideas. I recently came across this setting:
```elisp
    
(setq wdired-allow-to-change-permissions t)

```
plus a simple function to replace kill-buffer so that it kill the current buffer without prompting me for what buffer I want to kill(the docstring is mine):

```elisp
(defun my-kill-this-buffer ()
  "Kill the current buffer. This function is required because the
`kill-this-buffer' included in Emacs is safe to use only
from the menu bar"
  (interactive)
  (kill-buffer (current-buffer)))
```


## u/DasEwigeLicht [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeutpz1)
**Votes:** 10

Get info about installed Arch packages, with auto completion:

```elisp
(defun std::pacman-pkg-info ()
  (interactive)
  (let* ((completions (->> "pacman -Q"
                           (shell-command-to-string)
                           (s-trim)
                           (s-lines)
                           (--map (car (s-split " " it :no-nulls)))))
         (name (completing-read "Package: " completions)))
    (switch-to-buffer (get-buffer-create "*Package Info*"))
    (erase-buffer)
    (-> (format "pacman -Qi %s" name)
        (shell-command-to-string)
        (s-trim)
        (insert))
    (goto-char 0)
    (conf-mode)))
```


## u/ShyGuy32 [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efhx7vs)
**Votes:** 10

Inspired by the venerable Twitch [Stay Healthy Bot](https://www.twitch.tv/stayhealthybot), I decided to do up a similar reminder feature from my local Emacs. It's more or less just a timer mixed with alert.el. There's still some work do be done here (mostly in regards to resetting the timer, as my usual workflow is to close out Emacs at the end of the workday), but I've found it pretty useful during the workday to keep me hydrated and moving around.

```elisp
(use-package alert
  :config
  ;; OS-dependent
  (setq alert-default-style 'osx-notifier)

  ;; Reminder to stay healthy!
  (defvar stay-healthy--start nil)
  (defvar stay-healthy--timer nil)
  (defun stay-healthy--alert (start-time)
    "Alert the user to stay healthy"
    (let* ((now (time-to-seconds (current-time)))
           (seconds (- now start-time))
           (hours (format-seconds "%h" seconds)))
      (unless (string= hours "0")
        (alert
         (format "You've been working for %s hour(s)! Have you been drinking your water? It might also be a good time to get up and stretch, refill your water, grab something to eat, or maybe take a bathroom break! Stay Healthy!"
                 hours)
         :title "Stay Healthy!"
         ))))

  (defun stay-healthy-start-timer ()
    "Start a timer to keep you healthy"
    (interactive)
    (unless stay-healthy--start
      (setq stay-healthy--start (time-to-seconds (current-time))))
    (setq stay-healthy--timer (run-with-timer 0 (* 60 60) #'stay-healthy--alert stay-healthy--start)))

  (defun stay-healthy-stop-timer ()
    "End the healthy timer"
    (interactive)
    (when stay-healthy--timer
      (cancel-timer stay-healthy--timer)))

  (stay-healthy-start-timer))

```
&#x200B;


## u/7890yuiop [🔗](https://www.reddit.com/r/emacs/comments/awyvpa/comment/ehqdrmr)
**Votes:** 10

I find `M-\` preferable to a separate `hungry-delete` package (i.e. do it if that's what you mean to do, but not otherwise).

or `M-SPC` to call `just-one-space` if that's what you mean to do.

or bind something to `cycle-spacing`.


## u/zreeon [🔗](https://www.reddit.com/r/emacs/comments/98nkt6/comment/e4hrsja)
**Votes:** 10

This is basically my setup as well. Seems to work well. 

I set up a little raspberry pi that runs syncthing so that I can sync my phone and computers more easily. Also serves as a nice backup. 


## u/1-05457 [🔗](https://www.reddit.com/r/emacs/comments/ad8gzq/comment/edf3z02)
**Votes:** 10

If you're using the default VIM mode keybindings, it sounds like you've really fallen for the VIM-style modal workflow.

Also, if you find Spacemacs slow (particularly at startup), try DOOM Emacs.


## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/bz9rxn/comment/eqrgff3)
**Votes:** 10

Simple, but I think it counts as a tip: [minions](https://github.com/tarsius/minions) by [u/tarsius_](https://www.reddit.com/user/tarsius_). It allows one to invert the usual logic of managing minor modes lighters in the mode-line. While the default behaviour is "show everything by default, unless you diminish/delight it", `minions` goes in the opposite direction and hides all of them, unless you explicitly tell it to show them. Besides, the minor modes are still available, alongside some other common ones which thus become more easily accessible, in a menu which aggregates minor modes in the mode-line. Pretty neat.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/b2uqdm/comment/eivtuo4)
**Votes:** 10

If I have a file that I'd like to live in a Git repo, but I don't want to have to bother telling Git to ignore it, I'll just create it elsewhere but then put a line at the top like:

```elisp
# -*- default-directory: "/path/to/repo/"; -*-

```
The file then lives in the repo for nearly all intents and purposes, despite being stored elsewhere.  For example, if I run a shell command while visiting the file, the shell is run in the repo directory; Projectile commands can be run in the repo while visiting the file; etc.  Very handy!


## u/marcowahl [🔗](https://www.reddit.com/r/emacs/comments/b058f8/comment/eicbkxv)
**Votes:** 10

In the spirit of `incf` this is an abstraction to toggle a truth value without having to write the place twice.

E.g. write `(togglef foo)` instead of `(setf foo (not foo))`.

```elisp
(defmacro togglef (place)
  "Toggle PLACE.  non-nil becomes nil, nil becomes t.
PLACE may be a symbol, or any generalized variable allowed by ‘setf’.
The return value is the new value of PLACE."
  `(setf ,place (not ,place)))

```
&#x200B;


## u/robotreader [🔗](https://www.reddit.com/r/emacs/comments/b058f8/comment/eicaf0h)
**Votes:** 10

Bit of code to flip the window split from horizontal to vertical or vice versa.  Been in my .emacs almost since the beginning, so unfortunately I don't know where I got it from.  I use it when I switch emacs from half screen to full screen.

```elisp
;;flips two windows from horizontal split to vertical split
(defun rotate-windows ()
  (interactive)
  (if (= (count-windows) 2)
      (let* ((this-win-buffer (window-buffer))
             (next-win-buffer (window-buffer (next-window)))
             (this-win-edges (window-edges (selected-window)))
             (next-win-edges (window-edges (next-window)))
             (this-win-2nd (not (and (<= (car this-win-edges)
                                         (car next-win-edges))
                                     (<= (cadr this-win-edges)
                                         (cadr next-win-edges)))))
             (splitter
              (if (= (car this-win-edges)
                     (car (window-edges (next-window))))
                  'split-window-horizontally
                'split-window-vertically)))
        (delete-other-windows)
        (let ((first-win (selected-window)))
          (funcall splitter)
          (if this-win-2nd (other-window 1))
          (set-window-buffer (selected-window) this-win-buffer)
          (set-window-buffer (next-window) next-win-buffer)
          (select-window first-win)
          (if this-win-2nd (other-window 1))))))
    
```


## u/Vurpius [🔗](https://www.reddit.com/r/emacs/comments/b5n1yh/comment/ejjcd82)
**Votes:** 10

I just learned that savehist is a thing. It makes minibuffer history persist across sessions. Enable it with `(savehist-mode 1)`.


## u/permafrosty [🔗](https://www.reddit.com/r/emacs/comments/2wh9o0/comment/coqw3ui)
**Votes:** 10

For quick note-taking, there is deft:

http://jblevins.org/projects/deft/


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


## u/c17g [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f65iky4)
**Votes:** 10

I just found out the great variable `org-extend-today-until`. Basically it defines when your day really ends. If you sleep late, check it out.

Goodbye to the days using `M-x org-todo-yesterday` at midnight, clocking off items before sleep..


## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f848ctb)
**Votes:** 10

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
**Votes:** 10

My fav org-bullets config, looks nice and you immediately know which headline level you have:

```elisp
    (setq org-bullets-bullet-list
      '("⓪" "①" "②" "③" "④" "⑤" "⑥" "⑦" "⑧" "⑨"))
```


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/kvmmq3/comment/gj9ioly)
**Votes:** 10

Just a cool concept: if you have a keypad on your keyboard which you rarely use, bind its nums to something useful. The results are numlock-sensitive and are NOT the same keycodes as regular numbers, so they're just free keys. For example, `(define-key map (kbd "<kp-1>") 'winum-select-window-1)`


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


## u/noi-gai [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8mo1bz)
**Votes:** 10

Put the control keys next to space, mimicking mac's command key (which is effectively used as the equivalent of ctrl yet next to the space it's easier to press)

Win - Alt - Ctrl - Space - Ctrl - Alt - Win


## u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/112t0uo/comment/j8mpo5y)
**Votes:** 10

Does anyone have an `aspell` setup with multiple dictionaries?

When I try `--extra-dict` option I get an error: `Expected language "en" but got "de"`.


## u/ahk-_- [🔗](https://www.reddit.com/r/emacs/comments/bdrdx0/comment/el0glnh)
**Votes:** 10

I think the best productivity package for emacs is by far nyan-mode. I cannot live without it!

Life is too depressing to code without nyan-mode. Best productivity tool in the world!


## u/11fdriver [🔗](https://www.reddit.com/r/emacs/comments/bdrdx0/comment/el0a1dw)
**Votes:** 10

Moving, marking & killing by paragraph.

I've been using these for a while as a customisation of Xah-Fly-Keys, but I was surprised to learn that many Emacs users don't know this functionality.

I find that moving by paragraph is a good middle ground between moving vertically by line and moving vertically by page, it's a very natural hierarchy.

I move the page to display the correct section of the buffer, then I move to the right paragraph, then to the right line within that paragraph.

The default bindings for movement are annoying, `M-{`/`M-}`, but it's simple enough to rebind them to `M-[`/`M-]`.

You can mark the current paragraph with `M-h`.


## u/andrmuel [🔗](https://www.reddit.com/r/emacs/comments/jn6m14/comment/gb502ps)
**Votes:** 10

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


## u/kickingvegas1 [🔗](https://www.reddit.com/r/emacs/comments/x7zfs2/comment/innk62a)
**Votes:** 10

TIL when working with an Org table that `S-RET` will fill the current cell value with the value above it. <https://lists.gnu.org/archive/html/emacs-orgmode/2010-03/msg00462.html>


## u/hairlesscaveman [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/ikwhvfs)
**Votes:** 10

Question: I generally work with 3 vertical panes, with my preferred layout as left for code, middle for related test file, and right for test output or magit. However, keeping this layout is tricky; sometimes magit will open in the first pane, or the current pane when I'm focused in the middle, and deadgrep will open just anywhere… well, it's quite hectic and feels random.

Is there any way I can get files to open in panes 1 or 2, and always have things like magit/test-output/deadgrep/etc on pane 3? I've tried "shackle" but I've had no success with it; everything seems to open in a horizontal pane at the bottom of my screen regardless of config.

Any suggestions would be appreciated!


## u/Krautoni [🔗](https://www.reddit.com/r/emacs/comments/ja97xs/comment/g8pgyy1)
**Votes:** 10

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


## u/kastauyra [🔗](https://www.reddit.com/r/emacs/comments/ev2q9q/comment/fftpfj0)
**Votes:** 10

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


## u/PotentiallyAlice [🔗](https://www.reddit.com/r/emacs/comments/n9q662/comment/gxx6frj)
**Votes:** 10

I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

```elisp
(defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

```
Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "* TODO test reminder" line into my todo.org. Neat!


## u/primitiveinds [🔗](https://www.reddit.com/r/emacs/comments/erro41/comment/ff90d5b)
**Votes:** 10

I just now figured out that you can interactively pass flags to commands like `counsel-rg` by putting the `--` separator between the flags and the search string, so something like `-g '*.txt' -- whatever` will search for `whatever` only in `txt` files. `counsel` uses a function called `counsel--split-command-args` to split the parts before and after the `--`.


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


## u/isamert [🔗](https://www.reddit.com/r/emacs/comments/vskthv/comment/if1ua6o)
**Votes:** 10

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


## u/jstad [🔗](https://www.reddit.com/r/emacs/comments/cdu7cd/comment/eu8781u)
**Votes:** 10

When testing out or learning elisp, I have found [eros](https://github.com/xiongtx/eros) to be indispensable.

It shows an overlay inline with the function to display the evaluated result.


## u/char1zard4 [🔗](https://www.reddit.com/r/emacs/comments/v2by7z/comment/iarzi1s)
**Votes:** 10

This week I learned that:

-	You can redefine all yes/no prompts to y/n:
`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-	`C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-	Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:
https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7


## u/donio [🔗](https://www.reddit.com/r/emacs/comments/125h640/comment/je5ewfu)
**Votes:** 10

I've been looking for ways to take advantage of the emacs-29's package-vc.el from use-package. So far I have found [vc-use-package](https://github.com/slotThe/vc-use-package) and it seems to be working well so far.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvrlu40)
**Votes:** 10

I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it.  If it isn't, the command operates on the text from point to the end of the buffer.  That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time.  Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add:  Sorry folks, this doesn't work like I thought it did.  See the coments below for details.


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/cz1xt6/comment/eywhkq6)
**Votes:** 10

If you want executions of keyboard macros to undo in a single step, you can use the `undo-amalgamate-change-group` function that was added in Emacs 26.1:

```elisp
(defun block-undo (fn &rest args)
  (let ((marker (prepare-change-group)))
    (unwind-protect (apply fn args)
      (undo-amalgamate-change-group marker))))

(dolist (fn '(kmacro-call-macro kmacro-exec-ring-item))
  (advice-add fn :around #'block-undo))

```
And, of course, you can add that advice to other ready made functions you'd like to be able to undo in a single step. (I haven't found any examples in stock Emacs other than keyboard macros, where I want that behavior and don't already have it, but who knows). For new commands you write, it is probably best to use `undo-amalgamate-change-group` in the implementation rather than advising.


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/g5bat3/comment/fo362s8)
**Votes:** 10

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


## u/11fdriver [🔗](https://www.reddit.com/r/emacs/comments/br7q0y/comment/eob6fj2)
**Votes:** 10

I'm sure that this is something that people already knew about, but I had no idea until today.

I often start selecting text and realise that I actually want a rectangular selection (or the other way round). 

I found out that rather than having to jump to the mark, cancel, and restart, I can just press `C-x SPC` `[xfk: SPC r SPC]` `(rectangle-mark-mode)` and Emacs will toggle to the other selection scheme.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/cgptj7/comment/eulon9b)
**Votes:** 10

Recently I started getting annoyed by a new phenomenon: Messages sent to the echo area would only persist for a second or less before disappearing.

I tracked the problem down to the Slack package, where a timer callback was doing `(message "")` regularly.  The context suggested this was meant to erase transient messages like "so-and-so is typing...", but it was happening all the time.  I didn't see any clear way to prevent that timer callback from sending an empty message, so...

```elisp
(defun empty-slack-message-p (format &rest args)
  (and (string= format "")
       (catch 'slack
         (mapbacktrace
          (lambda (_ f _ _)
            (when (and (symbolp f) (string-match-p "slack-" (symbol-name f)))
              (throw 'slack t)))))))

(advice-add 'message :before-until #'empty-slack-message-p)

```
I hadn't known about `mapbacktrace` before.  Handy!


## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/w3gx6o/comment/ih6ievs)
**Votes:** 10

Emacs has better long-lines support now??

I just noticed while looking at the latest additions in the NEWS:

>\*\* Emacs is now capable of editing files with arbitrarily long lines. The display of long lines has been optimized, and Emacs no longer chokes when a buffer on display contains long lines.  If you still experience slowdowns while editing files with long lines, this is either due to font locking, which you can turn off with M-x font-lock-mode or C-u C-x x f, or to the current major mode or one of the enabled minor modes, in which case you should open the the file with M-x find-file-literally instead of C-x C-f.  The variable 'long-line-threshold' controls whether and when these display optimizations are used.

That sounds like great news!  Does anyone know what went into it?

(edit to add: this was added some time this week.  I rebuild from master weekly, and check out the NEWS diff each time)


## u/yogsototh [🔗](https://www.reddit.com/r/emacs/comments/qgrpte/comment/hi8crmc)
**Votes:** 10

I just made this nice combination of emacs packages and personal theme to achieve the cool effect of iAWriter

See here: https://her.esy.fun/posts/0021-ia-writer-clone-within-doom-emacs/index.html


## u/shitterwithaclitter [🔗](https://www.reddit.com/r/emacs/comments/s7lac1/comment/htnz373)
**Votes:** 10

I recently had the idea to start emacs in org-mode but have a src block at the top so I can still write elisp snippets easily. Here's the code for anyone interested:

```elisp
;; start in org-mode with a source block for lisp evaluation
(setq initial-major-mode #'org-mode
      initial-scratch-message "#+begin_src emacs-lisp\n;; This block is for text that is not saved, and for Lisp evaluation.\n;; To create a file, visit it with \\[find-file] and enter text in its buffer.\n\n#+end_src\n\n")
```


## u/yyoncho [🔗](https://www.reddit.com/r/emacs/comments/dbq80r/comment/f2fneae)
**Votes:** 10

evil-mode trick: you could use elisp in the `ex` when using substitute command using `\,`, for example: 

```elisp
%s/.*/\,(line-number-at-pos) \0 \,(length \0)
```

This will add line number at the beginning of the line and the line length in the end.


## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/ei02j1/comment/fcmosxh)
**Votes:** 10

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


## u/jalihal [🔗](https://www.reddit.com/r/emacs/comments/cjopqe/comment/evfx0a3)
**Votes:** 10

Awesome trick I learned yesterday. C-x r N numbers the lines in a region!


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


## u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/lfww57/comment/gmtk79e)
**Votes:** 10

From the mailing list, I've just learned of `generic-x.el`, which provides syntax highlighting for `/etc/fstab` or `/etc/passwd` and the like.  I appreciated that vim provided that out of the box and I was surprised that emacs also does, but it's just disabled.

`(require 'generic-x)` to enable it.


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


## u/blankspruce [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnrdt9x)
**Votes:** 10

Is there a package similar to wdired or wgrep that would work on magit diffs? 

Particular use cases I have in mind are:

1. You've prepared a commit for pull request and during review someone spotted a mistake that's present in multiple files of that commit. Usually I grep the mistake and edit only affected files with wgrep (there might be some arbitrary reason to not fix similar issue in files not present in the commit).
2. In C++ it happens sometimes that you want to separate declaration and definition and in your commit you forgot to move some definitions to .cpp.
Usually I switch to `foobar.hpp`, kill the necessary part, switch to `foobar.cpp`, yank that part.


## u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/polxft/comment/hcxub77)
**Votes:** 10

If you write Lisp, there's a couple of old Emacs commands to help you write code while keeping parenthesis balanced: `M-(` (`insert-parenthesis`), and `M-)` (`move-past-close-and-reindent`). They used to be documented in old Emacs manuals, but presumably their description was removed to make room for other content.

With a prefix argument, `M-(` wraps in parenthesis that number of sexps. For example with point represented as "*":

*foo -> C-u 1 M-( -> (foo)

There's more information in EmacsWiki: https://www.emacswiki.org/emacs/InsertPair


## u/andyjda [🔗](https://www.reddit.com/r/emacs/comments/yqciht/comment/iw00xhx)
**Votes:** 10

I started using `god-mode`, but I found it hard to get used to it at first: there was no easy way to check what command would be triggered by what key-sequence. 

I wrote up a `god-mode`\-specific `describe-key`, which translates `god-mode` key-sequences into commands and shows their usual description. I think it's a great way to get familiar with how the package handles keys, and it allows users to invoke `describe-key` without leaving god-mode (previously, most keys would just show information about the generic `god-mode-self-insert-command`)

I also reached out to the package's maintainers, and this feature (after some tweaking) [just got added to the master branch](https://github.com/emacsorphanage/god-mode). It was a great way to get familiar with `god-mode` code and its behavior, and I'm happy to have made my first contribution to an Emacs package.


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


## u/takethecannoli4 [🔗](https://www.reddit.com/r/emacs/comments/audffp/comment/eh7lrj3)
**Votes:** 9

May I suggest you post this to the Emacs Wiki?


## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/9ajxqj/comment/e4w2ych)
**Votes:** 9

Very nice post!  Although I certainly do not deserve to be named in the company of such pillars of the community as Sacha, John, and Nicolas.

You should consider starting a "real" blog to host these more permanently (or more personally and visibly, at least).  I often find myself reading blog posts from a few years ago about Emacs/Org, but old Reddit posts like this are probably more likely to fade into obscurity.  Then you can write a blog post about blogging with Emacs/Org!  ;)  You could also then submit it to Planet Emacs for syndication.  Kaushal Modi has set up a beautiful blog with Org and Hugo, so you might use it as an example.

Thanks for sharing your insights!


## u/Timesweeper_00 [🔗](https://www.reddit.com/r/emacs/comments/ijmgtx/comment/g3fdvf7)
**Votes:** 9

FYI lsp-mode has added support for pyright as a language server, Microsoft is deprecating the c# python language server in favor of pylance (proprietary and closed source), which uses pyright.


## u/zreeon [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efh2l6r)
**Votes:** 9

Those weird keys like C-m that actually send other keys like RETURN? From our terminal days?

You can convince GUI Emacs to let you bind them, which opens up a whole slew of new easy-to-reach keybindings.

https://emacs.stackexchange.com/questions/20240/how-to-distinguish-c-m-from-return


## u/10q20w [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efgr3wm)
**Votes:** 9

Idea: this thread could also double as a "help me implement this" thread.

Speaking of that: Is it possible to get atom-like completion suggestions before you start typing? I'm doing some CSS, and in Atom when you write something like

```elisp
.exampleclass {
    text-align: <cursor here>
}

```
Atom would give autocomplete-suggestions, displaying a list of possible values like "center", "left", "right". Company doesn't seem to do this.


## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/910pga/comment/e2urd08)
**Votes:** 9

Thanks, I like seeing workflow of others so I can steal because I'm a great artist.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/b5n1yh/comment/ejfk1o0)
**Votes:** 9

When I pretty-print JSON, I prefer to have arrays of numbers printed all on one line:

```elisp
(with-eval-after-load 'json
  (defun encode-json-array-of-numbers-on-one-line (encode array)
    (let* ((json-encoding-pretty-print
            (and json-encoding-pretty-print
                 (not (loop for x across array always (numberp x)))))
           (json-encoding-separator (if json-encoding-pretty-print "," ", ")))
      (funcall encode array)))
  (advice-add 'json-encode-array :around #'encode-json-array-of-numbers-on-one-line))

```
Before adding the advice. `(json-encode '((foo . [1 2 3]) (bar . [3 4 5])))` produces:

```elisp
{
  "foo": [
    1,
    2,
    3
  ],
  "bar": [
    3,
    4,
    5
  ]
}

```
After:

```elisp
{
  "foo": [1, 2, 3],
  "bar": [3, 4, 5]
}
```


## u/eeeickythump [🔗](https://www.reddit.com/r/emacs/comments/2wh9o0/comment/coqx59d)
**Votes:** 9

I've used orgmode a lot, Evernote only a little. Orgmode works best when your notes are stored as "children" within a single large outline (or a small number of outlines, e.g. one per large topic). Storing hundreds of individual files doesn't work so well.

It is possible for orgmode to display images inline by default - there is a variable that controls that (org-startup-with-inline-images) or you can put this line in your orgmode file:

```elisp
#+STARTUP: inlineimages

```
You can apply multiple tags to items in orgmode files, although the tags are crowded onto the end of the item's headline so it can get cumbersome and ugly if an item has more than a handful of tags.

Adding notes can be done very efficiently in orgmode using the "capture" functionality. You can add notes based on different predefined templates by associating each template with a hotkey. You can even set up a bookmark in your web browser that will add highlighted text as a new orgmode note when you click it (see "org-protocol" in the manual).

The main problem with orgmode is that it is completely dependent on Emacs. Essentially no other program understands the orgmode format, beyond the fact that it is based on plain text. There is a mobile app ([MobileOrg](http://mobileorg.ncogni.to/)) but it seems to be seldom updated and I have not found it useful. There are also modules that attempt to integrate orgmode with [Toodledo](http://toodledoo.com) and [Trello](http://trello.com). However, if you rely on orgmode then you should be aware that it will be difficult for you to interact with your notes database when you are not sitting in front of a computer running Emacs.

For notes, I use [SimpleNote](http://simplenote.com) (free, available for most platforms + web client, syncs your notes, plain text but also allows tagging of notes). For task management, orgmode is the most powerful piece of software available -- no contest -- but because of its mobile-unfriendliness I have ended up using OmniFocus instead (Mac/iOS only).

Another alternative to consider is Microsoft OneNote, which is free and is available on a lot of platforms. I don't think MS will be disappearing any time soon.


## u/globalcandyamnesia [🔗](https://www.reddit.com/r/emacs/comments/o68i0v/comment/h31xz50)
**Votes:** 9

If you're using the mark setting commands to expand a selection like `M-@` (mark next word) or `C-M-@` (mark next sexp), you can swap the point and mark (`C-x C-x`) and the selection will be expanded to the left rather than the right.

So if you're in the middle of a sentence, you can press `M-@` a few times to select some words to the right, press `C-xx`, and press `M-@` a few more times to add words before the selection.


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/hij4ga/comment/fwt1k27)
**Votes:** 9

Registers: in Emacs from the beginning, so simple you forget how insanely useful they can be. I use them to save text, windows, and locations. https://orys.us/tv


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


## u/primitiveinds [🔗](https://www.reddit.com/r/emacs/comments/domrl6/comment/f5oz3bp)
**Votes:** 9

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


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/dyhkcd/comment/f82dxoa)
**Votes:** 9

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


## u/martinslot [🔗](https://www.reddit.com/r/emacs/comments/kdgv43/comment/gfwlm9q)
**Votes:** 9

I need to try to do something custom to eshell so it feels more like home: http://www.modernemacs.com/post/custom-eshell/. Also set som aliases up.

How does your eshell look like?


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/x7zfs2/comment/inqralq)
**Votes:** 9

I use follow-mode (built-in to #emacs) to split reading buffers across modern wide screens to use all the real estate. The mode keeps the panes in sync with eachother. http://images.toryanderson.com/follow-mode.gif


## u/edumerco [🔗](https://www.reddit.com/r/emacs/comments/8toivy/comment/e197dit)
**Votes:** 9


>From a structural point of view, I use a single file for my contacts using several sub-headers to sort these contacts according to family, friends, colleagues, etc.

This headings structure is simple but it may limit each entry to 1 group only. What do you think about using tags for grouping?

In this way, each contact may be in N groups. This also means that they are easy to filter, or you could send a mail to a while group with 1 tag...


## u/ProfessorSexyTime [🔗](https://www.reddit.com/r/emacs/comments/bj0ti2/comment/emgahec)
**Votes:** 9

I guess if no one has learned yet, you can create external shell scripts for Emacs with having just

```elisp
!#/usr/bin/env bash
":"; exec emacs --script "$0" -- "$@"

```
At the top of a script and any elisp code you write under that will be ran when you run the script.

I don't know many fancier things to do with it than that. Some people probably know more.


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/bj0ti2/comment/em5rxk7)
**Votes:** 9

To log in to Slack I need a client ID, a client secret, and a token.  I don't want to store them in my `.emacs` file, since I share that publicly.  I looked at authinfo, but that seems to just map names to single passwords.  So what I do is, I put those three items in a Lisp property-list in a file `~/.slack-credentials` that only I can read:

```elisp
(:client-id "my-id"
 :client-secret "my-secret"
 :token "my-token")

```
Then in my `~/.emacs` I do this:

```elisp
(use-package slack
  ;; ... stuff omitted ...
  :config
  (let ((credentials (with-temp-buffer
                       (insert-file-contents-literally "~/.slack-credentials")
                       (read (current-buffer)))))
    (apply #'slack-register-team
           :name "emacs-slack"
           :default t
           :full-and-display-names t
           credentials)))
```


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


## u/luiggi_oasis [🔗](https://www.reddit.com/r/emacs/comments/zred55/comment/j14grej)
**Votes:** 9

What's the deal with all these completion framework I keep hearing about? Vertical elm ivy company and whatnot.

I think I have company in my init.el but I'm not even sure I'm actually using it (maybe I am and I'm just unaware). But why are they everywhere? I see them mentioned in at least every any two emacs threads.


## u/Ramin_HAL9001 [🔗](https://www.reddit.com/r/emacs/comments/d8k4ce/comment/f1blal2)
**Votes:** 9

I'm on Emacs 26.1, I just tried the [`M-x butterfly`]( https://www.xkcd.com/378/ ) command to see what would happen.

Wouldn't you know it, it actually does something that isn't an error message!


## u/aartist111 [🔗](https://www.reddit.com/r/emacs/comments/heaoiu/comment/fvrw4cu)
**Votes:** 9

Found / c for M-x ibuffer.  It filters buffers by content.
It looks like  'grep -c' .   Very helpful to locate a file quickly for which you remember any word from content
Until now I had only used filters for filenames or modes only. .


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/ojzv53/comment/h55vkl6)
**Votes:** 9

I often find myself wanting to be able to switch between `master` and a feature branch in magit quickly:

```elisp
(defun lw-magit-checkout-last (&optional start-point)
    (interactive)
    (magit-branch-checkout "-" start-point))
(transient-append-suffix 'magit-branch "w"
  '("-" "last branch" lw-magit-checkout-last))

```
So that `C-x g b -` switches to the last branch I was on, similar to `cd -`.


## u/pedzsanReddit [🔗](https://www.reddit.com/r/emacs/comments/ydsjfy/comment/itw7yp2)
**Votes:** 9

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


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/o0zvb5/comment/h1znz1s)
**Votes:** 9

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


## u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/q2g1gq/comment/hfldw8n)
**Votes:** 9

One of the most useful bindings for me:

```elisp
(use-package avy
  :bind* (("C-j" . avy-goto-char-timer)))
```


## u/Nondv [🔗](https://www.reddit.com/r/emacs/comments/108zin2/comment/j4ct1y1)
**Votes:** 9

Maybe not new for anyone but I only recently found out that `C-c <any letter>` is conventionally reserved for user bindings. I was constantly afraid to define my own bindings bc of a potential clash so tended to use M-x instead. Now I finally bind my most used commands.

With the above in mind, Im also afraid to forget my bindings. I use which-key package so I wrote a function "define-my-keybinding letter fn" which binds the letter to `C-c <letter>` and to "my-bindings" keyset (prefix) which itself is bound to `C-c m`. Basically, if i forget what bindings I use, I just press C-c m and which-key shows me all of MY bindings (yes, it shows them with C-c too but it's mixed with mode bindings so not helpful)


## u/11fdriver [🔗](https://www.reddit.com/r/emacs/comments/mpwapo/comment/gufsfeu)
**Votes:** 9

Sometimes I'm working on programs with functions a few pages long, and `follow-mode` means that I can open two windows of the same buffer side-by-side and have the text flow like a book between them. I can double or even triple the amount of lines I can view at one time.

This has largely superseded what I might have used those code-overview map things for, which is difficult anyway, since I like to use Emacs from the terminal.

It will keep the text aligned as you move through the file, and pairs well with binding `<mouse-5>` and `<mouse-4>` to the `scroll-up/down-line` commands in `xterm-mouse-mode`.

If I'm studying/notetaking, I often end up with a few Emacs-windows arranged in a vertical stack. `winner-mode` or `window-configuration-to-register` are great, but if I want to quickly regain some vertical screen-real-estate without messing up the layout, then it's pretty intuitive to use `follow-mode` and just switch multiple windows to the same buffer, now they behave like one.


## u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/pxqvtm/comment/hexdfiq)
**Votes:** 9

Replace the binding for `count-words-region` with `count-words`. The latter has better semantics: it only shows words in region if the region is active.

`(global-set-key (kbd "M-=") #'count-words)`


## u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/k4gv0x/comment/ge8si78)
**Votes:** 9

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


## u/rberaldo [🔗](https://www.reddit.com/r/emacs/comments/rbmfwk/comment/hnvaab8)
**Votes:** 9

A tiny thing I just noticed: in `tex-mode`, you can create a new environment with `C-c C-e`. With the universal argument (`C-u C-c C-e`), however, you can easily change any environment into another.

I created an `enumerate` environment and immediately changed my mind. By chance, I intuitively tried the aforementioned command and I was instantly able to change the environment into `itemize`.

EDIT: markdown


## u/emacsomancer [🔗](https://www.reddit.com/r/emacs/comments/gqsz8u/comment/fs5sq09)
**Votes:** 9

Preconfigured Emacs for collaborative writing (using a literate, self-generating init):

https://gitlab.com/emacsomancer/collaborative-writing-environment-emacs

Not a huge, lots-of-packages configuration, but with a focus on writing (org-mode, fountain), including version control (magit). 

Each person gets a different colour to indicate the part of the file they’re editing: (Screenshot (from the alternative world in which Cory Doctorow  co-wrote _For the Win_ in Emacs):)

https://imgur.com/a/zvfLpdH


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/d5dwkq/comment/f0mf4g0)
**Votes:** 9

I frequently want to copy a long, one-line JSON expression into a fresh buffer and pretty-print it there, and I recently got tired of performing all the steps myself.  Now I can just put the cursor on the first character of the string, and run this command:

```elisp
(defun view-pretty-json (begin)
  (interactive "d")
  (let ((buffer (current-buffer))
        (end (save-excursion (forward-sexp) (point))))
    (switch-to-buffer (generate-new-buffer "*json*"))
    (insert-buffer-substring buffer begin end)
    (json-pretty-print (point-min) (point-max))
    (goto-char (point-min))))
```


## u/BunnyLushington [🔗](https://www.reddit.com/r/emacs/comments/12zaqju/comment/jhrzybp)
**Votes:** 9

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


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/12cd23k/comment/jf3ohpv)
**Votes:** 9

I work with multiple Git repositories in my day job, but one in particular occupies 95% of my time.  I've often wished I could set up Projectile so that if I run one of its commands while not in any repo, it will behave as if I'd changed to that main repo first.  I couldn't find a built-in way to do that, but got the effect I wanted with some advice:

```elisp
(defun default-to-main-project (dir)
  (or dir *main-project-dir*))

(advice-add 'projectile-ensure-project :override #'default-to-main-project)

```
I lose some of the functionality of `projectile-ensure-project`, but I never used it anyway.


## u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/14l3jn8/comment/jpwn2ts)
**Votes:** 9

I was converting some old `.odt` notes files to `.org` today, and one of the things I wanted to do was to add two spaces after end of sentence periods for proper sentence navigation.  So there I was figuring out a general enough regexp for the nth time and, of course, I regretted not having taken note of this the last time. So I decided to do some searching for a good regexp and write it down this time, since this was obviously shared by someone somewhere. And it turns out Emacs has us covered, and I never knew: `repunctuate-sentences`. I have no idea if this is new or has always been there. It is new to me. It uses `query-replace-regexp`, so it's the same experience. And also can be configured for exclusions with `repunctuate-sentences-filter`. Neat!


## u/akirakom [🔗](https://www.reddit.com/r/emacs/comments/aikrx5/comment/eep53ec)
**Votes:** 8

Subscribe to Emacs News by Sacha Chua: http://sachachua.com/blog/category/geek/emacs/emacs-news/


## u/thehaas [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeujw0a)
**Votes:** 8

Replace a string in a bunch of files in a directory. I got this from https://emacs.stackexchange.com/a/13719

 - run  `helm-ag` and search for a string
 - on the results, do `C-c C-e` to get it into an editable buffer
 - make the changes
 - `C-c C-c` to commit all the changes


## u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/aja311/comment/eeujoxp)
**Votes:** 8

if I may, r/france has weekly threads about a few topics, I find it very nice, sometimes attendance shrinks a little, but there's always nice things to read.

I always found I learned more about emacs by hearing others ideas rather than manual or code.. workflow ideas don't necessarily come from mastering atoms. 

So I find this thread a very very nice idea. (thanks)


## u/tareefdev [🔗](https://www.reddit.com/r/emacs/comments/also27/comment/efid3tn)
**Votes:** 8

Install [Wttrin](https://github.com/bcbcarl/emacs-wttrin) package, and then add the following to your `.emacs`:

```elisp
(require 'wttrin)
(setq wttrin-default-cities '("XXXXX"))
(setq wttrin-default-accept-language '("Accept-Language" . "en-US"))
(global-set-key (kbd "s-w") 'wttrin)

```
Put your city name instead of XXXXX and change the key binding to whatever you want. Now you can check the weather condition without leaving Emacs :)


## u/nullman [🔗](https://www.reddit.com/r/emacs/comments/aoqcyl/comment/egfq7uz)
**Votes:** 8

Inspired by paradox's use of a spinner while it run its asynchronous tasks, I wrote this macro:

```elisp
;; show a spinner while running an asynchronous task
(defmacro async-spinner (start-func &optional finish-func)
  "Return `async-start' command for given params with a spinner."
  (let ((spinner (spinner-start 'horizontal-moving))
        (result (gensym)))
    `(async-start
      (lambda ()
        (funcall ,start-func))
      (lambda (,result)
        (funcall ,spinner)
        (funcall ,finish-func ,result)))))

```
You need to have the *spinner* and *async* packages installed to use this.  It is called just like the *async-start* function.  Example:

```elisp
(async-spinner
 (lambda ()
   (sleep-for 2)
   99)
 (lambda (result)
   (message "result: %s" result)))
```


## u/xircon [🔗](https://www.reddit.com/r/emacs/comments/aoqcyl/comment/eg3l6bt)
**Votes:** 8

Hydra to edit system config files (for Manjaro/Arch based distros):

```elisp
(defhydra hydra-edit-conf (:color red
                              :hint nil)

  "Edit system configuration files"
  ("p"  (find-file (concat "/sudo::" "/etc/pacman.conf")) "pacman" :color blue)
  ("m"  (find-file (concat "/sudo::" "/etc/pacman-mirrors.conf")) "mirrors" :color blue)
  ("f"  (find-file (concat "/sudo::" "/etc/fstab")) "fstab" :color blue)
  ("s"  (find-file (concat "/sudo::" "/etc/sddm.conf")) "sddm" :color blue)
  ("g"  (find-file (concat "/sudo::" "/etc/default/grub")) "grub" :color blue)
  ("y"  (find-file "~/.config/yay/config.json") "yay" :color blue)
  ("z"  (find-file "~/.zshrc") "zsh" :color blue)
  ("q"   quit-window "quit" :color blue))

(global-set-key (kbd "C-c m") #'hydra-edit-conf/body)
```


## u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/b058f8/comment/eid928p)
**Votes:** 8

I got tired of electric-pair-mode adding a closing parenthesis after I typed a frowny emoticon (`:(` or `:-(`), so:

```elisp
(setq electric-pair-inhibit-predicate
      (lambda (c) (or (looking-back ":-?(" (- (point) 3)
                      (string-match-p (rx bos " *Minibuf-" (+ digit)) (buffer-name))
                      (electric-pair-default-inhibit c))))

```
(The matching against the buffer name had already been present.)


## u/chrchr [🔗](https://www.reddit.com/r/emacs/comments/as83e2/comment/egtxcbu)
**Votes:** 8

I was watching some Lisp Machine demos on youtube a few weeks ago, and I observed that pressing ")" in the REPL causes the expression to be evaluated immediately if the expression is complete. There is no need to press `enter` at the end of the expression to submit it. I wanted my Emacs to do that as well, so I cooked up the following thing: [electric-paren](https://www.emacswiki.org/emacs/electric-paren.el)

&#x200B;

It works in inferior-lisp-mode, ielm, slime, and, my favorite of all, eshell.


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/xdw6ok/comment/iodmtzu)
**Votes:** 8

I find it pretty useful (for debugging etc) to override the default projectile mode line indicator and show the projectile project type of the buffer instead, which can be done pretty easily if you're a use-package user with https://elpa.gnu.org/packages/delight.html:

```elisp
(use-package projectile
  :delight '(:eval (format " P[%s]" (projectile-project-type)))
  :config
  (setq foo "bar"))
```


## u/jalihal [🔗](https://www.reddit.com/r/emacs/comments/drw8i3/comment/f6mm087)
**Votes:** 8

TIL about `indent-rigidly` bound to `C-x TAB`. Very useful when indenting copy-pasted python code at different levels!


## u/NoisyFrequency [🔗](https://www.reddit.com/r/emacs/comments/kdgv43/comment/gfxbwgy)
**Votes:** 8

I tried [marginalia](https://github.com/minad/marginalia) with light annotation and selectrum, it works well. It displays commands' keybindings in the minibuffer. It's especially useful for modes I don't use daily and for which I haven't memorized the keybindings.


## u/fabiopapa [🔗](https://www.reddit.com/r/emacs/comments/f972tf/comment/firc94m)
**Votes:** 8

This may be common knowledge, but I’m always surprised at how few people know about this.

If you have an `ALTERNATE_EDITOR=''`environment variable, and start emacsclient with no emacs server running, it will start an emacs server and try starting emacsclient again. Also works with a `-a` flag on emacsclient command.


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


## u/WalterGR [🔗](https://www.reddit.com/r/emacs/comments/bdrdx0/comment/el427i0)
**Votes:** 8

What's the series of videos that are 'like Emacs Rocks but they show the keys being pressed'?

I vaguely recall someone mentioning something like that...


## u/attento_redaz [🔗](https://www.reddit.com/r/emacs/comments/wqjare/comment/iku77h0)
**Votes:** 8

Using [zotra](https://github.com/mpedramfar/zotra), [citar](https://github.com/emacs-citar/citar) and some parts of the Org-cite ecosystem I hacked together a highly experimental but pretty comfortable environment for working with "org-biblatex bibliographies" which are basically like [org-bibtex](http://gewhere.github.io/org-bibtex) but with biblatex entries represented as headings with suitable properties instead of bibtex. I have a function which retrieves a biblatex entry corresponding to an url using zotra and adds a corresponding Org heading with the biblatex fields as properties, and the entry becomes available in Citar as soon as I save the document. Citing these entries then works anywhere, even in the same document with a suitable `#+bibliography: my-org-biblatex-file.org` declaration. Exporting the citations also works with the CSL exporter, no conversion is necessary to a proper biblatex bibliography file (but can be easily done if one needs biblatex-based export). Since the bibliography is an Org document, tagging, agenda commands, column view etc. can all be used with the bibliography entries.  In a way it's frightening how much can be achieved building on already existing stuff and with a few lines of Emacs Lisp.


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/bj0ti2/comment/em7wbz1)
**Votes:** 8

I use the following macros to completing-read using different menus:

```elisp
(defmacro with-ivy (&rest body)
  `(let ((completing-read-function 'ivy-completing-read)
         (completion-in-region-function 'ivy-completion-in-region))
     ,@body))

(defmacro with-helm (&rest body)
  `(let ((completing-read-function 'helm--completing-read-default)
         (read-file-name-function  'helm--generic-read-file-name)
         (read-buffer-function  'helm--generic-read-buffer)
         (completion-in-region-function  'helm--completion-in-region))
     ,@body))

(defmacro with-ivy-explorer (&rest body)
  `(let ((completing-read-function 'ivy-completing-read)
         (completion-in-region-function 'ivy-completion-in-region)
         (ivy-posframe-hide-minibuffer
          (eq ivy-explorer-message-function #'ivy-explorer--posframe))
         (ivy-display-function 'ivy-explorer--display-function)
         (ivy-minibuffer-map (make-composed-keymap
                              ivy-explorer-map ivy-minibuffer-map)))
     (unwind-protect
         ,@body
       (posframe-hide ivy-explorer--posframe-buffer))))

(defmacro with-frog-menu (&rest body)
  `(let ((completing-read-function 'frog-menu-completing-read-function))
     ,@body))
```


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


## u/clemera [🔗](https://www.reddit.com/r/emacs/comments/csut1x/comment/exnlj3x)
**Votes:** 8

I started to append a custom suffix to my personal package customizations instead of using a prefix. For example instead of `clemera-org-occur` I would use `org-occur-clemera`. This way I use the same elisp "namespace" as the package with my own custom "sub namespace", which has some advantages for completions, searches and so on.


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


## u/shiroghost [🔗](https://www.reddit.com/r/emacs/comments/g11mp9/comment/fnd2p6p)
**Votes:** 8

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


## u/ragoneio [🔗](https://www.reddit.com/r/emacs/comments/blo25q/comment/emq00pa)
**Votes:** 8

Not sure if it will be helpful to anyone else - but working on legacy code, I often need to review code in large files with hundreds of errors and warnings. So I wrote a package which runs Flycheck on all the files in a diff and filters the output to only the lines which was modified or added! https://github.com/ragone/magit-diff-flycheck. I hope you like it - it’s currently in review to get published on MELPA.


## u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/z8ltei/comment/izxi6ie)
**Votes:** 8

A great functionality for calculating some schedule events is `org-evaluate-time-range` (`C-c C-y`). When I need to propose an event of a particular length, I use this to get my length right between two dates.


## u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/txh85s/comment/i3mghuf)
**Votes:** 8

I wanted to be able to expand yasnippets within other yasnippets (so here tab would jump to the next position instead of trying to expand snippet), surprisingly all I had to do was:

`:bind ("C-<tab>" . yas-expand)`

So C-<tab> expands a snippet within a snippet, and everything just worked as I'd hoped.  Once I'm done with the nested expansion <TAB> just moves on to the outer one. \*shrug\*


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


## u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/qgrpte/comment/hicheof)
**Votes:** 8

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


## u/zkryrmr [🔗](https://www.reddit.com/r/emacs/comments/fgahb2/comment/fk567v4)
**Votes:** 8

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


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/pb6w2z/comment/haddtq6)
**Votes:** 8

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


## u/karthink [🔗](https://www.reddit.com/r/emacs/comments/gi70ye/comment/fqfc1wi)
**Votes:** 8

AucTex users: You're missing out if you don't use [CDLatex](https://github.com/cdominik/cdlatex). It's primarily a fast input tool for LaTeX, sort of like snippet templates. The difference between setting up Yasnippet templates for LaTeX and CDLatex is that CDLaTeX's TAB key to jump past stuff is _always available_, not just during snippet entry. It's difficult to explain, so here are some demos:

1. [Fast input with cdlatex and preview.el](https://gfycat.com/heavenlynegligiblehoiho)
2. [Fast input with keys displayed](https://gfycat.com/safeidolizedlangur)

I wrote a longer post explaining [how I set up AucTex](https://www.reddit.com/r/emacs/comments/g8ecpj/advice_for_auclatex_what_keybinds_do_you_find/foo64ge/) recently.

CDLaTeX was written by Carsten Dominik, the author of Org-mode and reftex. Thus Org ships with an `org-cdlatex` minor-mode that makes these features available in org-mode.


## u/00-11 [🔗](https://www.reddit.com/r/emacs/comments/f1x0jq/comment/fh95nk7)
**Votes:** 8

1. You can just go to the value of `isearch-other-end`, which is the buffer position of the start of the last (i.e. current) search hit. E.g., `(goto-char isearch-other-end)` instead of doing `(isearch-repeat 'backward)` (when searching forward).

2. It's generally better to use `push-mark`, and then end your command with `(setq deactivate-mark  nil)`, rather than use

```elisp
     (let ((current-prefix-arg '(4)))
       (call-interactively 'set-mark-command))
```


## u/elimik31 [🔗](https://www.reddit.com/r/emacs/comments/118sowc/comment/j9jgfhg)
**Votes:** 8

The [texdoc](https://github.com/TeX-Live/texdoc/wiki/Tab-completion) CLI program provided by TeXLive allows opening the documentation of LaTeX packages given a package name as its argument (requires having the documentation installed locally). It is available from AUCTeX via the `TeX-documentation-texdoc` command, which I find useful. However, it requires typing out the package name by hand (though it uses symbol-at-point as the default). Recently I found that the texdoc wiki contains a snippet how to add [tab-completion](https://github.com/TeX-Live/texdoc/wiki/Tab-completion), which showed me where to get a list of installed TeXLive package names. I ported this to emacs lisp, and combined with `completing-read` and `call-process` to call the terminal command I created my own `my-texdoc` command with interactive candidate selection. It's available as [github gist here](https://gist.github.com/meliache/e645bf50c6aeac8e6e58b92c6bddac20), but I can also paste the code:

```elisp
(defun my-texdoc--get-package-list ()
  (let ((tlpdb-fpath (file-name-concat
                      (string-trim-right
                       (shell-command-to-string "kpsewhich -var-value TEXMFROOT"))
                      "tlpkg/texlive.tlpdb"))
        (name-regex "^name \\([^ \n.]+\\)$"))
    (with-current-buffer (find-file-noselect tlpdb-fpath 'nowarn 'rawfile)
      (save-excursion
        (goto-char (point-min))
        (cl-loop
         while (re-search-forward name-regex nil 'noerror)
         collect (match-string-no-properties 1))))))

(defun my-texdoc (pkg)
  "Show TeX documentation for package PKG.
If called interactively, select package from TexLive with interactive completion."
  (interactive
   (list (completing-read
          "texdoc: "
          (my-texdoc--get-package-list)
          nil nil nil
          'my-texdoc-history)))
    (call-process "texdoc" nil " *texdoc*" nil "--view" pkg))

```
My biggest problem is that I'm not sure how to share it with the emacs community, it doesn't seem like it's big enough to be its own package, I might post it on some wiki or in some aggregating package like `consult`, but I thought I would start with this reddit thread. I also might try to merge this into `AUCTeX`, however I'm not sure if that's actively developed anymore and the `TeX-documentation-texdoc` source code is much more complex than what I have, that I'm a bit worried to break something.

And to make this post a real tip: If you know some CLI program and can somehow get a list of possible arguments for that program, try writing your own emacs lisp command wrapper with `completing-read` support, it's fun and not that hard.


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


## u/Amonwilde [🔗](https://www.reddit.com/r/emacs/comments/j61aoh/comment/g7wd5gj)
**Votes:** 8

For some this will be obvious, but I'm sure there will be at least one person who will find this useful. One of the most amazing features of Emacs to me is dabbrev-expand, by default bound to M-/. 

> Expand previous word "dynamically".

> Expands to the most recent, preceding word for which this is a prefix.
I> ifno suitable preceding word is found, words following point are
considered.  If still no suitable word is found, then look in the
> buffers accepted by the function pointed out by variable

This command is essentially omni-autocomplete. Chances are, the term you're trying to complete is in the buffer you're using or another buffer, and you can hit multiple times to cycle through different completions. I find the expander to be quicker and more deterministic than language autocomplete about 70% of the time. It's especially useful in writing, if you use Emacs for things other than programming, as you can complete proper names and specalized vocabulary quickly.


## u/b3n [🔗](https://www.reddit.com/r/emacs/comments/oxo1xh/comment/h85cv7f)
**Votes:** 8

Little quality of life improvement if you work with multiple eshell buffers:

```elisp
(defun eshell-buffer-name ()
  (rename-buffer (concat "*eshell*<" (eshell/pwd) ">") t))

(add-hook 'eshell-directory-change-hook #'eshell-buffer-name)
(add-hook 'eshell-prompt-load-hook #'eshell-buffer-name)
```


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/oxo1xh/comment/h88ph29)
**Votes:** 8

This isn't a tip or trick, so I guess it is covered by &c. The Init File section of the manual has this example of setting a user email address:

```elisp
(setq user-mail-address "cheney@torture.gov")
```


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


## u/poinkalum [🔗](https://www.reddit.com/r/emacs/comments/p28rl5/comment/h8rdjx9)
**Votes:** 8

If you follow master, you can use the very useful command `execute-extended-command-for-buffer` by using `M-X` (with a capital "X"), that implements the behaviour described in [this blog post by Lars](https://lars.ingebrigtsen.no/2021/02/16/command-discovery-in-emacs/). It will only show commands that are relevant to the current major mode.


## u/xu_chunyang [🔗](https://www.reddit.com/r/emacs/comments/c550ht/comment/es1auve)
**Votes:** 8

Add a button in Help buffer to remove advice

- https://repl.it/@xuchunyang/advice-remove-button

You can even run the code to try it! (C-x o to change window, tab to move the Remove button, RET to remove the advice). BYW, Repl.it is very cool.


## u/diamondnbond [🔗](https://www.reddit.com/r/emacs/comments/us7zae/comment/i928gaj)
**Votes:** 8

[I Recently discovered engine-mode.](https://github.com/DiamondBond/emacs/blob/master/config.org#initialize-engine-mode)


## u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/d5dwkq/comment/f0q8vgo)
**Votes:** 8

This one is a life changer! Use `(setq set-mark-command-repeat-pop t)` so after pressing `C-u C-SPC` to jump to a mark popped off the local mark ring, you can just keeping pressing `C-SPC` to repeat! (It's like repeatedly running `repeat` with `C-x z z z z ...`).

I learned this from [Tony Ballantyne's blog](https://tech.tonyballantyne.com/emacs/workout/evil-emacs-1/).


## u/Sudo_Brew [🔗](https://www.reddit.com/r/emacs/comments/y1y0kq/comment/isazxjg)
**Votes:** 8

I've been unhappy with the behavior of `comment-line` moving the point down a line, and sometimes inexplicably commenting an inactive region. I'm also unhappy with `comment-dwim`'s single-line behavior of adding a comment at the end of the line. What I want is a function to comment/uncomment the region if its active, and comment/uncomment the line at point otherwise, so I made this quick little function:

```elisp
(defun my/comment-dwim ()
  "Comment region if active, else comment line.

This avoids the excess region commenting of `comment-line' while also avoiding the weird single-line
behavior of `comment-dwim'."
  (interactive)
  (save-excursion
    (if (use-region-p)
        (call-interactively #'comment-or-uncomment-region)
      (call-interactively #'comment-line))))
```


## u/konrad1977 [🔗](https://www.reddit.com/r/emacs/comments/z2jgt5/comment/ixpdxvf)
**Votes:** 8

I found this one-liner yesterday, added it it to the `:config` section of **Evil**.

  `(define-key evil-visual-state-map (kbd "u") 'undo)`  
Now I can undo in selected region. Like magic.


## u/khourhin [🔗](https://www.reddit.com/r/emacs/comments/uxcm6i/comment/i9x2a0i)
**Votes:** 8

Just discovered that you can do pull request from magit forge. Got some troubles with the origin / myfork setup and was helped by this issue: https://github.com/magit/forge/issues/278 .
And this improved as well how I deal with the naming of my remotes.

Pure awesomeness, thanks a lot for Magit/ Magit forge !


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


## u/w0ntfix [🔗](https://www.reddit.com/r/emacs/comments/l0ei0t/comment/gknr3hp)
**Votes:** 8

my first package is now on melpa! https://melpa.org/#/ct


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

