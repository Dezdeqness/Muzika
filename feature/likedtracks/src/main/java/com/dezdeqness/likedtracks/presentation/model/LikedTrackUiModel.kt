package com.dezdeqness.likedtracks.presentation.model

data class LikedTrackUiModel(
    val id: String,
    val urn: String,
    val name: String,
    val authorName: String,
    val iconImageUrl: String,
    val streamUrl: String,
)
