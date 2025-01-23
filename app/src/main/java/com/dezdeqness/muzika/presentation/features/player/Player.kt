package com.dezdeqness.muzika.presentation.features.player

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.dezdeqness.muzika.core.ui.BottomSheet
import com.dezdeqness.muzika.core.ui.BottomSheetState
import com.dezdeqness.muzika.presentation.LocalPlaybackConnection
import kotlinx.coroutines.delay

@Composable
fun Player(
    state: BottomSheetState,
    modifier: Modifier = Modifier,
) {

    val playbackConnection = LocalPlaybackConnection.current ?: return

    val currentMediaItem by playbackConnection.currentMediaItem.collectAsState()

    val mediaItem = currentMediaItem ?: return

    val playBackSate by playbackConnection.playBackState.collectAsState()

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

    BottomSheet(
        state = state,
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        onDismiss = {
            state.dismiss()
            playbackConnection.togglePauseResume()
        },
        collapsedContent = {
            MiniPlayer(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = WindowInsets
                            .navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    ),
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(horizontal = 16.dp)
            ,
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(top = 56.dp)
            ) {
                AsyncImage(
                    mediaItem.mediaMetadata.artworkUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .clip(RoundedCornerShape(8.dp)),
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

                LinearProgressIndicator(
                    progress = { position.toFloat() / duration },
                    trackColor = Color.Gray,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                )
            }

        }
    }

}
