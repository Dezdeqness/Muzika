package com.dezdeqness.playlist.presentattion

data class PlaylistPageState(
    val urn: String = "",
    val title: String = "",
    val description: String? = null,
    val imageUrl: String = "",
    val authorName: String = "",
    val type: String = "",
    val duration: Long = 0,
    val tracksCount: Long = 0,
)
