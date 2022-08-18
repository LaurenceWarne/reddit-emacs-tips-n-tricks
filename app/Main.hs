{-# LANGUAGE OverloadedStrings #-}
module Main (main) where

import Data.Either
import Data.Text.IO (writeFile)
import Data.List (sortOn)
import Prelude hiding (writeFile) 
import Lib (CommentInfo(..), commentInfoToMd, getTopWeeklyComments, getRelevantPosts)
import Control.Monad
import Control.Monad.IO.Class

main :: IO ()
main = do
  postsOrErr <- getRelevantPosts "Weekly Tips"
  let posts = fromRight [] postsOrErr
  print ("No posts: " <> show (length posts))
  commentsOrErr <- getTopWeeklyComments posts
  let comments = sortOn (negate . upvotes) (fromRight [] commentsOrErr)

  print ("No comments: " <> show (length comments))
  let s = foldMap ((<> "\n\n") . commentInfoToMd) comments
  writeFile "out.md" s

  --print $ take 10 comments
