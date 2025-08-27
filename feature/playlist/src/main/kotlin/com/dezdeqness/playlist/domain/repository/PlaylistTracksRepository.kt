package com.dezdeqness.playlist.domain.repository

import com.dezdeqness.playlist.domain.model.PlaylistTracksState

interface PlaylistTracksRepository {
    suspend fun getPlaylistTracks(usn: String, key: String? = null): Result<PlaylistTracksState>
}
