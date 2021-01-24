package com.example.workyapp.domain.vote

import com.example.workyapp.util.Result


interface VoteRepository {

    suspend fun votePost(dir: Int, id: String): Result<Unit>
}
