package com.dezdeqness.home.domain.model

import com.dezdeqness.shared.domain.models.SongUserEntity

data class PlaylistEntity(
    val duration: Long,
    val genre: String,
    val description: String?,
    val uri: String,
    val trackCount: Long,
    val userEntity: SongUserEntity,
    val id: Long,
    val urn: String,
    val title: String,
    val artworkUrl: String?,
)