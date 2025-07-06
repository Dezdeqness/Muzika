package com.dezdeqness.likedtracks.domain

import androidx.paging.PagingData
import com.dezdeqness.likedtracks.domain.model.LikedState
import com.dezdeqness.shared.domain.models.SongEntity
import kotlinx.coroutines.flow.Flow

interface LikedRepository {
    fun createPager(): Flow<PagingData<SongEntity>>
    suspend fun getLikedSongsRemote(key: String? = null): Result<LikedState>
}
