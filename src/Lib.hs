{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE LambdaCase #-}
module Lib
    ( CommentInfo(..), commentInfoToMd, fromComment, searchLimit,
      getTopWeeklyComments, getRelevantPosts
    ) where
import Reddit
import Reddit.Types.Post hiding (score)
import Control.Monad.IO.Class
import Reddit.Types.SearchOptions (Order(Top))
import Data.Either
import Data.Maybe (mapMaybe, fromMaybe, isJust)
import Reddit.Types.Comment
import qualified Data.Text as T
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
commentFilter c = any (>= 8) (score c)


getRelevantPosts :: MonadIO m => T.Text -> m (Either (APIError RedditError) [Post])
getRelevantPosts s = runRedditAnon $ do
  let options = Options Nothing (Just searchLimit)
  Listing _ _ posts <- search (Just $ R "emacs") options Top s
  return posts

getTopWeeklyComments :: MonadIO m => [Post] -> m (Either (APIError RedditError) [CommentInfo])
getTopWeeklyComments posts = runRedditAnon $ do
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
      (codeNormalised, lastLineCode) = foldl (\ (s, codeLast) l ->
                let stripped = T.stripPrefix "    " l
                    code = isJust stripped || (T.empty == T.strip l && codeLast)
                    prefix = if code && not codeLast || not code && codeLast
                             then "```\n" else ""
                in (s <> "\n" <> prefix <> fromMaybe l stripped, code))
                ("", False) (T.lines raw)
      split = T.splitOn "```\n" (codeNormalised <> if lastLineCode then "\n```" else "")
      in mconcat (zipWith (<>) (init split) (cycle ["```elisp\n", "```\n"]) ++ [last split])
