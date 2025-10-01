package com.dezdeqness.player.presentation.composables

import android.util.Log
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import androidx.palette.graphics.Palette
import coil3.asDrawable
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.core.ui.views.buttons.AppIconButton
import com.dezdeqness.core.ui.views.image.AppImage
import com.dezdeqness.core.utils.TimeUtils
import com.dezdeqness.player.core.BottomSheetState
import com.dezdeqness.shared.ui.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerContent(
    modifier: Modifier = Modifier,
    onBackgroundColorChanged: (Color) -> Unit,
    state: BottomSheetState,
) {
    val context = LocalContext.current

    val playbackConnection = LocalPlaybackConnection.current ?: return

    val currentMediaItem by playbackConnection.currentMediaItem.collectAsState()

    val mediaItem = currentMediaItem ?: return

    val playBackSate by playbackConnection.playBackState.collectAsState()

    val isPlaying by playbackConnection.isPlaying.collectAsState()

    var position by rememberSaveable(currentMediaItem?.mediaId) {
        mutableLongStateOf(playbackConnection.mediaController.currentPosition)
    }
    var duration by rememberSaveable(currentMediaItem?.mediaId) {
        mutableLongStateOf(playbackConnection.mediaController.duration)
    }

    LaunchedEffect(position) {
        Log.d("PlayerContent", "Duration: $duration")
    }

    LaunchedEffect(position) {
        Log.d("PlayerContent", "Position: $position")
    }

    var sliderPosition by remember {
        mutableStateOf<Long?>(null)
    }

    LaunchedEffect(playBackSate, mediaItem) {
        if (playBackSate == Player.STATE_READY) {
            while (true) {
                position = playbackConnection.mediaController.currentPosition
                duration = playbackConnection.mediaController.duration
                delay(500)
            }
        }
    }
    val primary = AppTheme.colors.primary

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            state.collapseSoft()
                        },
                    ) {
                        Icon(
                            Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
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
                        .height(350.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    onSuccess = { success ->
                        val drawable = success.result.image.asDrawable(context.resources)
                        val bitmap = (drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
                        bitmap?.let {
                            val colorInt = Palette.from(it)
                                .generate()
                                .getDominantColor(primary.toArgb())
                            onBackgroundColorChanged(Color(colorInt))
                        }
                    }
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    mediaItem.mediaMetadata.title.toString(),
                    fontSize = 24.sp,
                    color = Color.White,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .basicMarquee()
                )

                Text(
                    mediaItem.mediaMetadata.artist.toString(),
                    fontSize = 20.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Slider(
                    value = (sliderPosition ?: position).toFloat(),
                    valueRange = 0f..(if (duration < 0) 0f else duration.toFloat()),
                    onValueChange = {
                        sliderPosition = it.toLong()
                    },
                    onValueChangeFinished = {
                        sliderPosition?.let {
                            playbackConnection.onPositionChanged(it)
                        }
                        sliderPosition = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = Color.White.copy(alpha = 0.3f),
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                    ),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        if (duration < 0) "" else TimeUtils.convertToTrackTime(sliderPosition ?: position),
                        maxLines = 1,
                        fontSize = 16.sp,
                        color = Color.White,
                    )

                    Text(
                        if (duration < 0) "" else TimeUtils.convertToTrackTime(duration),
                        maxLines = 1,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppIconButton(
                    icon = painterResource(R.drawable.ic_previous),
                    tint = Color.White,
                    onClick = {
                        playbackConnection.previousSong()
                    },
                )

                AppIconButton(
                    onClick = {
                        playbackConnection.togglePauseResume()
                    },
                    contentColor = Color.Transparent,
                    content = {
                        Icon(
                            painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_resume),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp),
                        )
                    }
                )

                AppIconButton(
                    icon = painterResource(R.drawable.ic_next),
                    tint = Color.White,
                    onClick = {
                        playbackConnection.nextSong()
                    },
                )
            }

        }
    }
}
