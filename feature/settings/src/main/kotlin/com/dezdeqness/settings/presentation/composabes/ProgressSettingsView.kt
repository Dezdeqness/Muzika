package com.dezdeqness.settings.presentation.composabes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressSettingsView(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    subtitle: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LinearProgressIndicator(
            progress = progress,
            modifier = modifier,
        )

        subtitle()
    }
}
