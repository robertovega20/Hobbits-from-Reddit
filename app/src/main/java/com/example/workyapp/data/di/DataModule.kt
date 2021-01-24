package com.example.workyapp.data.di

import com.example.workyapp.data.login.LoginRepositoryImp
import com.example.workyapp.data.subreddit.RedditRepositoryImp
import com.example.workyapp.data.vote.VoteRepositoryImp
import com.example.workyapp.domain.login.LoginRepository
import com.example.workyapp.domain.subreddit.RedditRepository
import com.example.workyapp.domain.vote.VoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRedditRepository(
        redditRepositoryImp: RedditRepositoryImp
    ): RedditRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImp: LoginRepositoryImp
    ): LoginRepository

    @Binds
    abstract fun bindVoteRepository(
        voteRepositoryImp: VoteRepositoryImp
    ): VoteRepository
}