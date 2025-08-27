package com.dezdeqness.playlist.data.repository

import com.dezdeqness.playlist.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.playlist.domain.repository.PlaylistTracksRepository
import org.koin.core.annotation.Single

@Single(binds = [PlaylistTracksRepository::class])
class PlaylistTracksRepositoryImpl(
    private val playlistRemoteDataSource: PlaylistRemoteDataSource,
) : PlaylistTracksRepository {
    override suspend fun getPlaylistTracks(
        usn: String,
        key: String?
    ) = playlistRemoteDataSource.getPlaylistTracks(usn = usn, key = key)
}
