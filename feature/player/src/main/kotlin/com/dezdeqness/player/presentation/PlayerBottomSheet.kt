package com.dezdeqness.player.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.player.core.BottomSheet
import com.dezdeqness.player.core.BottomSheetState
import com.dezdeqness.player.presentation.composables.MiniPlayer
import com.dezdeqness.player.presentation.composables.PlayerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    state: BottomSheetState,
    modifier: Modifier = Modifier,
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return

    var dominantColor by remember { mutableStateOf(Color.Black) }

    BottomSheet(
        state = state,
        modifier = modifier,
        backgroundColor = dominantColor,
        onDismiss = {
            state.dismiss()
            playbackConnection.togglePauseResume()
        },
        collapsedContent = {
            MiniPlayer(
                onBackgroundColorChanged = {
                    dominantColor = it
                }
            )
        }
    ) {
        PlayerContent(
            state = state,
            onBackgroundColorChanged = {
                dominantColor = it
            }
        )
    }

}
