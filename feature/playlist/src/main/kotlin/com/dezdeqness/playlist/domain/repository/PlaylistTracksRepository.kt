package com.dezdeqness.playlist.domain.repository

import androidx.paging.PagingData
import com.dezdeqness.shared.domain.models.SongEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistTracksRepository {
    fun createPager(usn: String): Flow<PagingData<SongEntity>>
}
