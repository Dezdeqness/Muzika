package com.dezdeqness.likedtracks.data.repository

import com.dezdeqness.likedtracks.data.datasource.LikedSongDataSource
import com.dezdeqness.likedtracks.domain.LikedRepository

class LikedRepositoryImpl(
    private val likedSongDataSource: LikedSongDataSource,
) : LikedRepository {
    override suspend fun getLikedSongs() = likedSongDataSource.getLikedSongs()
}
