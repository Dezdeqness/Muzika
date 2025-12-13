package com.dezdeqness.playlist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dezdeqness.playlist.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.playlist.data.paging.PlaylistTracksSource
import com.dezdeqness.playlist.domain.repository.PlaylistTracksRepository
import org.koin.core.annotation.Single

@Single(binds = [PlaylistTracksRepository::class])
class PlaylistTracksRepositoryImpl(
    private val playlistRemoteDataSource: PlaylistRemoteDataSource,
) : PlaylistTracksRepository {

    override fun createPager(usn: String) =
        Pager(
            config = PagingConfig(
                pageSize = 40,
                initialLoadSize = 40,
                prefetchDistance = 10,
            ),
            pagingSourceFactory = {
                PlaylistTracksSource(
                    usn = usn,
                    playlistRemoteDataSource = playlistRemoteDataSource,
                )
            }
        ).flow
}
