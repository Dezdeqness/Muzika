package com.dezdeqness.likedtracks.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.dezdeqness.likedtracks.data.datasource.LikedSongLocalDatasource
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSource
import com.dezdeqness.likedtracks.data.db.LikedDatabase
import com.dezdeqness.likedtracks.data.db.mapper.SongLocalMapper
import com.dezdeqness.likedtracks.data.paging.LikedTracksRemoteMediator
import com.dezdeqness.likedtracks.domain.LikedRepository
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [LikedRepository::class])
class LikedRepositoryImpl(
    private val likedSongRemoteDataSource: LikedSongRemoteDataSource,
    private val likedSongLocalDatasource: LikedSongLocalDatasource,
    private val songLocalMapper: SongLocalMapper,
    private val roomDatabase: LikedDatabase,
) : LikedRepository {

    // Outstanding move in Paging3
    // No map in PagingSource and all code is fucked up
    @OptIn(ExperimentalPagingApi::class)
    override fun createPager() =
        Pager(
            config = PagingConfig(
                pageSize = 40,
                initialLoadSize = 40,
                prefetchDistance = 10,
            ),
            remoteMediator = LikedTracksRemoteMediator(
                likedSongRemoteDataSource = likedSongRemoteDataSource,
                likedSongLocalDatasource = likedSongLocalDatasource,
                database = roomDatabase,
            ),
            pagingSourceFactory = { likedSongLocalDatasource.getPagedTracks() }
        )
            .flow
            .map { it.map(songLocalMapper::toEntity) }

    override suspend fun getLikedSongsRemote(key: String?) =
        likedSongRemoteDataSource.getLikedSongs(key)

}
