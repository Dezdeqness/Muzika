package com.dezdeqness.likedtracks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.likedtracks.presentation.LikedTrackPage
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel

const val LIKED_ROUTE = "liked_route"

fun NavGraphBuilder.likedScreen(onSongClick: (LikedTrackUiModel) -> Unit) {
    composable(LIKED_ROUTE) {
        LikedTrackPage(onSongClick = onSongClick)
    }
}
