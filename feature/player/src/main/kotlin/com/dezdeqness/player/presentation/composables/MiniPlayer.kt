package com.dezdeqness.player.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import androidx.palette.graphics.Palette
import coil3.asDrawable
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.core.ui.views.buttons.AppIconButton
import com.dezdeqness.core.ui.views.image.AppImage
import com.dezdeqness.shared.ui.R
import kotlinx.coroutines.delay

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    onBackgroundColorChanged: (Color) -> Unit,
) {
    val context = LocalContext.current

    val playbackConnection = LocalPlaybackConnection.current ?: return

    val currentMediaItem by playbackConnection.currentMediaItem.collectAsState()
    val playBackSate by playbackConnection.playBackState.collectAsState()
    val isPlaying by playbackConnection.isPlaying.collectAsState()

    var position by rememberSaveable(currentMediaItem?.mediaId) {
        mutableLongStateOf(playbackConnection.mediaController.currentPosition)
    }
    var duration by rememberSaveable(currentMediaItem?.mediaId) {
        mutableLongStateOf(playbackConnection.mediaController.duration)
    }

    LaunchedEffect(playBackSate) {
        if (playBackSate == Player.STATE_READY) {
            while (true) {
                position = playbackConnection.mediaController.currentPosition
                duration = playbackConnection.mediaController.duration
                delay(500)
            }
        }
    }

    val mediaItem = currentMediaItem ?: return

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth().padding(horizontal = 16.dp)
            .wrapContentHeight()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val request = remember(mediaItem.mediaMetadata.artworkUri) {
                    ImageRequest.Builder(context)
                        .data(mediaItem.mediaMetadata.artworkUri)
                        .allowHardware(false)
                        .build()
                }
                AppImage(
                    request = request,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    onSuccess = { success ->
                        val drawable = success.result.image.asDrawable(context.resources)
                        val bitmap = (drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
                        bitmap?.let {
                            val colorInt = Palette.from(it)
                                .generate()
                                .getDominantColor(Color.Black.toArgb())
                            onBackgroundColorChanged(Color(colorInt))
                        }
                    }
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        mediaItem.mediaMetadata.title.toString(),
                        fontSize = 18.sp,
                        maxLines = 1,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        mediaItem.mediaMetadata.artist.toString(),
                        fontSize = 16.sp,
                        maxLines = 1,
                        color = Color.White.copy(alpha = 0.7f),
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                AppIconButton(
                    icon = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_resume),
                    tint = Color.White,
                    onClick = {
                        playbackConnection.togglePauseResume()
                    },
                )
            }

            LinearProgressIndicator(
                progress = { position.toFloat() / duration },
                trackColor = Color.White.copy(alpha = 0.3f),
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(2.dp),
                drawStopIndicator = {},
            )
        }
    }

}
