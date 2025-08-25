package com.dezdeqness.home.data.repository

import com.dezdeqness.home.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.home.domain.repository.PlaylistRepository
import org.koin.core.annotation.Single

@Single(binds = [PlaylistRepository::class])
class PlaylistRepositoryImpl(
    private val playlistRemoteDataSource: PlaylistRemoteDataSource,
) : PlaylistRepository {
    override suspend fun getPlaylistByQuery(
        query: String,
        limit: Int,
    ) = playlistRemoteDataSource.getPlaylistByQuery(query = query, limit = limit)

    override suspend fun getLikedPlaylist() = playlistRemoteDataSource.getLikedPlaylist()
}
