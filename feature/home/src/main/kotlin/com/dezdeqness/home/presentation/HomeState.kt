package com.dezdeqness.home.presentation

import androidx.compose.runtime.Immutable
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

@Immutable
data class HomeState(
    val status: StateStatus = StateStatus.Initial,
    val liked: List<HomePlaylistUiModel> = listOf(),
    val sectionAnime: List<HomePlaylistUiModel> = listOf(),
    val sectionEurobeat: List<HomePlaylistUiModel> = listOf(),
    val sectionPhonk: List<HomePlaylistUiModel> = listOf(),
)

enum class StateStatus {
    Initial,
    Loading,
    Error,
    Loaded
}
