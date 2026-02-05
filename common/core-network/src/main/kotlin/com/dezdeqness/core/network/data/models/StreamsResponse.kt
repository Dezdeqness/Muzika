package com.dezdeqness.core.network.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamsResponse(
    @SerialName("http_mp3_128_url")
    val httpMp3128Url: String?,
    @SerialName("hls_mp3_128_url")
    val hlsMp3128Url: String?,
    @SerialName("preview_mp3_128_url")
    val previewMp3128Url: String?
)
