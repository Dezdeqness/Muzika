package com.dezdeqness.likedtracks.data.datasource

import androidx.paging.PagingSource
import com.dezdeqness.likedtracks.data.db.model.SongLocal
import com.dezdeqness.shared.domain.models.SongEntity

interface LikedSongLocalDatasource {
    fun getPagedTracks(): PagingSource<Int, SongLocal>
    suspend fun insertAll(entities: List<SongEntity>)
    suspend fun clearAll()
}
