
## u/gausby
**Votes:** 35

Shouldn't it be `/etc/tips/trick` ?

(…I'll show myself out)


## u/jalihal
**Votes:** 35

I just discovered calc mode. Oh my ...
It does symbolic differentiation, solves linear equations, plots with gnuplot, and does matrix operations, all with a few key strokes!! How is this amazing tool not as popular as org mode or Magit?? I am so excited to explore it more, especially for the "display" options. I can see it taking my latex experience to the next level. 
Any tips on using calc mode?


## u/TheDrownedKraken
**Votes:** 33

Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.


## u/celeritasCelery
**Votes:** 29

> I had received the advice to not install anything and just start with plain emacs so I can learn emacs. ... it was miserable. 
> Enter Doom Emacs. I decided to give Doom Emacs a try because it was also highly recommended in my initial RFC, especially since it is designed for Vim users. In short I love it.

This is why I disagree with the subreddits de facto advice to “learn vanilla first”. People who have used emacs for a long time don’t realize how much **time** it takes to get it to level of a normal modern editor people are used to. I recommend distros to *everyone* who is new unless they are that certain personality type that wants to do everything themselves.


## u/unbelievable_sc2
**Votes:** 28

I often use the compile feature to compile and run my projects to see the results in the compilation buffer. This works well unless you are waiting for user input in your terminal. I recently found out that you can invoke the compile with an additional non nil value to start compilation in comint-mode which allows for user input! 
The drawback is, that you then no longer can press q to close the window or g to recompile. Because of that I added a simple lambda, that switches to compilation-mode to the compilation-finish-functions. So I can give input while compiling and running, and after compilation I can close the window as usual with q.


## u/github-alphapapa
**Votes:** 25

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


## u/[deleted]
**Votes:** 23

[removed]


## u/clemera
**Votes:** 21

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


## u/meekale
**Votes:** 21

Automatic `chmod +x` when you save a file that starts with a `#!` shebang:

```elisp
(add-hook 'after-save-hook 'executable-make-buffer-file-executable-if-script-p)
```


## u/fogbugz
**Votes:** 20

I do something similar. But for me a big epiphany was to realize all my organization files and all my wiki files should live in the same directory to allow for simple hyperlinks across them and a beautiful flat structure.

The only difference is that I don't version control some of my organization files. E.g. my tasks. But that's a .gitignore detail.

I prefer all my tasks to live inside a single org file, instead of letting my agenda view harvest them from multiple files. I keep a separate calendar file with fixed events and holidays.

A second epiphany was to realize the best way to manage my tasks is not the much hyped GTD, but a JIT approach: a kanban board. I've set up my agenda view to mimic a kanban. GTD has some nice ideas: keeping a few or ideally one inbox and differentiating between tasks, tasks with deadlines, tasks with scheduled times and events.

But it does have a big flaw, for my personal lifestyle and for many creative people. It doesn't control flow. It leads to creating way too many tasks, no prioritization, and task lists become outdated frequently. This requires a lot of wasteful updating.

In my case, I split my life into a few key areas, and I limit the number of ongoing tasks for each area. I've also gotten rid of GTD contexts. I'm content with states: TODO, PROG (progress), WAIT (blocked) and DONE.

Lastly, I do really believe organization systems are only half-baked if they don't integrate into a knowledge system. Why? Because some tasks are not actionable. They are just knowledge bits you want to store and reuse in the future. Here they explain it better than I do: https://praxis.fortelabs.co/gtd-x-pkm-8ff720ef6939/

I believe in keeping stuff simple. And learning from Japanese production systems, which got lots of things right and which we seem to be reinventing in bad ways. 


## u/clemera
**Votes:** 20

This week someone asked to show commit messages along the file listing in dired, similar to the repo view on github. I hacked together a snippet which does this, if there is interest I will improve it further and make a package for it. Here is the [gist](https://gist.github.com/clemera/9c27bc8a003ef404182bf8d0f7bc00a0). After evaluating press ")" in a dired buffer of a git project to show the commit messages.


## u/thehaas
**Votes:** 20

After using Emacs for maybe 10 years I finally started using registers and I really should have started earlier. For those who don't know:

&#x200B;

* Highlight text and C-x r s <char> to save to register <char>
* C-x r i <char> to put the contents of the register at the cursor point. The text is still there -- use it over and over again

It seems like quite a few keystrokes but it's really not. Of course you can re-assign them to other keys if you don't like the defaults.


## u/sugarbridalsentry
**Votes:** 20

I'm sure this was widely known, but wow-- 

So, I started seeing a "Vacuous Schema" message when loading a specific Org file, and couldn't figure out why. The message didn't give me much to go on, and the file is kind of huge, so going through possible solutions one at a time was slow and miserable.

I wished I could debug the message -- or turn on some sort of verbose mode or something. And that's when I discovered the `debug-on-message` variable. 

This variable lets you debug what happens to cause any message, using a regex to find the right message. 

So, in my simple case I just eval'd:

```elisp
(setq debug-on-message "vacuous schema")

```
And re-opened the file, and lo-and-behold, it told me exactly what I needed to know to fix the screwed up source block. 

It's just... awesome to be able to answer the age-old, "Huh, I wonder why it said that...?" situation so easily.


## u/oantolin
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


## u/TeMPOraL_PL
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


## u/surelynotmymainacc
**Votes:** 18

Not much of as trick, but something helped me as a newbie, which must be very obvious to many. When you want to see how to correctly configure a package or use a function in elisp, and when documentation is not helping you, go to GitHub and search like "use-package package-name", switch over to the code tab and change language to Emacs lisp. This will give you hundreds of examples.


## u/xu_chunyang
**Votes:** 18

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


## u/tryptych
**Votes:** 18

It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/).  There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.


## u/oantolin
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


## u/Quasimoto3000
**Votes:** 17

Dude look into Elpy. It's incredibly simple to set up and very powerful.

I couldn't recommend it more.

Edit: here's all the info you need.

https://github.com/jorgenschaefer/elpy/wiki/Installation

Let me know if you have any questions.


## u/c17g
**Votes:** 17

To avoid accidentally inserting text in invisible area (`...`) in Org files, set `org-catch-invisible-edits` to `error` or `show-and-error`. See docs.


## u/TheDrownedKraken
**Votes:** 16

It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.


## u/github-alphapapa
**Votes:** 16

Here's a popular Emacs config I just rediscovered.  Some cool stuff here.  https://github.com/angrybacon/dotemacs


## u/dakra
**Votes:** 15

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


## u/Thaodan
**Votes:** 15

[Which-Key](https://github.com/justbur/emacs-which-key)
Great to learn keybinds, especially for new emacs users:

```elisp
(use-package which-key
  :config (which-key-mode 1))
```


## u/natarey
**Votes:** 15

For ages, I've had a custom function in my init that will add numbers to the front of a list -- transforming this:

```elisp
line 1
line 2
line 3

```
Into:

```elisp
1. line 1
2. line 2
3. line 3

```
But I just discovered this functionality is *built in*!

To just add numbers, select a region and call `rectangle-number-lines` with `C-x r N`. 

To customize things like what number to start at, or the format of the numbers (to add periods or parens after the numbers, for example) call it with the universal argument and it'll go through the options with you.

Truly, the batteries are included with Emacs.


## u/laralex
**Votes:** 15

A small basic thing, but once I'd discovered it, I started using dired.
```C-x C-j``` is most likely bound to ```dired-jump```, and this function opens dired for this window's file, without promting for directory (and this prompt was an issue for my workflow when using ```C-x d```). That makes finding and switching files just as convenient as in OS GUI. I've also bound a few keys when in dired mode (I find them decent):

```a``` - prompt a name and create empty file

```d``` - prompt a name and create empty dir

```u``` - go to parent dir (the key is a mnemonic to "go Up in directory tree)

```j``` - if it's a dir go into it, otherwise find this file (key is near to 'u' so jumping up and down is not a big deal with one hand, also the 'j' is the easiest key for me as a touchtyper) 

```n```/```p``` - move one entry down/up, which resembles ```C-n```/```C-p```


## u/mogigoma
**Votes:** 15

Every time I see this package I think to myself "People exit Emacs?"


## u/swhalemwo
**Votes:** 15

finally found an opportunity to use git-timemachine: I was optimizing a Python function when I realized I needed to be sure it returned the same results as before. jumped to previous commit, C-c C-y f and voila. made me feel like a code wizard.


## u/cfraizer
**Votes:** 15

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


## u/clemera
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


## u/Ramin_HAL9001
**Votes:** 15

Here's a really old trick that I only just now started using: save window layouts to registers with `C-x r w <Letter>`, which saves it into a `Letter` key register. Switch between window layouts using the usual "register jump" `C-x r j`, that is `C-x r j <Letter>` to go back to the layout in that lettered register.

If you use registers for storing text or other things, be careful to use a different set of letters for text and a different set for window layouts. Personally I never use registers for storing text (`M-y` to rotate through the kill-ring is all I need). But I have been using the window layout one quite often lately.

It feels a little like having the multiple desktops feature common to many Linux desktop environments, but I can use it to switch between different projects I am working on within Emacs without needing multiple instances of Emacs.


## u/[deleted]
**Votes:** 14

One of my favourite recent adds to my daily workflow is [nswbuff](https://github.com/joostkremers/nswbuff), which has proved to be my go-to in-project buffer-switching mechanism.

I wrote a bit about it [here](https://manuel-uberti.github.io/emacs/2019/02/05/nswbuff/).


## u/olaeCh0thuiNiihu
**Votes:** 14

Meta advice: when taking notes, put the notes where you would go look for them.  If you're trying to find some notes, think about where you would try to look first.  If it's not there, remember where you looked and once you find the notes, make a link/copy to where you looked first.  The more you do this, the better you'll get at picking the right spot the first time.

Try to keep things as flat as possible.  Your job is not to present a theses about the perfect taxonomy for your notes, it's just to find things again later.  Don't make lots of folders, directories, scattered Org files, deeply nested lists, etc.  Only start breaking things into groups once you find yourself looking for something and thinking, "All of this X related stuff should just go into a folder or something."  This combines with the first advice in that if everything is flat, there aren't very many places you will want to look for something.  If you come up with a huge tree that would make a biologist cry, you'll have a really hard time remembering where exactly you put something.

This advice was shameless stolen/adapted from GTD.


## u/dmartincy
**Votes:** 14

`C-l` `(recenter-top-bottom)` is a known command that "recenters" the window around point by scrolling to the top, center, bottom.

But there's a no so well-known version, `C-M-l` `(reposition-window)`, that intelligently scrolls the buffer so that the function under point is fully visible, etc. It's very useful for programming languages modes.


## u/grabyourmotherskeys
**Votes:** 14

I really love org mode. The way to use org mode in my opinion is to set up capture templates directing notes and tasks quickly to the right files (whichever you like).

http://orgmode.org/manual/Capture-templates.html

You can then use the agenda to view, sort, filter, and search your notes and tasks. 

http://orgmode.org/manual/Agenda-commands.html

Once you get used to adding things quickly and finding them quickly it all falls into place. You can even export your notes in various formats including ICS (calendar) files so you can view your appointments on your phone.

One major advantage Evernote has is of course a Mobile app. I get around this by renting a very cheap vps and ssh'ing to it to use emacs (from my phone, my Chromebook, or workstation). I use the vps for other things, too.

The other is the images. I really don't know how to handle that. Org mode has ways to link to files and other things but I haven't tried displaying them or otherwise wiring working with images.

This works for me and I love using emacs for this purpose. I have tried a million other methods and this seems to work for me.

Here is a great tutorial: http://orgmode.org/worg/org-tutorials/orgtutorial_dto.html


## u/b3n
**Votes:** 14

Here's a nice eshell command:

```elisp
(defun eshell/history ()
  (interactive)
  (insert
   (completing-read "History: " (delete-dups (ring-elements eshell-history-ring)))))

```
It lets you use your normal completion framework to select an item from history. Suddenly fzf-like history!


## u/vfclists
**Votes:** 14

Can this thread be numbered eg `Weekly tips/trick/etc/thread - 0xx` or `Weekly tips/trick/etc/thread - YYYY Week XX`?

It becomes hard to remember which version you noted an interesting tip you want to return to.

I don't know if it is generated by a script it should but it should be simple. It can be made more search engine friendy prefixing or suffixing with `r/emacs` if other subreddits do something similar.


## u/Tatrics
**Votes:** 14

I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \*scratch\* buffer and then evaluate it with \`C-x C-e\`.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what  do you think in general!


## u/marcowahl
**Votes:** 14

 `C-x s d` leads to a diff of the buffer with its file.


## u/[deleted]
**Votes:** 13

And also we should collect them and save them in a repo, so that those can be seen in the following weeks.


## u/[deleted]
**Votes:** 13

Just do it, it's an open sub.

If you think there is not enough response, then you can also change the subject every week. Rotate some stuff every week to hold inspiration fresh.


## u/username223
**Votes:** 13

`M-x quick-calc` (which I've bound to `C-=`).  Calc is insanely complex, but this lets you do simple calculations quickly.


## u/shoutouttmud
**Votes:** 13

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


## u/rcoacci
**Votes:** 13

Just one thing: which Spacemacs "style" did you choose? If you chose "vim" style you need to be careful when searching for emacs resources or documentation, because the "vim" style is quite different from the standard emacs way of doing things, and that's probably why you had so much problem in the beginning.


## u/clemera
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


## u/ketoengineer89
**Votes:** 13

Migrated to native compiled emacs branch this week. Some hiccups but everything seems to work out of box, including pdf-tools. Great performance improvement.


## u/clemera
**Votes:** 13



```elisp
emacs --batch -l cl-lib --eval "(cl-loop (print (eval (read))))"
```


## u/loopsdeer
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


## u/dmartincy
**Votes:** 13

If you want to edit the commit that added/deleted a particular line of code, Magit offers `magit-edit-line-commit` that will do the interactive git rebase automatically for you.

`magit-diff-edit-hunk-commit` does the same but from a Magit diff buffer.
`


## u/andreyorst
**Votes:** 13

If you're not using [`native-comp`](http://akrl.sdf.org/gccemacs.html) feature yet, I strongly recommend you to try it out! Have been using it for two weeks for now and everything seem to run pretty smooth and rock solid! The speed difference is quite noticeable in some interactive aspects like completion, although my machine is quite slow, so this may not be that noticeable on newer machines.


## u/mrolivertaylor
**Votes:** 13

I just discovered the [selected](https://github.com/Kungsgeten/selected.el) package, which is brilliant. It creates a keymap that becomes active any time you have an active region. I have bindings for next-line, previous-line, rectangle-mark-mode, end-of-line, upcase-dwim, exchange-point-and-mark, etc. It makes editing and acting on the active region super easy. Sort of like god-mode or Vim's visual mode.


## u/mullikine
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


## u/sshaw_
**Votes:** 13

### Highlighting

Adds a background color to matched text in the buffer. You can have multiple overlays each with a different background color. Some bindings:

```elisp
M-s h .         highlight-symbol-at-point
M-s h l         highlight-lines-matching-regexp
M-s h u         unhighlight-regexp
```


## u/permafrosty
**Votes:** 12

For quick note-taking, there is deft:

http://jblevins.org/projects/deft/


## u/xu_chunyang
**Votes:** 12

To replace a shell command with its output, select the command and type `C-u M-| sh`. It can work because bash supports read program via STDIN, Python and Ruby work as well.


## u/emacs-noob
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


## u/ouroboroslisp
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


## u/zoechi
**Votes:** 12

I (Emacs rookie) just found out that native/fast JSON support is not guaranteed when emacs 27+ is used. jansson-dev needs to be installed when Emacs is built https://github.com/emacs-lsp/lsp-mode/issues/1557#issuecomment-608409056


## u/rhmatthijs
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


## u/xu_chunyang
**Votes:** 12

Happy Birthday from Emacs, let's assume March 24 is your birthday, put this to your init file, when you open Emacs on your birthday, you'll receive a birthday present from Emacs

```elisp
(when (string= "03-24" (format-time-string "%m-%d"))
  (animate-birthday-present user-full-name))
```


## u/ProfessorSexyTime
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


## u/Desmesura
**Votes:** 12

I've found that if you press `C-f`, the cursor goes right.


## u/dmartincy
**Votes:** 12

Not very well-known, but Emacs has its own spreadsheet and file format (`.ses`) (`ses-mode`). It supports many advanced features, and formulas are written in Elisp.

&#x200B;

Just another alternative to Org tables.


## u/Adorable-Effort
**Votes:** 11

`image-mode` can be used to preview TTF and OTF fonts.

Emacs already opens TTF fonts with `image-mode` automatically, but I also wanted it to do the same with OTF.

```elisp
(add-to-list 'auto-mode-alist '("\\.otf\\'" . image-mode))
```


## u/xu_chunyang
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


## u/henry_flower
**Votes:** 11

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


## u/jumpUpHigh
**Votes:** 11

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


## u/sauntcartas
**Votes:** 11

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


## u/Gollum999
**Votes:** 11

I just found out that the tables in `org-mode` support Excel-like calculations.  You can use functions from `calc` as well as arbitrary Elisp expressions!

Only complaint is that it's not the most performant. I tested it on a table of ~1200 rows and it took around 8 seconds to update my calculated column. :/


## u/ahk-_-
**Votes:** 11

I think the best productivity package for emacs is by far nyan-mode. I cannot live without it!

Life is too depressing to code without nyan-mode. Best productivity tool in the world!


## u/PotentiallyAlice
**Votes:** 11

I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

```elisp
(defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

```
Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "* TODO test reminder" line into my todo.org. Neat!


## u/MCHerb
**Votes:** 11

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


## u/clemera
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


## u/jimm
**Votes:** 11

I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.


## u/vatai
**Votes:** 11

The emacs lisp tutorial is the real tutorial for emacs ;)


## u/oantolin
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


## u/jacmoe
**Votes:** 10

Very cool!

I gave up on getting Emacs set up as a C++ IDE, but I am tempted now :)

&#x200B;

Great that we can have a llvm client/server within Emacs!


## u/ieure
**Votes:** 10

Maybe you already know about `occur`, which shows matching lines in the current buffer -- like grep, but the buffer doesn't need to be backed by a file.

Emacs also has `multi-occur` which does the same thing across multiple buffers (you have to specify each one); and `multi-occur-in-matching-buffers`, which matches lines across buffers whose name matches a regexp'.

Most of the time, I want to search *all* open buffers, so I wrote:

```elisp
(defun multi-occur-global (regexp &optional nlines)
  "Do a `multi-occur' across all buffers."
  (interactive (occur-read-primary-args))
  (thread-first
```
  ;; Don't occur in *Occur* buffers
  (lambda (buffer) (eq (buffer-local-value 'major-mode buffer) 'occur-mode))
(cl-remove-if (buffer-list))
(multi-occur regexp)))
```elisp
```

This is great if you have IRC chats or emails open in Emacs and need to quickly find a previous conversation.  And of course `occur` works properly with `next-error` / `previous-error` just like the `grep` commands do.


## u/shoutouttmud
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


## u/DasEwigeLicht
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


## u/uptocode
**Votes:** 10

Like Magit? Like writing TODOs in your source code? Check out: 

https://github.com/alphapapa/magit-todos

It uses simple programs like grep or rgrep to show the TODOs in your git repo.


## u/7890yuiop
**Votes:** 10

I find `M-\` preferable to a separate `hungry-delete` package (i.e. do it if that's what you mean to do, but not otherwise).

or `M-SPC` to call `just-one-space` if that's what you mean to do.

or bind something to `cycle-spacing`.


## u/zreeon
**Votes:** 10

This is basically my setup as well. Seems to work well. 

I set up a little raspberry pi that runs syncthing so that I can sync my phone and computers more easily. Also serves as a nice backup. 


## u/gusbrs
**Votes:** 10

Simple, but I think it counts as a tip: [minions](https://github.com/tarsius/minions) by [u/tarsius_](https://www.reddit.com/user/tarsius_). It allows one to invert the usual logic of managing minor modes lighters in the mode-line. While the default behaviour is "show everything by default, unless you diminish/delight it", `minions` goes in the opposite direction and hides all of them, unless you explicitly tell it to show them. Besides, the minor modes are still available, alongside some other common ones which thus become more easily accessible, in a menu which aggregates minor modes in the mode-line. Pretty neat.


## u/marcowahl
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


## u/robotreader
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


## u/sauntcartas
**Votes:** 10

If I have a file that I'd like to live in a Git repo, but I don't want to have to bother telling Git to ignore it, I'll just create it elsewhere but then put a line at the top like:

```elisp
# -*- default-directory: "/path/to/repo/"; -*-

```
The file then lives in the repo for nearly all intents and purposes, despite being stored elsewhere.  For example, if I run a shell command while visiting the file, the shell is run in the repo directory; Projectile commands can be run in the repo while visiting the file; etc.  Very handy!


## u/Vurpius
**Votes:** 10

I just learned that savehist is a thing. It makes minibuffer history persist across sessions. Enable it with `(savehist-mode 1)`.


## u/w0wt1p
**Votes:** 10

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


## u/Rotatop
**Votes:** 10

I've made it !

After 6 month of emacs, I m able to open a side buffer when I m on ivy without using C-c C-O or hydra or alt-enter but directly with shift + arrow (except for Up because I need to go on buffer)


```elisp
```
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

```elisp
```

Emacs is good


## u/c17g
**Votes:** 10

I just found out the great variable `org-extend-today-until`. Basically it defines when your day really ends. If you sleep late, check it out.

Goodbye to the days using `M-x org-todo-yesterday` at midnight, clocking off items before sleep..


## u/sugarbridalsentry
**Votes:** 10

This is a classic "Wow, Emacs does that?" situation: I discovered `imenu` by accident this week.

On the one hand, it's a great tool for finding definitions in code -- but because my main use of Emacs is for writing and PIM, the thing that really excited me was `imenu-add-menubar-index`. 

In org files, it treats each heading as a part of the index -- so by adding `imenu` to the menu bar, I have fly-out navigation for large org files. (I know people hate the menu bar, but I've found it handy). 

That's all well and good, but now add in the `imenu-list`package, and you have a little pop-up outline of your org file just one keypress away: it's like the bookmarks in a PDF, and has been really helpful already in getting around. Light, clean, easy.

Customizing `org-imenu-depth` sets how deep into a nested hierarchy to index for either `imenu-list` or the menubar index.


## u/primitiveinds
**Votes:** 10

This is one of the things that I'm sure exists somewhere but I can't find it so I wrote a few lines. I work in a big monorepo and depend on `projectile` for navigation by adding `.projectile` files here and there, in subdirectories that I consider "projects". However there are some directories that e.g. contain libraries. If I want to work on all of them for e.g. refactoring, going into a project then locks me in that (I mean `projectile-find-file`) and I have to manually go into another project. Also I flood my projectile cache with little things that I might not use a lot. What I did was add some logic to create temporary root directories for projects, where I can then use `counsel-file-jump` and `counsel-ag`. I have some keybindings and with a prefix argument I am prompted to change the temp root. Here's the code:
```elisp
(defvar my/temp-project-root nil)

(defun my/get-or-set-temp-root (reset)
  (let* ((reset-root (if reset my/temp-project-root nil))
```
     (root
      (if (or reset
              (null my/temp-project-root)
              (not (file-directory-p my/temp-project-root)))
          (read-directory-name "Temp root dir: " reset-root)
        my/temp-project-root)))
(setq my/temp-project-root root)))

```elisp
(defun my/counsel-file-jump-temp-root (reset)
  (interactive "P")
  (my/get-or-set-temp-root reset)
  (let ((current-prefix-arg nil))
```
(counsel-file-jump nil my/temp-project-root)))

```elisp
(defun my/counsel-ag-temp-root (reset)
  (interactive "P")
  (my/get-or-set-temp-root reset)
  (let ((current-prefix-arg nil))
```
(counsel-ag "" my/temp-project-root)))
```elisp
```
Also `counsel-file-jump` is so good


## u/WorldsEndless
**Votes:** 10

Just a cool concept: if you have a keypad on your keyboard which you rarely use, bind its nums to something useful. The results are numlock-sensitive and are NOT the same keycodes as regular numbers, so they're just free keys. For example, `(define-key map (kbd "<kp-1>") 'winum-select-window-1)`


## u/11fdriver
**Votes:** 10

Moving, marking & killing by paragraph.

I've been using these for a while as a customisation of Xah-Fly-Keys, but I was surprised to learn that many Emacs users don't know this functionality.

I find that moving by paragraph is a good middle ground between moving vertically by line and moving vertically by page, it's a very natural hierarchy.

I move the page to display the correct section of the buffer, then I move to the right paragraph, then to the right line within that paragraph.

The default bindings for movement are annoying, `M-{`/`M-}`, but it's simple enough to rebind them to `M-[`/`M-]`.

You can mark the current paragraph with `M-h`.


## u/Krautoni
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
```
(setq pair-programming--pair-programmer pair-programmer)
(message (concat "Pair programming with " (car pair-programmer)))))

```elisp
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
```
  (enable-pair-programming-mode)
(disable-pair-programming-mode)))

```elisp
(defun insert-pair-programmer-as-coauthor ()
  "Insert your pair programer into the current git commit."
  (when (and pair-programming-mode git-commit-mode)
```
(pcase pair-programming--pair-programmer
  (`(,name ,email) (git-commit-insert-header "Co-authed-by" name email))
  (_ (error "No pair programmer found or wrong content")))))

```elisp
(add-hook 'git-commit-setup-hook 'insert-pair-programmer-as-coauthor)
```

It sets up a co-authored-by for git commits, and enables line numbers.


## u/primitiveinds
**Votes:** 10

I just now figured out that you can interactively pass flags to commands like `counsel-rg` by putting the `--` separator between the flags and the search string, so something like `-g '*.txt' -- whatever` will search for `whatever` only in `txt` files. `counsel` uses a function called `counsel--split-command-args` to split the parts before and after the `--`.


## u/jimm
**Votes:** 10

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


## u/rhmatthijs
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


## u/isamert
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


## u/c17g
**Votes:** 10

If you:

1. Develop code on remote machine;
2. Use SSH to access remote; and
3. Annoyed by `flycheck` not working properly over TRAMP

Then consider using [`sshfs`](https://github.com/libfuse/sshfs) to mount your remote filesystem to local. This way, `flycheck` will regard your files just as a local one, and works properly. Neat trick discovered today when debugging flycheck-flake8 with my colleague (are you reading? :P)


## u/spfft
**Votes:** 10

Undo-tree and kill-ring are two of the best features in Emacs / packages. Change your life today.


## u/wasamasa
**Votes:** 10

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


## u/takethecannoli4
**Votes:** 9

May I suggest you post this to the Emacs Wiki?


## u/github-alphapapa
**Votes:** 9

Very nice post!  Although I certainly do not deserve to be named in the company of such pillars of the community as Sacha, John, and Nicolas.

You should consider starting a "real" blog to host these more permanently (or more personally and visibly, at least).  I often find myself reading blog posts from a few years ago about Emacs/Org, but old Reddit posts like this are probably more likely to fade into obscurity.  Then you can write a blog post about blogging with Emacs/Org!  ;)  You could also then submit it to Planet Emacs for syndication.  Kaushal Modi has set up a beautiful blog with Org and Hugo, so you might use it as an example.

Thanks for sharing your insights!


## u/Timesweeper_00
**Votes:** 9

FYI lsp-mode has added support for pyright as a language server, Microsoft is deprecating the c# python language server in favor of pylance (proprietary and closed source), which uses pyright.


## u/[deleted]
**Votes:** 9

[deleted]


## u/tareefdev
**Votes:** 9

Install [Wttrin](https://github.com/bcbcarl/emacs-wttrin) package, and then add the following to your `.emacs`:

```elisp
(require 'wttrin)
(setq wttrin-default-cities '("XXXXX"))
(setq wttrin-default-accept-language '("Accept-Language" . "en-US"))
(global-set-key (kbd "s-w") 'wttrin)

```
Put your city name instead of XXXXX and change the key binding to whatever you want. Now you can check the weather condition without leaving Emacs :)


## u/ShyGuy32
**Votes:** 9

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


## u/10q20w
**Votes:** 9

Idea: this thread could also double as a "help me implement this" thread.

Speaking of that: Is it possible to get atom-like completion suggestions before you start typing? I'm doing some CSS, and in Atom when you write something like

```elisp
.exampleclass {
    text-align: <cursor here>
}

```
Atom would give autocomplete-suggestions, displaying a list of possible values like "center", "left", "right". Company doesn't seem to do this.


## u/1-05457
**Votes:** 9

If you're using the default VIM mode keybindings, it sounds like you've really fallen for the VIM-style modal workflow.

Also, if you find Spacemacs slow (particularly at startup), try DOOM Emacs.


## u/sauntcartas
**Votes:** 9

I got tired of electric-pair-mode adding a closing parenthesis after I typed a frowny emoticon (`:(` or `:-(`), so:

```elisp
(setq electric-pair-inhibit-predicate
      (lambda (c) (or (looking-back ":-?(" (- (point) 3)
                      (string-match-p (rx bos " *Minibuf-" (+ digit)) (buffer-name))
                      (electric-pair-default-inhibit c))))

```
(The matching against the buffer name had already been present.)


## u/[deleted]
**Votes:** 9

TIL: There exists a built in function for accessing the menu bar, even if you have it hidden, or are using a terminal interface, via `tmm-menubar`. While it doesn’t have some of the convenience features of newer packages, it is still really neat. Plus, it’s been around since 1995!

Already bound to F10 or M-`


## u/sauntcartas
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


## u/ji99
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


## u/itistheblurstoftimes
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


## u/ji99
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


## u/[deleted]
**Votes:** 9

[deleted]


## u/andrmuel
**Votes:** 9

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


## u/ProfessorSexyTime
**Votes:** 9

I think it's neat you can make use of Awk scripts with org-babel.

With learning Assembly, I used it to grab all the system calls from `/usr/include/asm/unistd_64.h`

```elisp
#+BEGIN_SRC sh
gawk '{$1=" "; print substr($2, 6, $NF) " " $3}' /usr/include/asm/unistd_64.h
#+END_SRC

```
Granted the output isn't really fancy, but it works.


## u/sauntcartas
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


## u/char1zard4
**Votes:** 9

This week I learned that:

-	You can redefine all yes/no prompts to y/n:
`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-	`C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-	Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:
https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7


## u/sauntcartas
**Votes:** 9

I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it.  If it isn't, the command operates on the text from point to the end of the buffer.  That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time.  Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add:  Sorry folks, this doesn't work like I thought it did.  See the coments below for details.


## u/ragoneio
**Votes:** 9

Not sure if it will be helpful to anyone else - but working on legacy code, I often need to review code in large files with hundreds of errors and warnings. So I wrote a package which runs Flycheck on all the files in a diff and filters the output to only the lines which was modified or added! https://github.com/ragone/magit-diff-flycheck. I hope you like it - it’s currently in review to get published on MELPA.


## u/PriorOutcome
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


## u/11fdriver
**Votes:** 9

I'm sure that this is something that people already knew about, but I had no idea until today.

I often start selecting text and realise that I actually want a rectangular selection (or the other way round). 

I found out that rather than having to jump to the mark, cancel, and restart, I can just press `C-x SPC` `[xfk: SPC r SPC]` `(rectangle-mark-mode)` and Emacs will toggle to the other selection scheme.


## u/[deleted]
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


## u/tryptych
**Votes:** 9

Emacs has better long-lines support now??

I just noticed while looking at the latest additions in the NEWS:

>\*\* Emacs is now capable of editing files with arbitrarily long lines. The display of long lines has been optimized, and Emacs no longer chokes when a buffer on display contains long lines.  If you still experience slowdowns while editing files with long lines, this is either due to font locking, which you can turn off with M-x font-lock-mode or C-u C-x x f, or to the current major mode or one of the enabled minor modes, in which case you should open the the file with M-x find-file-literally instead of C-x C-f.  The variable 'long-line-threshold' controls whether and when these display optimizations are used.

That sounds like great news!  Does anyone know what went into it?

(edit to add: this was added some time this week.  I rebuild from master weekly, and check out the NEWS diff each time)


## u/sauntcartas
**Votes:** 9

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


## u/tryptych
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


## u/yogsototh
**Votes:** 9

I just made this nice combination of emacs packages and personal theme to achieve the cool effect of iAWriter

See here: https://her.esy.fun/posts/0021-ia-writer-clone-within-doom-emacs/index.html


## u/akirakom
**Votes:** 8

Subscribe to Emacs News by Sacha Chua: http://sachachua.com/blog/category/geek/emacs/emacs-news/


## u/c17g
**Votes:** 8

```emacs-lisp
(setq org-odt-preferred-output-format "doc")
```elisp

Default Org to export ODT in Word format (.doc). I discovered it tonight right before I get off from work and send it to my manager, very convenient for exporting docs for business colleagues for editing. See [manual](https://orgmode.org/manual/Extending-ODT-export.html) for more options. 


## u/thehaas
**Votes:** 8

Replace a string in a bunch of files in a directory. I got this from https://emacs.stackexchange.com/a/13719

 - run  `helm-ag` and search for a string
 - on the results, do `C-c C-e` to get it into an editable buffer
 - make the changes
 - `C-c C-c` to commit all the changes


## u/agumonkey
**Votes:** 8

if I may, r/france has weekly threads about a few topics, I find it very nice, sometimes attendance shrinks a little, but there's always nice things to read.

I always found I learned more about emacs by hearing others ideas rather than manual or code.. workflow ideas don't necessarily come from mastering atoms. 

So I find this thread a very very nice idea. (thanks)


## u/zreeon
**Votes:** 8

Those weird keys like C-m that actually send other keys like RETURN? From our terminal days?

You can convince GUI Emacs to let you bind them, which opens up a whole slew of new easy-to-reach keybindings.

https://emacs.stackexchange.com/questions/20240/how-to-distinguish-c-m-from-return


## u/shoutouttmud
**Votes:** 8

Evil related:

An interesting thing I read in the past week was this [blog post](http://vimcasts.org/blog/2014/02/follow-my-leader/). What I got out of it is that, although vi keybinds cover almost all keys, leaving you with few free keys to change, a significant amount of combinations of keys are invalid (for example c followed by x doesn't do anything) and thus you can bind them to whatever you want. 

I have not integrated this idea to my evil workflow yet, but it sounds quite promising, and I think I'll start using it at some point. 

(One thing to consider though is that with evil there are packages that add additional text objects. If you added a text object that is represented by x then c followed by x would become a valid combination and thus would clash with your keybinding, forcing you to change it to something else)


## u/agumonkey
**Votes:** 8

Thanks, I like seeing workflow of others so I can steal because I'm a great artist.


## u/eeeickythump
**Votes:** 8

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


## u/globalcandyamnesia
**Votes:** 8

If you're using the mark setting commands to expand a selection like `M-@` (mark next word) or `C-M-@` (mark next sexp), you can swap the point and mark (`C-x C-x`) and the selection will be expanded to the left rather than the right.

So if you're in the middle of a sentence, you can press `M-@` a few times to select some words to the right, press `C-xx`, and press `M-@` a few more times to add words before the selection.


## u/chrchr
**Votes:** 8

I was watching some Lisp Machine demos on youtube a few weeks ago, and I observed that pressing ")" in the REPL causes the expression to be evaluated immediately if the expression is complete. There is no need to press `enter` at the end of the expression to submit it. I wanted my Emacs to do that as well, so I cooked up the following thing: [electric-paren](https://www.emacswiki.org/emacs/electric-paren.el)

&#x200B;

It works in inferior-lisp-mode, ielm, slime, and, my favorite of all, eshell.


## u/clemera
**Votes:** 8

In the past, when I forgot a command flag I would go to the man page, search, copy/remeber the option and finally paste it into the shell. Last week I thought about ways to make this more efficient, and thanks to `pcmpl-args` and `helm` I finally have a better workflow for this:

```elisp
(defun insert-shell-option (cmd)
  (interactive "sCommand: ")
  (let ((options ()))
    (require 'pcmpl-args)
    (require 'helm)
    (dolist (item (pcmpl-args-extract-argspecs-from-manpage cmd))
      (let ((option (plist-get item 'option))
            (help (plist-get item :help)))
        (push (cons (with-temp-buffer
                      (insert help)
                      (let ((fill-column 80))
                        (fill-paragraph))
                      (goto-char (point-min))
                      (insert (format "%s\n" option))
                      (buffer-string))
                    (format "%s" option))
              options)))
    (helm (helm-build-sync-source "Options: "
            :candidates (nreverse options)
            :multiline t
            :action #'insert))))

```
The command lets you fuzzy search the options (including their description in the man page) and insert the right flag in one go.


## u/clemera
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


## u/wasamasa
**Votes:** 8

If you ever find yourself debugging your cc-mode indentation, make sure to `(setq c-echo-syntactic-information-p t)`, that way indentation commands will echo what the context is and you know what part of the indentation style you're supposed to customize.


## u/[deleted]
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


## u/github-alphapapa
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


## u/clumsyKnife
**Votes:** 8

Newbie tip : open files with external processes in dired *asynchronously* with `&` instead of `!`.

Probably too basic for most of the users here but I was happy to find out about it today !


## u/username0x223
**Votes:** 8

Have you ever made the mistake of running something in `comint` that sprays a ton of text and makes your Emacs unresponsive (especially with `font-lock-mode`)?  I bind this to `C-c s` to discard all output until things quiet down.

```elisp
(defun my-comint-stfu-toss (x) "")
(defun my-comint-stfu ()
  "Start (or stop) tossing comint output."
  (interactive)
  (if (and (boundp 'comint-preoutput-filter-functions)
	   (member 'my-comint-stfu-toss comint-preoutput-filter-functions))
      (setq comint-preoutput-filter-functions
	    (remove 'my-comint-stfu-toss comint-preoutput-filter-functions))
      (add-hook 'comint-preoutput-filter-functions
		'my-comint-stfu-toss)))
```


## u/kastauyra
**Votes:** 8

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


## u/xu-chunyang
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


## u/[deleted]
**Votes:** 8

There is a [request](https://www.reddit.com/r/bugs/comments/g3c1go/might_gweneorg_be_whitelisted/) (created by /u/Amonwilde) for the Reddit team to whitelist [gwene.org](https://gwene.org) to be able to parse RSS feeds from different subreddits (right now, it does not work properly due to exceeded rate-limits). Upvote, please, if you use GNUS to consume RSS feeds and miss updates from Reddit. Thanks!


## u/a-k-n
**Votes:** 8

I just discovered that installing the Emacs macport homebrew formula with \`--with-mac-metal\` will significantly increase the performance of Emacs. It's buttery smooth!


## u/xu_chunyang
**Votes:** 8

Use the header line to show the mode line's information:

```elisp
(setq-default header-line-format mode-line-format
              mode-line-format nil)

```
Screenshot: https://i.imgur.com/cejaNar.png

This doesn't work everywhere e.g., some major modes need manipulate the header line for their own use. 

Inspired by https://www.reddit.com/r/emacs/comments/btz1d6/show_mode_line_in_minibuffer/


## u/clemera
**Votes:** 8

I started to append a custom suffix to my personal package customizations instead of using a prefix. For example instead of `clemera-org-occur` I would use `org-occur-clemera`. This way I use the same elisp "namespace" as the package with my own custom "sub namespace", which has some advantages for completions, searches and so on.


## u/jstad
**Votes:** 8

When testing out or learning elisp, I have found [eros](https://github.com/xiongtx/eros) to be indispensable.

It shows an overlay inline with the function to display the evaluated result.


## u/ji99
**Votes:** 8

```elisp
(defun repeat-last-shell-command ()
  (interactive)
  (let ((last-cmd (cadr (assoc 'shell-command command-history))))
    (when (y-or-n-p (concat "execute " last-cmd))
      (async-shell-command last-cmd))))
```


## u/mrolivertaylor
**Votes:** 8

`bs-show` is an interesting command, it shows a pop-up-like buffer that you can use to quickly act on open buffers. There are a ton of customizations you can make and a bunch of convenient bindings. I've been trying it out instead of `list-buffers` and `ibuffer` and I like it so far, very fast.


## u/1Nude
**Votes:** 8

> Remember window configuration (2:3)

Heya so window configuration, what exactly do you want? Do you want it so when you quit emacs and start it up it saves your session? If so `(desktop-save-mode 1)` might be what you're after [(info)](https://www.gnu.org/software/emacs/manual/html_node/emacs/Saving-Emacs-Sessions.html).

Are you doing something in emacs and some how the windows got messed up? To quickly revert add (winner-mode 1) to your init.el file and using the custom key binds C-c <left key> you can revert the window to the previous state [(info)](https://www.emacswiki.org/emacs/WinnerMode).

> - Open help and/or other files at a bottom buffer

To specify a buffer for help/other files, one way is by "locking" buffers. So I use this code to lock and unlock a buffer and then in your case I would set up one more buffer so that everything will open there.

```elisp
 (defun toggle-window-dedicated ()
        "Toggle whether the current active window is dedicated or not. 
 Code founded by peterfoldi 
 https://stackoverflow.com/questions/29332242/is-there-a-way-to-fix-window-buffer-in-emacs-for-cider-error-repl/33082302#33082302"
   (interactive)
   (message 
    (if (let (window (get-buffer-window (current-buffer)))
      (set-window-dedicated-p window 
                      (not (window-dedicated-p window))))
        "Window '%s' is dedicated"
      "Window '%s' is normal")
    (current-buffer)))

```
The help/pop up are annoying, and I think a hard problem to get right. 

> Make emacs place the auto-save files somewhere els

To make emacs stop doing #files# or files~ I use a piece of code provided by [Jorgen Schäfer](https://emacs.stackexchange.com/questions/33/put-all-backups-into-one-backup-folder). Something to note #files# are autosave files as in files in which a copy that you haven't saved yet (so if emacs crashes your work can be recovered). If you save your file these will go away naturally. While files~ are actual backup files. 


## u/oantolin
**Votes:** 8

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


## u/aartist111
**Votes:** 8

Found / c for M-x ibuffer.  It filters buffers by content.
It looks like  'grep -c' .   Very helpful to locate a file quickly for which you remember any word from content
Until now I had only used filters for filenames or modes only. .


## u/Ramin_HAL9001
**Votes:** 8

I'm on Emacs 26.1, I just tried the [`M-x butterfly`]( https://www.xkcd.com/378/ ) command to see what would happen.

Wouldn't you know it, it actually does something that isn't an error message!


## u/PriorOutcome
**Votes:** 8

I wanted to be able to expand yasnippets within other yasnippets (so here tab would jump to the next position instead of trying to expand snippet), surprisingly all I had to do was:

`:bind ("C-<tab>" . yas-expand)`

So C-<tab> expands a snippet within a snippet, and everything just worked as I'd hoped.  Once I'm done with the nested expansion <TAB> just moves on to the outer one. \*shrug\*


## u/[deleted]
**Votes:** 8

(Linux) desktop notifications when a compilation buffer finishes:

```elisp
(setq compilation-finish-functions (append compilation-finish-functions (lambda (buffer status) (call-process "notify-send" nil nil nil "-t" "0" "-i" "emacs" "Compilation finished in Emacs" status))))
```


## u/[deleted]
**Votes:** 8

[deleted]


## u/hairlesscaveman
**Votes:** 8

I follow the format of `{ticketnumber}-{short-description}` when creating branches using Magit, but when typing the descriptive name for the branch I often type `SPC` between words instead of dash due to muscle memory when writing sentences. This causes a warning to be shown, because "Whitespace isn't allowed here", and breaks "flow" for me.

The advice below quiets this warning, and inserts a dash whenever space is pressed.

```elisp
(advice-add 'magit-whitespace-disallowed :around (lambda (orig-fun &rest args) (interactive) (insert "-")))
```


## u/agumonkey
**Votes:** 8

you can have an org-mode file in source block in an org file

