package com.dezdeqness.home.domain.repository

import com.dezdeqness.home.domain.model.PlaylistState

interface PlaylistRepository {
    suspend fun getPlaylistByQuery(query: String, limit: Int = 5): Result<PlaylistState>
    suspend fun getLikedPlaylist(): Result<PlaylistState>
}