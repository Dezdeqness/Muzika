package com.dezdeqness.likedtracks.data.datasource

import androidx.paging.PagingSource
import com.dezdeqness.likedtracks.data.db.LikedDatabase
import com.dezdeqness.likedtracks.data.db.mapper.SongLocalMapper
import com.dezdeqness.likedtracks.data.db.model.SongLocal
import com.dezdeqness.shared.domain.models.SongEntity

class LikedSongLocalDataSourceImpl(
    private val database: LikedDatabase,
    private val mapper: SongLocalMapper
) : LikedSongLocalDatasource {

    override fun getPagedTracks(): PagingSource<Int, SongLocal> {
        return database.likedTrackDao().getPagedTracks()
    }

    override suspend fun insertAll(entities: List<SongEntity>) {
        var startIndex = database.likedTrackDao().getMaxOrder() ?: 0

        if (startIndex != 0) {
            startIndex += 1
        }

        val locals = entities.mapIndexed { index, item ->
            mapper.toLocal(index = startIndex + index, entity = item)
        }
        database.likedTrackDao().insertAll(locals)
    }

    override suspend fun clearAll() {
        database.likedTrackDao().clearAll()
    }
}
