package com.dezdeqness.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.core.ui.views.toolbar.AppToolbar
import com.dezdeqness.settings.presentation.composables.AboutBlock
import com.dezdeqness.settings.presentation.composables.AppearanceBlock
import com.dezdeqness.settings.presentation.composables.StorageBlock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = AppTheme.colors.success,
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            AppToolbar(
                title = "Settings",
                navigationClick = onBackClick,
                windowInsets = WindowInsets(0.dp),
            )
        },
    ) { padding ->
        val blockModifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, AppTheme.colors.border, RoundedCornerShape(8.dp))

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(AppTheme.colors.onPrimary),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item() {
                AppearanceBlock(modifier = blockModifier)
            }

            item() {
                StorageBlock(modifier = blockModifier)
            }

            item() {
                AboutBlock(modifier = blockModifier)
            }
        }
    }
}
