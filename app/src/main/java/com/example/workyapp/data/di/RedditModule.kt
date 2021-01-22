package com.example.workyapp.data.di

import com.example.workyapp.data.RedditRepositoryImp
import com.example.workyapp.domain.RedditRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RedditModule {

    @Binds
    abstract fun bindRedditRepository(
        redditRepositoryImp: RedditRepositoryImp
    ): RedditRepository
}