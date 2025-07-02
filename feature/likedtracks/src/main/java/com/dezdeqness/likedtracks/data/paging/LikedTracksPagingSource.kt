package com.dezdeqness.likedtracks.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dezdeqness.likedtracks.domain.LikedRepository
import com.dezdeqness.shared.domain.models.SongEntity
import androidx.core.net.toUri

class LikedTracksPagingSource(
    private val likedRepository: LikedRepository,
) : PagingSource<String, SongEntity>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, SongEntity> {
        val cursor = params.key

        val result = likedRepository.getLikedSongsRemote(key = cursor)

        if (result.isFailure) {
            return LoadResult.Error(result.exceptionOrNull()!!)
        }

        val state = result.getOrNull()!!

        return LoadResult.Page(
            data = state.list,
            prevKey = null,
            nextKey = extractCursor(state.nextKey)
        )
    }

    override fun getRefreshKey(state: PagingState<String, SongEntity>): String? = null

    private fun extractCursor(nextHref: String): String? {
        val uri = nextHref.toUri()
        return uri.getQueryParameter("cursor")
    }
}
