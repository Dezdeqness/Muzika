package com.dezdeqness.shared.domain.models

data class PlaylistState(
    val list: List<PlaylistEntity>,
    val nextKey: String,
)