package com.dezdeqness.settings.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dezdeqness.core.ui.views.settings.HeaderSettingsView
import com.dezdeqness.core.ui.views.settings.SwitchSettingsView
import com.dezdeqness.settings.domain.models.NightThemePreference
import com.dezdeqness.settings.presentation.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppearanceBlock(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel(),
    ) {
    val isDarkThemeEnabled by settingsViewModel
        .observe(NightThemePreference)
        .collectAsStateWithLifecycle(false)

    Column(modifier = modifier) {
        HeaderSettingsView(title = "Appearance")
        SwitchSettingsView(
            title = "Dark mode",
            subtitle = "Enable dark theme",
            checked = isDarkThemeEnabled,
        ) { isChecked ->
            settingsViewModel.set(NightThemePreference, isChecked)
        }
    }
}
