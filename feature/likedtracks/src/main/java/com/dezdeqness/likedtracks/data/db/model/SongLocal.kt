package com.dezdeqness.likedtracks.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_tracks")
data class SongLocal(
    @PrimaryKey val id: Long,
    val kind: String,
    val urn: String,
    val createdAt: String,
    val duration: Long,
    val commentCount: Long,
    val tags: String,
    val genre: String?,
    val title: String,
    val description: String?,
    val releaseYear: Long,
    val releaseMonth: Long,
    val releaseDay: Long,
    val uri: String,
    val permalinkUrl: String,
    val artworkUrl: String?,
    val streamUrl: String?,
    val downloadUrl: String?,
    val waveformUrl: String,
    val userPlaybackCount: Long,
    val playbackCount: Long,
    val downloadCount: Long,
    val favoriteCount: Long,
    val repostsCount: Long,
    val metadataArtist: String?,
    val userId: Long,
    val userName: String,
    val userAvatar: String,
    val page: Int
)
