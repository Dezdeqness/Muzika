package com.dezdeqness.home.data.models

import com.dezdeqness.shared.data.models.SongUserRemote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistRemote(
    val duration: Long,
    val genre: String,
    val description: String?,
    val uri: String,
    @SerialName("tag_list")
    val tagList: String,
    @SerialName("track_count")
    val trackCount: Long,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("user_urn")
    val userUrn: String,
    val user: SongUserRemote,
    val id: Long,
    val urn: String,
    val tags: String,
    val title: String,
    @SerialName("artwork_url")
    val artworkUrl: String?,
)