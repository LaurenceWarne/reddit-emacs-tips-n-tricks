## u/TheDrownedKraken
*Votes:* 35
Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.

## u/[deleted]
*Votes:* 24
[removed]

## u/Gangsir
*Votes:* 21
back-to-indentation. Before I found this function I would always do some awkward triple key combo like C-a M-f M-b.

It's just bound to M-m. Jumps you right to the first non-white space character on the line. What's even spicier is that it works in reverse too - if you're at the front of the line it jumps you forward, if you're at the end or middle it jumps backward.

It still works even on lines that aren't indented, same as C-a in that case.

So useful, especially for resetting point during macros that need to start at the first char on the line.

## u/TeMPOraL_PL
*Votes:* 19
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

## u/tryptych
*Votes:* 18
It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/).  There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.

## u/SamTheComputerSlayer
*Votes:* 18
Just figured this out, maybe a bit of a hack...

In flyspell, I was annoyed I had to use mouse-2 when I wanted to correct a word, and I didn't want to sacrifice a major-mode keybinding to do it from the keyboard. But flyspell actually creates an overlay for misspelled words and attaches a keymap to it, which you can do I just realized- very cool. So I just bound `flyspell-correct-at-point` to "<return>" in the `flyspell-mouse-map`, and now return corrects words when my cursor is on a misspelled word!

But the fact you can attach keymaps to overlays just seems so useful, will definitely use in the future.

## u/github-alphapapa
*Votes:* 18
Here's a popular Emacs config I just rediscovered.  Some cool stuff here.  https://github.com/angrybacon/dotemacs

## u/oantolin
*Votes:* 17
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

## u/sauntcartas
*Votes:* 16
I recently discovered `thing-at-point-looking-at`, which seems much easier to use on its own than to fully define a new kind of "thing."

For a while I've been wanting to conveniently identify a Jira ticket identifier at point so I can browse to it.  Ticket IDs are basically a sequence of letters, a hyphen, and a sequence of digits.  First I tried using `symbol-at-point`, but that can include extraneous neighboring characters, like `/` when the ticket ID is part of a URL.  Eventually, while poring over the `thingatpt` source, I found `thing-at-point-looking-at`, which quickly led to:

    (defun browse-ticket-at-point ()
      (interactive)
      (if (thing-at-point-looking-at (rx (+ alpha) "-" (+ digit)))
          (browse-url (format "https://jirahost/browse/%s" (match-string 0)))
        (error "No ticket at point")))

Easy peasy!

## u/AffectionateAd8985
*Votes:* 16
`(add-hook 'org-mode-hook (lambda () (org-next-visible-heading 1)))`

Move to first heading when open org files, with `org-use-speed-commands`, I can quick browse org file with only `n/p` keys.

## u/TeMPOraL_PL
*Votes:* 16
If you're like me, and your day ends way past midnight, handling those last few tasks in your Org Mode agenda gets tricky. Fortunately, it turns out Org Mode has what I call "25th hour mode".

    ;; consider the current day to end at 3AM
    (setq org-extend-today-until 3) 
    
    ;; make timestamp processing functions aware of this
    (setq org-use-effective-time t) 

Combined, this allows to extend the day past midnight, with things like agenda views, scheduling commands, repeaters, etc. thinking the current time is 23:59 up until the `org-extend-today-until` limit. With this enabled, if I have a task that has a repeater of  and complete it at 01:00, I no longer have to then manually reschedule the task back one day.

## u/pathemata
*Votes:* 15
Something amazin that I have been using recently is `ripgrep-all` as the `consult-ripgrep` command to search in pdfs. 

It is amazing with the `orderless` dispatchers to control the search filtering.
I use `!` to exclude a string and `=` to match exactly.

Also amazing with `embark-collect` which allows collapsing features.
Or within the collect buffer use `consult-line` to further filter.
And even open the pdf.

## u/yousufinternet
*Votes:* 15
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

