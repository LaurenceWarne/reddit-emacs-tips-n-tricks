{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE QuasiQuotes #-}
import Text.RawString.QQ
import Test.Hspec
import Lib(CommentInfo(..), commentInfoToMd)

main :: IO ()
main = hspec $ do
  describe "commentInfoToMd" $ do
    it "adds code blocks" $ do
      let commentInfo = CommentInfo "foo" 10 [r|Automatic `chmod +x` when you save a file that starts with a `#!` shebang:

    (add-hook 'after-save-hook 'executable-make-buffer-file-executable-if-script-p)
|] "commentID" "parentID"

      commentInfoToMd commentInfo `shouldBe` [r|
## u/foo [🔗](https://www.reddit.com/r/emacs/comments/parentID/comment/commentID)
**Votes:** 10

Automatic `chmod +x` when you save a file that starts with a `#!` shebang:

```elisp
(add-hook 'after-save-hook 'executable-make-buffer-file-executable-if-script-p)
```|]

