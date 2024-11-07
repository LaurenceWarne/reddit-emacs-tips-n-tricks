(require 'package)
(let* ((no-ssl (and (memq system-type '(windows-nt ms-dos))
                    (not (gnutls-available-p))))
       (proto (if no-ssl "http" "https")))
  (add-to-list 'package-archives (cons "melpa" (concat proto "://melpa.org/packages/")) t))

(package-initialize)

(package-install 'ox-gfm)
(require 'ox-gfm)

(setq org-export-use-babel nil)
(print command-line-args)
(let ((org-file (car (last command-line-args))))
  (find-file org-file)
  (beginning-of-buffer)
  (insert "#+OPTIONS: broken-links:t\n")
  (org-gfm-export-to-markdown))
