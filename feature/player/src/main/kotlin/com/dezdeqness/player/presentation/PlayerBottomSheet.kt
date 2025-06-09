package com.dezdeqness.player.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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

    BottomSheet(
        state = state,
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        onDismiss = {
            state.dismiss()
            playbackConnection.togglePauseResume()
        },
        collapsedContent = {
            MiniPlayer()
        }
    ) {
        PlayerContent(state = state)
    }

}
