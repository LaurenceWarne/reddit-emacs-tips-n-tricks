## u/gausby
**Votes:** 36

Shouldn't it be `/etc/tips/trick` ?

(…I'll show myself out)

## u/TheDrownedKraken
**Votes:** 35

Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.

## u/jalihal
**Votes:** 35

I just discovered calc mode. Oh my ...
It does symbolic differentiation, solves linear equations, plots with gnuplot, and does matrix operations, all with a few key strokes!! How is this amazing tool not as popular as org mode or Magit?? I am so excited to explore it more, especially for the "display" options. I can see it taking my latex experience to the next level. 
Any tips on using calc mode?

## u/celeritasCelery
**Votes:** 31

> I had received the advice to not install anything and just start with plain emacs so I can learn emacs. ... it was miserable. 
> Enter Doom Emacs. I decided to give Doom Emacs a try because it was also highly recommended in my initial RFC, especially since it is designed for Vim users. In short I love it.

This is why I disagree with the subreddits de facto advice to “learn vanilla first”. People who have used emacs for a long time don’t realize how much **time** it takes to get it to level of a normal modern editor people are used to. I recommend distros to *everyone* who is new unless they are that certain personality type that wants to do everything themselves.

## u/unbelievable_sc2
**Votes:** 28

I often use the compile feature to compile and run my projects to see the results in the compilation buffer. This works well unless you are waiting for user input in your terminal. I recently found out that you can invoke the compile with an additional non nil value to start compilation in comint-mode which allows for user input! 
The drawback is, that you then no longer can press q to close the window or g to recompile. Because of that I added a simple lambda, that switches to compilation-mode to the compilation-finish-functions. So I can give input while compiling and running, and after compilation I can close the window as usual with q.

## u/github-alphapapa
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

## u/[deleted]
**Votes:** 25

[removed]

## u/clemera
**Votes:** 22

Ever had to fix your broken config in a bare bones Emacs? I adopted this from [abo-abo](https://github.com/abo-abo) and bound it to `C-x C-c`:

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

This makes sure I never exit when the config is broken, so I can fix it with my full featured Emacs setup. First `C-x C-c` will show if everything is right, second `C-x C-c` will exit.

## u/fogbugz
**Votes:** 21

I do something similar. But for me a big epiphany was to realize all my organization files and all my wiki files should live in the same directory to allow for simple hyperlinks across them and a beautiful flat structure.

The only difference is that I don't version control some of my organization files. E.g. my tasks. But that's a .gitignore detail.

I prefer all my tasks to live inside a single org file, instead of letting my agenda view harvest them from multiple files. I keep a separate calendar file with fixed events and holidays.

A second epiphany was to realize the best way to manage my tasks is not the much hyped GTD, but a JIT approach: a kanban board. I've set up my agenda view to mimic a kanban. GTD has some nice ideas: keeping a few or ideally one inbox and differentiating between tasks, tasks with deadlines, tasks with scheduled times and events.

But it does have a big flaw, for my personal lifestyle and for many creative people. It doesn't control flow. It leads to creating way too many tasks, no prioritization, and task lists become outdated frequently. This requires a lot of wasteful updating.

In my case, I split my life into a few key areas, and I limit the number of ongoing tasks for each area. I've also gotten rid of GTD contexts. I'm content with states: TODO, PROG (progress), WAIT (blocked) and DONE.

Lastly, I do really believe organization systems are only half-baked if they don't integrate into a knowledge system. Why? Because some tasks are not actionable. They are just knowledge bits you want to store and reuse in the future. Here they explain it better than I do: https://praxis.fortelabs.co/gtd-x-pkm-8ff720ef6939/

I believe in keeping stuff simple. And learning from Japanese production systems, which got lots of things right and which we seem to be reinventing in bad ways. 

## u/meekale
**Votes:** 20

Automatic `chmod +x` when you save a file that starts with a `#!` shebang:

    (add-hook 'after-save-hook 'executable-make-buffer-file-executable-if-script-p)

## u/thehaas
**Votes:** 20

After using Emacs for maybe 10 years I finally started using registers and I really should have started earlier. For those who don't know:

&#x200B;

* Highlight text and C-x r s <char> to save to register <char>
* C-x r i <char> to put the contents of the register at the cursor point. The text is still there -- use it over and over again

It seems like quite a few keystrokes but it's really not. Of course you can re-assign them to other keys if you don't like the defaults.

## u/clemera
**Votes:** 19

This week someone asked to show commit messages along the file listing in dired, similar to the repo view on github. I hacked together a snippet which does this, if there is interest I will improve it further and make a package for it. Here is the [gist](https://gist.github.com/clemera/9c27bc8a003ef404182bf8d0f7bc00a0). After evaluating press ")" in a dired buffer of a git project to show the commit messages.

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

    Hello, #1!
    Hello, #2!
    Hello, #3!
    Hello, #4!
    Hello, #5!
    
by typing `C-1 C-x C-k C-c C-x ( Hello, # C-x C-k C-i ! RET C-5 C-x )`.

OK, now for the good stuff: all of that functionality can be achieved using just these two newer functions:

- `kmacro-start-macro-or-insert-counter` (`<f3>`): start recording if used outside a macro, and if you pass a numeric argument when you start recording, it sets the initial value of the counter. When used while already recording, it will insert the counter and increment it by one. So this command subsumes  `kmacro-start-macro`,  `kmacro-insert-counter`, and  `kmacro-set-counter`.
- `kmacro-end-or-call-macro` (`<f4>`): stops recording, optionally running the macro as many times as indicated by the numeric argument. When used while _not_ recording, it just runs the last keyboard macro. So this command subsumes `kmacro-end-macro` and `kmacro-end-and-call-macro`.

The example given above becomes `C-1 <f3> Hello, # <f3> ! RET C-5 <f4>`, much smoother!

## u/TeMPOraL_PL
**Votes:** 19

`shortdoc` - one of the new things in Emacs 28.1 - is great for maintaining your own "cheat sheets" of Elisp functions as you discover them. For example, eval this in your Emacs session:

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

After this, the `my-datetime` group will show as an option in `M-x shortdoc-display-group`. Additionally, if you view help for any of the functions mentioned above, the Help buffer will refer back to the `my-datetime` shortdoc group!

The example used here is a cheatsheet I just started writing for myself, as I've been researching the built-in time functions. For additional instructions on use, see `define-short-documentation-group` macro. For use examples, jump to the source of `define-short-documentation-group` and scroll down a bit - the default shortdoc groups are defined there.

## u/sugarbridalsentry
**Votes:** 19

I'm sure this was widely known, but wow-- 

So, I started seeing a "Vacuous Schema" message when loading a specific Org file, and couldn't figure out why. The message didn't give me much to go on, and the file is kind of huge, so going through possible solutions one at a time was slow and miserable.

I wished I could debug the message -- or turn on some sort of verbose mode or something. And that's when I discovered the `debug-on-message` variable. 

This variable lets you debug what happens to cause any message, using a regex to find the right message. 

So, in my simple case I just eval'd:

    (setq debug-on-message "vacuous schema")

And re-opened the file, and lo-and-behold, it told me exactly what I needed to know to fix the screwed up source block. 

It's just... awesome to be able to answer the age-old, "Huh, I wonder why it said that...?" situation so easily.

## u/oantolin
**Votes:** 18

I have two org mode link tips:

1. `(setq org-return-follows-link t)` lets you press RET to follow a
   link. Don't worry, the traditional behavior of RET, namely inserting
   a newline, is still easy: `C-q C-j`.

2. I often want to see what the destination of a link is. I used to
   use one of these methods:
   
   - mouse hover,
   - running and canceling `org-insert-link` (`C-c C-l`, read the
     destination, `C-g`),
   - `org-toggle-link-display`, which toggles between the neat formatting of 
     links and the raw source and is pretty ugly.
     
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
   
       (defun echo-area-tooltips ()
         "Show tooltips in the echo area automatically for current buffer."
         (setq-local help-at-pt-display-when-idle t
                     help-at-pt-timer-delay 0)
         (help-at-pt-cancel-timer)
         (help-at-pt-set-timer))

       (add-hook 'org-mode-hook #'echo-area-tooltips)

## u/tryptych
**Votes:** 18

It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/).  There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.

## u/ketoengineer89
**Votes:** 17

Migrated to native compiled emacs branch this week. Some hiccups but everything seems to work out of box, including pdf-tools. Great performance improvement.

## u/laralex
**Votes:** 17

A small basic thing, but once I'd discovered it, I started using dired.
```C-x C-j``` is most likely bound to ```dired-jump```, and this function opens dired for this window's file, without promting for directory (and this prompt was an issue for my workflow when using ```C-x d```). That makes finding and switching files just as convenient as in OS GUI. I've also bound a few keys when in dired mode (I find them decent):

```a``` - prompt a name and create empty file

```d``` - prompt a name and create empty dir

```u``` - go to parent dir (the key is a mnemonic to "go Up in directory tree)

```j``` - if it's a dir go into it, otherwise find this file (key is near to 'u' so jumping up and down is not a big deal with one hand, also the 'j' is the easiest key for me as a touchtyper) 

```n```/```p``` - move one entry down/up, which resembles ```C-n```/```C-p```

## u/TheDrownedKraken
**Votes:** 17

It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.

## u/surelynotmymainacc
**Votes:** 16

Not much of as trick, but something helped me as a newbie, which must be very obvious to many. When you want to see how to correctly configure a package or use a function in elisp, and when documentation is not helping you, go to GitHub and search like "use-package package-name", switch over to the code tab and change language to Emacs lisp. This will give you hundreds of examples.

## u/xu_chunyang
**Votes:** 16

Make C-j in Lisp Interaction mode produces this:

    (+ 1 2 3)
    ;; => 6

code:

    (define-advice eval-print-last-sexp (:around (old-fun &rest args) add-prefix)
      "Prepend ;; =>."
      (let ((op (point)))
        (apply old-fun args)
        (save-excursion
          (goto-char op)
          (forward-line 1)
          (insert ";; => "))))

## u/grabyourmotherskeys
**Votes:** 16

I really love org mode. The way to use org mode in my opinion is to set up capture templates directing notes and tasks quickly to the right files (whichever you like).

http://orgmode.org/manual/Capture-templates.html

You can then use the agenda to view, sort, filter, and search your notes and tasks. 

http://orgmode.org/manual/Agenda-commands.html

Once you get used to adding things quickly and finding them quickly it all falls into place. You can even export your notes in various formats including ICS (calendar) files so you can view your appointments on your phone.

One major advantage Evernote has is of course a Mobile app. I get around this by renting a very cheap vps and ssh'ing to it to use emacs (from my phone, my Chromebook, or workstation). I use the vps for other things, too.

The other is the images. I really don't know how to handle that. Org mode has ways to link to files and other things but I haven't tried displaying them or otherwise wiring working with images.

This works for me and I love using emacs for this purpose. I have tried a million other methods and this seems to work for me.

Here is a great tutorial: http://orgmode.org/worg/org-tutorials/orgtutorial_dto.html

## u/Quasimoto3000
**Votes:** 16

Dude look into Elpy. It's incredibly simple to set up and very powerful.

I couldn't recommend it more.

Edit: here's all the info you need.

https://github.com/jorgenschaefer/elpy/wiki/Installation

Let me know if you have any questions.

## u/github-alphapapa
**Votes:** 16

Here's a popular Emacs config I just rediscovered.  Some cool stuff here.  https://github.com/angrybacon/dotemacs

## u/c17g
**Votes:** 16

To avoid accidentally inserting text in invisible area (`...`) in Org files, set `org-catch-invisible-edits` to `error` or `show-and-error`. See docs.

## u/Ramin_HAL9001
**Votes:** 16

Here's a really old trick that I only just now started using: save window layouts to registers with `C-x r w <Letter>`, which saves it into a `Letter` key register. Switch between window layouts using the usual "register jump" `C-x r j`, that is `C-x r j <Letter>` to go back to the layout in that lettered register.

If you use registers for storing text or other things, be careful to use a different set of letters for text and a different set for window layouts. Personally I never use registers for storing text (`M-y` to rotate through the kill-ring is all I need). But I have been using the window layout one quite often lately.

It feels a little like having the multiple desktops feature common to many Linux desktop environments, but I can use it to switch between different projects I am working on within Emacs without needing multiple instances of Emacs.

## u/AffectionateAd8985
**Votes:** 16

`(add-hook 'org-mode-hook (lambda () (org-next-visible-heading 1)))`

Move to first heading when open org files, with `org-use-speed-commands`, I can quick browse org file with only `n/p` keys.

## u/dakra
**Votes:** 15

Sometimes I want to get info about my IP address (e.g. if VPN is
connected or not) or some other IP.

This function uses `request` to display infos from `ipinfo.io`:

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

As I like to keep my own init.el clean with use-package
[here](https://github.com/dakra/ipinfo.el) is the same function
packaged.

## u/dmartincy
**Votes:** 15

`C-l` `(recenter-top-bottom)` is a known command that "recenters" the window around point by scrolling to the top, center, bottom.

But there's a no so well-known version, `C-M-l` `(reposition-window)`, that intelligently scrolls the buffer so that the function under point is fully visible, etc. It's very useful for programming languages modes.

## u/Thaodan
**Votes:** 15

[Which-Key](https://github.com/justbur/emacs-which-key)
Great to learn keybinds, especially for new emacs users:

```elisp
(use-package which-key
  :config (which-key-mode 1))
```

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

## u/username223
**Votes:** 14

`M-x quick-calc` (which I've bound to `C-=`).  Calc is insanely complex, but this lets you do simple calculations quickly.

## u/[deleted]
**Votes:** 14

One of my favourite recent adds to my daily workflow is [nswbuff](https://github.com/joostkremers/nswbuff), which has proved to be my go-to in-project buffer-switching mechanism.

I wrote a bit about it [here](https://manuel-uberti.github.io/emacs/2019/02/05/nswbuff/).

## u/olaeCh0thuiNiihu
**Votes:** 14

Meta advice: when taking notes, put the notes where you would go look for them.  If you're trying to find some notes, think about where you would try to look first.  If it's not there, remember where you looked and once you find the notes, make a link/copy to where you looked first.  The more you do this, the better you'll get at picking the right spot the first time.

Try to keep things as flat as possible.  Your job is not to present a theses about the perfect taxonomy for your notes, it's just to find things again later.  Don't make lots of folders, directories, scattered Org files, deeply nested lists, etc.  Only start breaking things into groups once you find yourself looking for something and thinking, "All of this X related stuff should just go into a folder or something."  This combines with the first advice in that if everything is flat, there aren't very many places you will want to look for something.  If you come up with a huge tree that would make a biologist cry, you'll have a really hard time remembering where exactly you put something.

This advice was shameless stolen/adapted from GTD.

## u/natarey
**Votes:** 14

For ages, I've had a custom function in my init that will add numbers to the front of a list -- transforming this:

    line 1
    line 2
    line 3

Into:

    1. line 1
    2. line 2
    3. line 3

But I just discovered this functionality is *built in*!

To just add numbers, select a region and call `rectangle-number-lines` with `C-x r N`. 

To customize things like what number to start at, or the format of the numbers (to add periods or parens after the numbers, for example) call it with the universal argument and it'll go through the options with you.

Truly, the batteries are included with Emacs.

## u/marcowahl
**Votes:** 14

 `C-x s d` leads to a diff of the buffer with its file.

## u/mrolivertaylor
**Votes:** 14

I just discovered the [selected](https://github.com/Kungsgeten/selected.el) package, which is brilliant. It creates a keymap that becomes active any time you have an active region. I have bindings for next-line, previous-line, rectangle-mark-mode, end-of-line, upcase-dwim, exchange-point-and-mark, etc. It makes editing and acting on the active region super easy. Sort of like god-mode or Vim's visual mode.

## u/mogigoma
**Votes:** 14

Every time I see this package I think to myself "People exit Emacs?"

## u/swhalemwo
**Votes:** 14

finally found an opportunity to use git-timemachine: I was optimizing a Python function when I realized I needed to be sure it returned the same results as before. jumped to previous commit, C-c C-y f and voila. made me feel like a code wizard.

## u/yousufinternet
**Votes:** 14

I was reading through the org manual, and learnt about two variables `org-agenda-category-icon-alist` and `org-agenda-prefix-format`, the first allows you to set icons for categories (`CATEGORY` property), icons can be images or symbols, this is the code I came up with and the agenda already looks more colorful and clear


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

Of course for the emojis to show up correctly I use this:

    (set-fontset-font "fontset-default" 'symbol (font-spec :family "Noto Color Emoji"))

I am sure a lot of you know about these, please share your customization

## u/shoutouttmud
**Votes:** 13

This is for the evil users out there: 

Usually evil text objects are powerful enough, but sometimes you can use the extra functionality that [expand-region](https://github.com/magnars/expand-region.el) provides. So, sometime in the last month I started using it, and I managed to find a (quite seamless in my opinion) way to integrate it with evil. 
      
    (defhydra hydra-expand-region ()
       "region: "
       ("k" er/expand-region "expand")
       ("j" er/contract-region "contract"))

    (evil-define-key 'visual 'global (kbd "v") #'hydra-expand-region/body)
     
The above code should be pretty self-explanatory to anyone familiar with evil and hydra. By pressing v in visual state you "enter" the expand region hydra that lets you call expand and contract region by pressing j/k, effectively creating an additional evil state with a minimal amount of code

## u/rcoacci
**Votes:** 13

Just one thing: which Spacemacs "style" did you choose? If you chose "vim" style you need to be careful when searching for emacs resources or documentation, because the "vim" style is quite different from the standard emacs way of doing things, and that's probably why you had so much problem in the beginning.

## u/clemera
**Votes:** 13



    emacs --batch -l cl-lib --eval "(cl-loop (print (eval (read))))"

## u/loopsdeer
**Votes:** 13

You can store a bookmark on a Magit status window and it WORKS!

I just tried it last night figuring it would err when I tried to open the bookmark later, and it worked and I'm very excited about it.

This is the perfect entry point for projects for me, being reminded of what state source control is in. I used to jump to Dired in the .git root or some main code window but neither were helpful to start my day. This is perfect.

I set the bookmark with \`helm-filtered-bookmarks\` by just being on the Magit window and running that then typing in my name, and this is what was added to my bookmark file:

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

I quit Emacs, made an edit in my .git elsewhere, opened Emacs back up and jumped to this bookmark and it showed me the change! 

It's funny how excited I am to remove two keystrokes from the beginning of my day. Probably I am also excited that my random experiment worked. Obviously it's not so random as magit knew exactly what I wanted. Magit is life! </rant>

## u/b3n
**Votes:** 13

Here's a nice eshell command:

    (defun eshell/history ()
      (interactive)
      (insert
       (completing-read "History: " (delete-dups (ring-elements eshell-history-ring)))))

It lets you use your normal completion framework to select an item from history. Suddenly fzf-like history!

## u/vfclists
**Votes:** 13

Can this thread be numbered eg `Weekly tips/trick/etc/thread - 0xx` or `Weekly tips/trick/etc/thread - YYYY Week XX`?

It becomes hard to remember which version you noted an interesting tip you want to return to.

I don't know if it is generated by a script it should but it should be simple. It can be made more search engine friendy prefixing or suffixing with `r/emacs` if other subreddits do something similar.

## u/Tatrics
**Votes:** 13

I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \*scratch\* buffer and then evaluate it with \`C-x C-e\`.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what  do you think in general!

## u/MCHerb
**Votes:** 13

A narrowing toggle that does what I need most of the time so a single key can do all narrowing and un-narrowing.

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

## u/andreyorst
**Votes:** 13

If you're not using [`native-comp`](http://akrl.sdf.org/gccemacs.html) feature yet, I strongly recommend you to try it out! Have been using it for two weeks for now and everything seem to run pretty smooth and rock solid! The speed difference is quite noticeable in some interactive aspects like completion, although my machine is quite slow, so this may not be that noticeable on newer machines.

## u/mullikine
**Votes:** 13

## Use chrome DOM for eww

Basically, a lot of websites these days generate the DOM using javascript. You can dump the DOM from chrome and inject it into eww just before it renders.

It's set to wait 3 seconds before dumping the DOM. This allows many pages to load.

Since I'm using the `unbuffer` program, this requires `expect` to be installed on your system. It creates a tty so that chrome doesn't crash when run in this way.

`dump-dom` shell script

    #!/bin/bash
    
    url="$1"
    test -n "$url" || exit 1
    
    0</dev/tty unbuffer bash -c "chrome --headless --disable-gpu --virtual-time-budget=3000 --dump-dom \"$url\" 2>/dev/null"

Make these modifications to `eww-display-html`.

`eww-display-html`

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

## u/oantolin
**Votes:** 13

Most people probably know that `M-t` (`transpose-words`) when used between two words swaps them. But it has other a few other features that are useful:

- You don't have to be between words to use it: if you are on a word, from the second character on, it will swap that word with the next.

- If you are at the end of the buffer and use it you get an error message, "Don’t have two things to transpose", _but_ you additionally get placed at the beginning of the last word in the buffer. So `M-t M-t` used at the end of the buffer will swap the last two words!

- You can use it to swap _non-adjacent_ words too! If you call it with a numeric argument of 0, it will swap the word at the start of the region with the next word after the end of the region. For example, say you want to swap "two" with "five" in the following line:

      one five three four two six
    
  You can mark the words "five three four" (but don't mark "two") and then `M-0 M-t` will swap "five", the first word in the region, with "two" the first word _after_ the region.

That last trick works with the other `transpose-` commands as well, not just words: `transpose-chars`, `transpose-lines`, `transpose-paragraphs`, `transpose-sentences`, and `transpose-sexps`. Of course, if that `C-0` trick can't be used with any of those commands to swap the two things you want, there is always `transpose-regions`.

## u/[deleted]
**Votes:** 12

And also we should collect them and save them in a repo, so that those can be seen in the following weeks.

## u/[deleted]
**Votes:** 12

Just do it, it's an open sub.

If you think there is not enough response, then you can also change the subject every week. Rotate some stuff every week to hold inspiration fresh.

## u/jacmoe
**Votes:** 12

Very cool!

I gave up on getting Emacs set up as a C++ IDE, but I am tempted now :)

&#x200B;

Great that we can have a llvm client/server within Emacs!

## u/clemera
**Votes:** 12

For cases where `C-u C-x =` does not work (for example mode line or minibuffer prompt ivy-completions, fringe etc.) use a color picker and provide the value to `list-faces-for-color`:



 
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

## u/w0wt1p
**Votes:** 12

Most of the time when I need to basic calculations, `quick-calc` is enough, no need to fire up `calc`.

But sometimes it is nice to keep the expression as well, for modifying/copying the expression for further calculations.

Also, for example, when planning costs for a project it is nice to keep expressions and results with comments in an org file for future reference.

So I set up this basic function for calculating the expression written on the current line and then **insert the result on the next line**. Bound to <H-return> for easy access without conflicting with other modes.
Nothing fancy, and requires the expression to be on a single line. It can probably be improved a lot, but it does what I intended already.

That said, improvements and suggestions are very welcome, my current elisp skills mostly extends to copy and pasting online example code. :)


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

Usage, on a single line, write some math, and then press `<H-return>`

    125*2+3^2 ;; press <H-return>
    259       ;; answer given and inserted in buffer

## u/permafrosty
**Votes:** 12

For quick note-taking, there is deft:

http://jblevins.org/projects/deft/


## u/xu_chunyang
**Votes:** 12

To replace a shell command with its output, select the command and type `C-u M-| sh`. It can work because bash supports read program via STDIN, Python and Ruby work as well.

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

\`elisp-block!\` will catch any errors in CODE and print them out instead of raising them (and interrupting execution of your \`init.el\`). It will also tell you the specific block where that happened in the message and store the error in case you want to see what it was later. Finally, it will ensure that subsequent blocks of the same category are not run by adding their categories to \`blacklisted-elisp-blocks\`. In effect, this will ensure that any code that depends on a particular elisp block that has an error will not be run.

The main drawback to \`elisp-block!\` is that it will be littered throughout the \`init.el\`. Yet perhaps this is not so bad, I mean we do this with \`use-package\` as well. In any case, IMO the benefits outweigh the costs.

## u/zoechi
**Votes:** 12

I (Emacs rookie) just found out that native/fast JSON support is not guaranteed when emacs 27+ is used. jansson-dev needs to be installed when Emacs is built https://github.com/emacs-lsp/lsp-mode/issues/1557#issuecomment-608409056

## u/rhmatthijs
**Votes:** 12

Working in education, I often find myself having to assign students into groups. This week I made a function in ELisp that helps me do this. Select a region in a buffer that contains a list of students (presumably), call this function, say how many students should be in each group and the function then randomly assigns groups.

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

    (loop n from 0 to 99
        collect (concat "a" n))

for every alphabetical character. Then it'd be a matter of going through those collections for every register created and making sure they use different names. Was wondering if I could get some help.

#### [Isearch](https://www.emacswiki.org/emacs/IncrementalSearch) and [Query Replace](https://www.gnu.org/software/emacs/manual/html_node/emacs/Query-Replace.html)

Not much to say here, just that I might recommend that one go over the default keys for [isearch](https://www.emacswiki.org/emacs/IncrementalSearch#toc2) and query-replace.

Also I would switch `C-s` and `C-r` to use `isearch-forward-regex` and `isearch-backward-regex`, and `M-%` to use `query-replace-regex` because you can disable the use of regex for both. You can even start a query-replace from isearch with `M-%` or `C-M-%`.

#### `event-apply-*` Keys

I don't really know what I can use these keys for, but with which-key you can press `C-x @` to see them. They allow you to apply control, shift, alt, meta, super, or hyper keys.

## u/vatai
**Votes:** 12

The emacs lisp tutorial is the real tutorial for emacs ;)

## u/uptocode
**Votes:** 11

Like Magit? Like writing TODOs in your source code? Check out: 

https://github.com/alphapapa/magit-todos

It uses simple programs like grep or rgrep to show the TODOs in your git repo.

## u/7890yuiop
**Votes:** 11

I find `M-\` preferable to a separate `hungry-delete` package (i.e. do it if that's what you mean to do, but not otherwise).

or `M-SPC` to call `just-one-space` if that's what you mean to do.

or bind something to `cycle-spacing`.

## u/marcowahl
**Votes:** 11

In the spirit of `incf` this is an abstraction to toggle a truth value without having to write the place twice.

E.g. write `(togglef foo)` instead of `(setf foo (not foo))`.

    (defmacro togglef (place)
      "Toggle PLACE.  non-nil becomes nil, nil becomes t.
    PLACE may be a symbol, or any generalized variable allowed by ‘setf’.
    The return value is the new value of PLACE."
      `(setf ,place (not ,place)))

&#x200B;

## u/Vurpius
**Votes:** 11

I just learned that savehist is a thing. It makes minibuffer history persist across sessions. Enable it with `(savehist-mode 1)`.

## u/jumpUpHigh
**Votes:** 11

AucTeX

When you compile your TeX file and there are errors, the message asks you to see error messages using

     C-c `

This leads to the last error which is sometimes incomprehensible and you are left to yourself to figure out what went wrong.

Instead of getting the last error, you can get an overview of all the errors by setting below variable to `t`:

     (setq TeX-error-overview-open-after-TeX-run t)

You can pop this up in a separate frame using:

      (setq TeX-error-overview-setup 'separate-frame)

Related docs are [here](https://www.gnu.org/software/auctex/manual/auctex/Error-overview.html).

This totally changes the way you can handle errors messages.

## u/emacs-noob
**Votes:** 11

I use Emacs for React development and it's usually great (rjsx-mode). We recently introduced styled components into our app and while they're very handy, not having proper css support inside rjsx-mode was pretty annoying. I was looking for solutions, maybe extending rjsx-mode, but I wasn't up to that task. I then realized the built-in emacs commands and buffers themselves could solve my problem! What I want is for css inside a styled component, which always looks something like this:

    const myDiv = styled.div` // notice the backtick
        Some css...
     ` // ending backtick

to *actually* use scss-mode when editing, and then return to rjsx-mode when finished. The elisp is very simple and leads to a trivial workflow:

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


So now when I edit a styled component I just hit **, m s**, which narrows the region to whatever is enclosed by backticks (i.e. all the css) and actually treats it as a bona fide css buffer, with all my snippets, completion, etc. Then when I'm done I just got **, m s** again to widen back to the original (rjsx) buffer!

## u/PotentiallyAlice
**Votes:** 11

I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

    (defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "* TODO test reminder" line into my todo.org. Neat!

## u/dmartincy
**Votes:** 11

If you want to edit the commit that added/deleted a particular line of code, Magit offers `magit-edit-line-commit` that will do the interactive git rebase automatically for you.

`magit-diff-edit-hunk-commit` does the same but from a Magit diff buffer.
`

## u/primitiveinds
**Votes:** 11

I just now figured out that you can interactively pass flags to commands like `counsel-rg` by putting the `--` separator between the flags and the search string, so something like `-g '*.txt' -- whatever` will search for `whatever` only in `txt` files. `counsel` uses a function called `counsel--split-command-args` to split the parts before and after the `--`.

## u/xu_chunyang
**Votes:** 11

Happy Birthday from Emacs, let's assume March 24 is your birthday, put this to your init file, when you open Emacs on your birthday, you'll receive a birthday present from Emacs

    (when (string= "03-24" (format-time-string "%m-%d"))
      (animate-birthday-present user-full-name))

## u/c17g
**Votes:** 11

If you:

1. Develop code on remote machine;
2. Use SSH to access remote; and
3. Annoyed by `flycheck` not working properly over TRAMP

Then consider using [`sshfs`](https://github.com/libfuse/sshfs) to mount your remote filesystem to local. This way, `flycheck` will regard your files just as a local one, and works properly. Neat trick discovered today when debugging flycheck-flake8 with my colleague (are you reading? :P)

## u/Desmesura
**Votes:** 11

I've found that if you press `C-f`, the cursor goes right.

## u/PriorOutcome
**Votes:** 11

I often find myself wanting to be able to switch between `master` and a feature branch in magit quickly:

    (defun lw-magit-checkout-last (&optional start-point)
        (interactive)
        (magit-branch-checkout "-" start-point))
    (transient-append-suffix 'magit-branch "w"
      '("-" "last branch" lw-magit-checkout-last))

So that `C-x g b -` switches to the last branch I was on, similar to `cd -`.

## u/dmartincy
**Votes:** 11

Not very well-known, but Emacs has its own spreadsheet and file format (`.ses`) (`ses-mode`). It supports many advanced features, and formulas are written in Elisp.

&#x200B;

Just another alternative to Org tables.

## u/wasamasa
**Votes:** 11

Last night I wrote some code to improve syntax highlighting for CHICKEN Scheme files. Normally regular `scheme-mode` is sufficient, unfortunately it doesn't handle the non-standard heredoc and `#> ... <#` syntax:

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

## u/tryptych
**Votes:** 11

Emacs has better long-lines support now??

I just noticed while looking at the latest additions in the NEWS:

>\*\* Emacs is now capable of editing files with arbitrarily long lines. The display of long lines has been optimized, and Emacs no longer chokes when a buffer on display contains long lines.  If you still experience slowdowns while editing files with long lines, this is either due to font locking, which you can turn off with M-x font-lock-mode or C-u C-x x f, or to the current major mode or one of the enabled minor modes, in which case you should open the the file with M-x find-file-literally instead of C-x C-f.  The variable 'long-line-threshold' controls whether and when these display optimizations are used.

That sounds like great news!  Does anyone know what went into it?

(edit to add: this was added some time this week.  I rebuild from master weekly, and check out the NEWS diff each time)

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
      ;; Don't occur in *Occur* buffers
      (lambda (buffer) (eq (buffer-local-value 'major-mode buffer) 'occur-mode))
    (cl-remove-if (buffer-list))
    (multi-occur regexp)))
```

This is great if you have IRC chats or emails open in Emacs and need to quickly find a previous conversation.  And of course `occur` works properly with `next-error` / `previous-error` just like the `grep` commands do.

## u/Adorable-Effort
**Votes:** 10

`image-mode` can be used to preview TTF and OTF fonts.

Emacs already opens TTF fonts with `image-mode` automatically, but I also wanted it to do the same with OTF.

    (add-to-list 'auto-mode-alist '("\\.otf\\'" . image-mode))

## u/shoutouttmud
**Votes:** 10

Let me start this thread off: [Pragmatic emacs](http://pragmaticemacs.com/) is a great source of ideas. I recently came across this setting:
        
    (setq wdired-allow-to-change-permissions t)

plus a simple function to replace kill-buffer so that it kill the current buffer without prompting me for what buffer I want to kill(the docstring is mine):

    (defun my-kill-this-buffer ()
      "Kill the current buffer. This function is required because the
    `kill-this-buffer' included in Emacs is safe to use only
    from the menu bar"
      (interactive)
      (kill-buffer (current-buffer)))

## u/DasEwigeLicht
**Votes:** 10

Get info about installed Arch packages, with auto completion:

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


## u/tareefdev
**Votes:** 10

Install [Wttrin](https://github.com/bcbcarl/emacs-wttrin) package, and then add the following to your `.emacs`:

    (require 'wttrin)
    (setq wttrin-default-cities '("XXXXX"))
    (setq wttrin-default-accept-language '("Accept-Language" . "en-US"))
    (global-set-key (kbd "s-w") 'wttrin)

Put your city name instead of XXXXX and change the key binding to whatever you want. Now you can check the weather condition without leaving Emacs :)

## u/ShyGuy32
**Votes:** 10

Inspired by the venerable Twitch [Stay Healthy Bot](https://www.twitch.tv/stayhealthybot), I decided to do up a similar reminder feature from my local Emacs. It's more or less just a timer mixed with alert.el. There's still some work do be done here (mostly in regards to resetting the timer, as my usual workflow is to close out Emacs at the end of the workday), but I've found it pretty useful during the workday to keep me hydrated and moving around.

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

&#x200B;

## u/zreeon
**Votes:** 10

This is basically my setup as well. Seems to work well. 

I set up a little raspberry pi that runs syncthing so that I can sync my phone and computers more easily. Also serves as a nice backup. 

## u/1-05457
**Votes:** 10

If you're using the default VIM mode keybindings, it sounds like you've really fallen for the VIM-style modal workflow.

Also, if you find Spacemacs slow (particularly at startup), try DOOM Emacs.

## u/xu_chunyang
**Votes:** 10

Run multiline code in comment, e.g.,

    ;; (+ 1
    ;;    2
    ;;    3)

with the following advice you can use C-x C-e or C-j as usual:

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

(I already shared this on Emacs China last year, see https://emacs-china.org/t/c-x-c-e/7760)

## u/henry_flower
**Votes:** 10

A replacement for `C-x k RET`:

    (defun my-kill-buffer()
      (interactive)
      ;; a list of buffers we don't want to kill accidentally
      (let ((my-holy-buffers '("*scratch*" "*Messages*")))

        (if (member (buffer-name) my-holy-buffers)
            (message "not so fast")
          (kill-buffer (current-buffer))) ))

    (global-set-key [f8] 'my-kill-buffer)


## u/Rotatop
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

## u/sugarbridalsentry
**Votes:** 10

This is a classic "Wow, Emacs does that?" situation: I discovered `imenu` by accident this week.

On the one hand, it's a great tool for finding definitions in code -- but because my main use of Emacs is for writing and PIM, the thing that really excited me was `imenu-add-menubar-index`. 

In org files, it treats each heading as a part of the index -- so by adding `imenu` to the menu bar, I have fly-out navigation for large org files. (I know people hate the menu bar, but I've found it handy). 

That's all well and good, but now add in the `imenu-list`package, and you have a little pop-up outline of your org file just one keypress away: it's like the bookmarks in a PDF, and has been really helpful already in getting around. Light, clean, easy.

Customizing `org-imenu-depth` sets how deep into a nested hierarchy to index for either `imenu-list` or the menubar index.

## u/WorldsEndless
**Votes:** 10

Just a cool concept: if you have a keypad on your keyboard which you rarely use, bind its nums to something useful. The results are numlock-sensitive and are NOT the same keycodes as regular numbers, so they're just free keys. For example, `(define-key map (kbd "<kp-1>") 'winum-select-window-1)`

## u/andrmuel
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

## u/sauntcartas
**Votes:** 10

This seems almost too basic to mention, but it's handy:

    (defun add-to-hooks (func &rest hooks)
      (dolist (hook hooks) (add-hook hook func)))

Then, for example:

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

## u/Gollum999
**Votes:** 10

I just found out that the tables in `org-mode` support Excel-like calculations.  You can use functions from `calc` as well as arbitrary Elisp expressions!

Only complaint is that it's not the most performant. I tested it on a table of ~1200 rows and it took around 8 seconds to update my calculated column. :/

## u/ahk-_-
**Votes:** 10

I think the best productivity package for emacs is by far nyan-mode. I cannot live without it!

Life is too depressing to code without nyan-mode. Best productivity tool in the world!

## u/11fdriver
**Votes:** 10

Moving, marking & killing by paragraph.

I've been using these for a while as a customisation of Xah-Fly-Keys, but I was surprised to learn that many Emacs users don't know this functionality.

I find that moving by paragraph is a good middle ground between moving vertically by line and moving vertically by page, it's a very natural hierarchy.

I move the page to display the correct section of the buffer, then I move to the right paragraph, then to the right line within that paragraph.

The default bindings for movement are annoying, `M-{`/`M-}`, but it's simple enough to rebind them to `M-[`/`M-]`.

You can mark the current paragraph with `M-h`.

## u/kastauyra
**Votes:** 10

I use Emacs on my laptop either undocked, either with external screens connected. It used to be manual work of dragging and resizing frames around, and then setting up windows, but no more, thanks to the great [dispwatch](https://github.com/mnp/dispwatch) package, which handles the screen changes after Emacs has been started. I [wrote some code](https://github.com/laurynas-biveinis/dotfiles/blob/master/emacs/emacs/setup.el#L154) for the initial frame setup, and now have this, which makes the docking/undocking fully seamless:

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

Some things here might be overkill (cl-defstruct, seq?), but this was also an Emacs lisp exercise.

## u/clemera
**Votes:** 10

If you don't like the look of wave (under)lines in Emacs and prefer straight lines: 

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

## u/char1zard4
**Votes:** 10

This week I learned that:

-	You can redefine all yes/no prompts to y/n:
`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-	`C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-	Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:
https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7

## u/sauntcartas
**Votes:** 10

I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it.  If it isn't, the command operates on the text from point to the end of the buffer.  That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time.  Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add:  Sorry folks, this doesn't work like I thought it did.  See the coments below for details.

## u/jimm
**Votes:** 10

I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.

## u/spfft
**Votes:** 10

Undo-tree and kill-ring are two of the best features in Emacs / packages. Change your life today.

## u/ragoneio
**Votes:** 10

Not sure if it will be helpful to anyone else - but working on legacy code, I often need to review code in large files with hundreds of errors and warnings. So I wrote a package which runs Flycheck on all the files in a diff and filters the output to only the lines which was modified or added! https://github.com/ragone/magit-diff-flycheck. I hope you like it - it’s currently in review to get published on MELPA.

## u/oantolin
**Votes:** 10

`rx` isn't just an (extensible!) sexp syntax for regexps, it's an optimizing compiler!

Examples:

-   `(rx (or "dude@home.org" "dude@work.com"))` produces
    `"\\(?:dude@\\(?:home\\.org\\|work\\.com\\)\\)"`
-   `(rx (intersection (any (?g . ?z)) (any (?a . ?p))))` produces  `"[g-p]"`

And from Lisp code it's easy to plug in computed portions, using `eval`.
For example I recently used this in some silly code to count mentions
of animals in the bible :P:

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

Which I used to make an Org mode table with an Emacs Lisp column formula:

    #+TBLFM: $2='(bible-count $1)

**EDIT**:  A previous version of the `bible-count` function used the `rx` macro and the `eval` construct:

    (defun bible-count (word)
      "Count number of times WORD or its plural appears in the bible."
      (with-current-buffer "king-james-bible.txt"
        (count-matches (rx word-start
                           (or (eval word) (eval (plural word)))
                           word-end))))

This version is incorrect: `eval` is meant to be used only for value known at compile time. It's actual [behavior is very complicated](https://www.reddit.com/r/emacs/comments/g5bat3/weekly_tipstricketc_thread/fo7qdas) and depends on plenty of seemingly extraneous circumstances.

