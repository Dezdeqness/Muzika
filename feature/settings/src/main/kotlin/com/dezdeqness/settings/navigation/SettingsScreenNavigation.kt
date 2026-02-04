package com.dezdeqness.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.settings.presentation.SettingsPage

const val routeSettings = "Settings"

fun NavGraphBuilder.settingsScreen() {
    composable(route = routeSettings) {
        SettingsPage()
    }
}
