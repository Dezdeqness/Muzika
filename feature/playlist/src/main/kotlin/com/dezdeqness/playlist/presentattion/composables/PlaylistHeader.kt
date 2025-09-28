package com.dezdeqness.playlist.presentattion.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.core.ui.views.image.AppImage
import com.dezdeqness.playlist.presentattion.PlaylistPageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistHeader(
    modifier: Modifier = Modifier,
    state: PlaylistPageState,
) {
    Column(
        modifier = modifier
            .padding(top = TopAppBarDefaults.TopAppBarExpandedHeight)
            .height(IntrinsicSize.Max)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AppImage(
                data = state.imageUrl,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(4.dp)),
                errorVector = Icons.Default.Lock,
                placeholderVector = Icons.Default.Lock,
            )

            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    state.title,
                    style = AppTheme.typography.bodyLarge,
                )
                Row {
                    Text(
                        state.tracksCount.toString() + " tracks · " + formatTime(state.duration),
                        style = AppTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                }
                Text(
                    "By " + state.authorName,
                    style = AppTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }
        }
    }
}

private fun formatTime(value: Long, isMillis: Boolean = true): String {
    val totalSeconds = if (isMillis) value / 1000 else value
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0)
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    else
        String.format("%02d:%02d", minutes, seconds)
}
