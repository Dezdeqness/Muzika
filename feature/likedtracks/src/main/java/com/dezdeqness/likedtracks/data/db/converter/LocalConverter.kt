package com.dezdeqness.likedtracks.data.db.converter

import androidx.room.TypeConverter

class LocalConverter {
    @TypeConverter
    fun fromTags(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split(",")

    @TypeConverter
    fun tagsToString(tags: List<String>): String = tags.joinToString(",")
}
