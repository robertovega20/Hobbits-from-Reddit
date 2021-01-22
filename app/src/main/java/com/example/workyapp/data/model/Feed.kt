package com.example.workyapp.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "feed")
data class Feed(
    @Element(name = "entry")
    var entries: List<Entry>?
)

@Xml(name = "entry")
data class Entry(
    @Element(name = "author")
    var author: Author?,
    @PropertyElement(name = "category")
    var category: String?,
    @PropertyElement(name = "content")
    var content: String?,
    @PropertyElement(name = "id")
    var id: String?,
    @PropertyElement(name = "link")
    var link: String?,
    @PropertyElement(name = "updated")
    var updated: String?,
    @PropertyElement(name = "title")
    var title: String?
)

@Xml(name = "author")
data class Author(
    @PropertyElement(name = "name")
    val name: String?,
    @PropertyElement(name = "uri")
    val uri: String?
)