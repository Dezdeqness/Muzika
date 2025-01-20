package com.dezdeqness.muzika.service

import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.dezdeqness.innertube.core.YouTube
import com.dezdeqness.muzika.data.datasource.PlayerApiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MusicService : MediaSessionService() {

    private val playerApiDataSource: PlayerApiDataSource by inject()

    val playerCache: SimpleCache by inject(named("PlayerCache"))

    val downloaderCache: SimpleCache by inject(named("DownloadCache"))

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
            .setCallback(PlaybackCallback())
            .build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private fun createMediaSourceFactory() = DefaultMediaSourceFactory(createDataSourceFactory())

    private fun createDataSourceFactory(): DataSource.Factory {
        val songUrlCache = HashMap<String, Pair<String, Long>>()

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
            val mediaId = dataSpec.key ?: error("No media id")

            if (downloaderCache.isCached(mediaId, dataSpec.position, if (dataSpec.length >= 0) dataSpec.length else 1) ||
                playerCache.isCached(mediaId, dataSpec.position, 512 * 1024L)
            ) {
                return@Factory dataSpec
            }

            songUrlCache[mediaId]?.takeIf { it.second < System.currentTimeMillis() }?.let {
                return@Factory dataSpec.withUri(it.first.toUri())
            }

            val result = runBlocking(Dispatchers.IO) {
                YouTube.player(mediaId)
            }

            val playerResponse = result.getOrNull()

            val format = playerResponse?.streamingData?.adaptiveFormats
                    ?.filter { it.isAudio }
                    ?.maxByOrNull {
                        it.bitrate * 1 + (if (it.mimeType.startsWith("audio/webm")) 10240 else 0)
                    }!!

            songUrlCache[mediaId] = format.url!! to playerResponse.streamingData!!.expiresInSeconds * 1000L
            dataSpec.withUri(format?.url!!.toUri()).subrange(dataSpec.uriPositionOffset, 512 * 1024L)
        }
    }

}

class PlaybackCallback : MediaSession.Callback {

}
