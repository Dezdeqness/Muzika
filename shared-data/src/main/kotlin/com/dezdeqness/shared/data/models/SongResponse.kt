package com.dezdeqness.shared.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongResponse(
    val kind: String,
    val id: Long,
    val urn: String,
    @SerialName("created_at")
    val createdAt: String,
    val duration: Long,
    val commentable: Boolean,
    @SerialName("comment_count")
    val commentCount: Long,
    val sharing: String,
    @SerialName("tag_list")
    val tagList: String,
    val streamable: Boolean,
    @SerialName("embeddable_by")
    val embeddableBy: String,
    @SerialName("purchase_url")
    val purchaseUrl: String?,
    @SerialName("purchase_title")
    val purchaseTitle: String?,
    val genre: String?,
    val title: String,
    val description: String?,
    @SerialName("label_name")
    val labelName: String?,
    val release: String?,
    @SerialName("key_signature")
    val keySignature: String?,
    val isrc: String?,
    val bpm: String?,
    @SerialName("release_year")
    val releaseYear: Long?,
    @SerialName("release_month")
    val releaseMonth: Long?,
    @SerialName("release_day")
    val releaseDay: Long?,
    val license: String,
    val uri: String,
    val user: UserResponse,
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
    @SerialName("available_country_codes")
    val availableCountryCodes: String?,
    @SerialName("secret_uri")
    val secretUri: String?,
    @SerialName("user_favorite")
    val userFavorite: Boolean,
    @SerialName("user_playback_count")
    val userPlaybackCount: Long,
    @SerialName("playback_count")
    val playbackCount: Long,
    @SerialName("download_count")
    val downloadCount: Long,
    @SerialName("favoritings_count")
    val favoritingsCount: Long,
    @SerialName("reposts_count")
    val repostsCount: Long,
    val downloadable: Boolean,
    val access: String,
    @SerialName("metadata_artist")
    val metadataArtist: String?,
)