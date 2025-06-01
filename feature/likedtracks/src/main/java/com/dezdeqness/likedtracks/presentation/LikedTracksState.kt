package com.dezdeqness.likedtracks.presentation

import androidx.compose.runtime.Immutable
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel

@Immutable
data class LikedTracksState(
    val tracks: List<LikedTrackUiModel> = listOf(),
    val status: StateStatus = StateStatus.Initial,
)

enum class StateStatus {
    Initial,
    Loading,
    Error,
    Loaded
}
