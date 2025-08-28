package com.dezdeqness.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.home.presentation.HomePage
import com.dezdeqness.home.presentation.PlaylistTransferObject
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeScreen(
    onPlaylistClicked: (PlaylistTransferObject) -> Unit,
) {
    composable<Home> {
        HomePage(onPlaylistClicked = onPlaylistClicked)
    }
}
