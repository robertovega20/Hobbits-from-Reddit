package com.example.workyapp.domain.subreddit.model

data class EntryEntity(
    var author: AuthorEntity?,
    var category: CategoryEntity,
    var content: String,
    var id: String,
    var mediaEntity: MediaEntity,
    var link: LinkEntity,
    var updated: String,
    var title: String
)

data class CategoryEntity(
    var term: String,
    var label: String
)

data class LinkEntity(
    var href: String
)

data class MediaEntity(
    val url: String
)

