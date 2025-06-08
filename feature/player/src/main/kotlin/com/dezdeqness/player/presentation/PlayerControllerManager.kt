package com.dezdeqness.player.presentation

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.dezdeqness.core.player.service.MusicService
import com.dezdeqness.core.player.service.PlaybackConnection
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerControllerManager(
    private val context: Context,
) {
    private val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
    private var controllerFuture: ListenableFuture<MediaController> = MediaController
        .Builder(context, sessionToken)
        .buildAsync()

    private val _playbackConnection = MutableStateFlow<PlaybackConnection?>(null)
    val playbackConnection: StateFlow<PlaybackConnection?> get() = _playbackConnection

    fun connect() {
        if (_playbackConnection.value != null) return

        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener({
            if (controllerFuture.isDone) {
                val mediaController = controllerFuture.get()
                _playbackConnection.value = PlaybackConnection(mediaController)

            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun disconnect(isFinishing: Boolean) {
        if (isFinishing) {
            controllerFuture.let { MediaController.releaseFuture(it) }
            playbackConnection.value?.dispose()
        }
    }
}
