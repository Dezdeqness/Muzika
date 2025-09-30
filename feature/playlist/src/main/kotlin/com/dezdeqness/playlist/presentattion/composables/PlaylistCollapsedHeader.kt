package com.dezdeqness.playlist.presentattion.composables

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistCollapsedHeader(
    modifier: Modifier = Modifier,
    title: String,
    connection: CollapseNestedScrollConnection,
    onClick: () -> Unit,
) {
    var alphaValue by remember { mutableFloatStateOf(0f) }

    val progress = (3 * (1f - connection.progress)).coerceIn(0f, 1f)

    val flyDistance = with(LocalDensity.current) { TopAppBarDefaults.TopAppBarExpandedHeight.toPx() }

    val offsetY = -flyDistance + flyDistance * progress

    alphaValue = progress
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp),
        title = {
            Text(
                text = title,
                color = AppTheme.colors.textPrimary.copy(alpha = alphaValue),
                modifier = Modifier.graphicsLayer {
                    translationY = offsetY
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = AppTheme.colors.onSurface,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background.copy(alpha = alphaValue)
        )
    )

}
