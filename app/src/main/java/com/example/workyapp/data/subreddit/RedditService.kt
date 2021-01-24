
package com.example.workyapp.data.subreddit

import com.example.workyapp.data.subreddit.model.Feed
import com.example.workyapp.data.login.model.TokenAccessResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface RedditService {

    @GET("r/lotr/.rss")
    suspend fun getLotrResponse(
        @Query("limit") limit: Int
    ): Response<Feed>
}