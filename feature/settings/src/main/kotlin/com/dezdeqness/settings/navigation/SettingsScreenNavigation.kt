package com.dezdeqness.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dezdeqness.settings.presentation.SettingsPage
import kotlinx.serialization.Serializable

@Serializable
object Settings

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit,
) {
    composable<Settings> {
        SettingsPage(
            onBackClick = onBackClick,
        )
    }
}
