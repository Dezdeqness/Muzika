package com.dezdeqness.playlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.playlist.presentattion.PlaylistPage
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
) {
    composable<Playlist> {
        PlaylistPage()
    }
}