## u/rucci99
*Votes:* 15
I just found out that Magit can backup changes of uncommitted files automatically. Here's the link to online manual:
[Magit Wip Modes](https://magit.vc/manual/magit/Wip-Modes.html#Wip-Modes).

## u/natarey
*Votes:* 15
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

## u/TheDrownedKraken
*Votes:* 15
It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.

## u/gopar
*Votes:* 15
A very simple thing I've done is remap ";" (semicolon) to to "\_" (underscore) in almost all modes. Since I work with mainly Python, this is so much easier than always doing SHIFT-DASH every couple of keystrokes. And if I want a regular semicolon, I just do "C-u ;" and insert a semicolon

I also set this in modes such as C/C++, etc. This works by automatically setting the last character (if it was an underscore) to a semicolon on enter. 

eg. "|" is cursor

int a = 10\*10\_|

turns into

int a = 10\*10;

| (cursor on new line)

&#x200B;

Pretty simple time saver \\o/

## u/globalcandyamnesia
*Votes:* 14
I'm trying to feminize my voice and org mode has been invaluable.

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

This requires 'SoX' for linux. You can go into the record src block and press \`C-c C-c\` to start recording and \`C-g\` to end. To play back the recording, press \`C-c C-c\` within the play src block. I imagine this might be useful beyond the trans community for basic voice journaling.

## u/Tatrics
*Votes:* 14
I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \*scratch\* buffer and then evaluate it with \`C-x C-e\`.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what  do you think in general!

## u/mrolivertaylor
*Votes:* 14
I use, and love, [transient](https://github.com/magit/transient). I have a ton of commands set up, but the below command is for window manipulation. Personally, I bind it to `s-w`. I use [buffer-move](https://github.com/lukhas/buffer-move) for rearranging windows in a frame.

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

## u/el_tuxo
*Votes:* 13
Working on a remote server with Tramp in eshell it's so easy that I'm always worried that I could run by mistake a command on the wrong machine.

So I implemented a small function that makes me aware that I'm in a Tramp session by changing the prompt color.

    (require 'subr-x)  
    (defun tuxo/prompt-color-tramp ()  
    "Change prompt color if a tramp session is open"  
      (if (file-remote-p default-directory)  
          (set-face-foreground 'eshell-prompt "red")  
          (set-face-foreground 'eshell-prompt "green")))
    
    (use-package eshell
      :hook (eshell-post-command . tuxo/prompt-color-tramp))

Do you have any suggestions on how I could improve this issue?

## u/Stefan-Kangas
*Votes:* 13
This is pretty neat: scrolling up/down one line at a time while keeping the position of point:

`(setq scroll-preserve-screen-position 1)`  
`(global-set-key (kbd "M-n") (kbd "C-u 1 C-v"))`  
`(global-set-key (kbd "M-p") (kbd "C-u 1 M-v"))`  


From: http://pragmaticemacs.com/emacs/scrolling-and-moving-by-line/

## u/oantolin
*Votes:* 13
If you want to search and replace but with preview for the matches, don't use `query-replace-regexp` directly. Instead start by searching for your regexp in `isearch-forward-regexp`, which highlights the matches interactively, and once you have the correct regexp, run `isearch-query-replace` (bound to `M-%` in `isearch-mode-map`).

Note that there is also an `isearch-query-replace-regexp` command but you don't need it: `isearch-query-replace` will automatically detect if your isearch session was for regexps. The docstring for `isearch-query-replace` doesn't seem to mention this nice feature.

## u/mrolivertaylor
*Votes:* 13
I just discovered the [selected](https://github.com/Kungsgeten/selected.el) package, which is brilliant. It creates a keymap that becomes active any time you have an active region. I have bindings for next-line, previous-line, rectangle-mark-mode, end-of-line, upcase-dwim, exchange-point-and-mark, etc. It makes editing and acting on the active region super easy. Sort of like god-mode or Vim's visual mode.

## u/mullikine
*Votes:* 13
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

## u/WorldsEndless
*Votes:* 12
macros in emacs are like a secret, forgotten art, but I use them with regexp search, orgmode commands to tweak repeating events (or any number of other uses). Learn macros; they gave emacs its name! One usage here: https://orys.us/ug

## u/vatai
*Votes:* 12
The emacs lisp tutorial is the real tutorial for emacs ;)

## u/PotentiallyAlice
*Votes:* 12
I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

    (defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "* TODO test reminder" line into my todo.org. Neat!

## u/b3n
*Votes:* 12
Here's a nice eshell command:

    (defun eshell/history ()
      (interactive)
      (insert
       (completing-read "History: " (delete-dups (ring-elements eshell-history-ring)))))

It lets you use your normal completion framework to select an item from history. Suddenly fzf-like history!

## u/emacs-noob
*Votes:* 12
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

## u/celeritasCelery
*Votes:* 12
Shells in emacs like `shell-mode` and `eshell` can write multi line input using `comint-accumulate`. Normally bound to `C-c SPC`.

## u/[deleted]
*Votes:* 11
Org mode - insert a complete set of export options:

`org-export-insert-default-template`

This inserts all export keywords with default values at beginning of line.

This command is not documented in `info emacs` (v27.1).

## u/Stefan-Kangas
*Votes:* 11
Read [SICP](https://mitpress.mit.edu/sites/default/files/sicp/index.html). Preferably in Info, installable through MELPA or: [https://github.com/webframp/sicp-info](https://github.com/webframp/sicp-info)

## u/Stefan-Kangas
*Votes:* 11
Replace the binding for `count-words-region` with `count-words`. The latter has better semantics: it only shows words in region if the region is active.

`(global-set-key (kbd "M-=") #'count-words)`

## u/Bodertz
*Votes:* 11
From the mailing list, I've just learned of `generic-x.el`, which provides syntax highlighting for `/etc/fstab` or `/etc/passwd` and the like.  I appreciated that vim provided that out of the box and I was surprised that emacs also does, but it's just disabled.

`(require 'generic-x)` to enable it.

## u/WorldsEndless
*Votes:* 11
You can use EWW to bypass pay-walls on news sites, and other Javascript-enabled nastiness. Plus, eww can copy from what it sees into equivalent orgmode syntax, and it's also compatible with SPRAY for speed-reading. In otherwords, EWW is great for when you just need to READ the internet.

## u/sauntcartas
*Votes:* 11
I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it.  If it isn't, the command operates on the text from point to the end of the buffer.  That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time.  Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add:  Sorry folks, this doesn't work like I thought it did.  See the coments below for details.

## u/spfft
*Votes:* 11
Undo-tree and kill-ring are two of the best features in Emacs / packages. Change your life today.

## u/rhmatthijs
*Votes:* 11
On a Mac: make Emacs detect if you have light or dark mode enabled system wide.

If you have two themes, a light one and a dark one, and you want the dark theme by default unless you have light mode enabled, add this to your init.el:

    ;; If we're on a Mac and the file "~/bin/get_dark.osascript" exists
    ;; and it outputs "false", activate light mode. Otherwise activate
    ;; dark mode.
    (cond ((and (file-exists-p "~/bin/get_dark.osascript")
                (string> (shell-command-to-string "command -v osascript") "")
                (equal "false\n"
                       (shell-command-to-string "osascript ~/bin/get_dark.osascript")))
           (mcj/theme-set-light))
          (t (mcj/theme-set-dark)))

(mcj/theme-set-light and mcj/theme-set-light are functions that enable the light and the dark theme, respectively).

~/bin/get_dark.osascript contains the following:

    tell application "System Events"
    	tell appearance preferences
    		get dark mode
    	end tell
    end tell

## u/ainstr
*Votes:* 10
The other day I discovered that you can access Spotify through dbus. Most of my use-case for spotify is hitting shuffle on ~20 of my playlists; not much searching, discovering, charts, etc. So, I didn't need any of the existing packages that require an auth token or extra local server.

This basically wraps `completing-read` over the alist stored in spotify-playlists. You can probably translate the qdbus call to dbus-send or whatever.

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

## u/char1zard4
*Votes:* 10
This week I learned that:

-	You can redefine all yes/no prompts to y/n:
`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-	`C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-	Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:
https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7

## u/bgcartman
*Votes:* 10
I little advice I use in org-mode to auto load org-babel modules only when needed.
```
  (defadvice org-babel-execute-src-block (around load-language nil activate)
  "Load ob-{language} only when needed."
  (let ((lang (org-element-property :language (org-element-at-point))))
    (when (or (string= lang "bash") (string= lang "sh")) (setq lang "shell"))
    (unless (cdr (assoc (intern lang) org-babel-load-languages))
      (add-to-list 'org-babel-load-languages (cons (intern lang) t))
      (org-babel-do-load-languages 'org-babel-load-languages org-babel-load-languages))
    ad-do-it))

```

Saves me some time and speeds up loading org-mode as a nice bonus.

P.S. Speaking of speeding up the loading of org-mode I found out that setting this before loading it helps a lot. Obviously one should modify it to include used modules/back-ends:
```
(setq org-modules nil
      org-export-backends '(html)))
```

## u/shitterwithaclitter
*Votes:* 10
I recently had the idea to start emacs in org-mode but have a src block at the top so I can still write elisp snippets easily. Here's the code for anyone interested:

    ;; start in org-mode with a source block for lisp evaluation
    (setq initial-major-mode #'org-mode
          initial-scratch-message "#+begin_src emacs-lisp\n;; This block is for text that is not saved, and for Lisp evaluation.\n;; To create a file, visit it with \\[find-file] and enter text in its buffer.\n\n#+end_src\n\n")

## u/blankspruce
*Votes:* 10
Is there a package similar to wdired or wgrep that would work on magit diffs? 

Particular use cases I have in mind are:

1. You've prepared a commit for pull request and during review someone spotted a mistake that's present in multiple files of that commit. Usually I grep the mistake and edit only affected files with wgrep (there might be some arbitrary reason to not fix similar issue in files not present in the commit).
2. In C++ it happens sometimes that you want to separate declaration and definition and in your commit you forgot to move some definitions to .cpp.
Usually I switch to `foobar.hpp`, kill the necessary part, switch to `foobar.cpp`, yank that part.

## u/andreyorst
*Votes:* 10
Prevent horizontal scrolling from going too far left.

I use Emacs on a laptop and quite often scroll with a touchpad. I also don't use line wrapping, as in code it usually looks misleading, so lines can sometimes exceed window width, either because of some long names or because the current window configuration is too narrow.

However, when scrolling text sideways, there's a small annoyance that the scroll can go way too far to the left. E.g. if this is your window, and your text exceeds it:


    |Short line             |
    |Some really long line o|
    |Another short line     |

What I'd like to is to prevent scrolling any further than that:

    |line                   |
    |eally long line of text|
    |r short line           |

But Emacs actually allows to scroll as far as one would want to, like here:

    |                       |
    |t                      |
    |                       |

This doesn't make sense to me, as you can't see anything at all.
Probably handy, when you write in really long lines, and you wish to have some buffer for adding more text without triggering scrolling, but I never needed that. So I wrote such predicate:

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

This function can calculate window width, and line width, and check if any line in the buffer exceeds the window width screen-wise. By screen-wise I mean that if you've scrolled text to the left, it will begin to return `nil` once all lines don't exceed the right border of the window, thus achieving the described behavior in the diagrams. I then define advice around the `scroll-left` function, and it works pretty good:

    (define-advice scroll-left (:around (foo &optional arg set-minimum))
      (when (and truncate-lines
                 (not (memq major-mode '(vterm-mode term-mode)))
                 (truncated-lines-p))
        (funcall foo arg set-minimum)))

Though it's not very accurate when using `text-scale-adjust`, as line width is not the same as before, the function, that reports how much the window was scrolled to the left still returns unscaled values. You can see my thoughts in the function's comments. Any suggestions on how to make it more accurate?

## u/SamTheComputerSlayer
*Votes:* 10
I use a lot of toggles in my config. I used to do it ad-hoc every time, but the pattern ends up the same so I made this macro:
```
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
```
(deftoggle sam-toggle-theme
  "Toggle theme between light and dark."
  (progn (disable-theme 'dracula)
         (load-theme 'spacemacs-light t))
  (progn (disable-theme 'spacemacs-light)
         (load-theme 'dracula t)))
```

## u/yogsototh
*Votes:* 10
I just made this nice combination of emacs packages and personal theme to achieve the cool effect of iAWriter

See here: https://her.esy.fun/posts/0021-ia-writer-clone-within-doom-emacs/index.html

## u/PriorOutcome
*Votes:* 10
I often find myself wanting to be able to switch between `master` and a feature branch in magit quickly:

    (defun lw-magit-checkout-last (&optional start-point)
        (interactive)
        (magit-branch-checkout "-" start-point))
    (transient-append-suffix 'magit-branch "w"
      '("-" "last branch" lw-magit-checkout-last))

So that `C-x g b -` switches to the last branch I was on, similar to `cd -`.

## u/oantolin
*Votes:* 10
Imenu is pretty adictive and it's disappointing when some major mode doesn't support it. Luckily, it's fairly easy to cook up some regexps to provide imenu support in a new major mode. For example I recently noticed that customize buffers didn't have imenu support add I wrote this:

    (defun configure-imenu-Custom ()
      (setq imenu-generic-expression
            '(("Faces" "^\\(?:Show\\|Hide\\) \\(.*\\) face: \\[sample\\]" 1)
              ("Variables" "^\\(?:Show Value\\|Hide\\) \\([^:\n]*\\)" 1))))

    (add-hook 'Custom-mode-hook #'configure-imenu-Custom)

One subtlety with writing this is that the customize buffers show little triangles instead of the words "Show", "Hide" or "Show Value". To figure out what text is really in the buffer you can use `C-u C-x =` which tells you about any overlays at point.

## u/sauntcartas
*Votes:* 10
I keep forgetting how useful the `pcase` macro is.  Recently I wrote a command that expected to find a single jar file in a certain directory and operate on it.  I started with a more "traditional" implementation:

    (let ((jars (directory-files some-dir t (rx ".jar" eos))))
      (if (= 1 (length jars))
          (do-something-with (car jars))
        (error "Didn't find exactly one jar file")))

Then I remembered `pcase`:

    (pcase (directory-files some-dir t (rx ".jar" eos))
      (`(,jar) (do-something-with jar))
      (_ (error "Didn't find exactly one jar file")))

Much more readable!

## u/11fdriver
*Votes:* 10
Sometimes I'm working on programs with functions a few pages long, and `follow-mode` means that I can open two windows of the same buffer side-by-side and have the text flow like a book between them. I can double or even triple the amount of lines I can view at one time.

This has largely superseded what I might have used those code-overview map things for, which is difficult anyway, since I like to use Emacs from the terminal.

It will keep the text aligned as you move through the file, and pairs well with binding `<mouse-5>` and `<mouse-4>` to the `scroll-up/down-line` commands in `xterm-mouse-mode`.

If I'm studying/notetaking, I often end up with a few Emacs-windows arranged in a vertical stack. `winner-mode` or `window-configuration-to-register` are great, but if I want to quickly regain some vertical screen-real-estate without messing up the layout, then it's pretty intuitive to use `follow-mode` and just switch multiple windows to the same buffer, now they behave like one.

## u/andrmuel
*Votes:* 10
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

## u/Krautoni
*Votes:* 10
Since I find myself pair programming quite a bit, I made a small helper:

```
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

## u/jimm
*Votes:* 10
I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.

## u/hale314
*Votes:* 10
I tend to have a lot of function that is defined solely to be added to a hook. Turns out I can customize `defun-declarations-alist` to define a new `hook` property in the `declare` form. Now I can specify the hook that the function is intended for right inside the function definition.

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

## u/[deleted]
*Votes:* 10
[A beginers guide to emacs 24 or later by sasha chua](https://sachachua.com/blog/wp-content/uploads/2013/05/How-to-Learn-Emacs-v2-Large.png)  this helped me tremendously to get started with emacs.

