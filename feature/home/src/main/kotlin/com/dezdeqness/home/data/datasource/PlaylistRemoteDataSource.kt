package com.dezdeqness.home.data.datasource

import com.dezdeqness.shared.domain.models.PlaylistState

interface PlaylistRemoteDataSource {
    suspend fun getPlaylistByQuery(query: String, limit: Int): Result<PlaylistState>
    suspend fun getLikedPlaylist(): Result<PlaylistState>
}
