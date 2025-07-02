package com.dezdeqness.likedtracks.domain

import com.dezdeqness.likedtracks.domain.model.LikedState

interface LikedRepository {
    suspend fun getLikedSongsRemote(key: String? = null): Result<LikedState>
}
