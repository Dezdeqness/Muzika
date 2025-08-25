package com.dezdeqness.home.domain.model

data class PlaylistState(
    val list: List<PlaylistEntity>,
    val nextKey: String,
)