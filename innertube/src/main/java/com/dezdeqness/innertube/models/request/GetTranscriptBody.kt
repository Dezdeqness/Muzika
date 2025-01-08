package com.dezdeqness.innertube.models.request

import com.dezdeqness.innertube.models.others.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)
