package com.dezdeqness.playlist.presentattion.model

data class PlaylistTrackUiModel(
    val id: String,
    val urn: String,
    val name: String,
    val authorName: String,
    val iconImageUrl: String,
    val streamUrl: String,
)
