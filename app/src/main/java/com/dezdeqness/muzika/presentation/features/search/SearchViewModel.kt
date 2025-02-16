package com.dezdeqness.muzika.presentation.features.search

import androidx.media3.session.MediaController
import com.dezdeqness.muzika.core.BaseViewModel
import com.dezdeqness.muzika.core.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SearchViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    fun performSearch(query: String) {
        launchOnIo {

        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun loadQuery(songId: String, mediaController: MediaController) {
        launchOnIo {

        }
    }

    fun onEmptyQuery() {
        _searchState.update {
            _searchState.value.copy(
                items = listOf(),
                isEmptyScreenShown = false,
            )
        }
    }
}
