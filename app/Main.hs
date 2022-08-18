{-# LANGUAGE OverloadedStrings #-}
module Main (main) where

import Data.Either
import Data.Text.IO (writeFile)
import Data.List (sortOn)
import Prelude hiding (writeFile) 
import Lib (CommentInfo(..), commentInfoToMd, getTopWeeklyComments)

main :: IO ()
main = do
  commentsOrErr <- getTopWeeklyComments
  let comments = sortOn (negate . upvotes) (fromRight [] commentsOrErr)

  print ("No comments: " <> show (length comments))
  let s = foldMap ((<> "\n\n") . commentInfoToMd) comments
  writeFile "out.md" s

  --print $ take 10 comments
