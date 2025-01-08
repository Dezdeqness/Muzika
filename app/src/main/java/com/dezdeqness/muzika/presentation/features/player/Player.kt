package com.dezdeqness.muzika.presentation.features.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dezdeqness.muzika.presentation.LocalPlaybackConnection

@Composable
fun Player() {

    val playbackConnection = LocalPlaybackConnection.current ?: return

    val currentMediaItem by playbackConnection.currentMediaItem.collectAsState()

    val mediaItem = currentMediaItem ?: return

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .fillMaxSize()
            .background(Color.White),
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

        Text(
            mediaItem.mediaMetadata.title.toString(),
            fontSize = 24.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            mediaItem.mediaMetadata.artist.toString(),
            fontSize = 20.sp,
            color = Color.DarkGray,
        )

    }
}
