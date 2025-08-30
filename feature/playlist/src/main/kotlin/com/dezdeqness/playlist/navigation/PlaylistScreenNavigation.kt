package com.dezdeqness.playlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.playlist.presentattion.PlaylistPage
import com.dezdeqness.playlist.presentattion.model.PlaylistTrackUiModel
import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: Long,
    val title: String,
    val userName: String,
    val imageUrl: String,
    val urn: String,
    val description: String?,
    val duration: Long,
    val tracksCount: Long,
)

fun NavGraphBuilder.playlistScreen(
    onBackClicked: () -> Unit,
    onSongClick: (Int) -> Unit,
    onPlaylistChanged: (List<PlaylistTrackUiModel>) -> Unit,
) {
    composable<Playlist> {
        PlaylistPage(
            onBackClicked = onBackClicked,
            onSongClick = onSongClick,
            onPlaylistChanged = onPlaylistChanged,
        )
    }
}
