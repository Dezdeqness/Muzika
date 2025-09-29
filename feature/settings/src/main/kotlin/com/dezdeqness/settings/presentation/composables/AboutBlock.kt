package com.dezdeqness.settings.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dezdeqness.core.ui.views.settings.HeaderSettingsView
import com.dezdeqness.core.ui.views.settings.TextSettingsView
import com.dezdeqness.settings.core.LocalVersionName

@Composable
fun AboutBlock(modifier: Modifier = Modifier) {
    val versionName = LocalVersionName.current

    Column(modifier = modifier) {
        HeaderSettingsView(title = "About")
        TextSettingsView(
            title = "Version",
            subtitle = versionName,
        )
    }
}
