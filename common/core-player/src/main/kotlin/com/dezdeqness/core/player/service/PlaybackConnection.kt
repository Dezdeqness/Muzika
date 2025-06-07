package com.dezdeqness.core.player.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import kotlinx.coroutines.flow.MutableStateFlow

class PlaybackConnection(
    val mediaController: MediaController,
) : Player.Listener {

    init {
        mediaController.addListener(this)
    }

    val isPlaying = MutableStateFlow(mediaController.playWhenReady)
    val currentMediaItem = MutableStateFlow(mediaController.currentMediaItem)
    val playBackState = MutableStateFlow(mediaController.playbackState)

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        isPlaying.value = playWhenReady
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        currentMediaItem.value = mediaItem
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        playBackState.value = state
    }

    fun onPositionChanged(position: Long) {
        mediaController.seekTo(position)
    }

    fun togglePauseResume() {
        mediaController.playWhenReady = !mediaController.playWhenReady
    }

    fun dispose() {
        mediaController.removeListener(this)
    }
}
