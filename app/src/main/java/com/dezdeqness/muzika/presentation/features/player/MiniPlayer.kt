package com.dezdeqness.muzika.presentation.features.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.dezdeqness.muzika.R
import com.dezdeqness.muzika.presentation.LocalPlaybackConnection
import kotlinx.coroutines.delay

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return

    val currentMediaItem by playbackConnection.currentMediaItem.collectAsState()
    val playBackSate by playbackConnection.playBackState.collectAsState()
    val isPlaying by playbackConnection.isPlaying.collectAsState()

    var position by rememberSaveable(playBackSate) {
        mutableLongStateOf(playbackConnection.mediaController.currentPosition)
    }
    var duration by rememberSaveable(playBackSate) {
        mutableLongStateOf(playbackConnection.mediaController.duration)
    }

    LaunchedEffect(playBackSate) {
        if (playBackSate == Player.STATE_READY) {
            while (true) {
                delay(700)
                position = playbackConnection.mediaController.currentPosition
                duration = playbackConnection.mediaController.duration
            }
        }
    }

    val mediaItem = currentMediaItem ?: return

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    mediaItem.mediaMetadata.artworkUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp)),
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
                        color = Color(0x66FFFFFF),
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                IconButton(
                    onClick = {
                        playbackConnection.togglePauseResume()
                    },
                ) {
                    Icon(
                        painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_resume),
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }

            }

            LinearProgressIndicator(
                progress = { position.toFloat() / duration },
                trackColor = Color.Gray,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(2.dp),
            )
        }
    }

}