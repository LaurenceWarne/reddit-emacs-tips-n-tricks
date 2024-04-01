## u/vkazanov [🔗](https://www.reddit.com/r/emacs/comments/1bdm6mc/comment/kuo1f9y) 
**Votes:** 17

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

## u/geza42 [🔗](https://www.reddit.com/r/emacs/comments/1b20xgn/comment/ksifwh1) 
**Votes:** 12

If you use an LSP server with semantic highlighting, it's worth checking out the value of `font-lock-maximum-decoration`. For example, I use `c++-mode` with `lsp-mode` (with clangd), I decreased `font-lock-maximum-decoration` to `2`, and I didn't notice any highlighting difference (because the lost highlighting by `c++-mode` gets highlighted by `lsp-mode`), while `c++-mode` font-locking become faster (`c++-mode` 's font-locking works well 99.9% of the time, but sometimes it can become slow in some circumstances, these slowdowns seems to be gone).

I use:`(setq font-lock-maximum-decoration '((c-mode . 2) (c++-mode . 2) (t . t)))`

## u/LionyxML [🔗](https://www.reddit.com/r/emacs/comments/1b20xgn/comment/kslwb72) 
**Votes:** 8

A blog post regarding my own Emacs config aiming to get the same user experience on both TUI and GUI.  
[https://www.rahuljuliato.com/posts/lemacs](https://www.rahuljuliato.com/posts/lemacs)

## u/demosthenex [🔗](https://www.reddit.com/r/emacs/comments/1b7uj43/comment/ktogga6) 
**Votes:** 8

M-x ielm  Use the repl while learning elisp coding. I had no idea!