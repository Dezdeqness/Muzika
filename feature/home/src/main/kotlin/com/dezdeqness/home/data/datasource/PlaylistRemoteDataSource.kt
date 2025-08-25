package com.dezdeqness.home.data.datasource

import com.dezdeqness.home.domain.model.PlaylistState

interface PlaylistRemoteDataSource {
    suspend fun getPlaylistByQuery(query: String, limit: Int): Result<PlaylistState>
    suspend fun getLikedPlaylist(): Result<PlaylistState>
}
