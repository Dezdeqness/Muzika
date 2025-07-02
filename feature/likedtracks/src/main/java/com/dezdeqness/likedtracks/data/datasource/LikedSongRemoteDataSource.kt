package com.dezdeqness.likedtracks.data.datasource

import com.dezdeqness.likedtracks.domain.model.LikedState

interface LikedSongRemoteDataSource {
    suspend fun getLikedSongs(key: String? = null): Result<LikedState>
}
