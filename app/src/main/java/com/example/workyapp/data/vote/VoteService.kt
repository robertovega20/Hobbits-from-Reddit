package com.example.workyapp.data.vote

import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface VoteService {

    @POST
    suspend fun votePostService(
        @Header("Authorization") token: String,
        @Url url: String,
        @Query("dir") dir: Int,
        @Query("id") id: String,
        @Query("rank") rank: Int
    ) : Response<Unit>
}
