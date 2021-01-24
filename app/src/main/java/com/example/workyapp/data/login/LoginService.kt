package com.example.workyapp.data.login


import com.example.workyapp.data.login.model.TokenAccessResponse
import com.example.workyapp.data.subreddit.model.Feed
import retrofit2.Response
import retrofit2.http.*

interface LoginService {

    @POST("api/v1/access_token")
    suspend fun retrieveToken(
        @Header("Authorization") credentials: String,
        @Query("grant_type") grantType: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String
    ) : Response<TokenAccessResponse>
}