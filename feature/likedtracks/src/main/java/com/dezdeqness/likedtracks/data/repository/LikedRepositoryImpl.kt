package com.dezdeqness.likedtracks.data.repository

import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSource
import com.dezdeqness.likedtracks.domain.LikedRepository

class LikedRepositoryImpl(
    private val likedSongRemoteDataSource: LikedSongRemoteDataSource,
) : LikedRepository {
    override suspend fun getLikedSongsRemote(key: String?) = likedSongRemoteDataSource.getLikedSongs(key)
}
