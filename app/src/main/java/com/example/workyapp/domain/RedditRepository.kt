package com.example.workyapp.domain

import com.example.workyapp.domain.model.EntryEntity
import com.example.workyapp.util.Result

interface RedditRepository {

    suspend fun getPosts(): Result<List<EntryEntity>>
}