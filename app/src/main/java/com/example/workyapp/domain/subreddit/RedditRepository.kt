package com.example.workyapp.domain.subreddit

import com.example.workyapp.domain.subreddit.model.AllHobbitsPosts
import com.example.workyapp.domain.subreddit.model.EntryEntity
import com.example.workyapp.util.Result

interface RedditRepository {

    suspend fun getPosts(): Result<EntryEntity?>
}