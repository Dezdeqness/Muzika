package com.dezdeqness.home.domain.repository

import com.dezdeqness.shared.domain.models.PlaylistState

interface PlaylistRepository {
    suspend fun getPlaylistByQuery(query: String, limit: Int = 5): Result<PlaylistState>
    suspend fun getLikedPlaylist(): Result<PlaylistState>
}