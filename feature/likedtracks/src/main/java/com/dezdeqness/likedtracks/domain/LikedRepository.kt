package com.dezdeqness.likedtracks.domain

import com.dezdeqness.shared.domain.models.SongEntity

interface LikedRepository {
    suspend fun getLikedSongs(): Result<List<SongEntity>>
}
