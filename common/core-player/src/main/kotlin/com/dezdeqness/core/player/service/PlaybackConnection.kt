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

    fun updatePlaylist(mediaItems: List<MediaItem>) {
        mediaController.setMediaItems(mediaItems, false)
    }

    fun nextSong() {
        if (mediaController.hasNextMediaItem()) {
            mediaController.seekToNext()
            mediaController.prepare()
            mediaController.playWhenReady = true
        }
    }

    fun previousSong() {
        if (mediaController.hasPreviousMediaItem()) {
            mediaController.seekToPrevious()
            mediaController.prepare()
            mediaController.playWhenReady = true
        } else {

            mediaController.seekTo(0)
        }
    }

    fun startPlay(index: Int) {
        mediaController.seekTo(index, 0)
        mediaController.prepare()

        mediaController.playWhenReady = true
    }
}
