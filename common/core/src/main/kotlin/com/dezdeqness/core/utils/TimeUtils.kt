package com.dezdeqness.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

object TimeUtils {

    private val trackTime = SimpleDateFormat("m:ss", Locale.getDefault())

    fun convertToTrackTime(value: Long) = trackTime.format(value)

}
