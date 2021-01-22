package com.example.workyapp.domain.model

data class EntryEntity(
    var author: AuthorEntity?,
    var category: String,
    var content: String,
    var id: String,
    var link: String,
    var updated: String,
    var title: String
)