package com.dezdeqness.likedtracks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.likedtracks.domain.LikedRepository
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class LikedTracksViewModel(
    private val likedRepository: LikedRepository,
    private val likedTrackMapper: LikedTrackMapper,
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
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = LikedTracksState(status = StateStatus.Loading)
            )

}
