package com.example.workyapp.domain

import javax.inject.Inject

class RedditPostsUseCase @Inject constructor(private val redditRepository: RedditRepository) {

    suspend operator fun invoke() = redditRepository.getPosts()
}