package com.dezdeqness.likedtracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.likedtracks.data.paging.LikedTracksPagingSource
import com.dezdeqness.likedtracks.domain.LikedRepository
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LikedTracksViewModel(
    private val likedRepository: LikedRepository,
    private val likedTrackMapper: LikedTrackMapper,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    val likedTracks =
        createPager()
            .map { it.map(likedTrackMapper::toUiModel) }
            .flowOn(coroutineDispatcherProvider.io())
            .cachedIn(viewModelScope)

    private fun createPager() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = { LikedTracksPagingSource(likedRepository = likedRepository) }
        ).flow

}
