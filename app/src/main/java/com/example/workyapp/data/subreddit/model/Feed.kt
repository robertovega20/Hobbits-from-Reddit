package com.example.workyapp.data.subreddit.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Xml(name = "feed")
data class Feed(
    @Element(name = "entry")
    var entries: List<Entry>?
)

@Xml(name = "entry")
data class Entry(
    @Element(name = "author")
    var author: Author?,
    @Element(name = "category")
    var category: Category?,
    @PropertyElement(name = "content", converter = HtmlEscapeStringConverter::class)
    val content: String?,
    @PropertyElement(name = "id")
    var id: String?,
    @Element(name = "media:thumbnail")
    var media: Media?,
    @Element(name = "link")
    var link: Link?,
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

@Xml(name = "link")
data class Link(
    @Attribute(name = "href") val href: String?
)

@Xml(name = "category" )
data class Category(
    @Attribute(name = "term") val term: String?,
    @Attribute(name = "label") val label: String?
)

@Xml(name = "media:thumbnail")
data class Media(
    @Attribute(name = "url") val url: String?
)

