package com.dezdeqness.innertube.models.request

import com.dezdeqness.innertube.models.others.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
)
