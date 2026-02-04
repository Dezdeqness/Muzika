package com.dezdeqness.likedtracks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.likedtracks.presentation.LikedTrackPage
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel

const val routeLiked = "Liked"

fun NavGraphBuilder.likedScreen(
    onSongClick: (Int) -> Unit,
    onPlaylistChanged: (List<LikedTrackUiModel>) -> Unit,
) {
    composable(route = routeLiked) {
        LikedTrackPage(
            onSongClick = onSongClick,
            onPlaylistChanged = onPlaylistChanged,
        )
    }
}
