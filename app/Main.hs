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
import Data.Maybe (listToMaybe, mapMaybe, fromMaybe)
import Reddit.Types.Comment
import Data.Text (Text)


data CommentInfo = CommentInfo { user :: Username
                               , upvotes :: Integer
                               , content :: Text
                               , id :: CommentID} deriving Show

fromComment :: Comment -> CommentInfo
fromComment comment =
  CommentInfo commentAuthor commentScore (body comment) (commentID comment)
                where
                  commentAuthor = Reddit.Types.Comment.author comment
                  commentScore = fromMaybe 0 (score comment)

commentFilter :: Comment -> Bool
commentFilter comment = any (> 10) (score comment)

getTopWeeklyComments :: MonadIO m => m (Either (APIError RedditError) [CommentInfo])
getTopWeeklyComments = runRedditAnon $ do
  let options = Options Nothing (Just 100)
  let s = "Weekly Tips"
  Listing _ _ posts <- search (Just $ R "emacs") options Relevance s
  comments <- traverse (getComments . postID) posts
  let allComments = mapMaybe (\case
                      Actual comment -> if commentFilter comment then
                                          Just $ fromComment comment
                                        else Nothing
                      _ -> Nothing) (mconcat comments)
  return allComments

main :: IO ()
main = do
  commentsOrErr <- getTopWeeklyComments
  let comments = fromRight [] commentsOrErr
  liftIO $ print ("No comments: " <> show (length comments))
  liftIO $ print $ take 20 comments
