{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE LambdaCase #-}
module Main (main) where

import Reddit
import Reddit.Types.Post hiding (score)
import Control.Monad.IO.Class
import Reddit.Types.SearchOptions (Order(Top))
import Data.Either
import Data.Maybe (mapMaybe, fromMaybe)
import Reddit.Types.Comment
import qualified Data.Text as T
import Data.Text.IO (writeFile)
import Data.List (sortOn)
import Prelude hiding (writeFile)


searchLimit :: Int
searchLimit = 100

data CommentInfo = CommentInfo { user :: T.Text
                               , upvotes :: Integer
                               , comment :: T.Text
                               , id :: CommentID} deriving Show

fromComment :: Comment -> CommentInfo
fromComment c = CommentInfo commentAuthor commentScore (body c) (commentID c)
                  where
                    Username commentAuthor = Reddit.Types.Comment.author c
                    commentScore = fromMaybe 0 (score c)

commentFilter :: Comment -> Bool
commentFilter c = any (>= 10) (score c)

getTopWeeklyComments :: MonadIO m => m (Either (APIError RedditError) [CommentInfo])
getTopWeeklyComments = runRedditAnon $ do
  let options = Options Nothing (Just searchLimit)
  let s = "Weekly Tips"
  Listing _ _ posts <- search (Just $ R "emacs") options Top s
  comments <- traverse (getComments . postID) posts
  let allComments = mapMaybe (\case
                      Actual c -> if commentFilter c then Just $ fromComment c
                                  else Nothing
                      _ -> Nothing) (mconcat comments)
  return allComments

commentInfoToMd :: CommentInfo -> T.Text
commentInfoToMd c =
  let raw = "## u/" <> user c <> "\n**Votes:** " <>
        T.pack (show (upvotes c)) <> "\n\n" <> comment c
      split = T.splitOn "```\n" raw
      in mconcat (zipWith (<>) (init split) (cycle ["```elisp\n", "```\n"]) ++ [last split])
-- commentInfoToMd $ CommentInfo (T.pack "User") 10 (T.pack "``` foobar```  ```barfoo```") (CommentID (T.pack "id"))

main :: IO ()
main = do
  commentsOrErr <- getTopWeeklyComments
  let comments = sortOn (negate . upvotes) (fromRight [] commentsOrErr)

  print ("No comments: " <> show (length comments))
  writeFile "out.md"  (foldMap ((<> "\n\n") . commentInfoToMd) comments)

  --print $ take 10 comments
