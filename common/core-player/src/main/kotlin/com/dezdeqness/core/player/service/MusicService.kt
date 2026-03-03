package com.dezdeqness.core.player.service

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.dezdeqness.core.network.data.api.StreamsService
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.io.IOException

@OptIn(androidx.media3.common.util.UnstableApi::class)
class MusicService : MediaSessionService(), MediaSession.Callback {

    val playerCache: SimpleCache by inject(named("PlayerCache"))

    val downloaderCache: SimpleCache by inject(named("DownloadCache"))

    val retrieveAccessTokenUseCase: RetrieveAccessTokenUseCase by inject()

    val streamsService: StreamsService by inject()

    private var mediaSession: MediaSession? = null

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onCreate() {
        super.onCreate()

        val playbackAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()

        val player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(createMediaSourceFactory())
            .setAudioAttributes(playbackAttributes, true)
            .build()

        mediaSession = MediaSession
            .Builder(this, player)
            .setCallback(this)
            .build()

        player.addListener(object : Player.Listener {
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                updateCustomLayout()
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                updateCustomLayout()
            }
        })

        updateCustomLayout()
    }

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): MediaSession.ConnectionResult {
        val sessionCommands = MediaSession.ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS
            .buildUpon()
            .add(SHUFFLE_COMMAND)
            .add(REPEAT_COMMAND)
            .build()

        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
            .setAvailableSessionCommands(sessionCommands)
            .setCustomLayout(buildCustomLayout(session.player))
            .build()
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle,
    ): ListenableFuture<SessionResult> {
        when (customCommand.customAction) {
            ACTION_TOGGLE_SHUFFLE -> {
                session.player.shuffleModeEnabled = !session.player.shuffleModeEnabled
            }

            ACTION_CYCLE_REPEAT -> {
                session.player.repeatMode = when (session.player.repeatMode) {
                    Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ONE
                    Player.REPEAT_MODE_ONE -> Player.REPEAT_MODE_ALL
                    else -> Player.REPEAT_MODE_OFF
                }
            }

            else -> return super.onCustomCommand(session, controller, customCommand, args)
        }
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private fun updateCustomLayout() {
        val session = mediaSession ?: return
        session.setCustomLayout(buildCustomLayout(session.player))
    }

    private fun buildCustomLayout(player: Player): List<CommandButton> {
        val shuffleIcon = if (player.shuffleModeEnabled) {
            CommandButton.ICON_SHUFFLE_ON
        } else {
            CommandButton.ICON_SHUFFLE_OFF
        }

        val repeatIcon = when (player.repeatMode) {
            Player.REPEAT_MODE_ONE -> CommandButton.ICON_REPEAT_ONE
            Player.REPEAT_MODE_ALL -> CommandButton.ICON_REPEAT_ALL
            else -> CommandButton.ICON_REPEAT_OFF
        }

        return listOf(
            CommandButton.Builder(shuffleIcon)
                .setSessionCommand(SHUFFLE_COMMAND)
                .setDisplayName("Shuffle")
                .build(),
            CommandButton.Builder(repeatIcon)
                .setSessionCommand(REPEAT_COMMAND)
                .setDisplayName("Repeat")
                .build(),
        )
    }

    private fun createMediaSourceFactory() = DefaultMediaSourceFactory(createDataSourceFactory())

    private fun createDataSourceFactory(): DataSource.Factory {
        return ResolvingDataSource.Factory(
            CacheDataSource
                .Factory()
                .setCache(downloaderCache)
                .setUpstreamDataSourceFactory(
                    CacheDataSource.Factory()
                        .setCache(playerCache)
                        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(this))
                )
        ) { dataSpec ->
            val uri = dataSpec.uri

            if (uri.toString().startsWith("https://")) {
                return@Factory dataSpec
            }

            try {
                val token = runBlocking(Dispatchers.IO) {
                    retrieveAccessTokenUseCase.invoke().getOrNull()
                }

                val streamsResponse = runBlocking(Dispatchers.IO) {
                    streamsService.getStreams(dataSpec.uri.toString())
                }

                val streamUrl = streamsResponse.hlsMp3128Url
                    ?: streamsResponse.previewMp3128Url
                    ?: throw IOException("No stream URL available for track ${dataSpec.uri}")

                dataSpec.withUri(streamUrl.toUri())
                    .withAdditionalHeaders(
                        mapOf("Authorization" to "OAuth $token")
                    )
            } catch (e: IOException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Failed to resolve stream URL for ${dataSpec.uri}", e)
            }
        }
    }

    companion object {
        private const val ACTION_TOGGLE_SHUFFLE = "com.dezdeqness.TOGGLE_SHUFFLE"
        private const val ACTION_CYCLE_REPEAT = "com.dezdeqness.CYCLE_REPEAT"

        val SHUFFLE_COMMAND = SessionCommand(ACTION_TOGGLE_SHUFFLE, Bundle.EMPTY)
        val REPEAT_COMMAND = SessionCommand(ACTION_CYCLE_REPEAT, Bundle.EMPTY)
    }

}
