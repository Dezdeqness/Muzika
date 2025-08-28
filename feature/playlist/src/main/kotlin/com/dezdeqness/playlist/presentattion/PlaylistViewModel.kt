package com.dezdeqness.playlist.presentattion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.playlist.domain.repository.PlaylistTracksRepository
import com.dezdeqness.playlist.presentattion.mapper.PlaylistTrackUiMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PlaylistViewModel(
    private val playlistTracksRepository: PlaylistTracksRepository,
    private val playlistTrackUiMapper: PlaylistTrackUiMapper,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val playlistState =
        flow {
            emit(
                PlaylistPageState(
                    urn = savedStateHandle.get<String>("urn").orEmpty(),
                    title = savedStateHandle.get<String>("title").orEmpty(),
                    description = savedStateHandle.get<String>("description").orEmpty(),
                    imageUrl = savedStateHandle.get<String>("imageUrl").orEmpty(),
                    tracksCount = savedStateHandle.get<Long>("tracksCount") ?: 0,
                    authorName = savedStateHandle.get<String>("userName").orEmpty(),
                    duration = savedStateHandle.get<Long>("duration") ?: 0,
                )
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = PlaylistPageState()
            )

    val playlistTracks =
        playlistTracksRepository
            .createPager(savedStateHandle.get<String>("urn").orEmpty())
            .map { it.map(playlistTrackUiMapper::toUiModel) }
            .flowOn(coroutineDispatcherProvider.io())
            .cachedIn(viewModelScope)

}
