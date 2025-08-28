package com.dezdeqness.likedtracks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.likedtracks.presentation.LikedTrackPage
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel
import kotlinx.serialization.Serializable

@Serializable
object Liked

fun NavGraphBuilder.likedScreen(
    onSongClick: (Int) -> Unit,
    onPlaylistChanged: (List<LikedTrackUiModel>) -> Unit,
) {
    composable<Liked> {
        LikedTrackPage(
            onSongClick = onSongClick,
            onPlaylistChanged = onPlaylistChanged,
        )
    }
}
