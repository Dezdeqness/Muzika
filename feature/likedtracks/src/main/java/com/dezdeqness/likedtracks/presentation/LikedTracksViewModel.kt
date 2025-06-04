package com.dezdeqness.likedtracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.likedtracks.domain.LikedRepository
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class LikedTracksViewModel(
    private val likedRepository: LikedRepository,
    private val likedTrackMapper: LikedTrackMapper,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    val likedTracks =
        flow {
            likedRepository
                .getLikedSongs()
                .onSuccess {
                    val uiItems = likedTrackMapper.toUiModel(it)
                    emit(
                        LikedTracksState(
                            tracks = uiItems,
                            status = StateStatus.Loaded
                        )
                    )
                }
                .onFailure {
                    emit(LikedTracksState(status = StateStatus.Error))
                }
        }
            .flowOn(coroutineDispatcherProvider.io())
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = LikedTracksState(status = StateStatus.Loading)
            )

}
