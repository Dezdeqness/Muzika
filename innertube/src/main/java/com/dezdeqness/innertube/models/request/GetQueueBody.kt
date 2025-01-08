package com.dezdeqness.innertube.models.request

import com.dezdeqness.innertube.models.others.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetQueueBody(
    val context: Context,
    val videoIds: List<String>?,
    val playlistId: String?,
)
