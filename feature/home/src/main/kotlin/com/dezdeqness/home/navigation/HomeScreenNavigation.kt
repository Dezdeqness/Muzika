package com.dezdeqness.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.home.presentation.HomePage
import com.dezdeqness.home.presentation.PlaylistTransferObject

const val routeHome = "Home"

fun NavGraphBuilder.homeScreen(
    onPlaylistClicked: (PlaylistTransferObject) -> Unit,
) {
    composable(route = routeHome) {
        HomePage(onPlaylistClicked = onPlaylistClicked)
    }
}
