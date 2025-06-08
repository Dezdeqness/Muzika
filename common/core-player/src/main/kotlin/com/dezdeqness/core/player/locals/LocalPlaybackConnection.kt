package com.dezdeqness.core.player.locals

import androidx.compose.runtime.staticCompositionLocalOf
import com.dezdeqness.core.player.service.PlaybackConnection

val LocalPlaybackConnection =
    staticCompositionLocalOf<PlaybackConnection?> { error("No PlaybackConnection provided") }
