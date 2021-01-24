package com.example.workyapp.domain.vote

import com.example.workyapp.domain.login.LoginRepository
import javax.inject.Inject

class VoteUseCase @Inject constructor(private val voteRepository: VoteRepository) {

    suspend operator fun invoke(dir: Int, id: String) = voteRepository.votePost(dir, id)
}