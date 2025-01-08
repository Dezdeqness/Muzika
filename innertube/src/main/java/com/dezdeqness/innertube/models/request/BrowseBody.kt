package com.dezdeqness.innertube.models.request

import com.dezdeqness.innertube.models.others.Context
import kotlinx.serialization.Serializable

@Serializable
data class BrowseBody(
    val context: Context,
    val browseId: String?,
    val params: String?,
)
