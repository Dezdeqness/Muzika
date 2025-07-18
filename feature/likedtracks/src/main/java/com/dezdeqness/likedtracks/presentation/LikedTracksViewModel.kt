package com.dezdeqness.likedtracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.likedtracks.domain.LikedRepository
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class LikedTracksViewModel(
    @Provided private val likedRepository: LikedRepository,
    private val likedTrackMapper: LikedTrackMapper,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    val likedTracks =
        likedRepository
            .createPager()
            .map { it.map(likedTrackMapper::toUiModel) }
            .flowOn(coroutineDispatcherProvider.io())
            .cachedIn(viewModelScope)

}
