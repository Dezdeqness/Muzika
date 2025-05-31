package com.dezdeqness.likedtracks.data.datasource

import com.dezdeqness.shared.domain.models.SongEntity

interface LikedSongDataSource {
    suspend fun getLikedSongs(): Result<List<SongEntity>>
}
