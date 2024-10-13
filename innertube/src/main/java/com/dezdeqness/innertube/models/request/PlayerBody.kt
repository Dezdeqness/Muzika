package com.dezdeqness.innertube.models.request

import com.dezdeqness.innertube.models.others.Context
import kotlinx.serialization.Serializable

@Serializable
data class PlayerBody(
    val context: Context,
    val videoId: String,
    val playlistId: String?,
)
