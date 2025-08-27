package com.dezdeqness.playlist.data.datasource

import com.dezdeqness.playlist.domain.model.PlaylistTracksState

interface PlaylistRemoteDataSource {
    suspend fun getPlaylistTracks(usn: String, key: String?): Result<PlaylistTracksState>
}
