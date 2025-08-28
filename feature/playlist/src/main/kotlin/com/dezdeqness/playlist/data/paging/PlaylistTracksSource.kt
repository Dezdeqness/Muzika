package com.dezdeqness.playlist.data.paging

import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dezdeqness.playlist.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.shared.domain.models.SongEntity

@OptIn(ExperimentalPagingApi::class)
class PlaylistTracksSource(
    private val usn: String,
    private val playlistRemoteDataSource: PlaylistRemoteDataSource,
) : PagingSource<String, SongEntity>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SongEntity> {
        return try {
            val loadKey = params.key

            val result = playlistRemoteDataSource.getPlaylistTracks(usn = usn, key = loadKey)

            if (result.isFailure) {
                return LoadResult.Error(result.exceptionOrNull()!!)
            }

            val state = result.getOrNull()!!
            nextCursor = extractCursor(state.nextKey)

            LoadResult.Page(
                data = state.list,
                prevKey = null,
                nextKey = if (state.nextKey.isEmpty()) null else nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, SongEntity>): String? {
        return null
    }

    private fun extractCursor(nextHref: String): String? {
        val uri = nextHref.toUri()
        return uri.getQueryParameter("cursor")
    }

    companion object {
        private var nextCursor: String? = null
    }
}
