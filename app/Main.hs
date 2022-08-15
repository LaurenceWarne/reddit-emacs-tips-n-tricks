{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE LambdaCase #-}
module Main (main) where

import Lib
import Reddit
import Reddit.Types.Post hiding (score)
import Reddit.Types.Subreddit as R
import Control.Monad.IO.Class
import Reddit.Types.SearchOptions (Order(Relevance))
import Data.Either
import Data.Maybe (listToMaybe, catMaybes, mapMaybe)
import Reddit.Types.Comment
import Control.Monad (mfilter)
import Data.Text (Text)


getTopWeeklyComments :: MonadIO m => m (Either (APIError RedditError) [Text])
getTopWeeklyComments = runRedditAnon $ do
  let options = Options Nothing Nothing
  let s = "Weekly Tips"
  Listing _ _ posts <- search (Just $ R "emacs") options Relevance s
  comments <- traverse (getComments . postID) posts
  let allComments = mapMaybe (\case
                      Actual comment -> if any (> 20) (score comment) then
                                          Just $ body comment
                                        else Nothing
                      _ -> Nothing) (mconcat comments)
  return allComments

main :: IO ()
main = do
  posts <- getTopWeeklyComments
  let firstPost = listToMaybe (fromRight [] posts)
  liftIO $ print firstPost
