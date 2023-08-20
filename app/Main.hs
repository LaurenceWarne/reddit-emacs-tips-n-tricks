{-# LANGUAGE OverloadedStrings #-}
module Main (main) where

import Data.Either
import Data.Text.IO (writeFile)
import Data.List (sortOn, nub)
import Prelude hiding (writeFile) 
import Lib (CommentInfo(..), commentInfoToMd, getTopWeeklyComments, getRelevantPosts)

main :: IO ()
main = do
  postsOrErr <- getRelevantPosts "Weekly Tips" Nothing
  let posts = nub $ fromRight [] postsOrErr
  print ("No posts: " <> show (length posts))
  commentsOrErr <- getTopWeeklyComments posts
  let comments = sortOn (negate . upvotes) (fromRight [] commentsOrErr)
  let totalComments = length comments

  let s = foldMap ((<> "\n\n") . commentInfoToMd) comments
  if totalComments > 0 then
    writeFile "out.md" s *> print ("Processed comments: " <> show totalComments)
  else print ("Skipped writing comments as none were found" :: [Char])  -- Why needed?
  --let s2 = foldMap ((<> "\n\n") . comment) comments
  --writeFile "out2.md" s2
