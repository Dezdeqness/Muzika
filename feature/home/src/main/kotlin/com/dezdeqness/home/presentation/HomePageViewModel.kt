package com.dezdeqness.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.home.domain.repository.PlaylistRepository
import com.dezdeqness.home.presentation.mapper.HomeUiMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomePageViewModel(
    private val playlistRepository: PlaylistRepository,
    private val homeUiMapper: HomeUiMapper,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val reloadTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeState : StateFlow<HomeState> =
        reloadTrigger
            .onStart { emit(Unit) }
            .flatMapLatest {
                flow {
                    val (liked, q1, q2, q3) = coroutineScope {
                        awaitAll(
                            async { playlistRepository.getLikedPlaylist() },
                            async { playlistRepository.getPlaylistByQuery(QUERY_ANIME) },
                            async { playlistRepository.getPlaylistByQuery(QUERY_EUROBEAT) },
                            async { playlistRepository.getPlaylistByQuery(QUERY_PHONK) },
                        )
                    }.also {
                        it.forEach {
                            if (it.isFailure) {
                                emit(HomeState(status = StateStatus.Error))
                                return@flow
                            }
                        }
                    }
                    emit(
                        HomeState(
                            liked = liked.getOrThrow().list.map(homeUiMapper::toUiModel),
                            sectionAnime = q1.getOrThrow().list.map(homeUiMapper::toUiModel),
                            sectionEurobeat = q2.getOrThrow().list.map(homeUiMapper::toUiModel),
                            sectionPhonk = q3.getOrThrow().list.map(homeUiMapper::toUiModel),
                            status = StateStatus.Loaded,
                        )
                    )
                }.flowOn(coroutineDispatcherProvider.io())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = HomeState(status = StateStatus.Loading)
            )

    fun onErrorRetry() {
        reloadTrigger.tryEmit(Unit)
    }

    companion object {
        private const val QUERY_ANIME = "anime"
        private const val QUERY_EUROBEAT = "eurobeat"
        private const val QUERY_PHONK = "phonk"
    }

}
