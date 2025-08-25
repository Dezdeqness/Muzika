package com.dezdeqness.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.home.presentation.HomePage

const val HOME_ROUTE = "home_route"

fun NavGraphBuilder.homeScreen(
) {
    composable(HOME_ROUTE) {
        HomePage()
    }
}
