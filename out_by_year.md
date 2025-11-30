<a id="comments-grouped-by-year"></a>

# Comments Grouped by Year

-   [Comments Grouped by Year](#comments-grouped-by-year)
    -   [2025](#2025)
    -   [2024](#2024)
    -   [2023](#2023)
    -   [2022](#2022)
    -   [2021](#2021)
    -   [2020](#2020)


<a id="2025"></a>

## 2025


### u/sandinmyjoints [🔗](https://www.reddit.com/r/emacs/comments/t3_1hwf46n/comment/t1_m63mddk)

**Votes** 24

For years, I've figured I was using electric-pairs wrong, until I took some time recently to look into it. One case I regularly encounter whose behavior I don't like (pipe representing where point is when I type the quote key one time):

```elisp
""|word

```

I never want two quotes in this case.

The behavior is mostly controlled by electric-pair-inhibit-predicate. I was using the default (electric-pair-default-inhibit), with electric-pair-preserve-balance set to t. Overall, I realized that electric-pair-preserve-balance is not very useful for me, and what I want is closer to electric-pair-conservative-inhibit, which is what electric-pair-default-inhibit uses when electric-pair-preserve-balance is nil. Turning off electric-pair-preserve-balance fixed the above case.

However, electric-pair-conservative-inhibit still didn't handle well this case that I also often encounter:

```elisp
word|""

```

So I wrote my own, adding one additional condition to the `or` of the builtin electric-pair-conservative-inhibit, and now it finally works in a way that feels natural/correct!

```elisp
(defun my/electric-pair-conservative-inhibit (char)
  (or
   ;; I find it more often preferable not to pair when the
   ;; same char is next.
   (eq char (char-after))
   ;; Don't pair up when we insert the second of "" or of ((.
   (and (eq char (char-before))
```

(eq char (char-before (1- (point)))))

```elisp
;; I also find it often preferable not to pair next to a word.
(eq (char-syntax (following-char)) ?w)
;; Don't pair at the end of a word, unless parens.
(and
 (eq (char-syntax (char-before (1- (point)))) ?w)
 (eq (preceding-char) char)
 (not (eq (char-syntax (preceding-char)) 40) ;; 40 is open paren
      ))))
```


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1loqznm/comment/t1_n0rbpgy)

**Votes** 17

Two tips:

In Emacs 31, there is a new command `tramp-dired-find-file-with-sudo` that lets one more easily visit a file with sudo. See `info "(emacs) Dired Visiting"`.

You can input wildcards and globs while calling `C-x d`, or `dired`. For example, "\\~/.emacs.d/\\\*\*/\\\*.el" creates a dired buffer listing all `.el` files inside \\~/.emacs.d/ recursively. See `info "(emacs) Dired Enter"`.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1l21cgo/comment/t1_mvr341k)

**Votes** 16

A universally useful package that I'd like to recommend is [`ws-butler`](https://github.com/lewang/ws-butler). It automatically trims trailing whitespaces of the lines that were changed whenever you save a file:

```elisp
(use-package ws-butler
  :hook
```

(prog-mode . ws-butler-mode) (text-mode . ws-butler-mode))

The native emacs way to achieve something similar is

```elisp
(add-hook 'before-save-hook 'delete-trailing-whitespace)

```

However, `delete-trailing-whitespace` does not distinguish between changed and unchanged lines, which can be problematic in collaborative projects because it may lead to many changes that you don't want to commit.

edit: typo


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1hwf46n/comment/t1_m60s6o9)

**Votes** 15

A universally useful package that I don't see mentioned enough is [`helpful`](https://github.com/Wilfred/helpful). It improves help buffers (better highlighting, more information, etc; see GitHub for screenshots). Just rebind the help keybindings, defer loading until they are called, and it won't even impact your starting time:

```elisp
(use-package helpful
  :bind
  (("C-h f" . helpful-function)
   ("C-h x" . helpful-command)
   ("C-h k" . helpful-key)
   ("C-h v" . helpful-variable)))
```


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_1mnxffy/comment/t1_n896whs)

**Votes** 15

I use EMMS to listen to very long MP3 files, often over several login sessions. I can't easily remember what time I left off playing a particular file at, so I arranged for it to be saved whenever I pause:

```elisp
(defun save-playing-time-on-pause ()
  (when emms-player-paused-p
    (write-region
     (format "%s %d:%02d"
             (emms-track-name (emms-playlist-current-selected-track))
             (/ emms-playing-time 60)
             (mod emms-playing-time 60))
     nil
     "~/.emms-paused")))

(add-hook 'emms-player-paused-hook #'save-playing-time-on-pause)

```

One of these days I'll write a command to automatically resume playing the last file I paused at the right position.


### u/unduly-noted [🔗](https://www.reddit.com/r/emacs/comments/t3_1icp42g/comment/t1_m9ttaio)

**Votes** 14

On macOS, integrating with shortcuts (or osascript, I suppose) can be powerful. When I'm watching lectures I like to have video on the left, org-mode on the right for notes. However, I found I was often screenshotting the video player and pasting into org-mode. Or Anki.

I was able to create a macOS shortcut which finds the video player regardless of window focus (important so I don't have to leave emacs) and screenshot to clipboard. This is called super easily from emacs: `(call-process "shortcuts" nil nil nil "run" "IINA Screenshot")`.

Now, I have a binding to easily paste a video screenshot (org-download) without pausing the video or even leaving org-mode at all. Or easily paste into Anki. It's a three line function that totally smoothed my workflow.

```elisp
(defun me/iina-screenshot ()
  (interactive)
  (call-process "shortcuts" nil nil nil "run" "IINA Screenshot")
  (org-download-clipboard))
```


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1p00sah/comment/t1_nplvrk8)

**Votes** 14

Isearch searches "incrementally." But there is also non-incremental search (see `(info "(emacs) Nonincremental Search")`): invoke `isearch` (with `C-s` or `C-r` and similar) and press RET on an empty input. This lets you input the entire search string first. I've found this handy at times.


### u/chaozprizm [🔗](https://www.reddit.com/r/emacs/comments/t3_1n0as0m/comment/t1_nbd2w4c)

**Votes** 12

No tip or trick, just want to share that I've been using Emacs extensively for 5 years, and just realized you can undo with "C-/" instead of "C-x u". I've been suffering stupidly for years.


### u/Argletrough [🔗](https://www.reddit.com/r/emacs/comments/t3_1kqup1n/comment/t1_mtmvwj2)

**Votes** 11

There are some useful interactive help commands that aren't bound to keys by default; I find `describe-char` especially useful in Org documents with lots of Unicode characters. Here are my bindings:

```elisp
(use-package help
  :bind
  (:map help-map
        ("=" . describe-char)
        ("j" . describe-face)
        ("-" . describe-keymap)))
```


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1ldcgpi/comment/t1_myjy8bb)

**Votes** 11

You can set `project-compilation-buffer-name-function` to `project-prefixed-buffer-name` to have every compilation buffer created by `project-compile` be prefixed by the name of the project, effectively letting you have compilation buffers per project.el project.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1m06p9l/comment/t1_n42glnr)

**Votes** 11

