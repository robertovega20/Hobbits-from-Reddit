package com.example.workyapp.data.vote

import android.content.Context
import android.util.Log
import com.example.workyapp.R
import com.example.workyapp.data.subreddit.RedditRepositoryImp.Companion.ERROR_SERVICE
import com.example.workyapp.data.subreddit.RedditRepositoryImp.Companion.ERROR_TAG
import com.example.workyapp.domain.subreddit.model.AllHobbitsPosts
import com.example.workyapp.domain.vote.VoteRepository
import com.example.workyapp.util.Result
import com.example.workyapp.util.SessionManager
import com.example.workyapp.util.safeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject

class VoteRepositoryImp @Inject constructor(
    private val retrofit: Retrofit,
    @ApplicationContext context: Context
) : VoteRepository {

    companion object {
        const val RANK = 2
        const val OAUTH_URL = "https://oauth.reddit.com/api/vote"
        const val BEARER = "Bearer "
    }

    private val sessionManager = SessionManager(context)

    override suspend fun votePost(dir: Int, id: String) = safeApiCall(
        call = { votePostCall(dir, id) },
        errorMessage = R.string.error_polite
    )

    private suspend fun votePostCall(dir: Int, id: String): Result<Unit> {
        val response = retrofit
            .create(VoteService::class.java)
            .votePostService(
                BEARER + sessionManager.fetchAuthToken(),
                OAUTH_URL,
                dir,
                id,
                RANK
            )
        if (response.isSuccessful) {
            return Result.Success(Unit)
        }
        Log.d(ERROR_TAG, ERROR_SERVICE)
        return Result.Error(R.string.error_polite)
    }
}