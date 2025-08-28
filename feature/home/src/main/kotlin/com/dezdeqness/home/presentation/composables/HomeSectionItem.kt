package com.dezdeqness.home.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.core.ui.views.image.AppImage
import com.dezdeqness.home.presentation.PlaylistTransferObject
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

@Composable
fun HomeSectionItem(
    modifier: Modifier = Modifier,
    item: HomePlaylistUiModel,
    onPlaylistClicked: (PlaylistTransferObject) -> Unit,
) {
    Column(
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable(
                onClick = {
                    onPlaylistClicked.invoke(
                        PlaylistTransferObject(
                            id = item.id,
                            urn = item.urn,
                            title = item.title,
                            description = item.description,
                            userName = item.userName,
                            duration = item.duration,
                            tracksCount = item.tracksCount,
                            imageUrl = item.imageUrl,
                        )
                    )
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = AppTheme.colors.ripple),
            ),
    ) {
        Box {
            AppImage(
                data = item.imageUrl,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .align(alignment = Alignment.Center),
                errorVector = Icons.Default.Lock,
                placeholderVector = Icons.Default.Lock,
            )
        }

        Box(contentAlignment = Alignment.Center) {
            Text(
                text = item.title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 2,
                color = AppTheme.colors.textPrimary,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                style = AppTheme.typography.bodyMedium,
            )

            Text(
                text = "",
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                style = AppTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