Quick shoutout to [`move-text`](https://github.com/emacsfodder/move-text), a tiny package that allows you to move the current line (or selected region) up and down using `M-up` and `M-down`:

```elisp
(use-package move-text
    :ensure t
	:config
	(move-text-default-bindings))

```

Great for people who have grown accustomed to moving items up and down with `M-up` and `M-down` in org-mode, and are looking to replicate that behaviour outside of org-mode.


### u/therivercass [🔗](https://www.reddit.com/r/emacs/comments/t3_1nc7vm2/comment/t1_ndpf6em)

**Votes** 11

the feature/igc branch is working fantastically right now. it eliminated all the minor pauses/stutters and doesn't crash. haven't noticed any major slowdowns, either. if you haven't tried it yet, give it a shot.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1no6dq7/comment/t1_nftqn9d)

**Votes** 11

Something I just learned accidentally, that probably many already know: If you want to search for a long word (or two or three), you can press `C-s`, begin typing the word, and - when the point is at the right word - press `C-w` which makes it the search string. No need to finish typing it.


### u/meain [🔗](https://www.reddit.com/r/emacs/comments/t3_1i1sv9u/comment/t1_m7h4xwp)

**Votes** 10

Set buffer to read-only mode if the header(first 10 lines) contains "DO NOT EDIT". These are usually generated files that you wouldn't want to modify by hand.

```elisp
(use-package emacs
  :config
  (defun meain/set-read-only-if-do-not-edit ()
"Set the buffer to read-only if buffer contents has 'DO NOT EDIT' in it.
We limit the search to just top 10 lines so as to only check the header."
(save-excursion
  (goto-char (point-min))
  (let ((content
         (buffer-substring (point)
                           (save-excursion (forward-line 10) (point)))))
    (when (and (not buffer-read-only)
               (string-match "DO NOT EDIT" content))
      (read-only-mode 1)
      (message "Buffer seems to be generated. Set to read-only mode.")))))
  (add-hook 'find-file-hook 'meain/set-read-only-if-do-not-edit))
```


### u/DevelopmentCool2449 [🔗](https://www.reddit.com/r/emacs/comments/t3_1loqznm/comment/t1_n196ge3)

**Votes** 10

I wrote this little snippet for put custom icons in hl-todo keywords.

The `use-package` config here is optional, but you can use it in your existent `use-package` configuration:

>!(NOTE: This requires the `nerd-icons` package installed and loaded)!<

```elisp
(use-package hl-todo
  :defer t
  :hook
  (hl-todo-mode
   . (lambda ()
       (unless hl-todo-mode
         (remove-overlays nil nil 'hl-todo t))))
  :config
  (add-to-list 'hl-todo--keywords ~(,(lambda (bound) (remove-overlays (point) bound 'hl-todo t) nil)))
  :init
  (define-advice hl-todo--get-face (:override () with-icons)
    (let* ((keyword (match-string 2))
           (ov (make-overlay (match-beginning 0) (match-end 0))))
    
      ;; Overlays only for the icons
      (overlay-put ov 'hl-todo t)
      (overlay-put ov 'evaporate t)
      (overlay-put ov 'before-string
                   (pcase keyword
                     ("TODO" (nerd-icons-sucicon "nf-seti-todo"))
                     ("TEMP" (nerd-icons-mdicon "nf-md-timer"))
                     ("BUG" (nerd-icons-faicon "nf-fa-bug"))
                     ("FIXME" (nerd-icons-faicon "nf-fa-wrench"))
                     ("WARNING" (nerd-icons-faicon "nf-fa-flag"))
                     (_ (nerd-icons-mdicon "nf-md-content_paste"))))
    
      ;; Return color for font-lock
      (hl-todo--combine-face
       (cdr (or
             ;; Fast allocation free lookup for literal keywords.
             (assoc keyword hl-todo-keyword-faces)
             ;; Slower regexp lookup.
             (compat-call assoc keyword hl-todo-keyword-faces
                          (lambda (a b)
                            (string-match-p (format "\\~%s\\'" a) b)))))))))

```

Here is how it will look:

![img](https://preview.redd.it/8if6ydxpfsaf1.png?width=379&format=png&auto=webp&s=dfd594118f43a2d1a11d301f1a9ec42748143a83)


### u/alex-iam [🔗](https://www.reddit.com/r/emacs/comments/t3_1mnxffy/comment/t1_n8hzgo2)

**Votes** 10

I've created this hledger-ts-mode: [https://codeberg.org/alex-iam/hledger-ts-mode](https://codeberg.org/alex-iam/hledger-ts-mode) . Hledger is a plain text accounting tool. The mode is not very developed, right now only syntax highlighting and indentation work, so I decided not to create a post. I'm not sure if I will ever develop it much further, as it is enough for my usage. Also, for this to make sense I had to develop a grammar: [https://codeberg.org/alex-iam/tree-sitter-hledger](https://codeberg.org/alex-iam/tree-sitter-hledger)


### u/therivercass [🔗](https://www.reddit.com/r/emacs/comments/t3_1nc7vm2/comment/t1_neqx566)

**Votes** 10

the [proposed eglot patch that adds semantic tokens](https://lists.gnu.org/archive/html/bug-gnu-emacs/2025-09/msg00201.html) is working amazingly. while it's based on the lsp-mode implementation for the same feature, it manages to work better than the original. lsp-mode is running into an issue on the current igc branch where it's exceeding the max lisp eval depth when semantic tokens are enabled (try jumping via xref to a symbol definition). the new eglot implementation just works.


### u/captainflasmr [🔗](https://www.reddit.com/r/emacs/comments/t3_1kqup1n/comment/t1_mtgfbwu)

**Votes** 9

I was catching up with one of System Crafters videos and there was talk around using built-in functionality and how it would be nice if there was an ****orderless**** implementation to allow minibuffer completion on an any word basis.

Well I thought I would take up the challenge and came up with this:

```elisp
(defun simple-orderless-completion (string table pred point)
  "Enhanced orderless completion with better partial matching."
  (let* ((words (split-string string "[-, ]+"))
         (patterns (mapcar (lambda (word)
                             (concat "\\b.*" (regexp-quote word) ".*"))
                           words))
         (full-regexp (mapconcat 'identity patterns "")))
    (if (string-empty-p string)
        (all-completions "" table pred)
      (cl-remove-if-not
       (lambda (candidate)
         (let ((case-fold-search completion-ignore-case))
           (and (cl-every (lambda (word)
                            (string-match-p
                             (concat "\\b.*" (regexp-quote word))
                             candidate))
                          words)
                t)))
       (all-completions "" table pred)))))
    
;; Register the completion style
(add-to-list 'completion-styles-alist
             '(simple-orderless simple-orderless-completion
                                simple-orderless-completion))
    
;; Set different completion styles for minibuffer vs other contexts
(defun setup-minibuffer-completion-styles ()
  "Use orderless completion in minibuffer, regular completion elsewhere."
  ;; For minibuffer: use orderless first, then fallback to flex and basic
  (setq-local completion-styles '(simple-orderless flex basic substring)))
    
;; Hook into minibuffer setup
(add-hook 'minibuffer-setup-hook #'setup-minibuffer-completion-styles)
```


### u/skyler544 [🔗](https://www.reddit.com/r/emacs/comments/t3_1loqznm/comment/t1_n1m3kh6)

**Votes** 9

TIL: Using `completing-read` is quite simple. Here's a command that will let you run a flatpak app, optionally giving it arguments by calling the command with `C-u`.

```elisp
(defun r/run-flatpak-app (prefix)
  "Run a Flatpak application using completing-read."
  (interactive "P")
  (let* ((flatpak-list-command "flatpak list --app --columns=application")
     (flatpak-apps (split-string (shell-command-to-string flatpak-list-command) "\n" t))
     (selected-app (completing-read "Select app: " flatpak-apps))
     (args (if prefix (read-string "Arguments: " ""))))
(start-process "flatpak-run" nil "flatpak" "run" selected-app args)))
```


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1m06p9l/comment/t1_n3u4k0o)

**Votes** 9

Everyone knows that `M-n` and `M-p` in the minibuffer cycles through the history of minibuffer candidates. But not everyone knows that just calling `M-n` without any later candidates also sometimes does something depending on the command&#x2014;you cycle through the so-called "future history." You can read about it in `info "(emacs) Minibuffer History"`. An excerpt:

> If you type ‘M-n’ in the minibuffer when there are no later entries in the minibuffer history (e.g., if you haven’t previously typed ‘M-p’), Emacs tries fetching from a list of default arguments: values that you are likely to enter. You can think of this as moving through the “future history”.

For example, `C-x C-f M-n` completes to the file of the current buffer.


### u/RaisinSecure [🔗](https://www.reddit.com/r/emacs/comments/t3_1no6dq7/comment/t1_nh19e7d)

**Votes** 9

can we get post flair for LLM stuff so people can filter it out?


### u/druuuun [🔗](https://www.reddit.com/r/emacs/comments/t3_1icp42g/comment/t1_ma4bx1b)

**Votes** 8

Found out about setting `shift-select-mode` to `'permanent` today and it's a game changer. I like to use [expreg](https://github.com/casouri/expreg) to expand and contract regions and it always bugged me that once a region has been selected, if I then used shift-translated motion key (e.g. `C-S-f`, `C-S-n`, etc) to change the region, the region would be deactivated. If you set `shift-select-mode` to `'permanent` then you can modify the active region regardless of whether it was created by a non-shift-translated command.


### u/mindgitrwx [🔗](https://www.reddit.com/r/emacs/comments/t3_1icp42g/comment/t1_mczsy27)

**Votes** 8

Isn't this thread usually updated periodically?


### u/80286 [🔗](https://www.reddit.com/r/emacs/comments/t3_1icp42g/comment/t1_ma5ri3n)

**Votes** 8

Recently I discovered a little talked about package: [history](https://github.com/boyw165/history)

Emacs has always had multiple ways to navigate, e.g. via jumping in code via xref or imenu; switching nodes in info pages, etc. What they don't have is an integrated way to walk globally back and forward between all those navigations you made.

It's kind of jarring when you want to go back where you were let's say 4 navigation steps ago, but each step requires a mental context switch to use the functionality's own "go back", whichever did the navigation in that particular place.

So what has been missing is something akin to browser's back/forward buttons, with which you can move to any site you visited in that particular tab, and not just inside one webpage you've been navigating in, which is comparable to how emacs per-functionality history works. Back/forward buttons don't always work perfectly, but they're still a necessity in the age of complex websites.

Recently I had a look at [dgutov's emacs config](https://github.com/dgutov/dot-emacs), and discovered a) there exists the package aiming to achieve exactly this, and b) dgutov has a nice basic setup for it.

So, for the last few days I have finally the "global" history that emacs has been lacking in my pattern of use. It's working great so far. I expect there are bumps because it's a complex issue after all.

My personal setup:

```elisp
(use-package history
    :ensure t
    :bind (
         ("M-8" . #'history-prev-history)
         ("M-9" . #'history-next-history)
         ("M-0" . #'history-add-history))        
    :config
    ;package original: (imenu isearch-mode beginning-of-buffer end-of-buffer)    
    (setq history-advised-before-functions 
          '(isearch-mode 
            find-function-do-it 
            find-library
            imenu beginning-of-buffer 
            end-of-buffer
            xref-find-definitions 
            counsel-imenu counsel-git-grep
            xref-find-references
            paredit-backward-up 
            backward-up-list
            ;; may be risky
            switch-to-buffer
            ))
    (history-mode +1)
    )
```


### u/Hammar<sub>Morty</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_1j8h5aw/comment/t1_mhnx90t)

**Votes** 8

I like Prot's modeline and display-buffer-alist and wanted to try them out. Since he has done such a great job splitting his custom functions into library packages, you can use them without much effort to build your own.

Here is the Elpaca recipe I am using so I don't have to copy the packages into my own config.

```elisp
(use-package prot-modeline
  :ensure (:host gitlab
:repo "protesilaos/dotfiles"
:files ("emacs/.emacs.d/prot-lisp/prot-modeline.el" 
 "emacs/.emacs.d/prot-lisp/prot-common.el")
:main "emacs/.emacs.d/prot-lisp/prot-modeline.el")
  :config ...)

```

![img](https://preview.redd.it/wx10z7ngsjoe1.png?width=1155&format=png&auto=webp&s=ec963d86e0136b0af8863b00ccf3061ade123bfc)

```elisp
(use-package prot-window
  :ensure (:host gitlab
:repo "protesilaos/dotfiles"
:files ("emacs/.emacs.d/prot-lisp/prot-window.el"
 "emacs/.emacs.d/prot-lisp/prot-common.el")
:main "emacs/.emacs.d/prot-lisp/prot-window.el")
  :config ...)
```


### u/melioratus [🔗](https://www.reddit.com/r/emacs/comments/t3_1j8h5aw/comment/t1_mijndf0)

**Votes** 8

Discovered cool backtick syntax when using X command on marked files in dired

For example, Quick way to make individual sha256 checksum files using dired

1.  Mark files in dired
2.  Press `X`
3.  When prompted type

```elisp
 export FILENAME=~?~; sha256sum ${FILENAME} > “${FILENAME}-sha256sum.txt”;
```

1.  Press `return` key. 

2.  Press `g` to refresh dired

Now every marked file should have a named checksum file. 

Try it out


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1kfugbz/comment/t1_mqzd8oi)

**Votes** 8

Been recently motivated to rethink my usage of org-mode on my Android device. I've always had a subpar experience with even basic editing of org mode files on mobile. (Although I've been happy with Orgzly Revived when it comes to interfacing with my todos in org.)

I recently remembered that in that last 2 years or so native Android support for Emacs has become much much better. Or so I heard. So I tested it by installing Emacs from the F-Droid app store and&#x2026; it's amazing! Just in the last few days I've found using Emacs (with my notes files synced from my desktop via Syncthing) a pleasure.

It's truly remarkable how Emacs mostly just works the same way on Android as it does under Linux. Just had to enable some touchscreen specific things and magic!

P.S. I am incredibly appreciative of the Customize and tool bar interface. It was my primary means of setting options and saving them to my init.el.


### u/rafalw [🔗](https://www.reddit.com/r/emacs/comments/t3_1k4w6ov/comment/t1_modpcjd)

**Votes** 8

My first elisp function, any feedback welcome.

C-c b - switches to full-window \\\*Ibuffer\\\*, if already in \\\*Ibuffer\\\* go to previous 'layout'.

```elisp
(defun ibuffer-show ()
  (interactive)
  (if (string-equal "*Ibuffer*" (buffer-name))
      (winner-undo)
    (unless (get-buffer "*Ibuffer*")
      (ibuffer-list-buffers))
    (progn
      (switch-to-buffer "*Ibuffer*" nil t)
      (delete-other-windows))))
    
(global-set-key (kbd "C-c b") 'ibuffer-show)
```


### u/krisbalintona [🔗](https://www.reddit.com/r/emacs/comments/t3_1l21cgo/comment/t1_mw07l5v)

**Votes** 8

I'm not sure in which version this was added, but I've been setting the tab-bar group of certain buffers that I've set in `display-buffer-alist` to open in a new buffer. For instance, the following opens notmuch-hello buffers in a new tab inside the tab-group named "media," creating it if it doesn't exist already:

```elispemacs-lisp
(add-to-list 'display-buffer-alist
           '("\\*notmuch-hello\\*"
             (display-buffer-in-tab display-buffer-full-frame)
             (tab-group . "media")))
```

You can read about it in the docstring of `display-buffer-in-tab`. (Strangely, I there is no mention of "tab-group" in the Emacs Info manual.)


### u/JDRiverRun [🔗](https://www.reddit.com/r/emacs/comments/t3_1m06p9l/comment/t1_n3bmu9f)

**Votes** 8

If you compile your own emacs, you might prefer `xref` (`M-.`) to visit elisp symbols in the original source directory, **not** the install directory. [This small function](https://gist.github.com/jdtsmith/57c9c94bc916b58c0e4ebfc01d300bf1) enables that.


### u/DevelopmentCool2449 [🔗](https://www.reddit.com/r/emacs/comments/t3_1loqznm/comment/t1_n1kp1o6)

**Votes** 8

In emacs 31 there is a new variable `load-path-filter-function` that improves emacs startup time.

Accoding to the commit ([e5218df](https://github.com/emacs-mirror/emacs/commit/e5218df144203ff1b5da3d46b7579b6455008ee7)) where this was implemented:

>Add load-path-filter-function and use it to optimize loading

>When there are many directories on load-path, the part of load which searches load-path can become very slow. By filtering load-path up front to only contain directories which are likely to contain the searched-for file, load becomes much faster.

>This can be set in early-init.el for maximum effect.

I've set it in my `early-init.el` `(setq load-path-filter-function #'load-path-filter-cache-directory-files)` and i've noticed a good improvement in my startup time, from 1.36s to 1.02s, this may be different but the difference is noticeable.

This feature is experimental, but it is worth trying it


### u/Argletrough [🔗](https://www.reddit.com/r/emacs/comments/t3_1m06p9l/comment/t1_n3cylz1)

**Votes** 8

A possibly lesser-known recent Emacs feature is `tab-line-mode`, which provides a tab for each recent buffer on each window, similarly to the tabs in VSCode.

By default, tab-line tabs are closed by calling `bury-buffer`, which unintuitively switches to an arbitrary buffer when attempting to close a window's only tab. This function calls `delete-window` if there is only 1 tab, which is more intuitive:

```elisp
(defun my-close-window-if-last-tab (buffer)
  "Close the tab associated with BUFFER, and ~delete-window' if no other tabs."
  (cond
   ((length= (tab-line-tabs-window-buffers) 1)
    (delete-window))
   ((eq buffer (current-buffer))
    (bury-buffer))
   (t
    (set-window-prev-buffers nil (assq-delete-all buffer (window-prev-buffers)))
    (set-window-next-buffers nil (delq buffer (window-next-buffers))))))

(setopt tab-line-close-tab-function #'my-close-window-if-last-tab)
(global-tab-line-mode 1)

```

FYI, you can middle-click a tab-line or tab-bar tab to close it, which is easier than trying to hit that tiny × button.


<a id="2024"></a>

## 2024


### u/SlowMovingTarget [🔗](https://www.reddit.com/r/emacs/comments/t3_1fzmgwb/comment/t1_lr35bm5)

**Votes** 37

Nothing revolutionary (and veterans will already know this), but a nice little function for splitting out text to a separate file:

1.  Select the text (visual mode with Evil, or however you do it)
2.  `M-x write-region` and give it a file name to write out your selection to the named file. Nice and quick.

It's in the manual, and a basic little feature, but really handy. <https://www.gnu.org/software/emacs/manual/html_node/emacs/Misc-File-Ops.html>

I guess the other tip is to read the manual. :)


### u/Argletrough [🔗](https://www.reddit.com/r/emacs/comments/t3_1hlwpr0/comment/t1_m47cq9g)

**Votes** 22

The built-in `mode-local` package lets you set the values of variables based on the major mode. This lets you avoid the `(add-hook 'foo-mode-hook (lambda () (setq ...))` boilerplate that I see in a lot of people's configs. E.g.:

```elisplisp
(setq-mode-local prog-mode fill-column 100)
(setq-mode-local org-mode display-line-numbers 'visual)
```

See also: [pre-selecting relevant devdocs with `mode-local`](https://gitlab.com/aidanhall/emacs.d/-/blob/aaf1c1a26adc1376630801a011dce8b42b937fee/init.el#L506).


### u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/t3_1c0gg7n/comment/t1_kywimnf)

**Votes** 21

Yasnippet has the capability of surrounding. For example, if you have this C++ namespace snippet:

```elisp
# -*- mode: snippet -*-
# name: namespace-surround
# key: ns
# --
namespace ${1}${1:$(if (> (length yas-text) 0) " {" "{")}
~yas-selected-text~$0
} // namespace${1:$(if (> (length yas-text) 0) (concat " " (substring-no-properties yas-text)) "")}

```

Then if you bind `(yas-expand-snippet (yas-lookup-snippet "namespace-surround"))` to some key, then you can select some code, press your keybinding, and the selected code will be surrounded by "namespace NAME {" and "} // namespace NAME".

![img](https://i.redd.it/fbsbbr98smtc1.gif)


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1gktndf/comment/t1_lvojz57)

**Votes** 20

`use-package` has an inbuilt feature that roughly reports the loading times of each package on startup ([`esup`](https://github.com/jschaf/esup) most likely does a better job, if you can get it to run; there are [known issues](https://github.com/alexmurray/emacs-snap/issues/71) on Emacs snap):

1.  put `(setq use-package-compute-statistics t)` at the beginning of your `init.el`
2.  restart Emacs
3.  do `M-x use-package-report`

Which package is your biggest time sink and why is it worth it? Mine is [`pdf-tools`](https://github.com/vedang/pdf-tools), but to my knowledge there is simply no better alternative for working with pdfs in emacs.


### u/vkazanov [🔗](https://www.reddit.com/r/emacs/comments/t3_1bdm6mc/comment/t1_kuo1f9y)

**Votes** 19

A dump of my Emacs-related principles after 18 years of tinkering:

1.  Don't try to replicate a static IDE setup, Emacs is fluid.
2.  Emacs Lisp is inevitable for Emacser to make this fluidity possible.
3.  Language-agnostic is better than language-specific.
4.  Embrace display-alist, fast window manipulation, winner-mode.
5.  .emacs.el reset every couple of years to accomodate innovation.
6.  Org-mode/org-roam for all documentation, projects, tips. The agenda is not set in stone. Use queries, filters, tweak, evolve things.
7.  A contextual dwim is always better than many keybindings.
8.  Use completion everywhere on everything (vertico is magic).
9.  Contribute to the core and favourite packages.

I am a beginner though, things might change.


### u/Nice<sub>Elk</sub><sub>55</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_1gvkske/comment/t1_lyc7kx9)

**Votes** 13

I used to use the which-key package to discover key bindings, but now have completely dropped it ever since learning about `C-h`. Say you want to use rectangle commands and remember they start with `C-x r`, but can't remember anything after. Just enter `C-x r`, followed by `C-h`. It will list all the keys under that prefix. Seems to work with any prefix key like `M-s`, `C-c`, etc.

A related thing is that you can explore the keys for a major/minor mode with `C-h b`, `describe-bindings`. It used to be pretty useless because it would list every single possible keybinding and accent character, but in newer emacs it's way easier to navigate thanks to folding headings.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1fozctm/comment/t1_lpbqo0e)

**Votes** 12

I recently got tired of constantly having to move my eyes to the bottom of Emacs, so I switched to posframes, [which moves the information in a central pop-up instead](https://blog.costan.ro/img/emacs-completion-system/switch-to.png). For me, this meant using the packages:

\\\* [`vertico-posframe`](https://github.com/tumashu/vertico-posframe) - for [`vertico`](https://github.com/minad/vertico) \\\* [`which-key-posframe`](https://github.com/yanghaoxie/which-key-posframe) - for [`which-key`](https://github.com/justbur/emacs-which-key) \\\* [`transient-posframe`](https://github.com/yanghaoxie/transient-posframe) - for all transient commands, e.g., in [`magit`](https://github.com/magit/magit) or in [`casual-suite`](https://github.com/kickingvegas/casual-suite)

But regardless what you are using, chances are there already is a suitable `*-posframe` package for it. Installing these packages is easy:

```elisp
(use-package vertico-posframe
  :init
  (vertico-posframe-mode))
(use-package which-key-posframe
  :init
  (which-key-posframe-mode))
(use-package transient-posframe
  :init
  (transient-posframe-mode))
```


### u/remillard [🔗](https://www.reddit.com/r/emacs/comments/t3_1cbsvxd/comment/t1_l11l7he)

**Votes** 12

I thought about making a whole post about this, but it's mostly ancillary appreciation so maybe this is a better place. Anyhow, for anyone working with code projects, I'm finding that project.el is completely supplanting my previously heavy use of Treemacs. I liked Treemacs because it's a very similar feel to the file/project sidebar in Sublime Text and VSCode (and others I'm sure). It was kind of reassuring. And dired is nice for many things, but navigating a lot of directories does get a little tedious compared to just opening nested directories in Treemacs.

Well, I don't even remember why I started tinkering with project.el. I think I read a post where someone described switching projects, so I set it up, figured couldn't hurt. Man, definitely a good idea. The "project goto file" (`C-x p f`) is insanely convenient. I also have ctags support setup so a quick keybind will let me switch files based on definition, and between the two, I get through files across the project seamlessly. The project find directory is a faster way to direct dired to a place where I do want to see where things are stored.

I suspect completion is doing a lot of heavy lifting here though too, so a perfectly pristine project.el use might have a different experience, but those are quite common anymore.

Anyway, I haven't popped open the Treemacs sidebar in weeks now, and just felt like I should write something in case someone was curious about how handy it was.


### u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/t3_1b20xgn/comment/t1_ksifwh1)

**Votes** 12

If you use an LSP server with semantic highlighting, it's worth checking out the value of `font-lock-maximum-decoration`. For example, I use `c++-mode` with `lsp-mode` (with clangd), I decreased `font-lock-maximum-decoration` to `2`, and I didn't notice any highlighting difference (because the lost highlighting by `c++-mode` gets highlighted by `lsp-mode`), while `c++-mode` font-locking become faster (`c++-mode` 's font-locking works well 99.9% of the time, but sometimes it can become slow in some circumstances, these slowdowns seems to be gone).

I use:~(setq font-lock-maximum-decoration '((c-mode . 2) (c++-mode . 2) (t . t)))~


### u/mlk [🔗](https://www.reddit.com/r/emacs/comments/t3_1fjnqgy/comment/t1_lnqelw9)

**Votes** 11

I made my agenda collapsible (like org headings) by using outline-minor-mode. To make it work you need to name your agenda heading (`org-agenda-overriding-header`) with a starting asterisk, e.g "\* Current Tasks", "\* Today Agenda\*" etc

```elisp
  (defun my/org-agenda-fold()
  "fold sections of agenda starting with \"* \" tab"
    (interactive)
    (setq-local outline-regexp "^\\* ")
    (setq-local outline-heading-end-regexp "\n")
    (setq-local outline-minor-mode-prefix (kbd "C-'"))
    (outline-minor-mode)
    (local-set-key outline-minor-mode-prefix outline-mode-prefix-map)
    (org-defkey org-agenda-mode-map [(tab)] #'outline-toggle-children)
    (map!
      :after evil-org-agenda
      :map evil-org-agenda-mode-map
      :m "<tab>" #'outline-toggle-children
      :m "<return>" #'org-agenda-goto
      :m "S-<return>" #'org-agenda-switch-to
      :m "C-<return>" #'org-agenda-recenter))
    
(add-hook 'org-agenda-mode-hook 'my/org-agenda-fold)
```


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1f8nxb5/comment/t1_llfyzu6)

**Votes** 11

I regularly work with different emacs frames on different monitors, and [`framemove`](https://github.com/emacsmirror/framemove) is great for switching between them. It can hook into `windmove` (which means switching to an adjacent window becomes switching to an adjacent frame if no such window exists) and offers the functions:

\\\* `fm-up-frame`: move to the frame over the current frame \\\* `fm-down-frame`: move to the frame below the current frame \\\* `fm-left-frame`: move to the frame left of the current frame \\\* `fm-right-frame`: move to the frame right of the current frame

It's not on any of the major package repositories, so you have to install it manually. Thanks to [`emacsmirror`](https://github.com/emacsmirror), this can for example be done as follows using `use-package` and `straight` (adjust bindings to your liking):

```elisp
(use-package framemove
  :straight (:host github :repo "emacsmirror/framemove")
  :init
  (setq framemove-hook-into-windmove t) ;; doesn't work as :config or :custom
  :bind
  (("C-x 5 <up>" . fm-up-frame)
   ("C-x 5 <down>" . fm-down-frame)
   ("C-x 5 <left>" . fm-left-frame)
   ("C-x 5 <right>" . fm-right-frame)))

```

edit: The only thing I don't understand is why setting `framemove-hook-into-windmove` to `t` doesn't work in via `:config` or `:custom`. The package is loaded, `C-h f` confirms that `fm-up-frame` exists, but `C-h v` doesn't know `framemove-hook-into-windmove`. `framemove-hook-into-windmove` exists after I run `fm-up-frame`, but then it's set to `nil` and not `t`.


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_1djdync/comment/t1_l9cmdp9)

**Votes** 11

I started using org-capture when I started my current job several years ago. I recorded my daily activities using this template:

```elisp
(setq org-capture-templates
      '(("d" "Done" entry (file+olp+datetree "~/org/done.org"))))

```

A minor annoyance was that an unwanted link to whatever file location I happened to be in when I invoked org-capture was stored along with my log entry. I made occasional desultory efforts over the years to remove it, but I just couldn't figure it out. I recently made a more concerted effort and <span class="underline">finally</span> was able to work it out after poring over the documentation for the `org-capture-templates` variable. It turns out the default "template" for the `entry` capture type is `"* %?\n%a"`, where the code `%a` means "annotation," ie, a link to the file location. So I just had to change my definition to this:

```elisp
(setq org-capture-templates
      '(("d" "Done" entry (file+olp+datetree "~/org/done.org") "* %?")))
```


### u/Usual<sub>Office</sub><sub>1740</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_1d371oz/comment/t1_l687lg9)

**Votes** 11

I've just started to use macros. They are amazing. Here are my tips. C-x -( to start recording. C-x-) to stop recording. F4 to run last recorded macro. Always start at the beginning of a line. Always move by words or lines. If you go forward two chars and the next line needs you to go forward three chars, the macro won't work. Always return to the beginning of the line. For added awesome move down to the next line, positioning yourself to use the macro again.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1cmzd47/comment/t1_l33y04q)

**Votes** 11

Not from me, but I just wanted to share /u/arthurno1 one-line tip to get `which-key` to work with `dired` (see screenshot in linked post):

<https://www.reddit.com/r/emacs/comments/1clvkfe/announcing_casual_dired_an_opinionated_porcelain/l2yi5tn/>

I assume the same trick applies to other mode-maps as well.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1aky57w/comment/t1_kpct4cp)

**Votes** 11

Many of you probably know of this, but I found "indirect buffers" useful.

When I'm in Vim, I've found it useful to sometimes split a buffer into two windows, and use code folding to view different parts of the same file in the two windows. But this doesn't work in Emacs, because the "folding" and "narrow" states of the buffer are synced between the windows in contrast to Vim. One concrete use case I had: I have a huge Org file, and wanted to narrow `C-x n s` into different headings of the file in different windows.

Indirect buffers solve this. It makes two buffers for one file, and these buffers have separate settings for folding, narrowing, etc. But the buffer contents are still synced, so there's no risk of diverging file states. With default keybindings, I found that `C-x 4 c C-x n s` did what I wanted.


### u/algor512 [🔗](https://www.reddit.com/r/emacs/comments/t3_18xebux/comment/t1_kg4ni5d)

**Votes** 11

Recently I discovered that `C-h C-q` (or `M-x help-quick`) opens a small window showing \\\*Quick Help\\\* buffer with a nice overview of some basic keybindings. It seems that the content of this buffer is configurable via the variable `help-quick-sections`.

I intend to use it as a cheatsheet, reminding me about rare keybindings I always forget; I believe it is easy to make it context-dependent, just by changing the value of `help-quick-sections`.


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1gfhkdg/comment/t1_lui5ao9)

**Votes** 10

I've recently discovered [`org-pdftools`](https://github.com/fuxialexander/org-pdftools) and it offers everything I need for annotating pdfs in emacs. I mainly use it to enhance the inbuilt function `org-store-link`:

\\\* mark a passage in the pdf, run `M-x org-store-link`, and it creates a highlight in the pdf (technically an empty annotation) and copies a link to it. \\\* run `C-c C-l` in any org-buffer, and it pastes the link and prompts you for a name.

The installation is easy, you just need to hook it into `org-mode`:

```elisp
(use-package org-pdftools
  :after (org pdf-tools)
  :hook (org-mode . org-pdftools-setup-link))

```

The only thing to keep in mind is that the highlights in the pdf are not automatically deleted, as you delete the link in the org buffer. You have to do that manually in the pdf (`C-c C-a l` to list all highlights, `D` to delete).

It has less features than [`org-noter`](https://github.com/org-noter/org-noter), but it is more flexible, which is why it suits my use-case better. Unfortunately, it has `org-noter` as a dependency, so you will end up loading it either way.


### u/meedstrom [🔗](https://www.reddit.com/r/emacs/comments/t3_1fe504e/comment/t1_lmzbyro)

**Votes** 10

Elisp list indentation has been fixed since over two years ago (<https://debbugs.gnu.org/cgi/bugreport.cgi?bug=21922>). A lot of people probably still use an initfile hack, to fix the following problem, that lists would indent like this:

```elisp
'(:foo bar
       :baz zab
       :rab oof)

```

But now you can just add a space after the opening paren `'(`. This is the new convention.

```elisp
'( :foo bar
   :baz zab
   :rab oof)
```


### u/fuzzbomb23 [🔗](https://www.reddit.com/r/emacs/comments/t3_1f34tdh/comment/t1_lkrhqf7)

**Votes** 10

One of my favourite tips for managing my init file is the `imenu` support in `use-package`. It's turned off by default, though:

```elisp
(setq use-package-enable-imenu-support t)
```

Combined with a nice imenu UI (`consult-imenu` and Vertico, say) you can navigate your init file really quickly.


### u/pt-guzzardo [🔗](https://www.reddit.com/r/emacs/comments/t3_1chgsxe/comment/t1_l2cv5cx)

**Votes** 10

```elisp
(defun copy-source-for-reddit ()
  (interactive)
  (let ((contents (buffer-substring (point) (mark))))
    (with-temp-buffer
      (insert contents)
      (mark-whole-buffer)
      (indent-rigidly (point) (mark) 4 t)
      (mark-whole-buffer)
      (kill-ring-save 0 0 t))))

```

A handy little snippet for exporting code to reddit markdown. Takes the region, prepends four spaces to each line, and then copies it to the kill ring to be pasted in your browser, without modifying the original buffer.


### u/saltwaterflyguy [🔗](https://www.reddit.com/r/emacs/comments/t3_1bun8ky/comment/t1_kxur0j8)

**Votes** 10

describe-\\\*. It is one of the most useful feature sets to access documentation for just about everything there is in Emacs. Not sure what key bindings are set for a given mode? M-x describe-mode or C-h m. Need to know what font is begin used for a certain piece of text? M-x describe-char. Need to know how a given command works? M-x describe-command or C-h x. Need to know the value of a given variable? M-x describe-variable or C-h v.

If you are new to Emacs you will get so many answers to your questions by getting to know all of the describe functions.


### u/bopboa [🔗](https://www.reddit.com/r/emacs/comments/t3_1aky57w/comment/t1_kphrvz3)

**Votes** 10

This is how to have a beacon without installing any packages.

```elisp
(defun pulse-line (_)
  (pulse-momentary-highlight-one-line (point)))
(setq window-selection-change-functions '(pulse-line))
```


### u/badmaxton [🔗](https://www.reddit.com/r/emacs/comments/t3_19ec8v5/comment/t1_kjcu7vp)

**Votes** 10

Just added this to the `:init` section of my embark configuration:

```elisp
(define-key minibuffer-local-map [C-tab] 'embark-select)

```

This allows super-convenient marking of entries for later `embark-all` using control-tab, instead of having to go first through the `embark` menu. (By default, this key binding is mapped to `file-cache-minibuffer-complete`, which I never use.)


### u/JDRiverRun [🔗](https://www.reddit.com/r/emacs/comments/t3_1933co6/comment/t1_khe4dq6)

**Votes** 10

I have long had convenience bindings for `org-emphasize` like `super-i` for *italic*, that match system bindings. But I always wanted these to **be smarter**, i.e. do something useful when there is no text selected. Something like intelligently toggling emphasis depending on whether you were already in the right kind of emphasized text, or just emphasize the word at point if not.

[Check out my solution](https://gist.github.com/jdtsmith/55e6a660dd4c0779a600ac81bf9bfc23) (scroll down to see how it acts). Will miss this behavior in other apps!


### u/camel<sub>case</sub><sub>t</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_18xebux/comment/t1_kgce54q)

**Votes** 10

This is maybe more a macOS tip than an Emacs tip, but it always bothered me that `C-f`, `C-b`, etc worked in any text box, but not `M-f`, etc &#x2013; turns out that you can easily change that throughout the OS!

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

You can read more here: <https://developer.apple.com/library/archive/documentation/Cocoa/Conceptual/EventOverview/TextDefaultsBindings/TextDefaultsBindings.html>


### u/konrad1977 [🔗](https://www.reddit.com/r/emacs/comments/t3_1gq86x9/comment/t1_lx7y88i)

**Votes** 10

I totally forgot about `use-package-compute-statistics t` and (M-x) `use-package-report`. This helped me optimizing my startup time from around 3 seconds to less than a second.


### u/cidra\_ [🔗](https://www.reddit.com/r/emacs/comments/t3_1h0zjvq/comment/t1_lzbam44)

**Votes** 10

TIL that you can style the GTK components of Emacs by means of simply overriding CSS styling. There's a cool package for that ([custom-css on GitHub](https://github.com/florommel/custom-css.git)) but I also discovered that you can easily tweak it "in real time" and without any additional package by invoking the GTK inspector using the following function:

```elisp
(x-gtk-debug t)

```

One thing that I really wanted to achieve was to get rounded corners in the bottom edges of an Emacs frame using GNOME. To do that it is necessary for the client-side decorations to do so. I tried applying the `border-radius` attribute everywhere but it won't work on the main pane due to it not being a standard GTK component. What I did was putting the toolbar in the bottom position and then apply a `border-radius` styling on it. I also applied the `border-radius` styling on the main window and on the `decoration` component (which gives shadowing to the frame)

![img](https://preview.redd.it/fnf9sp6yoi3e1.png?width=1725&format=png&auto=webp&s=dca1b33886b72e32d445bb02830814357738c2f5)

```elisp
decoration {
    border-radius:12px;
}
    
window{
    border-radius: 12px;
}
    
menubar{
/* 
   For some reason the menu bar 
   gets a border radius as well. 
   Let's cover it 
\*/
    background-color: white;
}
    
toolbar {
    border-radius: 12px;
}

```

Now I wonder if it's possible to remove the header bar but without removing the shadow behind the frame and behind the context menus. 🤔


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1hgx486/comment/t1_m2mx3xd)

**Votes** 10

I just found out that `M-x make-frame` creates the frame on the monitor where the mouse cursor is. So for people who use multiple monitors, one alternative to `M-x make-frame-on-monitor` and selecting the monitor is to simply have the mouse on the monitor you want.


### u/pjhuxford [🔗](https://www.reddit.com/r/emacs/comments/t3_1hgx486/comment/t1_m36rqzv)

**Votes** 10

In a `*Help*` buffer, running `help-view-source` (e.g. by pressing `s`) jumps to the source of the current help item. However, by default it opens the source buffer in a different window to the help buffer.

Personally, I find it much more intuitive for the source buffer window to replace the `*Help*` buffer window. I just found out that in Emacs 29+ this behavior can be achieved by setting the user option `help-window-keep-selected` to a non-nil value. It also re-uses the `*Help*` buffer window when running `help-goto-info` in it (e.g. by pressing `i`).

While reading the news also discovered the new command `help-find-source` in Emacs 30+, which is globally bound to `C-h 4 s`. If a `*Help*` buffer exists (not necessarily in the selected window), then in the current window it jumps to the source file corresponding to the `*Help*` buffer, if one exists.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1fjnqgy/comment/t1_ls4kmt1)

**Votes** 9

[removed]


### u/winters<sub>here</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_1eaw1ia/comment/t1_leom8fv)

**Votes** 9

Simple hack to add attributes to the `<html>` tag of an exported org file by escaping the quote of the language header.

```elisp
#+HTML_DOCTYPE: html5
#+LANGUAGE: en" data-theme="dark

```

This when exported to html using `org-html-export-to-html` will result in:

```elisp
<html lang="en" data-theme="dark">

```

Useful when you include some classless css libraries which require you to add attributes to directly to `<html>` tag.

Don't skip `HTML_DOCTYPE` else it will duplicate the attribute because one extra will be added due to `xml:lang`


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1e5ctk2/comment/t1_ldsl3vy)

**Votes** 9

I recently discovered the [Dimmer](https://github.com/gonewest818/dimmer.el) package and can't believe I didn't try it until now. It subtly dims windows that aren't focused, just enough to draw your eyes to the window that **is** focused. And of course, you can customize how much it dims and exclude certain buffers.

I love things that reduce cognitive load like this. You don't realize how much time you spent looking for little clues like a solid cursor, until you don't have to anymore.


### u/demosthenex [🔗](https://www.reddit.com/r/emacs/comments/t3_1b7uj43/comment/t1_ktogga6)

**Votes** 9

M-x ielm Use the repl while learning elisp coding. I had no idea!


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1gvkske/comment/t1_lyptzyu)

**Votes** 9

For people who need to do a quick calculations from time to time but struggle using `M-x calc`, try `M-x quick-calc`. You can enter expressions such as `2*3.4+5`, and the result will be shown and put into your clipboard.


### u/fv\_\_ [🔗](https://www.reddit.com/r/emacs/comments/t3_1fzmgwb/comment/t1_lr5ceqe)

**Votes** 8

C-x in a transient popup shows menu to save selected options e.g., it can be used to select ~ &#x2013;force-with-lease~ by default while pushing in magit (P p)


### u/rego<sub>b</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_1eghspj/comment/t1_lfskmro)

**Votes** 8

`C-x C-e` to edit current command line in bash which opens the line in emacs (maybe not emacs related but I found this recently useful when working in the terminal)


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1eghspj/comment/t1_lfsffnb)

**Votes** 8

[deleted]


### u/Motor<sub>Mouth</sub>\_ [🔗](https://www.reddit.com/r/emacs/comments/t3_1eghspj/comment/t1_lg6q2rq)

**Votes** 8

For users of [rg.el](https://github.com/dajva/rg.el) (emacs interface to ripgrep) there is [rga](https://github.com/phiresky/ripgrep-all) (ripgrep-all) a wrapper around ripgrep that "enables it to search in pdf, docx, sqlite, jpg, movie subtitles (mkv, mp4), etc." You can set the rg executable in the configuration of rg.el like so: `(setq rg-executable (executable-find "rga")` to enable search in multiple document types.


### u/JDRiverRun [🔗](https://www.reddit.com/r/emacs/comments/t3_1de1hkf/comment/t1_l8d30q3)

**Votes** 8

A few people have asked about my code to change cursor color when repeat-mode is active (i.e. while you are repeating a command). I rely on it. See [this gist](https://gist.github.com/jdtsmith/a169362879388bc1bdf2bbb977782d4f) for the details.


### u/AdjointFunctor [🔗](https://www.reddit.com/r/emacs/comments/t3_1cmzd47/comment/t1_l359pqx)

**Votes** 8

I (re) discovered rectangle mode recently. Very useful when deleting lots of indents. C-x spc then make the region. <https://emacsredux.com/blog/2014/01/01/a-peek-at-emacs-24-dot-4-rectangular-selection/>


### u/lesliesrussell [🔗](https://www.reddit.com/r/emacs/comments/t3_198rnkj/comment/t1_kibmgv2)

**Votes** 8

[transient map for movement](https://gist.github.com/lesliesrussell/46302d413fcf49e9717eeea57fdadcbf)

Defines a transient keymap for movement controls and sets up a global key binding to activate this transient map. This transient map, \\~my-movement-transient-map\\~, includes bindings for various movement commands like moving forward or backward by a word or character and moving to the next or previous line. The \\~activate-my-movement-map\\~ function is defined to activate this transient map, and it is globally bound to \\~C-f\\~.

&#x200B;

This setup allows you to press \\~C-f\\~ followed by one of the specified keys (\\~f\\~, \\~b\\~, \\~c\\~, \\~l\\~, \\~n\\~, \\~p\\~) to perform the corresponding movement operation. The \\~set-transient-map\\~ call with a second argument of \\~t\\~ ensures that the transient map stays active until one of its keys is pressed.

&#x200B;

This is a neat way to create a custom, modal-like interface for movement within Emacs, leveraging your Emacs Lisp skills to tailor your editing environment to your preferences. If you have any specific modifications or additional features you'd like to implement, feel free to ask!

I didn't want to drop code in the thread so i put it in a gist


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1aw6xkc/comment/t1_kriu3ye)

**Votes** 8

Two tricks that helped me make my workflow a lot better:

1.  `(setq process-adaptive-read-buffering nil)` makes [EAT](https://codeberg.org/akib/emacs-eat) a lot quicker!
2.  `(setq eglot-events-buffer-size 0)`, `(fset #'jsonrpc--log-event #'ignore)` and [eglot-booster](https://github.com/jdtsmith/eglot-booster) makes eglot A LOT quicker!


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_1afd05g/comment/t1_kob6a5m)

**Votes** 8

One thing I found cool is that you can actually use modifiers like ****Super and Hyper in terminal Emacs****. It even works over SSH. No changes needed to `init.el`.

The trick is that Emacs has built-in keybindings like `C-x @ s` that simulates a super modifier and `C-x @ h` that simulates a hyper modifier. So if you press e.g. `C-x @ s a`, then Emacs will interpret that as super+a.

Terminals like e.g. Kitty lets you bind super+a to do whatever you want, so you can simply map it to send the key sequences that Emacs expects. Just add this to e.g. `kitty.conf`:

```elisp
map super+a send_key ctrl+x @ s a
map super+b send_key ctrl+x @ s b
...
```

And voila, super works fine in `emacs -nw`! Kitty translates `s-a` into normal terminal-supported control keybindings `C-x @ s a`, which Emacs then translates back into `s-a`.


### u/LionyxML [🔗](https://www.reddit.com/r/emacs/comments/t3_1b20xgn/comment/t1_kslwb72)

**Votes** 8

A blog post regarding my own Emacs config aiming to get the same user experience on both TUI and GUI. [https://www.rahuljuliato.com/posts/lemacs](https://www.rahuljuliato.com/posts/lemacs)


### u/jcubic [🔗](https://www.reddit.com/r/emacs/comments/t3_1b20xgn/comment/t1_ksoij65)

**Votes** 8

I use this often when working on large files. You can bookmark up to 9 places inside a file and jump to that position. The limitations is that if you add somehing above the bookmark the position is shifted a bit but it's not that hard to find the right place.

It works like this: `C-c 0` creates a bookmark and `C-c <1-9>` jumps into a bookmark. I've written about this on my old blog. I still use this from time to time.

[Faster buffer bookmarking in Emacs](https://jcubic.wordpress.com/2012/01/25/faster-buffer-bookmarking-in-emacs/).


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1c64gcj/comment/t1_l01n03t)

**Votes** 8

Just found out about the variable `confirm-kill-emacs`. Never again will I quit emacs by accident, because auctex bound a frequently used function to `C-c C-v` (one key away from `C-x C-c`).


### u/ImJustPassinBy [🔗](https://www.reddit.com/r/emacs/comments/t3_1hlwpr0/comment/t1_m3qn07c)

**Votes** 8

\\\*\*PSA for latex users:\*\* [`bibtool`](https://github.com/ge-ne/bibtool) is a software for cleaning up `.bib` files and it is bundled with most tex distributions. It can sort bibliography entries, uniformize their layout, and even systematically generate the keys. You can use it by running:

```elisp
bibtool input.bib -o output.bib 

```

(the default output doesn't look too good, see below for a minimal config)

\\\*\*For latex+emacs users:\*\* Here is a small function that runs `bibtool` on the current buffer:

```elisp
(defun my/bibtool-current-file ()
  "Run bibtool on the current buffer's file."
  (interactive)
  (let ((file (buffer-file-name)))
    (if file
        (let ((default-directory (file-name-directory file))
              (base-file (file-name-nondirectory file)))
          (shell-command (concat "bibtool " base-file " -o " base-file)))
    (message "Not visiting a file!"))))

```

And here is a minimal config for `bibtool`, just save it as `.bibtoolrsc` in the folder your are invoking `bibtool` or in your home folder:

```elisp
sort = on
sort.format = {%N(author)}

sort.order{* =
    author
    bibkey
    title
    editor
    booktitle
    mrnumber
    zbl
    journal
    fjournal
    series
    volume
    number
    note
    howpublished
    address
    organization
    publisher
    edition
    pages
    year
    month
    doi
    url
}

print.align.key = 0
print.line.length = 120
preserve.key.case = on
sort.cased = off
print.use.tab = off

fmt.name.name = { }
fmt.inter.name = { x }
```


### u/w0ntfix [🔗](https://www.reddit.com/r/emacs/comments/t3_1b7uj43/comment/t1_ktmhg6g)

**Votes** 8

idk who needs to hear this but the `no-littering` package has a function `(no-littering-theme-backups)` for setting the location of backups, autosaves, and undo-tree


<a id="2023"></a>

## 2023


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_10qo7vb/comment/t1_j6rmvvf)

**Votes** 33

When you have an active region, `undo` will only undo changes in that region instead of the whole file.


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_j8m9rlj)

**Votes** 20

With an active region, you can freely toggle between rectangle mark mode and normal, you don't need to get rid of your active region to switch between the two.


### u/AndreaSomePostfix [🔗](https://www.reddit.com/r/emacs/comments/t3_12cd23k/comment/t1_jf167qh)

**Votes** 19

org-mode is amazing!

I discovered \\~org-copy-visible\\~ the other day, when I wanted to send somebody only the outline of my notes.

That function (which is bound to C-c C-x v by default) let you copy just the outline for the selected region: very useful!


### u/alvarogonzalezs [🔗](https://www.reddit.com/r/emacs/comments/t3_12jexep/comment/t1_jg34ody)

**Votes** 18

I'm a big user of `ffap`. I use this function with `M-x` each time I want to open a file whose name is under the cursor.

But this week I discovered `ffap-bindings`. This function replaces some key bindings to use `ffap` when it makes sense. For example, it replaces `find-file` with `find-file-at-point`, so the usual keybindings are enriched at no cost.


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_12rlq4a/comment/t1_jgwlxuw)

**Votes** 16

Often when literate programming I want to split up a code block, maybe copy-pasted with multiple functions in it, into separate blocks so I can put some text in between them. The command, with cursor within a `BEGIN_SRC` block, is `org-babel-demarcate-block` `(C-c C-v d)`.


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_1758wua/comment/t1_k4g09iw)

**Votes** 15

`(delete-blank-lines)` `(C-x C-o)` is massively useful; I use it every day for text cleanup. Press it once and it deletes all but one blank line. Press it twice and it deletes that one, too.


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_11rq2gl/comment/t1_jc9t4tc)

**Votes** 15

Plain old `query-replace` has many cool features, first of all it respects the active region (if it's active it will only query for replacements in the active region). There are many useful keys in addition to plain `y~/~n`:

`!`: replaces all remaning matches

`u`: undo last replacement

`E`: changes replacement string on the fly

And many more you can see using `?`.


### u/alvarogonzalezs [🔗](https://www.reddit.com/r/emacs/comments/t3_16tes2a/comment/t1_k2gb81l)

**Votes** 14

If you need to find all the occurrences of a string in the project files, but only ****on some specific type of files****, you can use `consult-ripgrep` with `-- -t` in the search pattern.

For example, If you need occurrences of `fancystr` in files of type `html`, the search pattern should be `fancystr -- -t html`

From `consult-grep` documentation, command line options can be passed to grep, specified behind `--.` The overall prompt input has the form:

```elisp
#async-input -- rg-opts#filter-string

```

I have just discovered this, and it made my day.


### u/leothrix [🔗](https://www.reddit.com/r/emacs/comments/t3_13jvhp7/comment/t1_jl5zu6z)

**Votes** 14

For `use-package` users (which I assume is many of us), did you know that profiling is easy to do? I'm not talking about `esup`, but a built-in capability that makes it very straightforward to find places to optimize your `init.el` for significantly faster start times.

Enable `use-package-compute-statistics` right after you load `use-package`:

```elisp
(setq use-package-compute-statistics t)

```

Restart emacs, and then invoke `use-package-report`. You'll get a table of the load times for each package that `use-package` manages. I discovered this and found an immediate way to cut my startup time in half by fixing a few packages that weren't deferred properly by adding the right `:hook` keyword.


### u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/t3_11lqkbo/comment/t1_jbe06qv)

**Votes** 13

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


### u/ayy<sub>ess</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_18hc301/comment/t1_kdobd72)

**Votes** 12

I recently discovered `(setq read-minibuffer-restore-windows nil)` which resolves my frustration that quitting the minibuffer would discard any changes to the window layout while the minibuffer was open. For example, by default, `M-x C-h k k C-g` quits the just opened help buffer. I'm sure to have missed many more QOL improvements from NEWS.


### u/eleven<sub>cupfuls</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_10ktqj0/comment/t1_j5umed8)

**Votes** 12

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


### u/leothrix [🔗](https://www.reddit.com/r/emacs/comments/t3_17qh1hn/comment/t1_k8dlt4c)

**Votes** 10

Need to remove an element from a list when you're tinkering with elisp?

Sometimes when I'm adding and removing elements from hooks or variables like `completion-at-point-functions` I'll often need to tinker with the symbols I've added. You could evaluate some form somewhere, but I like to be lazy and just:

```elisp
M-x remove-hook

```

And you've got an interactive interface (using `completing-read`) for removing arbitrary elements from any list-like variable. It's <span class="underline">technically</span> for altering hooks, but you can abuse it to fool around with lists, too.


### u/Netherus [🔗](https://www.reddit.com/r/emacs/comments/t3_17qh1hn/comment/t1_k8c4mz7)

**Votes** 10

Just recently found out M-u makes the next word upper case, and the same for M-l for lower case. Maybe nothing fancy, but it's kinda handy for me.


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_12cd23k/comment/t1_jf3ohpv)

**Votes** 10

I work with multiple Git repositories in my day job, but one in particular occupies 95% of my time. I've often wished I could set up Projectile so that if I run one of its commands while not in any repo, it will behave as if I'd changed to that main repo first. I couldn't find a built-in way to do that, but got the effect I wanted with some advice:

```elisp
(defun default-to-main-project (dir)
  (or dir *main-project-dir*))

(advice-add 'projectile-ensure-project :override #'default-to-main-project)

```

I lose some of the functionality of `projectile-ensure-project`, but I never used it anyway.


### u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_11rq2gl/comment/t1_jca66k0)

**Votes** 10

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


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_j8mo1bz)

**Votes** 10

Put the control keys next to space, mimicking mac's command key (which is effectively used as the equivalent of ctrl yet next to the space it's easier to press)

Win - Alt - Ctrl - Space - Ctrl - Alt - Win


### u/AnugNef4 [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_ja41lso)

**Votes** 10

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


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_108zin2/comment/t1_j420ea0)

**Votes** 10

Do you want a key binding to wrap the selection in some kind of delimiter? Here's a built-in solution:

```elisp
(defvar insert-pair-map
  (let ((map (make-sparse-keymap)))
    (define-key map [t] #'insert-pair)
    map))

(global-set-key (kbd "C-S-w") insert-pair-map)

```

This setups up `C-S-w` to be a prefix map, `insert-pair-map`. The only key binding in `insert-pair-map` is for `[t]`, which means it is the default key binding and any key after the prefix will run the same command: `insert-pair`. Now, `insert-pair` looks at which key was used to invoke it and if it is an opening delimiter it inserts both it and the corresponding closing delimiter (and if the region is active it insert the opening delimiter at the start and the closing delimiter at the end, wrapping the region).


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_18mplfa/comment/t1_ke5xr5j)

**Votes** 9

This makes stack-outputs of debug-buffers much more readable:

```elisp
(setopt debugger-stack-frame-as-list t)
```


### u/frosch03 [🔗](https://www.reddit.com/r/emacs/comments/t3_15yxdz3/comment/t1_jxekm3a)

**Votes** 9

Very useful, but I keep forgetting it:

If you have two buffers open in one frame, where one contains just a few lines and otherwise just uses up a lot of space, you can shrink that buffer down just right by using: `C-x -`

And if you want to balance these two buffers again just use `C-x +`


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_15sjm3k/comment/t1_jwff8bw)

**Votes** 9

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


### u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/t3_14l3jn8/comment/t1_jpwn2ts)

**Votes** 9

I was converting some old `.odt` notes files to `.org` today, and one of the things I wanted to do was to add two spaces after end of sentence periods for proper sentence navigation. So there I was figuring out a general enough regexp for the nth time and, of course, I regretted not having taken note of this the last time. So I decided to do some searching for a good regexp and write it down this time, since this was obviously shared by someone somewhere. And it turns out Emacs has us covered, and I never knew: `repunctuate-sentences`. I have no idea if this is new or has always been there. It is new to me. It uses `query-replace-regexp`, so it's the same experience. And also can be configured for exclusions with `repunctuate-sentences-filter`. Neat!


### u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_j8mpo5y)

**Votes** 9

Does anyone have an `aspell` setup with multiple dictionaries?

When I try `--extra-dict` option I get an error: `Expected language "en" but got "de"`.


### u/vjgoh [🔗](https://www.reddit.com/r/emacs/comments/t3_18149ql/comment/t1_kahspwz)

**Votes** 8

I used to have a problem where eglot would decide that many mid-hierarchy directories were the project root and spin up a separate instance of clangd for each one (sometimes 10 or 12 total). This was almost certainly due to using emacs' built-in `project` to handle project discovery. At that point, I switched to lsp-mode because I generally find `project` to be impenetrable and poorly documented compared to projectile.

I was forced to go back to eglot, however, because lsp-mode has been failing to parse things well for a while.

Long story short, here's how you force `project` to find the actual project root if the automatic detection doesn't work. With this, eglot started working great, didn't spin up 10 instances of clangd, and has generally been ticking over just fine.

`(setq project-vc-extra-root-markers '(".project.el" ".projectile" ".dir-locals.el"))`


### u/AP145 [🔗](https://www.reddit.com/r/emacs/comments/t3_16hh7u4/comment/t1_k1803gl)

**Votes** 8

I used to always get confused why people say that the default Emacs key bindings hurt their pinky's, but then I realized that I don't touch type and thus I don't have to stretch my fingers anywhere for the default key bindings. Ironically my ineptitude at typing has saved me from repetitive stress injuries.


### u/BunnyLushington [🔗](https://www.reddit.com/r/emacs/comments/t3_12zaqju/comment/t1_jhrzybp)

**Votes** 8

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

I'd forgotten about `with-output-to-temp-buffer` which is pretty handy. The `t` at the end is there just to suppress an overly large echo area message.

(This should be obvious but note that the JWT is not validated or verified. This is intended for debugging only and the JWT should not be trusted.)


### u/w0ntfix [🔗](https://www.reddit.com/r/emacs/comments/t3_11ey9ft/comment/t1_jajfxc9)

**Votes** 8

turning off org-elements cache speeds up input latency for me (found from profiling):

```elisp
(setq org-element-use-cache nil)


```

it seems (at least on my org 9.6.1) to update the cache after calls to `org-self-insert-command` (so, a lot!)


### u/SlowValue [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_j8u1ebf)

**Votes** 8

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


### u/Nondv [🔗](https://www.reddit.com/r/emacs/comments/t3_108zin2/comment/t1_j4ct1y1)

**Votes** 8

Maybe not new for anyone but I only recently found out that `C-c <any letter>` is conventionally reserved for user bindings. I was constantly afraid to define my own bindings bc of a potential clash so tended to use M-x instead. Now I finally bind my most used commands.

With the above in mind, Im also afraid to forget my bindings. I use which-key package so I wrote a function "define-my-keybinding letter fn" which binds the letter to `C-c <letter>` and to "my-bindings" keyset (prefix) which itself is bound to `C-c m`. Basically, if i forget what bindings I use, I just press C-c m and which-key shows me all of MY bindings (yes, it shows them with C-c too but it's mixed with mode bindings so not helpful)


### u/hunajakettu [🔗](https://www.reddit.com/r/emacs/comments/t3_16tes2a/comment/t1_k2f683f)

**Votes** 8

It is the only thing that keeps me sane in a Windows shop.


### u/habamax [🔗](https://www.reddit.com/r/emacs/comments/t3_13qfepf/comment/t1_jli02ld)

**Votes** 8

I often do simple manuals with a lot of screenshots, now in `org`.

So I came up with the `org/insert-screenshot` command that works in windows and linux (wayland with `wl-paste` available):

```elisp
(defun org/insert-screenshot ()
  (interactive)
  (let* ((img-dir (concat "img-"
                          (file-name-sans-extension (buffer-name))))
         (img-name (concat (file-name-sans-extension (buffer-name))
                           "-" (format-time-string "%Y%m%d-%H%M%S") ".png"))
         (filename (concat img-dir "/" img-name)))
    (make-directory img-dir :parents)
    ;; Windows -- use powershell, other(implicit linux) -- use wl-paste
    (shell-command
     (if (eq system-type 'windows-nt)
         (concat
          "powershell -command \"Add-Type -AssemblyName System.Windows.Forms;"
          "if ($([System.Windows.Forms.Clipboard]::ContainsImage()))"
          "{$image = [System.Windows.Forms.Clipboard]::GetImage();"
          "[System.Drawing.Bitmap]$image.Save('"
          filename
          "',[System.Drawing.Imaging.ImageFormat]::Png);}\"")
       (concat "wl-paste > " filename)))
    (insert (concat "[[file:" filename "]]"))))
    
```

So the flow is:

1.  make a screenshot using OS
2.  goto org document (`doc1.org` for example)
3.  `M-x org/insert-screenshot RET`
4.  image file is saved under `./img-doc1/doc1-20230525-100621.png`
5.  link is inserted into org document


### u/swhalemwo [🔗](https://www.reddit.com/r/emacs/comments/t3_12zaqju/comment/t1_jhtis87)

**Votes** 8

recently started writing a report with a lot of numbers, many of which will change in later versions. I wanted to save myself the work of updating them all by hand, so I learned that I can [generate org macros from my `R` script](https://emacs.stackexchange.com/questions/14647/use-code-variable-in-org-mode-continuous-text), [display their values rather than the macro text](https://notes.alexkehayias.com/emacs-inline-macro-in-the-buffer/), and pass `org-macro-templates` to `consult--read` to select and insert them with a few keystrokes. took me a couple of hours but hopefully will save them later on!


### u/sebasTEEan [🔗](https://www.reddit.com/r/emacs/comments/t3_12zaqju/comment/t1_jhwipv8)

**Votes** 8

In a presentation this morning, I wanted to show a file, but it contained sensitive information: IP addresses and passwords. The following code hides this secret in a buffer:

```elisp
(defun sm/hide-ip-addresses ()
  "Hide IP addresses in the buffer."
  (interactive)
  (let ((ipv4-regex "\\b\\(?:[0-9]\\{1,3\\}\\.\\)\\{3\\}[0-9]\\{1,3\\}\\b")
	(ipv6-regex "\\b\\(?:[0-9a-f]\\{1,4\\}:\\)\\{7\\}[0-9a-f]\\{1,4\\}\\b\\|\\b\\(?:[0-9a-f]\\{1,4\\}:\\)\\{1,6\\}\\(:[0-9a-f]\\{1,4\\}\\)\\{1,6\\}\\b"))
(save-excursion
  (goto-char (point-min))
  (while (re-search-forward ipv4-regex nil t)
	(let ((overlay (make-overlay (match-beginning 0) (match-end 0))))
	  (overlay-put overlay 'hidden-text t)
	  (overlay-put overlay 'display "***.***.***.***"))))
(save-excursion
  (goto-char (point-min))
  (while (re-search-forward ipv6-regex nil t)
	(let ((overlay (make-overlay (match-beginning 0) (match-end 0))))
	  (overlay-put overlay 'hidden-text t)
	  (overlay-put overlay 'display "****:****:****::****"))))))

(defun sm/hide-passwords ()
  "Hide passwords in buffer."
  (interactive)
  (let ((pwd-regex ".*passwor[dt]*:\\(.*\\)"))
(save-excursion
  (goto-char (point-min))
  (while (re-search-forward pwd-regex nil t)
	(let ((overlay (make-overlay (match-beginning 1) (match-end 1))))
	  (overlay-put overlay 'hidden-text t)
	  (overlay-put overlay 'display "******"))))))

(defun sm/hide-secrets ()
  "Hide IP addresses and passwords in the buffer."
  (interactive)
  (sm/hide-ip-addresses)
  (sm/hide-passwords))

(defun sm/remove-overlays ()
  "Remove all overlays with the ~hidden-text' property in the buffer."
  (interactive)
  (dolist (overlay (overlays-in (point-min) (point-max)))
(when (overlay-get overlay 'hidden-text)
  (delete-overlay overlay))))
```


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_112t0uo/comment/t1_j9pr766)

**Votes** 8

[deleted]


### u/AffectionateAd8985 [🔗](https://www.reddit.com/r/emacs/comments/t3_17vp0o1/comment/t1_k9g3i9s)

**Votes** 8

In eshell, `cd =xxx` behaves like `z xxx` in [zoxide](https://github.com/ajeetdsouza/zoxide), jump to recent directory matching `xxx` pattern.


### u/desquared [🔗](https://www.reddit.com/r/emacs/comments/t3_11ey9ft/comment/t1_janipaz)

**Votes** 8

For a while, I've been thinking about a nice way to get arbitrary tooltips in org mode. I just figured out something pretty easy: just use a nonexistent link type.

I don't care about the follow behavior, or export &#x2013; just showing the tooltip under the house, or in the help-echo area, so if I do something like

```elisp
[[abbrev:this stands for Some Random Acronym][SRA]]

```

the buffer shows "SRA" as an org link, and when point or the mouse is over it, I get the expected message. There's nothing special about "abbrev", except that it hasn't been defined as an org link type. If I try to follow the link, org tries to create a heading, but for such things, I'm not going to follow the link, or export it, so that behavior doesn't matter.

Another use is for footnote-style things: say, in a table you want a cell where you can add some kind of note or comment, but don't want the cell to be very big. You can make your table like this:

```elisp
| col A | notes |
|-------+-------|
|  1234 | [[whatever:this is a note or comment about this row in the table][note]]  |
|       |       |

```

and the cell just says "note", but I can see the rest when I hover.

To get the link target &#x2013; the note, the abbreviation, and so on &#x2013; to display in the help-echo area, you'll need to set `help-at-pt-display-when-idle` suitably: see <https://emacs.stackexchange.com/questions/54319/how-to-display-target-of-an-org-mode-link-in-the-echo-area-or-as-tooltip>.


### u/elimik31 [🔗](https://www.reddit.com/r/emacs/comments/t3_118sowc/comment/t1_j9jgfhg)

**Votes** 8

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


### u/snippins1987 [🔗](https://www.reddit.com/r/emacs/comments/t3_10ktqj0/comment/t1_j67y1pt)

**Votes** 8

Context: after finally getting into org-mode (org-roam specifically) and writing a bunch of elisp, I still dot not at all satisfy dealing with **org-table** or **table.el**, it just felt so out-of-place and clunky compare to the otherwise slick experiences that org-mode bring. So I basically gave up and have been linking spreadsheet files into my org files instead.

Obviously, this isn't ideal, as I need to view tables in separated libreoffice calc windows. And the notes is not viewable on GitLab or GitHub, etc.

So I decided to leverage org-babel to render spreadsheets inside my org notes.

For that, I created a bash script called emacs<sub>excel</sub><sub>to</sub><sub>png</sub> that utilize ssconvert, ImageMagick, and Firefox. The script converts the spreadsheet into HTML, then the HTML is rendered by Firefox and finally ImageMagick will be used to crop the rendered image. The script will then print an org-link so that the image is showed the result section.

With that, in an org file, we can do something as follows:

```elisp
#+begin_src bash :dir ./ :results raw :var ZOOM=1.3
export ZOOM; emacs_excel_to_png \
    "note_files/emacs_excel_to_png.xlsx" \
    "note_files/emacs_excel_to_png.png"
```

\#+END<sub>SRC</sub> The content of the emacs<sub>excel</sub><sub>to</sub><sub>png</sub> script can be found below:

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


<a id="2022"></a>

## 2022


### u/zupatol [🔗](https://www.reddit.com/r/emacs/comments/t3_xdw6ok/comment/t1_iodig8c)

**Votes** 29

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


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_txh85s/comment/t1_i3m1liu)

**Votes** 24

[removed]


### u/howardthegeek [🔗](https://www.reddit.com/r/emacs/comments/t3_xdw6ok/comment/t1_ioeh1ly)

**Votes** 21

I just learned that in eshell, $$ is replaced with the output from the last command.


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_x27yc9/comment/t1_imi3kzz)

**Votes** 20

Update from a couple of weeks ago: after some grinding, I've set the parsing of past comments from this thread to auto update on a weekly basis here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

I've also fixed the broken highlighting of some code snippets, and hopefully parsed all past threads. There's 200+ comments there (sorted by upvotes), so ctrl-f ing e.g. \\~magit\\~ may help you if you're looking for something specific


### u/TeMPOraL<sub>PL</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_txh85s/comment/t1_i3ov7vq)

**Votes** 20

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


### u/SamTheComputerSlayer [🔗](https://www.reddit.com/r/emacs/comments/t3_sijcap/comment/t1_hvbbnjq)

**Votes** 20

Just figured this out, maybe a bit of a hack&#x2026;

In flyspell, I was annoyed I had to use mouse-2 when I wanted to correct a word, and I didn't want to sacrifice a major-mode keybinding to do it from the keyboard. But flyspell actually creates an overlay for misspelled words and attaches a keymap to it, which you can do I just realized- very cool. So I just bound `flyspell-correct-at-point` to "<return>" in the `flyspell-mouse-map`, and now return corrects words when my cursor is on a misspelled word!

But the fact you can attach keymaps to overlays just seems so useful, will definitely use in the future.


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_vnals8/comment/t1_ie7p6ja)

**Votes** 17

I recently discovered `thing-at-point-looking-at`, which seems much easier to use on its own than to fully define a new kind of "thing."

For a while I've been wanting to conveniently identify a Jira ticket identifier at point so I can browse to it. Ticket IDs are basically a sequence of letters, a hyphen, and a sequence of digits. First I tried using `symbol-at-point`, but that can include extraneous neighboring characters, like `/` when the ticket ID is part of a URL. Eventually, while poring over the `thingatpt` source, I found `thing-at-point-looking-at`, which quickly led to:

```elisp
(defun browse-ticket-at-point ()
  (interactive)
  (if (thing-at-point-looking-at (rx (+ alpha) "-" (+ digit)))
      (browse-url (format "https://jirahost/browse/%s" (match-string 0)))
    (error "No ticket at point")))

```

Easy peasy!


### u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/t3_v2by7z/comment/t1_iauyzbl)

**Votes** 17

It's not worth a separate post, but after spending some pleasant yak-shaving time optimising my startup using use-package, I wrote a [post about it](https://blog.markhepburn.com/posts/understanding-use-package-optimisations/). There's a few posts around suggesting features of `use-package` to optimise startup, but none of them really explained how they tied back to `autoload`, `eval-after-load`, etc so I was trying to encourage people to dig out `macroexpand` and find out.


### u/AffectionateAd8985 [🔗](https://www.reddit.com/r/emacs/comments/t3_sd10q9/comment/t1_hu9xfed)

**Votes** 17

`(add-hook 'org-mode-hook (lambda () (org-next-visible-heading 1)))`

Move to first heading when open org files, with `org-use-speed-commands`, I can quick browse org file with only `n/p` keys.


### u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_xw4muy/comment/t1_ir96qmu)

**Votes** 16

I finally got around to writing a small README for my Emacs config, highlighting some homegrown parts that I really like. I reckon most of these things are pretty standard, but maybe some people here still find it useful:

<https://gitlab.com/slotThe/dotfiles/-/tree/master/emacs/.config/emacs>


### u/meain [🔗](https://www.reddit.com/r/emacs/comments/t3_wwdpju/comment/t1_ilotsc5)

**Votes** 16

I use the following snippet to change background color of compilation buffer to a light red if the compilation failed. I use compilation buffer to run tests on a second monitor and this is pretty useful.

```elisp
(defun meain/compilation-colorcode (_buffer string)
    "Change background color of compilation ~_BUFFER' to red on failure."
    (unless (string-prefix-p "finished" string) ; Having color for success was distracting
    (face-remap-add-relative 'default 'diff-hl-delete)))
(add-to-list 'compilation-finish-functions 'meain/compilation-colorcode)
```


### u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/t3_y7wrdn/comment/t1_isze25m)

**Votes** 15

not emacs per se, but jack rusher did a talk about programming 'ux / ergonomics / pragmatics' with a lot of fun ideas about coding, past (lisp machines, smalltalk &#x2026; ) or more recent clojure based tools

<https://www.youtube.com/watch?v=8Ab3ArE8W3s>

hope you enjoy it

warning: poop emoji


### u/pathemata [🔗](https://www.reddit.com/r/emacs/comments/t3_un4wf8/comment/t1_i86hwzi)

**Votes** 15

Something amazin that I have been using recently is `ripgrep-all` as the `consult-ripgrep` command to search in pdfs.

It is amazing with the `orderless` dispatchers to control the search filtering. I use `!` to exclude a string and `=` to match exactly.

Also amazing with `embark-collect` which allows collapsing features. Or within the collect buffer use `consult-line` to further filter. And even open the pdf.


### u/com4 [🔗](https://www.reddit.com/r/emacs/comments/t3_xq6rpa/comment/t1_iqb2fci)

**Votes** 14

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
               ~(python-mode "pyright-langserver" "-w" "--stdio")))

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


### u/thr33body [🔗](https://www.reddit.com/r/emacs/comments/t3_wqjare/comment/t1_ikqxn0r)

**Votes** 14

I don’t have any specific tip but I just want to throw it out there that if you are tired of using spacemacs or doom it was much easier to set up my own install than I thought. It only took me a couple of days of active work and now diagnosing problems is so much simpler. Not to say that you should not use either one but I wanted to learn more about emacs and I’ve been really happy with the results.


### u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/t3_y1y0kq/comment/t1_is1ygyw)

**Votes** 13

I've been using `mu4e` for some years now, and I really love it. However, I'm also a Gmail user, don't love it, but consider myself stuck with it (given budget constraints and it being my email for several years already). This makes me always weary of "the next Google shenanigan" which might break my workflow. One of the things I have learned to cherish about using `mu4e` is its integration with Org, with it's `org-capture` support, which enables me not to confuse my Inbox with my todo list, thus keeping my Inbox clean. So I came up with a preventive function, leveraging `org-protocol` to be able to capture a Gmail message from a bookmarklet on the browser. Not particularly pretty code, but functional.

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
~org-protocol-protocol-alist'."
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

        (let ((props ~(:type "gmail"
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
              ;; Avoid call to ~org-store-link', see 'org-protocol-capture'.
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


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_sd10q9/comment/t1_hubjy3j)

**Votes** 13

I was reading through the org manual, and learnt about two variables `org-agenda-category-icon-alist` and `org-agenda-prefix-format`, the first allows you to set icons for categories (`CATEGORY` property), icons can be images or symbols, this is the code I came up with and the agenda already looks more colorful and clear

```elisp
(setq org-agenda-category-icon-alist nil)
(setq agenda-categories-alist
'(("WORK" "💼") ("SOFTWARE" "💻") ("SETUP" "🐧") ("EMAIL" "✉️")
("HOME" "🏠") ("WOOD" "🪵") ("FAMILY" "👪") ("REPORTS" "📚")
("INCOME" "💰")))
(dolist (icon agenda-categories-alist) (add-to-list 'org-agenda-category-icon-alist
~(,(car icon) ,(cdr icon) nil nil :width (16.) :ascent center)))
(defun format-agenda-prefix () (interactive)
(setcar org-agenda-prefix-format '(agenda . "  %-2i  %?-12t% s")))
(add-hook 'org-agenda-mode-hook 'format-agenda-prefix)

```

Of course for the emojis to show up correctly I use this:

```elisp
(set-fontset-font "fontset-default" 'symbol (font-spec :family "Noto Color Emoji"))

```

I am sure a lot of you know about these, please share your customization


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_wf0t0d/comment/t1_iirl0ea)

**Votes** 12

Org mode - insert a complete set of export options:

`org-export-insert-default-template`

This inserts all export keywords with default values at beginning of line.

This command is not documented in `info emacs` (v27.1).


### u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/t3_w3gx6o/comment/t1_ih6ievs)

**Votes** 11

Emacs has better long-lines support now??

I just noticed while looking at the latest additions in the NEWS:

>\\\*\\\* Emacs is now capable of editing files with arbitrarily long lines. The display of long lines has been optimized, and Emacs no longer chokes when a buffer on display contains long lines. If you still experience slowdowns while editing files with long lines, this is either due to font locking, which you can turn off with M-x font-lock-mode or C-u C-x x f, or to the current major mode or one of the enabled minor modes, in which case you should open the the file with M-x find-file-literally instead of C-x C-f. The variable 'long-line-threshold' controls whether and when these display optimizations are used.

That sounds like great news! Does anyone know what went into it?

(edit to add: this was added some time this week. I rebuild from master weekly, and check out the NEWS diff each time)


### u/andyjda [🔗](https://www.reddit.com/r/emacs/comments/t3_yqciht/comment/t1_iw00xhx)

**Votes** 10

I started using `god-mode`, but I found it hard to get used to it at first: there was no easy way to check what command would be triggered by what key-sequence.

I wrote up a `god-mode`\&#x00ad;specific `describe-key`, which translates `god-mode` key-sequences into commands and shows their usual description. I think it's a great way to get familiar with how the package handles keys, and it allows users to invoke `describe-key` without leaving god-mode (previously, most keys would just show information about the generic `god-mode-self-insert-command`)

I also reached out to the package's maintainers, and this feature (after some tweaking) [just got added to the master branch](https://github.com/emacsorphanage/god-mode). It was a great way to get familiar with `god-mode` code and its behavior, and I'm happy to have made my first contribution to an Emacs package.


### u/kickingvegas1 [🔗](https://www.reddit.com/r/emacs/comments/t3_x7zfs2/comment/t1_innk62a)

**Votes** 10

TIL when working with an Org table that `S-RET` will fill the current cell value with the value above it. <https://lists.gnu.org/archive/html/emacs-orgmode/2010-03/msg00462.html>


### u/ainstr [🔗](https://www.reddit.com/r/emacs/comments/t3_vcpk6u/comment/t1_ichiccu)

**Votes** 10

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
  "Wrapper around ~spotify--open-playlist~, to check if spotify is running."
  (interactive)
  (pcase
      (shell-command "pgrep spotify")
    (1 (message "Spotify not running."))
    (0 (spotify--open-playlist))))
```


### u/char1zard4 [🔗](https://www.reddit.com/r/emacs/comments/t3_v2by7z/comment/t1_iarzi1s)

**Votes** 10

This week I learned that:

-   You can redefine all yes/no prompts to y/n:

`(defalias ‘yes-or-no-p ‘y-or-n-p)`

-   `C-c C-c` in LaTeX-mode buffers will allow you to compile/view output (I’ve used LaTeX-preview-pane for the last couple of years)

-   Tab-stops in yas-snippet are very handy for filling out multiple parts of a template, didn’t even know these existed:

<https://joaotavora.github.io/yasnippet/snippet-development.html#org41a4ac7>


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_us7zae/comment/t1_i92mn8w)

**Votes** 10

[deleted]


### u/jimm [🔗](https://www.reddit.com/r/emacs/comments/t3_tfcmcx/comment/t1_i0vtxte)

**Votes** 10

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


### u/shitterwithaclitter [🔗](https://www.reddit.com/r/emacs/comments/t3_s7lac1/comment/t1_htnz373)

**Votes** 10

I recently had the idea to start emacs in org-mode but have a src block at the top so I can still write elisp snippets easily. Here's the code for anyone interested:

```elisp
;; start in org-mode with a source block for lisp evaluation
(setq initial-major-mode #'org-mode
      initial-scratch-message "#+begin_src emacs-lisp\n;; This block is for text that is not saved, and for Lisp evaluation.\n;; To create a file, visit it with \\[find-file] and enter text in its buffer.\n\n#+end_src\n\n")
```


### u/gusbrs [🔗](https://www.reddit.com/r/emacs/comments/t3_ywnt6p/comment/t1_ix0a6ui)

**Votes** 9

Some weeks ago, u/paretoOptimalDev made an interesting post about using more `next-buffer` and `previous-buffer` instead of `switch-to-buffer` (<https://redd.it/ybqp3m>). I liked the post and had captured it for later and only now could process it properly.

The reasoning for using `next-buffer` and `previous-buffer` is good, but pretty much everyone complained about their default bindings.

It is worth noting that Emacs 28 has included `repeat-mode` and Emacs 29 has added `next-buffer` and `previous-buffer` to the repeat maps. So, if you're in Emacs 29, just enabling `repeat-mode` gets you a better behavior for this. You can start with `C-x <right>` and, after that, just the arrow keys get you to the next or previous buffer.

If you're still on Emacs 28, you can use:

```elisp
(defvar buffer-navigation-repeat-map
  (let ((map (make-sparse-keymap)))
    (define-key map (kbd "<right>") 'next-buffer)
    (define-key map (kbd "<left>") 'previous-buffer)
    map)
  "Keymap to repeat ~next-buffer' and ~previous-buffer'.  Used in ~repeat-mode'.")
(put 'next-buffer 'repeat-map 'buffer-navigation-repeat-map)
(put 'previous-buffer 'repeat-map 'buffer-navigation-repeat-map)
```

\\\*\* u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_xdw6ok/comment/t1_iodmtzu) \\\*Votes\* 9

I find it pretty useful (for debugging etc) to override the default projectile mode line indicator and show the projectile project type of the buffer instead, which can be done pretty easily if you're a use-package user with <https://elpa.gnu.org/packages/delight.html>:

```elisp
(use-package projectile
  :delight '(:eval (format " P[%s]" (projectile-project-type)))
  :config
  (setq foo "bar"))
```


### u/hairlesscaveman [🔗](https://www.reddit.com/r/emacs/comments/t3_wqjare/comment/t1_ikwhvfs)

**Votes** 9

Question: I generally work with 3 vertical panes, with my preferred layout as left for code, middle for related test file, and right for test output or magit. However, keeping this layout is tricky; sometimes magit will open in the first pane, or the current pane when I'm focused in the middle, and deadgrep will open just anywhere… well, it's quite hectic and feels random.

Is there any way I can get files to open in panes 1 or 2, and always have things like magit/test-output/deadgrep/etc on pane 3? I've tried "shackle" but I've had no success with it; everything seems to open in a horizontal pane at the bottom of my screen regardless of config.

Any suggestions would be appreciated!


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_wqjare/comment/t1_ikrx30z)

**Votes** 9

I've parsed and prettified some of the comments (I think I'm missing some, but hopefully should be fixed soonish) from past weekly tips and tricks thread here: [https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md](https://github.com/LaurenceWarne/reddit-emacs-tips-n-tricks/blob/master/out.md)

If you fancy procrastinating for a bit today&#x2026;


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_s21457/comment/t1_hsgj7a6)

**Votes** 9

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

This doesn't make sense to me, as you can't see anything at all. Probably handy, when you write in really long lines, and you wish to have some buffer for adding more text without triggering scrolling, but I never needed that. So I wrote such predicate:

```elisp
(defun truncated-lines-p ()
  "Non-nil if any line is longer than ~window-width' + ~window-hscroll'.

Returns t if any line exceeds the right border of the window.
Used for stopping scroll from going beyond the longest line.
Based on ~so-long-detected-long-line-p'."
  (save-excursion
    (goto-char (point-min))
    (let* ((window-width
            ;; this computes a more accurate width rather than ~window-width', and respects
            ;; ~text-scale-mode' font width.
            (/ (window-body-width nil t) (window-font-width)))
           (hscroll-offset
            ;; ~window-hscroll' returns columns that are not affected by
            ;; ~text-scale-mode'.  Because of that, we have to recompute the correct
            ;; ~window-hscroll' by multiplying it with a non-scaled value and
            ;; dividing it with a scaled width value, rounding it to the upper
            ;; boundary.  Since there's no way to get unscaled value, we have to get
            ;; a width of a face that is not scaled by ~text-scale-mode', such as
            ;; ~window-divider' face.
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

\\\*\* u/attento<sub>redaz</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_wqjare/comment/t1_iku77h0) \\\*Votes\* 9

Using [zotra](https://github.com/mpedramfar/zotra), [citar](https://github.com/emacs-citar/citar) and some parts of the Org-cite ecosystem I hacked together a highly experimental but pretty comfortable environment for working with "org-biblatex bibliographies" which are basically like [org-bibtex](http://gewhere.github.io/org-bibtex) but with biblatex entries represented as headings with suitable properties instead of bibtex. I have a function which retrieves a biblatex entry corresponding to an url using zotra and adds a corresponding Org heading with the biblatex fields as properties, and the entry becomes available in Citar as soon as I save the document. Citing these entries then works anywhere, even in the same document with a suitable `#+bibliography: my-org-biblatex-file.org` declaration. Exporting the citations also works with the CSL exporter, no conversion is necessary to a proper biblatex bibliography file (but can be easily done if one needs biblatex-based export). Since the bibliography is an Org document, tagging, agenda commands, column view etc. can all be used with the bibliography entries. In a way it's frightening how much can be achieved building on already existing stuff and with a few lines of Emacs Lisp.


### u/luiggi<sub>oasis</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_zred55/comment/t1_j14grej)

**Votes** 8

What's the deal with all these completion framework I keep hearing about? Vertical elm ivy company and whatnot.

I think I have company in my init.el but I'm not even sure I'm actually using it (maybe I am and I'm just unaware). But why are they everywhere? I see them mentioned in at least every any two emacs threads.


### u/pedzsanReddit [🔗](https://www.reddit.com/r/emacs/comments/t3_ydsjfy/comment/t1_itw7yp2)

**Votes** 8

I spent the past week cleaning up my Emacs init files and I bumped into this little gem. I call it "ZSH man page search mode" because it was the place that I first needed it.

I do `M-x man zshall(1)` fairly frequently (and don't forget to `widen` so you can see all of the pages). Then I would start searching for what I was looking for. The man page is nicely structured so if I wanted to find "foo", search for "foo" had too many hits. What I wanted to find was the place where "foo" is described.

This little search does the trick. It is probably useful for other man pages and perhaps even other places but for now&#x2026; I call it "ZSH man page search"

Enjoy!

```elisplisp
  (defun zsh-manpage-search-regexp (string &optional lax)
"Returns a string to search for entries in the zshall man page"
(format "\n[A-Z ]*\n \\{7\\}%s%s" string (if lax "" "\\_>")))

  (isearch-define-mode-toggle zsh-manpage "z" zsh-manpage-search-regexp "\
  Searching zshall man page for where a concept is described")
```

\\\*\* u/trae [🔗](https://www.reddit.com/r/emacs/comments/t3_xdw6ok/comment/t1_iof00tz) \\\*Votes\* 8

Hey folks,

Is there a package/piece of code out there to:

1.  pop up a frame
2.  execute arbitrary code
3.  close frame on completion

Kind of like [emacs-everywhere](https://github.com/tecosaur/emacs-everywhere/) but for arbitrary code.


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_x7zfs2/comment/t1_inqralq)

**Votes** 8

I use follow-mode (built-in to #emacs) to split reading buffers across modern wide screens to use all the real estate. The mode keeps the panes in sync with eachother. ![img](http://images.toryanderson.com/follow-mode.gif)


### u/HM0880 [🔗](https://www.reddit.com/r/emacs/comments/t3_wwdpju/comment/t1_illuprk)

**Votes** 8

In Org Mode, what is the reason to use `~` for in-line code vs. `=` for monospace text? I use `=` for both code and monospace since (afaict) Org renders both code and monospace the same way in LaTeX PDF and HTML output, and `=` does not require using shift (unlike `~`).


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_w3gx6o/comment/t1_igyt3ff)

**Votes** 8

I sometimes want to pick a random choice from a long org-mode checkbox list from among those items not already checked. I recently whipped up a little helper function for that:

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


### u/isamert [🔗](https://www.reddit.com/r/emacs/comments/t3_vskthv/comment/t1_if1ua6o)

**Votes** 8

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

\\\*\* u/khourhin [🔗](https://www.reddit.com/r/emacs/comments/t3_uxcm6i/comment/t1_i9x2a0i) \\\*Votes\* 8

Just discovered that you can do pull request from magit forge. Got some troubles with the origin / myfork setup and was helped by this issue: <https://github.com/magit/forge/issues/278> . And this improved as well how I deal with the naming of my remotes.

Pure awesomeness, thanks a lot for Magit/ Magit forge !

\\\*\* u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_uxcm6i/comment/t1_i9xcaoa) \\\*Votes\* 8

[arxiv-citation](https://github.com/slotthe/arxiv-citation) from my [last post](https://old.reddit.com/r/emacs/comments/ufvg93/my_phd_research_workflowemacs_inside/) is now on MELPA! Give it a spin if you're in the habit of downloading or citing papers from the arXiv (if you're doing maths, these citations will even become actual journal citations, if possible)!

On that note, I've written a little bit about [calling Emacs from XMonad](https://tony-zorman.com/posts/2022-05-25-calling-emacs-from-xmonad.html). The post itself isn't really Emacs related and so I don't think it warrants a crosspost in this subreddit, but I figured perhaps there was the odd XMonad user here who may find it helpful.


### u/diamondnbond [🔗](https://www.reddit.com/r/emacs/comments/t3_us7zae/comment/t1_i928gaj)

**Votes** 8

[I Recently discovered engine-mode.](https://github.com/DiamondBond/emacs/blob/master/config.org#initialize-engine-mode)

\\\*\* u/mmarshall540 [🔗](https://www.reddit.com/r/emacs/comments/t3_un4wf8/comment/t1_i88sp07) \\\*Votes\* 8

Here's a little solution to a minor complaint about \`isearch\` that I think is fairly common.

TLDR: See [this old StackExchange question](https://emacs.stackexchange.com/questions/32373/go-to-start-of-search-string-after-ret)

Often, I'll want to go to a precise location in a buffer to correct a typo. If that location is behind point (as it usually is if I notice the typo shortly after entering it), then \`isearch-backward\` works great. You press "C-r" and type some characters starting with your target until the location becomes the current result. As soon as the cursor jumps to your target location, you can use an editing command like "C-t", which exits isearch automatically for you. Or you can press "C-m" to exit isearch-mode and then start inserting text at that location. Wonderful!

But it's a little less perfect when your target is **after** point. This is because with \`isearch-forward\`, point lands at the end of your search result, instead of the beginning as it does with \`isearch-backward\`.

You could type your search starting with some arbitrary number of characters in front of the target. But then you have to decide in advance how many characters to use, and if you don't use enough, there might be too many results. And then if you have to keep typing past your target character to sufficiently narrow the results, point won't land where you want it.

Another strategy would be to do the same thing as when searching backwards, start with the target character and just type characters until your target location is selected. But now point is at the other end of the result, instead of where you want it. "M-b" would exit and move point to the beginning of the word, but that assumes there are no word boundaries in your search result, and besides, your actual target won't always be the beginning of a word. Often it will be somewhere in the middle of a word.

Or you could use C-r to move point to the beginning of the result. But then you still have to press C-m to exit the search and start inserting text at that location. And that's 2 key-presses just to finish your search, not to mention all the key-presses you did to start.

So we can fix this.

```elisp
(defun my/isearch-bor-exit ()
  "Ensure point is at beginning of isearch result, then exit."
  (interactive)
  (when (< isearch-other-end (point))
    (goto-char isearch-other-end))
  (call-interactively 'isearch-exit))
    
(define-key isearch-mode-map ?\M-m 'my/isearch-bor-exit)

```

So now if I'm using isearch to get to a precise location (as opposed to just searching for a word), I can type beginning with the character at the target location, stop typing after it becomes part of the result, and press "M-m" to exit the search and make my correction. No more having to stop and think about whether I'm going backwards or forwards and whether I'll have to move point back before exiting isearch.

A minor problem with a fairly simple solution, but it made me happy to stop and solve it.

I think I'll be using isearch now for a lot of things that I used to use Avy for. But Avy is still great for a bunch of reasons. Not the least of which is the \`avy-isearch\` command which comes in handy when there are a lot of isearch results that would require too much typing to narrow down. (And it always puts point at the beginning of the result, never at the end).


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_txh85s/comment/t1_i3mghuf)

**Votes** 8

I wanted to be able to expand yasnippets within other yasnippets (so here tab would jump to the next position instead of trying to expand snippet), surprisingly all I had to do was:

`:bind ("C-<tab>" . yas-expand)`

So C-<tab> expands a snippet within a snippet, and everything just worked as I'd hoped. Once I'm done with the nested expansion <TAB> just moves on to the outer one. \\\*shrug\\\*


### u/konrad1977 [🔗](https://www.reddit.com/r/emacs/comments/t3_z2jgt5/comment/t1_ixpdxvf)

**Votes** 8

I found this one-liner yesterday, added it it to the `:config` section of ****Evil****.

`(define-key evil-visual-state-map (kbd "u") 'undo)` Now I can undo in selected region. Like magic.

\\\*\* u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_x27yc9/comment/t1_imjs86m) \\\*Votes\* 8

I don't think this is worth it as a top-level post, since it's more geared towards XMonad users who also happen to use Emacs, but at least a few folks here might appreciate how easy it is to [rapidly capture ideas](https://tony-zorman.com/posts/orgmode-prompt/2022-08-27-xmonad-and-org-mode.html) by using XMonad's org-mode integration!


### u/agumonkey [🔗](https://www.reddit.com/r/emacs/comments/t3_w3gx6o/comment/t1_ih3s9fl)

**Votes** 8

you can have an org-mode file in source block in an org file

\\\*\* u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_z8ltei/comment/t1_izxi6ie) \\\*Votes\* 8

A great functionality for calculating some schedule events is \`org-evaluate-time-range\` (\`C-c C-y\`). When I need to propose an event of a particular length, I use this to get my length right between two dates.


### u/meedstrom [🔗](https://www.reddit.com/r/emacs/comments/t3_ydsjfy/comment/t1_itwo67r)

**Votes** 8

I had the [defrepeater](https://github.com/alphapapa/defrepeater.el) package for a while, but didn't realize you could use it this elegantly!

```elisp
(global-set-key [remap transpose-lines] (defrepeater #'transpose-lines))

```

Something similar is actually in the readme, but I guess I glossed over it back then.


### u/khourhin [🔗](https://www.reddit.com/r/emacs/comments/t3_x7zfs2/comment/t1_inp54pm)

**Votes** 8

Just discovered 'desktop-environment-mode', really useful, particularly if you are using EXWM and want to get back functional standard keys. [https://github.com/DamienCassou/desktop-environment](https://github.com/DamienCassou/desktop-environment)

Thanks Damien !

\\\*\* u/ScottWC2 [🔗](https://www.reddit.com/r/emacs/comments/t3_vi01zb/comment/t1_idb3m43) \\\*Votes\* 8

Anyone know why r/planetemacs went private? Private subs don't work with rss and I just noticed it changed around April 15th.

\\\*\* u/kickingvegas1 [🔗](https://www.reddit.com/r/emacs/comments/t3_x27yc9/comment/t1_imqmfsi) \\\*Votes\* 8

\\\*Embedded calc mode\* to insert a quick and dirty calculation in a buffer:

```elisp
To enter Embedded mode, position the Emacs point (cursor) on a formula
in any buffer and press ‘C-x * e’ (‘calc-embedded’).  Note that ‘C-x *
e’ is not to be used in the Calc stack buffer like most Calc commands,
but rather in regular editing buffers that are visiting your own files.
```

\\\*\* u/Sudo<sub>Brew</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_y1y0kq/comment/t1_isazxjg) \\\*Votes\* 8

I've been unhappy with the behavior of `comment-line` moving the point down a line, and sometimes inexplicably commenting an inactive region. I'm also unhappy with `comment-dwim`'s single-line behavior of adding a comment at the end of the line. What I want is a function to comment/uncomment the region if its active, and comment/uncomment the line at point otherwise, so I made this quick little function:

```elisp
(defun my/comment-dwim ()
  "Comment region if active, else comment line.

This avoids the excess region commenting of ~comment-line' while also avoiding the weird single-line
behavior of ~comment-dwim'."
  (interactive)
  (save-excursion
    (if (use-region-p)
        (call-interactively #'comment-or-uncomment-region)
      (call-interactively #'comment-line))))
```


### u/nicholas<sub>hubbard</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_sd10q9/comment/t1_hucv1fe)

**Votes** 8

This function clears a comint-mode buffer in a dwim fashion by preserving the current input line and working even if the cursor is above the input line. I have been using it with shell-mode and ielm bound to `C-l` and it works nicely.

```elisp
(defun my/comint-clear ()
  (interactive)
  (let ((orig-ln (line-number-at-pos))
        (col (current-column))
        (cmd (progn (end-of-buffer)
                    (move-end-of-line nil)
                    (set-mark (point))
                    (move-beginning-of-line nil)
                    (buffer-substring (region-beginning) (region-end))))
        (after-ln (line-number-at-pos)))
    (delete-region (region-beginning) (region-end))
    (comint-clear-buffer)
    (insert cmd)
    (if (= orig-ln after-ln)
        (move-to-column col t)
      (move-beginning-of-line nil))))
```


<a id="2021"></a>

## 2021


### u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/t3_o68i0v/comment/t1_h2rdkkz)

**Votes** 37

Do you think it might be good to make this a little less frequently refreshed? There are usually some great tips that get lost to Reddit’s ephemerality pretty quickly.

I think monthly would be better, given the modest size of this subreddit.


### u/Gangsir [🔗](https://www.reddit.com/r/emacs/comments/t3_pxqvtm/comment/t1_hepqmq1)

**Votes** 22

back-to-indentation. Before I found this function I would always do some awkward triple key combo like C-a M-f M-b.

It's just bound to M-m. Jumps you right to the first non-white space character on the line. What's even spicier is that it works in reverse too - if you're at the front of the line it jumps you forward, if you're at the end or middle it jumps backward.

It still works even on lines that aren't indented, same as C-a in that case.

So useful, especially for resetting point during macros that need to start at the first char on the line.


### u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/t3_p6mwx2/comment/t1_h9e6uqq)

**Votes** 18

Here's a popular Emacs config I just rediscovered. Some cool stuff here. <https://github.com/angrybacon/dotemacs>


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_o68i0v/comment/t1_h2rizey)

**Votes** 17

I have two org mode link tips:

1.  `(setq org-return-follows-link t)` lets you press RET to follow a link. Don't worry, the traditional behavior of RET, namely inserting a newline, is still easy: `C-q C-j`.

2.  I often want to see what the destination of a link is. I used to use one of these methods:
    -   mouse hover,
    -   running and canceling `org-insert-link` (`C-c C-l`, read the

```elisp
destination, ~C-g~),
```

-   `org-toggle-link-display`, which toggles between the neat formatting of

```elisp
links and the raw source and is pretty ugly.

```

But a better option is to use `display-local-help` (`C-h .`), which will show the tooltip in the echo area. And, you can even have the tooltip at point echoed automatically with `(setq help-at-pt-display-when-idle t)`. There is a delay controlled by the variable `help-at-pt-timer-delay` which I like to set to 0. Beware that just using `(setq help-at-pt-timer-delay 0)` has no effect, you need to use `customize-set-variable` or manually cancel the timer and set a new one (see below).

Now, these `help-at-pt` variables aren't specifically for org links, they control the behavior of all tooltips, and I quickly realized I really only wanted to change the behavior in org mode buffers. You can do that as follows:

```elisp
(defun echo-area-tooltips ()
  "Show tooltips in the echo area automatically for current buffer."
  (setq-local help-at-pt-display-when-idle t
              help-at-pt-timer-delay 0)
  (help-at-pt-cancel-timer)
  (help-at-pt-set-timer))

(add-hook 'org-mode-hook #'echo-area-tooltips)
```


### u/TeMPOraL<sub>PL</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_rbmfwk/comment/t1_hnx4z28)

**Votes** 16

If you're like me, and your day ends way past midnight, handling those last few tasks in your Org Mode agenda gets tricky. Fortunately, it turns out Org Mode has what I call "25th hour mode".

```elisp
;; consider the current day to end at 3AM
(setq org-extend-today-until 3) 
    
;; make timestamp processing functions aware of this
(setq org-use-effective-time t) 

```

Combined, this allows to extend the day past midnight, with things like agenda views, scheduling commands, repeaters, etc. thinking the current time is 23:59 up until the `org-extend-today-until` limit. With this enabled, if I have a task that has a repeater of and complete it at 01:00, I no longer have to then manually reschedule the task back one day.


### u/rucci99 [🔗](https://www.reddit.com/r/emacs/comments/t3_r69w7i/comment/t1_hmryv5o)

**Votes** 16

I just found out that Magit can backup changes of uncommitted files automatically. Here's the link to online manual: [Magit Wip Modes](https://magit.vc/manual/magit/Wip-Modes.html#Wip-Modes).


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_mujxm7/comment/t1_gv8jxz5)

**Votes** 16

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


### u/globalcandyamnesia [🔗](https://www.reddit.com/r/emacs/comments/t3_ooldn6/comment/t1_h67qge6)

**Votes** 15

I'm trying to feminize my voice and org mode has been invaluable.

```elisp
(org-babel-do-load-languages 'org-babel-load-lanuages
  '((shell . t)))
    
(setq org-capture-templates
  ~(("v" "Voice" entry
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

This requires 'SoX' for linux. You can go into the record src block and press \\~C-c C-c\\~ to start recording and \\~C-g\\~ to end. To play back the recording, press \\~C-c C-c\\~ within the play src block. I imagine this might be useful beyond the trans community for basic voice journaling.


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_ofen99/comment/t1_h4dxjbz)

**Votes** 15

If you want to search and replace but with preview for the matches, don't use `query-replace-regexp` directly. Instead start by searching for your regexp in `isearch-forward-regexp`, which highlights the matches interactively, and once you have the correct regexp, run `isearch-query-replace` (bound to `M-%` in `isearch-mode-map`).

Note that there is also an `isearch-query-replace-regexp` command but you don't need it: `isearch-query-replace` will automatically detect if your isearch session was for regexps. The docstring for `isearch-query-replace` doesn't seem to mention this nice feature.


### u/el<sub>tuxo</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_rbmfwk/comment/t1_hnp5rhn)

**Votes** 14

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


### u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/t3_q76kok/comment/t1_hgk3wik)

**Votes** 14

This is pretty neat: scrolling up/down one line at a time while keeping the position of point:

`(setq scroll-preserve-screen-position 1)` `(global-set-key (kbd "M-n") (kbd "C-u 1 C-v"))` `(global-set-key (kbd "M-p") (kbd "C-u 1 M-v"))`

From: <http://pragmaticemacs.com/emacs/scrolling-and-moving-by-line/>


### u/Tatrics [🔗](https://www.reddit.com/r/emacs/comments/t3_n9q662/comment/t1_gxpeh9v)

**Votes** 14

I'm slowly working on an alternative shell: [https://github.com/TatriX/tshell](https://github.com/TatriX/tshell)

Instead of using repl-like interface, all the commands go to one buffer (and file if you want) and output goes to another buffer. Like if you put your elisp code in \\\*scratch\\\* buffer and then evaluate it with \\~C-x C-e\\~.

It's in a very early stage, but it already allows me to solve most tasks I usually do with more traditional shells.

Let me know what is your first impression, what can be improved and what do you think in general!


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_mg98ki/comment/t1_gstteeo)

**Votes** 14

I just discovered the [selected](https://github.com/Kungsgeten/selected.el) package, which is brilliant. It creates a keymap that becomes active any time you have an active region. I have bindings for next-line, previous-line, rectangle-mark-mode, end-of-line, upcase-dwim, exchange-point-and-mark, etc. It makes editing and acting on the active region super easy. Sort of like god-mode or Vim's visual mode.


### u/vatai [🔗](https://www.reddit.com/r/emacs/comments/t3_ojzv53/comment/t1_h5584no)

**Votes** 13

The emacs lisp tutorial is the real tutorial for emacs ;)


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_lapujj/comment/t1_glr8pkr)

**Votes** 13

You can use EWW to bypass pay-walls on news sites, and other Javascript-enabled nastiness. Plus, eww can copy from what it sees into equivalent orgmode syntax, and it's also compatible with SPRAY for speed-reading. In otherwords, EWW is great for when you just need to READ the internet.


### u/emacs-noob [🔗](https://www.reddit.com/r/emacs/comments/t3_kvmmq3/comment/t1_gj1kn9i)

**Votes** 13

I use Emacs for React development and it's usually great (rjsx-mode). We recently introduced styled components into our app and while they're very handy, not having proper css support inside rjsx-mode was pretty annoying. I was looking for solutions, maybe extending rjsx-mode, but I wasn't up to that task. I then realized the built-in emacs commands and buffers themselves could solve my problem! What I want is for css inside a styled component, which always looks something like this:

```elisp
const myDiv = styled.div~ // notice the backtick
    Some css...
 ~ // ending backtick

```

to **actually** use scss-mode when editing, and then return to rjsx-mode when finished. The elisp is very simple and leads to a trivial workflow:

```elisp
;; The following 2 functions allow editing styled components with all scss mode features.
(defun edit-styled-component ()
  (interactive)
  (progn
    (save-excursion
      (let ((start (search-backward "~"))
            (end (search-forward "~" nil nil 2))) ; second occurrence, since first is ~start'
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

So now when I edit a styled component I just hit ****, m s****, which narrows the region to whatever is enclosed by backticks (i.e. all the css) and actually treats it as a bona fide css buffer, with all my snippets, completion, etc. Then when I'm done I just got ****, m s**** again to widen back to the original (rjsx) buffer!


### u/b3n [🔗](https://www.reddit.com/r/emacs/comments/t3_lvw44q/comment/t1_gpeb8n3)

**Votes** 12

Here's a nice eshell command:

```elisp
(defun eshell/history ()
  (interactive)
  (insert
   (completing-read "History: " (delete-dups (ring-elements eshell-history-ring)))))

```

It lets you use your normal completion framework to select an item from history. Suddenly fzf-like history!


### u/yogsototh [🔗](https://www.reddit.com/r/emacs/comments/t3_qgrpte/comment/t1_hi8crmc)

**Votes** 11

I just made this nice combination of emacs packages and personal theme to achieve the cool effect of iAWriter

See here: <https://her.esy.fun/posts/0021-ia-writer-clone-within-doom-emacs/index.html>


### u/PotentiallyAlice [🔗](https://www.reddit.com/r/emacs/comments/t3_n9q662/comment/t1_gxx6frj)

**Votes** 11

I thought it might be a fun project to make a package to expose org-capture templates as endpoints, so I can add reminders to my TODO list via any device on the network. Turns out, it was easy enough that a package would be kinda pointless:

```elisp
(defservlet* capture/:keys/:contents text/plain () (org-capture-string contents keys))

```

Now I can hit "localhost:8080/capture/t/test reminder" and it'll put a "\* TODO test reminder" line into my todo.org. Neat!


### u/jumpUpHigh [🔗](https://www.reddit.com/r/emacs/comments/t3_kvmmq3/comment/t1_gj33uht)

**Votes** 11

AucTeX

When you compile your TeX file and there are errors, the message asks you to see error messages using

```elisp
C-c ~

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


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_r69w7i/comment/t1_hmst3ih)

**Votes** 10

macros in emacs are like a secret, forgotten art, but I use them with regexp search, orgmode commands to tweak repeating events (or any number of other uses). Learn macros; they gave emacs its name! One usage here: <https://orys.us/ug>


### u/SamTheComputerSlayer [🔗](https://www.reddit.com/r/emacs/comments/t3_qbvyza/comment/t1_hhinrm4)

**Votes** 10

I use a lot of toggles in my config. I used to do it ad-hoc every time, but the pattern ends up the same so I made this macro:

```elisp
(defun deftoggle-var-doc (name)
  (concat "Non-nil if " name " is enabled.\n\n"
      "See " name
      " command for a description of this toggle."))
(defun deftoggle-fun-doc (name doc)
  (concat "Toggle " name " on or off.\n\n" doc))
(defmacro deftoggle (name doc enabler disabler)
  ~(progn
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


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_q76kok/comment/t1_hghtyfo)

**Votes** 10

before you load evil `(setq evil-want-minibuffer t)` to use evil-mode in the minibuffer.


### u/github-alphapapa [🔗](https://www.reddit.com/r/emacs/comments/t3_q2g1gq/comment/t1_hfldw8n)

**Votes** 10

One of the most useful bindings for me:

```elisp
(use-package avy
  :bind* (("C-j" . avy-goto-char-timer)))
```


### u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/t3_pxqvtm/comment/t1_hf1gzs2)

**Votes** 10

Read [SICP](https://mitpress.mit.edu/sites/default/files/sicp/index.html). Preferably in Info, installable through MELPA or: [https://github.com/webframp/sicp-info](https://github.com/webframp/sicp-info)


### u/Stefan-Kangas [🔗](https://www.reddit.com/r/emacs/comments/t3_pxqvtm/comment/t1_hexdfiq)

**Votes** 10

Replace the binding for `count-words-region` with `count-words`. The latter has better semantics: it only shows words in region if the region is active.

`(global-set-key (kbd "M-=") #'count-words)`


### u/dmartincy [🔗](https://www.reddit.com/r/emacs/comments/t3_polxft/comment/t1_hcxub77)

**Votes** 10

If you write Lisp, there's a couple of old Emacs commands to help you write code while keeping parenthesis balanced: `M-(` (`insert-parenthesis`), and `M-)` (`move-past-close-and-reindent`). They used to be documented in old Emacs manuals, but presumably their description was removed to make room for other content.

With a prefix argument, `M-(` wraps in parenthesis that number of sexps. For example with point represented as "\*":

\\\*foo -> C-u 1 M-( -> (foo)

There's more information in EmacsWiki: <https://www.emacswiki.org/emacs/InsertPair>


### u/PriorOutcome [🔗](https://www.reddit.com/r/emacs/comments/t3_ojzv53/comment/t1_h55vkl6)

**Votes** 10

I often find myself wanting to be able to switch between `master` and a feature branch in magit quickly:

```elisp
(defun lw-magit-checkout-last (&optional start-point)
    (interactive)
    (magit-branch-checkout "-" start-point))
(transient-append-suffix 'magit-branch "w"
  '("-" "last branch" lw-magit-checkout-last))

```

So that `C-x g b -` switches to the last branch I was on, similar to `cd -`.


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_o0zvb5/comment/t1_h1znz1s)

**Votes** 10

I keep forgetting how useful the `pcase` macro is. Recently I wrote a command that expected to find a single jar file in a certain directory and operate on it. I started with a more "traditional" implementation:

```elisp
(let ((jars (directory-files some-dir t (rx ".jar" eos))))
  (if (= 1 (length jars))
      (do-something-with (car jars))
    (error "Didn't find exactly one jar file")))

```

Then I remembered `pcase`:

```elisp
(pcase (directory-files some-dir t (rx ".jar" eos))
  (~(,jar) (do-something-with jar))
  (_ (error "Didn't find exactly one jar file")))

```

Much more readable!


### u/11fdriver [🔗](https://www.reddit.com/r/emacs/comments/t3_mpwapo/comment/t1_gufsfeu)

**Votes** 10

Sometimes I'm working on programs with functions a few pages long, and `follow-mode` means that I can open two windows of the same buffer side-by-side and have the text flow like a book between them. I can double or even triple the amount of lines I can view at one time.

This has largely superseded what I might have used those code-overview map things for, which is difficult anyway, since I like to use Emacs from the terminal.

It will keep the text aligned as you move through the file, and pairs well with binding `<mouse-5>` and `<mouse-4>` to the `scroll-up/down-line` commands in `xterm-mouse-mode`.

If I'm studying/notetaking, I often end up with a few Emacs-windows arranged in a vertical stack. `winner-mode` or `window-configuration-to-register` are great, but if I want to quickly regain some vertical screen-real-estate without messing up the layout, then it's pretty intuitive to use `follow-mode` and just switch multiple windows to the same buffer, now they behave like one.


### u/b3n [🔗](https://www.reddit.com/r/emacs/comments/t3_ml4wql/comment/t1_gtkc524)

**Votes** 10

Skeletons are one of Emacs' killer features, especially when combined with `abbrev-mode`. Here's a macro I wrote to make them a little easier to handle:

```elisp
(defmacro snip (name &rest skeleton)
  (let* ((snip-name (symbol-name ~,name))
         (func-name (intern (concat "snip-" snip-name))))
    ~(progn
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

Now (assuming you have `abbrev-mode` enabled), type `dd ~ into your buffer (that's ~d` `d` `SPC`) and it'll be replaced with the current date.

This is just scratching the surface, skeletons are extremely powerful. Once you start using them they become a superpower and can take your Emacs usage to the next level.


### u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/t3_lfww57/comment/t1_gmtk79e)

**Votes** 10

From the mailing list, I've just learned of `generic-x.el`, which provides syntax highlighting for `/etc/fstab` or `/etc/passwd` and the like. I appreciated that vim provided that out of the box and I was surprised that emacs also does, but it's just disabled.

`(require 'generic-x)` to enable it.


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_kvmmq3/comment/t1_gj9ioly)

**Votes** 10

Just a cool concept: if you have a keypad on your keyboard which you rarely use, bind its nums to something useful. The results are numlock-sensitive and are NOT the same keycodes as regular numbers, so they're just free keys. For example, `(define-key map (kbd "<kp-1>") 'winum-select-window-1)`


### u/blankspruce [🔗](https://www.reddit.com/r/emacs/comments/t3_rbmfwk/comment/t1_hnrdt9x)

**Votes** 9

Is there a package similar to wdired or wgrep that would work on magit diffs?

Particular use cases I have in mind are:

1.  You've prepared a commit for pull request and during review someone spotted a mistake that's present in multiple files of that commit. Usually I grep the mistake and edit only affected files with wgrep (there might be some arbitrary reason to not fix similar issue in files not present in the commit).
2.  In C++ it happens sometimes that you want to separate declaration and definition and in your commit you forgot to move some definitions to .cpp.

Usually I switch to `foobar.hpp`, kill the necessary part, switch to `foobar.cpp`, yank that part.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_q76kok/comment/t1_hghp1e4)

**Votes** 9

Checkout [Topsy Mode](https://github.com/alphapapa/topsy.el), it creates a header at the top of your buffer to show the name of the first function outside of your visual range. It makes scrolling through code much easier because you get an additional visual queue of your location in the buffer. It's one of those things that you never knew you wanted. It takes about 15 seconds to setup.


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_p28rl5/comment/t1_h8utmh2)

**Votes** 9

Imenu is pretty adictive and it's disappointing when some major mode doesn't support it. Luckily, it's fairly easy to cook up some regexps to provide imenu support in a new major mode. For example I recently noticed that customize buffers didn't have imenu support add I wrote this:

```elisp
(defun configure-imenu-Custom ()
  (setq imenu-generic-expression
        '(("Faces" "^\\(?:Show\\|Hide\\) \\(.*\\) face: \\[sample\\]" 1)
          ("Variables" "^\\(?:Show Value\\|Hide\\) \\([^:\n]*\\)" 1))))

(add-hook 'Custom-mode-hook #'configure-imenu-Custom)

```

One subtlety with writing this is that the customize buffers show little triangles instead of the words "Show", "Hide" or "Show Value". To figure out what text is really in the buffer you can use `C-u C-x =` which tells you about any overlays at point.


### u/globalcandyamnesia [🔗](https://www.reddit.com/r/emacs/comments/t3_o68i0v/comment/t1_h31xz50)

**Votes** 9

If you're using the mark setting commands to expand a selection like `M-@` (mark next word) or `C-M-@` (mark next sexp), you can swap the point and mark (`C-x C-x`) and the selection will be expanded to the left rather than the right.

So if you're in the middle of a sentence, you can press `M-@` a few times to select some words to the right, press `C-xx`, and press `M-@` a few more times to add words before the selection.


### u/b3n [🔗](https://www.reddit.com/r/emacs/comments/t3_oxo1xh/comment/t1_h85cv7f)

**Votes** 9

Little quality of life improvement if you work with multiple eshell buffers:

```elisp
(defun eshell-buffer-name ()
  (rename-buffer (concat "*eshell*<" (eshell/pwd) ">") t))
    
(add-hook 'eshell-directory-change-hook #'eshell-buffer-name)
(add-hook 'eshell-prompt-load-hook #'eshell-buffer-name)
```

\\\*\* u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_mpwapo/comment/t1_gudoljm) \\\*Votes\* 9

\\\*\*Create Rectangular Selection with Meta+Click+Drag\*\*

By default, when you click and drag with the Meta key Emacs creates what it calls a "secondary selection" which is super interesting and useful, but not what this tip is about. In most editors (on the Mac anyway) option+click+drag is used to create a rectangular selection. Emacs, of course, supports this, you just need to remap it.

```elisp
(global-set-key [M-down-mouse-1] #'mouse-drag-region-rectangle)
(global-set-key [M-drag-mouse-1] #'ignore)
(global-set-key [M-mouse-1]      #'mouse-set-point)

```

You can also create a rectangular selection with the command `rectangle-mark-mode`.

Don't forget to bind `replace-rectangle` to something convenient for super easy editing.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_rgu8dp/comment/t1_hoqrg9e)

**Votes** 8

`bs-show` is an interesting command, it shows a pop-up-like buffer that you can use to quickly act on open buffers. There are a ton of customizations you can make and a bunch of convenient bindings. I've been trying it out instead of `list-buffers` and `ibuffer` and I like it so far, very fast.


### u/rberaldo [🔗](https://www.reddit.com/r/emacs/comments/t3_rbmfwk/comment/t1_hnvaab8)

**Votes** 8

A tiny thing I just noticed: in `tex-mode`, you can create a new environment with `C-c C-e`. With the universal argument (`C-u C-c C-e`), however, you can easily change any environment into another.

I created an `enumerate` environment and immediately changed my mind. By chance, I intuitively tried the aforementioned command and I was instantly able to change the environment into `itemize`.

EDIT: markdown

\\\*\* u/T<sub>Verron</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_qlpvgu/comment/t1_hjfbuae) \\\*Votes\* 8

(Nothing too fancy, I'm sure a lot of people have a similar thing in their toolbox, but it was useful to me again today.)

When writing elisp packages, the compiler expects that all variables and functions are declared. Declaring variables defined somewhere else is easy, one just needs to \\~defvar\\~ them, but declaring functions should mention the file where it is defined.

Inserting all those forms is tedious, especially if the function comes from a package with several files. But emacs already knows where the function comes from, so we can just ask it.

```elisp
(defun tv/add-declare-function (fun)
  (interactive "a") 
  (let* ((buf (car (find-function-noselect fun))) 
         (name (file-name-base (buffer-file-name buf)))) 
    (insert (format "(declare-function %s "%s")\n" fun name))))

```

Call it with M-x, insert the name of the function you want to declare (with completion), and voilà.


### u/tryptych [🔗](https://www.reddit.com/r/emacs/comments/t3_qgrpte/comment/t1_hicheof)

**Votes** 8

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

It's not ideal though. In particular, is there a better way to insert the "clipboard"? One thing I quickly found was that you might copy the region to compare but then so many editing commands will add to the kill-ring, so I might want to make that part of the process interactive.

\\\*\* u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_pk6akd/comment/t1_hc3bikc) \\\*Votes\* 8

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

\\\*\* u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_pfpgm9/comment/t1_hb765zp) \\\*Votes\* 8

This is a very simple function, but it has saved me from countless of "do I have to do <span class="underline">this</span> again now?" moments.

When editing LaTeX files I often find myself wanting to convert inline math to display math, in order for equations to "pop out" more. I could not find anything already implemented, so I wrote something that does this whenever the point is inside an inline math (\`$\`-based, sorry \`$ $\` gang) environment:

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

I'm a bit surprised by my not finding any function to already insert some string into the simple \`$$ $$\`-based display math; everything I could find just inserted dollars instead. I suppose one could insert a \`displaymath\` environment, but I've never like the look of that. Oh well.


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_pb6w2z/comment/t1_haddtq6)

**Votes** 8

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


### u/Bodertz [🔗](https://www.reddit.com/r/emacs/comments/t3_p28rl5/comment/t1_h8iin6r)

**Votes** 8

Meta:

Apparently, the `&c.` in the title is an abbreviation of the abbreviation `etc.`, which is fine except that the sidebar's link to past threads of this kind is in fact a link to a reddit search which includes as a search term `etc.` but not `&c.`, so this thread will not show up.

\\\*\* u/jumpUpHigh [🔗](https://www.reddit.com/r/emacs/comments/t3_nlefvx/comment/t1_gzjal45) \\\*Votes\* 8

Considering the ongoing freenode to librea.chat movement, I tried to use erc for the \*n\*th time to connect to the debian channel on oftc. I want to authenticate automatically but it doesn't happen. I still need to use \`/msg NickServ IDENTIFY mypass\`. Can you tell me what to do?

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

and my \`~/.authinfo\` file has an entry

```elisp
machine irc.oftc.net login "mynick" password "mypass"

```

Edit: Using GNU Emacs 27.1


### u/a-k-n [🔗](https://www.reddit.com/r/emacs/comments/t3_mg98ki/comment/t1_gsvlfku)

**Votes** 8

I just discovered that installing the Emacs macport homebrew formula with \\~&#x2013;with-mac-metal\\~ will significantly increase the performance of Emacs. It's buttery smooth!


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_mb8u1m/comment/t1_gry6bfs)

**Votes** 8

If you want helpful mode to completely take over all help functions, and be able to use it with helm-apropos, then add this to your config:

```elisp
(advice-add 'describe-function :override #'helpful-function)
(advice-add 'describe-variable :override #'helpful-variable)
(advice-add 'describe-command  :override #'helpful-callable)
(advice-add 'describe-key      :override #'helpful-key)
(advice-add 'describe-symbol   :override #'helpful-symbol)
```

\\\*\* u/w0ntfix [🔗](https://www.reddit.com/r/emacs/comments/t3_l0ei0t/comment/t1_gknr3hp) \\\*Votes\* 8

my first package is now on melpa! <https://melpa.org/#/ct>

\\\*\* u/StrangeAstronomer [🔗](https://www.reddit.com/r/emacs/comments/t3_kqsw1k/comment/t1_gi8tvp8) \\\*Votes\* 8

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


### u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/t3_kqsw1k/comment/t1_gi66krb)

**Votes** 8

I created a "smart" enter function for C/C++ mode. Here's what it does:

\\\* if you press it on a `for~/~if~/~else~/~switch` (no matter where the cursor is on the line) it will put `{`, an empty line and `}`, and will move the cursor into the body \\\* if you press it on a `struct~/~class`, it's similar to the previous case, but it puts a closing semicolon too. \\\* otherwise it will put a `;`, and a newline \\\* if the `{`, `;` or an empty line is already there, it won't put them again \\\* In comments, it will put an indented line, without continuing the comment (I configured my RET to continue commenting, so I use M-RET when I want to close the comment)

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

At function signatures, it cannot figure out whether you want to create a declaration (closed by `;`) or definition (closed by `{ }`), so it puts a `;`. And the function has a parameter (`force-curly`) with which you can force putting a~{~ (I mapped this to M-S-return).

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

\\\*\* u/poinkalum [🔗](https://www.reddit.com/r/emacs/comments/t3_p28rl5/comment/t1_h8rdjx9) \\\*Votes\* 8

If you follow master, you can use the very useful command `execute-extended-command-for-buffer` by using `M-X` (with a capital "X"), that implements the behaviour described in [this blog post by Lars](https://lars.ingebrigtsen.no/2021/02/16/command-discovery-in-emacs/). It will only show commands that are relevant to the current major mode.

\\\*\* u/slinchisl [🔗](https://www.reddit.com/r/emacs/comments/t3_pt2xws/comment/t1_hdtoivy) \\\*Votes\* 8

I've been playing around with Emacs 28's [repeat-mode](https://git.savannah.gnu.org/cgit/emacs.git/commit/lisp?id=12409c9064c386a496dcbdca76b790108f6c1cad) a bit. It allows for not having to press modifiers when executing several conceptually similar actions in a row. Sadly, the ergonomics of defining these repeat maps are not quite there yet, so I wrote a small macro (my first one ever, actually!) to define the map and set the appropriate symbol property for the function:

```elisp
(defmacro defrepeatmap (symbol &optional pairs docstring)
  "A macro for defining ~repeat-map's.
Defines a new repeat-map called SYMBOL with the given DOCSTRING.
The keys are derived via the list PAIRS, whose elements are cons
cells of the form (KEY . DEF), where KEY and DEF must fulfill the
same requirements as if given to ~define-key'."
  ~(progn
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
  "Keymap to repeat window key sequences.  Used in ~repeat-mode'.")
```


### u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_oxo1xh/comment/t1_h88ph29)

**Votes** 8

This isn't a tip or trick, so I guess it is covered by &c. The Init File section of the manual has this example of setting a user email address:

```elisp
(setq user-mail-address "cheney@torture.gov")
```

\\\*\* u/<sub>viz</sub>\_ [🔗](https://www.reddit.com/r/emacs/comments/t3_pb6w2z/comment/t1_haffax1) \\\*Votes\* 8

This might prove to be helpful for some. xdragon is <https://github.com/mwh/dragon>

```elisp
(defun vz/dired-popup-xdragon ()
  "Open xdragon with the marked files or the file at point."
  (interactive)
  ;; xdragon rename is a nix thing, pretty sure.
  (make-process
   :name "xdragon"
   :command (append '("xdragon") (dired-get-marked-files))
   :noquery t))

```

When I searched for drag and drop support, I did not see anything that hinted at the ability of dragging things <span class="underline">from</span> Emacs hence the usage of (x)dragon.

\\\*\* u/b3n [🔗](https://www.reddit.com/r/emacs/comments/t3_mb8u1m/comment/t1_gs55kqw) \\\*Votes\* 8

I use EXWM mode, so I can use Emacs' excellent window managing functionality universally with my standard applications like Firefox.

Firefox, like most web browsers nowadays, has tabs. But tabs are vastly inferior to Emacs' built in buffer management, and I don't think the abstraction belongs at the application level, instead it should be implemented once universally so it can be used with all applications. I think tabs became popular because window managers failed at effectively managing a large number of browser windows.

I use Firefox with the tab bar hidden, and with an extension which will open every new tab in a new window automatically. Now I can use my buffer switching commands like usual, across the 100s of Firefox windows I have, and it works like a breeze. Whatever improvements I add to help me manage buffers, automatically apply to Firefox. For example I recently enabled \`midnight-mode\` to clean up old buffers that I haven't visited in a few days, and I now have this automatically for Firefox too (I'm bad at manually closing webpages once I open them).

Now, onto my tip/trick. I wanted ibuffer to display the URL of each Firefox window as the file name. So I can search by the window name, or by the URL, while keeping them separated. The file name seems appropriate here as it would otherwise be empty.

To do this, I found an extension that adds the current URL to Firefox's title (I used <https://addons.mozilla.org/en-US/firefox/addon/keepass-helper-url-in-title/>, but any would work), I then wrote the following function:

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

I then added this function to the \`exwm-update-class-hook\` and \`exwm-update-title-hook\` hooks.

Now, in ibuffer, it looks like this:

```elisp
Firefox<Weekly tips/trick/etc/ thread : emacs — Mozilla Fi…> https://www.reddit.com/r/emacs/comments/mb8u1m/weekly_tipstricketc_thread/

```

With the buffer name on the left, and the file name (URL) on the right. Perfect :)

I will make some more improvements in the coming days, so if I split the window with \`C-x 3\` it duplicates the current window, so it works similar to a regular Emacs buffer and I can scroll to different points on the same page. EXWM should make this easy enough with simulation keys.

\\\*\* u/nicholas<sub>hubbard</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_rr330u/comment/t1_hqlp0en) \\\*Votes\* 8

Here is a consult source for [perspective.el](https://github.com/nex3/perspective-el)

```elisp
(defvar consult--source-perspective
    `(:name "Perspective"
            :narrow   ?p
            :category buffer
            :face     consult-buffer
            :history  buffer-name-history
            :state    ,#'consult--buffer-state
            :default  t
            :items    ,#'persp-get-buffer-names)
    "Perspective candidate source for `consult-buffer'.")
```


<a id="2020"></a>

## 2020


### u/gopar [🔗](https://www.reddit.com/r/emacs/comments/t3_k4gv0x/comment/t1_ge9det9)

**Votes** 16

A very simple thing I've done is remap ";" (semicolon) to to "\\\_" (underscore) in almost all modes. Since I work with mainly Python, this is so much easier than always doing SHIFT-DASH every couple of keystrokes. And if I want a regular semicolon, I just do "C-u ;" and insert a semicolon

I also set this in modes such as C/C++, etc. This works by automatically setting the last character (if it was an underscore) to a semicolon on enter.

eg. "|" is cursor

int a = 10\\\*10\\\_|

turns into

int a = 10\\\*10;

|                     |
|-------------------- |
| (cursor on new line) |

&#x200B;

Pretty simple time saver \\\o/


### u/TheDrownedKraken [🔗](https://www.reddit.com/r/emacs/comments/t3_jn6m14/comment/t1_gazzdyz)

**Votes** 16

It would be good to archive the questions and tips put in here. I feel like I always find cool stuff in here, but then it becomes very hard to find it later.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_hqxm5v/comment/t1_fy1rq34)

**Votes** 15

Migrated to native compiled emacs branch this week. Some hiccups but everything seems to work out of box, including pdf-tools. Great performance improvement.


### u/mullikine [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fwbtnte)

**Votes** 13

\## Use chrome DOM for eww

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

Demonstration: <https://asciinema.org/a/UAAVfp5O8SofJZvKBusTOP8QQ>


### u/<sub>hmenke</sub> [🔗](https://www.reddit.com/r/emacs/comments/t3_gqsz8u/comment/t1_fruqs1k)

**Votes** 13

Any ****BibTeX**** users here?

-   Tired of journals forcing you to download a file to get the BibTeX record of an article?
-   Tired of their usually broken formatting?
-   The journal doesn't offer BibTeX download in the first place? (Looking at you Nature)

Did you know that doi.org has query interface that gives you the BibTeX record when you call it with the article DOI? Of course you can access this via Emacs:

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


### u/celeritasCelery [🔗](https://www.reddit.com/r/emacs/comments/t3_gi70ye/comment/t1_fqdnyhk)

**Votes** 13

Shells in emacs like `shell-mode` and `eshell` can write multi line input using `comint-accumulate`. Normally bound to `C-c SPC`.


### u/rhmatthijs [🔗](https://www.reddit.com/r/emacs/comments/t3_gzivu3/comment/t1_ftgqnbp)

**Votes** 12

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


### u/hale314 [🔗](https://www.reddit.com/r/emacs/comments/t3_gi70ye/comment/t1_fqg7qys)

**Votes** 12

I tend to have a lot of function that is defined solely to be added to a hook. Turns out I can customize `defun-declarations-alist` to define a new `hook` property in the `declare` form. Now I can specify the hook that the function is intended for right inside the function definition.

```elisp
;; Need to be done during compilation as well if your functions are getting compiled
(eval-and-compile
  (setf (alist-get 'hook defun-declarations-alist)
        (list (lambda (fun _args hook &optional depth)
                ~(add-hook ',hook #',fun ,@(when depth (list depth)))))))
    
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


### u/kastauyra [🔗](https://www.reddit.com/r/emacs/comments/t3_ibwzcu/comment/t1_g1zlh2t)

**Votes** 11

I am porting my [config](https://github.com/laurynas-biveinis/dotfiles) from 26.3 to 27.1, which had the tweak to do GC whenever a frame loses focus, originally from [MatthewZMD's config](https://github.com/MatthewZMD/.emacs.d) I think:

```elispelisp
(add-hook 'focus-out-hook #'garbage-collect)
```

27.1 NEWS say more generic (and more correct) `after-focus-change-function` should be used instead. Which pointed out that I do not want to GC on just any frame going out of focus, if another frame is being focused instead. It might be a better idea to GC if no frames at all are focused. Somewhat surprisingly I was not able to find any public dotfiles repo implementing this to copy paste from, so I tried to write my own:

```elispelisp
(defun dotfiles--gc-on-last-frame-out-of-focus ()
  "GC if all frames are inactive."
  (if (seq-every-p #'null (mapcar #'frame-focus-state (frame-list)))
  (garbage-collect)))

(add-function :after after-focus-change-function
          #'dotfiles--gc-on-last-frame-out-of-focus)
```


### u/sauntcartas [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fvrlu40)

**Votes** 11

I've been using `M-|` (`shell-command-on-region`) frequently for years, and I only just stumbled on the fact that the region need not be active to use it. If it isn't, the command operates on the text from point to the end of the buffer. That's very reasonable and in line with various other commands, but the documentation doesn't mention it and so I never thought to try it.

That saves me a call to `C-x h` (`mark-whole-buffer`) whenever I want to process the entire buffer, which is most of the time. Also, it's a minor distraction for the entire buffer to be highlighted when I'm composing my shell command, so it's nice to avoid that.

Edited to add: Sorry folks, this doesn't work like I thought it did. See the coments below for details.


### u/jimm [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fvqvedf)

**Votes** 11

I can't say how often I use `dabbrev-expand` (`M-/`) to complete words. Saves me a ton of time.


### u/Krautoni [🔗](https://www.reddit.com/r/emacs/comments/t3_ja97xs/comment/t1_g8pgyy1)

**Votes** 10

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
  (~(,name ,email) (git-commit-insert-header "Co-authed-by" name email))
  (_ (error "No pair programmer found or wrong content")))))

(add-hook 'git-commit-setup-hook 'insert-pair-programmer-as-coauthor)
```

It sets up a co-authored-by for git commits, and enables line numbers.


### u/Amonwilde [🔗](https://www.reddit.com/r/emacs/comments/t3_j61aoh/comment/t1_g7wd5gj)

**Votes** 10

For some this will be obvious, but I'm sure there will be at least one person who will find this useful. One of the most amazing features of Emacs to me is dabbrev-expand, by default bound to M-/.

> Expand previous word "dynamically".

> Expands to the most recent, preceding word for which this is a prefix. I> ifno suitable preceding word is found, words following point are considered. If still no suitable word is found, then look in the > buffers accepted by the function pointed out by variable

This command is essentially omni-autocomplete. Chances are, the term you're trying to complete is in the buffer you're using or another buffer, and you can hit multiple times to cycle through different completions. I find the expander to be quicker and more deterministic than language autocomplete about 70% of the time. It's especially useful in writing, if you use Emacs for things other than programming, as you can complete proper names and specalized vocabulary quickly.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_ikgfxd/comment/t1_g3zeprg)

**Votes** 10

\\\*\*Suggestion for moderators\*\* - Consider putting a note in the weekly announcement for this thread that using 3 backquotes or tildes to make code blocks doesn't work for those of us using old reddit (so the code people post that way is almost unreadable) - and that indenting by 4 spaces is better for compatibility. (Am I the only one who still uses old reddit? :-) )


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fvqq7ck)

**Votes** 10

Undo-tree and kill-ring are two of the best features in Emacs / packages. Change your life today.

\\\*\* u/b3n [🔗](https://www.reddit.com/r/emacs/comments/t3_gdtqov/comment/t1_fq9186h) \\\*Votes\* 10

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


### u/andrmuel [🔗](https://www.reddit.com/r/emacs/comments/t3_jn6m14/comment/t1_gb502ps)

**Votes** 9

This is something I'm not actively using anymore, but it was one of my I-love-emacs moments, so I wanted to share this for a while.

At work, I used to have an org-mode journal where I would take meeting notes. After the meeting, I exported the subtree for the current meeting to a PDF (via ODT) and sent it to the participants via mail.

After a while I extended org-export to get a shortcut (`C-e C-s o M`) to automatically

\\\* export to PDF via ODT

-   using a proper corporate design & logo via ODT\\<sub>STYLES</sub>\\<sub>FILE</sub> header

\\\* rename the file to include the current date \\\* open thunderbird, starting a new message with

-   subject taken from document title (if exporting all) or subtree heading (if exporting subtree
-   pre-filled text
-   the exported PDF already attached

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


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_ixjcau/comment/t1_g69no38)

**Votes** 9

org-variable-pitch.el users might want to give [`valign`](https://github.com/casouri/valign) a look. It aligns your tables nicely, even with pictures (e.g. LaTeX previews) and links. The significance in context of OVP specifically is that you don't need to add `org-link` to `org-variable-pitch-fixed-faces` because valign-mode handles variable pitch links neatly in tables.

If you don't use OVP but use e.g. latex fragments in tables or just pictures, this one is still very helpful.

Kudos to the author, great little package.


### u/Rotatop [🔗](https://www.reddit.com/r/emacs/comments/t3_hij4ga/comment/t1_fwi4ikt)

**Votes** 9

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


### u/rhmatthijs [🔗](https://www.reddit.com/r/emacs/comments/t3_h9zoy9/comment/t1_fuzucay)

**Votes** 9

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

~/bin/get<sub>dark.osascript</sub> contains the following:

```elisp
tell application "System Events"
	tell appearance preferences
		get dark mode
	end tell
end tell
```


### u/zackallison [🔗](https://www.reddit.com/r/emacs/comments/t3_ki09cm/comment/t1_ggoehoo)

**Votes** 9

I posted this in the emacsclient thread, but I think it deserves to live here as well:

The emacsclient / server system is great. If you have it listening on tcp and port forward that when connecting to remote machine it adds another level of power.

I use [emacs-vterm](https://github.com/akermu/emacs-libvterm) for a terminal inside emacs, so I've got a lot of commands remapped. Like `man` runs `emacsclient ... man ..`, which opens the man page in the "other" buffer, so it doesn't interrupt my flow. I use "scroll-other-window" to navigate the man page while I still have my prompt. `magit`, `dired`, and others map to their `emacsclient` equivalents.

I've written wrapper script for e/emacs client that I've come to call `e`, because it saves keystrokes

\#### [The full repo is here e-emacs.sh](https://gitlab.com/zackallison/e-emacs/)

It does the standard things you would expect, starts emacs if it's not already started, open a file in a new buffer / window / terminal and optionally wait for you to finish or have the shell continue.

Then I added some functions that I found useful, starting with piping results from a command into an emacs buffer, such as `find . -name foo\* | e`. Naturally after that was piping from a buffer to a command: `e [file] | rot13` super secure encryption. And of course piping into and out of a buffer works as well `find . -name incriminating-evidence\* | e | xargs rm`, so you can verify / tweak the results before passing them through. Maybe you want to leave the evidence on Two Time Tommy. Who knows.

The other nice feature is the ability to use templates. For example to edit a HTTP request and then send it to a server you can do that: `e -t header_template.txt | nc www.example.com 80` The template file is copied to a temp file which is the one that is edited.

Or the poor man's blog system: `e -t header_template.html body_template.html footer.html > new_page.html`

See the repo for more examples and to download. The notes of getting it working on remote machines aren't the cleanest. If you have any suggestions or features that would make your life easier let me know; submit an issue or comment here.

\#### [The full repo is here e-emacs.sh](https://gitlab.com/zackallison/e-emacs/)

`~I really should post this on one of the share your stuff posts. But I get distracted.~` There, I did it.

In case you can't tell I **really** like emacs and it's client server model.


### u/emacsomancer [🔗](https://www.reddit.com/r/emacs/comments/t3_gqsz8u/comment/t1_fs5sq09)

**Votes** 9

Preconfigured Emacs for collaborative writing (using a literate, self-generating init):

<https://gitlab.com/emacsomancer/collaborative-writing-environment-emacs>

Not a huge, lots-of-packages configuration, but with a focus on writing (org-mode, fountain), including version control (magit).

Each person gets a different colour to indicate the part of the file they’re editing: (Screenshot (from the alternative world in which Cory Doctorow co-wrote <span class="underline">For the Win</span> in Emacs):)

<https://imgur.com/a/zvfLpdH>

\\\*\* u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_kdgv43/comment/t1_gfxbwgy) \\\*Votes\* 8

I tried [marginalia](https://github.com/minad/marginalia) with light annotation and selectrum, it works well. It displays commands' keybindings in the minibuffer. It's especially useful for modes I don't use daily and for which I haven't memorized the keybindings.


### u/martinslot [🔗](https://www.reddit.com/r/emacs/comments/t3_kdgv43/comment/t1_gfwlm9q)

**Votes** 8

I need to try to do something custom to eshell so it feels more like home: <http://www.modernemacs.com/post/custom-eshell/>. Also set som aliases up.

How does your eshell look like?

\\\*\* u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_k8zjx5/comment/t1_gf1msbr) \\\*Votes\* 8

[deleted]


### u/adt7799 [🔗](https://www.reddit.com/r/emacs/comments/t3_ja97xs/comment/t1_g8op875)

**Votes** 8

I find this very useful.

When I have multiple buffers opened and I switch from another program to emacs I always get confused about which buffer the cursor is in. So I created a mapping to

`(global-set-key (kbd "M-l") 'beacon-blink)`


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_hv3kzf/comment/t1_fyrgnk7)

**Votes** 8

[deleted]


### u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/t3_hqxm5v/comment/t1_fy0xduj)

**Votes** 8

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


### u/WorldsEndless [🔗](https://www.reddit.com/r/emacs/comments/t3_hij4ga/comment/t1_fwt1k27)

**Votes** 8

Registers: in Emacs from the beginning, so simple you forget how insanely useful they can be. I use them to save text, windows, and locations. <https://orys.us/tv>


### u/aartist111 [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fvrw4cu)

**Votes** 8

Found / c for M-x ibuffer. It filters buffers by content. It looks like 'grep -c' . Very helpful to locate a file quickly for which you remember any word from content Until now I had only used filters for filenames or modes only. .


### u/hairlesscaveman [🔗](https://www.reddit.com/r/emacs/comments/t3_gmkg4g/comment/t1_fr4gdm6)

**Votes** 8

I follow the format of `{ticketnumber}-{short-description}` when creating branches using Magit, but when typing the descriptive name for the branch I often type `SPC` between words instead of dash due to muscle memory when writing sentences. This causes a warning to be shown, because "Whitespace isn't allowed here", and breaks "flow" for me.

The advice below quiets this warning, and inserts a dash whenever space is pressed.

```elisp
(advice-add 'magit-whitespace-disallowed :around (lambda (orig-fun &rest args) (interactive) (insert "-")))
```


### u/karthink [🔗](https://www.reddit.com/r/emacs/comments/t3_gi70ye/comment/t1_fqfc1wi)

**Votes** 8

AucTex users: You're missing out if you don't use [CDLatex](https://github.com/cdominik/cdlatex). It's primarily a fast input tool for LaTeX, sort of like snippet templates. The difference between setting up Yasnippet templates for LaTeX and CDLatex is that CDLaTeX's TAB key to jump past stuff is <span class="underline">always available</span>, not just during snippet entry. It's difficult to explain, so here are some demos:

1.  [Fast input with cdlatex and preview.el](https://gfycat.com/heavenlynegligiblehoiho)
2.  [Fast input with keys displayed](https://gfycat.com/safeidolizedlangur)

I wrote a longer post explaining [how I set up AucTex](https://www.reddit.com/r/emacs/comments/g8ecpj/advice_for_auclatex_what_keybinds_do_you_find/foo64ge/) recently.

CDLaTeX was written by Carsten Dominik, the author of Org-mode and reftex. Thus Org ships with an `org-cdlatex` minor-mode that makes these features available in org-mode.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_gi70ye/comment/t1_fqczes1)

**Votes** 8

[A beginers guide to emacs 24 or later by sasha chua](https://sachachua.com/blog/wp-content/uploads/2013/05/How-to-Learn-Emacs-v2-Large.png) this helped me tremendously to get started with emacs.


### u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/t3_gi70ye/comment/t1_fqcycvb)

**Votes** 8

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
    ((pred (string-match "\\~d .*"))
     (links-search--launch "dict" (substring query 2 nil)))
    ((pred (string-match "\\~b .*"))
     (links-search--launch "book" (substring query 2 nil)))
    ((pred (string-match "\\~w .*"))
     (links-search--launch "wiki" (substring query 2 nil)))
    ((pred (string-match "\\~m .*"))
     (links-search--launch "imdb" (substring query 2 nil)))
    ((pred (string-match "\\~y .*"))
     (links-search--launch "yout" (substring query 2 nil)))
    ((pred (string-match "\\~t .*"))
     (links-search--launch "thes" (substring query 2 nil)))
    ((pred (string-match "\\~s .*"))
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


### u/ji99 [🔗](https://www.reddit.com/r/emacs/comments/t3_k4gv0x/comment/t1_ge8si78)

**Votes** 8

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

\\\*\* u/oantolin [🔗](https://www.reddit.com/r/emacs/comments/t3_jix6od/comment/t1_gaid3f4) \\\*Votes\* 8

I love using \`dabbrev-expand\` (bound by default to \`M-/\`) to complete text I know is in one of my buffers. It completes one word at a time, but if you want to keep pulling subsequent words from the location where a completion is found you can insert a space and run \`dabbrev-expand\` again, so \`SPC M-/\`.

For example if you have the text "The quick brown fox jumps over the lazy dog" in some buffer (and say no other "qu" appears anywhere in your buffers), then \`qu M-/ SPC M-/ SPC M-/\` will insert "quick brown fox".

This is already great, but I find the key sequence \`SPC M-/\` awkward to type so I wrote this little function which I bind to \`M-'\`:

```elisp
(defun dabbrev-next (arg)
  "Insert the next ARG words from where previous expansion was found."
  (interactive "p")
  (dotimes (_ arg)
    (insert " ")
    (dabbrev-expand 1)))
    
```

Then for "quick grown fox" I can go \`qu M-/ M-' M-'\` which feels much easier to type (on my keyboard \`/\` and \`'\` are very close to each other).

(\`M-'\` is bound by default to \`abbrev-prefix-mark\` which I never use, so I didn't mind rebinding it.)


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_gqsz8u/comment/t1_frynpvt)

**Votes** 8

Make a little mode called `my-minor-mode` and enable it globally. Use it's keymap for your keybindings, without the prefix. Then assign that keymap to a prefix. This way, you can disable most of your keybindings easily when needed, you can easily switch your prefix key (e.g. go from `C-c` to `H-x` or `<menu>`, etc.), have those keybindings available on multiple prefixes, and you can easily restore a default keybinding via `(define-key my-minor-mode-map <key> nil)`. Here is how I define a minor mode for myself:

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
  "Keymap prefix for ~gk-minor-mode'.")
    
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
  "Enable or disable the modes listed in ~gk-global-modes' at once."
  (interactive)
  (setf gk-toggle-global-modes (not gk-toggle-global-modes))
  (let (errors)
    ;; Enable global modes
    (dolist (mode gk-global-modes)
      (condition-case e
          (funcall mode (if gk-toggle-global-modes 1 -1))
        (error (push ~(,mode ,e) errors))))
    ;; Disable modes in gk-disabled-modes
    (dolist (mode gk-disabled-modes)
      (condition-case e
          (funcall mode (if gk-toggle-global-modes -1 1))
        (error (push ~(,mode ,e) errors))))
    (when errors
      (warn "Following errors occurred when activating global modes:\n%S"
            errors))))
    
(add-hook 'after-init-hook 'gk-toggle-global-modes)

```

`~That function needs some updating tho. But it works fine as a hook that sets up the global modes.~` I put `global-gk-minor-mode` into `gk-global-modes` and it's enabled alongside some other ones.

Edit: fix `gk-toggle-global-modes`.


### u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_gmkg4g/comment/t1_fr65b4d)

**Votes** 8

TIL that I can disable \`\`company\`\` for some modes, I needed to do it because completion was really slow for the shell and eshell which have vanilla shell completion anyway. Thus

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

\\\*\* u/spinochet [🔗](https://www.reddit.com/r/emacs/comments/t3_jix6od/comment/t1_gaapjtw) \\\*Votes\* 8

For some time I have wished I could color code the background colors of different buffers to make it easier to pick them out on a busy screen and to give myself visual cues about keybinding differences (like paging with the space bar in view-mode). I've finally done it, and it wasn't as complicated as I thought it might be.

```elisp
(defun bespoke-background-color ()
  "Color code background according to buffer type."
  ;; called in after-change-major-mode-hook & view-mode-hook
  (cond
   ((member (buffer-name)
            (list "*Backtrace*" "*Compile-Log*"
                  "*Completions*" "*Messages*"))
    (buffer-face-set :background "#1f1d1b"))  ; brown
   ((member (buffer-name)
            (list "*Colors*" "*Faces*")) nil) ; text gray (8)
   ((derived-mode-p 'special-mode)
    (buffer-face-set :background "#1c221c"))  ; green
   ((or buffer-read-only view-mode)
    (buffer-face-set :background "#111c22"))  ; blue
   ((derived-mode-p 'prog-mode)
    (buffer-face-set :background "gray5"))    ; prog gray (5)
   (t nil)))                                  ; text gray (8)
```

\\\*\* u/[deleted] [🔗](https://www.reddit.com/r/emacs/comments/t3_heaoiu/comment/t1_fvy3geb) \\\*Votes\* 8

`(setq visual-order-cursor-movement t)`

> If non-nil, moving cursor with arrow keys follows the visual order. > > When this is non-nil, <right> will move to the character that is to the right of point on display, and <left> will move to the left, disregarding the surrounding bidirectional context. Depending on the bidirectional context of the surrounding characters, this can move point many buffer positions away. > > When the text is entirely left-to-right, logical-order and visual-order cursor movements produce identical results."

I write in Arabic.
