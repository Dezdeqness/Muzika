package com.dezdeqness.likedtracks.data.paging

import androidx.paging.PagingState
import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.dezdeqness.likedtracks.data.datasource.LikedSongLocalDatasource
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSource
import com.dezdeqness.likedtracks.data.db.model.SongLocal

@OptIn(ExperimentalPagingApi::class)
class LikedTracksRemoteMediator(
    private val likedSongRemoteDataSource: LikedSongRemoteDataSource,
    private val likedSongLocalDatasource: LikedSongLocalDatasource,
    private val database: RoomDatabase,
) : RemoteMediator<Int, SongLocal>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SongLocal>
    ): MediatorResult {
        return try {
            val isRefresh = loadType == LoadType.REFRESH

            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        null
                    } else {
                        nextCursor
                    }
                }
            }

            val result = likedSongRemoteDataSource.getLikedSongs(key = loadKey)
            if (result.isFailure) {
                MediatorResult.Error(result.exceptionOrNull()!!)
            }

            val state = result.getOrNull()!!

            database.withTransaction<RoomDatabase> {
                if (isRefresh) {
                    likedSongLocalDatasource.clearAll()
                }
                likedSongLocalDatasource.insertAll(state.list)
                database
            }

            nextCursor = extractCursor(state.nextKey)

            MediatorResult.Success(endOfPaginationReached = state.nextKey.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun extractCursor(nextHref: String): String? {
        val uri = nextHref.toUri()
        return uri.getQueryParameter("cursor")
    }

    companion object {
        private var nextCursor: String? = null
    }
}
