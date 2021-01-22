package com.example.workyapp.data

import com.example.workyapp.data.model.Feed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("lotr/.rss")
    suspend fun getLotrResponse(
        @Query("limit") limit: Int,
    ): Response<Feed>
}