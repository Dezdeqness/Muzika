package com.dezdeqness.core.network.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse<T : Any>(
    val collection: List<T>?,
    @SerialName("next_href")
    val nextHref: String?
)
