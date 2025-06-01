package com.dezdeqness.likedtracks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.likedtracks.presentation.LikedTrackPage

const val LIKED_ROUTE = "liked_route"

fun NavGraphBuilder.likedScreen() {
    composable(LIKED_ROUTE) {
        LikedTrackPage()
    }
}
