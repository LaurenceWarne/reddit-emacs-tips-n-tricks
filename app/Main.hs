{-# LANGUAGE OverloadedStrings #-}
module Main (main) where

import Lib
import Reddit
import Reddit.Types.Post
import Reddit.Types.Subreddit as R
import Control.Monad.IO.Class
import Reddit.Types.SearchOptions (Order(Relevance))
import Data.Either
import Data.Maybe (listToMaybe)

getTopPosts :: MonadIO m => m (Either (APIError RedditError) [Post])
getTopPosts = runRedditAnon $ do
  let options = Options Nothing Nothing
  Listing _ _ posts <- search (Just $ R "emacs") options Relevance "org"
  return posts

main :: IO ()
main = do
  posts <- getTopPosts
  let firstPost = listToMaybe (fromRight [] posts)
  liftIO $ print firstPost
