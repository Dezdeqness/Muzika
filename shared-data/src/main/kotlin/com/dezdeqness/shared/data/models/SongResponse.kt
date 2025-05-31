package com.dezdeqness.shared.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongRemote(
    val kind: String,
    val id: Long,
    val urn: String,
    @SerialName("created_at")
    val createdAt: String,
    val duration: Long,
    @SerialName("comment_count")
    val commentCount: Long,
    @SerialName("tag_list")
    val tagList: String,
    val genre: String?,
    val title: String,
    val description: String?,
    @SerialName("release_year")
    val releaseYear: Long?,
    @SerialName("release_month")
    val releaseMonth: Long?,
    @SerialName("release_day")
    val releaseDay: Long?,
    val uri: String,
    @SerialName("permalink_url")
    val permalinkUrl: String,
    @SerialName("artwork_url")
    val artworkUrl: String?,
    @SerialName("stream_url")
    val streamUrl: String?,
    @SerialName("download_url")
    val downloadUrl: String?,
    @SerialName("waveform_url")
    val waveformUrl: String,
    @SerialName("user_playback_count")
    val userPlaybackCount: Long,
    @SerialName("playback_count")
    val playbackCount: Long,
    @SerialName("download_count")
    val downloadCount: Long,
    @SerialName("favoritings_count")
    val favoriteCount: Long,
    @SerialName("reposts_count")
    val repostsCount: Long,
    val user: SongUserRemote,
)

@Serializable
data class SongUserRemote(
    val id: Long,
    @SerialName("username")
    val userName: String,
)
