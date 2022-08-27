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
import Data.Maybe (mapMaybe, fromMaybe, isJust, isNothing)
import Reddit.Types.Comment
import qualified Data.Text as T
import Prelude hiding (writeFile)
import Data.Text (isInfixOf)

searchLimit :: Int
searchLimit = 100

data CommentInfo = CommentInfo { user :: T.Text
                               , upvotes :: Integer
                               , comment :: T.Text
                               , commentInfoID :: T.Text
                               , parentID :: T.Text} deriving (Eq, Show)

runWithAgent :: MonadIO m => RedditT m a -> m (Either (APIError RedditError) a)
runWithAgent r = runRedditWith (RedditOptions True Nothing Anonymous (Just "reddit-tips-n-tricks")) r

fromComment :: Comment -> CommentInfo
fromComment c = CommentInfo auth votes (body c) cID parentPostID
                  where
                    Username auth = Reddit.Types.Comment.author c
                    votes = fromMaybe 0 (score c)
                    CommentID cID = commentID c
                    PostID parentPostID = parentLink c

commentFilter :: Comment -> Bool
commentFilter c = any (>= 8) (score c) && not (isDeleted c)

getRelevantPosts :: MonadIO m => T.Text -> Maybe PostID -> m (Either (APIError RedditError) [Post])
getRelevantPosts s maybePostID = runWithAgent $ do
  let options = Options (After <$> maybePostID) (Just searchLimit)
  Listing _ maybeAfter posts <- search (Just $ R "emacs") options Top s
  nextPage <- case (maybeAfter, posts) of
    (maybeAfterID @ (Just _), _:_) -> getRelevantPosts s maybeAfterID
    _ -> return $ Right []
  return (posts ++ fromRight [] nextPage)

getTopWeeklyComments :: MonadIO m => [Post] -> m (Either (APIError RedditError) [CommentInfo])
getTopWeeklyComments posts = runWithAgent $ do
  comments <- traverse (getComments . postID) posts
  let allComments = mapMaybe (\case
                      Actual c -> if commentFilter c then Just $ fromComment c
                                  else Nothing
                      _ -> Nothing) (mconcat comments)
  return allComments

data CodeFormatting = TripleQuoted | Indented deriving Eq

commentInfoToMd :: CommentInfo -> T.Text
commentInfoToMd c =
  let link = "[🔗](" <> "https://www.reddit.com/r/emacs/comments/" <> parentID c <> "/comment/" <> commentInfoID c <> ")"
      raw = "## u/" <> user c <> " " <> link <> "\n**Votes:** " <>
        T.pack (show (upvotes c)) <> "\n\n" <> comment c
      (codeNormalised, maybeFormatting) = foldl (\ (s, lastFormatting) l ->
                let stripped = T.stripPrefix "    " l
                    code = (case lastFormatting of
                             Just TripleQuoted -> if "```" `isInfixOf` l then Nothing
                                                  else Just TripleQuoted
                             Just Indented ->
                               if isJust stripped ||
                                 (T.empty == T.strip l && isJust lastFormatting)
                               then Just Indented else Nothing

                             Nothing -> if "```" `isInfixOf` l
                                        then Just TripleQuoted
                                        else (if isJust stripped then Just Indented
                                              else Nothing))

                    prefix = if (code == Just Indented) && isNothing lastFormatting ||
                               isNothing code && (lastFormatting == Just Indented)
                             then "```\n" else ""
                in (s <> "\n" <> prefix <> fromMaybe l stripped, code))
                ("", Nothing) (T.lines raw)
      quoteLastLine = maybeFormatting == Just Indented
      split = T.splitOn "```\n" (codeNormalised <> if quoteLastLine then "\n```" else "")
      in mconcat (zipWith (<>) (init split) (cycle ["```elisp\n", "```\n"]) ++ [last split])
