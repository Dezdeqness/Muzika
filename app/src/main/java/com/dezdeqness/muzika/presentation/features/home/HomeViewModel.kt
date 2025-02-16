package com.dezdeqness.muzika.presentation.features.home

import androidx.media3.session.MediaController
import com.dezdeqness.muzika.core.BaseViewModel
import com.dezdeqness.muzika.core.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    init {
        launchOnIo {

        }
    }

    fun loadQuery(songId: String, mediaController: MediaController) {
        launchOnIo {

        }
    }
}
