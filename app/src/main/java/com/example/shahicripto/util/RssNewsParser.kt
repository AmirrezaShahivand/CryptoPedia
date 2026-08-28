package com.example.shahicripto.util

import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

object RssNewsParser {
    fun parse(xml: String): List<NewsDataEntity> {
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(xml.reader())
        val result = mutableListOf<NewsDataEntity>()
        var event = parser.eventType
        var insideItem = false
        var title = ""
        var link = ""
        var body = ""
        var image = ""
        var currentTag = ""

        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    val name = parser.name.lowercase()
                    if (name == "item" || name == "entry") {
                        insideItem = true
                        title = ""; link = ""; body = ""; image = ""
                    } else if (insideItem) {
                        currentTag = name
                        if ((name == "enclosure" || name.endsWith(":content")) && image.isBlank()) {
                            image = parser.getAttributeValue(null, "url").orEmpty()
                        }
                    }
                }
                XmlPullParser.TEXT, XmlPullParser.CDSECT -> {
                    if (insideItem) {
                        val value = parser.text.orEmpty()
                        when (currentTag) {
                            "title" -> title += value
                            "link", "guid" -> if (link.isBlank()) link += value
                            "description", "content:encoded", "content" -> body += value
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    val name = parser.name.lowercase()
                    if (name == "item" || name == "entry") {
                        if (link.isNotBlank() && title.isNotBlank()) {
                            result += NewsDataEntity(
                                title = title.trim().stripHtml(),
                                url = link.trim(),
                                image = image.trim(),
                                body = body.trim().stripHtml().ifBlank { title.trim().stripHtml() }
                            )
                        }
                        insideItem = false
                    }
                    currentTag = ""
                }
            }
            event = parser.next()
        }
        return result.distinctBy { it.url }
    }

    private fun String.stripHtml(): String =
        replace(Regex("<[^>]*>"), " ").replace(Regex("\\s+"), " ").trim()
}
