package com.example.workyapp.domain.subreddit

import javax.inject.Inject

class RedditPostsUseCase @Inject constructor(private val redditRepository: RedditRepository) {

    suspend operator fun invoke() = redditRepository.getPosts()
}