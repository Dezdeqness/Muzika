package com.dezdeqness.home.presentation

data class PlaylistTransferObject(
    val id: Long,
    val title: String,
    val userName: String,
    val imageUrl: String,
    val urn: String,
    val description: String?,
    val duration: Long,
    val tracksCount: Long,
)
